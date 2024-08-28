package com.golfsprint.GolfSprint.Email;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmailMessage {
    private String message;
    @Nullable
    private String subject;
}
