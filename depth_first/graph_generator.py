
import pydot

class Graph_Gen(object):

	@staticmethod
	def gen_dot(path):

		if not path:
			exit(0)
			#path = [1, 2, 2, 4, 4, 8, 4, 9, 2, 5, 5, 10, 5, 11, 1, 3, 3, 6, 6, 12, 6, 13]
		
		graph = pydot.Dot(graph_type='graph')

		initial_index = 0 

		for indx, val in enumerate(path):
			if initial_index < len(path):
				edge_1 = path[initial_index] 
				edge_2 = path[initial_index + 1]

				initial_index = initial_index + 2 

				edge = pydot.Edge("%s" % str(edge_1) , "%s" % edge_2)
				graph.add_edge(edge)

		graph.write_png('result_graph.png')
