package models;

import java.math.BigDecimal;

public class Budget extends FinancialEntity {
    private double totalBudget;
    private double totalExpenses;
    private boolean isBudgetExceeded;
    private double[] monthlyBudgets;
    private double[] monthlyIncome;
    private double[] monthlyExpenses;

    // Static field for maximum allowed budget limit
    private static final double MAX_BUDGET_LIMIT = 1_000_000.00;

    public Budget(String budgetId, String name, double totalBudget) {
        super(budgetId, name);
        if (totalBudget > 0) {
            if (totalBudget <= MAX_BUDGET_LIMIT) {
                this.totalBudget = totalBudget;
                this.totalExpenses = 0;
                this.isBudgetExceeded = false;
                this.monthlyBudgets = new double[12];
                this.monthlyIncome = new double[12];
                this.monthlyExpenses = new double[12];
            } else {
                throw new IllegalArgumentException("Total budget cannot exceed " + MAX_BUDGET_LIMIT);
            }
        } else {
            throw new IllegalArgumentException("Total budget must be greater than zero.");
        }
    }

    // New Constructor to match MainGUI usage
    public Budget(String budgetId, String name) {
        this(budgetId, name, 0.0);
    }

    // Getters and Setters
    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        if (totalBudget > 0) {
            if (totalBudget <= MAX_BUDGET_LIMIT) {
                this.totalBudget = totalBudget;
            } else {
                System.out.println("Total budget cannot exceed " + MAX_BUDGET_LIMIT);
            }
        } else {
            System.out.println("Total budget must be greater than zero.");
        }
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        if (totalExpenses >= 0) {
            this.totalExpenses = totalExpenses;
        } else {
            System.out.println("Total expenses cannot be negative.");
        }
    }

    public boolean isBudgetExceeded() {
        return isBudgetExceeded;
    }

    // Overloaded method to add income without specifying month (applies to total budget)
    public void addIncome(String description, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.totalBudget += amount.doubleValue();
        } else {
            System.out.println("Income amount must be positive.");
        }
    }

    // Overloaded method to add an expense without specifying month (applies to total expenses)
    public void addExpense(String description, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.totalExpenses += amount.doubleValue();
            if (totalExpenses > totalBudget) {
                isBudgetExceeded = true;
                System.out.println("Warning: Budget exceeded by " + (totalExpenses - totalBudget));
            }
        } else {
            System.out.println("Expense amount must be positive.");
        }
    }

    // Method to add income to a specific month
    public void addIncome(int month, String description, BigDecimal amount) {
        if (month >= 1 && month <= 12) {
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                this.monthlyIncome[month - 1] += amount.doubleValue();
                this.totalBudget += amount.doubleValue();
            } else {
                System.out.println("Income amount must be positive.");
            }
        } else {
            System.out.println("Invalid month. Must be between 1 and 12.");
        }
    }

    // Method to add an expense to a specific month
    public void addExpense(int month, String description, BigDecimal amount) {
        if (month >= 1 && month <= 12) {
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                this.monthlyExpenses[month - 1] += amount.doubleValue();
                this.totalExpenses += amount.doubleValue();
                if (totalExpenses > totalBudget) {
                    isBudgetExceeded = true;
                    System.out.println("Warning: Budget exceeded by " + (totalExpenses - totalBudget));
                }
            } else {
                System.out.println("Expense amount must be positive.");
            }
        } else {
            System.out.println("Invalid month. Must be between 1 and 12.");
        }
    }

    // Method to get the remaining budget
    public double getRemainingBudget() {
        if (totalBudget >= totalExpenses) {
            return totalBudget - totalExpenses;
        } else {
            System.out.println("Expenses exceed the total budget.");
            return 0;
        }
    }

    // Method to reset the budget using a while loop
    public void resetBudget(double newBudget) {
        if (newBudget > 0) {
            if (newBudget <= MAX_BUDGET_LIMIT) {
                while (totalExpenses > 0) {
                    totalExpenses -= 1;
                }
                this.totalBudget = newBudget;
                this.totalExpenses = 0;
                this.isBudgetExceeded = false;
                System.out.println("Budget has been reset to: " + newBudget);
            } else {
                System.out.println("New budget cannot exceed " + MAX_BUDGET_LIMIT);
            }
        } else {
            System.out.println("New budget must be greater than zero.");
        }
    }

    // Method to set monthly budget for a specific month
    public void setMonthlyBudget(int month, double amount) {
        if (month >= 1 && month <= 12) {
            if (amount > 0 && amount <= MAX_BUDGET_LIMIT) {
                monthlyBudgets[month - 1] = amount;
            } else {
                System.out.println("Invalid budget amount. Must be positive and within limit.");
            }
        } else {
            System.out.println("Invalid month. Must be between 1 and 12.");
        }
    }

    // Method to get monthly budget for a specific month
    public double getMonthlyBudget(int month) {
        if (month >= 1 && month <= 12) {
            return monthlyBudgets[month - 1];
        } else {
            System.out.println("Invalid month. Must be between 1 and 12.");
            return 0;
        }
    }

    // Method to get all monthly budgets
    public double[] getMonthlyBudgets() {
        return monthlyBudgets;
    }

    // Method to get all monthly expenses
    public double[] getMonthlyExpenses() {
        return monthlyExpenses;
    }

    // Method to print all monthly budgets, income, expenses, and balances
    public void printMonthlyBudgets() {
        System.out.println("Month           Income          Expenses        Balance");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < 12; i++) {
            double balance = monthlyIncome[i] - monthlyExpenses[i];
            System.out.printf("%-15s $%-14.2f $%-14.2f $%-14.2f%n", getMonthName(i + 1), monthlyIncome[i], monthlyExpenses[i], balance);
        }
    }

    // Helper method to get month name
    private String getMonthName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month - 1];
    }

    // Static method to get the maximum allowed budget limit
    public static double getMaxBudgetLimit() {
        return MAX_BUDGET_LIMIT;
    }

    // Implementation of abstract method to calculate balance
    @Override
    public BigDecimal calculateBalance() {
        return BigDecimal.valueOf(totalBudget - totalExpenses);
    }
}
