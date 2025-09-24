package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentService {
    
    private final Map<String, Student> studentsByRegNo = new HashMap<>();

    
    public Student addStudent(String fullName, String email, String regNo) {
        if (studentsByRegNo.containsKey(regNo)) {
            
            throw new IllegalArgumentException("Student with RegNo " + regNo + " already exists.");
        }
        Student student = new Student(fullName, email, regNo);
        studentsByRegNo.put(regNo, student);
        System.out.println("Student added successfully: " + fullName);
        return student;
    }

    
    public Optional<Student> findStudentByRegNo(String regNo) {
        return Optional.ofNullable(studentsByRegNo.get(regNo));
    }

   
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentsByRegNo.values());
    }

  
}