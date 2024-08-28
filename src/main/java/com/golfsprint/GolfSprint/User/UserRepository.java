package com.golfsprint.GolfSprint.User;

import com.golfsprint.GolfSprint.Company.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT s FROM User s WHERE s.email=?1")
    Optional<User> findUserByEmail(String email);
    @Query("SELECT s FROM User s WHERE s.name=?1")
    Optional<User> findUserByName(String name);
    @Query("SELECT s FROM User s WHERE s.number=?1")
    Optional<User> findUserByNumber(String number);
    @Query("SELECT s FROM User s WHERE s.surname=?1")
    Optional<User> findUserBySurname(String surname);
    @Query("SELECT u FROM User u WHERE u.company=?1")
    List<User> findByCompany(Company company);
    @Query("SELECT s FROM User s WHERE s.email=?1")
    UserDetails findUserByEmailU(String email);
    @Query("SELECT s FROM User s WHERE s.userId=?1")
    Optional<User> findUserById(Long userId);
    @Query(value = "SELECT * FROM user_table " +
            "WHERE name LIKE %?1% " +
            "   OR surname LIKE %?1% " +
            "   OR email LIKE %?1% " +
            "   OR number LIKE %?1%",
            nativeQuery = true)
    Optional<List<User>> findUsersByNameOrSurnameOrEmailOrNumber(String key);
    Optional<List<User>> findUsersByCompany(Company company);
}
