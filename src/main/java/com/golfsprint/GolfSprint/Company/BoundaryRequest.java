package com.golfsprint.GolfSprint.Company;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BoundaryRequest {
    private LocalTime startBoundary;
    private LocalTime finishBoundary;
}
