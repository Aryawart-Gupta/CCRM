package edu.ccrm.domain;

public class Course {
    
    private final String courseCode;
    private final String title;
    private final int credits;
    private Instructor instructor; 
    private final Semester semester;

   
    private Course(Builder builder) {
        this.courseCode = builder.courseCode;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
    }

   
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public Instructor getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    @Override
    public String toString() {
        String instructorName = (instructor != null) ? instructor.getFullName() : "Not Assigned";
        return String.format("Course[%s: %s, %d Credits, Semester: %s, Instructor: %s]",
                courseCode, title, credits, semester, instructorName);
    }

   
    public static class Builder {
        private String courseCode;
        private String title;
        private int credits;
        private Instructor instructor;
        private Semester semester;

        public Builder(String courseCode, String title) {
            this.courseCode = courseCode;
            this.title = title;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this; 
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Course build() {
           
            return new Course(this);
        }
    }
}