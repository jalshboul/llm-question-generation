"""
Quick Sort implementation in Python.

Quick Sort is a divide-and-conquer sorting algorithm that works by selecting a 'pivot' element
from the array and partitioning the other elements into two sub-arrays according to whether
they are less than or greater than the pivot.

Time Complexity: O(n log n) average case, O(n²) worst case
Space Complexity: O(log n) due to recursion stack
"""

def quick_sort(arr):
    """
    Sort an array using quick sort algorithm.
    
    Args:
        arr: List of comparable elements
        
    Returns:
        Sorted list
    """
    if len(arr) <= 1:
        return arr
    
    # Choose pivot (here we choose the middle element)
    pivot = arr[len(arr) // 2]
    
    # Partition elements
    left = [x for x in arr if x < pivot]
    middle = [x for x in arr if x == pivot]
    right = [x for x in arr if x > pivot]
    
    # Recursively sort sub-arrays and combine
    return quick_sort(left) + middle + quick_sort(right)


# Example usage
if __name__ == "__main__":
    # Test with a sample array
    test_array = [10, 7, 8, 9, 1, 5]
    print("Original array:", test_array)
    
    sorted_array = quick_sort(test_array)
    print("Sorted array:", sorted_array)
