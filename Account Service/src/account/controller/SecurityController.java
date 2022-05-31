package account.controller;

import account.model.Event;
import account.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/security")
public class SecurityController {
    @Autowired
    EventService eventService;

    @GetMapping("/events")
    public List<Event> getEvents(){
        return eventService.loadAllEvent();
    }
}
