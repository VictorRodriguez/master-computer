from pyfann import libfann
from optparse import OptionParser

connection_rate = 1			#The connection rate controls how many connections
					#here will be in the network.  If the connection
					#rate is set to 1, the network will be fully connected,
					#but if it is set to 0.5
					#only half of the connections will be set.

learning_rate = 0.7			#determine how aggressive training should be for some
					#of the training algorithms
					#default value is 0.7

num_input = 8				#number of input neurons
num_hidden = 4050			#number of hidden neurons
num_output = 1				#number of output neurons

desired_error = 0.005
max_iterations = 300			#maximum number of epochs the training should continue
iterations_between_reports = 10		#number of epochs between printing a status report to stdout.
                               		#A value of zero means no reports should be printed.


if __name__ == "__main__":

	parser = OptionParser()
	parser.add_option("-n", "--num_hidden", dest="num_hidden",
                  help="number of hidden neurons")
	parser.add_option("-m", "--max_iterations", dest="max_iterations",
                  help="number of epochs between printing a status report to stdout")

	(options, args) = parser.parse_args()

	if options.num_hidden:
		num_hidden = int(options.num_hidden)
	if options.num_hidden:
		max_iterations = int(options.max_iterations)

	print "num_hidden " + str(num_hidden)
	print "max_iterations " + str(max_iterations)

	
	ann = libfann.neural_net() 	#Default constructor creates an empty neural net or
                           		#Creates a copy the other neural_net.

	ann.create_sparse_array(connection_rate, (num_input, num_hidden, num_output))
                           		#Creates a standard fully connected backpropagation
					#neural network. with an array of layer sizes instead
					#of individual parameters.

	ann.set_learning_rate(learning_rate)	#Set the learning rate

	ann.set_activation_function_output(libfann.SIGMOID)	
					#Set the activation function for the output layer.

	#ann.set_activation_function_output(libfann.LINEAR)

 

	# start training the network
	print "Training network"
	ann.train_on_file("diabetes.train", max_iterations, iterations_between_reports, desired_error)
					#Trains on an entire dataset, for a period of time
					#from a file

	ann.save("diabetes.net")	#Save the entire network to a configuration file.


	# test outcome
	print "Testing network"
	ann_train = libfann.training_data()
	ann_train.read_train_from_file("diabetes.test")

	#ann.create_from_file("diabetes.net") #Constructs a backpropagation neural network
                                #from a configuration file, which have been
                                #saved by save.

	ann.reset_MSE()
	ann.test_data(ann_train)
	print "MSE error on test data: %f" % ann.get_MSE()
	ann.save("diabetes_net.net")
