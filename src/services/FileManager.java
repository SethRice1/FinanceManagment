package services;

import java.io.*;
import java.util.List;
import java.util.Map;
import models.Budget;
import models.Transaction;
import models.User;

/**
 * FileManager class handles the serialization and deserialization of data.
 * This class provides methods to save and load different types of data to/from files.
 */
public class FileManager {

    /**
     * Saves the given data object to a file using serialization.
     * 
     * @param data The data object to be saved (e.g., User, Budget, Transactions)
     * @param fileName The name of the file to save the data to
     * @throws IOException If an I/O error occurs while writing to the file
     */
    public static void saveData(Object data, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        }
    }

    /**
     * Loads the data object from a file using deserialization.
     * 
     * @param <T> The type of the data to load
     * @param fileName The name of the file to load the data from
     * @return The deserialized data object
     * @throws IOException If an I/O error occurs while reading from the file
     * @throws ClassNotFoundException If the class of the serialized object cannot be found
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadData(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (T) ois.readObject();  // Suppressing unchecked cast warning here
        }
    }

    /**
     * Saves user data to a separate file.
     * 
     * @param user The User object to be saved
     * @throws IOException If an I/O error occurs
     */
    public static void saveUserData(User user) throws IOException {
        saveData(user, "data/user_data.ser");
    }

    /**
     * Loads user data from a file.
     * 
     * @return The deserialized User object
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If the class of the serialized object cannot be found
     */
    public static User loadUserData() throws IOException, ClassNotFoundException {
        return loadData("data/user_data.ser");
    }

    /**
     * Saves budget data to a separate file.
     * 
     * @param budget The Budget object to be saved
     * @throws IOException If an I/O error occurs
     */
    public static void saveBudgetData(Budget budget) throws IOException {
        saveData(budget, "data/budget_data.ser");
    }

    /**
     * Loads budget data from a file.
     * 
     * @return The deserialized Budget object
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If the class of the serialized object cannot be found
     */
    public static Budget loadBudgetData() throws IOException, ClassNotFoundException {
        return loadData("data/budget_data.ser");
    }

    /**
     * Saves transaction data to a separate file.
     * 
     * @param transactions The map of user transactions to be saved
     * @throws IOException If an I/O error occurs
     */
    public static void saveTransactionData(Map<User, List<Transaction>> transactions) throws IOException {
        saveData(transactions, "data/transactions.ser");
    }

    /**
     * Loads transaction data from a file.
     * 
     * @return The deserialized map of user transactions
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If the class of the serialized object cannot be found
     */
    public static Map<User, List<Transaction>> loadTransactionData() throws IOException, ClassNotFoundException {
        return loadData("data/transactions.ser");
    }

    /**
     * Checks if the user data file exists.
     * 
     * @return true if the file exists, false otherwise
     */
    public static boolean userDataFileExists() {
        File file = new File("data/user_data.ser");
        return file.exists() && !file.isDirectory();
    }

    /**
     * Deletes the user data file if it exists.
     * This method can be used for resetting the application or clearing user data.
     * 
     * @return true if the file was successfully deleted or didn't exist, false otherwise
     */
    public static boolean deleteUserDataFile() {
        File file = new File("data/user_data.ser");
        return !file.exists() || file.delete();
    }
}
