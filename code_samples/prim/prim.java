/**
 * Prim's Algorithm implementation in Java.
 *
 * Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a weighted undirected graph.
 * It finds a subset of the edges that forms a tree that includes every vertex, where the total weight of all
 * the edges in the tree is minimized.
 *
 * Time Complexity: O(E log V) with binary heap, O(V^2) with adjacency matrix
 * Space Complexity: O(V)
 */
import java.util.*;

public class Prim {
    
    // Class to represent a vertex with its weight for the priority queue
    static class Vertex implements Comparable<Vertex> {
        int id;
        int weight;
        int parent;
        
        public Vertex(int id, int weight, int parent) {
            this.id = id;
            this.weight = weight;
            this.parent = parent;
        }
        
        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
    
    /**
     * Find the minimum spanning tree of a graph using Prim's algorithm.
     *
     * @param graph Adjacency list representation of the graph where graph[u] is a list of (v, weight) pairs
     * @param startVertex Starting vertex for the algorithm
     * @return Pair of MST edges and total weight
     */
    public static Map<String, Object> primsAlgorithm(List<List<int[]>> graph, int startVertex) {
        // Number of vertices
        int n = graph.size();
        
        // Priority queue to store vertices to be processed
        // Format: (vertex, weight, parent)
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(startVertex, 0, -1));
        
        // Array to keep track of vertices already in MST
        boolean[] inMST = new boolean[n];
        
        // List to store MST edges
        List<int[]> mstEdges = new ArrayList<>();
        
        // Total weight of MST
        int totalWeight = 0;
        
        while (!pq.isEmpty()) {
            // Get vertex with minimum weight edge
            Vertex vertex = pq.poll();
            
            // If already in MST, skip
            if (inMST[vertex.id]) {
                continue;
            }
            
            // Add to MST
            inMST[vertex.id] = true;
            
            // Add edge to MST (except for start vertex)
            if (vertex.parent != -1) {
                mstEdges.add(new int[]{vertex.parent, vertex.id, vertex.weight});
                totalWeight += vertex.weight;
            }
            
            // Add all adjacent vertices not in MST to priority queue
            for (int[] edge : graph.get(vertex.id)) {
                int neighbor = edge[0];
                int weight = edge[1];
                
                if (!inMST[neighbor]) {
                    pq.add(new Vertex(neighbor, weight, vertex.id));
                }
            }
        }
        
        // Return both MST edges and total weight
        Map<String, Object> result = new HashMap<>();
        result.put("edges", mstEdges);
        result.put("weight", totalWeight);
        
        return result;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example graph represented as an adjacency list
        // Each entry graph[u] contains a list of [v, weight] pairs
        List<List<int[]>> graph = new ArrayList<>();
        
        // Initialize adjacency list
        for (int i = 0; i < 9; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Add edges
        graph.get(0).add(new int[]{1, 1});
        graph.get(0).add(new int[]{7, 4});
        
        graph.get(1).add(new int[]{0, 1});
        graph.get(1).add(new int[]{2, 3});
        graph.get(1).add(new int[]{7, 2});
        
        graph.get(2).add(new int[]{1, 3});
        graph.get(2).add(new int[]{3, 5});
        graph.get(2).add(new int[]{5, 3});
        graph.get(2).add(new int[]{8, 6});
        
        graph.get(3).add(new int[]{2, 5});
        graph.get(3).add(new int[]{4, 4});
        graph.get(3).add(new int[]{5, 2});
        
        graph.get(4).add(new int[]{3, 4});
        graph.get(4).add(new int[]{5, 7});
        
        graph.get(5).add(new int[]{2, 3});
        graph.get(5).add(new int[]{3, 2});
        graph.get(5).add(new int[]{4, 7});
        graph.get(5).add(new int[]{6, 6});
        
        graph.get(6).add(new int[]{5, 6});
        graph.get(6).add(new int[]{7, 1});
        graph.get(6).add(new int[]{8, 5});
        
        graph.get(7).add(new int[]{0, 4});
        graph.get(7).add(new int[]{1, 2});
        graph.get(7).add(new int[]{6, 1});
        graph.get(7).add(new int[]{8, 7});
        
        graph.get(8).add(new int[]{2, 6});
        graph.get(8).add(new int[]{6, 5});
        graph.get(8).add(new int[]{7, 7});
        
        // Find minimum spanning tree
        Map<String, Object> result = primsAlgorithm(graph, 0);
        List<int[]> mstEdges = (List<int[]>) result.get("edges");
        int totalWeight = (int) result.get("weight");
        
        // Print MST edges and total weight
        System.out.println("Edges in the minimum spanning tree:");
        for (int[] edge : mstEdges) {
            System.out.println("(" + edge[0] + ", " + edge[1] + ") with weight " + edge[2]);
        }
        
        System.out.println("Total weight of MST: " + totalWeight);
    }
}
