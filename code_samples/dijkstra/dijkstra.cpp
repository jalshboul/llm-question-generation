/**
 * Dijkstra's Algorithm implementation in C++.
 *
 * Dijkstra's algorithm is a graph search algorithm that finds the shortest path
 * between nodes in a graph. It works by visiting vertices in order of increasing
 * distance from the source.
 *
 * Time Complexity: O(V^2) with adjacency matrix, O((V+E)logV) with min-heap
 * Space Complexity: O(V)
 */
#include <iostream>
#include <vector>
#include <unordered_map>
#include <queue>
#include <limits>
#include <string>

/**
 * Find shortest paths from start vertex to all vertices in the graph.
 *
 * @param graph Weighted adjacency list of the graph
 * @param start Starting vertex
 * @return Pair of maps for distances and predecessors
 */
std::pair<std::unordered_map<std::string, int>, std::unordered_map<std::string, std::string>> 
dijkstra(const std::unordered_map<std::string, std::unordered_map<std::string, int>>& graph, 
         const std::string& start) {
    // Initialize distances with infinity for all vertices except start
    std::unordered_map<std::string, int> distances;
    for (const auto& pair : graph) {
        distances[pair.first] = std::numeric_limits<int>::max();
    }
    distances[start] = 0;
    
    // Initialize predecessors
    std::unordered_map<std::string, std::string> predecessors;
    for (const auto& pair : graph) {
        predecessors[pair.first] = "";
    }
    
    // Custom comparator for priority queue
    auto comparator = [](const std::pair<std::string, int>& a, const std::pair<std::string, int>& b) {
        return a.second > b.second;  // Min-heap based on distance
    };
    
    // Priority queue to store vertices to be processed
    // Format: (vertex, distance)
    std::priority_queue<std::pair<std::string, int>, 
                        std::vector<std::pair<std::string, int>>, 
                        decltype(comparator)> priorityQueue(comparator);
    priorityQueue.push({start, 0});
    
    // Set to keep track of vertices already processed
    std::unordered_map<std::string, bool> processed;
    
    while (!priorityQueue.empty()) {
        // Get vertex with minimum distance
        std::string currentVertex = priorityQueue.top().first;
        int currentDistance = priorityQueue.top().second;
        priorityQueue.pop();
        
        // If already processed or found a longer path, skip
        if (processed[currentVertex] || currentDistance > distances[currentVertex]) {
            continue;
        }
        
        // Mark as processed
        processed[currentVertex] = true;
        
        // Check all neighbors of current vertex
        if (graph.find(currentVertex) != graph.end()) {
            for (const auto& neighborPair : graph.at(currentVertex)) {
                std::string neighbor = neighborPair.first;
                int weight = neighborPair.second;
                
                // Calculate distance to neighbor through current vertex
                int distance = currentDistance + weight;
                
                // If found a shorter path to neighbor
                if (distance < distances[neighbor]) {
                    distances[neighbor] = distance;
                    predecessors[neighbor] = currentVertex;
                    priorityQueue.push({neighbor, distance});
                }
            }
        }
    }
    
    return {distances, predecessors};
}

/**
 * Reconstruct shortest path from start to target using predecessors.
 *
 * @param predecessors Map of predecessors
 * @param target Target vertex
 * @return Vector representing the shortest path from start to target
 */
std::vector<std::string> getShortestPath(const std::unordered_map<std::string, std::string>& predecessors,
                                        const std::string& target) {
    std::vector<std::string> path;
    std::string current = target;
    
    // Reconstruct path from target to start
    while (!current.empty()) {
        path.push_back(current);
        auto it = predecessors.find(current);
        if (it != predecessors.end()) {
            current = it->second;
        } else {
            break;
        }
    }
    
    // Return reversed path (from start to target)
    std::reverse(path.begin(), path.end());
    return path;
}

// Example usage
int main() {
    // Example graph represented as a weighted adjacency list
    std::unordered_map<std::string, std::unordered_map<std::string, int>> graph;
    
    graph["A"]["B"] = 4;
    graph["A"]["C"] = 2;
    
    graph["B"]["A"] = 4;
    graph["B"]["D"] = 2;
    graph["B"]["E"] = 3;
    
    graph["C"]["A"] = 2;
    graph["C"]["D"] = 4;
    graph["C"]["F"] = 5;
    
    graph["D"]["B"] = 2;
    graph["D"]["C"] = 4;
    graph["D"]["E"] = 1;
    graph["D"]["F"] = 7;
    
    graph["E"]["B"] = 3;
    graph["E"]["D"] = 1;
    graph["E"]["F"] = 4;
    
    graph["F"]["C"] = 5;
    graph["F"]["D"] = 7;
    graph["F"]["E"] = 4;
    
    std::string startVertex = "A";
    std::string targetVertex = "F";
    
    // Find shortest paths from start vertex
    auto result = dijkstra(graph, startVertex);
    auto distances = result.first;
    auto predecessors = result.second;
    
    // Print shortest distances from start vertex
    std::cout << "Shortest distances from " << startVertex << ":" << std::endl;
    for (const auto& pair : distances) {
        if (pair.second == std::numeric_limits<int>::max()) {
            std::cout << pair.first << ": Infinity" << std::endl;
        } else {
            std::cout << pair.first << ": " << pair.second << std::endl;
        }
    }
    
    // Print shortest path to target vertex
    std::vector<std::string> shortestPath = getShortestPath(predecessors, targetVertex);
    std::cout << "\nShortest path from " << startVertex << " to " << targetVertex << ": ";
    for (size_t i = 0; i < shortestPath.size(); i++) {
        std::cout << shortestPath[i];
        if (i < shortestPath.size() - 1) {
            std::cout << " -> ";
        }
    }
    std::cout << std::endl;
    std::cout << "Distance: " << distances[targetVertex] << std::endl;
    
    return 0;
}
