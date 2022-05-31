package account.util;

import account.model.Event;
import account.model.User;
import account.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private EventService eventService;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied!");
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String path = request.getRequestURI();
        Event event = new Event();
        event.setDate(new Date(System.currentTimeMillis()));
        event.setAction("ACCESS_DENIED");
        event.setSubject(currentUser.getEmail().toLowerCase());
        event.setObject(path);
        event.setPath("api/auth/changepass");
        eventService.save(event);
    }
}