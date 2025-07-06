/**
 * A* Search Algorithm implementation in Java.
 *
 * A* is a best-first search algorithm that finds the least-cost path from a given initial node
 * to a goal node. It uses a heuristic function to estimate the cost from the current node to the goal,
 * which guides the search towards the goal more efficiently than algorithms like Dijkstra's.
 *
 * Time Complexity: O(b^d) where b is the branching factor and d is the depth of the goal
 * Space Complexity: O(b^d) to store all generated nodes
 */
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class AStar {
    
    /**
     * Perform A* search from start node to goal.
     *
     * @param <T> Type of nodes
     * @param start Starting node
     * @param goalFn Function that returns true if a node is a goal
     * @param successorsFn Function that returns a list of (successor, cost) pairs
     * @param heuristicFn Function that estimates cost from a node to the goal
     * @return Pair of (path, cost) if a path is found, null otherwise
     */
    public static <T> PathResult<T> search(
            T start,
            Predicate<T> goalFn,
            Function<T, List<SuccessorInfo<T>>> successorsFn,
            Function<T, Double> heuristicFn) {
        
        // Priority queue for open nodes
        PriorityQueue<NodeInfo<T>> openSet = new PriorityQueue<>(
                Comparator.comparingDouble(node -> node.fScore));
        
        // Set of visited nodes
        Set<T> closedSet = new HashSet<>();
        
        // Maps to store g scores and f scores
        Map<T, Double> gScore = new HashMap<>();
        Map<T, Double> fScore = new HashMap<>();
        
        // Map to store parent nodes for path reconstruction
        Map<T, T> cameFrom = new HashMap<>();
        
        // Initialize scores for start node
        gScore.put(start, 0.0);
        fScore.put(start, heuristicFn.apply(start));
        
        // Add start node to open set
        openSet.add(new NodeInfo<>(start, fScore.get(start)));
        
        while (!openSet.isEmpty()) {
            // Get node with lowest f_score
            T current = openSet.poll().node;
            
            // Check if goal is reached
            if (goalFn.test(current)) {
                // Reconstruct path
                List<T> path = new ArrayList<>();
                double cost = gScore.get(current);
                
                // Build path from goal to start
                while (current != null) {
                    path.add(current);
                    current = cameFrom.get(current);
                }
                
                // Reverse path to get start to goal
                Collections.reverse(path);
                
                return new PathResult<>(path, cost);
            }
            
            // Add current node to closed set
            closedSet.add(current);
            
            // Explore successors
            for (SuccessorInfo<T> successorInfo : successorsFn.apply(current)) {
                T successor = successorInfo.node;
                double cost = successorInfo.cost;
                
                // Skip if successor is already evaluated
                if (closedSet.contains(successor)) {
                    continue;
                }
                
                // Calculate tentative g_score
                double tentativeGScore = gScore.get(current) + cost;
                
                // Check if successor is not in open set or has a better g_score
                if (!gScore.containsKey(successor) || tentativeGScore < gScore.get(successor)) {
                    // Update path and scores
                    cameFrom.put(successor, current);
                    gScore.put(successor, tentativeGScore);
                    double newFScore = tentativeGScore + heuristicFn.apply(successor);
                    fScore.put(successor, newFScore);
                    
                    // Add successor to open set if not already there
                    boolean found = false;
                    for (NodeInfo<T> node : openSet) {
                        if (node.node.equals(successor)) {
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found) {
                        openSet.add(new NodeInfo<>(successor, newFScore));
                    }
                }
            }
        }
        
        // No path found
        return null;
    }
    
    /**
     * Class to store node information for the priority queue.
     */
    private static class NodeInfo<T> {
        T node;
        double fScore;
        
        public NodeInfo(T node, double fScore) {
            this.node = node;
            this.fScore = fScore;
        }
    }
    
    /**
     * Class to store successor information.
     */
    public static class SuccessorInfo<T> {
        T node;
        double cost;
        
        public SuccessorInfo(T node, double cost) {
            this.node = node;
            this.cost = cost;
        }
    }
    
    /**
     * Class to store path result.
     */
    public static class PathResult<T> {
        List<T> path;
        double cost;
        
        public PathResult(List<T> path, double cost) {
            this.path = path;
            this.cost = cost;
        }
    }
    
    // Example usage: Grid-based pathfinding
    public static void main(String[] args) {
        // Define a simple grid (0 = empty, 1 = obstacle)
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };
        
        // Define start and goal positions
        Position start = new Position(0, 0);
        Position goal = new Position(4, 4);
        
        // Define goal function
        Predicate<Position> isGoal = pos -> pos.equals(goal);
        
        // Define successor function
        Function<Position, List<SuccessorInfo<Position>>> getSuccessors = pos -> {
            List<SuccessorInfo<Position>> successors = new ArrayList<>();
            
            // Define possible moves (up, right, down, left)
            int[][] moves = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
            
            for (int[] move : moves) {
                int nx = pos.x + move[0];
                int ny = pos.y + move[1];
                
                // Check if position is valid
                if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && grid[nx][ny] == 0) {
                    successors.add(new SuccessorInfo<>(new Position(nx, ny), 1.0));
                }
            }
            
            return successors;
        };
        
        // Define heuristic function (Manhattan distance)
        Function<Position, Double> heuristic = pos -> 
            (double) (Math.abs(pos.x - goal.x) + Math.abs(pos.y - goal.y));
        
        // Run A* search
        PathResult<Position> result = search(start, isGoal, getSuccessors, heuristic);
        
        if (result != null) {
            System.out.println("Path found with cost " + result.cost + ":");
            for (Position pos : result.path) {
                System.out.println("  " + pos);
            }
            
            // Visualize the path
            int[][] pathGrid = new int[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    pathGrid[i][j] = grid[i][j];
                }
            }
            
            for (Position pos : result.path) {
                pathGrid[pos.x][pos.y] = 2;
            }
            
            // Print the grid
            System.out.println("\nGrid visualization:");
            for (int[] row : pathGrid) {
                StringBuilder sb = new StringBuilder("  ");
                for (int cell : row) {
                    sb.append(cell == 0 ? "." : cell == 1 ? "#" : "o").append(" ");
                }
                System.out.println(sb.toString());
            }
        } else {
            System.out.println("No path found");
        }
    }
    
    /**
     * Class to represent a position in the grid.
     */
    static class Position {
        int x;
        int y;
        
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return x == position.x && y == position.y;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
        
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}
