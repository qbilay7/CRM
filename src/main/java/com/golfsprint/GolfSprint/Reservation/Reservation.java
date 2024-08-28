package com.golfsprint.GolfSprint.Reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.User.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @SequenceGenerator(
            name="reservation_sequence",
            sequenceName = "reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_sequence"
    )
    @Column(name = "reservation_id")
    private Long reservationId;
    @OneToOne( targetEntity = User.class)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
    private String courtName;
    //@JsonFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime start;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime finish;
    @ManyToOne
    private Company company;


    public Reservation(User user, String courtName, LocalDate date, LocalTime start, LocalTime finish, Company company) {
        this.user = user;
        this.courtName = courtName;
        this.date = date;
        this.start = start;
        this.finish = finish;
        this.company = company;
    }


}
