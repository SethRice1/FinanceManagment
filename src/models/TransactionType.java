/*
 * File: TransactionType.java
 * Description: Enumeration of different types of financial transactions.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package models;

 /**
  * Enumeration representing the type of a financial transaction.
  * 
  * <p>
  * {@code TransactionType} defines two possible types for transactions:
  * {@code INCOME} and {@code EXPENSE}. This helps in categorizing and processing
  * transactions accordingly within the application.
  * </p>
  * 
  * <p>
  * <b>Usage Example:</b>
  * <pre>{@code
  * Transaction transaction = new Transaction.Builder()
  *     .setDescription("Salary")
  *     .setCategory("Job")
  *     .setAmount(new BigDecimal("5000"))
  *     .setType(TransactionType.INCOME)
  *     .build();
  * }</pre>
  * </p>
  */
 public enum TransactionType {
     /**
      * Represents an income transaction.
      */
     INCOME,
 
     /**
      * Represents an expense transaction.
      */
     EXPENSE
 }
 