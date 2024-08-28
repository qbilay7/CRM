package com.golfsprint.GolfSprint.User;


import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.CourseRequest.CourseRequest;
import com.golfsprint.GolfSprint.CourseRequest.CourseRequestRepository;
import com.golfsprint.GolfSprint.Email.EmailService;
import com.golfsprint.GolfSprint.Registration.EmailValidator;
import com.golfsprint.GolfSprint.Reservation.Reservation;
import com.golfsprint.GolfSprint.Reservation.ReservationRepository;
import com.golfsprint.GolfSprint.SMS.SmsService;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final SmsService smsService;
    private final EmailValidator emailValidator;
    private final ReservationRepository reservationRepository;
    private final CourseRequestRepository courseRequestRepository;

    public ResponseEntity<String> addUser(User user, String companyEmail){

        if(user.getName().isEmpty() || user.getSurname().isEmpty() || user.getNumber().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            return ResponseEntity.status(403).body("Don't submit empty values.");
        }
        if(!emailValidator.test(user.getEmail())){
            return ResponseEntity.status(403).body("Please enter a valid email.");
        }
        if(userRepository.findUserByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.status(403).body("There is already an account with that email.");
        }
        if(companyRepository.findCompanyByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.status(403).body("There is already an account with that email.");
        }
        if(!emailValidator.isValidPhoneNumber(user.getNumber())){
            return ResponseEntity.status(403).body("Please enter the number in valid format.");
        }
        if(user.getName().length() > 35 || user.getSurname().length() > 35){
            return ResponseEntity.status(403).body("Name or surname is too long.");
        }
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()-> new IllegalStateException("Company not found"));
        user.setCompany(company);
        user.setEnabled(true);
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        userRepository.save(user);
        return ResponseEntity.ok("Success");
    }

    public void deleteUser(Long id, String companyEmail){
        Optional<User> user = userRepository.findById(id);
        Optional<Company> company = companyRepository.findCompanyByEmail(companyEmail);
        if(user.isEmpty() || company.isEmpty() || !user.get().getCompany().equals(company.get())){
            throw new IllegalStateException("Error");
        }
        else {
            Optional<Reservation> reservation = reservationRepository.findReservationByUser(id);
            reservation.ifPresent(value -> reservationRepository.deleteById(value.getReservationId()));
            Optional<List<CourseRequest>> requests = courseRequestRepository.findCourseRequestsByUser(user.get());
            requests.ifPresent(courseRequestRepository::deleteAll);
            //Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findConfirmationTokenByUser(user.get());
            //confirmationToken.ifPresent(confirmationTokenRepository::delete);
            userRepository.deleteById(id);
        }
    }
    public ResponseEntity<String> deleteByUser(Long id, String userEmail, String password1, String password){
        User user = userRepository.findById(id).orElseThrow(()->new IllegalStateException("No such user."));
        User user1 = userRepository.findUserByEmail(userEmail).orElseThrow(()->new IllegalStateException("No such user."));
        if(!user.equals(user1)){
            throw new IllegalStateException("Error");
        }
        if(bCryptPasswordEncoder.matches(password,password1)){
            Optional<Reservation> reservation = reservationRepository.findReservationByUser(id);
            reservation.ifPresent(value -> reservationRepository.deleteById(value.getReservationId()));
           // Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findConfirmationTokenByUser(user.get());
           // confirmationToken.ifPresent(confirmationTokenRepository::delete);
            Optional<List<CourseRequest>> requests = courseRequestRepository.findCourseRequestsByUser(user);
            requests.ifPresent(courseRequestRepository::deleteAll);

            userRepository.deleteById(id);
            return ResponseEntity.ok("Success");
        }
        else return ResponseEntity.status(403).body("Wrong password");
    }
    @Transactional
    public ResponseEntity<String> updateUser(String companyEmail,Long id, String name,String surname, String email, String number, String password){
        if(!emailValidator.test(email)){
            return ResponseEntity.status(403).body("Please enter a valid email.");
        }
        if(!emailValidator.isValidPhoneNumber(number)){
            return ResponseEntity.status(403).body("Please enter the number in valid format.");
        }

        Company company1 = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()->new IllegalStateException("Company with email"+companyEmail+"does not exist"));
        User user = userRepository.findById(id).orElseThrow(()->new IllegalStateException("User with id"+id+"does not exist"));
        if(user.getCompany().equals(company1)) {
            if (name != null && !name.isEmpty() && !Objects.equals(user.getName(), name)) {
                if(name.length() > 35){
                    return ResponseEntity.status(403).body("Name is too long.");
                }
                user.setName(name);
            }
            if (surname != null && !surname.isEmpty() && !Objects.equals(user.getSurname(), surname)) {
                if(surname.length() > 35){
                    return ResponseEntity.status(403).body("Surname is too long.");
                }
                user.setSurname(surname);
            }
            if (number != null && !Objects.equals(user.getNumber(), number)) {
                user.setNumber(number);
            }
            if (email != null && !email.isEmpty() && !Objects.equals(user.getEmail(), email)) {
                Optional<User> userOptional = userRepository.findUserByEmail(email);
                if (userOptional.isPresent()) {
                    throw new IllegalStateException("Email already taken");
                }
                user.setEmail(email);
            }
            if (password != null) {
                user.setPassword(password);
            }
            userRepository.save(user);
            return ResponseEntity.ok("Success.");
        }
        else return ResponseEntity.status(403).body("An error occured.");
    }
    public User getUser(Long id, String companyEmail){
        Optional<User> user = userRepository.findById(id);
        Optional<Company> company = companyRepository.findCompanyByEmail(companyEmail);
        if(user.isPresent() && company.isPresent() && user.get().getCompany().equals(company.get())){
            return user.get();
        }
        else{
            throw new IllegalStateException("Error");
        }
    }
    public User getUserByEmail(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        else throw new IllegalStateException("Email does not exist.");
    }
    /*
    public String signUpUser(User user){
        boolean exists = userRepository.findUserByEmail(user.getEmail()).isPresent();
        boolean exist = companyRepository.findCompanyByEmail(user.getEmail()).isPresent();
        if(exists || exist){
            throw new IllegalStateException("Email already exists.");
        }
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        userRepository.save(user);
        confirmationTokenService.save(confirmationToken);
        return token;
    } */
    public List<User> getUsersByCompany(String companyEmail){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return userRepository.findAll().stream()
                .filter(user -> user.getCompany() != null && user.getCompany().equals(companyOptional.get()))
                .collect(Collectors.toList());
    }
    public void sendEmailUsers(String from, String to, @Nullable String subject,String message){
        User user = userRepository.findUserByEmail(to).orElseThrow(()-> new IllegalStateException("no user found."));
        if(user.getCompany().getCompanyEmail().equals(from)) {
            emailService.sendUsers(from, to, message, subject);
        }
    }
    public void sendSMSUsers(String toPhoneNumber, String fromPhoneNumber, String messageBody){
        smsService.sendSms(toPhoneNumber,fromPhoneNumber,messageBody);
    }
    /*
    public List<User> searchUsers(String key){
        return userRepository.findUsersByNameOrSurnameOrEmailOrNumber(key)
                .orElseThrow(() -> new IllegalStateException("No users found for the search term: "+key));
    } */
    /*
    public List<User> searchUsersStream(String companyEmail, String searchTerm) {
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> user.getCompany() != null && user.getCompany().equals(companyOptional.get()))
                .filter(user -> user.getName().toLowerCase().contains(searchTerm.toLowerCase())
                        || user.getEmail().toLowerCase().contains(searchTerm.toLowerCase())
                        || user.getSurname().toLowerCase().contains(searchTerm.toLowerCase())
                        || user.getNumber().toLowerCase().contains(searchTerm.toLowerCase())
                        || user.getUserId().toString().contains(searchTerm))
                .collect(Collectors.toList());
    } */
    @Transactional
    public void arrangeUsers(Company company){
        Optional<List<User>> users = userRepository.findUsersByCompany(company);
        users.ifPresent(userRepository::deleteAll);
    }
    @Transactional
    public ResponseEntity<String> edit(String email,String name, String surname, String number){
       User user = userRepository.findUserByEmail(email).orElseThrow(()->new IllegalStateException("No such user exists"));

        if (!name.isEmpty() && !Objects.equals(user.getName(), name)) {
            if(name.length() > 35){
                return ResponseEntity.status(403).body("Name is too long.");
            }
            user.setName(name);
        }
        if (!surname.isEmpty() && !Objects.equals(user.getSurname(), surname)) {
            if(surname.length() > 35){
                return ResponseEntity.status(403).body("Surname is too long.");
            }
            user.setSurname(surname);
        }
        if (!number.isEmpty() && !Objects.equals(user.getNumber(), number)) {
            if(!emailValidator.isValidPhoneNumber(number)){
                return ResponseEntity.status(403).body("Please enter the number in valid format.");
            }
            else user.setNumber(number);
        }
        userRepository.save(user);
        return ResponseEntity.ok("Success");
    }
    public ResponseEntity<String> changePassword(String email,String oldPassword, String password, String validPassword){
        User user = userRepository.findUserByEmail(email).orElseThrow(()->new IllegalStateException("No user found"));
        if(oldPassword.isEmpty() || password.isEmpty() || validPassword.isEmpty()){
            return ResponseEntity.status(403).body("Don't submit empty values.");
        }
        if(!passwordCheck(user,oldPassword)){
            return ResponseEntity.status(403).body("Please enter your current password correct.");
        }
        if(password.equals(validPassword)){
            String encoded = bCryptPasswordEncoder.encode(password);
            user.setPassword(encoded);
            userRepository.save(user);
            return ResponseEntity.ok("Success");
        }
        else return ResponseEntity.status(403).body("Please enter the same new password to validate.");
    }
    private boolean passwordCheck(User user, String password){
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
}
