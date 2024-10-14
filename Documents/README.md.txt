# Personal Finance Management System

A comprehensive tool to manage budgets, track expenses, and improve financial habits using Java.

## Overview
Managing finances can be overwhelming. For example, tracking multiple expenses manually can lead to errors and missed opportunities to save. The **Personal Finance Management System** provides an intuitive Java-based platform to track spending, manage budgets, and gain financial insights. Unlike other tools, it features a custom linked list implementation for efficient data handling. The user-friendly GUI is built with Java Swing for ease of use and flexibility in creating desktop applications. Robust file I/O provides effective data persistence, making it a unique solution for finance management. This system's user-friendly GUI and expense tracking features help users stay organized, while reliable budget management is ensured through data persistence with file I/O.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Step-by-Step User Guide](#step-by-step-user-guide)
- [Classes Overview](#classes-overview)
- [File I/O Information](#file-i-o-information)
- [Running the Program](#running-the-program)
- [Screenshots](#screenshots)
- [Objectives and Purpose](#objectives-and-purpose)
- [License](#license)

## Features
- **User Registration and Authentication**: Secure login mechanism with password hashing.
- **Budget Management**: Track income, expenses, and savings effortlessly.
- **Custom Linked List Implementation**: Efficient handling of data using a custom linked list.
- **User-Friendly GUI**: Built using Java Swing components like `JFrame`, `JLabel`, and `JButton` for an interactive experience due to their ease of use and flexibility in creating desktop applications.
- **Exception Handling**: Custom exceptions for common budget management issues (e.g., `BudgetExceededException`, `InvalidInputException`).
- **File I/O Support**: Save and load budget data through file operations.

## Getting Started
To begin tracking your expenses and creating budgets effectively, follow the installation and setup steps provided. Proper configuration, including setting up environment variables, installing necessary dependencies, and configuring file paths, ensures optimal performance.

- **Estimated Setup Time**: Approximately 10-15 minutes.

## System Requirements
- **Java Development Kit (JDK)**: Version 11 or later.
- **Integrated Development Environment (IDE)**: VS Code, IntelliJ IDEA, Eclipse, or JGrasp.

## Installation
1. **Clone the Repository**
   ```sh
   git clone [REPOSITORY_URL] # Replace [REPOSITORY_URL] with the actual URL of the repository
   ```
2. **Navigate to the Project Directory**
   ```sh
   cd PersonalFinanceManagementSystem/Final/src
   ```
3. **Compile the Java Files**
   ```sh
   javac exceptions/*.java gui/*.java models/*.java services/*.java utils/*.java
   ```

## Usage
To run the program:
```sh
java gui.MainGUI
```
Note: Ensure you are in the correct directory (`src`) before executing this command to avoid path-related issues. The GUI will launch, providing an easy and interactive experience. Users can log in, add income, track expenses, and view budget summaries.

## Step-by-Step User Guide
1. **Launch the Application**: Compile the files and run `MainGUI.java` using your IDE or terminal. To ensure successful compilation, check that there are no error messages in the terminal or console output. If you encounter issues, ensure that all dependencies are correctly installed, and verify that file paths are accurate.
2. **Login or Register**: Enter credentials to access the main dashboard. Secure password handling is implemented for safety.
3. **Add Income and Expenses**: Use the dashboard to add income, expenses, and visualize your budget balance.
4. **Transaction Management**: Add, view, edit, and delete transactions.
5. **Save/Load Budget**: Save or load your budget using `FileManager.java`. Budget data is automatically stored in `budget.dat`, while individual transactions are saved in `transactions.csv` by the application. These files are saved in the project's root directory by default. If you want to use a custom directory, modify the file paths in `FileManager.java` accordingly, and ensure the specified directories exist.
6. **Logout and Exit**: Click the logout button in the top right corner to return to the login screen or exit the application.

## Classes Overview
### Exceptions
- **BudgetExceededException**: Thrown when expenses exceed the budget.
- **InvalidInputException**: Handles invalid user input.

### Models
- **Budget**: Represents income, expenses, and savings.
- **Transaction**: Handles individual transactions.
- **User**: Represents users with authentication details.

### GUI
- **MainGUI**: Main graphical user interface for the application.

### Services
- **FileManager**: Manages saving/loading data from files.
- **UserService**: Provides user-related operations like login.

### Utilities
- **LinkedList**: Custom linked list implementation.

## File I/O Information
- **budget.dat**: Stores budget information.
- **transactions.csv**: Stores transaction details.
- **File Paths**: Ensure correct paths are set in `FileManager.java` for proper file operations. For example, update the file paths in the `FileManager` class to point to the desired directory on your system, such as `C:/data/budget.dat` or `/home/user/data/transactions.csv`. Incorrect file paths can lead to runtime errors, so verify the paths after updates to avoid such issues.

## Running the Program
The project can be run using an IDE like **VS Code** or **IntelliJ IDEA** for features like integrated debugging tools, ease of navigation, and easier file path management, which is especially helpful for beginners. Alternatively, use the command line with `javac` and `java` for compiling and running the application.

Example command line run:
```sh
javac gui/MainGUI.java
java gui.MainGUI
```

## Screenshots
Include screenshots showing the login screen, dashboard, budget overview, and transaction history. Place these screenshots after this section to help users visually locate each part of the application. Example screenshots can be found in the 'docs/screenshots' folder or through links provided in the repository's README. Use consistent file formats such as PNG or JPEG for better quality and uniformity. The recommended resolution for screenshots is at least 1280x720 to ensure they are clear and readable on all devices, including higher-resolution displays.

## Objectives and Purpose
The **Personal Finance Management System** is intended to help users:
- Gain control over their finances.
- Track income and spending efficiently.
- Improve financial habits through detailed tracking and insights.

## License
This project is licensed under the MIT License. Users can use, modify, and distribute the code, provided the original license text is included in any derivative works.

For more details, see the `LICENSE` file in the repository.