package services;

public class UserAuthentication {
    public boolean authenticate(String email, String password) {
        // Placeholder authentication logic
        if (email.equals("user@example.com") && password.equals("password123")) {
            return true;
        }
        return false;
    }
}
