/*package com.golfsprint.GolfSprint.Events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.golfsprint.GolfSprint.Company.Company;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="event")
public class Event {
    @Id
    @SequenceGenerator(
            name="event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_sequence"
    )
    @Column(name = "event_id")
    private Long eventId;
    private String eventName;
    @Column(length = 500)
    private String eventDescription;
    @Lob
    private String eventImage;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id",referencedColumnName = "company_id") // This assumes a column "company_id" in the User table
    private Company company;
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "dd/MM/yyyy, HH:mm")
    private LocalDateTime eventDate;
    public Event(String eventName, String eventDescription, LocalDateTime eventDate){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
    }
    public Event(String eventName, String eventDescription,LocalDateTime eventDate, String eventImage){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventImage = eventImage;
        this.eventDate = eventDate;
    }
    public Event(String eventName, String eventDescription,LocalDateTime eventDate, Company company){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.company = company;
        this.eventDate = eventDate;
    }
    public Event(String eventName, String eventDescription, String eventImage,LocalDateTime eventDate, Company company){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventImage = eventImage;
        this.company = company;
        this.eventDate = eventDate;
    }
}
*/