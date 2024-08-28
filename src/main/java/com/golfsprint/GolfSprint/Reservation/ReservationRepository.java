package com.golfsprint.GolfSprint.Reservation;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT s FROM Reservation s WHERE s.reservationId=?1")
    Optional<Reservation> findReservationById(Long reservationId);
    @Query("SELECT s FROM Reservation s WHERE s.user.userId=?1")
    Optional<Reservation> findReservationByUser(Long userId);
    @Query("SELECT s FROM Reservation s WHERE s.date=?1")
    Optional<Reservation> findReservationByDate(LocalDate date);
    @Query("SELECT s FROM Reservation s WHERE s.start=?1")
    Optional<Reservation> findReservationByStart(LocalTime start);
    @Query("SELECT s FROM Reservation s WHERE s.finish=?1")
    Optional<Reservation> findReservationByFinish(LocalTime finish);

    Optional<Reservation> findReservationByStartAndFinishAndDateAndCompany(LocalTime start, LocalTime finish, LocalDate date, Company company);
    Optional<List<Reservation>> findReservationsByDateAndCompany(LocalDate date,Company company);
    Optional<List<Reservation>> findReservationsByCompany(Company company);
    Optional<Reservation> findReservationByUser(User user);
    //TODO List of Reservations according to users' company

}
