package com.golfsprint.GolfSprint.Course;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Company.CompanyRepository;
import com.golfsprint.GolfSprint.Registration.EmailValidator;
import com.golfsprint.GolfSprint.User.User;
import com.golfsprint.GolfSprint.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    public List<Course> getCoursesByCompany(String companyEmail){
        Optional<Company> companyOptional = companyRepository.findCompanyByEmail(companyEmail);
        if(companyOptional.isEmpty()){
            throw new IllegalStateException("There is no such company");
        }
        return courseRepository.findAll().stream()
                .filter(course -> course.getCompany() != null && course.getCompany().equals(companyOptional.get()))
                .collect(Collectors.toList());
    }
    public List<Course> getCoursesByUser(String userEmail){
        Optional<User> user = userRepository.findUserByEmail(userEmail);
        if(user.isEmpty()){
            throw new IllegalStateException("Unauthorized");
        }

         return courseRepository.findAll().stream()
                .filter(course -> course.getCompany() != null && course.getCompany().equals(user.get().getCompany()))
                .collect(Collectors.toList());
    }
    public ResponseEntity<String> addCourseToCompany(Course course, String companyEmail){
        if(course.getCourseName().isEmpty() || course.getPrice() == null || course.getCurrency().isEmpty()){
            return ResponseEntity.status(403).body("Don't submit empty values to important parts.");
        }

        if(course.getPrice() < 0){
            return ResponseEntity.status(403).body("Price can not be lower than 0.");
        }
        if(course.getCourseDescription().length() > 10000){
            return ResponseEntity.status(403).body("Description is too long.");
        }
        if(course.getCourseName().length() > 35){
            return ResponseEntity.status(403).body("Course name or instructor name is too long.");
        }
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()-> new IllegalStateException("Company not found"));
        course.setCompany(company);
        courseRepository.save(course);
        return ResponseEntity.ok("Success");
    }

    public void deleteCourse(Long courseId, String companyEmail){
        Optional<Course> course = courseRepository.findById(courseId);
        Optional<Company> company = companyRepository.findCompanyByEmail(companyEmail);
        if(course.isEmpty() || company.isEmpty() || !course.get().getCompany().equals(company.get())){
            throw new IllegalStateException("Course error");
        }
        else {
            courseRepository.deleteById(courseId);
        }
    }
    @Transactional
    public ResponseEntity<String> updateCourse(String companyEmail,Long courseId, String courseDescription, String courseName, String courseImage, Integer price, String currency){
        Company company = companyRepository.findCompanyByEmail(companyEmail).orElseThrow(()->new IllegalStateException("Company doesnt exist."));
        Course course = courseRepository.findById(courseId).orElseThrow(()->new IllegalStateException("Course with id"+courseId+"does not exist"));
        if(course.getCompany().equals(company)) {
            if(price == null || currency == null){
                return ResponseEntity.status(403).body("Don't submit empty values to important parts.");
            }
            if(course.getCourseDescription().length() > 10000){
                return ResponseEntity.status(403).body("Text is too long.");
            }
            if (price < 0) {
                return ResponseEntity.status(403).body("Price can not be negative.");
            }
            if(!courseDescription.isEmpty()) {
                course.setCourseDescription(courseDescription);
            }
            if(!courseName.isEmpty() && !Objects.equals(course.getCourseName(),courseName)) {
                if(courseName.length() > 35){
                    return ResponseEntity.status(403).body("Course name is too long.");
                }
                course.setCourseName(courseName);
            }
            if(courseImage != null && !Objects.equals(course.getCourseImage(),courseImage)) {
                course.setCourseImage(courseImage);
            }
            course.setPrice(price);
            course.setCurrency(currency);
            courseRepository.save(course);
            return ResponseEntity.ok("Success");
        }
        else return ResponseEntity.status(403).body("Not authorized.");
    }
    @Transactional
    public void deleteAllCoursesByCompany(Company company){
        List<Course> courses = courseRepository.findCoursesByCompany(company).orElseThrow(() -> new IllegalStateException("Error course"));
        courseRepository.deleteAll(courses);
    }
    public Course getCourse(Long courseId, String companyEmail){
        Optional<Course> course = courseRepository.findById(courseId);
        Optional<Company> company = companyRepository.findCompanyByEmail(companyEmail);
        if(course.isPresent() && company.isPresent()){
            return course.get();
        }
        else throw new IllegalStateException("There is no such course");
    }
}
