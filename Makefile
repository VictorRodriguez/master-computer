SHELL = /bin/sh
CC    = gcc
 
FLAGS        = -std=gnu99 -Iinclude
CFLAGS       = -pedantic -Wall -Wextra -march=native -ggdb3
DEBUGFLAGS   = -O0 -D _DEBUG
RELEASEFLAGS = -O2 -D NDEBUG -combine -fwhole-program
 
TARGET  = demo
SOURCES = $(shell echo src/*.c)
COMMON  = include/definitions.h include/debug.h
HEADERS = $(shell echo include/*.h)
OBJECTS = $(SOURCES:.c=.o)
 
PREFIX = $(DESTDIR)/usr/local
BINDIR = $(PREFIX)/bin
 
 
all: $(TARGET)
 
$(TARGET): $(OBJECTS) $(COMMON)
	$(CC) $(FLAGS) $(CFLAGS) $(DEBUGFLAGS) -o $(TARGET) $(OBJECTS)

release: $(SOURCES) $(HEADERS) $(COMMON)
	$(CC) $(FLAGS) $(CFLAGS) $(RELEASEFLAGS) -o $(TARGET) $(SOURCES)

default:
	gcc main.c bubble_sort.c pi_calculation.c matrix_multiplication.c -lm -o demo
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

