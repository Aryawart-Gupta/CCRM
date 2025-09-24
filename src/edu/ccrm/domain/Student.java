package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private LocalDate registrationDate;
    private List<Enrollment> enrolledCourses;

    public Student(String fullName, String email, String regNo) {
        super(fullName, email); // Calls the parent class constructor
        this.regNo = regNo;
        this.registrationDate = LocalDate.now();
        this.enrolledCourses = new ArrayList<>();
    }

    @Override
    public String getProfileDetails() {
        return "Student Profile:\n  ID: " + id + "\n  RegNo: " + regNo +
               "\n  Name: " + fullName + "\n  Email: " + email +
               "\n  Registered On: " + registrationDate;
    }

   
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public List<Enrollment> getEnrolledCourses() { return enrolledCourses; }
}