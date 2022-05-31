package account.controller;

import account.model.Event;
import account.model.Group;
import account.model.NewPassword;
import account.model.User;
import account.repository.GroupRepository;
import account.service.EventService;
import account.service.UserService;
import account.util.breachedPasswords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
public class AuthorizationController {
    @Autowired
    UserService userservice;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    EventService eventService;

    @PostMapping("/api/auth/signup")
    public Map<String, Object> userSignUp(@RequestBody @Valid User user){
        userservice.loadUserByEmail(user.getEmail());
        Group group;
        if(!userservice.isAnyUser()){
            System.out.println(groupRepository.findByCode("ROLE_ADMINISTRATOR").isEmpty());
            group = groupRepository.findByCode("ROLE_ADMINISTRATOR").get();
        } else {
            group = groupRepository.findByCode("ROLE_USER").get();
        }
        user.setRoles(Set.of(group));
        if(user.getPassword().length() < 12){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password length must be 12 chars minimum!");
        }
        if(breachedPasswords.getList().contains(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The password is in the hacker's database!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userservice.save(user);
        List<String> roles = new ArrayList<>();
        for (Group role : user.getRole()) {
            roles.add(role.getCode());
        }
        Event event = new Event();
        event.setDate(new Date(System.currentTimeMillis()));
        event.setAction("CREATE_USER");
        event.setSubject("Anonymous");
        event.setObject(user.getEmail().toLowerCase());
        event.setPath("/api/auth/signup");
        eventService.save(event);
        System.out.println("Create user " + user.getEmail());
        System.out.println(userservice.loadUserByUsername(user.getEmail()).getUsername());
        return Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "lastname", user.getLastname(),
                "email", user.getEmail(),
                "roles", roles);
    }

    @PostMapping("api/auth/changepass")
    public Map<String, String> changePassword(@RequestBody @Valid NewPassword newPassword){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(newPassword.getNew_password().length() < 12){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password length must be 12 chars minimum!");
        }
        if(breachedPasswords.getList().contains(newPassword.getNew_password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The password is in the hacker's database!");
        }
        if(passwordEncoder.matches(newPassword.getNew_password(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The passwords must be different!");
        }
        user.setPassword(passwordEncoder.encode(newPassword.getNew_password()));
        userservice.save(user);
        Event event = new Event();
        event.setDate(new Date(System.currentTimeMillis()));
        event.setAction("CHANGE_PASSWORD");
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setSubject(currentUser.getEmail().toLowerCase());
        event.setObject(user.getEmail().toLowerCase());
        event.setPath("api/auth/changepass");
        eventService.save(event);
        return Map.of("email", user.getEmail().toLowerCase(), "status", "The password has been updated successfully");
    }

}
