/*
 * File: Utility.java
 * Description: Utility class with helper methods for the application.
 * Author: Seth (R0ice_1)
 * Version: 1.0
 * Date: October 13, 2024
 */
package utils;

/**
 * Utility class containing generic methods.
 */
public class Utility {
    /**
     * Generic method to print elements of an array.
     *
     * @param array The array to print.
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
     * Generic method to find an element in an array.
     *
     * @param array The array to search.
     * @param key   The element to find.
     * @param <T>   The type of elements in the array.
     * @return The index of the element if found, else -1.
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
