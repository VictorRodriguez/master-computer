from numpy import *
from numpy.random import *
import logging

class successor_function(object):
	@staticmethod	
	def func_suc(matrix):
	    logging.basicConfig(filename='example.log',level=logging.DEBUG)
	    moves = 4
	    logging.debug("Initial Matrix")
	    logging.debug(matrix)
	    size_matrix = matrix.size
	    length_matrix_x = matrix.shape[0]
	    length_matrix_y = matrix.shape[1]
	    successor = {}
	    new_matrix = matrix.copy()
	    final_matrix = matrix.copy()
	    set_move_up = 0
	    set_move_down = 0
	    set_move_left = 0
	    set_move_right = 0
	    for i in range(0, moves):
		for x in range(0, length_matrix_x):
		    for y in range(0, length_matrix_y):
		        if matrix[0][y] != 0:
		            if matrix[x][y] == 0 and set_move_up == 0:
		                new_matrix[x, y] = matrix[x-1][y]
		                final_matrix[x-1, y] = 0
		                final_matrix[x, y] = new_matrix[x, y]
		                logging.debug("up")
		                logging.debug(final_matrix)
		                successor["up"] = final_matrix
		                set_move_up = 1
		                new_matrix = matrix.copy()
		                final_matrix = matrix.copy()
		        #Move down
		        if matrix[length_matrix_x-1][y] != 0:
		            if matrix[x][y] == 0 and set_move_down == 0:
		                new_matrix[x, y] = matrix[x+1][y]
		                final_matrix[x+1, y] = 0
		                final_matrix[x, y] = new_matrix[x, y]
		                logging.debug("down")
		                logging.debug(final_matrix)
		                successor["down"] = final_matrix
		                set_move_down = 1
		                new_matrix = matrix.copy()
		                final_matrix = matrix.copy()
		        #Move left
		        if matrix[x][0] != 0:
		            if matrix[x][y] == 0 and set_move_left == 0:
		                new_matrix[x, y] = matrix[x][y-1]
		                final_matrix[x, y-1] = 0
		                final_matrix[x, y] = new_matrix[x, y]
		                logging.debug("left")
		                logging.debug(final_matrix)
		                successor["left"] = final_matrix
		                set_move_left = 1
		                new_matrix = matrix.copy()
		                final_matrix = matrix.copy()
		        #Move right
		        if matrix[x][length_matrix_y-1] != 0:
		            if matrix[x][y] == 0 and set_move_right == 0:
		                new_matrix[x, y] = matrix[x][y+1]
		                final_matrix[x, y+1] = 0
		                final_matrix[x, y] = new_matrix[x, y]
		                logging.debug("right")
		                logging.debug(final_matrix)
		                successor["right"] = final_matrix
		                set_move_right = 1
		                new_matrix = matrix.copy()
		                final_matrix = matrix.copy()
	    return successor


