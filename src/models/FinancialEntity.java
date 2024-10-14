/*
 * File: FinancialEntity.java
 * Description: Abstract base class for financial entities within the system.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package models;

 import java.io.Serializable;
 
 /**
  * Abstract base class for all financial entities within the system.
  * 
  * <p>
  * The FinancialEntity class serves as a superclass for entities that represent 
  * financial data, such as {@link Budget} and {@link Transaction}. It implements
  * {@link Serializable} to allow subclasses to be serialized for data persistence.
  * </p>
  * 
  * <p>
  * <b>Note:</b> Currently, this class does not contain any fields or methods, but 
  * it provides a common type for financial entities and can be extended in the future
  * to include shared attributes or behaviors.
  * </p>
  * 
  * @see Budget
  * @see Transaction
  */
 public abstract class FinancialEntity implements Serializable {
     private static final long serialVersionUID = 1L;
 
     // Future common fields and methods for financial entities can be added here
 }
 