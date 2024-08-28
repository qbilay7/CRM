package com.golfsprint.GolfSprint.Company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.golfsprint.GolfSprint.Roles.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company implements UserDetails {
    @Id
    @SequenceGenerator(
            name="company_sequence",
            sequenceName = "company_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_sequence"
    )
    @Column(name = "company_id")
    private Long companyId;
    private String companyName;
    private String companyEmail;
    private String companyNumber;
    private String password;
    private String companyLocation;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean locked = false;
    private Boolean enabled = false;
    @Lob
    private String companyImage = null;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startBoundary = null;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime finishBoundary = null;

    public Company(String companyName, String companyEmail, String companyNumber,String companyLocation ,String password) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyNumber = companyNumber;
        this.companyLocation = companyLocation;
        this.password = password;
        this.role = UserRole.CLUB;
    }
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return companyEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
