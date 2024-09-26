package services;

import java.io.*;
import models.User;

/**
 * FileManager class handles the serialization and deserialization of User data.
 * This class provides methods to save and load user data to/from a file.
 */
public class FileManager {
    // The file name where user data will be stored
    private static final String DATA_FILE = "user_data.ser";

    /**
     * Saves the User object to a file using serialization.
     * 
     * @param user The User object to be saved
     * @throws IOException If an I/O error occurs while writing to the file
     */
    public static void saveUserData(User user) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(user);
        }
    }

    /**
     * Loads the User object from a file using deserialization.
     * 
     * @return The deserialized User object
     * @throws IOException If an I/O error occurs while reading from the file
     * @throws ClassNotFoundException If the class of the serialized object cannot be found
     */
    public static User loadUserData() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (User) ois.readObject();
        }
    }

    /**
     * Checks if the user data file exists.
     * 
     * @return true if the file exists, false otherwise
     */
    public static boolean userDataFileExists() {
        File file = new File(DATA_FILE);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Deletes the user data file if it exists.
     * This method can be used for resetting the application or clearing user data.
     * 
     * @return true if the file was successfully deleted or didn't exist, false otherwise
     */
    public static boolean deleteUserDataFile() {
        File file = new File(DATA_FILE);
        return !file.exists() || file.delete();
    }
}
