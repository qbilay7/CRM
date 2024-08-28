/*package com.golfsprint.GolfSprint.Places;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final CompanyRepository companyRepository;
    public List<Place> getPlacesByCompany(Company company){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(company.getCompanyEmail());
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return placeRepository.findAll().stream()
                .filter(place -> place.getCompany() != null && place.getCompany().equals(companyOptional.get()))
                .collect(Collectors.toList());
    }
    public void addPlace(Place place, Long id){
        Company company = companyRepository.findById(id).orElseThrow(()-> new IllegalStateException("Company not found"));
        place.setCompany(company);
        placeRepository.save(place);
    }
    public void deletePlace(Long placeId){
        boolean exists = placeRepository.existsById(placeId);
        if(!exists){
            throw new IllegalStateException("Place with id"+placeId+"does not exist");
        }
        placeRepository.deleteById(placeId);
    }
    @Transactional
    public void updatePlace(Long placeId, String placeName, String description, Byte[] placeImage){
        Place place = placeRepository.findById(placeId).orElseThrow(()->new IllegalStateException("Place with id"+placeId+"does not exist"));
        place.setPlaceImage(placeImage);
        if(placeName!=null && !placeName.isEmpty() && !Objects.equals(place.getPlaceName(),placeName)){
            place.setPlaceName(placeName);
        }
        place.setDescription(description);

    }
}
*/