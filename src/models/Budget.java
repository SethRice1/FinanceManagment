package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set; // Add this import

/**
 * Represents a budget for managing financial limits and expenses.
 * This class implements Serializable to allow for easy storage and retrieval.
 */
public class Budget implements Serializable {
    // The total amount allocated for the budget
    private final double totalAmount;
    
    // Map to store category-specific budget limits
    private final Map<String, Double> categoryLimits;
    
    // The total budget amount (same as totalAmount, kept for clarity)
    private final double totalBudget;
    
    // List to store all expenses
    private final List<Expense> expenses;
    
    // An overall limit for the budget (can be different from totalAmount)
    private double limit;

    /**
     * Constructs a new Budget with the specified total amount.
     * 
     * @param totalAmount The total amount allocated for this budget
     */
    public Budget(double totalAmount) {
        this.totalAmount = totalAmount;
        this.categoryLimits = new HashMap<>();
        this.totalBudget = totalAmount;
        this.expenses = new ArrayList<>();
        this.limit = 0.0; // Initialize limit to 0
    }

    /**
     * Sets a spending limit for a specific category.
     * 
     * @param category The category to set the limit for
     * @param limit The spending limit for the category
     */
    public void setCategoryLimit(String category, double limit) {
        categoryLimits.put(category, limit);
    }

    /**
     * Retrieves the spending limit for a specific category.
     * 
     * @param category The category to get the limit for
     * @return The spending limit for the category, or 0.0 if not set
     */
    public double getCategoryLimit(String category) {
        return categoryLimits.getOrDefault(category, 0.0);
    }

    /**
     * Calculates the remaining budget based on total expenses.
     * 
     * @param totalExpenses The total amount of expenses
     * @return The remaining budget amount
     */
    public double getRemainingBudget(double totalExpenses) {
        return totalAmount - totalExpenses;
    }

    /**
     * Checks if the sum of all category limits exceeds the total budget amount.
     * 
     * @return true if the total of category limits exceeds the budget, false otherwise
     */
    public boolean isTotalCategoryLimitExceeded() {
        double totalCategoryLimits = categoryLimits.values().stream().mapToDouble(Double::doubleValue).sum();
        return totalCategoryLimits > totalAmount;
    }

    /**
     * Gets the total amount allocated for this budget.
     * 
     * @return The total budget amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Retrieves all categories that have spending limits set.
     * 
     * @return A Set of category names
     */
    public Set<String> getCategories() {
        return new HashSet<>(categoryLimits.keySet());
    }

    /**
     * Calculates the remaining budget amount.
     * 
     * @return The remaining budget as a double value
     */
    public double getRemainingAmountValue() {
        return calculateRemainingBudget();
    }

    /**
     * Calculates the remaining budget by subtracting all expenses from the total budget.
     * 
     * @return The remaining budget amount
     */
    public double calculateRemainingBudget() {
        double remainingBudget = totalBudget;
        for (Expense expense : expenses) {
            remainingBudget -= expense.getAmount();
        }
        return remainingBudget;
    }

    /**
     * Gets the overall limit set for this budget.
     * 
     * @return The overall budget limit
     */
    public double getLimit() {
        return limit;
    }

    /**
     * Sets an overall limit for this budget.
     * 
     * @param limit The overall budget limit to set
     */
    public void setLimit(double limit) {
        this.limit = limit;
    }
}

/**
 * Represents an individual expense within a budget.
 */
class Expense {
    private final double amount;

    /**
     * Constructs a new Expense with the specified amount.
     * 
     * @param amount The amount of the expense
     */
    public Expense(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the amount of this expense.
     * 
     * @return The expense amount
     */
    public double getAmount() {
        return amount;
    }
}
