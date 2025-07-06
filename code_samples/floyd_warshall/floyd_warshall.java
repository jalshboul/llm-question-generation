/**
 * Floyd-Warshall Algorithm implementation in Java.
 *
 * Floyd-Warshall algorithm is used to find shortest paths between all pairs of vertices in a weighted graph.
 * It works by incrementally improving an estimate on the shortest path between two vertices.
 *
 * Time Complexity: O(V^3) where V is the number of vertices
 * Space Complexity: O(V^2)
 */
public class FloydWarshall {
    
    /**
     * Find shortest paths between all pairs of vertices in a weighted graph.
     *
     * @param graph 2D matrix where graph[i][j] is the weight of the edge from i to j,
     *              or Integer.MAX_VALUE if there is no direct edge
     * @return Pair of matrices: shortest distances and path reconstruction matrix
     */
    public static int[][][] floydWarshall(int[][] graph) {
        // Number of vertices
        int n = graph.length;
        
        // Initialize distance matrix as the input graph
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
            }
        }
        
        // Initialize path reconstruction matrix
        int[][] next = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] != Integer.MAX_VALUE) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
        
        // Floyd-Warshall algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }
        
        // Return both distance and path reconstruction matrices
        return new int[][][] { dist, next };
    }
    
    /**
     * Reconstruct the shortest path from vertex u to vertex v.
     *
     * @param next Path reconstruction matrix from floydWarshall
     * @param u Source vertex
     * @param v Target vertex
     * @return List representing the shortest path from u to v
     */
    public static int[] reconstructPath(int[][] next, int u, int v) {
        if (next[u][v] == -1) {
            return new int[0]; // No path exists
        }
        
        // Count path length first
        int pathLength = 1; // Start with source vertex
        int current = u;
        while (current != v) {
            current = next[current][v];
            pathLength++;
        }
        
        // Construct the path
        int[] path = new int[pathLength];
        current = u;
        for (int i = 0; i < pathLength - 1; i++) {
            path[i] = current;
            current = next[current][v];
        }
        path[pathLength - 1] = v;
        
        return path;
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example graph represented as an adjacency matrix
        // Integer.MAX_VALUE represents no direct edge between vertices
        final int INF = Integer.MAX_VALUE;
        int[][] graph = {
            {0, 5, INF, 10},
            {INF, 0, 3, INF},
            {INF, INF, 0, 1},
            {INF, INF, INF, 0}
        };
        
        // Find shortest paths between all pairs of vertices
        int[][][] result = floydWarshall(graph);
        int[][] dist = result[0];
        int[][] next = result[1];
        
        // Print shortest distances between all pairs of vertices
        System.out.println("Shortest distances between all pairs of vertices:");
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[i].length; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF\t");
                } else {
                    System.out.print(dist[i][j] + "\t");
                }
            }
            System.out.println();
        }
        
        // Print shortest path from vertex 0 to vertex 3
        int[] path = reconstructPath(next, 0, 3);
        System.out.print("\nShortest path from vertex 0 to vertex 3: [");
        for (int i = 0; i < path.length; i++) {
            System.out.print(path[i]);
            if (i < path.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println("Distance: " + dist[0][3]);
    }
}
