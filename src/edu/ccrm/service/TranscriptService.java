package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;


public class TranscriptService {

    
    public double calculateGpa(Student student) {
        double totalCreditPoints = 0;
        int totalCreditsAttempted = 0;

        for (Enrollment enrollment : student.getEnrolledCourses()) {
            if (enrollment.getGrade() != Grade.NOT_ASSIGNED) {
                int credits = enrollment.getCourse().getCredits();
                double gradePoints = enrollment.getGrade().getPoints();
                totalCreditPoints += credits * gradePoints;
                totalCreditsAttempted += credits;
            }
        }
       
        return totalCreditsAttempted == 0 ? 0.0 : totalCreditPoints / totalCreditsAttempted;
    }

    
    public String generateTranscript(Student student) {
        StringBuilder transcript = new StringBuilder();
        transcript.append("--- TRANSCRIPT ---\n");
        transcript.append(student.getProfileDetails()).append("\n");
        transcript.append("------------------\n");
        transcript.append("Enrolled Courses:\n");

        if (student.getEnrolledCourses().isEmpty()) {
            transcript.append("  No courses enrolled.\n");
        } else {
            for (Enrollment enrollment : student.getEnrolledCourses()) {
                transcript.append("  - ").append(enrollment.getCourse().toString())
                          .append(", Grade: ").append(enrollment.getGrade())
                          .append("\n");
            }
        }

        transcript.append("------------------\n");
      
        transcript.append(String.format("Cumulative GPA: %.2f\n", calculateGpa(student)));
        transcript.append("--- END OF TRANSCRIPT ---\n");

        return transcript.toString();
    }
}