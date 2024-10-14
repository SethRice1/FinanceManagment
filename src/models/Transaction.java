/*
 * File: Transaction.java
 * Description: Represents a financial transaction.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */
package models;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a financial transaction.
 */
public class Transaction extends FinancialEntity {
    private static final long serialVersionUID = 1L;

    private final String transactionId;
    private final String description;
    private final String category;
    private final BigDecimal amount;
    private final TransactionType type;

    /**
     * Private constructor to enforce the use of Builder.
     */
    private Transaction(Builder builder) {
        this.transactionId = UUID.randomUUID().toString();
        this.description = builder.description;
        this.category = builder.category;
        this.amount = builder.amount;
        this.type = builder.type;
    }

    // Getter methods
    public String getTransactionId() {
        return transactionId;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    /**
     * Builder class for Transaction.
     */
    public static class Builder {
        private String description;
        private String category;
        private BigDecimal amount;
        private TransactionType type;

        public Builder setDescription(String description) {
            if (description == null || description.isEmpty()) {
                throw new IllegalArgumentException("Description cannot be null or empty.");
            }
            this.description = description;
            return this;
        }

        public Builder setCategory(String category) {
            if (category == null || category.isEmpty()) {
                throw new IllegalArgumentException("Category cannot be null or empty.");
            }
            this.category = category;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Amount must be non-negative.");
            }
            this.amount = amount;
            return this;
        }

        public Builder setType(TransactionType type) {
            if (type == null) {
                throw new IllegalArgumentException("Transaction type cannot be null.");
            }
            this.type = type;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
