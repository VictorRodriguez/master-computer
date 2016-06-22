"""@package docstring
	Evil NDA to FDA
"""
import logging
import optparse
import sys
import os

from CSV_Handler import CSV_Handler

csv_file = None

def union_clee_states(matrix):
	"""
	Union the value of each state
	
	{q0} U CLEE{q0} = q0{q1}  
		and if one of the values has a clee add it 
	{q1} U CLEE{q0} = q1q2q3
	then 
		q0} U CLEE{q0} = q0q1q2q3 
	"""

	num_rows = CSV_Handler.get_rows_length(csv_file)
	num_cols = CSV_Handler.get_columns_length(csv_file)
	
	first_matrix = {}
	second_matrix = {}

	
	for row in range(1,num_rows):
		first_matrix[matrix[row][0]] = matrix[row][0] + matrix[row][num_cols-1]

	for base_state, base_state_value in first_matrix.iteritems() :
		logging.debug(base_state, base_state_value)
		# check all the states if they can be substitute
		for state, state_value in first_matrix.iteritems() :
			if state in base_state_value and state != base_state: 
				logging.debug( "I found a " + str(state))
				logging.debug( "replace with ")
				new_value = first_matrix.get(state)
				logging.debug( new_value)
				second_matrix[base_state] = base_state_value.replace(state,new_value)
		
	for state, state_value in first_matrix.iteritems() :
		if state not in second_matrix:
			second_matrix[state] = state_value

	#this matrix is the union of the states and clee 
	return second_matrix


def get_matrix_value(column_value,row_value,matrix):
	"""Get the value of the main matrix"""
	num_rows = CSV_Handler.get_rows_length(csv_file)
	matrix = CSV_Handler.generate_matrix(csv_file)
	
	row_value_index = 0
	column_value_index = 0 
	
	row_index_count = 0
	for row in range(1,num_rows):
		row_index_count = row_index_count + 1
		if row_value == matrix[row][0]:
			row_value_index = row_index_count
			break
	
	column_value_index = matrix[0].index(column_value)
	value = matrix[row_value_index][column_value_index]
	return value



def get_clee(state_value,matrix,union_dictionary):
	"""
	This method return the clee closure of each state value

	state value : {q0 q4} => q0 : q0q1q2q3 / q4 : q4q3
	"""
	num_rows = CSV_Handler.get_rows_length(csv_file)
	num_cols = CSV_Handler.get_columns_length(csv_file)

	clee_state_list = []
	
	for row in range(1,num_rows):
		if matrix[row][0] in state_value:
			clee_value =  union_dictionary[matrix[row][0]]
			clee_state_list.append(clee_value)

	logging.debug("clee_state_list")
	logging.debug(clee_state_list)
	return clee_state_list

def clean_list(list):
	"""Clean the list from duplicated values"""
	num_rows = CSV_Handler.get_rows_length(csv_file)
	matrix = CSV_Handler.generate_matrix(csv_file)
	
	final_list = []
	for value in list:
		for row in range(1,num_rows):
			state =  matrix[row][0]
			if state in str(value) and state not in final_list:
				final_list.append(state)

	logging.debug("initial_list --> " + str(list))
	logging.debug("final_list --> " + str(final_list))
	return final_list

def get_clee_of_union(union_dictionary,transition):
	"""Get the clee closure of the union of the states and clee state"""
	
	clee_state_dict = {}
	master_clee_state_dict = {}

	num_rows = CSV_Handler.get_rows_length(csv_file)
	matrix = CSV_Handler.generate_matrix(csv_file)

	for state, state_value in union_dictionary.iteritems():
		clee_state_list = []

		for row in range(1,num_rows):
			state_to_search = matrix[row][0]
			#if I found the the state on the union of states, get the value for (a)
			if state_to_search in state_value: 
				matrix_value = get_matrix_value(transition,state_to_search,matrix)
				logging.debug("state -> " + state_to_search)
				logging.debug("state_value -> " + state_value)
				logging.debug("matrix_value " + matrix_value)
				#get "closure de clee" of each state on state_value
				clee_state_list.append(get_clee(matrix_value,matrix,union_dictionary))
				logging.debug("list -> " + str(clee_state_list))
				
			clee_state_dict[state_value] = clean_list(clee_state_list)
			
		master_clee_state_dict[state] = clee_state_dict[state_value]
		
		logging.debug("clee_state_dict")
		logging.debug(clee_state_dict)

	return master_clee_state_dict

	
