/**
 * Topological Sort implementation in C++.
 *
 * Topological Sort is an algorithm for ordering the vertices of a directed acyclic graph (DAG)
 * such that for every directed edge (u, v), vertex u comes before vertex v in the ordering.
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
 * Recursive utility function for topological sort.
 *
 * @param graph Adjacency list representation of the graph
 * @param vertex Current vertex
 * @param visited Set of visited vertices
 * @param stack Stack to store the topological order
 */
void topologicalSortUtil(const std::unordered_map<std::string, std::vector<std::string>>& graph,
                        const std::string& vertex,
                        std::unordered_set<std::string>& visited,
                        std::stack<std::string>& stack) {
    // Mark the current vertex as visited
    visited.insert(vertex);
    
    // Recur for all adjacent vertices
    if (graph.find(vertex) != graph.end()) {
        for (const std::string& neighbor : graph.at(vertex)) {
            if (visited.find(neighbor) == visited.end()) {
                topologicalSortUtil(graph, neighbor, visited, stack);
            }
        }
    }
    
    // After all adjacent vertices are processed, push the current vertex to stack
    stack.push(vertex);
}

/**
 * Perform topological sort on a directed acyclic graph.
 *
 * @param graph Adjacency list representation of the graph
 * @return Vector of vertices in topological order
 */
std::vector<std::string> topologicalSort(const std::unordered_map<std::string, std::vector<std::string>>& graph) {
    // Mark all vertices as not visited
    std::unordered_set<std::string> visited;
    // Stack to store the topological order
    std::stack<std::string> stack;
    
    // Visit all vertices in the graph
    for (const auto& pair : graph) {
        const std::string& vertex = pair.first;
        if (visited.find(vertex) == visited.end()) {
            topologicalSortUtil(graph, vertex, visited, stack);
        }
    }
    
    // Convert stack to vector (topological order)
    std::vector<std::string> result;
    while (!stack.empty()) {
        result.push_back(stack.top());
        stack.pop();
    }
    
    return result;
}

// Example usage
int main() {
    // Example directed acyclic graph represented as an adjacency list
    std::unordered_map<std::string, std::vector<std::string>> graph;
    graph["5"] = {"0", "2"};
    graph["4"] = {"0", "1"};
    graph["2"] = {"3"};
    graph["3"] = {"1"};
    graph["0"] = {};
    graph["1"] = {};
    
    std::cout << "Topological Sort order:" << std::endl;
    std::vector<std::string> result = topologicalSort(graph);
    for (const std::string& vertex : result) {
        std::cout << vertex << " ";
    }
    std::cout << std::endl;
    
    return 0;
}
