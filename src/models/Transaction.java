/*
 * File: Transaction.java
 * Description: Represents a financial transaction.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package models;

 import java.math.BigDecimal;
 import java.util.UUID;
 
 /**
  * Represents a financial transaction, either an income or an expense.
  * 
  * <p>
  * The Transaction class encapsulates details of a financial transaction, including its
  * unique identifier, description, category, amount, and type (income or expense).
  * It utilizes the Builder pattern to facilitate the creation of Transaction instances.
  * </p>
  * 
  * <p>
  * <b>Key Attributes:</b>
  * <ul>
  *     <li><b>transactionId:</b> A unique identifier generated for each transaction.</li>
  *     <li><b>description:</b> A brief description of the transaction.</li>
  *     <li><b>category:</b> The category under which the transaction falls (e.g., "Food", "Salary").</li>
  *     <li><b>amount:</b> The monetary value of the transaction.</li>
  *     <li><b>type:</b> The type of transaction, either INCOME or EXPENSE.</li>
  * </ul>
  * </p>
  * 
  * <p>
  * <b>Builder Pattern:</b> The Transaction class uses a static nested Builder class to
  * create Transaction instances. This approach enhances code readability and allows
  * for flexible object construction.
  * </p>
  * 
  * @see TransactionType
  * @see Builder
  */
 public class Transaction extends FinancialEntity {
     private static final long serialVersionUID = 1L;
 
     // Unique identifier for the transaction
     private final String transactionId;
     // Description of the transaction
     private final String description;
     // Category of the transaction (e.g., "Food", "Salary")
     private final String category;
     // Amount of the transaction
     private final BigDecimal amount;
     // Type of the transaction: INCOME or EXPENSE
     private final TransactionType type;
 
     /**
      * Private constructor to enforce the use of Builder for object creation.
      * 
      * @param builder The Builder instance containing the transaction details.
      */
     private Transaction(Builder builder) {
         this.transactionId = UUID.randomUUID().toString();
         this.description = builder.description;
         this.category = builder.category;
         this.amount = builder.amount;
         this.type = builder.type;
     }
 
     // Getter methods
 
     /**
      * Retrieves the unique identifier of the transaction.
      * 
      * @return The transaction ID.
      */
     public String getTransactionId() {
         return transactionId;
     }
 
     /**
      * Retrieves the description of the transaction.
      * 
      * @return The transaction description.
      */
     public String getDescription() {
         return description;
     }
 
     /**
      * Retrieves the category of the transaction.
      * 
      * @return The transaction category.
      */
     public String getCategory() {
         return category;
     }
 
     /**
      * Retrieves the amount of the transaction.
      * 
      * @return The transaction amount.
      */
     public BigDecimal getAmount() {
         return amount;
     }
 
     /**
      * Retrieves the type of the transaction (INCOME or EXPENSE).
      * 
      * @return The transaction type.
      */
     public TransactionType getType() {
         return type;
     }
 
     /**
      * Builder class for constructing Transaction instances.
      */
     public static class Builder {
         private String description;
         private String category;
         private BigDecimal amount;
         private TransactionType type;
 
         /**
          * Sets the description for the transaction.
          * 
          * @param description A brief description of the transaction.
          * @return The current Builder instance.
          * @throws IllegalArgumentException If the description is null or empty.
          */
         public Builder setDescription(String description) {
             if (description == null || description.isEmpty()) {
                 throw new IllegalArgumentException("Description cannot be null or empty.");
             }
             this.description = description;
             return this;
         }
 
         /**
          * Sets the category for the transaction.
          * 
          * @param category The category under which the transaction falls.
          * @return The current Builder instance.
          * @throws IllegalArgumentException If the category is null or empty.
          */
         public Builder setCategory(String category) {
             if (category == null || category.isEmpty()) {
                 throw new IllegalArgumentException("Category cannot be null or empty.");
             }
             this.category = category;
             return this;
         }
 
         /**
          * Sets the amount for the transaction.
          * 
          * @param amount The monetary value of the transaction.
          * @return The current Builder instance.
          * @throws IllegalArgumentException If the amount is null or negative.
          */
         public Builder setAmount(BigDecimal amount) {
             if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
                 throw new IllegalArgumentException("Amount must be non-negative.");
             }
             this.amount = amount;
             return this;
         }
 
         /**
          * Sets the type for the transaction.
          * 
          * @param type The type of transaction, either INCOME or EXPENSE.
          * @return The current Builder instance.
          * @throws IllegalArgumentException If the type is null.
          */
         public Builder setType(TransactionType type) {
             if (type == null) {
                 throw new IllegalArgumentException("Transaction type cannot be null.");
             }
             this.type = type;
             return this;
         }
 
         /**
          * Builds and returns a new Transaction instance based on the set parameters.
          * 
          * @return A new Transaction instance.
          */
         public Transaction build() {
             return new Transaction(this);
         }
     }
 
     /**
      * Returns a string representation of the Transaction.
      * 
      * @return A string detailing the transaction's attributes.
      */
     @Override
     public String toString() {
         return "Transaction{" +
                 "transactionId='" + transactionId + '\'' +
                 ", description='" + description + '\'' +
                 ", category='" + category + '\'' +
                 ", amount=" + amount +
                 ", type=" + type +
                 '}';
     }
 }
 