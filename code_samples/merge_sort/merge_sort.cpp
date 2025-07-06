/**
 * Merge Sort implementation in C++.
 *
 * Merge Sort is a divide and conquer algorithm that divides the input array into two halves,
 * recursively sorts them, and then merges the sorted halves.
 *
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 */
#include <iostream>
#include <vector>

/**
 * Merge two sorted arrays into a single sorted array.
 *
 * @param left First sorted array
 * @param right Second sorted array
 * @return Merged sorted array
 */
std::vector<int> merge(const std::vector<int>& left, const std::vector<int>& right) {
    std::vector<int> result;
    size_t i = 0, j = 0;
    
    // Compare elements from both arrays and add the smaller one to the result
    while (i < left.size() && j < right.size()) {
        if (left[i] <= right[j]) {
            result.push_back(left[i]);
            i++;
        } else {
            result.push_back(right[j]);
            j++;
        }
    }
    
    // Add remaining elements from left array (if any)
    while (i < left.size()) {
        result.push_back(left[i]);
        i++;
    }
    
    // Add remaining elements from right array (if any)
    while (j < right.size()) {
        result.push_back(right[j]);
        j++;
    }
    
    return result;
}

/**
 * Sort an array using merge sort algorithm.
 *
 * @param arr Vector of comparable elements
 * @return Sorted vector
 */
std::vector<int> mergeSort(std::vector<int> arr) {
    // Base case: if array has 1 or 0 elements, it's already sorted
    if (arr.size() <= 1) {
        return arr;
    }
    
    // Divide the array into two halves
    size_t mid = arr.size() / 2;
    std::vector<int> left(arr.begin(), arr.begin() + mid);
    std::vector<int> right(arr.begin() + mid, arr.end());
    
    // Recursively sort both halves
    left = mergeSort(left);
    right = mergeSort(right);
    
    // Merge the sorted halves
    return merge(left, right);
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
    std::vector<int> testArray = {38, 27, 43, 3, 9, 82, 10};
    std::cout << "Original array: ";
    printArray(testArray);
    
    std::vector<int> sortedArray = mergeSort(testArray);
    std::cout << "Sorted array: ";
    printArray(sortedArray);
    
    return 0;
}
