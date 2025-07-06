/**
 * Insertion Sort implementation in Java.
 *
 * Insertion Sort is a simple sorting algorithm that builds the final sorted array one item at a time.
 * It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.
 * However, it performs well for small data sets or nearly sorted data.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
public class InsertionSort {
    
    /**
     * Sort an array using insertion sort algorithm.
     *
     * @param arr Array of comparable elements
     * @return Sorted array
     */
    public static int[] insertionSort(int[] arr) {
        int n = arr.length;
        
        // Traverse through 1 to n
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            
            // Move elements of arr[0..i-1], that are greater than key,
            // to one position ahead of their current position
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        
        return arr;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Test with a sample array
        int[] testArray = {12, 11, 13, 5, 6};
        System.out.print("Original array: ");
        printArray(testArray);
        
        int[] sortedArray = insertionSort(testArray);
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
