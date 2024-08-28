package com.golfsprint.GolfSprint.Reservation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
    @GetMapping(path = "/getAll")
    public List<Reservation> getReservations(@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return reservationService.getReservationsByCompany(companyEmail);
    }
    @GetMapping(path = "/getAllByUser")
    public List<Reservation> getReservationsByUser(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
        return reservationService.getReservationsByUser(userEmail);
    }
    @PostMapping(path = "/add/{userId}")
    public ResponseEntity<String> addReservation(@RequestBody Reservation reservation, @PathVariable("userId") Long id, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
       return reservationService.addReservation(reservation,id,companyEmail);
    }
    @DeleteMapping(path = "/delete/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable("reservationId") Long id, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
       return reservationService.deleteReservation(id,companyEmail);
    }
    @GetMapping(path = "/get")
    public Reservation getReservationByUser(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return reservationService.getReservationByUser(email);
    }

}
