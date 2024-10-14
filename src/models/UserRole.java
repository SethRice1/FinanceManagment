/*
 * File: UserRole.java
 * Description: Enumeration of different user roles within the system.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package models;

 /**
  * Enumeration representing the role of a user within the system.
  * 
  * <p>
  * {@code UserRole} defines two primary roles:
  * {@code REGULAR_USER} and {@code ADMIN}. These roles determine the level of access
  * and permissions a user has within the application.
  * </p>
  * 
  * <p>
  * <b>Roles Defined:</b>
  * <ul>
  *     <li><b>REGULAR_USER:</b> Standard users who can manage their own budgets and transactions.</li>
  *     <li><b>ADMIN:</b> Users with elevated privileges, capable of managing other user accounts and overseeing system operations.</li>
  * </ul>
  * </p>
  * 
  * <p>
  * <b>Usage Example:</b>
  * <pre>{@code
  * UserRole role = UserRole.ADMIN;
  * if (role == UserRole.ADMIN) {
  *     // Grant access to admin functionalities
  * }
  * }</pre>
  * </p>
  */
 public enum UserRole {
     /**
      * Represents a standard user with permissions to manage personal budgets and transactions.
      */
     REGULAR_USER,
 
     /**
      * Represents an administrative user with permissions to manage all user accounts and system settings.
      */
     ADMIN
 }
 