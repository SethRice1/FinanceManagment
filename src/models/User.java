package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String password; // Consider storing hashed passwords
    private final String email;
    private final BigDecimal budgetGoals;
    private final UserRole role;
    private final List<Transaction> transactions;

    /**
     * Constructor for User.
     *
     * @param username     The username.
     * @param password     The password.
     * @param email        The email address.
     * @param budgetGoals  The budget goals.
     * @param role         The user role.
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
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    } // In real applications, passwords should be hashed and handled securely.

    public String getEmail() {
        return email;
    }

    public BigDecimal getBudgetGoals() {
        return budgetGoals;
    }

    public UserRole getRole() {
        return role;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Adds a transaction to the user's transaction list.
     *
     * @param transaction The transaction to add.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
