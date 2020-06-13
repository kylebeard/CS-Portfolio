
'''
Demonstration of some simple graph algorithms.
    
@author: Kyle Beard
'''

import sys

class GraphAlgorithms:
    
    '''
    Reads in the specified input file containing
    adjacent edges in a graph and constructs an
    adjacency list.

    The adjacency list is a dictionary that maps
    a vertex to its adjacent vertices.
    '''
    def __init__(self, fileName): 
        
        graphFile = open(fileName)

        '''
        create an initially empty dictionary representing
        an adjacency list of the graph
        '''
        self.adjacencyList = { }
      
    
        '''
        collection of vertices in the graph (there may be duplicates)
        '''
        self.vertices = [ ]

        for line in graphFile:
            '''
            Get the two vertices
        
            Python lets us assign two variables with one
            assignment statement.
            '''
            (firstVertex, secondVertex) = line.split()
            
        
            '''
            Add the two vertices to the list of vertices
            At this point, duplicates are ok as later
            operations will retrieve the set of vertices.
            '''
            self.vertices.append(firstVertex)
            self.vertices.append(secondVertex)

            '''
            Check if the first vertex is in the adjacency list.
            If not, add it to the adjacency list.
            '''
            if firstVertex not in self.adjacencyList:
                self.adjacencyList[firstVertex] = [ ]

            '''
            Add the second vertex to the adjacency list of the first vertex.
            '''
            self.adjacencyList[firstVertex].append(secondVertex)
        
        # creates and sort a set of vertices (removes duplicates)
        self.vertices = list(set(self.vertices))
        self.vertices.sort()

        # sort adjacency list for each vertex
        for vertex in self.adjacencyList:
            self.adjacencyList[vertex].sort()

    '''
    Begins the DFS algorithm.
    '''
    def DFSInit(self):
        # initially all vertices are considered unknown
        self.unVisitedVertices = list(set(self.vertices))
        # initialize path as an empty string
        self.path = ""

    '''
    depth-first traversal of specified graph
    '''
    def DFS(self, method):
        self.DFSInit()
        if method is 'recursive':
            for vertex in self.vertices:
                if vertex in self.unVisitedVertices:
                    self.DFS_recur(vertex)

            return self.path
        elif method is 'stack':
            for vertex in self.vertices:
                if vertex in self.unVisitedVertices: 
                    self.DFS_stack(vertex)
            
            return self.path
            

    def DFS_recur(self,vertex):
        # mark the vertex as visted
        self.unVisitedVertices.remove(vertex)

        # append vertex to the path
        self.path += vertex

        # repeat for each adjacent vertex that is unvisited
        for adjacentVertex in self.adjacencyList[vertex]:
            if adjacentVertex in self.unVisitedVertices:
                self.DFS_recur(adjacentVertex)



            
                
    def DFS_stack(self, vertex):
        stack=[]
        stack.append(vertex)
        while len(stack) > 0:
            vertex_ = stack.pop()
            if vertex_ in self.unVisitedVertices:
                self.unVisitedVertices.remove(vertex_)
                self.path += vertex_
                for adjacentVertex in self.adjacencyList[vertex_]:
                    if adjacentVertex in self.unVisitedVertices:
                        stack.append(adjacentVertex)





    def BFSInit(self):
        # initially all vertices are considered unknown
        self.unVisitedVertices = list(set(self.vertices))
        # initialize path as an empty string
        self.path = ""
        

    def BFS(self):
        self.BFSInit()
        for vertex in self.vertices:
            if vertex in self.unVisitedVertices:
                self.BFS_queue(vertex)

        return self.path

    def BFS_queue(self, vertex):
        queue = []
        queue.insert(0, vertex)
        while len(queue) > 0:
            vertex_ = queue.pop()
            if vertex_ in self.unVisitedVertices:
                self.path += vertex_
                self.unVisitedVertices.remove(vertex_)
            for adjacentVertex in self.adjacencyList[vertex_]:
                if adjacentVertex in self.unVisitedVertices:
                    self.path += adjacentVertex
                    self.unVisitedVertices.remove(adjacentVertex)
                    queue.insert(0, adjacentVertex)


    def hasCycle(self):
      self.DFSInit()
      immediateParent = {}
      for vertex in self.vertices:
          if vertex in self.unVisitedVertices:
              stack=[]
              stack.append(vertex)
              while len(stack) > 0:
                  vertex_ = stack.pop()
                  if vertex_ in self.unVisitedVertices:
                      self.unVisitedVertices.remove(vertex_)
                      self.path += vertex_
                      
                  for adjacentVertex in self.adjacencyList[vertex_]:
                      immediateParent[adjacentVertex] = vertex_
                      if adjacentVertex in self.unVisitedVertices:
                          stack.append(adjacentVertex)
                      if (adjacentVertex not in self.unVisitedVertices) and (immediateParent[vertex_] != adjacentVertex):
                          return True

      return False
