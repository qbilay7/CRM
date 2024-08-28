package com.golfsprint.GolfSprint.User;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Roles.UserRole;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="user_table")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "user_id")
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String number;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
    private Boolean locked = false;
    private Boolean enabled = false;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id",referencedColumnName = "company_id") // This assumes a column "company_id" in the User table
    private Company company;
    public User(String name,String surname, String email, String number, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.number = number;
        this.password = password;
    }
    public User(String name,String surname, String email, String number) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.number = number;
    }
    public User(String name,String surname, String email, String number, String password, Company company) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.number = number;
        this.password = password;
        this.company = company;
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
        return email;
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
