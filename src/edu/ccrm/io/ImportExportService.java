package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class ImportExportService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final AppConfig config = AppConfig.getInstance();

    public ImportExportService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

       public void exportData() {
        System.out.println("Exporting data...");
        exportStudents();
        exportCourses();
        System.out.println("Data export complete.");
    }

    private void exportStudents() {
        Path studentFile = config.getStudentFilePath();
        List<String> studentLines = new ArrayList<>();
        for (Student student : studentService.getAllStudents()) {
           
            String line = String.join(",", student.getRegNo(), student.getFullName(), student.getEmail());
            studentLines.add(line);
        }
        try {
            Files.write(studentFile, studentLines);
        } catch (IOException e) {
            System.err.println("Error exporting student data: " + e.getMessage());
        }
    }

    private void exportCourses() {
        Path courseFile = config.getCourseFilePath();
        List<String> courseLines = new ArrayList<>();
        for (Course course : courseService.getAllCourses()) {
            
            String line = String.join(",", course.getCourseCode(), course.getTitle(),
                    String.valueOf(course.getCredits()), course.getSemester().name());
            courseLines.add(line);
        }
        try {
            Files.write(courseFile, courseLines);
        } catch (IOException e) {
            System.err.println("Error exporting course data: " + e.getMessage());
        }
    }

    
    public void importData() {
        System.out.println("Importing data...");
        importStudents();
        importCourses();
        System.out.println("Data import complete.");
    }

    private void importStudents() {
        Path studentFile = config.getStudentFilePath();
        if (!Files.exists(studentFile)) {
            System.out.println("Student data file not found, skipping import.");
            return;
        }
        try (Stream<String> lines = Files.lines(studentFile)) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    try {
                        studentService.addStudent(parts[1], parts[2], parts[0]);
                    } catch (IllegalArgumentException e) {
                        
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Error importing student data: " + e.getMessage());
        }
    }

    private void importCourses() {
        Path courseFile = config.getCourseFilePath();
        if (!Files.exists(courseFile)) {
            System.out.println("Course data file not found, skipping import.");
            return;
        }
        try (Stream<String> lines = Files.lines(courseFile)) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                     
                        Course course = new Course.Builder(parts[0], parts[1])
                                .credits(Integer.parseInt(parts[2]))
                                .semester(edu.ccrm.domain.Semester.valueOf(parts[3]))
                                .build();
                        courseService.addCourse(course);
                    } catch (IllegalArgumentException e) {
                        
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Error importing course data: " + e.getMessage());
        }
    }
}