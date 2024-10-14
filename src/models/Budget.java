/*
 * File: Budget.java
 * Description: Represents a user's budget, encapsulating incomes and expenses on a monthly basis.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package models;

 import exceptions.BudgetExceededException;
 import exceptions.InvalidInputException;
 import java.io.Serializable;
 import java.math.BigDecimal;
 import java.util.HashMap;
 import java.util.Map;
 
 /**
  * Represents a user's budget, encapsulating incomes and expenses on a monthly basis.
  * 
  * <p>
  * The Budget class manages financial transactions by tracking incomes and expenses
  * for each month. It ensures that expenses do not exceed predefined budget goals.
  * </p>
  * 
  * <p>
  * <b>Key Functionalities:</b>
  * <ul>
  *     <li>Add income to a specific month.</li>
  *     <li>Add expense to a specific month with budget limit checks.</li>
  *     <li>Retrieve monthly incomes and expenses.</li>
  *     <li>Calculate the net balance across all months.</li>
  * </ul>
  * </p>
  * 
  * @see BudgetExceededException
  * @see InvalidInputException
  */
 public class Budget implements Serializable {
     private static final long serialVersionUID = 1L;
 
     // Unique identifier for the budget entity
     private final String entityId;
     // Name of the budget
     private final String name;
     // Map to store incomes per month (1-12)
     private final Map<Integer, BigDecimal> incomes;
     // Map to store expenses per month (1-12)
     private final Map<Integer, BigDecimal> expenses;
 
     /**
      * Constructs a new Budget with the specified details.
      * 
      * <p>
      * Initializes the incomes and expenses for all months to zero. Adds an initial
      * income to January as a starting point.
      * </p>
      * 
      * @param entityId      The unique identifier for the budget.
      * @param name          The name of the budget.
      * @param initialAmount The initial amount allocated to the budget.
      * @throws InvalidInputException If any of the inputs are invalid (e.g., null or negative).
      */
     public Budget(String entityId, String name, BigDecimal initialAmount) throws InvalidInputException {
         if (entityId == null || entityId.isEmpty()) {
             throw new InvalidInputException("Entity ID cannot be null or empty.");
         }
         if (name == null || name.isEmpty()) {
             throw new InvalidInputException("Budget name cannot be null or empty.");
         }
         if (initialAmount == null || initialAmount.compareTo(BigDecimal.ZERO) < 0) {
             throw new InvalidInputException("Initial amount must be non-negative.");
         }
         this.entityId = entityId;
         this.name = name;
         this.incomes = new HashMap<>();
         this.expenses = new HashMap<>();
         // Initialize all months with zero
         for (int i = 1; i <= 12; i++) {
             incomes.put(i, BigDecimal.ZERO);
             expenses.put(i, BigDecimal.ZERO);
         }
         // Add initial amount to January as an example
         addIncome(1, "Initial Funding", initialAmount);
     }
 
     /**
      * Adds income to a specific month.
      * 
      * <p>
      * This method updates the income for the given month by adding the specified amount.
      * It validates the month, category, and amount before performing the operation.
      * </p>
      * 
      * @param month    The month (1-12) to which the income should be added.
      * @param category The category of the income (e.g., "Salary", "Investment").
      * @param amount   The amount of income to add.
      * @throws InvalidInputException If the month is invalid, or if category/amount are invalid.
      */
     public final void addIncome(int month, String category, BigDecimal amount) throws InvalidInputException {
         validateMonth(month);
         if (category == null || category.isEmpty()) {
             throw new InvalidInputException("Income category cannot be null or empty.");
         }
         if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
             throw new InvalidInputException("Income amount must be non-negative.");
         }
         incomes.put(month, incomes.get(month).add(amount));
     }
 
     /**
      * Adds expense to a specific month.
      * 
      * <p>
      * This method updates the expense for the given month by adding the specified amount.
      * It validates the month, category, and amount, and checks whether adding the expense
      * exceeds the budget goal. If the budget is exceeded, a {@link BudgetExceededException}
      * is thrown.
      * </p>
      * 
      * @param month    The month (1-12) to which the expense should be added.
      * @param category The category of the expense (e.g., "Food", "Utilities").
      * @param amount   The amount of expense to add.
      * @throws InvalidInputException    If the month is invalid, or if category/amount are invalid.
      * @throws BudgetExceededException If adding the expense exceeds the monthly budget goal.
      */
     public final void addExpense(int month, String category, BigDecimal amount) throws InvalidInputException, BudgetExceededException {
         validateMonth(month);
         if (category == null || category.isEmpty()) {
             throw new InvalidInputException("Expense category cannot be null or empty.");
         }
         if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
             throw new InvalidInputException("Expense amount must be non-negative.");
         }
 
         // Calculate the new total expenses for the month
         BigDecimal newTotalExpenses = expenses.get(month).add(amount);
         // Define the budget goal for the month (This could be dynamic based on user settings)
         BigDecimal budgetGoal = new BigDecimal("10000"); // Example budget goal
 
         // Check if the new total expenses exceed the budget goal
         if (newTotalExpenses.compareTo(budgetGoal) > 0) {
             throw new BudgetExceededException("Adding this expense exceeds your budget for the month.");
         }
 
         expenses.put(month, newTotalExpenses);
     }
 
     /**
      * Retrieves the income for a specific month.
      * 
      * @param month The month (1-12) for which to retrieve the income.
      * @return The total income for the specified month.
      * @throws InvalidInputException If the month is invalid.
      */
     public BigDecimal getMonthlyIncome(int month) throws InvalidInputException {
         validateMonth(month);
         return incomes.get(month);
     }
 
     /**
      * Retrieves the expenses for a specific month.
      * 
      * @param month The month (1-12) for which to retrieve the expenses.
      * @return The total expenses for the specified month.
      * @throws InvalidInputException If the month is invalid.
      */
     public BigDecimal getMonthlyExpenses(int month) throws InvalidInputException {
         validateMonth(month);
         return expenses.get(month);
     }
 
     /**
      * Calculates the net balance across all months.
      * 
      * @return The total net balance (Total Income - Total Expenses).
      */
     public BigDecimal calculateBalance() {
         BigDecimal totalIncome = incomes.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
         BigDecimal totalExpenses = expenses.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
         return totalIncome.subtract(totalExpenses);
     }
 
     /**
      * Validates if the month is between 1 and 12.
      * 
      * @param month The month to validate.
      * @throws InvalidInputException If the month is out of the valid range.
      */
     private void validateMonth(int month) throws InvalidInputException {
         if (month < 1 || month > 12) {
             throw new InvalidInputException("Month must be between 1 and 12.");
         }
     }
 
     // Getter methods
 
     /**
      * Retrieves the unique identifier for the budget.
      * 
      * @return The entity ID of the budget.
      */
     public String getEntityId() {
         return entityId;
     }
 
     /**
      * Retrieves the name of the budget.
      * 
      * @return The name of the budget.
      */
     public String getName() {
         return name;
     }
 }
 