package models;

import java.io.Serializable;

/**
 * Represents the budget of a user.
 */
public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;

    private double limit; // Total budget limit
    private double totalSpent; // Total amount spent so far

    /**
     * Constructs a new Budget with a specified limit.
     *
     * @param limit The initial budget limit
     */
    public Budget(double limit) {
        this.limit = limit;
        this.totalSpent = 0.0;
    }

    /**
     * Updates the budget limit.
     *
     * @param newLimit The new budget limit
     */
    public void updateBudget(double newLimit) {
        this.limit = newLimit;
    }

    /**
     * Gets the current budget limit.
     *
     * @return The budget limit
     */
    public double getLimit() {
        return limit;
    }

    /**
     * Adds an amount to the total spent.
     *
     * @param amount The amount to be added to the total spent
     */
    public void addSpent(double amount) {
        this.totalSpent += amount;
    }

    /**
     * Calculates the remaining budget.
     *
     * @return The remaining budget amount
     */
    public double calculateRemainingBudget() {
        return limit - totalSpent;
    }

    /**
     * Generates a budget alert message based on a specified threshold percentage.
     *
     * @param threshold The threshold percentage for the budget alert
     * @return A warning message if the budget is low, otherwise an empty string
     */
    public String generateBudgetAlert(double threshold) {
        double remaining = calculateRemainingBudget();
        if (remaining < (limit * threshold / 100)) {
            return "Warning: Your remaining budget is below " + threshold + "% of the limit.";
        }
        return "";
    }

    /**
     * Checks if the remaining budget is near the given threshold percentage.
     *
     * @param thresholdPercentage The threshold percentage
     * @return true if the remaining budget is below the threshold, false otherwise
     */
    public boolean isNearLimit(double thresholdPercentage) {
        double remaining = calculateRemainingBudget();
        return (remaining / limit) < (thresholdPercentage / 100);
    }

    @Override
    public String toString() {
        return "Budget{" +
               "limit=" + limit +
               ", totalSpent=" + totalSpent +
               ", remainingBudget=" + calculateRemainingBudget() +
               '}';
    }
}
