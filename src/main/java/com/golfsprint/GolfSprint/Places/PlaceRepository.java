/*package com.golfsprint.GolfSprint.Places;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    @Query("SELECT s from Place s WHERE s.placeId=?1")
    Optional<Place> findPlaceByPlaceId(Long placeId);
}
*/