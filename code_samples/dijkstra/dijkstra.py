"""
Dijkstra's Algorithm implementation in Python.

Dijkstra's algorithm is a graph search algorithm that finds the shortest path
between nodes in a graph. It works by visiting vertices in order of increasing
distance from the source.

Time Complexity: O(V^2) with adjacency matrix, O((V+E)logV) with min-heap
Space Complexity: O(V)
"""

import heapq

def dijkstra(graph, start):
    """
    Find shortest paths from start vertex to all vertices in the graph.
    
    Args:
        graph: Dictionary representing weighted adjacency list of the graph
               {vertex: {neighbor: weight, ...}, ...}
        start: Starting vertex
        
    Returns:
        Dictionary of shortest distances and dictionary of predecessors
    """
    # Initialize distances with infinity for all vertices except start
    distances = {vertex: float('infinity') for vertex in graph}
    distances[start] = 0
    
    # Initialize predecessors
    predecessors = {vertex: None for vertex in graph}
    
    # Priority queue to store vertices to be processed
    # Format: (distance, vertex)
    priority_queue = [(0, start)]
    
    # Set to keep track of vertices already processed
    processed = set()
    
    while priority_queue:
        # Get vertex with minimum distance
        current_distance, current_vertex = heapq.heappop(priority_queue)
        
        # If already processed or found a longer path, skip
        if current_vertex in processed or current_distance > distances[current_vertex]:
            continue
        
        # Mark as processed
        processed.add(current_vertex)
        
        # Check all neighbors of current vertex
        for neighbor, weight in graph[current_vertex].items():
            # Calculate distance to neighbor through current vertex
            distance = current_distance + weight
            
            # If found a shorter path to neighbor
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                predecessors[neighbor] = current_vertex
                heapq.heappush(priority_queue, (distance, neighbor))
    
    return distances, predecessors

def get_shortest_path(predecessors, target):
    """
    Reconstruct shortest path from start to target using predecessors.
    
    Args:
        predecessors: Dictionary of predecessors
        target: Target vertex
        
    Returns:
        List representing the shortest path from start to target
    """
    path = []
    current = target
    
    # Reconstruct path from target to start
    while current is not None:
        path.append(current)
        current = predecessors[current]
    
    # Return reversed path (from start to target)
    return path[::-1]


# Example usage
if __name__ == "__main__":
    # Example graph represented as a weighted adjacency list
    graph = {
        'A': {'B': 4, 'C': 2},
        'B': {'A': 4, 'D': 2, 'E': 3},
        'C': {'A': 2, 'D': 4, 'F': 5},
        'D': {'B': 2, 'C': 4, 'E': 1, 'F': 7},
        'E': {'B': 3, 'D': 1, 'F': 4},
        'F': {'C': 5, 'D': 7, 'E': 4}
    }
    
    start_vertex = 'A'
    target_vertex = 'F'
    
    # Find shortest paths from start vertex
    distances, predecessors = dijkstra(graph, start_vertex)
    
    # Print shortest distances from start vertex
    print(f"Shortest distances from {start_vertex}:")
    for vertex, distance in distances.items():
        print(f"{vertex}: {distance}")
    
    # Print shortest path to target vertex
    shortest_path = get_shortest_path(predecessors, target_vertex)
    print(f"\nShortest path from {start_vertex} to {target_vertex}: {shortest_path}")
    print(f"Distance: {distances[target_vertex]}")
