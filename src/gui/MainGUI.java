/*
 * File: MainGUI.java
 * Description: Main graphical user interface class for the Personal Finance Management System.
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
 
 /**
  * MainGUI class is responsible for creating and managing the graphical user interface
  * for the Personal Finance Management System.
  * 
  * <p>
  * This class extends {@link JFrame} and incorporates various Swing components to provide
  * functionalities such as adding incomes and expenses, viewing budget details, user
  * authentication, and administrative tasks.
  * </p>
  * 
  * <p>
  * <b>Key Features:</b>
  * <ul>
  *     <li>User Authentication: Login and registration system.</li>
  *     <li>Transaction Management: Add and view incomes and expenses.</li>
  *     <li>Budget Display: View detailed budget summaries and total balance.</li>
  *     <li>Data Persistence: Save and load budget data.</li>
  *     <li>Administrative Controls: Manage user accounts (for admins).</li>
  * </ul>
  * </p>
  * 
  * @see Budget
  * @see UserService
  * @see FileManager
  */
 public class MainGUI extends JFrame {
     private static final Logger LOGGER = Logger.getLogger(MainGUI.class.getName());
 
     // UI Components
     private JLabel incomeLabel;
     private JTextField incomeField;
     private JButton addIncomeButton;
     private JLabel expenseLabel;
     private JTextField expenseField;
     private JButton addExpenseButton;
     private JLabel balanceLabel;
     private JTextArea budgetDisplayArea;
     private JComboBox<String> monthComboBox;
     
     // Data Models
     private Budget budget;
     private final UserService userService;
     private User currentUser;
 
     // Menu Components
     private JMenuBar menuBar;
     private JMenu fileMenu;
     private JMenu helpMenu;
     private JMenu userMenu;
     private JMenuItem saveItem, loadItem, exitItem, aboutItem, loginItem, registerItem, logoutItem, manageUsersItem;
 
     /**
      * Constructor to initialize the MainGUI.
      * 
      * <p>
      * Sets up the user interface components, configures the frame properties,
      * and applies the desired look and feel.
      * </p>
      */
     public MainGUI() {
         userService = new UserService();
         initializeUI();
         setLookAndFeel();
         setTitle("Personal Finance Management System");
         setSize(800, 600);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
     }
 
     /**
      * Initializes the user interface components and layout.
      * 
      * <p>
      * This method sets up the menu bar, input panels, budget display area, 
      * and balance label. It organizes these components within the main panel.
      * </p>
      */
     private void initializeUI() {
         // Create Menu Bar
         menuBar = new JMenuBar();
 
         // File Menu Setup
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
 
         // User Menu Setup
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
 
         // Help Menu Setup
         helpMenu = new JMenu("Help");
         aboutItem = new JMenuItem("About");
         aboutItem.addActionListener(e -> showAboutDialog());
         helpMenu.add(aboutItem);
 
         // Add Menus to Menu Bar
         menuBar.add(fileMenu);
         menuBar.add(userMenu);
         menuBar.add(helpMenu);
         setJMenuBar(menuBar);
 
         // Create Main Panel with BorderLayout
         JPanel mainPanel = new JPanel(new BorderLayout());
         mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
 
         // Input Panel for Adding Transactions
         mainPanel.add(createInputPanel(), BorderLayout.NORTH);
 
         // Budget Display Area in the Center
         budgetDisplayArea = createBudgetDisplayArea();
         mainPanel.add(new JScrollPane(budgetDisplayArea), BorderLayout.CENTER);
 
         // Balance Label at the Bottom
         balanceLabel = new JLabel("Total Balance: $0.00");
         balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
         mainPanel.add(balanceLabel, BorderLayout.SOUTH);
 
         add(mainPanel);
     }
 
     /**
      * Creates the input panel for adding income and expenses.
      * 
      * <p>
      * This panel includes fields and buttons for users to input income and expense
      * amounts, as well as select the corresponding month.
      * </p>
      * 
      * @return The configured input JPanel.
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
      * <p>
      * This text area displays detailed budget information, including monthly incomes,
      * expenses, and balances. It is set to be non-editable and uses a monospaced font
      * for better readability.
      * </p>
      * 
      * @return The configured JTextArea for budget display.
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
      * @return Array of month names from January to December.
      */
     private String[] getMonths() {
         return new String[]{
             "January", "February", "March", "April", "May", "June",
             "July", "August", "September", "October", "November", "December"
         };
     }
 
     /**
      * Handles the action of adding income based on user input.
      * 
      * <p>
      * This method performs the following steps:
      * <ol>
      *     <li>Checks if a user is currently logged in.</li>
      *     <li>Parses and validates the income amount entered by the user.</li>
      *     <li>Adds the income to the budget for the selected month.</li>
      *     <li>Creates and records a new Transaction object for the income.</li>
      *     <li>Updates the budget display and resets the input field.</li>
      * </ol>
      * </p>
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
             // Create and add transaction to user
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
         // Removed BudgetExceededException catch block as it is not applicable here
     }
 
     /**
      * Handles the action of adding an expense based on user input.
      * 
      * <p>
      * This method performs the following steps:
      * <ol>
      *     <li>Checks if a user is currently logged in.</li>
      *     <li>Parses and validates the expense amount entered by the user.</li>
      *     <li>Attempts to add the expense to the budget for the selected month.</li>
      *     <li>Creates and records a new Transaction object for the expense.</li>
      *     <li>Updates the budget display and resets the input field.</li>
      * </ol>
      * </p>
      * 
      * <p>
      * <b>Exception Handling:</b>
      * <ul>
      *     <li><code>InvalidInputException</code>: Thrown if the input amount is invalid or if no month is selected.</li>
      *     <li><code>BudgetExceededException</code>: Thrown if adding the expense exceeds the budget for the month.</li>
      * </ul>
      * </p>
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
             // Create and add transaction to user
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
         } catch (InvalidInputException | BudgetExceededException ex) { // Multicatch for both exceptions
             JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
     }
 
     /**
      * Parses a string input to a BigDecimal amount.
      * 
      * <p>
      * This method trims the input string and attempts to convert it to a 
      * BigDecimal. It ensures that the amount is non-negative.
      * </p>
      * 
      * @param text The input string representing the amount.
      * @return The parsed BigDecimal amount.
      * @throws InvalidInputException If the input is not a valid positive number.
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
      * Retrieves the selected month index from the combo box.
      * 
      * @return The month index (1-based, where 1 represents January).
      * @throws InvalidInputException If no month is selected.
      */
     private int getSelectedMonthIndex() throws InvalidInputException {
         int index = monthComboBox.getSelectedIndex();
         if (index < 0) {
             throw new InvalidInputException("Please select a month.");
         }
         return index + 1;
     }
 
     /**
      * Updates the budget display area with the latest budget details.
      * 
      * <p>
      * This method refreshes the budget information displayed to the user,
      * including incomes, expenses, and balances for each month.
      * </p>
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
      * 
      * <p>
      * This method serializes the {@link Budget} object and saves it using the 
      * {@link FileManager} service. The file is named based on the current user's username.
      * </p>
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
      * 
      * <p>
      * This method deserializes the {@link Budget} object using the 
      * {@link FileManager} service. If no existing data is found, a new budget is initialized.
      * </p>
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
         } catch (IOException | ClassNotFoundException | InvalidInputException ex) { // Multicatch for multiple exceptions
             LOGGER.log(Level.SEVERE, "Error loading data", ex);
             JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
         }
     }
 
     /**
      * Displays the About dialog with application information.
      */
     private void showAboutDialog() {
         JOptionPane.showMessageDialog(this, "Personal Finance Management System\nVersion 1.0\nDeveloped by Seth AKA R0ice_1", "About", JOptionPane.INFORMATION_MESSAGE);
     }
 
     /**
      * Displays the login dialog for user authentication.
      * 
      * <p>
      * This method prompts the user to enter their username and password.
      * Upon successful authentication, it initializes the budget and updates UI components accordingly.
      * </p>
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
      * 
      * <p>
      * This method allows users to create a new account by providing necessary details
      * such as username, password, email, budget goals, and role.
      * </p>
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
             } catch (NumberFormatException | InvalidInputException ex) { // Multicatch for multiple exceptions
                 JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
             }
         }
     }
 
     /**
      * Logs out the current user and resets the UI components.
      * 
      * <p>
      * This method clears the current user session, resets budget data,
      * and updates the UI to reflect the logged-out state.
      * </p>
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
      * Initializes the budget by loading from a file or creating a new one.
      * 
      * <p>
      * This method attempts to load existing budget data for the current user.
      * If no data is found, it initializes a new Budget object with default values.
      * </p>
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
         } catch (IOException | ClassNotFoundException | InvalidInputException ex) { // Multicatch for multiple exceptions
             LOGGER.log(Level.SEVERE, "Error initializing budget", ex);
             JOptionPane.showMessageDialog(this, "Error initializing budget: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
     }
 
     /**
      * Sets the look and feel of the application to Nimbus if available.
      * 
      * <p>
      * This method iterates through the installed Look and Feels and applies
      * Nimbus for a modern and sleek user interface. If Nimbus is not available,
      * the default Look and Feel is used.
      * </p>
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
      * 
      * <p>
      * This method is accessible only to users with ADMIN privileges. It lists
      * all registered users and their details in a scrollable text area.
      * </p>
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
      * <p>
      * This method initializes the {@link MainGUI} on the Event Dispatch Thread (EDT)
      * to ensure thread safety with Swing components.
      * </p>
      *
      * @param args Command-line arguments (not used).
      */
     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             MainGUI gui = new MainGUI();
             gui.setVisible(true);
         });
     }
 }
 