/**
 * Quick Sort implementation in C++.
 *
 * Quick Sort is a divide-and-conquer sorting algorithm that works by selecting a 'pivot' element
 * from the array and partitioning the other elements into two sub-arrays according to whether
 * they are less than or greater than the pivot.
 *
 * Time Complexity: O(n log n) average case, O(n²) worst case
 * Space Complexity: O(log n) due to recursion stack
 */
#include <iostream>
#include <vector>

/**
 * Partition the array and return the partition index.
 *
 * @param arr Array to be partitioned
 * @param low Starting index
 * @param high Ending index
 * @return Partition index
 */
int partition(std::vector<int>& arr, int low, int high) {
    // Choose the rightmost element as pivot
    int pivot = arr[high];
    
    // Index of smaller element
    int i = low - 1;
    
    for (int j = low; j < high; j++) {
        // If current element is smaller than the pivot
        if (arr[j] < pivot) {
            i++;
            
            // Swap arr[i] and arr[j]
            std::swap(arr[i], arr[j]);
        }
    }
    
    // Swap arr[i+1] and arr[high] (or pivot)
    std::swap(arr[i + 1], arr[high]);
    
    return i + 1;
}

/**
 * Helper method to recursively sort array using quick sort.
 *
 * @param arr Array to be sorted
 * @param low Starting index
 * @param high Ending index
 */
void quickSortHelper(std::vector<int>& arr, int low, int high) {
    if (low < high) {
        // Find the partition index
        int pi = partition(arr, low, high);
        
        // Recursively sort elements before and after partition
        quickSortHelper(arr, low, pi - 1);
        quickSortHelper(arr, pi + 1, high);
    }
}

/**
 * Sort an array using quick sort algorithm.
 *
 * @param arr Vector of comparable elements
 * @return Sorted vector
 */
std::vector<int> quickSort(std::vector<int> arr) {
    quickSortHelper(arr, 0, arr.size() - 1);
    return arr;
}

// Utility function to print an array
void printArray(const std::vector<int>& arr) {
    for (size_t i = 0; i < arr.size(); i++) {
        std::cout << arr[i] << " ";
    }
    std::cout << std::endl;
}

// Example usage
int main() {
    // Test with a sample array
    std::vector<int> testArray = {10, 7, 8, 9, 1, 5};
    std::cout << "Original array: ";
    printArray(testArray);
    
    std::vector<int> sortedArray = quickSort(testArray);
    std::cout << "Sorted array: ";
    printArray(sortedArray);
    
    return 0;
}
