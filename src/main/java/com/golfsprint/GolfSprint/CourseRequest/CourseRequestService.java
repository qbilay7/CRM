package com.golfsprint.GolfSprint.CourseRequest;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.Course.Course;
import com.golfsprint.GolfSprint.Course.CourseRepository;
import com.golfsprint.GolfSprint.User.User;
import com.golfsprint.GolfSprint.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseRequestService {
    private final CourseRequestRepository courseRequestRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public List<CourseRequest> getRequestsByCompany(String companyEmail){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return courseRequestRepository.findAll().stream()
                .filter(courseRequest -> courseRequest.getCourse().getCompany().equals(companyOptional.get()))
                .sorted(Comparator.comparing(CourseRequest::getRequestDate).reversed())
                .collect(Collectors.toList());
    }
    public ResponseEntity<String> addRequest(String userEmail, Long courseId){
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(()-> new IllegalStateException("No user exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalStateException("No course exist"));

        if (courseRequestRepository.findCourseRequestByCourseAndUser(course, user).isPresent()){
            return ResponseEntity.status(403).body("You already sent request for this course.");
        }
        else{
            CourseRequest courseRequest = new CourseRequest(user, course);
           courseRequestRepository.save(courseRequest);
           return ResponseEntity.ok("Success");
        }
    }
    public void deleteRequest(Long courseRequestId, String companyEmail){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        Optional<CourseRequest> courseRequest = courseRequestRepository.findById(courseRequestId);
        if(companyOptional.isEmpty() || courseRequest.isEmpty()){
            throw new IllegalStateException("Error");
        }
        if(courseRequest.get().getCourse().getCompany().equals(companyOptional.get())) {
            courseRequestRepository.deleteById(courseRequestId);
        }
        else throw new IllegalStateException("Not authorized.");
    }
    public void deleteAll(Company company){
        Optional<List<CourseRequest>> requests = courseRequestRepository.findCourseRequestsByCourseCompany(company);
        requests.ifPresent(courseRequestRepository::deleteAll);
    }
}
