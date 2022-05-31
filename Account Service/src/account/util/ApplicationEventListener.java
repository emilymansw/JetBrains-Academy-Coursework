package account.util;

/*
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


@Component
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("rrrrrrr"+ event.getSource().toString());
        if (event instanceof AuthenticationSuccessEvent) {
            System.out.println("AuthenticationSuccessEvent " +((AbstractAuthenticationEvent) event).getAuthentication().getName());
        }
        if (event instanceof AbstractAuthenticationFailureEvent) {
            String username = ((AbstractAuthenticationFailureEvent) event).getAuthentication().getName();
            System.out.println("AbstractAuthenticationFailureEvent " +username);
            String cause = ((AbstractAuthenticationFailureEvent) event).getException().toString();
            System.out.println("AbstractAuthenticationFailureEvent " +cause);
            if (event instanceof AuthenticationFailureBadCredentialsEvent) {
                System.out.println("AuthenticationFailureBadCredentialsEvent " +((AuthenticationFailureBadCredentialsEvent) event).getException().toString());
            }
        }
    }
}

 */