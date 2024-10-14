/*
 * File: Utility.java
 * Description: Utility class containing generic helper methods for common operations.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */

 package utils;

 /**
  * Utility class containing generic helper methods for common operations.
  * 
  * <p>
  * The Utility class provides static methods that perform generic tasks such as
  * printing array elements and finding elements within arrays. These methods are 
  * designed to be reusable across different parts of the application.
  * </p>
  * 
  * <p>
  * <b>Key Functionalities:</b>
  * <ul>
  *     <li>Print elements of an array.</li>
  *     <li>Find the index of a specific element in an array.</li>
  * </ul>
  * </p>
  * 
  * <p>
  * <b>Note:</b> These methods are generic and can handle arrays of any object type.
  * </p>
  */
 public class Utility {
     /**
      * Prints each element of the provided array to the console.
      * 
      * <p>
      * This generic method iterates through the array and prints each element using
      * {@link System#out}.
      * </p>
      * 
      * @param array The array whose elements are to be printed.
      * @param <T>   The type of elements in the array.
      */
     public static <T> void printArray(T[] array) {
         if (array == null) {
             System.out.println("Array is null.");
             return;
         }
         for (T element : array) {
             System.out.println(element);
         }
     }
 
     /**
      * Finds the index of the specified element in the provided array.
      * 
      * <p>
      * This generic method searches for the given key in the array and returns its
      * index if found. If the element is not found or if the array/key is null,
      * the method returns -1.
      * </p>
      * 
      * @param array The array to search within.
      * @param key   The element to find.
      * @param <T>   The type of elements in the array.
      * @return The index of the element if found; otherwise, -1.
      */
     public static <T> int findElement(T[] array, T key) {
         if (array == null || key == null) {
             return -1;
         }
         for (int i = 0; i < array.length; i++) {
             if (key.equals(array[i])) {
                 return i;
             }
         }
         return -1;
     }
 }
 