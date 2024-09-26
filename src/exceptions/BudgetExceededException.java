package exceptions;

/**
 * Custom exception class for handling scenarios where a user's budget is exceeded.
 * This exception is thrown when a transaction or series of transactions would cause
 * the user to go over their predefined budget limit.
 */
public class BudgetExceededException extends Exception {
    
    /**
     * Constructs a new BudgetExceededException with the specified detail message.
     * 
     * @param message A description of the budget exceedance that caused the exception.
     *                This message should typically include details such as the budget limit,
     *                the amount by which it was exceeded, and potentially the category of spending.
     *                The message can be retrieved later by the Throwable.getMessage() method.
     */
    public BudgetExceededException(String message) {
        super(message);
    }

    /**
     * Constructs a new BudgetExceededException with a default message.
     * This constructor can be used when a specific message is not necessary.
     */
    public BudgetExceededException() {
        super("Budget limit has been exceeded.");
    }

    /**
     * Constructs a new BudgetExceededException with the specified detail message and cause.
     * 
     * @param message A description of the budget exceedance that caused the exception.
     * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
     *              A null value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public BudgetExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
