/**
 * Dijkstra's Algorithm implementation in Java.
 *
 * Dijkstra's algorithm is a graph search algorithm that finds the shortest path
 * between nodes in a graph. It works by visiting vertices in order of increasing
 * distance from the source.
 *
 * Time Complexity: O(V^2) with adjacency matrix, O((V+E)logV) with min-heap
 * Space Complexity: O(V)
 */
import java.util.*;

public class Dijkstra {
    
    /**
     * Find shortest paths from start vertex to all vertices in the graph.
     *
     * @param graph Weighted adjacency list of the graph
     * @param start Starting vertex
     * @return Map of shortest distances and map of predecessors
     */
    public static Map<String, Object> dijkstra(Map<String, Map<String, Integer>> graph, String start) {
        // Initialize distances with infinity for all vertices except start
        Map<String, Integer> distances = new HashMap<>();
        for (String vertex : graph.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        
        // Initialize predecessors
        Map<String, String> predecessors = new HashMap<>();
        for (String vertex : graph.keySet()) {
            predecessors.put(vertex, null);
        }
        
        // Priority queue to store vertices to be processed
        // Format: (vertex, distance)
        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(
            Comparator.comparing(Map.Entry::getValue)
        );
        priorityQueue.add(new AbstractMap.SimpleEntry<>(start, 0));
        
        // Set to keep track of vertices already processed
        Set<String> processed = new HashSet<>();
        
        while (!priorityQueue.isEmpty()) {
            // Get vertex with minimum distance
            Map.Entry<String, Integer> entry = priorityQueue.poll();
            String currentVertex = entry.getKey();
            int currentDistance = entry.getValue();
            
            // If already processed or found a longer path, skip
            if (processed.contains(currentVertex) || currentDistance > distances.get(currentVertex)) {
                continue;
            }
            
            // Mark as processed
            processed.add(currentVertex);
            
            // Check all neighbors of current vertex
            Map<String, Integer> neighbors = graph.get(currentVertex);
            for (Map.Entry<String, Integer> neighborEntry : neighbors.entrySet()) {
                String neighbor = neighborEntry.getKey();
                int weight = neighborEntry.getValue();
                
                // Calculate distance to neighbor through current vertex
                int distance = currentDistance + weight;
                
                // If found a shorter path to neighbor
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    predecessors.put(neighbor, currentVertex);
                    priorityQueue.add(new AbstractMap.SimpleEntry<>(neighbor, distance));
                }
            }
        }
        
        // Return both distances and predecessors
        Map<String, Object> result = new HashMap<>();
        result.put("distances", distances);
        result.put("predecessors", predecessors);
        
        return result;
    }
    
    /**
     * Reconstruct shortest path from start to target using predecessors.
     *
     * @param predecessors Map of predecessors
     * @param target Target vertex
     * @return List representing the shortest path from start to target
     */
    public static List<String> getShortestPath(Map<String, String> predecessors, String target) {
        List<String> path = new ArrayList<>();
        String current = target;
        
        // Reconstruct path from target to start
        while (current != null) {
            path.add(current);
            current = predecessors.get(current);
        }
        
        // Return reversed path (from start to target)
        Collections.reverse(path);
        return path;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example graph represented as a weighted adjacency list
        Map<String, Map<String, Integer>> graph = new HashMap<>();
        
        graph.put("A", new HashMap<>());
        graph.get("A").put("B", 4);
        graph.get("A").put("C", 2);
        
        graph.put("B", new HashMap<>());
        graph.get("B").put("A", 4);
        graph.get("B").put("D", 2);
        graph.get("B").put("E", 3);
        
        graph.put("C", new HashMap<>());
        graph.get("C").put("A", 2);
        graph.get("C").put("D", 4);
        graph.get("C").put("F", 5);
        
        graph.put("D", new HashMap<>());
        graph.get("D").put("B", 2);
        graph.get("D").put("C", 4);
        graph.get("D").put("E", 1);
        graph.get("D").put("F", 7);
        
        graph.put("E", new HashMap<>());
        graph.get("E").put("B", 3);
        graph.get("E").put("D", 1);
        graph.get("E").put("F", 4);
        
        graph.put("F", new HashMap<>());
        graph.get("F").put("C", 5);
        graph.get("F").put("D", 7);
        graph.get("F").put("E", 4);
        
        String startVertex = "A";
        String targetVertex = "F";
        
        // Find shortest paths from start vertex
        Map<String, Object> result = dijkstra(graph, startVertex);
        Map<String, Integer> distances = (Map<String, Integer>) result.get("distances");
        Map<String, String> predecessors = (Map<String, String>) result.get("predecessors");
        
        // Print shortest distances from start vertex
        System.out.println("Shortest distances from " + startVertex + ":");
        for (Map.Entry<String, Integer> entry : distances.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // Print shortest path to target vertex
        List<String> shortestPath = getShortestPath(predecessors, targetVertex);
        System.out.println("\nShortest path from " + startVertex + " to " + targetVertex + ": " + shortestPath);
        System.out.println("Distance: " + distances.get(targetVertex));
    }
}
