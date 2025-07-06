/**
 * Linear Search implementation in Java.
 *
 * Linear Search is a simple search algorithm that checks each element in the list
 * sequentially until it finds the target value or reaches the end of the list.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class LinearSearch {
    
    /**
     * Search for a target value in an array using linear search algorithm.
     *
     * @param arr Array of elements
     * @param target Value to search for
     * @return Index of the target if found, -1 otherwise
     */
    public static int linearSearch(int[] arr, int target) {
        // Traverse the array sequentially
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        
        // Target is not present in the array
        return -1;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Test with an array
        int[] testArray = {64, 34, 25, 12, 22, 11, 90};
        int target = 22;
        
        int result = linearSearch(testArray, target);
        if (result != -1) {
            System.out.println("Element " + target + " is present at index " + result);
        } else {
            System.out.println("Element " + target + " is not present in array");
        }
    }
}
