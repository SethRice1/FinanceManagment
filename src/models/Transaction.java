package models;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Class representing a financial transaction.
 */
public class Transaction {
    private String entityId;
    private String description;
    private BigDecimal amount;
    private String category;

    /**
     * Constructor for Transaction.
     *
     * @param description Description of the transaction.
     * @param amount      Amount of the transaction.
     * @param category    Category of the transaction.
     */
    public Transaction(String description, BigDecimal amount, String category) {
        this.entityId = UUID.randomUUID().toString(); // Generates a unique ID
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    // Getters and Setters

    public String getEntityId() {
        return entityId;
    }

    // No setter for entityId as it's generated automatically

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Amount cannot be null.");
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category != null && !category.isEmpty()) {
            this.category = category;
        } else {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
    }

    /**
     * Overrides the default toString method to provide a string representation of the Transaction.
     *
     * @return A string representation of the Transaction.
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "entityId='" + entityId + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                '}';
    }
}
