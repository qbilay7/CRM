package com.golfsprint.GolfSprint.Token;

import com.golfsprint.GolfSprint.Company.Company;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void save(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    @Transactional
    public void deleteTokenByCompany(Company company){
        ConfirmationToken confirmationToken = confirmationTokenRepository.findConfirmationTokenByCompany(company).orElseThrow(() -> new IllegalStateException("error token"));
        confirmationTokenRepository.delete(confirmationToken);
    }
}
