/**
 * Binary Search implementation in Java.
 *
 * Binary Search is an efficient algorithm for finding a target value within a sorted array.
 * It works by repeatedly dividing the search interval in half.
 *
 * Time Complexity: O(log n)
 * Space Complexity: O(1) for iterative implementation, O(log n) for recursive implementation
 */
public class BinarySearch {
    
    /**
     * Search for a target value in a sorted array using binary search algorithm.
     *
     * @param arr Sorted array of comparable elements
     * @param target Value to search for
     * @return Index of the target if found, -1 otherwise
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            // Calculate middle index
            int mid = left + (right - left) / 2;
            
            // Check if target is present at mid
            if (arr[mid] == target) {
                return mid;
            }
            
            // If target is greater, ignore left half
            if (arr[mid] < target) {
                left = mid + 1;
            }
            // If target is smaller, ignore right half
            else {
                right = mid - 1;
            }
        }
        
        // Target is not present in the array
        return -1;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Test with a sorted array
        int[] testArray = {2, 3, 4, 10, 40};
        int target = 10;
        
        int result = binarySearch(testArray, target);
        if (result != -1) {
            System.out.println("Element " + target + " is present at index " + result);
        } else {
            System.out.println("Element " + target + " is not present in array");
        }
    }
}
