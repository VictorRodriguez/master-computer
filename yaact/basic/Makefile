
main:
	gcc -o hello hello.c

coverage:
	gcc -Wall -fprofile-arcs -ftest-coverage hello.c -o hello

get_coverage:
	gcov -a hello.c


clean: 
	rm -rf *.gcov
	rm -rf *.gcda
	rm -rf *.gcno 
	rm -rf *~
	rm -rf *.pyc
	rm -rf hello

