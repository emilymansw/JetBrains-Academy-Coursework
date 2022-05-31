package account.service;

import account.model.Event;
import account.model.Payroll;
import account.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepo;

    public Event save(Event toSave) {
        return eventRepo.save(toSave);
    }

    public List<Event> loadAllEvent() {
        return eventRepo.findAll();
    }
}
