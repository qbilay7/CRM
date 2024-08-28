/*package com.golfsprint.GolfSprint.Places;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Shop.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/places")
public class PlaceController {
    private final PlaceService placeService;
    @Autowired
    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }
    @GetMapping(path = "/getAll")
    @Secured("CLUB")
    public List<Place> getPlaces(@RequestBody Company company){
        return placeService.getPlacesByCompany(company);
    }
    @PostMapping(path = "/add/{companyId}")
    @Secured("CLUB")
    public ResponseEntity<String> addPlace(@RequestBody Place place, @PathVariable("companyId") Long companyId){
        placeService.addPlace(place, companyId);
        return ResponseEntity.ok("Successfully added.");
    }
    @DeleteMapping(path = "/delete/{placeId}")
    @Secured("CLUB")
    public ResponseEntity<String> deletePlace(@PathVariable("placeId") Long placeId){
        placeService.deletePlace(placeId);
        return ResponseEntity.ok("Successfully deleted.");
    }
    @PutMapping(path = "/update/{placeId}")
    @Secured("CLUB")
    public ResponseEntity<String> updatePlace(@PathVariable("placeId") Long placeId, @RequestParam(required = false) String placeName, @RequestParam(required = false) String description, @RequestParam(required = false) Byte[] placeImage){
        placeService.updatePlace(placeId,placeName,description,placeImage);
        return ResponseEntity.ok("Successfully updated.");
    }
}
*/