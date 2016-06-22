# --------------------------------------------------------------------------
# Class Name: Process
# Purpose   : Wrapper to execute OS commands
# --------------------------------------------------------------------------
import sys
import subprocess
import shlex

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
