package models;

import java.io.Serializable;

/**
 * Represents a financial transaction in the Personal Finance Management System.
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double amount;  // Set as final since it's not modified after initialization
    private final TransactionType type;  // Set as final
    private final String category;  // Set as final
    private final String description;  // Set as final
    private final String date;  // Set as final

    /**
     * Constructs a new Transaction.
     *
     * @param amount The transaction amount
     * @param type The type of transaction (e.g., INCOME, EXPENSE)
     * @param category The category of the transaction
     * @param description A brief description of the transaction
     * @param date The date of the transaction
     */
    public Transaction(double amount, TransactionType type, String category, String description, String date) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    /**
     * Gets the transaction amount.
     *
     * @return The transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the transaction type.
     *
     * @return The transaction type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Gets the category of the transaction.
     *
     * @return The category of the transaction
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the description of the transaction.
     *
     * @return The description of the transaction
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the date of the transaction.
     *
     * @return The date of the transaction
     */
    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "amount=" + amount +
               ", type=" + type +
               ", category='" + category + '\'' +
               ", description='" + description + '\'' +
               ", date='" + date + '\'' +
               '}';
    }
}
