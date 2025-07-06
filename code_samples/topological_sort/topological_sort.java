/**
 * Topological Sort implementation in Java.
 *
 * Topological Sort is an algorithm for ordering the vertices of a directed acyclic graph (DAG)
 * such that for every directed edge (u, v), vertex u comes before vertex v in the ordering.
 *
 * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 * Space Complexity: O(V) for the visited set and recursion stack
 */
import java.util.*;

public class TopologicalSort {
    
    /**
     * Perform topological sort on a directed acyclic graph.
     *
     * @param graph Adjacency list representation of the graph
     * @return List of vertices in topological order
     */
    public static List<String> topologicalSort(Map<String, List<String>> graph) {
        // Mark all vertices as not visited
        Set<String> visited = new HashSet<>();
        // Stack to store the topological order
        Stack<String> stack = new Stack<>();
        
        // Visit all vertices in the graph
        for (String vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(graph, vertex, visited, stack);
            }
        }
        
        // Convert stack to list in reverse order (topological order)
        List<String> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result;
    }
    
    /**
     * Recursive utility function for topological sort.
     *
     * @param graph Adjacency list representation of the graph
     * @param vertex Current vertex
     * @param visited Set of visited vertices
     * @param stack Stack to store the topological order
     */
    private static void topologicalSortUtil(Map<String, List<String>> graph, String vertex,
                                          Set<String> visited, Stack<String> stack) {
        // Mark the current vertex as visited
        visited.add(vertex);
        
        // Recur for all adjacent vertices
        List<String> neighbors = graph.getOrDefault(vertex, new ArrayList<>());
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(graph, neighbor, visited, stack);
            }
        }
        
        // After all adjacent vertices are processed, push the current vertex to stack
        stack.push(vertex);
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example directed acyclic graph represented as an adjacency list
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("5", Arrays.asList("0", "2"));
        graph.put("4", Arrays.asList("0", "1"));
        graph.put("2", Arrays.asList("3"));
        graph.put("3", Arrays.asList("1"));
        graph.put("0", new ArrayList<>());
        graph.put("1", new ArrayList<>());
        
        System.out.println("Topological Sort order:");
        System.out.println(topologicalSort(graph));
    }
}
