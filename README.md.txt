Personal Finance Management System - Term Project Proposal

Introduction

Managing personal finances effectively is crucial for achieving financial stability and meeting long-term goals. The Personal Finance Management System is a comprehensive Java-based application designed to help users track their income and expenses, maintain budgets, and gain insightful visualizations of their financial activities. This system allows users to store transaction records, categorize expenses, and provides a clear comparison between actual spending and budgeted goals. With a user-friendly GUI, the system is built using fundamental programming concepts such as selection, looping, arrays, file I/O, and exception handling—offering an efficient and intuitive platform for personal financial management.

Key Features

1. Object-Oriented Programming

The system is built using object-oriented principles to ensure modularity, reusability, and maintainability.

**Transaction Class**

- **Purpose**: Manages details of individual financial transactions, including income and expenses.
- **Attributes**: Transaction ID, amount, date, category, description.
- **Methods**: Getters and setters, transaction validation, categorization.

**User Class**

- **Purpose**: Stores and manages user information and budget goals.
- **Attributes**: User ID, name, email, password, budget goals.
- **Methods**: User authentication, profile management, budget setting.

**Budget Class**

- **Purpose**: Calculates and monitors the remaining budget based on total expenses.
- **Attributes**: Total budget, total expenses, remaining budget.
- **Methods**: Budget calculations, budget updates, budget alerts.

**UserTransaction Class**

- **Purpose**: Manages financial transactions for individual users.
- **Attributes**: List of transactions, user reference.
- **Methods**: Add transaction, get total expenses, list transactions.

**TransactionService Class**

- **Purpose**: Handles the management of all transactions, including storage and retrieval.
- **Attributes**: List of all transactions.
- **Methods**: Add transaction, get transactions by category, calculate total expenses.

**FileManager Class**

- **Purpose**: Provides file handling capabilities for saving and loading transactions.
- **Methods**: Save transactions, load transactions.

**Utility Class**

- **Purpose**: Contains utility methods to support other classes.
- **Methods**: Rounding to two decimal places, email validation.

**UserAuthentication Class**

- **Purpose**: Handles user login and authentication.
- **Methods**: Authenticate user by email and password.

**MainGUI Class**

- **Purpose**: Provides the main graphical user interface for interacting with the Personal Finance Management System.
- **Features**: Allows users to input income, add expenses, and get visual feedback on their financial status.

**TransactionType Enum**

- **Purpose**: Defines the different types of transactions (e.g., INCOME, EXPENSE).

Project Requirements Compliance

**Chapter 1**: Basic procedural programming with no syntax errors has been accomplished throughout the project, ensuring the code is error-free.

**Chapter 2**: Proper use of variables and major types such as integer, double, Boolean, and the final keyword has been implemented. Print and println methods are used along with GUI dialog boxes, arithmetic statements, assignment statements, and keyboard/GUI input.

**Chapter 3**: The use of getters and setters, methods, parameters, arguments, return statements, classes, static and non-static methods, instance methods, fields, and constructors are all demonstrated in the Transaction, User, and Budget classes.

**Chapter 4**: Appropriate use of scope, constructors (including overloading), the `this` reference, static fields, and constants has been demonstrated. Classes such as nested or inner classes are utilized where needed.

**Chapter 5**: Nested if statements are employed where appropriate to enhance decision-making capabilities within the code.

**Chapter 6**: Looping, including `for` and `while` loops, as well as nested looping, is used extensively for tasks such as budget calculations and transaction input.

**Chapter 7**: String operations, such as comparisons with `equals()`, using `length()`, and converting strings to numbers, are utilized to handle user inputs and categorize transactions.

**Chapter 8**: Arrays and looping are used to create and access data structures, with multi-dimensional arrays employed to manage more complex data.

**Chapter 9**: Inheritance and the use of abstract classes are implemented to promote code reuse and maintainability.

**Chapter 10**: Try-catch blocks are used extensively throughout the application to manage exceptions and enhance robustness.

**Chapter 11**: File I/O operations are implemented to manage transaction records and user profile data.

**Chapter 12**: Recursion is not included, as it is not a requirement for this particular project.

**Chapter 13**: Linked lists and generic methods are incorporated to handle dynamic data structures and ensure flexibility.

**Chapter 14**: The GUI includes button event listeners, checkboxes, and option buttons, using JFrame, JLabel, and other Swing components to create an interactive interface.

Internal Documentation

Comprehensive internal documentation, including detailed class descriptions, method explanations, and variable roles, is integrated into the codebase to facilitate understanding and maintenance. Key aspects include:

- **Class Documentation**: Detailed descriptions of each class, including purpose, attributes, and methods.
- **Method Comments**: Explanations of method functionalities, input parameters, and return values.
- **Variable Descriptions**: Clear descriptions of variables and their roles within the program.
- **Routine Explanations**: Insights into complex algorithms and logic implementations, particularly within budget calculations and data processing modules.

User Guide

A detailed user guide will assist users in navigating and utilizing the Personal Finance Management System effectively. The guide covers:

- **Getting Started**: Installation instructions and initial setup procedures.
- **Adding Transactions**: Step-by-step instructions for inputting income and expenses.
- **Setting Budget Goals**: Guidelines for establishing and adjusting budgetary limits.
- **Generating Reports**: Instructions for accessing and interpreting financial reports.
- **Navigating the Interface**: Overview of the GUI components and their functionalities.
- **Troubleshooting**: Solutions to common issues and FAQs.