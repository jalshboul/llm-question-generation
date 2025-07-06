"""
Floyd-Warshall Algorithm implementation in Python.

Floyd-Warshall algorithm is used to find shortest paths between all pairs of vertices in a weighted graph.
It works by incrementally improving an estimate on the shortest path between two vertices.

Time Complexity: O(V^3) where V is the number of vertices
Space Complexity: O(V^2)
"""

def floyd_warshall(graph):
    """
    Find shortest paths between all pairs of vertices in a weighted graph.
    
    Args:
        graph: 2D matrix where graph[i][j] is the weight of the edge from i to j,
               or float('inf') if there is no direct edge
        
    Returns:
        2D matrix of shortest distances between all pairs of vertices
    """
    # Number of vertices
    n = len(graph)
    
    # Initialize distance matrix as the input graph
    dist = [row[:] for row in graph]  # Create a copy of the graph
    
    # Initialize path reconstruction matrix
    next_vertex = [[j if dist[i][j] != float('inf') else None for j in range(n)] for i in range(n)]
    
    # Floyd-Warshall algorithm
    for k in range(n):
        for i in range(n):
            for j in range(n):
                if dist[i][k] != float('inf') and dist[k][j] != float('inf'):
                    if dist[i][j] > dist[i][k] + dist[k][j]:
                        dist[i][j] = dist[i][k] + dist[k][j]
                        next_vertex[i][j] = next_vertex[i][k]
    
    return dist, next_vertex

def reconstruct_path(next_vertex, u, v):
    """
    Reconstruct the shortest path from vertex u to vertex v.
    
    Args:
        next_vertex: Path reconstruction matrix from floyd_warshall
        u: Source vertex
        v: Target vertex
        
    Returns:
        List representing the shortest path from u to v
    """
    if next_vertex[u][v] is None:
        return []
    
    path = [u]
    while u != v:
        u = next_vertex[u][v]
        path.append(u)
    
    return path


# Example usage
if __name__ == "__main__":
    # Example graph represented as an adjacency matrix
    # INF represents no direct edge between vertices
    INF = float('inf')
    graph = [
        [0, 5, INF, 10],
        [INF, 0, 3, INF],
        [INF, INF, 0, 1],
        [INF, INF, INF, 0]
    ]
    
    # Find shortest paths between all pairs of vertices
    dist, next_vertex = floyd_warshall(graph)
    
    # Print shortest distances between all pairs of vertices
    print("Shortest distances between all pairs of vertices:")
    for i in range(len(dist)):
        for j in range(len(dist[i])):
            if dist[i][j] == INF:
                print("INF", end="\t")
            else:
                print(dist[i][j], end="\t")
        print()
    
    # Print shortest path from vertex 0 to vertex 3
    path = reconstruct_path(next_vertex, 0, 3)
    print("\nShortest path from vertex 0 to vertex 3:", path)
    print("Distance:", dist[0][3])
