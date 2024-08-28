/*package com.golfsprint.GolfSprint.Delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    @Query("SELECT s from Delivery s WHERE s.deliveryId=?1")
    Optional<Delivery> findDeliveryById(Long deliveryId);
}
*/