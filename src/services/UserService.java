package services;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import models.User;

/**
 * Service class to manage User operations.
 */
public class UserService {
    // In-memory storage for users; key is userId
    private final Map<String, User> userDatabase = new HashMap<>();

    /**
     * Registers a new user.
     *
     * @param username      The username of the new user.
     * @param password      The password of the new user.
     * @param email         The email address of the new user.
     * @param budgetGoals   The budget goals for the new user.
     * @return The newly created User object.
     * @throws IllegalArgumentException if the username already exists.
     */
    public User registerUser(String username, String password, String email, BigDecimal budgetGoals) {
        // Check if username already exists
        if (isUsernameTaken(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Create a new User
        User newUser = new User(username, password, email, budgetGoals);
        userDatabase.put(newUser.getUserId(), newUser);
        return newUser;
    }

    /**
     * Adds a user to the service.
     *
     * @param user The user to be added.
     * @throws IllegalArgumentException if the user is null or already exists.
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        String userId = user.getUserId();
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }

        if (userDatabase.containsKey(userId)) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }

        userDatabase.put(userId, user);
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The username to search for.
     * @return The User object if found, else null.
     */
    public User getUser(String username) {
        for (User user : userDatabase.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The User object if found, else null.
     */
    public User getUserById(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        return userDatabase.get(userId);
    }

    /**
     * Deletes a user by username.
     *
     * @param username The username of the user to delete.
     * @return True if deletion was successful, else false.
     */
    public boolean deleteUser(String username) {
        User user = getUser(username);
        if (user != null) {
            userDatabase.remove(user.getUserId());
            return true;
        }
        return false;
    }

    /**
     * Updates a user's email.
     *
     * @param username The username of the user to update.
     * @param newEmail The new email address.
     * @return True if update was successful, else false.
     */
    public boolean updateUserEmail(String username, String newEmail) {
        User user = getUser(username);
        if (user != null) {
            user.setEmail(newEmail);
            return true;
        }
        return false;
    }

    /**
     * Validates user credentials.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return True if credentials are valid, else false.
     */
    public boolean validateCredentials(String username, String password) {
        User user = getUser(username);
        if (user != null) {
            return user.getPassword().equals(password); // In real applications, use hashed passwords
        }
        return false;
    }

    /**
     * Lists all registered users.
     *
     * @return An unmodifiable map of userIds to User objects.
     */
    public Map<String, User> listAllUsers() {
        return Collections.unmodifiableMap(userDatabase);
    }

    /**
     * Checks if a username is already taken.
     *
     * @param username The username to check.
     * @return True if the username is taken, else false.
     */
    private boolean isUsernameTaken(String username) {
        for (User user : userDatabase.values()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
