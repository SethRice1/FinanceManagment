package models;

import java.math.BigDecimal;

/**
 * Class representing a budget for a specific month.
 */
public class MonthlyBudget {
    private String month; // e.g., "January", "2024-01"
    private BigDecimal income;
    private BigDecimal expenses;

    /**
     * Constructor for MonthlyBudget.
     *
     * @param month The month for which the budget is allocated.
     */
    public MonthlyBudget(String month) {
        this.month = month;
        this.income = BigDecimal.ZERO;
        this.expenses = BigDecimal.ZERO;
    }

    // Getters and Setters
    public String getMonth() {
        return month;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void addIncome(BigDecimal amount) {
        if (amount != null) {
            this.income = this.income.add(amount);
        }
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void addExpense(BigDecimal amount) {
        if (amount != null) {
            this.expenses = this.expenses.add(amount);
        }
    }

    public BigDecimal getBalance() {
        return income.subtract(expenses);
    }

    @Override
    public String toString() {
        return "MonthlyBudget{" +
                "month='" + month + '\'' +
                ", income=" + income +
                ", expenses=" + expenses +
                ", balance=" + getBalance() +
                '}';
    }
}
