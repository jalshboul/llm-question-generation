/**
 * Dynamic Programming implementation in C++ - Knapsack Problem.
 *
 * The 0/1 Knapsack problem is a classic optimization problem where we need to select items
 * to maximize value while keeping the total weight under a given limit. Each item can be
 * selected only once (0/1 property).
 *
 * Time Complexity: O(n*W) where n is the number of items and W is the capacity
 * Space Complexity: O(n*W)
 */
#include <iostream>
#include <vector>
#include <algorithm>

/**
 * Solve the 0/1 Knapsack problem using dynamic programming.
 *
 * @param weights Vector of weights of the items
 * @param values Vector of values of the items
 * @param capacity Maximum weight capacity of the knapsack
 * @return Pair of maximum value and selected items
 */
std::pair<int, std::vector<int>> knapsack01(const std::vector<int>& weights, 
                                           const std::vector<int>& values, 
                                           int capacity) {
    int n = weights.size();
    
    // Create a 2D DP table
    // dp[i][w] represents the maximum value that can be obtained
    // using first i items and with weight limit w
    std::vector<std::vector<int>> dp(n + 1, std::vector<int>(capacity + 1, 0));
    
    // Fill the dp table in bottom-up manner
    for (int i = 1; i <= n; i++) {
        for (int w = 0; w <= capacity; w++) {
            // If current item weight is less than or equal to capacity w
            // We have two choices: include the item or exclude it
            if (weights[i-1] <= w) {
                // Maximum of including the current item or excluding it
                dp[i][w] = std::max(values[i-1] + dp[i-1][w-weights[i-1]], dp[i-1][w]);
            } else {
                // If current item weight is more than capacity w, we can't include it
                dp[i][w] = dp[i-1][w];
            }
        }
    }
    
    // Find the selected items
    std::vector<int> selectedItems;
    int w = capacity;
    for (int i = n; i > 0; i--) {
        // If the value comes from including this item
        if (dp[i][w] != dp[i-1][w]) {
            selectedItems.push_back(i-1);
            w -= weights[i-1];
        }
    }
    
    // Reverse to get items in original order
    std::reverse(selectedItems.begin(), selectedItems.end());
    
    return {dp[n][capacity], selectedItems};
}

// Example usage
int main() {
    // Example problem
    std::vector<int> weights = {2, 3, 4, 5};
    std::vector<int> values = {3, 4, 5, 6};
    int capacity = 8;
    
    auto result = knapsack01(weights, values, capacity);
    int maxValue = result.first;
    std::vector<int> selectedItems = result.second;
    
    std::cout << "Maximum value: " << maxValue << std::endl;
    std::cout << "Selected items (0-indexed): ";
    for (size_t i = 0; i < selectedItems.size(); i++) {
        std::cout << selectedItems[i];
        if (i < selectedItems.size() - 1) {
            std::cout << ", ";
        }
    }
    std::cout << std::endl;
    
    std::cout << "Selected items details:" << std::endl;
    for (int i : selectedItems) {
        std::cout << "Item " << i << ": Weight = " << weights[i] << ", Value = " << values[i] << std::endl;
    }
    
    return 0;
}
