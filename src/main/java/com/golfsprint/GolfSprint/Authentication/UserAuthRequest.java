package com.golfsprint.GolfSprint.Authentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserAuthRequest {
    private String email;
    private String password;
}
