'''
@author: Kyle Beard
'''
import unittest

import Graph

class Test(unittest.TestCase):

    ga1 = Graph.GraphAlgorithms('graph-1.txt')
    ga2 = Graph.GraphAlgorithms('graph-2.txt')
    ga3 = Graph.GraphAlgorithms('graph-3.txt')
    ga4 = Graph.GraphAlgorithms('graph-4.txt')

    def testDFS_recursive(self):
        self.assertEqual('abefcgdhij', self.ga1.DFS('recursive'))
        self.assertEqual('abefcgdhijklmon', self.ga2.DFS('recursive'))
        self.assertEqual('acdfbeghij', self.ga3.DFS('recursive'))
        self.assertEqual('abdecf', self.ga4.DFS('recursive'))


    def testDFS_stack(self):
        self.assertEqual('aijdhcgfbe', self.ga1.DFS('stack'))
        self.assertEqual('aijdhcgfbekmoln', self.ga2.DFS('stack'))
        self.assertEqual('aefcdbgjih', self.ga3.DFS('stack'))
        self.assertEqual('acfbed', self.ga4.DFS('stack'))
        


    def testBFS(self):
        self.assertEqual('abcdiefghj', self.ga1.BFS())
        self.assertEqual('abcdiefghjklmno', self.ga2.BFS())
        self.assertEqual('acdefbghji', self.ga3.BFS())
        self.assertEqual('abcdef', self.ga4.BFS())
        
        


    def testhasCycle(self):
        self.assertTrue(self.ga1.hasCycle())
        self.assertTrue(self.ga2.hasCycle())
        self.assertTrue(self.ga3.hasCycle())
        self.assertFalse(self.ga4.hasCycle())
      
        


    # Uncomment and work on this test for at most 10 extra points
    # def testshortestpath(self):
    #     self.assertEqual(2, self.ga1.shortestpath('a','f'))
    #     # Please add test cases for graph2, graph3, and graph4
    #     # Your code goes here:
        


if __name__ == "__main__":
    unittest.main()