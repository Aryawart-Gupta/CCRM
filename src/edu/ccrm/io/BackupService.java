package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;


public class BackupService {
    private final AppConfig config = AppConfig.getInstance();
    private final String backupBaseFolder = "backup";

    
    public void createBackup() {
        
        Path backupDir = Paths.get(backupBaseFolder);
        try {
            Files.createDirectories(backupDir);
        } catch (IOException e) {
            System.err.println("Could not create base backup directory: " + e.getMessage());
            return;
        }

       
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path timestampedDir = backupDir.resolve("backup_" + timestamp);

        try {
            Files.createDirectory(timestampedDir);
            
            Files.copy(config.getStudentFilePath(), timestampedDir.resolve(config.getStudentFilePath().getFileName()));
            Files.copy(config.getCourseFilePath(), timestampedDir.resolve(config.getCourseFilePath().getFileName()));
            System.out.println("SUCCESS: Backup created at " + timestampedDir);
        } catch (FileAlreadyExistsException e) {
            System.err.println("Error: A backup with this timestamp already exists.");
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    
    public long getBackupDirectorySize() {
        Path backupDir = Paths.get(backupBaseFolder);
        if (!Files.exists(backupDir)) {
            return 0;
        }

        AtomicLong size = new AtomicLong(0);
        try {
            Files.walkFileTree(backupDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Error calculating directory size: " + e.getMessage());
        }
        return size.get();
    }
}