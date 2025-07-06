/**
 * Breadth-First Search (BFS) implementation in C++.
 *
 * BFS is a graph traversal algorithm that explores all vertices at the present depth
 * before moving on to vertices at the next depth level. It uses a queue data structure.
 *
 * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 * Space Complexity: O(V) for the visited set and queue
 */
#include <iostream>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <queue>
#include <string>

/**
 * Traverse a graph using breadth-first search algorithm.
 *
 * @param graph Adjacency list representation of the graph
 * @param start Starting vertex
 * @return Vector of vertices in BFS traversal order
 */
std::vector<std::string> bfs(const std::unordered_map<std::string, std::vector<std::string>>& graph,
                            const std::string& start) {
    std::unordered_set<std::string> visited;
    std::queue<std::string> queue;
    std::vector<std::string> result;
    
    // Mark the source node as visited and enqueue it
    visited.insert(start);
    queue.push(start);
    
    while (!queue.empty()) {
        // Dequeue a vertex from queue
        std::string vertex = queue.front();
        queue.pop();
        result.push_back(vertex);
        
        // Get all adjacent vertices of the dequeued vertex
        // If an adjacent vertex has not been visited, mark it
        // visited and enqueue it
        if (graph.find(vertex) != graph.end()) {
            for (const std::string& neighbor : graph.at(vertex)) {
                if (visited.find(neighbor) == visited.end()) {
                    visited.insert(neighbor);
                    queue.push(neighbor);
                }
            }
        }
    }
    
    return result;
}

// Example usage
int main() {
    // Example graph represented as an adjacency list
    std::unordered_map<std::string, std::vector<std::string>> graph;
    graph["A"] = {"B", "C"};
    graph["B"] = {"A", "D", "E"};
    graph["C"] = {"A", "F"};
    graph["D"] = {"B"};
    graph["E"] = {"B", "F"};
    graph["F"] = {"C", "E"};
    
    std::cout << "BFS starting from vertex 'A':" << std::endl;
    std::vector<std::string> result = bfs(graph, "A");
    for (const std::string& vertex : result) {
        std::cout << vertex << " ";
    }
    std::cout << std::endl;
    
    return 0;
}
