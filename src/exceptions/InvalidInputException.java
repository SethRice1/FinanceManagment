package exceptions;

/**
 * Exception thrown when an invalid input is encountered.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
