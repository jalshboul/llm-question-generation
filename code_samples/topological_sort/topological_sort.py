"""
Topological Sort implementation in Python.

Topological Sort is an algorithm for ordering the vertices of a directed acyclic graph (DAG)
such that for every directed edge (u, v), vertex u comes before vertex v in the ordering.

Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
Space Complexity: O(V) for the visited set and recursion stack
"""

def topological_sort(graph):
    """
    Perform topological sort on a directed acyclic graph.
    
    Args:
        graph: Dictionary representing an adjacency list of the graph
        
    Returns:
        List of vertices in topological order
    """
    # Mark all vertices as not visited
    visited = set()
    # Stack to store the topological order
    stack = []
    
    # Visit all vertices in the graph
    for vertex in graph:
        if vertex not in visited:
            _topological_sort_util(graph, vertex, visited, stack)
    
    # Return the reversed stack (topological order)
    return stack[::-1]


def _topological_sort_util(graph, vertex, visited, stack):
    """
    Recursive utility function for topological sort.
    
    Args:
        graph: Dictionary representing an adjacency list of the graph
        vertex: Current vertex
        visited: Set of visited vertices
        stack: Stack to store the topological order
    """
    # Mark the current vertex as visited
    visited.add(vertex)
    
    # Recur for all adjacent vertices
    for neighbor in graph.get(vertex, []):
        if neighbor not in visited:
            _topological_sort_util(graph, neighbor, visited, stack)
    
    # After all adjacent vertices are processed, push the current vertex to stack
    stack.append(vertex)


# Example usage
if __name__ == "__main__":
    # Example directed acyclic graph represented as an adjacency list
    graph = {
        '5': ['0', '2'],
        '4': ['0', '1'],
        '2': ['3'],
        '3': ['1'],
        '0': [],
        '1': []
    }
    
    print("Topological Sort order:")
    print(topological_sort(graph))
