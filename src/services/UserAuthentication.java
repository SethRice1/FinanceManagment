package services;

import java.util.Scanner;

public class UserAuthentication {
    public boolean authenticate(String username, String password) {
        String userType;

        // Use scanner to get userType from input
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter user type (admin/user): ");
            userType = scanner.nextLine().trim().toLowerCase();
        }

        // Using switch expressions (Java 14+)
        boolean isAuthenticated = switch (userType) {
            case "admin" -> {
                // Admin authentication logic
                System.out.println("Authenticating admin: " + username);
                // Implement actual admin authentication here
                yield username.equals("admin") && password.equals("adminpass");
            }
            case "user" -> {
                // Regular user authentication logic
                System.out.println("Authenticating user: " + username);
                // Implement actual user authentication here
                yield username.equals("user") && password.equals("userpass");
            }
            default -> {
                System.out.println("Unknown user type: " + userType);
                yield false;
            }
        };

        return isAuthenticated;
    }
}