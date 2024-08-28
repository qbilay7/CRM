/*package com.golfsprint.GolfSprint.Delivery;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.Shop.Product;
import com.golfsprint.GolfSprint.Shop.ProductRepository;
import com.golfsprint.GolfSprint.User.User;
import com.golfsprint.GolfSprint.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public List<Delivery> getDeliveriesByCompany(Company company){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(company.getCompanyEmail());
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return deliveryRepository.findAll().stream()
                .filter(delivery -> delivery.getUser().getCompany() != null && delivery.getUser().getCompany().equals(companyOptional.get()))
                .collect(Collectors.toList());
    }
    public void addDelivery(Delivery delivery, Long userId, Long productId){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(()-> new IllegalStateException("Product not found"));
        delivery.setUser(user);
        delivery.setProduct(product);
        deliveryRepository.save(delivery);
    }
    public void deleteDelivery(Long deliveryId){
        boolean exists = deliveryRepository.existsById(deliveryId);
        if(!exists){
            throw new IllegalStateException("Delivery with id"+deliveryId+"does not exist");
        }
        deliveryRepository.deleteById(deliveryId);
    }
    @Transactional
    public void updateDelivery(Long deliveryId, String address, LocalDateTime time, Boolean delivered){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(()->new IllegalStateException("Delivery with id"+deliveryId+"does not exist"));
        if(address!=null && !address.isEmpty() && !Objects.equals(delivery.getAddress(),address)){
            delivery.setAddress(address);
        }
        delivery.setTime(time);
        if(!delivered){
            delivery.setDelivered(true);
        }
    }
}
*/