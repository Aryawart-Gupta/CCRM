package edu.ccrm.cli;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.TranscriptService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    // Instantiate all the services our application will use
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final TranscriptService transcriptService = new TranscriptService();
    private static final ImportExportService importExportService = new ImportExportService(studentService, courseService);
    private static final BackupService backupService = new BackupService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load any existing data from files on startup
        importExportService.importData();

        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageCourses();
                case 3 -> manageEnrollments();
                case 4 -> manageFileOperations();
                case 5 -> runDemos();
                case 9 -> {
                    // Save all data to files before exiting
                    importExportService.exportData();
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Campus Course & Records Manager ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments & Grades");
        System.out.println("4. File & Backup Operations");
        System.out.println("5. Utilities/Demos");
        System.out.println("9. Save & Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Return an invalid choice
        }
    }

    private static void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. Find Student by Registration No.");
        System.out.println("3. List All Students");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();
        switch (choice) {
            case 1 -> {
                System.out.print("Enter Full Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                System.out.print("Enter Registration No: ");
                String regNo = scanner.nextLine();
                try {
                    studentService.addStudent(name, email, regNo);
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Enter Registration No: ");
                String regNo = scanner.nextLine();
                Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
                if (studentOpt.isPresent()) {
                    System.out.println("Student Found: " + studentOpt.get().getProfileDetails());
                } else {
                    System.out.println("Student with Registration No '" + regNo + "' not found.");
                }
            }
            case 3 -> {
                System.out.println("\n--- List of All Students ---");
                studentService.getAllStudents().forEach(s -> {
                    System.out.println(s.getProfileDetails());
                    System.out.println("--------------------");
                });
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. List All Courses");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();
        switch (choice) {
            case 1 -> {
                try {
                    System.out.print("Enter Course Code (e.g., CS101): ");
                    String code = scanner.nextLine();
                    System.out.print("Enter Course Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Credits: ");
                    int credits = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Semester (SPRING, SUMMER, FALL): ");
                    Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());

                    Course course = new Course.Builder(code, title)
                            .credits(credits)
                            .semester(semester)
                            .build();
                    courseService.addCourse(course);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid input. Please check semester name or credits. " + e.getMessage());
                }
            }
            case 2 -> {
                System.out.println("\n--- List of All Courses ---");
                courseService.getAllCourses().forEach(System.out::println);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void manageEnrollments() {
        System.out.println("\n--- Enrollment & Grading ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Record Grade for Student");
        System.out.println("3. Print Student Transcript");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();

        switch (choice) {
            case 1 -> enrollStudentInCourse();
            case 2 -> recordStudentGrade();
            case 3 -> printStudentTranscript();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void enrollStudentInCourse() {
        System.out.println("\n--- Enroll Student in Course ---");
        System.out.print("Enter Student's Registration No: ");
        String regNo = scanner.nextLine();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);

        if (studentOpt.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);

        if (courseOpt.isEmpty()) {
            System.out.println("Course not found.");
            return;
        }

        try {
            enrollmentService.enrollStudent(studentOpt.get(), courseOpt.get());
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void recordStudentGrade() {
        System.out.println("\n--- Record Grade ---");
        System.out.print("Enter Student's Registration No: ");
        String regNo = scanner.nextLine();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        if (studentOpt.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        if (courseOpt.isEmpty()) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
        String gradeStr = scanner.nextLine().toUpperCase();
        try {
            Grade grade = Grade.valueOf(gradeStr);
            enrollmentService.recordGrade(studentOpt.get(), courseOpt.get(), grade);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid grade entered.");
        }
    }

    private static void printStudentTranscript() {
        System.out.println("\n--- Print Transcript ---");
        System.out.print("Enter Student's Registration No: ");
        String regNo = scanner.nextLine();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        if (studentOpt.isPresent()) {
            String transcript = transcriptService.generateTranscript(studentOpt.get());
            System.out.println(transcript);
        } else {
            System.out.println("Student not found.");
        }
    }
    
    private static void manageFileOperations() {
        System.out.println("\n--- File & Backup Operations ---");
        System.out.println("1. Import data from files");
        System.out.println("2. Export data to files");
        System.out.println("3. Create a Backup");
        System.out.println("4. Show Total Backup Size");
        System.out.print("Enter your choice: ");
        int choice = getUserChoice();
        switch (choice) {
            case 1 -> importExportService.importData();
            case 2 -> importExportService.exportData();
            case 3 -> backupService.createBackup();
            case 4 -> {
                long size = backupService.getBackupDirectorySize();
                System.out.printf("Total size of all backups: %d bytes (%.2f KB)\n", size, size / 1024.0);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void runDemos() {
        System.out.println("\n--- Demonstrating Specific Java Features ---");

        // 1. Arrays and Arrays utility class demo
        System.out.println("\n1. Demo: Sorting student registration numbers using Arrays.sort()");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("  No students to sort.");
        } else {
            String[] regNos = students.stream().map(Student::getRegNo).toArray(String[]::new);
            System.out.println("  Original Order: " + java.util.Arrays.toString(regNos));
            java.util.Arrays.sort(regNos);
            System.out.println("  Sorted Order:   " + java.util.Arrays.toString(regNos));
        }

        // 2. Labeled jump (break) demo
        System.out.println("\n2. Demo: Labeled break in a nested loop");
        System.out.println("  Searching for the first course in the FALL semester...");
        List<Course> courses = courseService.getAllCourses();
        SEARCH_LOOP: // This is the label
        for (Course course : courses) {
            if (course.getSemester() == Semester.FALL) {
                System.out.println("  Found Fall Course: " + course.getTitle());
                break SEARCH_LOOP; // Jumps out of the loop
            }
        }
        
        // 3. Assertion Demo
        // Note: Assertions must be enabled with the -ea VM flag.
        System.out.println("\n3. Demo: Assertion");
        try {
            if (!students.isEmpty()) {
                // This will pass if GPA is positive
                assert transcriptService.calculateGpa(students.get(0)) >= 0 : "GPA cannot be negative!";
                System.out.println("  Assertion check passed (GPA is not negative).");
            } else {
                 System.out.println("  Cannot run assertion demo, no students exist.");
            }
        } catch (AssertionError e) {
            System.err.println("  Assertion failed: " + e.getMessage());
        }

        System.out.println("\n--- End of Demos ---");
    }
}