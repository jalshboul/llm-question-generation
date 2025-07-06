/**
 * Insertion Sort implementation in C++.
 *
 * Insertion Sort is a simple sorting algorithm that builds the final sorted array one item at a time.
 * It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.
 * However, it performs well for small data sets or nearly sorted data.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
#include <iostream>
#include <vector>

/**
 * Sort an array using insertion sort algorithm.
 *
 * @param arr Vector of comparable elements
 * @return Sorted vector
 */
std::vector<int> insertionSort(std::vector<int> arr) {
    int n = arr.size();
    
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

// Utility function to print an array
void printArray(const std::vector<int>& arr) {
    for (int i = 0; i < arr.size(); i++) {
        std::cout << arr[i] << " ";
    }
    std::cout << std::endl;
}

// Example usage
int main() {
    // Test with a sample array
    std::vector<int> testArray = {12, 11, 13, 5, 6};
    std::cout << "Original array: ";
    printArray(testArray);
    
    std::vector<int> sortedArray = insertionSort(testArray);
    std::cout << "Sorted array: ";
    printArray(sortedArray);
    
    return 0;
}
