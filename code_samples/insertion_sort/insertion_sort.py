"""
Insertion Sort implementation in Python.

Insertion Sort is a simple sorting algorithm that builds the final sorted array one item at a time.
It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.
However, it performs well for small data sets or nearly sorted data.

Time Complexity: O(n^2)
Space Complexity: O(1)
"""

def insertion_sort(arr):
    """
    Sort an array using insertion sort algorithm.
    
    Args:
        arr: List of comparable elements
        
    Returns:
        Sorted list
    """
    # Traverse through 1 to len(arr)
    for i in range(1, len(arr)):
        key = arr[i]
        
        # Move elements of arr[0..i-1], that are greater than key,
        # to one position ahead of their current position
        j = i - 1
        while j >= 0 and key < arr[j]:
            arr[j + 1] = arr[j]
            j -= 1
        arr[j + 1] = key
    
    return arr


# Example usage
if __name__ == "__main__":
    # Test with a sample array
    test_array = [12, 11, 13, 5, 6]
    print("Original array:", test_array)
    
    sorted_array = insertion_sort(test_array)
    print("Sorted array:", sorted_array)
