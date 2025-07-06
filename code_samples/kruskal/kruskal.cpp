/**
 * Kruskal's Algorithm implementation in C++.
 *
 * Kruskal's algorithm is a minimum spanning tree algorithm that finds an edge of the least possible weight
 * that connects any two trees in the forest. It is a greedy algorithm that builds the spanning tree by
 * adding edges one by one in order of increasing weight.
 *
 * Time Complexity: O(E log E) where E is the number of edges
 * Space Complexity: O(V + E) where V is the number of vertices
 */
#include <iostream>
#include <vector>
#include <algorithm>

// Edge class to represent a weighted edge in the graph
struct Edge {
    int src, dest, weight;
    
    Edge(int src, int dest, int weight) : src(src), dest(dest), weight(weight) {}
    
    // Operator for sorting edges by weight
    bool operator<(const Edge& other) const {
        return weight < other.weight;
    }
};

// Disjoint Set (Union-Find) data structure for Kruskal's algorithm
class DisjointSet {
private:
    std::vector<int> parent, rank;
    
public:
    DisjointSet(int n) {
        parent.resize(n);
        rank.resize(n, 0);
        
        // Initialize each element as a separate set
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    
    // Find the representative (root) of the set containing element x
    // Uses path compression for efficiency
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    // Union of two sets containing elements x and y
    // Uses union by rank for efficiency
    void unionSets(int x, int y) {
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
};

/**
 * Find the minimum spanning tree of a graph using Kruskal's algorithm.
 *
 * @param edges Vector of edges in the graph
 * @param numVertices Number of vertices in the graph
 * @return Vector of edges in the minimum spanning tree
 */
std::vector<Edge> kruskal(std::vector<Edge>& edges, int numVertices) {
    // Sort edges by weight
    std::sort(edges.begin(), edges.end());
    
    // Initialize disjoint set
    DisjointSet ds(numVertices);
    
    // Vector to store MST edges
    std::vector<Edge> mst;
    
    // Process edges in order of increasing weight
    for (const Edge& edge : edges) {
        // If including this edge doesn't form a cycle
        if (ds.find(edge.src) != ds.find(edge.dest)) {
            // Include this edge in MST
            mst.push_back(edge);
            ds.unionSets(edge.src, edge.dest);
        }
    }
    
    return mst;
}

// Example usage
int main() {
    // Example graph represented as a vector of edges
    std::vector<Edge> edges = {
        {0, 1, 1},
        {0, 7, 4},
        {1, 2, 3},
        {1, 7, 2},
        {2, 3, 5},
        {2, 8, 6},
        {2, 5, 3},
        {3, 4, 4},
        {3, 5, 2},
        {4, 5, 7},
        {5, 6, 6},
        {6, 7, 1},
        {6, 8, 5},
        {7, 8, 7}
    };
    
    int numVertices = 9;  // 0 to 8
    
    // Find minimum spanning tree
    std::vector<Edge> mst = kruskal(edges, numVertices);
    
    // Print MST edges and total weight
    std::cout << "Edges in the minimum spanning tree:" << std::endl;
    int totalWeight = 0;
    for (const Edge& edge : mst) {
        std::cout << "(" << edge.src << ", " << edge.dest << ") with weight " << edge.weight << std::endl;
        totalWeight += edge.weight;
    }
    
    std::cout << "Total weight of MST: " << totalWeight << std::endl;
    
    return 0;
}
