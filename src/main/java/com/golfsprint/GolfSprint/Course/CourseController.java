package com.golfsprint.GolfSprint.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/club/course")
public class CourseController {
    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }
    @GetMapping(path = "/getAll")
    public List<Course> getCourses(@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return courseService.getCoursesByCompany(companyEmail);
    }
    @GetMapping(path = "/getAllByUser")
    public List<Course> getCoursesByUser(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
        return courseService.getCoursesByUser(userEmail);
    }
    @PostMapping(path = "/add")
    public ResponseEntity<String> addCourse(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Course course){
        String companyEmail = userDetails.getUsername();
       return courseService.addCourseToCompany(course,companyEmail);
    }
    @DeleteMapping(path = "/delete/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Long courseId, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        courseService.deleteCourse(courseId, companyEmail);
        return ResponseEntity.ok("Successfully deleted");
    }
    @PutMapping(path = "/update/{courseId}")
    public ResponseEntity<String> updateCourse(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("courseId") Long courseId, @RequestBody Course course){
        String companyEmail = userDetails.getUsername();
       return courseService.updateCourse(companyEmail,courseId,course.getCourseDescription(),course.getCourseName(),course.getCourseImage(),course.getPrice(),course.getCurrency());
    }
    @GetMapping(path = "/get/{courseId}")
    public Course getCourse(@PathVariable("courseId") Long courseId, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return courseService.getCourse(courseId, companyEmail);
    }
}
