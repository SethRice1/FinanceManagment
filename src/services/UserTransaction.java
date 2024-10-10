package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import models.Transaction;
import models.User;

/**
 * Manages user transactions and related operations.
 */
public class UserTransaction {
    private User user;
    private List<Transaction> transactions;
    private String[] transactionCategories;

    /**
     * Constructor for UserTransaction.
     *
     * @param user The user associated with these transactions.
     */
    public UserTransaction(User user) {
        this.user = user;
        this.transactions = new ArrayList<>();
        this.transactionCategories = new String[] {"Groceries", "Rent", "Utilities", "Entertainment", "Miscellaneous"};
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user != null) {
            this.user = user;
        } else {
            throw new IllegalArgumentException("User cannot be null.");
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        if (transactions != null) {
            this.transactions = transactions;
        } else {
            throw new IllegalArgumentException("Transactions list cannot be null.");
        }
    }

    public String[] getTransactionCategories() {
        return transactionCategories.clone();
    }

    public void setTransactionCategories(String[] transactionCategories) {
        if (transactionCategories != null && transactionCategories.length > 0) {
            this.transactionCategories = transactionCategories.clone();
        } else {
            throw new IllegalArgumentException("Transaction categories cannot be null or empty.");
        }
    }

    /**
     * Adds a transaction to the user's transaction list.
     *
     * @param transaction The transaction to be added.
     */
    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                transactions.add(transaction);
            } else {
                throw new IllegalArgumentException("Transaction amount must be positive.");
            }
        } else {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }
    }

    /**
     * Calculates the total expenses (negative transactions) using a for loop.
     *
     * @return The total expenses as BigDecimal.
     */
    public BigDecimal getTotalExpenses() {
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                    total = total.add(transaction.getAmount());
                }
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Counts the number of transactions in a specific category using a for loop.
     *
     * @param category The category to count transactions for.
     * @return The count of transactions in the specified category.
     */
    public int countTransactionsByCategory(String category) {
        int count = 0;
        if (category != null && !category.isEmpty()) {
            for (Transaction transaction : transactions) {
                if (transaction != null) {
                    if (transaction.getCategory().equalsIgnoreCase(category)) {
                        count++;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        return count;
    }

    /**
     * Prints all available transaction categories.
     */
    public void printTransactionCategories() {
        System.out.println("Available Transaction Categories:");
        for (String category : transactionCategories) {
            System.out.println("- " + category);
        }
    }

    /**
     * Resets all transactions using a while loop.
     */
    public void resetTransactions() {
        while (!transactions.isEmpty()) {
            transactions.remove(0);
        }
        System.out.println("All transactions have been reset.");
    }

    /**
     * Checks if the user's budget goal is met based on total expenses.
     *
     * @return True if budget goal is met, else false.
     */
    public boolean isBudgetGoalMet() {
        if (user != null) {
            BigDecimal totalExpenses = getTotalExpenses();
            BigDecimal budgetGoals = user.getBudgetGoals();
            if (totalExpenses.compareTo(budgetGoals) <= 0) {
                return true;
            } else {
                System.out.println("Budget goal exceeded.");
                return false;
            }
        } else {
            System.out.println("User information is not available.");
            return false;
        }
    }

    /**
     * Represents a summary of user transactions.
     */
    public class TransactionSummary {
        private final BigDecimal totalIncome;
        private final BigDecimal totalExpenses;

        /**
         * Constructor for TransactionSummary.
         *
         * @param totalIncome  Total income from transactions.
         * @param totalExpenses Total expenses from transactions.
         */
        public TransactionSummary(BigDecimal totalIncome, BigDecimal totalExpenses) {
            this.totalIncome = totalIncome.setScale(2, RoundingMode.HALF_UP);
            this.totalExpenses = totalExpenses.setScale(2, RoundingMode.HALF_UP);
        }

        public BigDecimal getTotalIncome() {
            return totalIncome;
        }

        public BigDecimal getTotalExpenses() {
            return totalExpenses;
        }

        public BigDecimal getNetBalance() {
            return totalIncome.subtract(totalExpenses).setScale(2, RoundingMode.HALF_UP);
        }
    }

    /**
     * Generates a summary of transactions including total income and expenses.
     *
     * @return A TransactionSummary object containing the summary.
     */
    public TransactionSummary getTransactionSummary() {
        BigDecimal totalIncome = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    totalIncome = totalIncome.add(transaction.getAmount());
                }
            }
        }
        return new TransactionSummary(totalIncome, getTotalExpenses());
    }

    /**
     * Finds a transaction by its unique identifier.
     *
     * @param transactionId The ID of the transaction to find.
     * @return The Transaction object if found, else null.
     */
    public Transaction findTransactionById(String transactionId) {
        if (transactionId != null && !transactionId.isEmpty()) {
            for (Transaction transaction : transactions) {
                if (transaction != null && transaction.getEntityId().equals(transactionId)) { // Using getEntityId()
                    return transaction;
                }
            }
        } else {
            throw new IllegalArgumentException("Transaction ID cannot be null or empty.");
        }
        return null;
    }
}
