package services;

import java.io.*;
import models.Budget;

/**
 * Handles file operations for saving and loading budgets.
 */
public class FileManager {
    private final String filePath;

    /**
     * Constructor for FileManager.
     *
     * @param filePath The path to the file.
     */
    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the budget to a file.
     *
     * @param budget The budget to save.
     * @throws IOException if an I/O error occurs.
     */
    public void saveBudget(Budget budget) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream outStream = new ObjectOutputStream(fileOut)) {
            outStream.writeObject(budget);
        }
    }

    /**
     * Loads the budget from a file.
     *
     * @return The loaded Budget object, or null if the file does not exist.
     * @throws IOException            if an I/O error occurs.
     * @throws ClassNotFoundException if the Budget class is not found.
     */
    public Budget loadBudget() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream inStream = new ObjectInputStream(fileIn)) {
            return (Budget) inStream.readObject();
        }
    }
}
