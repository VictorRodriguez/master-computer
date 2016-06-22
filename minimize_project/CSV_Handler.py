import csv


class CSV_Handler():

	@staticmethod
	def get_rows_length(file):
		f = open(file, 'rb')
		try:
			num_rows = 0
			reader = csv.reader(f)
			for row in reader:
				num_rows = num_rows + 1
			#print num_rows
			return num_rows
		finally:
			f.close()

	@staticmethod
	def get_columns_length(file):
		f = open(file, 'rb')
		try:
			num_cols = 0 
			reader = csv.reader(f)

			first_row = next(reader)
			num_cols = len(first_row)

			#print num_cols
			return num_cols
		finally:
			f.close()
	
	@staticmethod
	def generate_matrix(file):
		f = open(file, 'rb')
		try:
			reader = csv.reader(f)
			num_rows = CSV_Handler.get_rows_length(file)
			num_cols = CSV_Handler.get_columns_length(file)
			Matrix = [[0 for x in xrange(num_cols)] for x in xrange(num_rows)]
			
			# Fill matrix
			# Each row is a state 
			# State,Transition_0,Transition_1,...,Trancition_E
			count_row = 0
			for row in reader:
				Matrix[count_row] = row
				count_row = count_row + 1

			#print Matrix
			return Matrix
		finally: 
			f.close()
	
	@staticmethod
	def fill_csv(matrix,file): 
		f = open(file, 'wb')
		try:
			writer = csv.writer(f, delimiter=',', quotechar='"', quoting=csv.QUOTE_ALL)
			writer.writerows(matrix)
		finally: 
			f.close()

