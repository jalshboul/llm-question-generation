"""
Merge Sort implementation in Python.

Merge Sort is a divide and conquer algorithm that divides the input array into two halves,
recursively sorts them, and then merges the sorted halves.

Time Complexity: O(n log n)
Space Complexity: O(n)
"""

def merge_sort(arr):
    """
    Sort an array using merge sort algorithm.
    
    Args:
        arr: List of comparable elements
        
    Returns:
        Sorted list
    """
    if len(arr) <= 1:
        return arr
    
    # Divide the array into two halves
    mid = len(arr) // 2
    left_half = arr[:mid]
    right_half = arr[mid:]
    
    # Recursively sort both halves
    left_half = merge_sort(left_half)
    right_half = merge_sort(right_half)
    
    # Merge the sorted halves
    return merge(left_half, right_half)


def merge(left, right):
    """
    Merge two sorted arrays into a single sorted array.
    
    Args:
        left: First sorted array
        right: Second sorted array
        
    Returns:
        Merged sorted array
    """
    result = []
    i = j = 0
    
    # Compare elements from both arrays and add the smaller one to the result
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    
    # Add remaining elements from left array (if any)
    while i < len(left):
        result.append(left[i])
        i += 1
    
    # Add remaining elements from right array (if any)
    while j < len(right):
        result.append(right[j])
        j += 1
    
    return result


# Example usage
if __name__ == "__main__":
    # Test with a sample array
    test_array = [38, 27, 43, 3, 9, 82, 10]
    print("Original array:", test_array)
    
    sorted_array = merge_sort(test_array)
    print("Sorted array:", sorted_array)
