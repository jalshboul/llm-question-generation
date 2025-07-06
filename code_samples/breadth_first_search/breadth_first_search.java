/**
 * Breadth-First Search (BFS) implementation in Java.
 *
 * BFS is a graph traversal algorithm that explores all vertices at the present depth
 * before moving on to vertices at the next depth level. It uses a queue data structure.
 *
 * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 * Space Complexity: O(V) for the visited set and queue
 */
import java.util.*;

public class BreadthFirstSearch {
    
    /**
     * Traverse a graph using breadth-first search algorithm.
     *
     * @param graph Adjacency list representation of the graph
     * @param start Starting vertex
     * @return List of vertices in BFS traversal order
     */
    public static List<String> bfs(Map<String, List<String>> graph, String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();
        
        // Mark the source node as visited and enqueue it
        visited.add(start);
        queue.add(start);
        
        while (!queue.isEmpty()) {
            // Dequeue a vertex from queue
            String vertex = queue.poll();
            result.add(vertex);
            
            // Get all adjacent vertices of the dequeued vertex
            // If an adjacent vertex has not been visited, mark it
            // visited and enqueue it
            List<String> neighbors = graph.getOrDefault(vertex, new ArrayList<>());
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
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
        
        System.out.println("BFS starting from vertex 'A':");
        System.out.println(bfs(graph, "A"));
    }
}
