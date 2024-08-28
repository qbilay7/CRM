package com.golfsprint.GolfSprint.User;


import com.golfsprint.GolfSprint.Email.EmailMessage;
import com.golfsprint.GolfSprint.Registration.Passwords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(path = "/getAll")
    public List<User> getUsers(@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return userService.getUsersByCompany(companyEmail);
    }

    @GetMapping(path = "/get/{userId}")
    public User getUser(@PathVariable("userId") Long userId, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
       return userService.getUser(userId,companyEmail);
    }

    @GetMapping(path = "/getBy")
    public User getUserByEmail(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
       return userService.getUserByEmail(userEmail);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addUser(@RequestBody User user, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
       return userService.addUser(user,companyEmail);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        userService.deleteUser(userId,companyEmail);
        return ResponseEntity.ok("Successfully deleted.");
    }
    @DeleteMapping(path = "/deleteByUser/{userId}")
    public ResponseEntity<String> deleteByUser(@PathVariable("userId") Long userId, @AuthenticationPrincipal UserDetails userDetails, @RequestParam("password") String password){
        String userEmail = userDetails.getUsername();
        String password1 = userDetails.getPassword();
        return userService.deleteByUser(userId,userEmail,password1,password);
    }

    @PutMapping(path = "/update/{userId}")
    public ResponseEntity<String> updateUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("userId") Long userId,@RequestParam(required = false) String name,@RequestParam(required = false) String surname,@RequestParam(required = false) String email,@RequestParam(required = false) String number,@RequestParam(required = false) String password,@RequestParam(required = false) Boolean locked,@RequestParam(required = false) Boolean enabled){
        String companyEmail = userDetails.getUsername();
        return userService.updateUser(companyEmail,userId,name,surname,email,number,password);
    }

    @PostMapping(path = "/send/email/all")
    public void sendEmailUsers(@AuthenticationPrincipal UserDetails userDetails, @RequestBody EmailMessage emailMessage){
        String companyEmail = userDetails.getUsername();
        List<User> users = userService.getUsersByCompany(companyEmail);
        for(User user : users){
            userService.sendEmailUsers(companyEmail,user.getEmail(),emailMessage.getSubject(),emailMessage.getMessage());
        }
    }
    @PostMapping(path = "/send/email/to")
    public void sendEmailTo(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("userEmail") String email,@RequestBody EmailMessage emailMessage ){
        String companyEmail = userDetails.getUsername();
       userService.sendEmailUsers(companyEmail,email,emailMessage.getSubject(),emailMessage.getMessage());
    }
    /*
    @PostMapping(path = "/send/SMS/{userNumber}")
    public void sendSmsTo(@PathVariable("userNumber") String toPhoneNumber,@RequestParam String fromPhoneNumber,@RequestBody String messageBody){
        userService.sendSMSUsers(toPhoneNumber,fromPhoneNumber,messageBody);
    }

    @PostMapping(path = "/send/SMS")
    public void sendSmsUsers(@RequestParam String toPhoneNumber,@RequestParam String fromPhoneNumber,@RequestBody String messageBody,@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        List<User> users = userService.getUsersByCompany(companyEmail);
        for(User user : users){
            userService.sendSMSUsers(user.getNumber(),fromPhoneNumber,messageBody);
        }
    } */
    /*
    @GetMapping(path = "/search/users")
    public List<User> search(@AuthenticationPrincipal UserDetails userDetails,@RequestParam String searchTerm){
        String companyEmail = userDetails.getUsername();
        return userService.searchUsersStream(companyEmail,searchTerm);
    } */
    @PutMapping(path = "/edit")
    public ResponseEntity<String> edit(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User user){
        String email = userDetails.getUsername();
        return userService.edit(email, user.getName(), user.getSurname(), user.getNumber());
    }
    @PutMapping(path = "/change/password")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Passwords passwords){
        String email = userDetails.getUsername();
        return userService.changePassword(email, passwords.getOldPassword(), passwords.getPassword(), passwords.getValidPassword());
    }
}
