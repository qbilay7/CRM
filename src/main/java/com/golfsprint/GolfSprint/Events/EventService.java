/*package com.golfsprint.GolfSprint.Events;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final CompanyRepository companyRepository;
    public List<Event> getEventsByCompany(String companyEmail){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return eventRepository.findAll().stream()
                .filter(event -> event.getCompany() != null && event.getCompany().equals(companyOptional.get())).sorted(Comparator.comparing(Event::getEventDate))
                .collect(Collectors.toList());
    }
    public List<Event> getEventsByCompanyId(Long companyId){
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return eventRepository.findAll().stream()
                .filter(event -> event.getCompany() != null && event.getCompany().equals(companyOptional.get())).sorted(Comparator.comparing(Event::getEventDate))
                .collect(Collectors.toList());
    }
    public ResponseEntity<String> addEvent(Event event, String companyEmail){
        if(event.getEventDate().isBefore(LocalDateTime.now())){
            return ResponseEntity.status(403).body("Please enter a valid date.");
        }
        if(event.getEventDescription().length() > 500){
            return ResponseEntity.status(403).body("Text is too long.");
        }
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()-> new IllegalStateException("Company not found"));
        event.setCompany(company);
        eventRepository.save(event);
        return ResponseEntity.ok("Success");
    }
    public void deleteEvent(Long eventId){
        boolean exists = eventRepository.existsById(eventId);
        if(!exists){
            throw new IllegalStateException("Event with id"+eventId+"does not exist");
        }
        eventRepository.deleteById(eventId);
    }
    @Transactional
    public ResponseEntity<String> updateEvent(String companyEmail , Long eventId, String eventName, String eventDescription, String eventImage, LocalDateTime eventDate){
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()->new IllegalStateException("Company with email"+companyEmail+"does not exist"));
        Event event = eventRepository.findById(eventId).orElseThrow(()->new IllegalStateException("Event with id"+eventId+"does not exist"));
        if(event.getCompany().equals(company)) {
            event.setEventImage(eventImage);
            if (eventName != null && !eventName.isEmpty() && !Objects.equals(event.getEventName(), eventName)) {
                event.setEventName(eventName);
            }
            if(!eventDescription.isEmpty()) {
                event.setEventDescription(eventDescription);
            }
            if(eventDate.isBefore(LocalDateTime.now())){
                return ResponseEntity.status(403).body("Please enter a valid date.");
            }
            event.setEventDate(eventDate);
            eventRepository.save(event);
            return ResponseEntity.ok("Success");
        }
        else return ResponseEntity.status(403).body("Unauthorized");
    }
    public Event getEvent(Long eventId){
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isPresent()) return event.get();
        else throw new IllegalStateException("There is no such event.");
    }
    @Transactional
    public void deleteAllEventsByCompany(Company company){
        List<Event> events = eventRepository.findEventsByCompany(company).orElseThrow(() -> new IllegalStateException("Error event"));
        eventRepository.deleteAll(events);
    }
}
*/