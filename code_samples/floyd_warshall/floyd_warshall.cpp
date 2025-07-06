/**
 * Floyd-Warshall Algorithm implementation in C++.
 *
 * Floyd-Warshall algorithm is used to find shortest paths between all pairs of vertices in a weighted graph.
 * It works by incrementally improving an estimate on the shortest path between two vertices.
 *
 * Time Complexity: O(V^3) where V is the number of vertices
 * Space Complexity: O(V^2)
 */
#include <iostream>
#include <vector>
#include <limits>
#include <algorithm>

/**
 * Find shortest paths between all pairs of vertices in a weighted graph.
 *
 * @param graph 2D matrix where graph[i][j] is the weight of the edge from i to j,
 *              or INT_MAX if there is no direct edge
 * @return Pair of matrices: shortest distances and path reconstruction matrix
 */
std::pair<std::vector<std::vector<int>>, std::vector<std::vector<int>>> 
floydWarshall(const std::vector<std::vector<int>>& graph) {
    // Number of vertices
    int n = graph.size();
    
    // Initialize distance matrix as the input graph
    std::vector<std::vector<int>> dist = graph;
    
    // Initialize path reconstruction matrix
    std::vector<std::vector<int>> next(n, std::vector<int>(n, -1));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (dist[i][j] != std::numeric_limits<int>::max()) {
                next[i][j] = j;
            }
        }
    }
    
    // Floyd-Warshall algorithm
    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][k] != std::numeric_limits<int>::max() && 
                    dist[k][j] != std::numeric_limits<int>::max()) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }
    
    return {dist, next};
}

/**
 * Reconstruct the shortest path from vertex u to vertex v.
 *
 * @param next Path reconstruction matrix from floydWarshall
 * @param u Source vertex
 * @param v Target vertex
 * @return Vector representing the shortest path from u to v
 */
std::vector<int> reconstructPath(const std::vector<std::vector<int>>& next, int u, int v) {
    if (next[u][v] == -1) {
        return {}; // No path exists
    }
    
    std::vector<int> path;
    path.push_back(u);
    
    while (u != v) {
        u = next[u][v];
        path.push_back(u);
    }
    
    return path;
}

// Example usage
int main() {
    // Example graph represented as an adjacency matrix
    // INT_MAX represents no direct edge between vertices
    const int INF = std::numeric_limits<int>::max();
    std::vector<std::vector<int>> graph = {
        {0, 5, INF, 10},
        {INF, 0, 3, INF},
        {INF, INF, 0, 1},
        {INF, INF, INF, 0}
    };
    
    // Find shortest paths between all pairs of vertices
    auto result = floydWarshall(graph);
    auto dist = result.first;
    auto next = result.second;
    
    // Print shortest distances between all pairs of vertices
    std::cout << "Shortest distances between all pairs of vertices:" << std::endl;
    for (int i = 0; i < dist.size(); i++) {
        for (int j = 0; j < dist[i].size(); j++) {
            if (dist[i][j] == INF) {
                std::cout << "INF\t";
            } else {
                std::cout << dist[i][j] << "\t";
            }
        }
        std::cout << std::endl;
    }
    
    // Print shortest path from vertex 0 to vertex 3
    std::vector<int> path = reconstructPath(next, 0, 3);
    std::cout << "\nShortest path from vertex 0 to vertex 3: [";
    for (size_t i = 0; i < path.size(); i++) {
        std::cout << path[i];
        if (i < path.size() - 1) {
            std::cout << ", ";
        }
    }
    std::cout << "]" << std::endl;
    std::cout << "Distance: " << dist[0][3] << std::endl;
    
    return 0;
}
