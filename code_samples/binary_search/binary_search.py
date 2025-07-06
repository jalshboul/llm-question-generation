"""
Binary Search implementation in Python.

Binary Search is an efficient algorithm for finding a target value within a sorted array.
It works by repeatedly dividing the search interval in half.

Time Complexity: O(log n)
Space Complexity: O(1) for iterative implementation, O(log n) for recursive implementation
"""

def binary_search(arr, target):
    """
    Search for a target value in a sorted array using binary search algorithm.
    
    Args:
        arr: Sorted list of comparable elements
        target: Value to search for
        
    Returns:
        Index of the target if found, -1 otherwise
    """
    left = 0
    right = len(arr) - 1
    
    while left <= right:
        # Calculate middle index
        mid = left + (right - left) // 2
        
        # Check if target is present at mid
        if arr[mid] == target:
            return mid
        
        # If target is greater, ignore left half
        elif arr[mid] < target:
            left = mid + 1
        
        # If target is smaller, ignore right half
        else:
            right = mid - 1
    
    # Target is not present in the array
    return -1


# Example usage
if __name__ == "__main__":
    # Test with a sorted array
    test_array = [2, 3, 4, 10, 40]
    target = 10
    
    result = binary_search(test_array, target)
    if result != -1:
        print(f"Element {target} is present at index {result}")
    else:
        print(f"Element {target} is not present in array")
