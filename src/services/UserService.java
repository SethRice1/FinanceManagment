package services;

import java.util.ArrayList;
import java.util.List;
import models.User;

/**
 * UserService class manages user-related operations in the Personal Finance Management System.
 * It handles user registration, authentication, and retrieval.
 */
public class UserService {
    // List to store all registered users
    private final List<User> users;

    /**
     * Constructs a new UserService instance.
     * Initializes an empty list to store users.
     */
    public UserService() {
        this.users = new ArrayList<>();
    }

    /**
     * Registers a new user with the given username and password.
     * 
     * @param username The username for the new user
     * @param password The password for the new user
     */
    public void registerUser(String username, String password) {
        User newUser = new User(username, password);
        users.add(newUser);
    }

    /**
     * Authenticates a user with the given username and password.
     * 
     * @param username The username of the user to authenticate
     * @param password The password to check against the user's stored password
     * @return The authenticated User object if successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.checkPassword(password))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a user by their username.
     * 
     * @param username The username of the user to retrieve
     * @return The User object if found, null otherwise
     */
    public User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}