package account.util;

import account.model.Event;
import account.model.Group;
import account.model.User;
import account.repository.UserRepository;
import account.service.EventService;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;


@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final EventService eventService;
    private final UserService userService;

    private final HttpServletRequest request;

    @Autowired
    public AuthenticationFailureListener(EventService eventService, UserService userService, HttpServletRequest request) {
        this.eventService = eventService;
        this.userService = userService;
        this.request = request;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = Optional.of(event.getAuthentication().getName()).orElse("Anonymous");
        String path = request.getRequestURI();
        Event logEvent = new Event();
        logEvent.setDate(new Date(System.currentTimeMillis()));
        logEvent.setSubject(username);
        logEvent.setPath(path);
        logEvent.setAction("LOGIN_FAILED");
        logEvent.setObject(path);
        eventService.save(logEvent);


        Optional<User> userOptional = userService.findFirstByEmail(username);
        if(userOptional.isPresent()){
            User currentUser = userOptional.get();
            for(Group role : currentUser.getRole()) {
                if (role.getCode().equals("ROLE_ADMINISTRATOR")) {
                    return;
                }
            }
            int failAttempt = currentUser.getFailedAttempt() + 1;

            if (failAttempt >= 5) {
                Event logEvent2 = new Event();
                logEvent2.setSubject(currentUser.getEmail().toLowerCase());
                logEvent2.setDate(new Date(System.currentTimeMillis()));
                logEvent2.setPath(path);
                logEvent2.setAction("BRUTE_FORCE");
                logEvent2.setObject(path);
                eventService.save(logEvent2);


                Event nextEvent = new Event();
                nextEvent.setSubject(currentUser.getEmail().toLowerCase());
                nextEvent.setDate(new Date(System.currentTimeMillis()));
                nextEvent.setPath(path);
                nextEvent.setAction("LOCK_USER");
                nextEvent.setObject(String.format("Lock user %s", currentUser.getEmail().toLowerCase()));
                eventService.save(nextEvent);
                userService.lock(currentUser);

            }
            currentUser.setFailedAttempt(failAttempt);
            userService.save(currentUser);

        }


    }
}

