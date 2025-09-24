package edu.ccrm.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manages global application configuration using the Singleton pattern.
 * This ensures there's only one instance of configuration settings.
 */
public class AppConfig {
    // 1. The single, private, static instance of the class
    private static final AppConfig INSTANCE = new AppConfig();

    private final String dataFolderPath = "data";
    private final String studentsFileName = "students.csv";
    private final String coursesFileName = "courses.csv";

    // 2. A private constructor to prevent anyone else from creating it
    private AppConfig() {
        // Create the data directory if it doesn't exist on startup
        try {
            Files.createDirectories(Paths.get(dataFolderPath));
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }

    // 3. A public, static method to get the single instance
    public static AppConfig getInstance() {
        return INSTANCE;
    }

    // Public getters to access the configuration properties
    public Path getStudentFilePath() {
        return Paths.get(dataFolderPath, studentsFileName);
    }

    public Path getCourseFilePath() {
        return Paths.get(dataFolderPath, coursesFileName);
    }
}