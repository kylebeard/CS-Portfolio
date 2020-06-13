import numpy as np
from heapq import heappush, heappop
from animation import draw
import argparse

class Node():
    """
    cost_from_start - the cost of reaching this node from the starting node
    state - the state (row,col)
    parent - the parent node of this node, default as None
    """
    def __init__(self, state, cost_from_start, parent = None):
        self.state = state
        self.parent = parent
        self.cost_from_start = cost_from_start


class EightPuzzle():
    
    def __init__(self, start_state, goal_state, method, algorithm, array_index):
        self.start_state = start_state
        self.goal_state = goal_state
        self.visited = [] # state
        self.method = method
        self.algorithm = algorithm
        self.m, self.n = start_state.shape 
        self.array_index = array_index
        

  # goal test function
    def goal_test(self, current_state):
        if np.array_equal(current_state,self.goal_state):
            return True
        else:
            return False

    # get cost function
    def get_cost(self, current_state, next_state):
        return 1


    # get successor function
    def get_successors(self, state):
        successors = []
        # get the position of the empty node
        p, q = None, None
        for row_ in range(len(state)):
            for col_ in range(len(state[row_])):
                if state[row_][col_] == 0:
                    # position of the empty node
                    p, q = row_, col_

        row = [-1,1,0,0]
        col = [0,0,-1,1]
        for i in range(4):
            nextp, nextq = p+row[i], q+col[i]
            if 0 <= nextp <= 2 and 0 <= nextq <= 2:
                temp_state = state.copy()
                temp_node = temp_state[nextp][nextq]
                temp_state[nextp][nextq] = 0
                temp_state[p][q] = temp_node
                successors.append(temp_state)       
        
        return successors

    # get priority of node for UCS
    def priority(self, node):
        if self.algorithm == 'Greedy':
            return self.heuristics(node.state)
        elif self.algorithm == 'AStar':
            return (self.heuristics(node.state) + node.cost_from_start)



    
    # heuristics function
    def heuristics(self, state):
        positions = [(0,0), (0,1), (0,2), 
                     (1,0), (1,1), (1,2), 
                     (2,0), (2,1), (2,2)]
        total_distance = 0
        if self.method == 'Manhattan':
            for i in range(8):
                row, col = np.where(state == (i+1))
                temp_pos = positions[i]
                row_distance = abs(row - temp_pos[0])
                col_distance = abs(col - temp_pos[1])
                total_distance += (row_distance + col_distance)
            
            return total_distance

        elif self.method == "Hamming":
            misplaced_tiles = 0
            for i in range(8):
                row, col = np.where(state == (i+1))
                temp_pos = positions[i]
                if (row, col) != temp_pos:
                    misplaced_tiles += 1

            return misplaced_tiles
       
    # draw 
    def draw(self, node):
        path=[]
        while node.parent:
            path.append(node.state)
            node = node.parent
        path.append(self.start_state)

        draw(path[::-1], self.array_index, self.algorithm, self.method)

    # solve it
    def solve(self):
        if self.goal_test(self.start_state): 
            return print("Puzzle is already in goal state.")

        container = [] # node
        count = 1
        state = self.start_state.copy()
        node = Node(state, 0, None)
        self.visited.append(state)

        heappush(container, (self.priority(node), count, node))
        count += 1
        

        while container:
            node = heappop(container)
            node = node[2]
            
            successors = self.get_successors(node.state)

            for next_state in successors:

                # check if successor has already been visited
                state_visited = False
                for state_ in self.visited:
                    if np.array_equal(next_state, state_):
                        state_visited = True
                
                if state_visited is False:
                    next_cost = node.cost_from_start + self.get_cost(node.state, next_state)
                    next_node = Node(next_state, next_cost, node)

                    if self.goal_test(next_state) is True:
                        self.draw(next_node)
                        return
                    
                  
                    heappush(container, (self.priority(next_node), count, next_node))
                    count += 1
                    self.visited.append(next_state)

            
    

if __name__ == "__main__":
    
    goal = np.array([[1,2,3],[4,5,6],[7,8,0]])
    start_arrays = [np.array([[1,2,0],[3,4,6],[7,5,8]]),
                    np.array([[8,1,3],[4,0,2],[7,6,5]])]
    methods = ["Hamming", "Manhattan"]
    algorithms = ['Greedy', 'AStar']
    
    parser = argparse.ArgumentParser(description='eight puzzle')

    parser.add_argument('-array', dest='array_index', required = True, type = int, help='index of array')
    parser.add_argument('-method', dest='method_index', required = True, type = int, help='index of method')
    parser.add_argument('-algorithm', dest='algorithm_index', required = True, type = int, help='index of algorithm')

    args = parser.parse_args()

    # Example:
    # Run this in the terminal using array 0, method Hamming, algorithm AStar:
    #     python eight_puzzle.py -array 0 -method 0 -algorithm 4
    game = EightPuzzle(start_arrays[args.array_index], goal, methods[args.method_index], algorithms[args.algorithm_index], args.array_index)
    game.solve()
