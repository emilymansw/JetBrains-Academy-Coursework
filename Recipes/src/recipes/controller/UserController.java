package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.User;
import recipes.repository.UserRepository;
import recipes.service.UserRepositoryUserDetailsService;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepositoryUserDetailsService userDetailsService;

    @PostMapping("/api/register")
    public void userRegister(@Valid @RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));

        if(userDetailsService.loadUserByUsername(user.getEmail()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already existed");
        }

        userRepo.save(user);
    }
}
