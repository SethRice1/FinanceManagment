package services;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import models.Budget;
import models.Transaction;
import models.User;

/**
 * MainGUI class provides the graphical user interface for the Personal Finance Management System.
 * It includes components for managing budgets, transactions, and file operations.
 */
public class MainGUI extends JFrame {
    private User user;
    private final TransactionService transactionService;
    private final JTextArea outputArea;
    private final JTextField budgetLimitField, thresholdField;

    /**
     * Constructor for MainGUI.
     * Initializes the GUI components.
     *
     * @param user The user for whom the GUI is being created
     * @param transactionService The transaction service for managing transactions
     */
    public MainGUI(User user, TransactionService transactionService) {
        this.user = user;
        this.transactionService = transactionService;

        setTitle("Personal Finance Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output area for displaying information
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Input fields for budget management
        controlPanel.add(new JLabel("Update Budget Limit:"));
        budgetLimitField = new JTextField();
        controlPanel.add(budgetLimitField);

        JButton updateBudgetButton = new JButton("Update Budget");
        updateBudgetButton.addActionListener(e -> updateBudget());
        controlPanel.add(updateBudgetButton);

        controlPanel.add(new JLabel("Set Threshold Alert (%):"));
        thresholdField = new JTextField();
        controlPanel.add(thresholdField);

        JButton setAlertButton = new JButton("Set Alert");
        setAlertButton.addActionListener(e -> setThresholdAlert());
        controlPanel.add(setAlertButton);

        add(controlPanel, BorderLayout.NORTH);

        // Buttons for saving and loading data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JButton saveDataButton = new JButton("Save All Data");
        saveDataButton.addActionListener(e -> saveAllData());
        dataPanel.add(saveDataButton);

        JButton loadDataButton = new JButton("Load All Data");
        loadDataButton.addActionListener(e -> loadAllData());
        dataPanel.add(loadDataButton);

        add(dataPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the budget limit based on user input.
     */
    private void updateBudget() {
        try {
            double newLimit = Double.parseDouble(budgetLimitField.getText());
            user.getBudget().updateBudget(newLimit);
            outputArea.setText("Budget limit updated to: " + newLimit);
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid budget limit value.");
        }
    }

    /**
     * Sets a threshold alert for the budget.
     */
    private void setThresholdAlert() {
        try {
            double threshold = Double.parseDouble(thresholdField.getText());
            String alertMessage = user.getBudget().generateBudgetAlert(threshold);
            outputArea.setText(alertMessage.isEmpty() ? "Threshold set successfully." : alertMessage);
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid threshold value.");
        }
    }

    /**
     * Saves all data (user, budget, transactions) to separate files.
     */
    private void saveAllData() {
        try {
            FileManager.saveUserData(user);
            FileManager.saveBudgetData(user.getBudget());
            Map<User, List<Transaction>> transactionsMap = new HashMap<>();
            transactionsMap.put(user, transactionService.getUserTransactions(user));
            FileManager.saveTransactionData(transactionsMap);
            outputArea.setText("Data saved successfully.");
        } catch (IOException e) {
            outputArea.setText("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Loads all data (user, budget, transactions) from separate files.
     */
    private void loadAllData() {
        try {
            user = FileManager.loadUserData();
            user.setBudget(FileManager.loadBudgetData());
            Map<User, List<Transaction>> transactionsMap = FileManager.loadTransactionData();
            List<Transaction> transactions = transactionsMap.get(user);
            transactionService.loadTransactions(user, transactions);
            outputArea.setText("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            outputArea.setText("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Main method to run the GUI.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Sample user and budget setup
        Budget budget = new Budget(1000);
        User user = new User("username", budget);
        TransactionService transactionService = new TransactionService();

        // Launch the GUI
        MainGUI gui = new MainGUI(user, transactionService);
        gui.setVisible(true);
    }
}
