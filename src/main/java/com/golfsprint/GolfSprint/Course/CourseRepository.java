package com.golfsprint.GolfSprint.Course;


import com.golfsprint.GolfSprint.Company.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query("SELECT s from Course s WHERE s.courseId=?1")
    Optional<Course> findCourseByCourseId(Long courseId);
    Optional<List<Course>> findCoursesByCompany(Company company);


}
