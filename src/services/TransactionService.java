package services;

import exceptions.BudgetExceededException;
import exceptions.InvalidInputException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import models.Budget;
import models.Transaction;
import models.TransactionType;
import models.User;

/**
 * TransactionService class handles all transaction-related operations in the Personal Finance Management System.
 * It provides methods for adding transactions, retrieving user transactions, calculating financial summaries,
 * and performing budget-related checks.
 */
public class TransactionService {

    private static final String FILE_NAME = "transactions.dat";

    private final Map<User, List<Transaction>> userTransactions;

    /**
     * Constructor for TransactionService.
     * Initializes the userTransactions map by loading existing transactions from the file.
     */
    public TransactionService() {
        this.userTransactions = loadTransactions();
    }

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
        userTransactions.putIfAbsent(user, new ArrayList<>());
        userTransactions.get(user).add(transaction);

        // Save transactions after adding a new one
        saveTransactions();
    }

    /**
     * Retrieves all transactions for a given user.
     *
     * @param user The user whose transactions are to be retrieved
     * @return A list of all transactions for the user
     */
    public List<Transaction> getUserTransactions(User user) {
        // Return the user's transactions or an empty list if none exist
        return userTransactions.getOrDefault(user, new ArrayList<>());
    }

    /**
     * Calculates the total income for a user.
     *
     * @param user The user for whom to calculate total income
     * @return The sum of all income transactions
     */
    public double calculateTotalIncome(User user) {
        return getUserTransactions(user).stream()
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
        return getUserTransactions(user).stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Calculates the expenses per category for a given user.
     *
     * @param user The user for whom to calculate category-wise expenses
     * @return A map of category names to the total expenses for each category
     */
    public Map<String, Double> getCategoryWiseExpenses(User user) {
        Map<String, Double> categoryExpenses = new HashMap<>();
        for (Transaction t : getUserTransactions(user)) {
            if (t.getType() == TransactionType.EXPENSE) {
                categoryExpenses.put(t.getCategory(),
                    categoryExpenses.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }
        return categoryExpenses;
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
     * @param user The user whose transactions to check
     * @param budget The budget to check against
     * @return A list of expense transactions that exceed the budget limit
     */
    public List<Transaction> getTransactionsExceedingBudget(User user, Budget budget) {
        return getUserTransactions(user).stream()
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
     * Saves all user transactions to a file.
     * Serializes the userTransactions map to persist data.
     */
    private void saveTransactions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(userTransactions);
        } catch (IOException e) {
            // Consider logging instead of printing stack trace
            System.err.println("Error saving transactions: " + e.getMessage());
        }
            
    }
    public void loadTransactions(User user, List<Transaction> transactions) {
        userTransactions.put(user, transactions);
    }
    
    /**
     * Loads all user transactions from a file.
     * Deserializes the userTransactions map to restore data.
     *
     * @return A map containing all user transactions
     */
    @SuppressWarnings("unchecked")
    private Map<User, List<Transaction>> loadTransactions() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<User, List<Transaction>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Consider logging instead of printing stack trace
            System.err.println("Error loading transactions: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
