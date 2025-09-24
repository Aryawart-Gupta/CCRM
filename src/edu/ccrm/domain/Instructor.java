package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {
    private String department;
    private List<Course> assignedCourses;

    public Instructor(String fullName, String email, String department) {
        
        super(fullName, email); 
        this.department = department;
        this.assignedCourses = new ArrayList<>();
    }

   
    @Override
    public String getProfileDetails() {
        return "Instructor Profile:\n  ID: " + id + "\n  Name: " + fullName +
               "\n  Email: " + email + "\n  Department: " + department;
    }

   
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void addAssignedCourse(Course course) {
        this.assignedCourses.add(course);
    }
}