package exceptions;

/**
 * Exception thrown when a budget limit is exceeded.
 */
public class BudgetExceededException extends Exception {
    public BudgetExceededException(String message) {
        super(message);
    }
}
