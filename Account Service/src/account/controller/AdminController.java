package account.controller;

import account.model.*;
import account.repository.GroupRepository;
import account.service.EventService;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
public class AdminController {
    @Autowired
    UserService userservice;
    @Autowired
    GroupRepository groupRepo;
    @Autowired
    private EventService eventService;

    @GetMapping("api/admin/user")
    public List<Map<String, Object>> getAllUser(){
        List<User> users = userservice.loadAllUsers();
        List<Map<String, Object>> usersResponse = new ArrayList<>();
        for(User user:users){
            List<String> roles = new ArrayList<>();
            for (Group role : user.getRole()) {
                roles.add(role.getCode());
            }
            Collections.sort(roles);
            usersResponse.add(Map.of(
                    "id", user.getId(),
                    "name", user.getName(),
                    "lastname", user.getLastname(),
                    "email", user.getEmail().toLowerCase(),
                    "roles", roles));
        }
        return usersResponse;
    }

    @DeleteMapping("api/admin/user/{email}")
    public Map<String, String> deleteUser(@PathVariable String email){
        User user = (User) userservice.loadUserByUsername(email);
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(Group role : user.getRole()){
            if(role.getCode().equals("ROLE_ADMINISTRATOR")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Can't remove ADMINISTRATOR role!");
            }
        }
        Event event = new Event();
        event.setSubject(admin.getEmail().toLowerCase());
        event.setDate(new Date(System.currentTimeMillis()));
        event.setPath("/api/admin/user/role");
        event.setAction("DELETE_USER");
        event.setObject(user.getEmail().toLowerCase());
        eventService.save(event);
        userservice.deleteUser(user);
        return Map.of("user", email, "status", "Deleted successfully!");
    }

    @PutMapping("api/admin/user/role")
    public Map<String, Object> updateUser(@RequestBody RoleChanges roleChanges){
        User user = (User) userservice.loadUserByUsername(roleChanges.getUser());
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Group> roles = user.getRole();
        String toUpdate = String.format("ROLE_%s",roleChanges.getRole());
        if(roleChanges.getOperation().equals("REMOVE")){
            if(roleChanges.getRole().equals("ADMINISTRATOR")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Can't remove ADMINISTRATOR role!");
            }
            if(!roles.contains(groupRepo.findByCode(toUpdate).get())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The user does not have a role!");
            }
            if(roles.size()==1){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The user must have at least one role!");
            }
            for (Iterator<Group> iterator = roles.iterator(); iterator.hasNext();) {
                Group role = iterator.next();
                if(role.getCode().equals(String.format("ROLE_%s",roleChanges.getRole()))) {
                    iterator.remove();
                }
            }
            Event event = new Event();
            event.setSubject(admin.getEmail().toLowerCase());
            event.setDate(new Date(System.currentTimeMillis()));
            event.setPath("/api/admin/user/role");
            event.setAction("REMOVE_ROLE");
            event.setObject(String.format("Remove role %s from %s", roleChanges.getRole(), user.getEmail().toLowerCase()));
            eventService.save(event);

        }else if(roleChanges.getOperation().equals("GRANT")) {
                if(roleChanges.getRole().equals("ADMINISTRATOR") &&
                   user.getRole().contains(groupRepo.findByCode("ROLE_USER").get()) ||
                   user.getRole().contains(groupRepo.findByCode("ROLE_ADMINISTRATOR").get()) &&
                   !roleChanges.getRole().equals("ADMINISTRATOR")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "The user cannot combine administrative and business roles!");
                }

            if(groupRepo.findByCode(toUpdate).isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
            }
            Event event = new Event();
            event.setSubject(admin.getEmail().toLowerCase());
            event.setDate(new Date(System.currentTimeMillis()));
            event.setPath("/api/admin/user/role");
            event.setAction("GRANT_ROLE");
            event.setObject(String.format("Grant role %s to %s", roleChanges.getRole(), user.getEmail().toLowerCase()));
            eventService.save(event);
            roles.add(groupRepo.findByCode(toUpdate).get());
        }
        user.setRoles(roles);
        userservice.save(user);
        List<String> newRoles = new ArrayList<>();
        for (Group role : user.getRole()) {
            newRoles.add(role.getCode());
        }
        Collections.sort(newRoles);

        return Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "lastname", user.getLastname(),
                "email", user.getEmail().toLowerCase(),
                "roles", newRoles);
    }

    @PutMapping("api/admin/user/access")
    public Map<String, String> updateAccess(@RequestBody @Valid Access access){
        User user = (User) userservice.loadUserByUsername(access.getUser());
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (access.getOperation().equals("LOCK")) {
            for(Group role : user.getRole()){
                if(role.getCode().equals("ROLE_ADMINISTRATOR")){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Can't lock the ADMINISTRATOR!");
                }
            }
            userservice.lock(user);
            Event event = new Event();
            event.setSubject(admin.getEmail().toLowerCase());
            event.setDate(new Date(System.currentTimeMillis()));
            event.setPath("/api/admin/user/role");
            event.setAction("LOCK_USER");
            event.setObject(String.format("Lock user %s", user.getEmail().toLowerCase()));
            eventService.save(event);
        } else {
            userservice.unlock(user);
            Event event = new Event();
            event.setSubject(admin.getEmail().toLowerCase());
            event.setDate(new Date(System.currentTimeMillis()));
            event.setPath("/api/admin/user/role");
            event.setAction("UNLOCK_USER");
            event.setObject(String.format("Unlock user %s", user.getEmail().toLowerCase()));
            eventService.save(event);
        }
        return Map.of("status", String.format("User %s %sed!", access.getUser().toLowerCase(), access.getOperation().toLowerCase()));
    }
}
