"""
Breadth-First Search (BFS) implementation in Python.

BFS is a graph traversal algorithm that explores all vertices at the present depth
before moving on to vertices at the next depth level. It uses a queue data structure.

Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
Space Complexity: O(V) for the visited set and queue
"""

from collections import deque

def bfs(graph, start):
    """
    Traverse a graph using breadth-first search algorithm.
    
    Args:
        graph: Dictionary representing an adjacency list of the graph
        start: Starting vertex
        
    Returns:
        List of vertices in BFS traversal order
    """
    visited = set()
    queue = deque([start])
    result = []
    
    # Mark the source node as visited and enqueue it
    visited.add(start)
    
    while queue:
        # Dequeue a vertex from queue
        vertex = queue.popleft()
        result.append(vertex)
        
        # Get all adjacent vertices of the dequeued vertex
        # If an adjacent vertex has not been visited, mark it
        # visited and enqueue it
        for neighbor in graph.get(vertex, []):
            if neighbor not in visited:
                visited.add(neighbor)
                queue.append(neighbor)
    
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
    
    print("BFS starting from vertex 'A':")
    print(bfs(graph, 'A'))
