
all:
	gcc hello_world.c -o demo
clean:
	rm -rf demo*
	rm -rf *.data*
	rm -rf *.afdo*
	rm -rf *.gcda

optimized:
	gcc hello_world.c -o demo_optimized -O3

normalfdo:
	gcc -g3 hello_world.c -o demo_instrumented -fprofile-generate
	./demo_instrumented
	gcc hello_world.c -o demo_normalfdo -fprofile-use -freorder-blocks-and-partition -O3 -fprofile-correction -Wno-coverage-mismatch

autofdo:
	~/pmu-tools-r100/ocperf.py record -b -e br_inst_retired.near_taken -- ./demo
	/tmp/autofdo/create_gcov --binary=./demo --profile=perf.data --gcov=demo.afdo -gcov_version=1
	gcc -O3 -fauto-profile=demo.afdo hello_world.c -o demo_autofdo