def get_final_matrix_without_clee(transition_dict):
	"""Get the final matrix without clee closure (not reduced yet)"""
	matrix = CSV_Handler.generate_matrix(csv_file)
	num_rows = CSV_Handler.get_rows_length(csv_file)
	num_cols = CSV_Handler.get_columns_length(csv_file)
	
	new_num_cols = num_cols - 2
	new_num_rows = num_rows - 1 
	
	final_matrix_without_clee = [[0 for x in xrange(new_num_cols)] for x in xrange(new_num_rows)]

	state_count = 0 
	transition_count = 0

	#Fill first matrix
	for column in range(1,num_cols -1 ):
		transition = matrix[0][column]
		state_count = 0 
		for row in range(1,num_rows):
			state = matrix[row][0]
			for key, value in transition_dict[transition].iteritems() :
				if state == key:
					final_matrix_without_clee[state_count][transition_count] = ', '.join(value)
					state_count = state_count + 1
		transition_count = transition_count + 1
	return final_matrix_without_clee

def fill_final_matrix(start_state_with_out_clee,final_matrix_without_clee,acepting_states_list):
	"""Fill Final list"""
	num_rows = CSV_Handler.get_rows_length(csv_file)
	matrix = CSV_Handler.generate_matrix(csv_file)
	
	states_to_search = []
	states_to_search.append(start_state_with_out_clee)
	
	for state_to_search in states_to_search:
		for transition_count in range(0,len(final_matrix_without_clee[0])):
			logging.info("state_to_search " + str(state_to_search))
			state_position = 0
			tmp_final_list = []
			for row in range(1,num_rows):
				state =  matrix[row][0]
				if state in state_to_search:
					logging.info("transition_count "  + str(transition_count))
					tmp_final_list.append(final_matrix_without_clee[state_position][transition_count])
				state_position = state_position + 1

			new_state = ''.join(clean_list(tmp_final_list))
			logging.info("almost_new_state " + str(new_state))
				
			if new_state not in states_to_search and new_state != "":
				logging.info("nuevo")
				logging.info("new state -> " + new_state )
				states_to_search.append(new_state)


	# Create empty matrix with the size of final_matrix_without_clee and states_to_search
	final_matrix = [[0 for x in xrange(len(final_matrix_without_clee[0])+ 1)] for x in xrange(len(states_to_search))]
	
	# Add new deterministic states
	row_count = 0 
	for row in final_matrix:
		row[0] = states_to_search[row_count]
		row_count = row_count + 1

	logging.info("states_to_search")
	logging.info(states_to_search)

	new_accepting_state = []
	# Find new accepting states
	for old_accepting_state in acepting_states_list:
		for current_accepting_state in states_to_search:
			if str(old_accepting_state) in str(current_accepting_state):
				new_accepting_state.append(current_accepting_state)
	
	tmp_new_accepting_state = list(set(new_accepting_state))
	new_accepting_state = []
	
	for state in tmp_new_accepting_state:
		new_accepting_state.append(states_to_search.index(state))
	
	# Fill rest of matrix
	for transition_count in range(0,len(final_matrix_without_clee[0])):
		for state_to_search in states_to_search:
			logging.debug("state_to_search " + str(state_to_search))
			state_position = 0
			tmp_final_list = []
			for row in range(1,num_rows):
				state =  matrix[row][0]
				if state in state_to_search:
					logging.debug("transition_count "  + str(transition_count))
					tmp_final_list.append(final_matrix_without_clee[state_position][transition_count])
				state_position = state_position + 1

			new_state = ''.join(clean_list(tmp_final_list))
			logging.debug("almost_new_state " + str(new_state))
			logging.debug("states_to_search.index(state_to_search) " + str(states_to_search.index(state_to_search)))
			logging.debug("transition_count " + str(transition_count + 1))
			final_matrix[states_to_search.index(state_to_search)][transition_count + 1] = new_state
			logging.debug(final_matrix)
	
	return final_matrix,new_accepting_state

def clean_final_matrix(final_matrix):
	
	new_final_matrix = final_matrix
	initial_and_future_states = {}
	
	states_count = 0 
	row_count = 0
	
	for row in final_matrix:
		basic_state = row[0]
		initial_and_future_states[basic_state] = states_count
		new_final_matrix[row_count][0] = states_count
		states_count = states_count +1
		row_count = row_count + 1 

	for row_count in range(0,len(final_matrix)):
		for column_count in range(1,len(final_matrix[0])):
			state = final_matrix[row_count][column_count]
			if state != "":
				new_final_matrix[row_count][column_count] = initial_and_future_states[state]
	
	return new_final_matrix

def add_inferno_state(new_final_matrix):

	for row_count in range(0,len(new_final_matrix)):
		for column_count in range(1,len(new_final_matrix[0])):
			state = new_final_matrix[row_count][column_count]
			if state == "":
				inferno_state_flag = True
				new_final_matrix[row_count][column_count] = len(new_final_matrix)
	
	inferno_row = []
	
	for column_count in range(0,len(new_final_matrix[0])):
		inferno_row.append(len(new_final_matrix))
	
	new_final_matrix.append(inferno_row)
	
	return new_final_matrix

