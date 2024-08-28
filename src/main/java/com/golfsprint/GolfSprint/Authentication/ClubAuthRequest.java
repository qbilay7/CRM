package com.golfsprint.GolfSprint.Authentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ClubAuthRequest {
    private String companyEmail;
    private String password;

}
