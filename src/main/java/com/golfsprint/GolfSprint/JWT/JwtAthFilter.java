package com.golfsprint.GolfSprint.JWT;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.Roles.UserRole;
import com.golfsprint.GolfSprint.User.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String jwtToken;

        if(authHeader==null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken=authHeader.substring(7);
        userEmail = jwtUtil.extractUsername(jwtToken);
        //System.out.println(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                boolean existsC = companyRepository.findCompanyByEmail(userEmail).isPresent();
                boolean existsU = userRepository.findUserByEmail(userEmail).isPresent();
                if (existsC) {
                    UserDetails company = companyRepository.findCompanyByEmailU(userEmail);
                    if (jwtUtil.validateToken(jwtToken, company)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(company, null, company.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } else if (existsU) {
                    UserDetails user = userRepository.findUserByEmailU(userEmail);
                    if (jwtUtil.validateToken(jwtToken, user)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }catch (DataAccessException e){
               System.out.println(e.getMessage());
            }
        }

        filterChain.doFilter(request,response);
    }
}
