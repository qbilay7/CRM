package com.golfsprint.GolfSprint.Company;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query("SELECT s FROM Company s WHERE s.companyId=?1")
    Optional<Company> findCompanyById(Long id);
    @Query("SELECT s FROM Company s WHERE s.companyEmail=?1")
    UserDetails findCompanyByEmailU(String email);
    @Query("SELECT s FROM Company s WHERE s.companyEmail=?1")
    Optional<Company> findCompanyByEmail(String email);
    @Query("SELECT s FROM Company s WHERE s.companyName=?1")
    Optional<Company> findCompanyByName(String companyName);
    @Query("SELECT s FROM Company s WHERE s.companyNumber=?1")
    Optional<Company> findCompanyByNumber(String companyNumber);
}
