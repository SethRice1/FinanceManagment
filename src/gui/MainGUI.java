package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.*;
import models.Budget;

public class MainGUI extends JFrame {
    // Labels, fields, and buttons for the UI
    private final JLabel incomeLabel;
    private final JTextField incomeField;
    private final JButton addIncomeButton;
    private final JLabel expenseLabel;
    private final JTextField expenseField;
    private final JButton addExpenseButton;
    private final JLabel balanceLabel;
    private final JTextArea budgetDisplayArea;
    private final JComboBox<String> monthComboBox;

    // Instance of Budget class to manage personal finance data
    private final Budget budget;

    public MainGUI(Budget budget) {
        // Initialize Budget with the provided instance
        this.budget = budget;

        setTitle("Personal Finance Management System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel for Inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout()); // GridBagLayout for more flexibility in aligning components
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Month Selection
        JLabel monthLabel = new JLabel("Select Month:");
        monthComboBox = new JComboBox<>(getMonths()); // ComboBox to select month
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(monthLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(monthComboBox, gbc);

        // Income Section
        incomeLabel = new JLabel("Enter Income:");
        incomeField = new JTextField();
        addIncomeButton = new JButton("Add Income");

        // Action listener to handle income addition
        addIncomeButton.addActionListener(new AddIncomeActionListener());

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(incomeLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(incomeField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(addIncomeButton, gbc);

        // Expense Section
        expenseLabel = new JLabel("Enter Expense:");
        expenseField = new JTextField();
        addExpenseButton = new JButton("Add Expense");

        // Action listener to handle expense addition
        addExpenseButton.addActionListener(e -> addExpense());

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(expenseLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(expenseField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(addExpenseButton, gbc);

        // Balance Display
        balanceLabel = new JLabel("Total Balance: $0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for emphasis
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        inputPanel.add(balanceLabel, gbc);

        add(inputPanel, BorderLayout.NORTH); // Add input panel to the top of the frame

        // Center Panel for Budget Display
        budgetDisplayArea = new JTextArea();
        budgetDisplayArea.setEditable(false); // Make the text area read-only
        JScrollPane scrollPane = new JScrollPane(budgetDisplayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Budget Details")); // Border with title
        add(scrollPane, BorderLayout.CENTER); // Add scroll pane to the center of the frame

        // Initialize Budget Display
        updateBudgetDisplay();
    }

    /**
     * ActionListener for adding income.
     */
    private class AddIncomeActionListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            addIncome();
        }
    }

    /**
     * Adds income to the selected month in the Budget.
     */
    private void addIncome() {
        String incomeText = incomeField.getText().trim(); // Get the income value from the input field
        String selectedMonth = (String) monthComboBox.getSelectedItem(); // Get the selected month from the ComboBox

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a month.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BigDecimal income = new BigDecimal(incomeText); // Convert the income input to BigDecimal
            if (income.compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberFormatException("Negative income not allowed.");
            }
            int monthIndex = monthComboBox.getSelectedIndex() + 1; // Get the month index (1-based)
            budget.addIncome(monthIndex, selectedMonth, income); // Add income to the budget for the selected month
            JOptionPane.showMessageDialog(this, "Income added: $" + income, "Success", JOptionPane.INFORMATION_MESSAGE);
            incomeField.setText(""); // Clear the income field after adding
            updateBudgetDisplay(); // Update the budget display area
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for income.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds expense to the selected month in the Budget.
     */
    private void addExpense() {
        String expenseText = expenseField.getText().trim(); // Get the expense value from the input field
        String selectedMonth = (String) monthComboBox.getSelectedItem(); // Get the selected month from the ComboBox

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a month.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BigDecimal expense = new BigDecimal(expenseText); // Convert the expense input to BigDecimal
            if (expense.compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberFormatException("Negative expense not allowed.");
            }
            int monthIndex = monthComboBox.getSelectedIndex() + 1; // Get the month index (1-based)
            budget.addExpense(monthIndex, selectedMonth, expense); // Add expense to the budget for the selected month
            JOptionPane.showMessageDialog(this, "Expense added: $" + expense, "Success", JOptionPane.INFORMATION_MESSAGE);
            expenseField.setText(""); // Clear the expense field after adding
            updateBudgetDisplay(); // Update the budget display area
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for expense.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the budget display area and balance label.
     * Displays the budget summary for each month, including income, expenses, and balance.
     */
    private void updateBudgetDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget Name: ").append(budget.getName()).append("\n");
        sb.append("Entity ID: ").append(budget.getEntityId()).append("\n\n");

        sb.append(String.format("%-15s %-15s %-15s %-15s%n", "Month", "Income", "Expenses", "Balance"));
        sb.append("---------------------------------------------------------------\n");

        double[] monthlyBudgets = budget.getMonthlyBudgets();
        double[] monthlyExpenses = budget.getMonthlyExpenses();
        for (int i = 0; i < monthlyBudgets.length; i++) {
            String month = getMonths()[i];
            double income = monthlyBudgets[i]; // Get income for the month
            double expenses = monthlyExpenses[i]; // Get expenses for the month
            double balance = calculateMonthlyBalance(income, expenses); // Calculate balance using dedicated method
            sb.append(String.format("%-15s $%-14.2f $%-14.2f $%-14.2f%n", month, income, expenses, balance));
        }

        budgetDisplayArea.setText(sb.toString()); // Set the text in the display area
        balanceLabel.setText("Total Balance: $" + calculateTotalBalance()); // Update the balance label
    }

    /**
     * Calculates the balance for a given income and expenses.
     *
     * @param income   The total income for the month.
     * @param expenses The total expenses for the month.
     * @return The balance for the month.
     */
    private double calculateMonthlyBalance(double income, double expenses) {
        return income - expenses;
    }

    /**
     * Calculates the total balance across all months in the budget.
     *
     * @return The total balance.
     */
    private double calculateTotalBalance() {
        return budget.calculateBalance().doubleValue();
    }

    /**
     * Returns an array of month names.
     *
     * @return Array of month names.
     */
    private String[] getMonths() {
        return new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
    }

    public static void main(String[] args) {
        // Create and show the GUI in the event dispatch thread for thread safety
        SwingUtilities.invokeLater(() -> {
            Budget budget = new Budget("B001", "Personal Budget", 1000.0); // Pass budget instance dynamically
            MainGUI gui = new MainGUI(budget);
            gui.setVisible(true);
        });
    }
}