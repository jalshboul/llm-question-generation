/**
 * Merge Sort implementation in Java.
 *
 * Merge Sort is a divide and conquer algorithm that divides the input array into two halves,
 * recursively sorts them, and then merges the sorted halves.
 *
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 */
public class MergeSort {
    
    /**
     * Sort an array using merge sort algorithm.
     *
     * @param arr Array of comparable elements
     * @return Sorted array
     */
    public static int[] mergeSort(int[] arr) {
        // Base case: if array has 1 or 0 elements, it's already sorted
        if (arr.length <= 1) {
            return arr;
        }
        
        // Divide the array into two halves
        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];
        
        // Copy elements to left and right arrays
        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }
        for (int i = mid; i < arr.length; i++) {
            right[i - mid] = arr[i];
        }
        
        // Recursively sort both halves
        left = mergeSort(left);
        right = mergeSort(right);
        
        // Merge the sorted halves
        return merge(left, right);
    }
    
    /**
     * Merge two sorted arrays into a single sorted array.
     *
     * @param left First sorted array
     * @param right Second sorted array
     * @return Merged sorted array
     */
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        
        // Compare elements from both arrays and add the smaller one to the result
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        
        // Add remaining elements from left array (if any)
        while (i < left.length) {
            result[k++] = left[i++];
        }
        
        // Add remaining elements from right array (if any)
        while (j < right.length) {
            result[k++] = right[j++];
        }
        
        return result;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Test with a sample array
        int[] testArray = {38, 27, 43, 3, 9, 82, 10};
        System.out.print("Original array: ");
        printArray(testArray);
        
        int[] sortedArray = mergeSort(testArray);
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
