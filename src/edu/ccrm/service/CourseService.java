package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class CourseService {
   
    private final Map<String, Course> coursesByCode = new HashMap<>();

    
    public Course addCourse(Course course) {
        if (coursesByCode.containsKey(course.getCourseCode())) {
            throw new IllegalArgumentException("Error: Course with code '" + course.getCourseCode() + "' already exists.");
        }
        coursesByCode.put(course.getCourseCode(), course);
        System.out.println("SUCCESS: Course '" + course.getTitle() + "' added.");
        return course;
    }

   
    public Optional<Course> findCourseByCode(String courseCode) {
        return Optional.ofNullable(coursesByCode.get(courseCode));
    }

   
    public List<Course> getAllCourses() {
        return new ArrayList<>(coursesByCode.values());
    }

        public List<Course> findCoursesByInstructor(Instructor instructor) {
        return coursesByCode.values().stream() // Create a stream from the map's values
                .filter(course -> course.getInstructor() != null && course.getInstructor().equals(instructor)) // Filter courses by instructor
                .collect(Collectors.toList()); // Collect the results into a list
    }
}