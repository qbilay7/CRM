/*package com.golfsprint.GolfSprint.Delivery;

import com.golfsprint.GolfSprint.Company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    @Autowired
    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }
    @GetMapping(path = "/getAll")
    @Secured("CLUB")
    public List<Delivery> getDeliveries(@RequestBody Company company){
        return deliveryService.getDeliveriesByCompany(company);
    }
    @PostMapping(path = "/add/users/{userId}/{productId}")
    @Secured({"CLUB","USER"})
    public ResponseEntity<String> addDelivery(@RequestBody Delivery delivery, @PathVariable("userId") Long userId,@PathVariable("productId") Long productId){
        deliveryService.addDelivery(delivery, userId, productId);
        return ResponseEntity.ok("Successfully added.");
    }
    @DeleteMapping(path = "/delete/{deliveryId}")
    @Secured("CLUB")
    public ResponseEntity<String> deleteDelivery(@PathVariable("deliveryId") Long deliveryId){
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.ok("Successfully deleted.");
    }
    @PutMapping(path = "/update/{deliveryId}")
    @Secured({"CLUB"})
    public ResponseEntity<String> updateDelivery(@PathVariable("deliveryId") Long deliveryId, @RequestParam(required = false) String address, @RequestParam(required = false) LocalDateTime time, @RequestParam(required = false) Boolean delivered){
        deliveryService.updateDelivery(deliveryId, address, time, delivered);
        return ResponseEntity.ok("Successfully updated.");
    }
}
*/