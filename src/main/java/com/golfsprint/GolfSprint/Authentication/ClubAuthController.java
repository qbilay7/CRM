package com.golfsprint.GolfSprint.Authentication;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.JWT.JwtUtil;
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
@RequestMapping(path = "golfsprint/api/auth/CL")
public class ClubAuthController {

    private final CompanyRepository companyRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginPost(@RequestBody ClubAuthRequest clubAuthRequest, HttpServletRequest request) {
        // Validate user credentials (e.g., using userRepository)
        String email = clubAuthRequest.getCompanyEmail();
        String password = clubAuthRequest.getPassword();
        Optional<Company> company = companyRepository.findCompanyByEmail(email);
        if(company.isEmpty()){
            return ResponseEntity.status(403).body("There is no such account.");
        }
            if (validCredentials(company)) {
                if(!passwordCheck(company.get(),password)){
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
                final UserDetails companyU = companyRepository.findCompanyByEmailU(email);
                if (companyU != null) {
                    String token = jwtUtil.generateToken(companyU);
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

    private boolean validCredentials(Optional<Company> company) {
        return company.isPresent() && company.get().isEnabled() ;
    }
    private boolean passwordCheck(Company company, String password){
        return bCryptPasswordEncoder.matches(password, company.getPassword());
    }

}

