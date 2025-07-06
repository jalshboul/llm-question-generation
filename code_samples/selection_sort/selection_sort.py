"""
Selection Sort implementation in Python.

Selection Sort is a simple comparison-based sorting algorithm.
It divides the input list into two parts: a sorted sublist and an unsorted sublist.
The algorithm repeatedly finds the minimum element from the unsorted sublist
and moves it to the end of the sorted sublist.

Time Complexity: O(n^2)
Space Complexity: O(1)
"""

def selection_sort(arr):
    """
    Sort an array using selection sort algorithm.
    
    Args:
        arr: List of comparable elements
        
    Returns:
        Sorted list
    """
    n = len(arr)
    
    # Traverse through all array elements
    for i in range(n):
        # Find the minimum element in remaining unsorted array
        min_idx = i
        for j in range(i + 1, n):
            if arr[j] < arr[min_idx]:
                min_idx = j
        
        # Swap the found minimum element with the first element
        arr[i], arr[min_idx] = arr[min_idx], arr[i]
    
    return arr


# Example usage
if __name__ == "__main__":
    # Test with a sample array
    test_array = [64, 25, 12, 22, 11]
    print("Original array:", test_array)
    
    sorted_array = selection_sort(test_array)
    print("Sorted array:", sorted_array)
