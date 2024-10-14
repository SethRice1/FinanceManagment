/*
 * File: UserTransaction.java
 * Description: Handles transactions related to users.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

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
  * Manages user transactions and provides related operations such as calculating
  * total expenses, counting transactions by category, and generating transaction summaries.
  * 
  * <p>
  * The UserTransaction class interacts with the {@link UserService} to retrieve user data
  * and perform operations on their transactions. It ensures accurate financial calculations
  * and provides summaries for better financial insights.
  * </p>
  * 
  * <p>
  * <b>Key Functionalities:</b>
  * <ul>
  *     <li>Calculate total expenses for a user.</li>
  *     <li>Count the number of transactions in a specific category.</li>
  *     <li>Generate a summary of transactions including total income, total expenses, and net balance.</li>
  * </ul>
  * </p>
  * 
  * @see UserService
  * @see Transaction
  * @see TransactionType
  */
 public class UserTransaction {
     private static final Logger LOGGER = Logger.getLogger(UserTransaction.class.getName());
     private final UserService userService;
 
     /**
      * Constructs a new UserTransaction with the specified UserService.
      * 
      * @param userService The UserService instance to interact with user data.
      * @throws IllegalArgumentException If the provided UserService is null.
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
      * <p>
      * This method sums up all transactions of type {@link TransactionType#EXPENSE}
      * associated with the specified user.
      * </p>
      * 
      * @param username The username of the user.
      * @return The total expenses as {@link BigDecimal}.
      * @throws InvalidInputException If the user does not exist.
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
      * <p>
      * This method filters the user's transactions by the specified category and
      * returns the count of matching transactions.
      * </p>
      * 
      * @param username The username of the user.
      * @param category The category to filter transactions by.
      * @return The count of transactions in the specified category.
      * @throws InvalidInputException If the user does not exist or if the category is invalid.
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
      * <p>
      * This method calculates the total income, total expenses, and net balance
      * (income minus expenses) for the specified user.
      * </p>
      * 
      * @param username The username of the user.
      * @return A {@link TransactionSummary} object containing the summary.
      * @throws InvalidInputException If the user does not exist.
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
      * Represents a summary of user transactions, including total income, total expenses,
      * and net balance.
      */
     public static class TransactionSummary {
         private final BigDecimal totalIncome;
         private final BigDecimal totalExpenses;
 
         /**
          * Constructs a new TransactionSummary with the specified totals.
          * 
          * @param totalIncome   The total income from transactions.
          * @param totalExpenses The total expenses from transactions.
          */
         public TransactionSummary(BigDecimal totalIncome, BigDecimal totalExpenses) {
             this.totalIncome = totalIncome.setScale(2, RoundingMode.HALF_UP);
             this.totalExpenses = totalExpenses.setScale(2, RoundingMode.HALF_UP);
         }
 
         /**
          * Retrieves the total income.
          * 
          * @return The total income.
          */
         public BigDecimal getTotalIncome() {
             return totalIncome;
         }
 
         /**
          * Retrieves the total expenses.
          * 
          * @return The total expenses.
          */
         public BigDecimal getTotalExpenses() {
             return totalExpenses;
         }
 
         /**
          * Calculates the net balance (Total Income - Total Expenses).
          * 
          * @return The net balance.
          */
         public BigDecimal getNetBalance() {
             return totalIncome.subtract(totalExpenses).setScale(2, RoundingMode.HALF_UP);
         }
 
         /**
          * Returns a string representation of the TransactionSummary.
          * 
          * @return A string detailing the summary.
          */
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
 