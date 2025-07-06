"""
Prim's Algorithm implementation in Python.

Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a weighted undirected graph.
It finds a subset of the edges that forms a tree that includes every vertex, where the total weight of all
the edges in the tree is minimized.

Time Complexity: O(E log V) with binary heap, O(V^2) with adjacency matrix
Space Complexity: O(V)
"""

import heapq

def prims_algorithm(graph, start_vertex=0):
    """
    Find the minimum spanning tree of a graph using Prim's algorithm.
    
    Args:
        graph: Adjacency list representation of the graph where graph[u] is a list of (v, weight) pairs
        start_vertex: Starting vertex for the algorithm
        
    Returns:
        List of edges in the minimum spanning tree and total weight
    """
    # Number of vertices
    n = len(graph)
    
    # Priority queue to store vertices to be processed
    # Format: (weight, vertex, parent)
    pq = [(0, start_vertex, -1)]
    
    # Set to keep track of vertices already in MST
    in_mst = [False] * n
    
    # List to store MST edges
    mst_edges = []
    
    # Total weight of MST
    total_weight = 0
    
    while pq:
        # Get vertex with minimum weight edge
        weight, vertex, parent = heapq.heappop(pq)
        
        # If already in MST, skip
        if in_mst[vertex]:
            continue
        
        # Add to MST
        in_mst[vertex] = True
        
        # Add edge to MST (except for start vertex)
        if parent != -1:
            mst_edges.append((parent, vertex, weight))
            total_weight += weight
        
        # Add all adjacent vertices not in MST to priority queue
        for neighbor, edge_weight in graph[vertex]:
            if not in_mst[neighbor]:
                heapq.heappush(pq, (edge_weight, neighbor, vertex))
    
    return mst_edges, total_weight


# Example usage
if __name__ == "__main__":
    # Example graph represented as an adjacency list
    # Each entry graph[u] contains a list of (v, weight) pairs
    graph = [
        [(1, 1), (7, 4)],              # 0: edges to 1 (weight 1), 7 (weight 4)
        [(0, 1), (2, 3), (7, 2)],      # 1: edges to 0, 2, 7
        [(1, 3), (3, 5), (5, 3), (8, 6)], # 2: edges to 1, 3, 5, 8
        [(2, 5), (4, 4), (5, 2)],      # 3: edges to 2, 4, 5
        [(3, 4), (5, 7)],              # 4: edges to 3, 5
        [(2, 3), (3, 2), (4, 7), (6, 6)], # 5: edges to 2, 3, 4, 6
        [(5, 6), (7, 1), (8, 5)],      # 6: edges to 5, 7, 8
        [(0, 4), (1, 2), (6, 1), (8, 7)], # 7: edges to 0, 1, 6, 8
        [(2, 6), (6, 5), (7, 7)]       # 8: edges to 2, 6, 7
    ]
    
    # Find minimum spanning tree
    mst_edges, total_weight = prims_algorithm(graph)
    
    # Print MST edges and total weight
    print("Edges in the minimum spanning tree:")
    for u, v, weight in mst_edges:
        print(f"({u}, {v}) with weight {weight}")
    
    print(f"Total weight of MST: {total_weight}")
