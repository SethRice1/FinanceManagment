package models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a financial transaction in the Personal Finance Management System.
 * This class implements Serializable to allow for easy storage and retrieval of transaction data.
 */
public class Transaction implements Serializable {
    // The monetary amount of the transaction
    private final double amount;
    
    // The category of the transaction (e.g., "Groceries", "Utilities", "Salary")
    private final String category;
    
    // The date when the transaction occurred
    private final LocalDate date;
    
    // The type of the transaction (e.g., INCOME, EXPENSE)
    private final TransactionType type;
    
    // A brief description of the transaction
    private final String description;

    /**
     * Constructs a new Transaction with the specified details.
     * 
     * @param amount      The monetary amount of the transaction
     * @param category    The category of the transaction
     * @param date        The date when the transaction occurred
     * @param type        The type of the transaction (INCOME or EXPENSE)
     * @param description A brief description of the transaction
     */
    public Transaction(double amount, String category, LocalDate date, TransactionType type, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
        this.description = description;
    }

    /**
     * Gets the monetary amount of the transaction.
     * 
     * @return The transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the category of the transaction.
     * 
     * @return The transaction category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the type of the transaction.
     * 
     * @return The transaction type (INCOME or EXPENSE)
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Gets the date of the transaction.
     * 
     * @return The date when the transaction occurred
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the description of the transaction.
     * 
     * @return A brief description of the transaction
     */
    public String getDescription() {
        return description;
    }

    /**
     * Provides a string representation of the Transaction.
     * 
     * @return A formatted string containing all transaction details
     */
    @Override
    public String toString() {
        return String.format("Transaction{amount=%.2f, category='%s', date=%s, type=%s, description='%s'}",
                amount, category, date, type, description);
    }

    /**
     * Checks if this Transaction is equal to another object.
     * Two Transactions are considered equal if all their fields are equal.
     * 
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction that = (Transaction) obj;
        return Double.compare(that.amount, amount) == 0 &&
               category.equals(that.category) &&
               date.equals(that.date) &&
               type == that.type &&
               description.equals(that.description);
    }

    /**
     * Generates a hash code for this Transaction.
     * 
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Double.hashCode(amount);
        result = 31 * result + category.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
