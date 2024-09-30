package services;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;

/**
 * MainGUI class represents the main graphical user interface for the Personal Finance Management System.
 * It provides functionality for user authentication, transaction management, and financial summary display.
 * This class extends JFrame to create the main application window.
 */
public class MainGUI extends JFrame {
    // User object to store the currently logged-in user
    private User user;
    
    // Service objects for handling transactions and user-related operations
    private final TransactionService transactionService;
    private final UserService userService;
    
    // GUI components for displaying output and capturing user input
    private JTextArea outputArea;
    private JTextField amountField, categoryField, dateField, descriptionField;
    private JComboBox<TransactionType> typeComboBox;

    /**
     * Constructor for MainGUI. Initializes services and displays the login dialog.
     * If login is successful, it proceeds to initialize the main application window.
     */
    public MainGUI() {
        // Initialize services for user authentication and transaction management
        this.transactionService = new TransactionService();
        this.userService = new UserService();

        // Show login dialog and initialize main window if login is successful
        if (showLoginDialog()) {
            initializeMainWindow();
        } else {
            System.exit(0);
        }
    }

    /**
     * Displays a login dialog and authenticates the user.
     * Uses JOptionPane to create a custom dialog for username and password input.
     * 
     * @return true if authentication is successful, false otherwise
     */
    private boolean showLoginDialog() {
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            try {
                // Attempt to authenticate user
                user = userService.authenticateUser(username, password);
                return user != null;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Authentication failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    /**
     * Initializes the main window of the application after successful login.
     * Sets up the frame properties and adds all necessary components.
     */
    private void initializeMainWindow() {
        // Set up the main frame with title, size, and layout
        setTitle("Personal Finance Management System - " + user.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add components to the main window
        addComponents();

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Adds all necessary components to the main window.
     * Creates and arranges input fields, buttons, and output area.
     */
    private void addComponents() {
        // Create panels for input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create input fields for transaction details
        amountField = new JTextField(10);
        categoryField = new JTextField(10);
        dateField = new JTextField(10);
        descriptionField = new JTextField(10);
        typeComboBox = new JComboBox<>(TransactionType.values());

        // Add labels and input fields to the input panel
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeComboBox);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);

        // Create buttons for various actions
        JButton addButton = new JButton("Add Transaction");
        JButton viewButton = new JButton("View Transactions");
        JButton summaryButton = new JButton("Financial Summary");

        // Add action listeners to buttons
        addButton.addActionListener(e -> addTransaction());
        viewButton.addActionListener(e -> viewTransactions());
        summaryButton.addActionListener(e -> showFinancialSummary());

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(summaryButton);

        // Create output area for displaying results and messages
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add all panels to the main frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * Handles the addition of a new transaction based on user input.
     * Validates input, creates a new Transaction object, and adds it to the user's transactions.
     * Displays success message or appropriate error messages in case of invalid input or exceptions.
     */
    private void addTransaction() {
        try {
            // Parse and validate user input
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryField.getText();
            LocalDate date = LocalDate.parse(dateField.getText());
            TransactionType type = (TransactionType) typeComboBox.getSelectedItem();
            String description = descriptionField.getText();

            // Create and add the new transaction
            Transaction transaction = new Transaction(amount, category, date, type, description);
            transactionService.addTransaction(user, transaction);

            outputArea.setText("Transaction added successfully.");
            clearInputFields();
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid amount. Please enter a valid number.");
        } catch (DateTimeParseException e) {
            outputArea.setText("Invalid date format. Please use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            outputArea.setText("Error adding transaction: " + e.getMessage());
        } catch (Exception e) {
            outputArea.setText("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Displays all transactions for the current user.
     * Retrieves transactions from the TransactionService and formats them for display in the output area.
     */
    private void viewTransactions() {
        StringBuilder sb = new StringBuilder("Transaction History:\n");
        for (Transaction t : transactionService.getUserTransactions(user)) {
            sb.append(String.format("%s | %s | $%.2f | %s | %s%n",
                t.getDate(), t.getType(), t.getAmount(), t.getCategory(), t.getDescription()));
        }
        outputArea.setText(sb.toString());
    }

    /**
     * Displays a financial summary for the current user.
     * Calculates and shows total income, total expenses, and the current balance.
     */
    private void showFinancialSummary() {
        double totalIncome = transactionService.calculateTotalIncome(user);
        double totalExpenses = transactionService.calculateTotalExpenses(user);
        double balance = totalIncome - totalExpenses;

        String summary = String.format("""
            Financial Summary:
            Total Income: $%.2f
            Total Expenses: $%.2f
            Balance: $%.2f""",
            totalIncome, totalExpenses, balance);
        outputArea.setText(summary);
    }

    /**
     * Clears all input fields after a transaction is added.
     * Resets text fields to empty strings and resets the transaction type combo box.
     */
    private void clearInputFields() {
        amountField.setText("");
        categoryField.setText("");
        dateField.setText("");
        descriptionField.setText("");
        typeComboBox.setSelectedIndex(0);
    }

    /**
     * Main method to launch the application.
     * Uses SwingUtilities.invokeLater to ensure thread safety when creating and showing the GUI.
     * 
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}

// Model classes moved to the services package
class User {
    private String username;
    // Add other user properties and methods as needed
    
    public String getUsername() {
        return username;
    }
}

class Transaction {
    private final double amount;
    private final String category;
    private final LocalDate date;
    private final TransactionType type;
    private final String description;
    
    public Transaction(double amount, String category, LocalDate date, TransactionType type, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
        this.description = description;
    }
    
    // Add getters and setters as needed
    public LocalDate getDate() { return date; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
}

enum TransactionType {
    INCOME, EXPENSE
}

class TransactionService {
    public void addTransaction(User user, Transaction transaction) {
        // Implementation for adding a transaction
    }
    
    public java.util.List<Transaction> getUserTransactions(User user) {
        // Implementation for getting user transactions
        return new java.util.ArrayList<>();
    }
    
    public double calculateTotalIncome(User user) {
        // Implementation for calculating total income
        return 0.0;
    }
    
    public double calculateTotalExpenses(User user) {
        // Implementation for calculating total expenses
        return 0.0;
    }
}

class UserService {
    public User authenticateUser(String username, String password) {
        // Implementation for user authentication
        return new User();
    }
}
