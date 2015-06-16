
all:
	gcc hello_world.c -o hello_world
clean:
	rm -rf hello_world
	rm -rf *.data
	rm -rf *.gcov*
perf:
	 ~/pmu-tools-r100/ocperf.py  record -c 10000 -e br_inst_retired.near_taken -b -g -- ./hello_world

autofdo:
	/tmp/autofdo/create_gcov --binary=./hello_world --profile=perf.data --gcov=hello.gcov -gcov_version=1
rebuild:
	gcc hello_world.c -o hello_world -fauto-profile=hello.gcov

