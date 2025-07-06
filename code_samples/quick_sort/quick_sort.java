/**
 * Quick Sort implementation in Java.
 *
 * Quick Sort is a divide-and-conquer sorting algorithm that works by selecting a 'pivot' element
 * from the array and partitioning the other elements into two sub-arrays according to whether
 * they are less than or greater than the pivot.
 *
 * Time Complexity: O(n log n) average case, O(n²) worst case
 * Space Complexity: O(log n) due to recursion stack
 */
public class QuickSort {
    
    /**
     * Sort an array using quick sort algorithm.
     *
     * @param arr Array of comparable elements
     * @return Sorted array
     */
    public static int[] quickSort(int[] arr) {
        return quickSortHelper(arr, 0, arr.length - 1);
    }
    
    /**
     * Helper method to recursively sort array using quick sort.
     *
     * @param arr Array to be sorted
     * @param low Starting index
     * @param high Ending index
     * @return Sorted array
     */
    private static int[] quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            // Find the partition index
            int pi = partition(arr, low, high);
            
            // Recursively sort elements before and after partition
            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
        return arr;
    }
    
    /**
     * Partition the array and return the partition index.
     *
     * @param arr Array to be partitioned
     * @param low Starting index
     * @param high Ending index
     * @return Partition index
     */
    private static int partition(int[] arr, int low, int high) {
        // Choose the rightmost element as pivot
        int pivot = arr[high];
        
        // Index of smaller element
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
                i++;
                
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Test with a sample array
        int[] testArray = {10, 7, 8, 9, 1, 5};
        System.out.print("Original array: ");
        printArray(testArray);
        
        int[] sortedArray = quickSort(testArray);
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
