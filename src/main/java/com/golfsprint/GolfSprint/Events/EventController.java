/*package com.golfsprint.GolfSprint.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/events")
public class EventController {
    private final EventService eventService;
    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }
    @GetMapping(path = "/getAll/{companyEmail}")
    public List<Event> getEvents(@PathVariable("companyEmail") String companyEmail){
        return eventService.getEventsByCompany(companyEmail);
    }
    @GetMapping(path = "/getAllById/{companyId}")
    public List<Event> getEventsById(@PathVariable("companyId") Long companyId){
        return eventService.getEventsByCompanyId(companyId);
    }
    @PostMapping(path = "/add/{companyEmail}")
    public ResponseEntity<String> addEvent(@RequestBody Event event, @PathVariable("companyEmail") String companyEmail){
       return eventService.addEvent(event, companyEmail);
    }
    @DeleteMapping(path = "/delete/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable("eventId") Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Successfully deleted.");
    }
    @PutMapping(path = "/update/{companyEmail}/{eventId}")
    public ResponseEntity<String> updateEvent(@PathVariable("companyEmail") String companyEmail,@PathVariable("eventId") Long eventId, @RequestBody Event event){
       return eventService.updateEvent(companyEmail,eventId, event.getEventName(), event.getEventDescription(), event.getEventImage(), event.getEventDate());
    }
    @GetMapping(path = "/get/{eventId}")
    public Event getEvent(@PathVariable("eventId") Long eventId){
        return eventService.getEvent(eventId);
    }
}
*/