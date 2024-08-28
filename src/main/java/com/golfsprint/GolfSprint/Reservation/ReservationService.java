package com.golfsprint.GolfSprint.Reservation;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.User.User;
import com.golfsprint.GolfSprint.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public List<Reservation> getReservationsByCompany(String companyEmail){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        else {
            return reservationRepository.findAll().stream()
                    .filter(reservation -> reservation.getCompany() != null && reservation.getCompany().equals(companyOptional.get()) && reservation.getUser().getCompany().equals(companyOptional.get()))
                    .sorted(Comparator.comparing(Reservation::getDate).thenComparing(Reservation::getStart))
                    .collect(Collectors.toList());
        }
    }
    public List<Reservation> getReservationsByUser(String userEmail){
        Optional<User> user = userRepository.findUserByEmail(userEmail);
        if(user.isEmpty()){
            throw new IllegalStateException("Unauthorized");
        }

        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getCompany() != null && reservation.getCompany().equals(user.get().getCompany()))
                .sorted(Comparator.comparing(Reservation::getDate).thenComparing(Reservation::getStart))
                .collect(Collectors.toList());

    }
    /*
    public Reservation getReservation(Long id){
        return reservationRepository.findReservationById(id).orElseThrow(()->new IllegalStateException("Not found"));
    } */
    public ResponseEntity<String> addReservation(Reservation reservation, Long id, String companyEmail){
        if(id == null){
            return ResponseEntity.status(403).body("Don't forget to submit the user id.");
        }
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()->new IllegalStateException("Error."));

        Optional<User> user = userRepository.findUserById(id);

        if(user.isPresent()) {
            if(!user.get().getCompany().equals(company)){
                return ResponseEntity.status(403).body("There is no such user with Id: "+ id);
            }
            Optional<Reservation> reservationByUser = reservationRepository.findReservationByUser(user.get());
            if(reservationByUser.isPresent()){
                return ResponseEntity.status(403).body(user.get().getName()+" "+user.get().getSurname()+" (Reservation Id:"+user.get().getUserId()+") already has a reservation.");
            }
            if(company.getStartBoundary() != null && company.getFinishBoundary() != null) {
                if (reservation.getStart().isBefore(company.getStartBoundary()) || reservation.getFinish().isAfter(company.getFinishBoundary())) {
                    return ResponseEntity.status(403).body("You configured reservations between these times: " + company.getStartBoundary() + "-" + company.getFinishBoundary() + ". If you want to change or reset, please configure again.");
                }
            }
            Optional<Reservation> reservationOptional = reservationRepository.findReservationByStartAndFinishAndDateAndCompany(reservation.getStart(), reservation.getFinish(), reservation.getDate(),company);
            List<Reservation> reservations = reservationRepository.findReservationsByDateAndCompany(reservation.getDate(),company).orElseThrow(()-> new IllegalStateException("Empty."));
            for (Reservation existingReservation : reservations) {
                if (isOverlapping(existingReservation, reservation)) {
                    return ResponseEntity.status(403).body("Reservation time overlaps with existing reservation.");
                }
            }
            if (reservationOptional.isPresent()) {
                return ResponseEntity.status(403).body("Already reserved.");
            }
            if (reservation.getDate().isBefore(LocalDate.now())) {
                return ResponseEntity.status(403).body("Not a valid date.");
            }
            if (reservation.getFinish().isBefore(reservation.getStart())) {
                return ResponseEntity.status(403).body("Not a valid time.");
            }
            if (reservation.getDate().equals(LocalDate.now()) && reservation.getStart().isBefore(LocalTime.now())) {
                return ResponseEntity.status(403).body("Not a valid time.");
            }
            if(reservation.getCourtName().length() > 35){
                return ResponseEntity.status(403).body("Court name or instructor name is too long.");
            }
            else {
                reservation.setUser(user.get());
                reservation.setCompany(company);
                reservationRepository.save(reservation);
                return ResponseEntity.ok("Success");
            }
        }
        else return ResponseEntity.status(403).body("There is no such user with Id: "+ id);
    }
    private boolean isOverlapping(Reservation existingReservation, Reservation newReservation) {
        return (newReservation.getStart().isBefore(existingReservation.getFinish()) &&
                newReservation.getStart().isAfter(existingReservation.getStart())) ||
                (newReservation.getFinish().isBefore(existingReservation.getFinish()) &&
                        newReservation.getFinish().isAfter(existingReservation.getStart()));
    }
    public ResponseEntity<String> deleteReservation(Long id, String companyEmail){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        Optional<Company> company = companyRepository.findCompanyByEmail(companyEmail);
        if(reservation.isPresent() && company.isPresent() && reservation.get().getCompany().equals(company.get())){
            reservationRepository.deleteById(id);
            return ResponseEntity.ok("Successfully deleted");
        }
        else return ResponseEntity.status(404).body("There is no such reservation");
    }
    /*
    @Transactional
    public void updateReservation(Long id, User user, String courtName, LocalDate date, LocalTime start, LocalTime finish, String teacherName){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()->new IllegalStateException("Reservation with id "+id+" does not exist"));
        if(user != null){
            reservation.setUser(user);
        }
        if(courtName != null && !courtName.isEmpty() && !Objects.equals(reservation.getCourtName(),courtName)){
            reservation.setCourtName(courtName);
        }
        if(date != null && !Objects.equals(reservation.getDate(),date) && !date.isBefore(LocalDate.now())){
            reservation.setCourtName(courtName);
        }
        if(start != null && !(reservation.getDate().equals(LocalDate.now()) && start.isBefore(LocalTime.now()))){
            reservation.setStart(start);
        }
        if(finish != null && finish.isAfter(reservation.getStart())){
            reservation.setFinish(finish);
        }
        if(teacherName != null && !Objects.equals(reservation.getTeacherName(), teacherName)){
            reservation.setTeacherName(teacherName);
        }

    } */
    @Transactional
    public void deleteAllReservationByCompany(Company company){
        List<Reservation> reservations = reservationRepository.findReservationsByCompany(company).orElseThrow(() -> new IllegalStateException("Error reservation"));
        reservationRepository.deleteAll(reservations);
    }
    public Reservation getReservationByUser(String email){
        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new IllegalStateException("No such user exists."));
        return reservationRepository.findReservationByUser(user.getUserId()).orElseThrow(()-> new IllegalStateException("No such reservation exists."));

    }
}
