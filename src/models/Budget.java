/*
 * File: Budget.java
 * Description: Represents a budget entity that contains financial details.
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
 * Represents a user's budget.
 */
public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String entityId;
    private final String name;
    private final Map<Integer, BigDecimal> incomes;
    private final Map<Integer, BigDecimal> expenses;

    /**
     * Constructor for Budget.
     *
     * @param entityId      The unique identifier for the budget.
     * @param name          The name of the budget.
     * @param initialAmount The initial amount allocated.
     * @throws InvalidInputException if any input is invalid.
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
     * @param month    The month (1-12).
     * @param category The income category.
     * @param amount   The amount to add.
     * @throws InvalidInputException if inputs are invalid.
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
     * @param month    The month (1-12).
     * @param category The expense category.
     * @param amount   The amount to add.
     * @throws InvalidInputException    if inputs are invalid.
     * @throws BudgetExceededException if adding the expense exceeds the budget goals.
     */
    public final void addExpense(int month, String category, BigDecimal amount) throws InvalidInputException, BudgetExceededException {
        validateMonth(month);
        if (category == null || category.isEmpty()) {
            throw new InvalidInputException("Expense category cannot be null or empty.");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException("Expense amount must be non-negative.");
        }

        // Example: Check if total expenses exceed a certain limit (implement as needed)
        BigDecimal newTotalExpenses = expenses.get(month).add(amount);
        // Assuming a budget goal per month, replace with actual logic
        BigDecimal budgetGoal = new BigDecimal("10000"); // Example budget goal
        if (newTotalExpenses.compareTo(budgetGoal) > 0) {
            throw new BudgetExceededException("Adding this expense exceeds your budget for the month.");
        }

        expenses.put(month, newTotalExpenses);
    }

    /**
     * Retrieves the income for a specific month.
     *
     * @param month The month (1-12).
     * @return The total income for the month.
     * @throws InvalidInputException if the month is invalid.
     */
    public BigDecimal getMonthlyIncome(int month) throws InvalidInputException {
        validateMonth(month);
        return incomes.get(month);
    }

    /**
     * Retrieves the expenses for a specific month.
     *
     * @param month The month (1-12).
     * @return The total expenses for the month.
     * @throws InvalidInputException if the month is invalid.
     */
    public BigDecimal getMonthlyExpenses(int month) throws InvalidInputException {
        validateMonth(month);
        return expenses.get(month);
    }

    /**
     * Calculates the net balance across all months.
     *
     * @return The total net balance.
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
     * @throws InvalidInputException if the month is out of range.
     */
    private void validateMonth(int month) throws InvalidInputException {
        if (month < 1 || month > 12) {
            throw new InvalidInputException("Month must be between 1 and 12.");
        }
    }

    // Getter methods
    public String getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }
}
