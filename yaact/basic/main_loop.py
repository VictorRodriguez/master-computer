import random 
import os
from libs.Process import Process

GOAL = 100
coverage = 0 
iteration = 0

while (coverage < GOAL and iteration < 10):

	print 
	print "#--------------------------------------#"
	print "Start"
        print "#--------------------------------------#" 


	#clean
	os.system("make clean")
	
	#build for coverage
	os.system("make coverage")

	#get genetic
	secuence = random.sample([1, 2, 3, 4, 5, 6, 7, 8, 9,10], 4)
	print secuence 

	# run tests
	cmd = ""

	for test in secuence: 
		cmd = "tests/test_%d.sh " % test
		os.system(cmd)

	#get coverage
	out = Process.run_with_output("make get_coverage")
	print out 
	coverage = out.split('\n')
	for line in  out.split('\n'):
		if "Lines executed" in line:
			first = ":"
			last = "%"
			start = line.index( first ) + len( first )
        		end = line.index( last, start )
        		coverage = float(line[start:end])
	
	print "Current Coverage = "
	print coverage
	print 
	iteration += 1


print "Best Coverage = "
print coverage
print 
print 
