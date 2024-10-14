/*
 * File: User.java
 * Description: Represents a user within the Personal Finance Management System.
 * Author: Seth (R0ice_1)
 * Version: 1.1
 * Date: October 14, 2024
 */

 package models;

 import java.io.Serializable;
 import java.math.BigDecimal;
 import java.util.ArrayList;
 import java.util.List;
 
 /**
  * Represents a user within the Personal Finance Management System.
  * 
  * <p>
  * The User class encapsulates user-related data, including credentials,
  * contact information, budget goals, role-based permissions, and transaction history.
  * </p>
  * 
  * <p>
  * <b>Key Attributes:</b>
  * <ul>
  *     <li><b>username:</b> Unique identifier for the user.</li>
  *     <li><b>password:</b> User's password (Note: In real applications, passwords should be hashed).</li>
  *     <li><b>email:</b> User's email address.</li>
  *     <li><b>budgetGoals:</b> Financial goals set by the user.</li>
  *     <li><b>role:</b> Defines the user's permissions (e.g., REGULAR_USER, ADMIN).</li>
  *     <li><b>transactions:</b> List of financial transactions associated with the user.</li>
  * </ul>
  * </p>
  * 
  * <p>
  * <b>Note:</b> Passwords are stored in plain text for simplicity, but it is recommended
  * to implement secure password storage mechanisms (e.g., hashing) in production applications.
  * </p>
  * 
  * @see UserRole
  * @see Transaction
  */
 public class User implements Serializable {
     private static final long serialVersionUID = 1L;
 
     // Unique username for the user
     private final String username;
     // User's password (should be hashed in real applications)
     private final String password;
     // User's email address
     private final String email;
     // User's financial budget goals
     private final BigDecimal budgetGoals;
     // User's role determining access level
     private final UserRole role;
     // List of transactions associated with the user
     private final List<Transaction> transactions;
 
     /**
      * Constructs a new User with the specified details.
      * 
      * @param username    The unique username for the user.
      * @param password    The user's password.
      * @param email       The user's email address.
      * @param budgetGoals The user's financial budget goals.
      * @param role        The user's role (REGULAR_USER or ADMIN).
      */
     public User(String username, String password, String email, BigDecimal budgetGoals, UserRole role) {
         this.username = username;
         this.password = password;
         this.email = email;
         this.budgetGoals = budgetGoals;
         this.role = role;
         this.transactions = new ArrayList<>();
     }
 
     // Getter methods
 
     /**
      * Retrieves the username of the user.
      * 
      * @return The user's username.
      */
     public String getUsername() {
         return username;
     }
 
     /**
      * Retrieves the password of the user.
      * 
      * @return The user's password.
      * @deprecated Passwords should be handled securely. This method is for demonstration purposes only.
      */
     @Deprecated
     public String getPassword() {
         return password;
     }
 
     /**
      * Validates the provided password against the user's stored password.
      *
      * <p>
      * This method compares the input password with the user's stored password.
      * It returns {@code true} if the passwords match, otherwise {@code false}.
      * </p>
      * 
      * <p>
      * <b>Security Note:</b> In production applications, passwords should be hashed and salted.
      * This method should be updated accordingly to handle hashed passwords.
      * </p>
      *
      * @param password The password to validate.
      * @return {@code true} if the password matches; {@code false} otherwise.
      */
     public boolean validatePassword(String password) {
         return this.password.equals(password);
     }
 
     /**
      * Retrieves the email address of the user.
      * 
      * @return The user's email.
      */
     public String getEmail() {
         return email;
     }
 
     /**
      * Retrieves the budget goals of the user.
      * 
      * @return The user's budget goals.
      */
     public BigDecimal getBudgetGoals() {
         return budgetGoals;
     }
 
     /**
      * Retrieves the role of the user.
      * 
      * @return The user's role (REGULAR_USER or ADMIN).
      */
     public UserRole getRole() {
         return role;
     }
 
     /**
      * Retrieves the list of transactions associated with the user.
      * 
      * @return A list of the user's transactions.
      */
     public List<Transaction> getTransactions() {
         return transactions;
     }
 
     /**
      * Adds a transaction to the user's transaction history.
      * 
      * <p>
      * This method appends the provided {@link Transaction} to the user's list of transactions.
      * </p>
      * 
      * @param transaction The Transaction object to add.
      */
     public void addTransaction(Transaction transaction) {
         transactions.add(transaction);
     }
 }
 