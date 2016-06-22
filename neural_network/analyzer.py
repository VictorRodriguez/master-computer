import glob
import csv


def get_my_string(file):
    try:
        with open(file) as inputFileHandle:
            return inputFileHandle.read()

    except IOError:
        sys.stderr.write( "[myScript] - Error: Could not open %s\n" % (file) )
        sys.exit(-1)


if __name__ == '__main__':


	num_hidden_array = []
	max_iterations_array = []

	for value in range(100,5100,100):
		num_hidden_array.append(value)
		
	for value in range(10,510,10):
		max_iterations_array.append(value)


	num_rows = len(num_hidden_array)
	num_cols = len(max_iterations_array)
	Matrix = [[0 for x in xrange(num_rows)] for x in xrange(num_cols)]
	
	for file in glob.glob("*.txt"):
		
		hidden = file.split("_")[2]
		iterations = file.split("_")[4].replace(".txt","")
	
		file_data = get_my_string(file)
		file_data_array = file_data.split('\n')
		for line in file_data_array: 
			if "MSE error on test data:" in line: 
				mse_error = line.split("MSE error on test data:")[1].strip()
				Matrix[num_hidden_array.index(int(hidden))][max_iterations_array.index(int(iterations))] = (mse_error)

	print Matrix
	
	try:
		myfile = open('mse_error.csv', 'wb')
		wr = csv.writer(myfile, quoting=csv.QUOTE_ALL)
		for column in range (0,num_cols):
			print Matrix[column]
			wr.writerow(Matrix[column])
	finally: 
		myfile.close()

	
