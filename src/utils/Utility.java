package utils;

/*
 * Utility.java - Generic Method Example
 */
public class Utility {
    // Generic method to print elements of an array
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}
