package services;

import exceptions.InvalidInputException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Transaction;
import models.TransactionType;
import models.User;

/**
 * Manages user transactions and related operations.
 */
public class UserTransaction {
    private static final Logger LOGGER = Logger.getLogger(UserTransaction.class.getName());
    private final UserService userService;

    /**
     * Constructor for UserTransaction.
     *
     * @param userService The UserService instance to interact with user data.
     */
    public UserTransaction(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("UserService cannot be null.");
        }
        this.userService = userService;
    }

    /**
     * Calculates the total expenses for a user.
     *
     * @param username The username of the user.
     * @return The total expenses as BigDecimal.
     * @throws InvalidInputException if the user does not exist.
     */
    public BigDecimal getTotalExpenses(String username) throws InvalidInputException {
        User user = userService.getUser(username);
        if (user == null) {
            LOGGER.log(Level.WARNING, "User {0} does not exist.", username);
            throw new InvalidInputException("User does not exist.");
        }

        BigDecimal totalExpenses = user.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        LOGGER.log(Level.INFO, "Total expenses for user {0}: {1}", new Object[]{username, totalExpenses});
        return totalExpenses;
    }

    /**
     * Counts the number of transactions in a specific category for a user.
     *
     * @param username The username of the user.
     * @param category The category to count transactions for.
     * @return The count of transactions in the specified category.
     * @throws InvalidInputException if the user does not exist.
     */
    public int countTransactionsByCategory(String username, String category) throws InvalidInputException {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }

        User user = userService.getUser(username);
        if (user == null) {
            LOGGER.log(Level.WARNING, "User {0} does not exist.", username);
            throw new InvalidInputException("User does not exist.");
        }

        int count = (int) user.getTransactions().stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .count();

        LOGGER.log(Level.INFO, "User {0} has {1} transactions in category {2}.", new Object[]{username, count, category});
        return count;
    }

    /**
     * Generates a summary of transactions for a user.
     *
     * @param username The username of the user.
     * @return A TransactionSummary object containing the summary.
     * @throws InvalidInputException if the user does not exist.
     */
    public TransactionSummary getTransactionSummary(String username) throws InvalidInputException {
        User user = userService.getUser(username);
        if (user == null) {
            LOGGER.log(Level.WARNING, "User {0} does not exist.", username);
            throw new InvalidInputException("User does not exist.");
        }

        BigDecimal totalIncome = user.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = user.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        TransactionSummary summary = new TransactionSummary(totalIncome, totalExpenses);
        LOGGER.log(Level.INFO, "Transaction summary for user {0}: {1}", new Object[]{username, summary});
        return summary;
    }

    /**
     * Represents a summary of user transactions.
     */
    public static class TransactionSummary {
        private final BigDecimal totalIncome;
        private final BigDecimal totalExpenses;

        /**
         * Constructor for TransactionSummary.
         *
         * @param totalIncome   Total income from transactions.
         * @param totalExpenses Total expenses from transactions.
         */
        public TransactionSummary(BigDecimal totalIncome, BigDecimal totalExpenses) {
            this.totalIncome = totalIncome.setScale(2, RoundingMode.HALF_UP);
            this.totalExpenses = totalExpenses.setScale(2, RoundingMode.HALF_UP);
        }

        /**
         * Gets the total income.
         *
         * @return The total income.
         */
        public BigDecimal getTotalIncome() {
            return totalIncome;
        }

        /**
         * Gets the total expenses.
         *
         * @return The total expenses.
         */
        public BigDecimal getTotalExpenses() {
            return totalExpenses;
        }

        /**
         * Gets the net balance.
         *
         * @return The net balance.
         */
        public BigDecimal getNetBalance() {
            return totalIncome.subtract(totalExpenses).setScale(2, RoundingMode.HALF_UP);
        }

        @Override
        public String toString() {
            return "TransactionSummary{" +
                    "totalIncome=" + totalIncome +
                    ", totalExpenses=" + totalExpenses +
                    ", netBalance=" + getNetBalance() +
                    '}';
        }
    }
}
