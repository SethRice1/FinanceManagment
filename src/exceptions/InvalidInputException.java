/*
 * File: InvalidInputException.java
 * Description: Custom exception to handle invalid user input cases.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */
package exceptions;

/**
 * Exception thrown when an invalid input is encountered.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}