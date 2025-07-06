/**
 * Depth-First Search (DFS) implementation in C++.
 *
 * DFS is a graph traversal algorithm that explores as far as possible along each branch
 * before backtracking. It uses a stack data structure (or recursion) to keep track of vertices.
 *
 * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 * Space Complexity: O(V) for the visited set and recursion stack
 */
#include <iostream>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <stack>
#include <string>

/**
 * Recursive utility function for DFS traversal.
 *
 * @param graph Adjacency list representation of the graph
 * @param vertex Current vertex
 * @param visited Set of visited vertices
 * @param result Vector to store traversal result
 */
void dfsUtil(const std::unordered_map<std::string, std::vector<std::string>>& graph,
             const std::string& vertex,
             std::unordered_set<std::string>& visited,
             std::vector<std::string>& result) {
    // Mark the current vertex as visited and add to result
    visited.insert(vertex);
    result.push_back(vertex);
    
    // Recur for all adjacent vertices
    if (graph.find(vertex) != graph.end()) {
        for (const std::string& neighbor : graph.at(vertex)) {
            if (visited.find(neighbor) == visited.end()) {
                dfsUtil(graph, neighbor, visited, result);
            }
        }
    }
}

/**
 * Traverse a graph using recursive depth-first search algorithm.
 *
 * @param graph Adjacency list representation of the graph
 * @param start Starting vertex
 * @return Vector of vertices in DFS traversal order
 */
std::vector<std::string> dfs(const std::unordered_map<std::string, std::vector<std::string>>& graph,
                            const std::string& start) {
    std::unordered_set<std::string> visited;
    std::vector<std::string> result;
    
    // Call recursive helper function
    dfsUtil(graph, start, visited, result);
    
    return result;
}

/**
 * Traverse a graph using iterative depth-first search algorithm.
 *
 * @param graph Adjacency list representation of the graph
 * @param start Starting vertex
 * @return Vector of vertices in DFS traversal order
 */
std::vector<std::string> dfsIterative(const std::unordered_map<std::string, std::vector<std::string>>& graph,
                                     const std::string& start) {
    std::unordered_set<std::string> visited;
    std::stack<std::string> stack;
    std::vector<std::string> result;
    
    // Push the starting vertex
    stack.push(start);
    
    while (!stack.empty()) {
        // Pop a vertex from stack
        std::string vertex = stack.top();
        stack.pop();
        
        // If not visited, mark it as visited and add to result
        if (visited.find(vertex) == visited.end()) {
            visited.insert(vertex);
            result.push_back(vertex);
            
            // Get all adjacent vertices
            if (graph.find(vertex) != graph.end()) {
                const std::vector<std::string>& neighbors = graph.at(vertex);
                
                // Push all unvisited neighbors to stack (in reverse order to match recursive DFS)
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    const std::string& neighbor = neighbors[i];
                    if (visited.find(neighbor) == visited.end()) {
                        stack.push(neighbor);
                    }
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
    
    std::cout << "Recursive DFS starting from vertex 'A':" << std::endl;
    std::vector<std::string> recursiveResult = dfs(graph, "A");
    for (const std::string& vertex : recursiveResult) {
        std::cout << vertex << " ";
    }
    std::cout << std::endl;
    
    std::cout << "\nIterative DFS starting from vertex 'A':" << std::endl;
    std::vector<std::string> iterativeResult = dfsIterative(graph, "A");
    for (const std::string& vertex : iterativeResult) {
        std::cout << vertex << " ";
    }
    std::cout << std::endl;
    
    return 0;
}
