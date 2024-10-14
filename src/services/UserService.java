package services;

import exceptions.InvalidInputException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import models.Transaction;
import models.User;
import models.UserRole;

/**
 * Service class for managing users.
 */
public class UserService {
    private final Map<String, User> users;

    /**
     * Constructor initializes the user storage.
     */
    public UserService() {
        users = new HashMap<>();
        // Optionally, add a default admin user
        users.put("admin", new User("admin", "admin123", "admin@example.com", BigDecimal.ZERO, UserRole.ADMIN));
    }

    /**
     * Registers a new user.
     *
     * @param username    The username.
     * @param password    The password.
     * @param email       The email address.
     * @param budgetGoals The budget goals.
     * @param role        The user role.
     * @throws InvalidInputException if the username already exists or inputs are invalid.
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
     * Validates user credentials.
     *
     * @param username The username.
     * @param password The password.
     * @return True if credentials are valid, else false.
     */
    public boolean validateCredentials(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The username.
     * @return The User object, or null if not found.
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * Lists all registered users.
     *
     * @return A map of username to User objects.
     */
    public Map<String, User> listAllUsers() {
        return new HashMap<>(users);
    }

    /**
     * Adds a transaction to a user's transaction list.
     *
     * @param username    The username.
     * @param transaction The transaction to add.
     * @throws InvalidInputException if the user does not exist.
     */
    public void addTransactionToUser(String username, Transaction transaction) throws InvalidInputException {
        User user = users.get(username);
        if (user == null) {
            throw new InvalidInputException("User does not exist.");
        }
        user.addTransaction(transaction);
    }
}
