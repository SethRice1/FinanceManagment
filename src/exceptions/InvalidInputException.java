package exceptions;

/**
 * Custom exception class for handling invalid input scenarios in the application.
 * This exception is thrown when user input or data does not meet the expected criteria.
 */
public class InvalidInputException extends Exception {
    
    /**
     * Constructs a new InvalidInputException with the specified detail message.
     * 
     * @param message A description of the invalid input that caused the exception.
     *                This message can be retrieved later by the Throwable.getMessage() method.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
