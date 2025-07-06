/**
 * Selection Sort implementation in Java.
 *
 * Selection Sort is a simple comparison-based sorting algorithm.
 * It divides the input list into two parts: a sorted sublist and an unsorted sublist.
 * The algorithm repeatedly finds the minimum element from the unsorted sublist
 * and moves it to the end of the sorted sublist.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
public class SelectionSort {
    
    /**
     * Sort an array using selection sort algorithm.
     *
     * @param arr Array of comparable elements
     * @return Sorted array
     */
    public static int[] selectionSort(int[] arr) {
        int n = arr.length;
        
        // Traverse through all array elements
        for (int i = 0; i < n; i++) {
            // Find the minimum element in remaining unsorted array
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            
            // Swap the found minimum element with the first element
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
        
        return arr;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Test with a sample array
        int[] testArray = {64, 25, 12, 22, 11};
        System.out.print("Original array: ");
        printArray(testArray);
        
        int[] sortedArray = selectionSort(testArray);
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
