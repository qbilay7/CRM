package com.golfsprint.GolfSprint.Registration;



import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="golfsprint/api/user")
@AllArgsConstructor
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;
    //private final ConfirmationTokenRepository confirmationTokenRepository;
    //private final UserRepository userRepository;
    /*
    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest registrationRequest){
       return userRegistrationService.register(registrationRequest);

    }
    @PutMapping(path= "/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token){
        Optional<ConfirmationToken> confToken = confirmationTokenRepository.findConfirmationTokenByToken(token);
        if(confToken.get().getUser().equals(null)){
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
            User user = confToken.get().getUser();
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Confirmed");
        }
        else{
            return ResponseEntity.status(404).body("There is no such token.");
        }
    } */

}
