/*
 * File: FileManager.java
 * Description: Handles file operations related to saving and loading budget data.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package services;

 import java.io.*;
 import models.Budget;
 
 /**
  * Handles file operations related to saving and loading budget data.
  * 
  * <p>
  * The FileManager class provides functionalities to serialize and deserialize
  * {@link Budget} objects to and from the file system. This allows for persistent
  * storage of user budget data across application sessions.
  * </p>
  * 
  * <p>
  * <b>Key Functionalities:</b>
  * <ul>
  *     <li>Save a Budget object to a specified file.</li>
  *     <li>Load a Budget object from a specified file.</li>
  * </ul>
  * </p>
  */
 public class FileManager {
     // Path to the budget file
     private final String filePath;
 
     /**
      * Constructs a new FileManager with the specified file path.
      * 
      * @param filePath The path to the budget file (e.g., "budget_user.dat").
      */
     public FileManager(String filePath) {
         this.filePath = filePath;
     }
 
     /**
      * Saves the provided Budget object to the designated file.
      * 
      * <p>
      * This method serializes the Budget object and writes it to the file specified
      * during the FileManager's construction. If the file does not exist, it will
      * be created.
      * </p>
      * 
      * @param budget The Budget object to save.
      * @throws IOException If an I/O error occurs during the save operation.
      */
     public void saveBudget(Budget budget) throws IOException {
         try (FileOutputStream fileOut = new FileOutputStream(filePath);
              ObjectOutputStream outStream = new ObjectOutputStream(fileOut)) {
             outStream.writeObject(budget);
         }
     }
 
     /**
      * Loads the Budget object from the designated file.
      * 
      * <p>
      * This method deserializes the Budget object from the file specified during
      * the FileManager's construction. If the file does not exist, {@code null} is returned.
      * </p>
      * 
      * @return The loaded Budget object, or {@code null} if the file does not exist.
      * @throws IOException            If an I/O error occurs during the load operation.
      * @throws ClassNotFoundException If the Budget class is not found during deserialization.
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
 