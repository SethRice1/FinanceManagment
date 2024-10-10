package services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class FileManager {

    private final String userFilePath;

    // Constructor that initializes the file path
    public FileManager(String userFilePath) {
        this.userFilePath = userFilePath;
    }

    // Save users to a file
    public void saveUsers(List<User> users) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userFilePath))) {
            out.writeObject(users);
        }
    }

    // Load users from a file
    @SuppressWarnings("unchecked")
    public List<User> loadUsers() throws IOException, ClassNotFoundException {
        File file = new File(userFilePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>(); // Return an empty list if the file doesn't exist or is empty
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(userFilePath))) {
            return (List<User>) in.readObject();
        }
    }
}