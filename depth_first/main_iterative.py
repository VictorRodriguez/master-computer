from graph_generator import Graph_Gen
from numpy import *
from numpy.random import *
from funcionsucesor import successor_function
import logging
from optparse import OptionParser

GOAL = matrix('1 2 3; 4 5 6; 7 8 0')
DEPTH = 4

def error(error):
	if error == "fringe":
		print "Fringe Empty"
	elif error == "successors":
		print "There is no more successors"
	elif error == "node":
		print "Node not found"
	elif error == "matrix":
		print "Matrix not found"
	else:
		print error
		print "Error not found"

def empty(list):
	if not list:
		return True
	else:
		return False

def push(node,fringe):
	fringe.append(node)
	return fringe 
	
def end(node,dictionary):
	print "You have reach the goal"
	print "Node -> " + str(node)
	print "Matrix -> "
	print get_matrix(node,dictionary)
	Graph_Gen.gen_dot(path)
	exit(0)

def goal(node,dictionary_of_matrixs):
	if (get_matrix(node,dictionary_of_matrixs) == GOAL).all():
		return True	
	else:
		return False

def pop(queue):
	return queue.pop()


def loop(fringe,path,dictionary_of_matrixs):

	level = 0 
	states_level = {}

	while not empty(fringe):
		list_of_succ_matrix = None

		level = level + 1

		states_level = add_level(states_level,fringe,level)

		# check if fringe is empty and level < level goal, if not analyse
		node_to_analyze = pop(fringe)

		# get successors
		matrix_to_analyze = get_matrix(node_to_analyze,dictionary_of_matrixs)
		list_of_succ_matrix = successor_function.func_suc(matrix_to_analyze)
	
		# add successors to dictionary of matrixs (memory of states)
		for key,value in list_of_succ_matrix.iteritems():
			dictionary_of_matrixs = add_matrix_to_dict(value,dictionary_of_matrixs)

		# generate list of successors ( as nodes )
		list_of_successors = []

		for key_succ,value_succ in list_of_succ_matrix.iteritems():
			for key,value in dictionary_of_matrixs.iteritems():	
				if (value_succ == value).all() and int(key) not in path :
					list_of_successors.append(int(key))

		if empty(list_of_successors): 
			error("successors")

		# if any successor is goal finish, else push to queue  
		else:
			for state in list_of_successors:
				print "Analyzing state => " + str(state)
				logging.debug("Analyzing state => " + str(state))
				path.append(node_to_analyze)
				path.append(state)
				if goal(state,dictionary_of_matrixs):
					end(state,dictionary_of_matrixs)	
				else:
					# has some branch reach the level?
					if level < DEPTH and level >= 0 :
						push(state,fringe)
					else:
						continue

			if level == DEPTH:
				print "I reach the limit -> " + str(level)
				logging.debug("I reach the limit -> " + str(level))
				if fringe:
					level = get_level(fringe[-1],states_level) - 1
				
		

def get_level(state,states_level):
	return states_level[state]

def add_level(states_level,fringe,level):
	for state_fringe in fringe:
		if not state_fringe in states_level:
				states_level[state_fringe] = level
	return states_level

def add_matrix_to_dict(matrix,dictionary):
	for key,value in dictionary.iteritems():
		if (matrix == value).all():
			return dictionary

	dictionary[str(len(dictionary)+1)] = matrix
	return dictionary

def get_node(dictionary,searched_matrix):
	for node, matrix in dictionary.iteritems():
		if (matrix == searched_matrix).all():
			return node
		else:
			error("node")
			return None

def get_matrix(searched_node,dictionary):
	for node, matrix in dictionary.iteritems():
		if int(node) == int(searched_node):
			return matrix


if __name__ == "__main__":

	global DEEPTH

	parser = OptionParser()
	parser.add_option("-d", "--deepth", dest="deepth",
                  help="Deepth of the search")
	parser.add_option("-m", "--matrix", dest="matrix",
                  help="Initial matrix")
	(options, args) = parser.parse_args()


	if options.deepth:
		DEPTH = int(options.deepth)

	if options.matrix:
		matrix_list = options.matrix.split(',')
		initial_matrix = array([[1,2,3], [4,0,5], [7,8,6]])
		print
	# logging
	with open('debug.log', 'w'):
		pass
	logging.basicConfig(filename='debug.log',level=logging.DEBUG)

	# set up of initial matrix 
	initial_matrix = array([[1,2,3], [4,0,5], [7,8,6]])

	if options.matrix and len(options.matrix.split(',')) == 9:
		matrix_list = options.matrix.split(',')
		initial_matrix = \
		array([[int(matrix_list[0]),int(matrix_list[1]),int(matrix_list[2])], \
		[int(matrix_list[3]),int(matrix_list[4]),int(matrix_list[5])],\
		[int(matrix_list[6]),int(matrix_list[7]),int(matrix_list[8])]])

	print "initial matrix"
	print initial_matrix

	
	#map matrix to node
	dictionary_of_matrixs = {}
	dictionary_of_matrixs = add_matrix_to_dict(initial_matrix,dictionary_of_matrixs)
	nodes = [int(get_node(dictionary_of_matrixs,initial_matrix))]
	fringe = []
	node_to_analyze = None
	node = pop(nodes)
	path = []

	# check if you reach the goal 
	if goal(node,dictionary_of_matrixs):
		end(node,dictionary_of_matrixs)
	else:
		fringe = push(node,fringe)

	# check if fringe is empty , if not analyse
	loop(fringe,path,dictionary_of_matrixs)

	# plot ( if GOAL not found) 
	Graph_Gen.gen_dot(path)
