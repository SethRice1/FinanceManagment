package services;

import exceptions.BudgetExceededException;
import exceptions.InvalidInputException;
import java.util.*;
import java.util.stream.Collectors;
import models.Budget;
import models.Transaction;
import models.TransactionType; // Add this import
import models.User; // Add this import

/**
 * TransactionService class handles all transaction-related operations in the Personal Finance Management System.
 * It provides methods for adding transactions, retrieving user transactions, calculating financial summaries,
 * and performing budget-related checks.
 */
public class TransactionService {

    /**
     * Adds a new transaction for a user, performing necessary validations and budget checks.
     *
     * @param user The user to whom the transaction belongs
     * @param transaction The transaction to be added
     * @throws InvalidInputException If the transaction amount is not positive
     * @throws BudgetExceededException If the transaction would exceed the user's budget
     */
    public void addTransaction(User user, Transaction transaction) throws InvalidInputException, BudgetExceededException {
        // Validate input
        if (transaction.getAmount() <= 0) {
            throw new InvalidInputException("Transaction amount must be positive");
        }

        // Check if the transaction exceeds the budget
        if (transaction.getType() == TransactionType.EXPENSE) {
            double totalExpenses = calculateTotalExpenses(user);
            if (totalExpenses + transaction.getAmount() > user.getBudget().getLimit()) {
                throw new BudgetExceededException("This transaction would exceed your budget");
            }
        }

        // Add the transaction
        user.addTransaction(transaction);
    }

    /**
     * Retrieves all transactions for a given user.
     *
     * @param user The user whose transactions are to be retrieved
     * @return A list of all transactions for the user
     */
    public List<Transaction> getUserTransactions(User user) {
        return user.getTransactions();
    }

    /**
     * Calculates the total income for a user.
     *
     * @param user The user for whom to calculate total income
     * @return The sum of all income transactions
     */
    public double calculateTotalIncome(User user) {
        return user.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Calculates the total expenses for a user.
     *
     * @param user The user for whom to calculate total expenses
     * @return The sum of all expense transactions
     */
    public double calculateTotalExpenses(User user) {
        return user.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Calculates the total expenses for a specific category.
     *
     * @param user The user for whom to calculate category expenses
     * @param category The category to calculate expenses for
     * @return The sum of all expense transactions in the specified category
     */
    public double calculateCategoryExpenses(User user, String category) {
        return user.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE && t.getCategory().equals(category))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Checks if a single transaction exceeds the budget limit.
     *
     * @param transaction The transaction to check
     * @param budget The budget to check against
     * @return true if the transaction amount exceeds the budget limit, false otherwise
     */
    public boolean isTransactionExceedingBudget(Transaction transaction, Budget budget) {
        return transaction.getAmount() > budget.getLimit();
    }

    /**
     * Retrieves a list of transactions that exceed the budget limit.
     *
     * @param transactions The list of transactions to check
     * @param budget The budget to check against
     * @return A list of expense transactions that exceed the budget limit
     */
    public List<Transaction> getTransactionsExceedingBudget(List<Transaction> transactions, Budget budget) {
        return transactions.stream()
            .filter(t -> t.getType() == TransactionType.EXPENSE && t.getAmount() > budget.getLimit())
            .collect(Collectors.toList());
    }

    /**
     * Performs a budget check for a user.
     * This method compares the user's total expenses against their budget limit.
     *
     * @param user The user to perform the budget check for
     * @param budget The budget to check against
     */
    public void someMethod(User user, Budget budget) {
        double totalExpenses = calculateTotalExpenses(user);
        if (totalExpenses > budget.getLimit()) {
            System.out.println("User has exceeded their budget");
        } else {
            System.out.println("User is within budget");
        }
    }

    /**
     * Performs an operation based on the transaction type and budget.
     * This method is a placeholder and should be implemented based on specific requirements.
     *
     * @param user The user for whom the operation is performed
     * @param transactionType The type of transaction (as a string)
     * @param budget The budget to consider in the operation
     * @return The updated or new Budget object after the operation
     */
    public Budget anotherMethod(User user, String transactionType, Budget budget) {
        
        return budget; // Return the budget or a new Budget object based on the method's logic
    }
}
