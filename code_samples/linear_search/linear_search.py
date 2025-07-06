"""
Linear Search implementation in Python.

Linear Search is a simple search algorithm that checks each element in the list
sequentially until it finds the target value or reaches the end of the list.

Time Complexity: O(n)
Space Complexity: O(1)
"""

def linear_search(arr, target):
    """
    Search for a target value in an array using linear search algorithm.
    
    Args:
        arr: List of elements
        target: Value to search for
        
    Returns:
        Index of the target if found, -1 otherwise
    """
    # Traverse the array sequentially
    for i in range(len(arr)):
        if arr[i] == target:
            return i
    
    # Target is not present in the array
    return -1


# Example usage
if __name__ == "__main__":
    # Test with an array
    test_array = [64, 34, 25, 12, 22, 11, 90]
    target = 22
    
    result = linear_search(test_array, target)
    if result != -1:
        print(f"Element {target} is present at index {result}")
    else:
        print(f"Element {target} is not present in array")
