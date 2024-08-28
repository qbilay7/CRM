package com.golfsprint.GolfSprint.CourseRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.golfsprint.GolfSprint.Course.Course;
import com.golfsprint.GolfSprint.User.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="courseRequest")
public class CourseRequest {
    @Id
    @SequenceGenerator(
            name="courseRequest_sequence",
            sequenceName = "courseRequest_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "courseRequest_sequence"
    )
    @Column(name = "courseRequest_id")
    private Long courseRequestId;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestDate;

    public CourseRequest(User user, Course course){
        this.user = user;
        this.course = course;
        this.requestDate = LocalDate.now();
    }

}
