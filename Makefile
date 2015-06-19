
all:
	gcc hello_world.c -g -o hello_world
clean:
	rm -rf hello_world
	rm -rf hello_world_optimized
	rm -rf *.data*
	rm -rf *.afdo*
perf:
	~/pmu-tools-r100/ocperf.py record -b -e br_inst_retired.near_taken -- ./hello_world

autofdo:
	/tmp/autofdo/create_gcov --binary=./hello_world --profile=perf.data --gcov=hello.afdo -gcov_version=1
rebuild:
	gcc -O3 -fauto-profile=hello.afdo hello_world.c -o hello_world_optimized

