package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Personal Finance Management System.
 * This class encapsulates user information, including username, password (hashed),
 * budget, and transactions.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    // The user's unique username
    private final String username;
    
    // The hashed password for the user
    private final String passwordHash;
    
    // The user's budget
    private Budget budget;
    
    // List of the user's transactions
    private final List<Transaction> transactions;

    /**
     * Constructs a new User with the specified username and password.
     * The password is hashed before storage for security.
     *
     * @param username The user's username
     * @param password The user's password (will be hashed)
     */
    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.transactions = new ArrayList<>();
    }

    /**
     * Constructs a new User with the specified username and budget.
     *
     * @param username The user's username
     * @param budget The user's budget
     */
    public User(String username, Budget budget) {
        this.username = username;
        this.passwordHash = ""; // No password is provided in this constructor
        this.budget = budget;
        this.transactions = new ArrayList<>();
    }

    /**
     * Gets the user's username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's budget.
     *
     * @return The user's Budget object
     */
    public Budget getBudget() {
        return this.budget;
    }

    /**
     * Sets the user's budget.
     *
     * @param budget The Budget object to set
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Adds a new transaction to the user's list of transactions.
     *
     * @param transaction The Transaction object to add
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Gets the list of user's transactions.
     *
     * @return A List of Transaction objects
     */
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param password The password to check
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String password) {
        return hashPassword(password).equals(this.passwordHash);
    }

    /**
     * Hashes the provided password for secure storage.
     * Note: This is a placeholder method and should be replaced with a secure hashing algorithm.
     *
     * @param password The password to hash
     * @return A hashed version of the password
     */
    private String hashPassword(String password) {
        // This is a placeholder and should be replaced with an actual secure hashing method
        return password + "hashed";
    }

    /**
     * Provides a string representation of the User.
     * Note: This method does not include sensitive information like the password hash.
     *
     * @return A string representation of the User
     */
    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", budget=" + budget +
               ", transactionCount=" + transactions.size() +
               '}';
    }

    /**
     * Checks if this User is equal to another object.
     * Users are considered equal if they have the same username.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equals(user.username);
    }

    /**
     * Generates a hash code for this User.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