if __name__ == '__main__':

	NDA_flag = False
	DFA_flag = False
	start_state = None
	output_file = "DFA.csv"
	acepting_states = None
	acepting_states_list = []
	
	parser = optparse.OptionParser()
	parser.add_option('--start_state', dest='start_state', help='Specify the  start state')
	parser.add_option('--acepting_states', dest='acepting_states', help='Specify the accepting states ie: "q0 q1 q2"')
	parser.add_option('--input_file', dest='csv_file', help='Specify the file that describe the NDA ( empty transition = "")')
	parser.add_option('--kind', dest='kind', help='NDA or DFA')

	(options, args) = parser.parse_args()

	# Assign variables

	if not options.start_state: 
		print "============================="
		print "Missing start states"
		print "============================="
		parser.print_help()
		sys.exit(1)
	else:
		start_state = options.start_state
		print "============================="
		print "start => %s " % (start_state)
		print "============================="

	if not options.acepting_states:
		print "============================="
		print "Missing accepting states"
		print "============================="
		parser.print_help()
		sys.exit(1)
	else:
		acepting_states = options.acepting_states
		acepting_states_list = acepting_states.split(" ")
		print "============================="
		print "accepting states => %s " % (acepting_states)
		print "============================="

	if not options.csv_file:
		print "============================="
		print "Missing csv_file"
		print "============================="
		parser.print_help()
		sys.exit(1)
	else:
		csv_file = options.csv_file
		print "============================="
		print "csv_file = > %s " % (csv_file)
		print "============================="

	if options.kind == "NDA":
		NDA_flag = True
		print "============================="
		print "Working with a NDA"
		print "============================="
	elif options.kind == "DFA":
		DFA_flag = True 
		print "============================="
		print "Working with a DFA"
		print "============================="
	else: 
		parser.print_help()
		sys.exit(1)

	# change this to level=logging.DEBUG to print everything for debug
	logging.getLogger().setLevel(logging.WARNING)
	
	# Print Initial matrix
	CSV_Handler()
	matrix = CSV_Handler.generate_matrix(csv_file)
	num_rows = CSV_Handler.get_rows_length(csv_file)
	num_cols = CSV_Handler.get_columns_length(csv_file)
	
	print 
	print "Initial matrix"
	for row in matrix:
		print row
		
	# Get the union of clee and states
	union_dictionary = union_clee_states(matrix)
	logging.info("Union of clee and states")
	logging.info(union_dictionary)

	transition_dict = {}
	clee_state_dict = {}

	# Get the matrix for each clee result transition value
	for column in range(1,num_cols -1 ):
		transition = matrix[0][column]
		clee_state_dict = get_clee_of_union(union_dictionary,transition)
		transition_dict[transition] = clee_state_dict

	# Get final matrix ( funcion de transicion aumentada ) 
	final_matrix_without_clee = get_final_matrix_without_clee(transition_dict)
	
	logging.info("final_matrix_without_clee")
	logging.info(final_matrix_without_clee)
	logging.debug(final_matrix_without_clee)
	
	final_matrix_without_clee_reduced = {}
	
	start_state_with_out_clee = union_dictionary[start_state]
	logging.debug(start_state_with_out_clee)

	final_matrix,new_accepting_state = fill_final_matrix(start_state_with_out_clee,final_matrix_without_clee,acepting_states_list)
	print
	print "Final AFD"
	for row in final_matrix:
		print row
		
	# Clean final matrix
	new_final_matrix = clean_final_matrix(final_matrix)
	print
	print "Final Clean AFD"
	for row in new_final_matrix:
		print row

	if NDA_flag:
		# Add inferno state
		new_final_matrix = add_inferno_state(new_final_matrix)
		print
		print "Final Clean AFD with inferno state"
		for row in new_final_matrix:
			print row

	temp_header_list = []
	temp_header_list.append("State")
	for column_count in range(1,len(new_final_matrix[0])):
		header_str = "Transition_%s" % (column_count-1)
		temp_header_list.append(header_str)
		
	new_final_matrix.insert(0,temp_header_list)
	
	# write into csv  file
	CSV_Handler.fill_csv(new_final_matrix,output_file)
	
	print "============================="
	print "DFA on file =>  %s " % (output_file)
	print "============================="

	print "============================="
	print "New Accepting States =>  %s " % (new_accepting_state)
	print "============================="
	
	str_accepting = ""
	for accepting in new_accepting_state: 
		str_accepting = str_accepting + " " + str(accepting)
	cmd = "python minimAFD.py --file DFA.csv --accepting_states '%s'" % str_accepting[1:]
	os.system("python minimAFD.py --file DFA.csv --accepting_states%s" % str_accepting)