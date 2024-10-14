/*
 * File: InvalidInputException.java
 * Description: Exception thrown when an invalid input is encountered.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package exceptions;

 /**
  * Exception thrown to indicate that an invalid input has been encountered.
  * 
  * <p>
  * This exception is used to handle cases where user input does not meet the required criteria,
  * such as empty fields, incorrect data formats, or out-of-range values.
  * </p>
  * 
  * <p>
  * <b>Example Usage:</b>
  * <pre>{@code
  * if (input == null || input.isEmpty()) {
  *     throw new InvalidInputException("Input cannot be null or empty.");
  * }
  * }</pre>
  * </p>
  * 
  * @see BudgetExceededException
  */
 public class InvalidInputException extends Exception {
 
     /**
      * Constructs a new InvalidInputException with the specified detail message.
      *
      * @param message The detail message explaining the reason for the exception.
      */
     public InvalidInputException(String message) {
         super(message);
     }
 }
 