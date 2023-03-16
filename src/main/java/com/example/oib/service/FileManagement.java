package com.example.oib.service;

import com.example.oib.model.entity.User;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileManagement {

    private final String FOLDER_NAME = "user";
    private final String DELIMITER = ";";

    private String getFilePath(User user) {
        return String.format("%s%s%s_%s.txt", FOLDER_NAME, File.separator, user.getOIB(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
    }

    public void createFile(User user) {
        try {
            File folder = new File(FOLDER_NAME);
            if (!folder.exists()) {
                folder.mkdir();
            }

            statusOfFile(user);

            String content = String.format("%s%s%s%s%s%s%s", user.getName(), DELIMITER, user.getSurname(), DELIMITER, user.getOIB(), DELIMITER, user.getStatus());
            String filePath = getFilePath(user);
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void statusOfFile(User user) {
        File folder = new File(FOLDER_NAME);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().startsWith(user.getOIB().toString())) {
                try {
                    String fileName = file.getAbsolutePath();
                    String content = Files.readString(file.toPath());
                    content = content.substring(0, content.lastIndexOf(DELIMITER) + 1) + "false";
                    Files.delete(file.toPath());
                    FileWriter fileWriter = new FileWriter(fileName);
                    fileWriter.write(content);
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateUserInFile(User user) {
        try {
            System.out.println("Updating user in file: " + user.toString());
            File folder = new File(FOLDER_NAME);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().startsWith(user.getOIB().toString())) {
                    System.out.println("Found user file: " + file.getName());
                    // Update user data in the file
                    String updatedUser = user.getName() + DELIMITER + user.getSurname() + DELIMITER + user.getOIB() + DELIMITER + user.getStatus();
                    Files.write(file.toPath(), updatedUser.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                    System.out.println("User updated successfully in file");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
