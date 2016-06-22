
main:
	wget http://www.busybox.net/downloads/busybox-1.22.1.tar.bz2
	tar -xvf *.tar.bz2


coverage:
	cd busybox-1.22.1 && make allnoconfig
	cp .config busybox-1.22.1/.config
	cd busybox-1.22.1 && make

get_coverage:
	cd busybox-1.22.1/ && cp -rf coreutils/*.gcda . 
	cd busybox-1.22.1/ && cp -rf coreutils/*.gcno . 
	lcov --capture --directory busybox-1.22.1/  --output-file coverage.info
	genhtml coverage.info --output-directory coverage_results

	
run_tests:
	cd busybox-1.22.1/testsuite && ./runtest

distclean:
	find . -type f -name "*.info" -exec rm -f {} \;
	find . -type f -name "*.gcov" -exec rm -f {} \;
	find . -type f -name "*.gcda" -exec rm -f {} \;
	find . -type f -name "*.gcno" -exec rm -f {} \;
	rm -rf coverage_results
	cd busybox-1.22.1 && make clean 

clean: 
	rm -rf *.gcov
	rm -rf *.gcda
	rm -rf *.gcno 
	rm -rf *~
	rm -rf *.pyc
	rm -rf busybox-*

