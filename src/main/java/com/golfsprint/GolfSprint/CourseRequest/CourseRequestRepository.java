package com.golfsprint.GolfSprint.CourseRequest;

import com.golfsprint.GolfSprint.Company.Company;
import com.golfsprint.GolfSprint.Course.Course;
import com.golfsprint.GolfSprint.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CourseRequestRepository extends JpaRepository<CourseRequest, Long> {
    Optional<CourseRequest> findCourseRequestByCourse(Course course);
    Optional<CourseRequest> findCourseRequestByUser(User user);
    Optional<CourseRequest> findCourseRequestByRequestDate(LocalDate date);
    Optional<CourseRequest> findCourseRequestByCourseAndUser(Course course, User user);
    Optional<List<CourseRequest>> findCourseRequestsByCourseCompany(Company company);
    Optional<List<CourseRequest>> findCourseRequestsByUser(User user);
}
