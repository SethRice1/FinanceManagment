package exceptions;

// Exception Class - BudgetExceededException.java

public class BudgetExceededException extends Exception {
    public BudgetExceededException(String message) {
        super(message);
    }
}