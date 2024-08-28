/*package com.golfsprint.GolfSprint.Shop;

import com.golfsprint.GolfSprint.Company.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT s FROM Product s WHERE s.productId=?1")
    Optional<Product> findProductByProductId(Long productId);
    Optional<List<Product>> findProductsByCompany(Company company);
}
*/