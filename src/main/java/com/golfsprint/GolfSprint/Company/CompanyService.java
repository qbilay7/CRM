package com.golfsprint.GolfSprint.Company;


import com.golfsprint.GolfSprint.Course.CourseService;

import com.golfsprint.GolfSprint.CourseRequest.CourseRequestService;
import com.golfsprint.GolfSprint.Registration.EmailValidator;
import com.golfsprint.GolfSprint.Reservation.ReservationService;
import com.golfsprint.GolfSprint.Token.ConfirmationToken;
import com.golfsprint.GolfSprint.Token.ConfirmationTokenService;
import com.golfsprint.GolfSprint.User.User;
import com.golfsprint.GolfSprint.User.UserRepository;
import com.golfsprint.GolfSprint.User.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CourseService courseService;
    private final CourseRequestService courseRequestService;
    //private final EventService eventService;
    private final ReservationService reservationService;
    //private final ProductService productService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailValidator emailValidator;
    public List<Company> getCompanies(String userEmail){
        if(userRepository.findUserByEmail(userEmail).isEmpty()){
            throw new IllegalStateException("You are not authorized.");
        }
        return companyRepository.findAll();
    }
    @Transactional
    public ResponseEntity<String> deleteCompany(Long id, String companyEmail, String password1, String password) {
          Company company = companyRepository.findCompanyById(id).orElseThrow(() -> new IllegalStateException("No such club exist."));
          Company company1 = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(() -> new IllegalStateException("No such club exist."));
          if(!company.equals(company1)){
              throw new IllegalStateException("You are not authorized");
          }
          if(bCryptPasswordEncoder.matches(password,password1)) {
              courseRequestService.deleteAll(company);
              courseService.deleteAllCoursesByCompany(company);
              //eventService.deleteAllEventsByCompany(company);
              //productService.deleteAllProductsByCompany(company);
              reservationService.deleteAllReservationByCompany(company);
              userService.arrangeUsers(company);
              confirmationTokenService.deleteTokenByCompany(company);
              companyRepository.delete(company);
              return ResponseEntity.ok("Success");
          }
          else return ResponseEntity.status(403).body("Wrong password");
    }
    @Transactional
    public ResponseEntity<String> updateCompany(String companyEmail,Long companyId, String companyName, String companyNumber, String companyLocation, String companyImage){
        Company company = companyRepository.findById(companyId).orElseThrow(()->new IllegalStateException("Company with id "+companyId+" does not exist"));
        Company company1 = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()->new IllegalStateException("Error"));
        if(!company.equals(company1)){
            throw new IllegalStateException("Not authorized");
        }
        if(companyName!=null && !companyName.isEmpty() && !Objects.equals(company.getCompanyName(),companyName)){
            if(companyName.length() > 35){
                return ResponseEntity.status(403).body("Name is too long.");
            }
            company.setCompanyName(companyName);
        }
        if(companyLocation!=null && !companyLocation.isEmpty() && !Objects.equals(company.getCompanyLocation(),companyLocation)){
            if(companyLocation.length() > 35){
                return ResponseEntity.status(403).body("Location is too long.");
            }
            company.setCompanyLocation(companyLocation);
        }
        if(companyNumber!=null && !Objects.equals(company.getCompanyNumber(),companyNumber)){
            if(emailValidator.isValidPhoneNumber(companyNumber)) {
                company.setCompanyNumber(companyNumber);
            }
            else return ResponseEntity.status(403).body("Please enter the number in valid format.");
        }
        if(companyImage!=null && !Objects.equals(company.getCompanyImage(),companyImage)) {
            company.setCompanyImage(companyImage);
        }
        companyRepository.save(company);
        return ResponseEntity.ok("Success");
    }

    public Company getCompany(String companyEmail){
        Optional<Company> company = companyRepository.findCompanyByEmail(companyEmail);
        if(company.isPresent()){
            return company.get();
        }
        else{
            throw new IllegalStateException("There is no such company");
        }
    }
    public Company getCompanyByUser(String userEmail){
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(()->new IllegalStateException("No such user."));
        Company company = user.getCompany();
        if(company != null){
            return company;
        }
        else{
            throw new IllegalStateException("There is no such company");
        }
    }
    public String signUpCompany(Company company){
      boolean exists = companyRepository.findCompanyByEmail(company.getCompanyEmail()).isPresent();
      boolean exist = userRepository.findUserByEmail(company.getCompanyEmail()).isPresent();
      if(exists || exist){
          throw new IllegalStateException("Email already exists.");
      }
      String encoded = bCryptPasswordEncoder.encode(company.getPassword());
      company.setPassword(encoded);

      String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                company
        );
        companyRepository.save(company);
        confirmationTokenService.save(confirmationToken);
        return token;
    }
    public ResponseEntity<String> changePassword(String companyEmail,String oldPassword, String password, String validPassword){
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()->new IllegalStateException("No club found."));
        if(oldPassword.isEmpty() || password.isEmpty() || validPassword.isEmpty()){
            return ResponseEntity.status(403).body("Don't submit empty values.");
        }
        if(!passwordCheck(company,oldPassword)){
            return ResponseEntity.status(403).body("Please enter your current password correct.");
        }
        if(password.equals(validPassword)){
            String encoded = bCryptPasswordEncoder.encode(password);
            company.setPassword(encoded);
            companyRepository.save(company);
            return ResponseEntity.ok("Success");
        }
        else return ResponseEntity.status(403).body("New password doesn't match with validation password. Please enter the same new password to both.");
    }
    private boolean passwordCheck(Company company, String password){
        return bCryptPasswordEncoder.matches(password, company.getPassword());
    }
    public ResponseEntity<String> setBoundries(String companyEmail,LocalTime startBoundary, LocalTime finishBoundary){
        if(startBoundary == null || finishBoundary == null){
            return ResponseEntity.status(403).body("Please do not enter empty values.");
        }
        if(finishBoundary.isBefore(startBoundary)){
            return ResponseEntity.status(403).body("Finish boundary time can not be before start boundary time.");
        }
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()->new IllegalStateException("No club found."));
        company.setStartBoundary(startBoundary);
        company.setFinishBoundary(finishBoundary);
        companyRepository.save(company);
        return ResponseEntity.ok("Success");
    }
    public ResponseEntity<String> resetBoundry(String companyEmail){
        try {
            Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(() -> new IllegalStateException("No club found."));
            if(company.getStartBoundary() == null && company.getFinishBoundary() == null){
                return ResponseEntity.status(403).body("There is no configuration to reset.");
            }
            company.setStartBoundary(null);
            company.setFinishBoundary(null);
            companyRepository.save(company);
            return ResponseEntity.ok("Success");
        }catch (IllegalStateException e){
            return ResponseEntity.status(403).body("An error occured during reset operation.");
        }
    }
}
