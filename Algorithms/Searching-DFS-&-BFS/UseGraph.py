
import Graph

ga1 = Graph.GraphAlgorithms('graph-1.txt')
ga2 = Graph.GraphAlgorithms('graph-2.txt')
ga3 = Graph.GraphAlgorithms('graph-3.txt')
ga4 = Graph.GraphAlgorithms('graph-4.txt')

# Example
print('Graph 1')
print("DFS recursive:")
print(ga1.DFS('recursive'))

print("DFS stack:")
print(ga1.DFS('stack'))

print("BFS:")
print(ga1.BFS())

print("hasCycle?")
print(ga1.hasCycle())

print()
print('Graph 2')
print("DFS recursive:")
print(ga2.DFS('recursive'))

print("DFS stack:")
print(ga2.DFS('stack'))

print("BFS:")
print(ga2.BFS())

print("hasCycle?")
print(ga2.hasCycle())

print()
print('Graph 3')
print("DFS recursive:")
print(ga3.DFS('recursive'))

print("DFS stack:")
print(ga3.DFS('stack'))

print("BFS:")
print(ga3.BFS())

print("hasCycle?")
print(ga3.hasCycle())

print()
print('Graph 4')
print("DFS recursive:")
print(ga4.DFS('recursive'))

print("DFS stack:")
print(ga4.DFS('stack'))

print("BFS:")
print(ga4.BFS())

print("hasCycle?")
print(ga4.hasCycle())



