/**
 * Selection Sort implementation in C++.
 *
 * Selection Sort is a simple comparison-based sorting algorithm.
 * It divides the input list into two parts: a sorted sublist and an unsorted sublist.
 * The algorithm repeatedly finds the minimum element from the unsorted sublist
 * and moves it to the end of the sorted sublist.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
#include <iostream>
#include <vector>

/**
 * Sort an array using selection sort algorithm.
 *
 * @param arr Vector of comparable elements
 * @return Sorted vector
 */
std::vector<int> selectionSort(std::vector<int> arr) {
    int n = arr.size();
    
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
        std::swap(arr[minIdx], arr[i]);
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
    std::vector<int> testArray = {64, 25, 12, 22, 11};
    std::cout << "Original array: ";
    printArray(testArray);
    
    std::vector<int> sortedArray = selectionSort(testArray);
    std::cout << "Sorted array: ";
    printArray(sortedArray);
    
    return 0;
}
