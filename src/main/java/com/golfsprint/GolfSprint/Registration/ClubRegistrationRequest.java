package com.golfsprint.GolfSprint.Registration;

import jakarta.persistence.Lob;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ClubRegistrationRequest {
    private String companyName;
    private String companyEmail;
    private String companyNumber;
    private String companyLocation;
    private String password;

}
