/**
 * Kruskal's Algorithm implementation in Java.
 *
 * Kruskal's algorithm is a minimum spanning tree algorithm that finds an edge of the least possible weight
 * that connects any two trees in the forest. It is a greedy algorithm that builds the spanning tree by
 * adding edges one by one in order of increasing weight.
 *
 * Time Complexity: O(E log E) where E is the number of edges
 * Space Complexity: O(V + E) where V is the number of vertices
 */
import java.util.*;

public class Kruskal {
    
    // Edge class to represent a weighted edge in the graph
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;
        
        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
        
        // Comparator for sorting edges by weight
        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }
    
    // Disjoint Set (Union-Find) data structure for Kruskal's algorithm
    static class DisjointSet {
        int[] parent, rank;
        
        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            
            // Initialize each element as a separate set
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        // Find the representative (root) of the set containing element x
        // Uses path compression for efficiency
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);  // Path compression
            }
            return parent[x];
        }
        
        // Union of two sets containing elements x and y
        // Uses union by rank for efficiency
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) {
                return;
            }
            
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
    
    /**
     * Find the minimum spanning tree of a graph using Kruskal's algorithm.
     *
     * @param edges List of edges in the graph
     * @param numVertices Number of vertices in the graph
     * @return List of edges in the minimum spanning tree
     */
    public static List<Edge> kruskal(List<Edge> edges, int numVertices) {
        // Sort edges by weight
        Collections.sort(edges);
        
        // Initialize disjoint set
        DisjointSet ds = new DisjointSet(numVertices);
        
        // List to store MST edges
        List<Edge> mst = new ArrayList<>();
        
        // Process edges in order of increasing weight
        for (Edge edge : edges) {
            // If including this edge doesn't form a cycle
            if (ds.find(edge.src) != ds.find(edge.dest)) {
                // Include this edge in MST
                mst.add(edge);
                ds.union(edge.src, edge.dest);
            }
        }
        
        return mst;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example graph represented as a list of edges
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 1));
        edges.add(new Edge(0, 7, 4));
        edges.add(new Edge(1, 2, 3));
        edges.add(new Edge(1, 7, 2));
        edges.add(new Edge(2, 3, 5));
        edges.add(new Edge(2, 8, 6));
        edges.add(new Edge(2, 5, 3));
        edges.add(new Edge(3, 4, 4));
        edges.add(new Edge(3, 5, 2));
        edges.add(new Edge(4, 5, 7));
        edges.add(new Edge(5, 6, 6));
        edges.add(new Edge(6, 7, 1));
        edges.add(new Edge(6, 8, 5));
        edges.add(new Edge(7, 8, 7));
        
        int numVertices = 9;  // 0 to 8
        
        // Find minimum spanning tree
        List<Edge> mst = kruskal(edges, numVertices);
        
        // Print MST edges and total weight
        System.out.println("Edges in the minimum spanning tree:");
        int totalWeight = 0;
        for (Edge edge : mst) {
            System.out.println("(" + edge.src + ", " + edge.dest + ") with weight " + edge.weight);
            totalWeight += edge.weight;
        }
        
        System.out.println("Total weight of MST: " + totalWeight);
    }
}
