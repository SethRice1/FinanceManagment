package models;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Class representing a User in the system.
 */
public class User {
    private final String userId; // Declared as final
    private String username;
    private String password; // Consider using hashed passwords in real applications
    private String email;
    private BigDecimal budgetGoals; // Added attribute for budget goals

    /**
     * Constructor for User.
     *
     * @param username     The username of the user.
     * @param password     The password of the user.
     * @param email        The email address of the user.
     * @param budgetGoals  The budget goals for the user.
     */
    public User(String username, String password, String email, BigDecimal budgetGoals) {
        this.userId = UUID.randomUUID().toString(); // Generates a unique ID
        this.username = username;
        this.password = password;
        this.email = email;
        this.budgetGoals = budgetGoals;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    // No setter for userId as it's generated automatically and final

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password; // In real applications, hash the password before setting
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the budget goals of the user.
     *
     * @return The budget goals as BigDecimal.
     */
    public BigDecimal getBudgetGoals() {
        return budgetGoals;
    }

    /**
     * Sets the budget goals of the user.
     *
     * @param budgetGoals The new budget goals.
     */
    public void setBudgetGoals(BigDecimal budgetGoals) {
        if (budgetGoals != null && budgetGoals.compareTo(BigDecimal.ZERO) >= 0) {
            this.budgetGoals = budgetGoals;
        } else {
            throw new IllegalArgumentException("Budget goals must be a non-negative value.");
        }
    }

    /**
     * Overrides the default toString method to provide a string representation of the User.
     *
     * @return A string representation of the User.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", budgetGoals=" + budgetGoals +
                '}';
    }
}
