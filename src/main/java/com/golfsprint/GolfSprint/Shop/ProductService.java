/*package com.golfsprint.GolfSprint.Shop;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    public List<Product> getProductsByCompany(String companyEmail){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return productRepository.findAll().stream()
                .filter(product -> product.getCompany() != null && product.getCompany().equals(companyOptional.get()))
                .collect(Collectors.toList());
    }
    public List<Product> getProductsByCompanyId(Long companyId){
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return productRepository.findAll().stream()
                .filter(product -> product.getCompany() != null && product.getCompany().equals(companyOptional.get()))
                .collect(Collectors.toList());
    }
    public ResponseEntity<String> addProduct(Product product, String companyEmail){
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()-> new IllegalStateException("Company not found"));
        product.setCompany(company);
        if(product.getPrice() < 0){
            return ResponseEntity.status(403).body("Price can't be lower than 0");
        }
        productRepository.save(product);
        return ResponseEntity.ok("Success");
    }
    public void deleteProduct(Long id){
        boolean exists = productRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Product with id"+id+"does not exist");
        }
        productRepository.deleteById(id);
    }
    @Transactional
    public ResponseEntity<String> updateProduct(String companyEmail, Long id, String productName, String description, Integer price, String currency, String image, String category) {
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(() -> new IllegalStateException("Company with email" + companyEmail + "does not exist"));
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product with id" + id + "does not exist"));
        if (product.getCompany().equals(company)) {
            if (productName != null && !productName.isEmpty() && !Objects.equals(product.getProductName(), productName)) {
                product.setProductName(productName);
            }
            product.setDescription(description);

            if (price != null && !Objects.equals(product.getPrice(), price)) {
                if(price < 0){
                    return ResponseEntity.status(403).body("Price can't be lower than 0");
                }
                product.setPrice(price);
            }
            if (currency != null && !currency.isEmpty() && !Objects.equals(product.getCurrency(), currency)) {
                product.setCurrency(currency);
            }
            product.setImage(image);

            product.setCategory(category);
            productRepository.save(product);
        }
        return ResponseEntity.ok("Success");
    }
    public Product getProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            return product.get();
        }
        else throw new IllegalStateException("There is no such product");
    }
    @Transactional
    public void deleteAllProductsByCompany(Company company){
        List<Product> products = productRepository.findProductsByCompany(company).orElseThrow(() -> new IllegalStateException("Error product"));
        productRepository.deleteAll(products);
    }
}
*/