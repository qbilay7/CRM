package com.golfsprint.GolfSprint.Course;

import com.golfsprint.GolfSprint.Company.Company;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="course")
public class Course {
    @Id
    @SequenceGenerator(
            name="course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(name = "course_id")
    private Long courseId;
    @ManyToOne( targetEntity = Company.class)
    @JoinColumn(name = "company_id",referencedColumnName = "company_id")
    private Company company;
    @Column(length = 10000)
    private String courseDescription;
    private String courseName;
    @Lob
    private String courseImage;
    private Integer price;
    private String currency;

    public Course( Company company, String courseDescription, String courseName, Integer price, String currency){

        this.company = company;
        this.courseDescription = courseDescription;
        this.courseName = courseName;
        this.price=price;
        this.currency=currency;
    }
    public Course( Company company, String courseDescription, String courseName, String courseImage, Integer price, String currency){

        this.company = company;
        this.courseDescription = courseDescription;
        this.courseName = courseName;
        this.courseImage = courseImage;
        this.price=price;
        this.currency=currency;
    }
}
