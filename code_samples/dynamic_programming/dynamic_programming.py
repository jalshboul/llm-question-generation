"""
Dynamic Programming implementation in Python - Knapsack Problem.

The 0/1 Knapsack problem is a classic optimization problem where we need to select items
to maximize value while keeping the total weight under a given limit. Each item can be
selected only once (0/1 property).

Time Complexity: O(n*W) where n is the number of items and W is the capacity
Space Complexity: O(n*W)
"""

def knapsack_01(weights, values, capacity):
    """
    Solve the 0/1 Knapsack problem using dynamic programming.
    
    Args:
        weights: List of weights of the items
        values: List of values of the items
        capacity: Maximum weight capacity of the knapsack
        
    Returns:
        Maximum value that can be obtained and the selected items
    """
    n = len(weights)
    
    # Create a 2D DP table
    # dp[i][w] represents the maximum value that can be obtained
    # using first i items and with weight limit w
    dp = [[0 for _ in range(capacity + 1)] for _ in range(n + 1)]
    
    # Fill the dp table in bottom-up manner
    for i in range(1, n + 1):
        for w in range(capacity + 1):
            # If current item weight is less than or equal to capacity w
            # We have two choices: include the item or exclude it
            if weights[i-1] <= w:
                # Maximum of including the current item or excluding it
                dp[i][w] = max(values[i-1] + dp[i-1][w-weights[i-1]], dp[i-1][w])
            else:
                # If current item weight is more than capacity w, we can't include it
                dp[i][w] = dp[i-1][w]
    
    # Find the selected items
    selected_items = []
    w = capacity
    for i in range(n, 0, -1):
        # If the value comes from including this item
        if dp[i][w] != dp[i-1][w]:
            selected_items.append(i-1)
            w -= weights[i-1]
    
    # Reverse to get items in original order
    selected_items.reverse()
    
    return dp[n][capacity], selected_items


# Example usage
if __name__ == "__main__":
    # Example problem
    weights = [2, 3, 4, 5]
    values = [3, 4, 5, 6]
    capacity = 8
    
    max_value, selected_items = knapsack_01(weights, values, capacity)
    
    print(f"Maximum value: {max_value}")
    print(f"Selected items (0-indexed): {selected_items}")
    print("Selected items details:")
    for i in selected_items:
        print(f"Item {i}: Weight = {weights[i]}, Value = {values[i]}")
