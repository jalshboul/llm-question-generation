"""
Depth-First Search (DFS) implementation in Python.

DFS is a graph traversal algorithm that explores as far as possible along each branch
before backtracking. It uses a stack data structure (or recursion) to keep track of vertices.

Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
Space Complexity: O(V) for the visited set and recursion stack
"""

def dfs(graph, start, visited=None):
    """
    Traverse a graph using depth-first search algorithm.
    
    Args:
        graph: Dictionary representing an adjacency list of the graph
        start: Starting vertex
        visited: Set of visited vertices (used in recursion)
        
    Returns:
        List of vertices in DFS traversal order
    """
    if visited is None:
        visited = set()
    
    result = [start]  # Add the current vertex to result
    visited.add(start)  # Mark the current vertex as visited
    
    # Recur for all adjacent vertices
    for neighbor in graph.get(start, []):
        if neighbor not in visited:
            # Recursively visit unvisited neighbors
            result.extend(dfs(graph, neighbor, visited))
    
    return result


def dfs_iterative(graph, start):
    """
    Traverse a graph using iterative depth-first search algorithm.
    
    Args:
        graph: Dictionary representing an adjacency list of the graph
        start: Starting vertex
        
    Returns:
        List of vertices in DFS traversal order
    """
    visited = set()
    stack = [start]
    result = []
    
    while stack:
        vertex = stack.pop()
        
        if vertex not in visited:
            visited.add(vertex)
            result.append(vertex)
            
            # Add neighbors to stack in reverse order to get the same result as recursive DFS
            for neighbor in reversed(graph.get(vertex, [])):
                if neighbor not in visited:
                    stack.append(neighbor)
    
    return result


# Example usage
if __name__ == "__main__":
    # Example graph represented as an adjacency list
    graph = {
        'A': ['B', 'C'],
        'B': ['A', 'D', 'E'],
        'C': ['A', 'F'],
        'D': ['B'],
        'E': ['B', 'F'],
        'F': ['C', 'E']
    }
    
    print("Recursive DFS starting from vertex 'A':")
    print(dfs(graph, 'A'))
    
    print("\nIterative DFS starting from vertex 'A':")
    print(dfs_iterative(graph, 'A'))
