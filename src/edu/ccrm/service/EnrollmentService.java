package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.domain.Grade;

public class EnrollmentService {
  
    public static final int MAX_CREDITS_PER_SEMESTER = 20;

    public boolean recordGrade(Student student, Course course, Grade grade) {
        for (Enrollment enrollment : student.getEnrolledCourses()) {
            if (enrollment.getCourse().equals(course)) {
                enrollment.setGrade(grade);
                System.out.println("SUCCESS: Grade " + grade + " recorded for " + student.getFullName() + " in " + course.getTitle());
                return true;
            }
        }
        System.err.println("Error: Cannot record grade. Student is not enrolled in this course.");
        return false;
    }
    public void enrollStudent(Student student, Course course)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {

        
        for (Enrollment existingEnrollment : student.getEnrolledCourses()) {
            if (existingEnrollment.getCourse().equals(course)) {
                throw new DuplicateEnrollmentException(
                    "Error: Student " + student.getFullName() + " is already enrolled in " + course.getTitle());
            }
        }

        
        int currentCredits = student.getEnrolledCourses().stream()
                .filter(enrollment -> enrollment.getCourse().getSemester() == course.getSemester())
                .mapToInt(enrollment -> enrollment.getCourse().getCredits())
                .sum();

        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException(
                "Error: Cannot enroll. Exceeds max credit limit of " + MAX_CREDITS_PER_SEMESTER + " for the semester.");
        }

     
        Enrollment newEnrollment = new Enrollment(student, course);
        student.getEnrolledCourses().add(newEnrollment);
        System.out.println("SUCCESS: " + student.getFullName() + " has been enrolled in " + course.getTitle() + ".");
    }
}