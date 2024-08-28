package com.golfsprint.GolfSprint.Authentication;


import com.golfsprint.GolfSprint.JWT.JwtUtil;
import com.golfsprint.GolfSprint.User.User;
import com.golfsprint.GolfSprint.User.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "golfsprint/api/auth/US")
public class UserAuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginPost(@RequestBody UserAuthRequest userAuthRequest, HttpServletRequest request) {
        String email = userAuthRequest.getEmail();
        String password = userAuthRequest.getPassword();
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty()){
            return ResponseEntity.status(403).body("There is no such account.");
        }
        if (validCredentials(user)) {
            if(!passwordCheck(user.get(),password)){
                return ResponseEntity.status(403).body("Wrong password.");
            }
            if(jwtUtil.extractTokenFromRequest(request) != null){
                try {
                    //HttpSession session = request.getSession();
                    //session.setAttribute("Email", email);
                    request.login(email, password);
                }catch (ServletException e){
                    System.out.println(e.getMessage());
                }
                return ResponseEntity.ok(jwtUtil.extractTokenFromRequest(request));
            }
            // Create a session and store user data
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final UserDetails userU = userRepository.findUserByEmailU(email);
            if (userU != null) {
                String token = jwtUtil.generateToken(userU);
                try {
                    //HttpSession session = request.getSession();
                    //session.setAttribute("Email", email);
                    request.login(email, password);
                }catch (ServletException e){
                    System.out.println(e.getMessage());
                }
                return ResponseEntity.ok(token);
            }

            else return ResponseEntity.status(404).body("Some error has occurred");
        } else {
            return ResponseEntity.status(401).body("Please confirm your email.");
        }

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            SecurityContextHolder.clearContext();
            //HttpSession session = request.getSession();
            //session.invalidate();
            request.logout();
        } catch(ServletException e){
            System.out.println( e.getMessage());
        }
        return "redirect:/login";
    }

    private boolean validCredentials(Optional<User> user) {
        return user.isPresent() && user.get().isEnabled();
    }
    private boolean passwordCheck(User user, String password){
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}
