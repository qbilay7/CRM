package com.golfsprint.GolfSprint.Token;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    @Query("SELECT s FROM ConfirmationToken s WHERE s.token=?1")
    Optional<ConfirmationToken> findConfirmationTokenByToken(String token);
    Optional<ConfirmationToken> findConfirmationTokenByCompany(Company company);
    //Optional<ConfirmationToken> findConfirmationTokenByUser(User user);
}
