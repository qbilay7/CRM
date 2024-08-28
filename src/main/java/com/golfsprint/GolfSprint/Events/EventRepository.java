/*package com.golfsprint.GolfSprint.Events;

import com.golfsprint.GolfSprint.Company.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event,Long> {
    @Query("SELECT s from Event s WHERE s.eventId=?1")
    Optional<Event> findEventByEventId(Long eventId);
    Optional<List<Event>> findEventsByCompany(Company company);
}
*/