package account.util;

import account.model.User;
import account.repository.UserRepository;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccessEventListener implements
        ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        User currentUser = (User) event.getAuthentication().getPrincipal();
        User user = (User) userService.loadUserByUsername(currentUser.getEmail());
        try {
            user.setFailedAttempt(0);
            userService.save(user);
        } catch (Exception e) {
            System.out.println("_________________________NULL_USER________________________");
        }


    }
}