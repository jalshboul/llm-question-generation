/**
 * Bubble Sort implementation in Java.
 *
 * Bubble Sort is a simple sorting algorithm that repeatedly steps through the list,
 * compares adjacent elements and swaps them if they are in the wrong order.
 * The pass through the list is repeated until the list is sorted.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
public class BubbleSort {
    
    /**
     * Sort an array using bubble sort algorithm.
     *
     * @param arr Array of comparable elements
     * @return Sorted array
     */
    public static int[] bubbleSort(int[] arr) {
        int n = arr.length;
        
        // Traverse through all array elements
        for (int i = 0; i < n; i++) {
            // Flag to optimize if no swapping occurs
            boolean swapped = false;
            
            // Last i elements are already in place
            for (int j = 0; j < n - i - 1; j++) {
                // Traverse the array from 0 to n-i-1
                // Swap if the element found is greater than the next element
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // If no swapping occurred in this pass, array is sorted
            if (!swapped) {
                break;
            }
        }
        
        return arr;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Test with a sample array
        int[] testArray = {64, 34, 25, 12, 22, 11, 90};
        System.out.print("Original array: ");
        printArray(testArray);
        
        int[] sortedArray = bubbleSort(testArray);
        System.out.print("Sorted array: ");
        printArray(sortedArray);
    }
    
    // Utility method to print an array
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
