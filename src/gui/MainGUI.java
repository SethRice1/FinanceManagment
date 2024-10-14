/*
 * File: MainGUI.java
 * Description: Main graphical user interface for the application.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */
package gui;

import exceptions.BudgetExceededException;
import exceptions.InvalidInputException;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import models.Budget;
import models.Transaction;
import models.TransactionType;
import models.User;
import models.UserRole;
import services.FileManager;
import services.UserService;
// Removed unused import of UserTransaction

/**
 * MainGUI class is responsible for creating the user interface for managing personal finances.
 */
public class MainGUI extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(MainGUI.class.getName());

    private JLabel incomeLabel;
    private JTextField incomeField;
    private JButton addIncomeButton;
    private JLabel expenseLabel;
    private JTextField expenseField;
    private JButton addExpenseButton;
    private JLabel balanceLabel;
    private JTextArea budgetDisplayArea;
    private JComboBox<String> monthComboBox;
    private Budget budget;
    private final UserService userService;
    // Removed userTransaction field as it is not used
    private User currentUser;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenu userMenu;
    private JMenuItem saveItem, loadItem, exitItem, aboutItem, loginItem, registerItem, logoutItem, manageUsersItem;

    /**
     * Constructor to initialize the MainGUI.
     */
    public MainGUI() {
        userService = new UserService();
        // Removed userTransaction initialization
        initializeUI();
        setLookAndFeel();
        setTitle("Personal Finance Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        // Create Menu Bar
        menuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveData());
        loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> loadData());
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // User Menu
        userMenu = new JMenu("User");
        loginItem = new JMenuItem("Login");
        loginItem.addActionListener(e -> showLoginDialog());
        registerItem = new JMenuItem("Register");
        registerItem.addActionListener(e -> showRegisterDialog());
        logoutItem = new JMenuItem("Logout");
        logoutItem.setEnabled(false);
        logoutItem.addActionListener(e -> logoutUser());
        manageUsersItem = new JMenuItem("Manage Users");
        manageUsersItem.setEnabled(false);
        manageUsersItem.addActionListener(e -> showManageUsersDialog());
        userMenu.add(loginItem);
        userMenu.add(registerItem);
        userMenu.addSeparator();
        userMenu.add(logoutItem);
        userMenu.add(manageUsersItem);

        // Help Menu
        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        // Add Menus to Menu Bar
        menuBar.add(fileMenu);
        menuBar.add(userMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Create Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Input Panel
        mainPanel.add(createInputPanel(), BorderLayout.NORTH);

        // Budget Display Area
        budgetDisplayArea = createBudgetDisplayArea();
        mainPanel.add(new JScrollPane(budgetDisplayArea), BorderLayout.CENTER);

        // Balance Label
        balanceLabel = new JLabel("Total Balance: $0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(balanceLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Creates the input panel for adding income and expenses.
     *
     * @return The input JPanel.
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Add Income/Expense"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Select Month
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Month:"), gbc);
        monthComboBox = new JComboBox<>(getMonths());
        gbc.gridx = 1;
        panel.add(monthComboBox, gbc);

        // Enter Income
        gbc.gridx = 0;
        gbc.gridy = 1;
        incomeLabel = new JLabel("Enter Income:");
        panel.add(incomeLabel, gbc);
        gbc.gridx = 1;
        incomeField = new JTextField();
        panel.add(incomeField, gbc);
        addIncomeButton = new JButton("Add Income");
        addIncomeButton.addActionListener(e -> addIncomeAction());
        gbc.gridy = 2;
        panel.add(addIncomeButton, gbc);

        // Enter Expense
        gbc.gridx = 0;
        gbc.gridy = 3;
        expenseLabel = new JLabel("Enter Expense:");
        panel.add(expenseLabel, gbc);
        gbc.gridx = 1;
        expenseField = new JTextField();
        panel.add(expenseField, gbc);
        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(e -> addExpenseAction());
        gbc.gridy = 4;
        panel.add(addExpenseButton, gbc);

        return panel;
    }

    /**
     * Creates the budget display area.
     *
     * @return The JTextArea for budget display.
     */
    private JTextArea createBudgetDisplayArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setBorder(new TitledBorder("Budget Details"));
        return area;
    }

    /**
     * Returns an array of month names.
     *
     * @return Array of months.
     */
    private String[] getMonths() {
        return new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
    }

    /**
     * Handles the action of adding income.
     */
    private void addIncomeAction() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.", "Authentication Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            BigDecimal income = parseAmount(incomeField.getText());
            int monthIndex = getSelectedMonthIndex();
            budget.addIncome(monthIndex, "Income", income);
            // Add transaction to user
            Transaction transaction = new Transaction.Builder()
                    .setDescription("Income for " + getMonths()[monthIndex - 1])
                    .setAmount(income)
                    .setCategory("Income")
                    .setType(TransactionType.INCOME)
                    .build();
            userService.addTransactionToUser(currentUser.getUsername(), transaction);
            JOptionPane.showMessageDialog(this, "Income added: $" + income, "Success", JOptionPane.INFORMATION_MESSAGE);
            incomeField.setText("");
            updateBudgetDisplay();
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
        // Removed BudgetExceededException catch block as it is never thrown here
    }

    /**
     * Handles the action of adding an expense.
     */
    private void addExpenseAction() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.", "Authentication Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            BigDecimal expense = parseAmount(expenseField.getText());
            int monthIndex = getSelectedMonthIndex();
            budget.addExpense(monthIndex, "Expense", expense);
            // Add transaction to user
            Transaction transaction = new Transaction.Builder()
                    .setDescription("Expense for " + getMonths()[monthIndex - 1])
                    .setAmount(expense)
                    .setCategory("Expense")
                    .setType(TransactionType.EXPENSE)
                    .build();
            userService.addTransactionToUser(currentUser.getUsername(), transaction);
            JOptionPane.showMessageDialog(this, "Expense added: $" + expense, "Success", JOptionPane.INFORMATION_MESSAGE);
            expenseField.setText("");
            updateBudgetDisplay();
        } catch (InvalidInputException | BudgetExceededException ex) { // Updated to multicatch
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Parses a string to BigDecimal.
     *
     * @param text The string to parse.
     * @return The parsed BigDecimal.
     * @throws InvalidInputException if parsing fails or the amount is negative.
     */
    private BigDecimal parseAmount(String text) throws InvalidInputException {
        try {
            BigDecimal amount = new BigDecimal(text.trim());
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvalidInputException("Negative values are not allowed.");
            }
            return amount;
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Please enter a valid positive number.");
        }
    }

    /**
     * Gets the selected month index from the combo box.
     *
     * @return The month index (1-based).
     * @throws InvalidInputException if no month is selected.
     */
    private int getSelectedMonthIndex() throws InvalidInputException {
        int index = monthComboBox.getSelectedIndex();
        if (index < 0) {
            throw new InvalidInputException("Please select a month.");
        }
        return index + 1;
    }

    /**
     * Updates the budget display area with current budget details.
     */
    private void updateBudgetDisplay() {
        if (budget == null) {
            budgetDisplayArea.setText("No budget data available.");
            balanceLabel.setText("Total Balance: $0.00");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Budget Name: ").append(budget.getName()).append("\n");
        sb.append("Entity ID: ").append(budget.getEntityId()).append("\n\n");
        sb.append(String.format("%-15s %-15s %-15s %-15s%n", "Month", "Income", "Expenses", "Balance"));
        sb.append("---------------------------------------------------------------\n");

        for (int i = 1; i <= 12; i++) {
            try {
                BigDecimal income = budget.getMonthlyIncome(i);
                BigDecimal expenses = budget.getMonthlyExpenses(i);
                BigDecimal balance = income.subtract(expenses);
                sb.append(String.format("%-15s $%-14.2f $%-14.2f $%-14.2f%n",
                        getMonths()[i - 1],
                        income,
                        expenses,
                        balance));
            } catch (InvalidInputException e) {
                sb.append(String.format("%-15s %-15s %-15s %-15s%n",
                        getMonths()[i - 1],
                        "Error",
                        "Error",
                        "Error"));
            }
        }

        budgetDisplayArea.setText(sb.toString());
        balanceLabel.setText("Total Balance: $" + budget.calculateBalance().setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Saves the current budget data to a file.
     */
    private void saveData() {
        if (currentUser == null || budget == null) {
            JOptionPane.showMessageDialog(this, "No data to save.", "Save Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FileManager fileManager = new FileManager("budget_" + currentUser.getUsername() + ".dat");
            fileManager.saveBudget(budget);
            JOptionPane.showMessageDialog(this, "Data saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error saving data", ex);
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads the budget data from a file.
     */
    private void loadData() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.", "Authentication Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            FileManager fileManager = new FileManager("budget_" + currentUser.getUsername() + ".dat");
            Budget loadedBudget = fileManager.loadBudget();
            if (loadedBudget != null) {
                this.budget = loadedBudget;
                JOptionPane.showMessageDialog(this, "Data loaded successfully.", "Load", JOptionPane.INFORMATION_MESSAGE);
            } else {
                this.budget = new Budget("B001", "Personal Budget", BigDecimal.ZERO);
                JOptionPane.showMessageDialog(this, "No existing data found. Initialized new budget.", "Load", JOptionPane.INFORMATION_MESSAGE);
            }
            updateBudgetDisplay();
        } catch (IOException | ClassNotFoundException | InvalidInputException ex) { // Updated to multicatch
            LOGGER.log(Level.SEVERE, "Error loading data", ex);
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the About dialog.
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Personal Finance Management System\nVersion 1.0\nDeveloped by Seth AKA R0ice_1", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the login dialog for user authentication.
     */
    private void showLoginDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (userService.validateCredentials(username, password)) {
                currentUser = userService.getUser(username);
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                logoutItem.setEnabled(true);
                loginItem.setEnabled(false);
                registerItem.setEnabled(false);
                if (currentUser.getRole() == UserRole.ADMIN) {
                    manageUsersItem.setEnabled(true);
                }
                initializeBudget();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Displays the registration dialog for new users.
     */
    private void showRegisterDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField budgetGoalsField = new JTextField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"REGULAR_USER", "ADMIN"});

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Budget Goals:"));
        panel.add(budgetGoalsField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String email = emailField.getText().trim();
            String budgetGoalsText = budgetGoalsField.getText().trim();
            String roleText = (String) roleComboBox.getSelectedItem();

            try {
                BigDecimal budgetGoals = new BigDecimal(budgetGoalsText);
                if (budgetGoals.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvalidInputException("Budget goals must be non-negative.");
                }
                UserRole role = UserRole.valueOf(roleText);
                userService.registerUser(username, password, email, budgetGoals, role);
                JOptionPane.showMessageDialog(this, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException | InvalidInputException ex) { // Updated to multicatch
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Logs out the current user and resets the UI components.
     */
    private void logoutUser() {
        currentUser = null;
        budget = null;
        budgetDisplayArea.setText("");
        balanceLabel.setText("Total Balance: $0.00");
        logoutItem.setEnabled(false);
        loginItem.setEnabled(true);
        registerItem.setEnabled(true);
        manageUsersItem.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Initializes the budget by loading from file or creating a new one.
     */
    private void initializeBudget() {
        // Load budget from file or initialize new
        try {
            FileManager fileManager = new FileManager("budget_" + currentUser.getUsername() + ".dat");
            Budget loadedBudget = fileManager.loadBudget();
            if (loadedBudget != null) {
                this.budget = loadedBudget;
                LOGGER.log(Level.INFO, "Budget loaded for user: {0}", currentUser.getUsername());
            } else {
                this.budget = new Budget("B001", "Personal Budget", BigDecimal.ZERO);
                LOGGER.log(Level.INFO, "Initialized new budget for user: {0}", currentUser.getUsername());
            }
            updateBudgetDisplay();
        } catch (IOException | ClassNotFoundException | InvalidInputException ex) { // Updated to multicatch
            LOGGER.log(Level.SEVERE, "Error initializing budget", ex);
            JOptionPane.showMessageDialog(this, "Error initializing budget: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sets the look and feel of the application to Nimbus if available.
     */
    private void setLookAndFeel() {
        try {
            // Set Nimbus Look and Feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Failed to set look and feel", ex);
        }
    }

    /**
     * Displays the Manage Users dialog for admin users.
     */
    private void showManageUsersDialog() {
        // Only accessible by admins
        if (currentUser == null || currentUser.getRole() != UserRole.ADMIN) {
            JOptionPane.showMessageDialog(this, "Access Denied.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Display a dialog with user management options
        // For simplicity, list all users
        Map<String, User> allUsers = userService.listAllUsers();
        StringBuilder userList = new StringBuilder();
        allUsers.values().forEach(user -> {
            userList.append("Username: ").append(user.getUsername())
                    .append(", Email: ").append(user.getEmail())
                    .append(", Role: ").append(user.getRole()).append("\n");
        });

        JTextArea textArea = new JTextArea(userList.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Manage Users", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The main method to launch the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}