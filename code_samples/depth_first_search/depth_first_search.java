/**
 * Depth-First Search (DFS) implementation in Java.
 *
 * DFS is a graph traversal algorithm that explores as far as possible along each branch
 * before backtracking. It uses a stack data structure (or recursion) to keep track of vertices.
 *
 * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 * Space Complexity: O(V) for the visited set and recursion stack
 */
import java.util.*;

public class DepthFirstSearch {
    
    /**
     * Traverse a graph using recursive depth-first search algorithm.
     *
     * @param graph Adjacency list representation of the graph
     * @param start Starting vertex
     * @return List of vertices in DFS traversal order
     */
    public static List<String> dfs(Map<String, List<String>> graph, String start) {
        Set<String> visited = new HashSet<>();
        List<String> result = new ArrayList<>();
        
        // Call recursive helper function
        dfsUtil(graph, start, visited, result);
        
        return result;
    }
    
    /**
     * Recursive utility function for DFS traversal.
     *
     * @param graph Adjacency list representation of the graph
     * @param vertex Current vertex
     * @param visited Set of visited vertices
     * @param result List to store traversal result
     */
    private static void dfsUtil(Map<String, List<String>> graph, String vertex, 
                               Set<String> visited, List<String> result) {
        // Mark the current vertex as visited and add to result
        visited.add(vertex);
        result.add(vertex);
        
        // Recur for all adjacent vertices
        List<String> neighbors = graph.getOrDefault(vertex, new ArrayList<>());
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                dfsUtil(graph, neighbor, visited, result);
            }
        }
    }
    
    /**
     * Traverse a graph using iterative depth-first search algorithm.
     *
     * @param graph Adjacency list representation of the graph
     * @param start Starting vertex
     * @return List of vertices in DFS traversal order
     */
    public static List<String> dfsIterative(Map<String, List<String>> graph, String start) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        List<String> result = new ArrayList<>();
        
        // Push the starting vertex
        stack.push(start);
        
        while (!stack.isEmpty()) {
            // Pop a vertex from stack
            String vertex = stack.pop();
            
            // If not visited, mark it as visited and add to result
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                result.add(vertex);
                
                // Get all adjacent vertices
                List<String> neighbors = graph.getOrDefault(vertex, new ArrayList<>());
                
                // Push all unvisited neighbors to stack (in reverse order to match recursive DFS)
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    String neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        
        return result;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example graph represented as an adjacency list
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("A", "D", "E"));
        graph.put("C", Arrays.asList("A", "F"));
        graph.put("D", Arrays.asList("B"));
        graph.put("E", Arrays.asList("B", "F"));
        graph.put("F", Arrays.asList("C", "E"));
        
        System.out.println("Recursive DFS starting from vertex 'A':");
        System.out.println(dfs(graph, "A"));
        
        System.out.println("\nIterative DFS starting from vertex 'A':");
        System.out.println(dfsIterative(graph, "A"));
    }
}
