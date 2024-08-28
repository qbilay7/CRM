package com.golfsprint.GolfSprint.Registration;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.Token.ConfirmationToken;
import com.golfsprint.GolfSprint.Token.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path="golfsprint/api/club")
@AllArgsConstructor
public class ClubRegistrationController {
    private final ClubRegistrationService clubRegistrationService;
    private final CompanyRepository companyRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @PostMapping("/send/admin")
    public void sendToMe(@RequestParam("message") String email, @RequestParam("subject") String subject){
        clubRegistrationService.sendToMe(email,subject);
    }
    @PostMapping(path = "/register")
    public ResponseEntity<String> registerClub(@RequestBody ClubRegistrationRequest registrationRequest){
       return clubRegistrationService.register(registrationRequest);
    }
    @GetMapping(path= "/confirm")
    public ResponseEntity<String> confirmClub(@RequestParam("token") String token){
         Optional<ConfirmationToken> confToken = confirmationTokenRepository.findConfirmationTokenByToken(token);
         if(confToken.get().getCompany() == null){
             return ResponseEntity.status(403).body("You are not permitted.");
         }
        if (confToken.get().getConfirmedAt() != null) {
            return ResponseEntity.status(403).body("Email already confirmed");
        }
        LocalDateTime expiredAt = confToken.get().getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(403).body("Token expired");
        }
         if(confToken.isPresent()) {
             confToken.get().setConfirmedAt(LocalDateTime.now());
             confirmationTokenRepository.save(confToken.get());
             Company company = confToken.get().getCompany();
             company.setEnabled(true);
             companyRepository.save(company);
             return ResponseEntity.ok("Confirmed");
         }
         else{
             return ResponseEntity.status(404).body("There is no such token.");
         }
    }

}
