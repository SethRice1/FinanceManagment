/*
 * File: BudgetExceededException.java
 * Description: Exception thrown when a budget limit is exceeded.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package exceptions;

 /**
  * Exception thrown to indicate that a budget limit has been exceeded.
  * 
  * <p>
  * This exception is typically thrown when an attempt is made to add an expense 
  * that surpasses the allocated budget for a specific month.
  * </p>
  * 
  * <p>
  * <b>Example Usage:</b>
  * <pre>{@code
  * if (expenseAmount.compareTo(budgetLimit) > 0) {
  *     throw new BudgetExceededException("Expense exceeds the monthly budget limit.");
  * }
  * }</pre>
  * </p>
  * 
  * @see Budget
  * @see InvalidInputException
  */
 public class BudgetExceededException extends Exception {
 
     /**
      * Constructs a new BudgetExceededException with the specified detail message.
      *
      * @param message The detail message explaining the reason for the exception.
      */
     public BudgetExceededException(String message) {
         super(message);
     }
 }
 