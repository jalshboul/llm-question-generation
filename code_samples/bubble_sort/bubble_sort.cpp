/**
 * Bubble Sort implementation in C++.
 *
 * Bubble Sort is a simple sorting algorithm that repeatedly steps through the list,
 * compares adjacent elements and swaps them if they are in the wrong order.
 * The pass through the list is repeated until the list is sorted.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
#include <iostream>
#include <vector>

/**
 * Sort an array using bubble sort algorithm.
 *
 * @param arr Vector of comparable elements
 * @return Sorted vector
 */
std::vector<int> bubbleSort(std::vector<int> arr) {
    int n = arr.size();
    
    // Traverse through all array elements
    for (int i = 0; i < n; i++) {
        // Flag to optimize if no swapping occurs
        bool swapped = false;
        
        // Last i elements are already in place
        for (int j = 0; j < n - i - 1; j++) {
            // Traverse the array from 0 to n-i-1
            // Swap if the element found is greater than the next element
            if (arr[j] > arr[j + 1]) {
                // Swap arr[j] and arr[j+1]
                std::swap(arr[j], arr[j + 1]);
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
    std::vector<int> testArray = {64, 34, 25, 12, 22, 11, 90};
    std::cout << "Original array: ";
    printArray(testArray);
    
    std::vector<int> sortedArray = bubbleSort(testArray);
    std::cout << "Sorted array: ";
    printArray(sortedArray);
    
    return 0;
}
