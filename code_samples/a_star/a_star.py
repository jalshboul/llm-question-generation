"""
A* Search Algorithm implementation in Python.

A* is a best-first search algorithm that finds the least-cost path from a given initial node
to a goal node. It uses a heuristic function to estimate the cost from the current node to the goal,
which guides the search towards the goal more efficiently than algorithms like Dijkstra's.

Time Complexity: O(b^d) where b is the branching factor and d is the depth of the goal
Space Complexity: O(b^d) to store all generated nodes
"""

import heapq
from typing import Dict, List, Tuple, Callable, TypeVar, Set, Optional

# Type for nodes
T = TypeVar('T')

class AStar:
    """
    A* Search Algorithm implementation.
    """
    
    @staticmethod
    def search(
        start: T,
        goal_fn: Callable[[T], bool],
        successors_fn: Callable[[T], List[Tuple[T, float]]],
        heuristic_fn: Callable[[T], float]
    ) -> Optional[Tuple[List[T], float]]:
        """
        Perform A* search from start node to goal.
        
        Args:
            start: Starting node
            goal_fn: Function that returns True if a node is a goal
            successors_fn: Function that returns a list of (successor, cost) tuples
            heuristic_fn: Function that estimates cost from a node to the goal
            
        Returns:
            Tuple of (path, cost) if a path is found, None otherwise
        """
        # Priority queue for open nodes
        open_set = []
        
        # Set of visited nodes
        closed_set: Set[T] = set()
        
        # Dictionary to store g scores (cost from start to node)
        g_score: Dict[T, float] = {start: 0}
        
        # Dictionary to store f scores (g_score + heuristic)
        f_score: Dict[T, float] = {start: heuristic_fn(start)}
        
        # Dictionary to store parent nodes for path reconstruction
        came_from: Dict[T, T] = {}
        
        # Add start node to open set
        heapq.heappush(open_set, (f_score[start], id(start), start))
        
        while open_set:
            # Get node with lowest f_score
            _, _, current = heapq.heappop(open_set)
            
            # Check if goal is reached
            if goal_fn(current):
                # Reconstruct path
                path = [current]
                while current in came_from:
                    current = came_from[current]
                    path.append(current)
                path.reverse()
                
                return path, g_score[path[-1]]
            
            # Add current node to closed set
            closed_set.add(current)
            
            # Explore successors
            for successor, cost in successors_fn(current):
                # Skip if successor is already evaluated
                if successor in closed_set:
                    continue
                
                # Calculate tentative g_score
                tentative_g_score = g_score[current] + cost
                
                # Check if successor is not in open set or has a better g_score
                if successor not in g_score or tentative_g_score < g_score[successor]:
                    # Update path and scores
                    came_from[successor] = current
                    g_score[successor] = tentative_g_score
                    f_score[successor] = g_score[successor] + heuristic_fn(successor)
                    
                    # Add successor to open set if not already there
                    if successor not in [node for _, _, node in open_set]:
                        heapq.heappush(open_set, (f_score[successor], id(successor), successor))
        
        # No path found
        return None


# Example usage: Grid-based pathfinding
def grid_example():
    # Define a simple grid (0 = empty, 1 = obstacle)
    grid = [
        [0, 0, 0, 0, 0],
        [0, 1, 1, 0, 0],
        [0, 0, 0, 1, 0],
        [0, 1, 0, 0, 0],
        [0, 0, 0, 0, 0]
    ]
    
    # Define start and goal positions
    start = (0, 0)
    goal = (4, 4)
    
    # Define goal function
    def is_goal(pos):
        return pos == goal
    
    # Define successor function
    def get_successors(pos):
        x, y = pos
        successors = []
        
        # Define possible moves (up, right, down, left)
        moves = [(0, -1), (1, 0), (0, 1), (-1, 0)]
        
        for dx, dy in moves:
            nx, ny = x + dx, y + dy
            
            # Check if position is valid
            if 0 <= nx < len(grid) and 0 <= ny < len(grid[0]) and grid[nx][ny] == 0:
                successors.append(((nx, ny), 1))
        
        return successors
    
    # Define heuristic function (Manhattan distance)
    def heuristic(pos):
        x, y = pos
        gx, gy = goal
        return abs(x - gx) + abs(y - gy)
    
    # Run A* search
    result = AStar.search(start, is_goal, get_successors, heuristic)
    
    if result:
        path, cost = result
        print(f"Path found with cost {cost}:")
        for pos in path:
            print(f"  {pos}")
        
        # Visualize the path
        path_grid = [row[:] for row in grid]
        for x, y in path:
            path_grid[x][y] = 2
        
        # Print the grid
        print("\nGrid visualization:")
        for row in path_grid:
            print("  " + " ".join(["." if cell == 0 else "#" if cell == 1 else "o" for cell in row]))
    else:
        print("No path found")


if __name__ == "__main__":
    grid_example()
