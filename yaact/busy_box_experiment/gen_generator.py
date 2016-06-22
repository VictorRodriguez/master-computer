from random import shuffle
import sys

MAX_LIMIT = 44

TEST_SUITES = ["basename","bunzip2","cat",\
"cmp", "cp", "cut", "date", "dd", "dir1", "dirname",\
"du", "echo", "expand", "expr", "false", "find",\
"gunzip", "gzip","head", "hostid","hostname","id", \
"ln","ls", "md5sum", "mkdir", "msh", "mv","pwd", "rm",\
"rmdir", "strings", "tail","tar", "tee", "there", "touch",\
"tr", "true", "unexpand", "uptime", "wc", "wget", "which", "xargs"]


if len(sys.argv) >= 2:
	NEW_TEST_SUITES = []
	NEW_ORDER = sys.argv[1].split(" ")
	NEW_ORDER.pop()
	NEW_ORDER.pop(0)
	print NEW_ORDER
	for order in NEW_ORDER:
		if int(order) <= MAX_LIMIT:
			NEW_TEST_SUITES.append(TEST_SUITES[int(order)])
		else:
			print int(order)
			print "error out of boundary"
			sys.exit(1)
	TEST_SUITES = NEW_TEST_SUITES	
else:
	shuffle(TEST_SUITES)

string = ""
for test in TEST_SUITES: 
	string = string + test + " "
print string

