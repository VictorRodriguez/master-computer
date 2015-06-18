
all:
	gcc -g -O3 hello_world.c -o hello_world
clean:
	rm -rf hello_world
	rm -rf hello_world_optimized
	rm -rf *.data
	rm -rf *.gcov*
perf:
	~/pmu-tools/ocperf.py record -e br_inst_retired.near_taken -b -- ./hello_world

autofdo:
	~/autofdo/create_gcov --binary=./hello_world --profile=perf.data --gcov=hello.gcov -gcov_version=1
rebuild:
	gcc hello_world.c -o hello_world_optimized -fauto-profile=hello.gcov -O3

