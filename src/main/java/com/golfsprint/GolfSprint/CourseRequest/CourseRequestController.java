package com.golfsprint.GolfSprint.CourseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/request_course")
public class CourseRequestController {
    private final CourseRequestService courseRequestService;
    @Autowired
    public CourseRequestController(CourseRequestService courseRequestService){
        this.courseRequestService = courseRequestService;
    }
    @GetMapping(path = "/getAll")
    public List<CourseRequest> getRequests(@AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        return courseRequestService.getRequestsByCompany(companyEmail);
    }
    @PostMapping(path = "/add/{courseId}")
    public ResponseEntity<String> addRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("courseId") Long courseId){
        String userEmail = userDetails.getUsername();
        return courseRequestService.addRequest(userEmail,courseId);
    }
    @DeleteMapping(path = "/delete/{courseRequestId}")
    public void deleteRequest(@PathVariable("courseRequestId") Long courseRequestId, @AuthenticationPrincipal UserDetails userDetails){
        String companyEmail = userDetails.getUsername();
        courseRequestService.deleteRequest(courseRequestId, companyEmail);
    }
}
