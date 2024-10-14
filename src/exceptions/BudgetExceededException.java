/*
 * File: BudgetExceededException.java
 * Description: Custom exception to handle budget limit exceed cases.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */
package exceptions;

/**
 * Exception thrown when a budget limit is exceeded.
 */
public class BudgetExceededException extends Exception {
    public BudgetExceededException(String message) {
        super(message);
    }
}