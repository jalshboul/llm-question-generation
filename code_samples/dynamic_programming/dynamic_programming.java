/**
 * Dynamic Programming implementation in Java - Knapsack Problem.
 *
 * The 0/1 Knapsack problem is a classic optimization problem where we need to select items
 * to maximize value while keeping the total weight under a given limit. Each item can be
 * selected only once (0/1 property).
 *
 * Time Complexity: O(n*W) where n is the number of items and W is the capacity
 * Space Complexity: O(n*W)
 */
import java.util.*;

public class Knapsack {
    
    /**
     * Solve the 0/1 Knapsack problem using dynamic programming.
     *
     * @param weights List of weights of the items
     * @param values List of values of the items
     * @param capacity Maximum weight capacity of the knapsack
     * @return Pair of maximum value and selected items
     */
    public static Map<String, Object> knapsack01(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        
        // Create a 2D DP table
        // dp[i][w] represents the maximum value that can be obtained
        // using first i items and with weight limit w
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Fill the dp table in bottom-up manner
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // If current item weight is less than or equal to capacity w
                // We have two choices: include the item or exclude it
                if (weights[i-1] <= w) {
                    // Maximum of including the current item or excluding it
                    dp[i][w] = Math.max(values[i-1] + dp[i-1][w-weights[i-1]], dp[i-1][w]);
                } else {
                    // If current item weight is more than capacity w, we can't include it
                    dp[i][w] = dp[i-1][w];
                }
            }
        }
        
        // Find the selected items
        List<Integer> selectedItems = new ArrayList<>();
        int w = capacity;
        for (int i = n; i > 0; i--) {
            // If the value comes from including this item
            if (dp[i][w] != dp[i-1][w]) {
                selectedItems.add(i-1);
                w -= weights[i-1];
            }
        }
        
        // Reverse to get items in original order
        Collections.reverse(selectedItems);
        
        // Return both maximum value and selected items
        Map<String, Object> result = new HashMap<>();
        result.put("maxValue", dp[n][capacity]);
        result.put("selectedItems", selectedItems);
        
        return result;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example problem
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 8;
        
        Map<String, Object> result = knapsack01(weights, values, capacity);
        int maxValue = (int) result.get("maxValue");
        List<Integer> selectedItems = (List<Integer>) result.get("selectedItems");
        
        System.out.println("Maximum value: " + maxValue);
        System.out.println("Selected items (0-indexed): " + selectedItems);
        System.out.println("Selected items details:");
        for (int i : selectedItems) {
            System.out.println("Item " + i + ": Weight = " + weights[i] + ", Value = " + values[i]);
        }
    }
}
