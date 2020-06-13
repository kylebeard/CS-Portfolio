
from Bridges.GraphAdjList import *
import heapq

class Dijkstra():
    def __init__(self, inputFile, startingVertex, goalVertex):
        # an initially empty dictionary containing mapping: vertex: [child, weight]
        self.adjacency = {}
        # collection of vertices
        self.vertices = []
        # each dictionary entry contains mapping of vertex:parent
        self.parent = {}
        # startingVertex, goalVertex
        self.startingVertex, self.goalVertex = startingVertex, goalVertex

        # The following reads in the input file and constructs an adjacency list of the graph.
        graph = open(inputFile)
        for line in graph:
            entry = line.split()

            # get the vertices
            self.vertices.append(entry[0])
            self.vertices.append(entry[1])

            if entry[0] not in self.adjacency:
                self.adjacency[entry[0]] = []

            # construct an edge for the adjacency list
            edge = (entry[1], int(entry[2]))
            self.adjacency[entry[0]].append(edge)

        # remove duplication in vertices
        self.vertices = list(set(self.vertices))

        # checking if start and goal are in vertices
        if startingVertex not in self.vertices:
            print('Starting vertex ', startingVertex, ' not present in graph')
            quit()
        elif goalVertex not in self.vertices:
            print('Goal vertex ', goalVertex, ' not present in graph')
            quit()

        # create Bridges graph
        self.g = GraphAdjList()
        for vertex in self.vertices:
            self.g.add_vertex(vertex, "")
            self.g.get_vertices().get(vertex).get_visualizer().set_color("red")
        
        for vertex in self.adjacency:      
            for edge in self.adjacency[vertex]:
                self.g.add_edge(vertex, edge[0], edge[1])

    # solve it using Dijkstra algorithm
    def solve(self):
        self.visited = []
        self.cost_from_start = 0
        heap = []
        heapq.heappush(heap, (0, self.startingVertex, None))
        self.visited.append(self.startingVertex)
        
        while heap:
            vertex = heapq.heappop(heap)

            if vertex not in self.visited:
                self.visited.append(vertex)

                # get the weight of the parent
                if vertex[2] is not None:
                    parent = vertex[2]
                    for adjacentVertex in self.adjacency[parent]:
                        if adjacentVertex[0] == vertex:
                            weight = adjacentVertex[1]
                            # add to the cost from start
                            self.cost_from_start += weight

                vertex = vertex[1]

                if vertex == self.goalVertex:
                    return True

                if vertex in self.adjacency:
                    for edge in self.adjacency[vertex]:
                        priority = self.cost_from_start + edge[1]
                        heapq.heappush(heap, (priority, edge[0], vertex))
                        self.parent[edge[0]] = vertex 
                    







    # retrieve the path from start to the goal 
    def find_path(self):
        self.path = []
        vertex = self.goalVertex
        self.path.append(self.goalVertex)
        while vertex != self.startingVertex:
            vertex = self.parent[vertex]
            self.path.append(vertex)
        self.path.reverse()




    
    # draw the path as red
    def draw_path(self):
        print(self.path)
        for i in range(len(self.path)-1):
            self.g.get_link_visualizer(self.path[i], self.path[i+1]).set_color("red")

    # return the Bridges object
    def get_graph(self):
        return self.g
