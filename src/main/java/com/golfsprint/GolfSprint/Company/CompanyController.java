package com.golfsprint.GolfSprint.Company;

import com.golfsprint.GolfSprint.Registration.Passwords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("golfsprint/api/club_profile")
public class CompanyController {
    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }
    @GetMapping("/get")
    public Company getCompany(@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return companyService.getCompany(companyEmail);
    }
    @GetMapping("/getByUser")
    public Company getCompanyByUser(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
        return companyService.getCompanyByUser(userEmail);
    }
    @PutMapping("/update/{companyId}")
    public ResponseEntity<String> updateCompany(@PathVariable("companyId") Long companyId, @RequestBody Company company,@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return companyService.updateCompany(companyEmail,companyId,company.getCompanyName(),company.getCompanyNumber(),company.getCompanyLocation(),company.getCompanyImage());
    }
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable("companyId") Long companyId, @AuthenticationPrincipal UserDetails userDetails, @RequestParam("password") String password) {
        String companyEmail = userDetails.getUsername();
        String password1 = userDetails.getPassword();
        return companyService.deleteCompany(companyId, companyEmail,password1,password);
    }
    @GetMapping("/user/getAll")
    public List<Company> getAll(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
        return companyService.getCompanies(userEmail);
    }
    @PutMapping("/set/boundary")
    public ResponseEntity<String> setBoundry(@AuthenticationPrincipal UserDetails userDetails, @RequestBody BoundaryRequest boundaryRequest){
        String companyEmail = userDetails.getUsername();
       return companyService.setBoundries(companyEmail,boundaryRequest.getStartBoundary(), boundaryRequest.getFinishBoundary());
    }
    @PutMapping("/reset/boundary")
    public ResponseEntity<String> resetBoundry(@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return companyService.resetBoundry(companyEmail);
    }
    @PutMapping(path = "/change/password")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Passwords passwords){
        String companyEmail = userDetails.getUsername();
        return companyService.changePassword(companyEmail,passwords.getOldPassword(),passwords.getPassword(),passwords.getValidPassword());
    }
}
