# Campus Course & Records Manager (CCRM)

This is a console-based Java SE application for managing students, courses, enrollments, and grades for an educational institute. The project is built using core Java principles and modern language features.

---

## How to Run
1.  **JDK Version:** Requires Java SE 17 or higher (developed on JDK 21).
2.  **Clone the repository:** `git clone https://github.com/Aryawart-Gupta/CCRM
3.  **Compile & Run:** Open the project in an IDE like Eclipse and run the `edu.ccrm.cli.Main` class.
4.  **Enable Assertions:** To run with assertions enabled (for the demo), use the VM argument `-ea`.

---

## Evolution of Java
- **1995:** Java 1.0 released by Sun Microsystems.
- **1998:** J2SE 1.2 (Java 2) released, introducing Swing and the Collections Framework.
- **2004:** J2SE 5.0 (Tiger) released, adding Generics, Enums, and Annotations.
- **2014:** Java SE 8 released, a major update with Lambdas, Stream API, and a new Date/Time API.
- **2018:** Java SE 11 released as a Long-Term Support (LTS) version.
- **2021:** Java SE 17 (LTS) released.
- **2023:** Java SE 21 (LTS) released.

---

## Java ME vs SE vs EE

| Feature          | Java ME (Micro Edition)    | Java SE (Standard Edition)    | Java EE (Enterprise Edition)       |
|:-----------------|:---------------------------|:------------------------------|:-----------------------------------|
|   Target         | Mobile, embedded devices   | Desktops, servers             | Large-scale enterprise systems     |
|   APIs           | Limited, small footprint   | Core Java language, I/O, etc. | Extends SE with Servlets, JSP, etc.|
|   Use Case       | Old mobile games, SIM cards| General-purpose programming   | Web applications, backend services |

---

## Java Architecture: JDK, JRE, JVM
*JVM (Java Virtual Machine):  An abstract machine that provides a runtime environment to execute Java bytecode. It is platform-dependent.
*JRE (Java Runtime Environment): A software package containing the JVM and standard libraries needed to *run* Java applications.
*JDK (Java Development Kit): A superset of the JRE. It contains everything in the JRE, plus development tools like the compiler (`javac`) needed to *create* Java applications.


## Syllabus Topic Mapping

| Syllabus Topic            | File/Class/Method Where Demonstrated                                 |
|:--------------------------|:---------------------------------------------------------------------|
| Inheritance               | `Student.java`, `Instructor.java` (extending `Person`)                 |
| Polymorphism              | `TranscriptService.generateTranscript()` (calls `course.toString()`) |
| Abstraction               | `Person.java` (abstract class with abstract method)                  |
| Encapsulation             | All domain classes (e.g., `Student.java` with private fields)        |
| Singleton Pattern         | `AppConfig.java`                                                     |
| Builder Pattern           | `Course.java` (inner class `Course.Builder`)                           |
| Custom Exceptions         | `DuplicateEnrollmentException.java`                                  |
| Exception Handling        | `Main.java` in `enrollStudentInCourse()` (try-multi-catch block)     |
| NIO.2 & Streams (I/O)     | `ImportExportService.java` (uses `Files.lines`, `Files.write`)       |
| Lambdas & Streams API     | `CourseService.findCoursesByInstructor()`, `Main.runDemos()`         |
| Recursion                 | `BackupService.getBackupDirectorySize()` (uses `Files.walkFileTree`) |
| Arrays Utility Class      | `Main.runDemos()` (uses `Arrays.sort()` and `Arrays.toString()`)     |
| Labeled Jump              | `Main.runDemos()` (uses a labeled `break`)                           |
| Assertions                | `Main.runDemos()` (uses an `assert` statement)                       |
| Date/Time API             | `Student.java` (registrationDate), `BackupService.java` (timestamp)  |
| Enums with Constructors   | `Grade.java`                                                         |
