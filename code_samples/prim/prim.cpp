/**
 * Prim's Algorithm implementation in C++.
 *
 * Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a weighted undirected graph.
 * It finds a subset of the edges that forms a tree that includes every vertex, where the total weight of all
 * the edges in the tree is minimized.
 *
 * Time Complexity: O(E log V) with binary heap, O(V^2) with adjacency matrix
 * Space Complexity: O(V)
 */
#include <iostream>
#include <vector>
#include <queue>
#include <utility>
#include <limits>

// Class to represent a vertex with its weight for the priority queue
struct Vertex {
    int id;
    int weight;
    int parent;
    
    Vertex(int id, int weight, int parent) : id(id), weight(weight), parent(parent) {}
    
    // Operator for priority queue (min-heap)
    bool operator>(const Vertex& other) const {
        return weight > other.weight;
    }
};

/**
 * Find the minimum spanning tree of a graph using Prim's algorithm.
 *
 * @param graph Adjacency list representation of the graph where graph[u] is a vector of (v, weight) pairs
 * @param startVertex Starting vertex for the algorithm
 * @return Pair of MST edges and total weight
 */
std::pair<std::vector<std::vector<int>>, int> 
primsAlgorithm(const std::vector<std::vector<std::pair<int, int>>>& graph, int startVertex) {
    // Number of vertices
    int n = graph.size();
    
    // Priority queue to store vertices to be processed
    // Format: (vertex, weight, parent)
    std::priority_queue<Vertex, std::vector<Vertex>, std::greater<Vertex>> pq;
    pq.push(Vertex(startVertex, 0, -1));
    
    // Vector to keep track of vertices already in MST
    std::vector<bool> inMST(n, false);
    
    // Vector to store MST edges
    std::vector<std::vector<int>> mstEdges;
    
    // Total weight of MST
    int totalWeight = 0;
    
    while (!pq.empty()) {
        // Get vertex with minimum weight edge
        Vertex vertex = pq.top();
        pq.pop();
        
        // If already in MST, skip
        if (inMST[vertex.id]) {
            continue;
        }
        
        // Add to MST
        inMST[vertex.id] = true;
        
        // Add edge to MST (except for start vertex)
        if (vertex.parent != -1) {
            mstEdges.push_back({vertex.parent, vertex.id, vertex.weight});
            totalWeight += vertex.weight;
        }
        
        // Add all adjacent vertices not in MST to priority queue
        for (const auto& edge : graph[vertex.id]) {
            int neighbor = edge.first;
            int weight = edge.second;
            
            if (!inMST[neighbor]) {
                pq.push(Vertex(neighbor, weight, vertex.id));
            }
        }
    }
    
    return {mstEdges, totalWeight};
}

// Example usage
int main() {
    // Example graph represented as an adjacency list
    // Each entry graph[u] contains a vector of (v, weight) pairs
    std::vector<std::vector<std::pair<int, int>>> graph = {
        {{1, 1}, {7, 4}},                      // 0: edges to 1 (weight 1), 7 (weight 4)
        {{0, 1}, {2, 3}, {7, 2}},              // 1: edges to 0, 2, 7
        {{1, 3}, {3, 5}, {5, 3}, {8, 6}},      // 2: edges to 1, 3, 5, 8
        {{2, 5}, {4, 4}, {5, 2}},              // 3: edges to 2, 4, 5
        {{3, 4}, {5, 7}},                      // 4: edges to 3, 5
        {{2, 3}, {3, 2}, {4, 7}, {6, 6}},      // 5: edges to 2, 3, 4, 6
        {{5, 6}, {7, 1}, {8, 5}},              // 6: edges to 5, 7, 8
        {{0, 4}, {1, 2}, {6, 1}, {8, 7}},      // 7: edges to 0, 1, 6, 8
        {{2, 6}, {6, 5}, {7, 7}}               // 8: edges to 2, 6, 7
    };
    
    // Find minimum spanning tree
    auto result = primsAlgorithm(graph, 0);
    auto mstEdges = result.first;
    int totalWeight = result.second;
    
    // Print MST edges and total weight
    std::cout << "Edges in the minimum spanning tree:" << std::endl;
    for (const auto& edge : mstEdges) {
        std::cout << "(" << edge[0] << ", " << edge[1] << ") with weight " << edge[2] << std::endl;
    }
    
    std::cout << "Total weight of MST: " << totalWeight << std::endl;
    
    return 0;
}
