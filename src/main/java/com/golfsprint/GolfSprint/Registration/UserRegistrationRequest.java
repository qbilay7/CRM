package com.golfsprint.GolfSprint.Registration;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRegistrationRequest {
    private String name;
    private String surname;
    private String email;
    private String number;
    private String password;
}
