/*
 * File: UserService.java
 * Description: Provides services related to user management.
 * Author: Seth (R0ice_1)
 * Version: 1.1
 * Date: October 14, 2024
 */

 package services;

 import exceptions.InvalidInputException;
 import java.math.BigDecimal;
 import java.util.HashMap;
 import java.util.Map;
 import models.Transaction;
 import models.User;
 import models.UserRole;
 
 /**
  * Service class responsible for managing user-related operations.
  * 
  * <p>
  * The UserService class handles user registration, authentication, and management.
  * It maintains an in-memory storage of users and provides methods to interact with user data.
  * </p>
  * 
  * <p>
  * <b>Key Functionalities:</b>
  * <ul>
  *     <li>Register new users with validation.</li>
  *     <li>Validate user credentials for authentication.</li>
  *     <li>Retrieve user details.</li>
  *     <li>List all registered users (primarily for admin purposes).</li>
  *     <li>Add transactions to a user's transaction history.</li>
  * </ul>
  * </p>
  * 
  * <p>
  * <b>Note:</b> Currently, user data is stored in-memory using a HashMap. For persistent
  * storage, consider integrating a database or file-based storage system.
  * </p>
  * 
  * @see User
  * @see UserRole
  * @see Transaction
  */
 public class UserService {
     // In-memory storage for users, mapping usernames to User objects
     private final Map<String, User> users;
 
     /**
      * Constructs a new UserService and initializes the user storage.
      * 
      * <p>
      * A default admin user is added for administrative tasks.
      * </p>
      */
     public UserService() {
         users = new HashMap<>();
         // Optionally, add a default admin user
         users.put("admin", new User("admin", "admin123", "admin@example.com", BigDecimal.ZERO, UserRole.ADMIN));
     }
 
     /**
      * Registers a new user with the provided details.
      * 
      * <p>
      * This method validates the input parameters and ensures that the username
      * is unique before creating and storing a new User object.
      * </p>
      * 
      * @param username    The desired username for the new user.
      * @param password    The password for the new user.
      * @param email       The email address of the new user.
      * @param budgetGoals The initial budget goals for the user.
      * @param role        The role assigned to the user (REGULAR_USER or ADMIN).
      * @throws InvalidInputException If any input is invalid or the username already exists.
      */
     public void registerUser(String username, String password, String email, BigDecimal budgetGoals, UserRole role) throws InvalidInputException {
         if (username == null || username.isEmpty()) {
             throw new InvalidInputException("Username cannot be null or empty.");
         }
         if (password == null || password.isEmpty()) {
             throw new InvalidInputException("Password cannot be null or empty.");
         }
         if (email == null || email.isEmpty()) {
             throw new InvalidInputException("Email cannot be null or empty.");
         }
         if (budgetGoals == null || budgetGoals.compareTo(BigDecimal.ZERO) < 0) {
             throw new InvalidInputException("Budget goals must be non-negative.");
         }
         if (role == null) {
             throw new InvalidInputException("User role cannot be null.");
         }
         if (users.containsKey(username)) {
             throw new InvalidInputException("Username already exists.");
         }
 
         User newUser = new User(username, password, email, budgetGoals, role);
         users.put(username, newUser);
     }
 
     /**
      * Validates user credentials for authentication.
      * 
      * <p>
      * This method checks whether the provided username exists and if the corresponding
      * password matches using the {@code validatePassword} method from the {@link User} class.
      * </p>
      * 
      * @param username The username to validate.
      * @param password The password to validate.
      * @return {@code true} if the credentials are valid; {@code false} otherwise.
      */
     public boolean validateCredentials(String username, String password) {
         if (username == null || password == null) {
             return false;
         }
         User user = users.get(username);
         return user != null && user.validatePassword(password);
     }
 
     /**
      * Retrieves a user by their username.
      * 
      * @param username The username of the user to retrieve.
      * @return The {@link User} object if found; {@code null} otherwise.
      */
     public User getUser(String username) {
         return users.get(username);
     }
 
     /**
      * Lists all registered users.
      * 
      * <p>
      * This method returns a copy of the user map to prevent external modification
      * of the internal storage.
      * </p>
      * 
      * @return A {@link Map} of usernames to {@link User} objects.
      */
     public Map<String, User> listAllUsers() {
         return new HashMap<>(users);
     }
 
     /**
      * Adds a transaction to a user's transaction history.
      * 
      * <p>
      * This method retrieves the user by username and appends the provided
      * {@link Transaction} to their transaction list.
      * </p>
      * 
      * @param username    The username of the user.
      * @param transaction The Transaction object to add.
      * @throws InvalidInputException If the user does not exist.
      */
     public void addTransactionToUser(String username, Transaction transaction) throws InvalidInputException {
         User user = users.get(username);
         if (user == null) {
             throw new InvalidInputException("User does not exist.");
         }
         user.addTransaction(transaction);
     }
 }
 