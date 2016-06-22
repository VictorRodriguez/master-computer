import os
import subprocess

EXECFILE="python main.py"


# --------------------------------------------------------------------------
# Class Name: Process
# Purpose   : Wrapper to execute OS commands
# --------------------------------------------------------------------------
import sys
import subprocess
import shlex
import shutil

class Process(object):
	@staticmethod
	def run_with_output(command):
		if "linux" in  sys.platform:
			proc = subprocess.Popen([command], shell=True, stdout=subprocess.PIPE,\
			stderr=subprocess.STDOUT, close_fds=True)
			return subprocess.Popen.communicate(proc)[0]
		else:
			retcode = subprocess.Popen(command,stdout=subprocess.PIPE,\
			stderr=subprocess.STDOUT)
		return subprocess.Popen.communicate(retcode)[0]
	
	@staticmethod
	def run_with_retcode(command):
		if "linux" in  sys.platform:
			retcode = subprocess.call([command], shell=True, stdout=subprocess.PIPE,\
			stderr=subprocess.STDOUT, close_fds=True)
			return retcode
		else:
			retcode = subprocess.call(command,stdout=subprocess.PIPE,\
			stderr=subprocess.STDOUT)
			return retcode

	@staticmethod
	def run_with_retcode_and_output(command):
		if "linux" in  sys.platform:
			retcode = subprocess.call([command], shell=True, stdout=subprocess.PIPE,\
			stderr=subprocess.STDOUT, close_fds=True)
			return retcode,subprocess.Popen.communicate(retcode)[0]
		else:
			sp = subprocess.Popen(command, stdout=subprocess.PIPE,\
			stderr=subprocess.PIPE)
			out, err = sp.communicate()
			return sp.returncode,out,err
			

def write_logfile(string,file):
	f = open(file,"w")
	f.write(string)
	f.close


if __name__ == '__main__':

	# fill arrays

	num_hidden_array = []
	max_iterations_array = []

	for value in range(100,5100,100):
		num_hidden_array.append(value)
		
	for value in range(10,510,10):
		max_iterations_array.append(value)

	
	for num_hidden in num_hidden_array:
		for max_iterations in max_iterations_array: 
			print "num_hidden = " + str(num_hidden)
			print "max_iterations = " + str(max_iterations)
			cmd = EXECFILE + " -n " + str(num_hidden) + " -m " + str(max_iterations)
			print "cmd = " + cmd
			output = Process.run_with_output(cmd)
			print output
			dst = "experiment_hidden_"+str(num_hidden)+"_iterations_" \
			+str(max_iterations) + ".txt"
			write_logfile(output,dst)
