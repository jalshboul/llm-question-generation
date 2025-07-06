"""
Kruskal's Algorithm implementation in Python.

Kruskal's algorithm is a minimum spanning tree algorithm that finds an edge of the least possible weight
that connects any two trees in the forest. It is a greedy algorithm that builds the spanning tree by
adding edges one by one in order of increasing weight.

Time Complexity: O(E log E) where E is the number of edges
Space Complexity: O(V + E) where V is the number of vertices
"""

# Disjoint Set (Union-Find) data structure for Kruskal's algorithm
class DisjointSet:
    def __init__(self, n):
        """
        Initialize disjoint set with n elements.
        
        Args:
            n: Number of elements
        """
        self.parent = list(range(n))
        self.rank = [0] * n
    
    def find(self, x):
        """
        Find the representative (root) of the set containing element x.
        Uses path compression for efficiency.
        
        Args:
            x: Element to find
            
        Returns:
            Root of the set containing x
        """
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])  # Path compression
        return self.parent[x]
    
    def union(self, x, y):
        """
        Union of two sets containing elements x and y.
        Uses union by rank for efficiency.
        
        Args:
            x: First element
            y: Second element
        """
        root_x = self.find(x)
        root_y = self.find(y)
        
        if root_x == root_y:
            return
        
        # Union by rank
        if self.rank[root_x] < self.rank[root_y]:
            self.parent[root_x] = root_y
        elif self.rank[root_x] > self.rank[root_y]:
            self.parent[root_y] = root_x
        else:
            self.parent[root_y] = root_x
            self.rank[root_x] += 1


def kruskal(graph, num_vertices):
    """
    Find the minimum spanning tree of a graph using Kruskal's algorithm.
    
    Args:
        graph: List of edges, where each edge is (weight, u, v)
        num_vertices: Number of vertices in the graph
        
    Returns:
        List of edges in the minimum spanning tree
    """
    # Sort edges by weight
    graph.sort()
    
    # Initialize disjoint set
    ds = DisjointSet(num_vertices)
    
    # List to store MST edges
    mst = []
    
    # Process edges in order of increasing weight
    for weight, u, v in graph:
        # If including this edge doesn't form a cycle
        if ds.find(u) != ds.find(v):
            # Include this edge in MST
            mst.append((weight, u, v))
            ds.union(u, v)
    
    return mst


# Example usage
if __name__ == "__main__":
    # Example graph represented as a list of edges
    # Each edge is (weight, u, v)
    graph = [
        (1, 0, 1),
        (4, 0, 7),
        (3, 1, 2),
        (2, 1, 7),
        (5, 2, 3),
        (6, 2, 8),
        (3, 2, 5),
        (4, 3, 4),
        (2, 3, 5),
        (7, 4, 5),
        (6, 5, 6),
        (1, 6, 7),
        (5, 6, 8),
        (7, 7, 8)
    ]
    
    num_vertices = 9  # 0 to 8
    
    # Find minimum spanning tree
    mst = kruskal(graph, num_vertices)
    
    # Print MST edges and total weight
    print("Edges in the minimum spanning tree:")
    total_weight = 0
    for weight, u, v in mst:
        print(f"({u}, {v}) with weight {weight}")
        total_weight += weight
    
    print(f"Total weight of MST: {total_weight}")
