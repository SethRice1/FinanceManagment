package models;

/**
 * Represents the types of financial transactions in the Personal Finance Management System.
 * This enum is used to categorize transactions as either income or expenses.
 */
public enum TransactionType {
    /**
     * Represents an income transaction.
     * This type is used for transactions that increase the user's financial resources,
     * such as salary payments, investments returns, or any other form of monetary gain.
     */
    INCOME,

    /**
     * Represents an expense transaction.
     * This type is used for transactions that decrease the user's financial resources,
     * such as purchases, bill payments, or any other form of monetary expenditure.
     */
    EXPENSE;

    /**
     * Returns a string representation of the transaction type.
     * 
     * @return A lowercase string representation of the transaction type
     */
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * Converts a string to the corresponding TransactionType.
     * This method is case-insensitive.
     * 
     * @param typeString The string representation of the transaction type
     * @return The corresponding TransactionType enum value
     * @throws IllegalArgumentException if the input string doesn't match any TransactionType
     */
    public static TransactionType fromString(String typeString) {
        try {
            return TransactionType.valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type: " + typeString);
        }
    }

    /**
     * Checks if the transaction type represents an income.
     * 
     * @return true if the transaction type is INCOME, false otherwise
     */
    public boolean isIncome() {
        return this == INCOME;
    }

    /**
     * Checks if the transaction type represents an expense.
     * 
     * @return true if the transaction type is EXPENSE, false otherwise
     */
    public boolean isExpense() {
        return this == EXPENSE;
    }
}