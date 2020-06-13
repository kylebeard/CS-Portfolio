import numpy as np
from heapq import heappush, heappop
from animation import draw

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
    
    def __init__(self, start_state, goal_state, algorithm, array_index):
        self.start_state = start_state
        self.goal_state = goal_state
        self.visited = [] 
        self.algorithm = algorithm
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
        return node.cost_from_start


    
    # draw 
    # you do not need to modify anthing in this function.
    def draw(self, node):
        path=[]
        while node.parent:
            path.append(node.state)
            node = node.parent
        path.append(self.start_state)
        print(path)
        draw(path[::-1], self.array_index, self.algorithm)

    # solve it
    def solve(self):
        if self.goal_test(self.start_state): 
            return print("Puzzle is already in goal state.")

        container = [] # node
        count = 1
        depth_limit = 15
        state = self.start_state.copy()
        node = Node(state, 0, None)
        self.visited.append(state)
        if self.algorithm == 'Depth-Limited-DFS': 
            container.append(node)

        elif self.algorithm == 'BFS': 
            container.append(node)

        elif self.algorithm == 'UCS': 
            heappush(container, (self.priority(node), count, node))
            count += 1

        while container:
            # if one solution is found, call self.draw(current_node) to show and save the animation. 
            if self.algorithm == 'Depth-Limited-DFS':
                node = container.pop()
            elif self.algorithm == 'BFS':
                node = container.pop(0)
            elif self.algorithm == 'UCS':
                node = heappop(container)
                # without this it didnt work because node was a tuple object, so I had to get the node object in the tuple
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
                    
                    if self.algorithm == 'Depth-Limited-DFS': 
                        if node.cost_from_start <= depth_limit:
                            container.append(next_node)
                            self.visited.append(next_state)

                    elif self.algorithm == 'BFS': 
                        container.append(next_node)
                        self.visited.append(next_state)

                    elif self.algorithm == 'UCS': 
                        heappush(container, (self.priority(next_node), count, next_node))
                        count += 1
                        self.visited.append(next_state)


if __name__ == "__main__":
    
    goal = np.array([[1,2,3],[4,5,6],[7,8,0]])

    start_arrays = [np.array([[0,1,3],[4,2,5],[7,8,6]]), 
                    np.array([[0,2,3],[1,4,6],[7,5,8]])] 

    algorithms = ['Depth-Limited-DFS', 'BFS', 'UCS']
    
    for i in range(len(start_arrays)):
        # for j in range(len(algorithms)):
        game = EightPuzzle(start_arrays[i], goal, algorithms[2], i)
        game.solve()
