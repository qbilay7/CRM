package com.golfsprint.GolfSprint.Security;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
//import com.golfsprint.GolfSprint.JWT.JwtAthFilter;
import com.golfsprint.GolfSprint.JWT.JwtAthFilter;
import com.golfsprint.GolfSprint.User.User;
import com.golfsprint.GolfSprint.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CompanyRepository companyRepository;
    private final JwtAthFilter jwtAthFilter;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("golfsprint/api/club/**","golfsprint/api/user/**","golfsprint/api/auth/CL/**","golfsprint/api/auth/US/**").permitAll() //hasAnyRole("ADMIN","USER")// Permit access to auth paths
                                .anyRequest().authenticated() // Require authentication for other paths
                )
               /* .formLogin(form -> form.loginPage("/login").permitAll())
                .logout((logout) -> logout
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES)))
                ) */
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //ALWAYS
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(AbstractHttpConfigurer::disable)
                .cors(cors -> corsFilter());
        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Optional<Company> company = companyRepository.findCompanyByEmail(email);
                Optional<User> user = userRepository.findUserByEmail(email);

                if (company.isPresent()) {
                    // Create and return a UserDetails object for a Company
                    return org.springframework.security.core.userdetails.User
                            .withUsername(email)
                            .password(company.get().getPassword()) // Replace with your company's password property
                            .authorities("CLUB") // Add any roles or authorities for companies
                            .build();
                } else if (user.isPresent()) {
                    // Create and return a UserDetails object for a User
                    return org.springframework.security.core.userdetails.User
                            .withUsername(email)
                            .password(user.get().getPassword()) // Replace with your user's password property
                            .authorities("USER") // Add any roles or authorities for users
                            .build();
                } else {
                    throw new UsernameNotFoundException("User not found with email: " + email);
                }
            }
        };
    }

   /*
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Optional<Company> company = companyRepository.findCompanyByEmail(email);
                Optional<User> user = userRepository.findUserByEmail(email);
                if(company.isPresent()){
                    return company.get();
                }
                else if(user.isPresent()){
                    return user.get();
                }
                else return null;
            }
        } ;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(COMPANY_NOT_FOUND,email)));
            }
        } ;
    } */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Allow requests from this origin
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET","PUT","POST","DELETE","OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

}
