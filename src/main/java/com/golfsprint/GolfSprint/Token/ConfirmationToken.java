package com.golfsprint.GolfSprint.Token;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class ConfirmationToken {
    @Id
    @SequenceGenerator(
            name="token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @OneToOne(targetEntity = Company.class)
    @JoinColumn(
            nullable = true,
            name = "company_id",
            referencedColumnName = "company_id"
    )
    private Company company;
   /* @OneToOne(targetEntity = User.class)
    @JoinColumn(
            nullable = true,
            name = "user_id",
            referencedColumnName = "user_id"
    )
    private User user; */
    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, Company company) {
        this.token = token;
        this.createdAt=createdAt;
        this.expiredAt=expiredAt;
        this.company=company;
    }
   /* public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, User user) {
        this.token = token;
        this.createdAt=createdAt;
        this.expiredAt=expiredAt;
        this.user=user;
    } */

}
