/**
 * A* Search Algorithm implementation in C++.
 *
 * A* is a best-first search algorithm that finds the least-cost path from a given initial node
 * to a goal node. It uses a heuristic function to estimate the cost from the current node to the goal,
 * which guides the search towards the goal more efficiently than algorithms like Dijkstra's.
 *
 * Time Complexity: O(b^d) where b is the branching factor and d is the depth of the goal
 * Space Complexity: O(b^d) to store all generated nodes
 */
#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>
#include <unordered_set>
#include <functional>
#include <algorithm>
#include <limits>
#include <cmath>

// Template class for A* search algorithm
template <typename T, typename Hash = std::hash<T>, typename Equal = std::equal_to<T>>
class AStar {
public:
    // Structure to store successor information
    struct SuccessorInfo {
        T node;
        double cost;
        
        SuccessorInfo(const T& n, double c) : node(n), cost(c) {}
    };
    
    // Structure to store path result
    struct PathResult {
        std::vector<T> path;
        double cost;
        
        PathResult(const std::vector<T>& p, double c) : path(p), cost(c) {}
    };
    
    /**
     * Perform A* search from start node to goal.
     *
     * @param start Starting node
     * @param goalFn Function that returns true if a node is a goal
     * @param successorsFn Function that returns a vector of (successor, cost) pairs
     * @param heuristicFn Function that estimates cost from a node to the goal
     * @return Pair of (path, cost) if a path is found, nullptr otherwise
     */
    static std::unique_ptr<PathResult> search(
        const T& start,
        const std::function<bool(const T&)>& goalFn,
        const std::function<std::vector<SuccessorInfo>(const T&)>& successorsFn,
        const std::function<double(const T&)>& heuristicFn
    ) {
        // Structure for nodes in the priority queue
        struct NodeInfo {
            T node;
            double fScore;
            
            NodeInfo(const T& n, double f) : node(n), fScore(f) {}
            
            // Comparison operator for priority queue (min-heap)
            bool operator>(const NodeInfo& other) const {
                return fScore > other.fScore;
            }
        };
        
        // Priority queue for open nodes (min-heap)
        std::priority_queue<NodeInfo, std::vector<NodeInfo>, std::greater<NodeInfo>> openSet;
        
        // Set of visited nodes
        std::unordered_set<T, Hash, Equal> closedSet;
        
        // Maps to store g scores and f scores
        std::unordered_map<T, double, Hash, Equal> gScore;
        std::unordered_map<T, double, Hash, Equal> fScore;
        
        // Map to store parent nodes for path reconstruction
        std::unordered_map<T, T, Hash, Equal> cameFrom;
        
        // Initialize scores for start node
        gScore[start] = 0.0;
        fScore[start] = heuristicFn(start);
        
        // Add start node to open set
        openSet.push(NodeInfo(start, fScore[start]));
        
        while (!openSet.empty()) {
            // Get node with lowest f_score
            T current = openSet.top().node;
            openSet.pop();
            
            // Check if goal is reached
            if (goalFn(current)) {
                // Reconstruct path
                std::vector<T> path;
                double cost = gScore[current];
                
                // Build path from goal to start
                T currentNode = current;
                while (cameFrom.find(currentNode) != cameFrom.end()) {
                    path.push_back(currentNode);
                    currentNode = cameFrom[currentNode];
                }
                path.push_back(currentNode); // Add start node
                
                // Reverse path to get start to goal
                std::reverse(path.begin(), path.end());
                
                return std::make_unique<PathResult>(path, cost);
            }
            
            // Add current node to closed set
            closedSet.insert(current);
            
            // Explore successors
            for (const auto& successorInfo : successorsFn(current)) {
                const T& successor = successorInfo.node;
                double cost = successorInfo.cost;
                
                // Skip if successor is already evaluated
                if (closedSet.find(successor) != closedSet.end()) {
                    continue;
                }
                
                // Calculate tentative g_score
                double tentativeGScore = gScore[current] + cost;
                
                // Check if successor is not in open set or has a better g_score
                if (gScore.find(successor) == gScore.end() || tentativeGScore < gScore[successor]) {
                    // Update path and scores
                    cameFrom[successor] = current;
                    gScore[successor] = tentativeGScore;
                    fScore[successor] = tentativeGScore + heuristicFn(successor);
                    
                    // Add successor to open set
                    openSet.push(NodeInfo(successor, fScore[successor]));
                }
            }
        }
        
        // No path found
        return nullptr;
    }
};

// Position class for grid-based pathfinding example
class Position {
public:
    int x;
    int y;
    
    Position(int x, int y) : x(x), y(y) {}
    
    bool operator==(const Position& other) const {
        return x == other.x && y == other.y;
    }
    
    friend std::ostream& operator<<(std::ostream& os, const Position& pos) {
        os << "(" << pos.x << ", " << pos.y << ")";
        return os;
    }
};

// Hash function for Position
namespace std {
    template <>
    struct hash<Position> {
        std::size_t operator()(const Position& pos) const {
            return std::hash<int>()(pos.x) ^ (std::hash<int>()(pos.y) << 1);
        }
    };
}

// Example usage: Grid-based pathfinding
int main() {
    // Define a simple grid (0 = empty, 1 = obstacle)
    std::vector<std::vector<int>> grid = {
        {0, 0, 0, 0, 0},
        {0, 1, 1, 0, 0},
        {0, 0, 0, 1, 0},
        {0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0}
    };
    
    // Define start and goal positions
    Position start(0, 0);
    Position goal(4, 4);
    
    // Define goal function
    auto isGoal = [&goal](const Position& pos) {
        return pos == goal;
    };
    
    // Define successor function
    auto getSuccessors = [&grid](const Position& pos) {
        std::vector<AStar<Position>::SuccessorInfo> successors;
        
        // Define possible moves (up, right, down, left)
        std::vector<std::pair<int, int>> moves = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        
        for (const auto& move : moves) {
            int nx = pos.x + move.first;
            int ny = pos.y + move.second;
            
            // Check if position is valid
            if (nx >= 0 && nx < grid.size() && ny >= 0 && ny < grid[0].size() && grid[nx][ny] == 0) {
                successors.emplace_back(Position(nx, ny), 1.0);
            }
        }
        
        return successors;
    };
    
    // Define heuristic function (Manhattan distance)
    auto heuristic = [&goal](const Position& pos) {
        return std::abs(pos.x - goal.x) + std::abs(pos.y - goal.y);
    };
    
    // Run A* search
    auto result = AStar<Position>::search(start, isGoal, getSuccessors, heuristic);
    
    if (result) {
        std::cout << "Path found with cost " << result->cost << ":" << std::endl;
        for (const auto& pos : result->path) {
            std::cout << "  " << pos << std::endl;
        }
        
        // Visualize the path
        std::vector<std::vector<int>> pathGrid = grid;
        for (const auto& pos : result->path) {
            pathGrid[pos.x][pos.y] = 2;
        }
        
        // Print the grid
        std::cout << "\nGrid visualization:" << std::endl;
        for (const auto& row : pathGrid) {
            std::cout << "  ";
            for (int cell : row) {
                std::cout << (cell == 0 ? "." : cell == 1 ? "#" : "o") << " ";
            }
            std::cout << std::endl;
        }
    } else {
        std::cout << "No path found" << std::endl;
    }
    
    return 0;
}
