SHELL = /bin/sh
CC    = gcc

FLAGS        = -std=gnu99 -Iinclude
CFLAGS       = -pedantic -Wall -Wextra -march=native -ggdb3
LIBS         = -lm
DEBUGFLAGS   = -O0 -D _DEBUG
RELEASEFLAGS = -O3 -D NDEBUG

TARGET  = demo
SOURCES = $(shell echo src/*.c)
COMMON  = include/definitions.h include/debug.h
HEADERS = $(shell echo include/*.h)
OBJECTS = $(SOURCES:.c=.o)

PREFIX = $(DESTDIR)/usr/local
BINDIR = $(PREFIX)/bin


all: $(TARGET)

$(TARGET): $(OBJECTS) $(COMMON)
	$(CC) $(FLAGS) $(CFLAGS) $(LIBS) $(DEBUGFLAGS) -o $(TARGET) $(OBJECTS)

release: $(SOURCES) $(HEADERS) $(COMMON)
	$(CC) $(FLAGS) $(CFLAGS) $(LIBS) $(RELEASEFLAGS) -o $(TARGET) $(SOURCES)

install: release
	install -D $(TARGET) $(BINDIR)/$(TARGET)

install-strip: release
	install -D -s $(TARGET) $(BINDIR)/$(TARGET)

uninstall:
	-rm $(BINDIR)/$(TARGET)

clean:
	-rm -f $(OBJECTS)
	# -rm -f gmon.out

distclean: clean
	-rm -f $(TARGET)

default:
	gcc main.c bubble_sort.c pi_calculation.c matrix_multiplication.c -lm -o demo

remove:
	rm -rf demo*
	rm -rf *.data*
	rm -rf *.afdo*
	rm -rf *.gcda

optimized:
	gcc hello_world.c -o demo_optimized -O3

normalfdo:
	gcc -g3 hello_world.c -o demo_instrumented -fprofile-generate
	./demo_instrumented
	gcc -O3 hello_world.c -o demo_normalfdo -fprofile-use=hello_world.gcda

autofdo:
	~/pmu-tools-r100/ocperf.py record -b -e br_inst_retired.near_taken -- ./demo
	/tmp/autofdo/create_gcov --binary=./demo --profile=perf.data --gcov=demo.afdo -gcov_version=1
	gcc -O3 -fauto-profile=demo.afdo main.c -o demo_autofdo

# .SECONDEXPANSION:
 
# $(foreach OBJ,$(OBJECTS),$(eval $(OBJ)_DEPS = $(shell gcc -MM $(OBJ:.o=.c) | sed s/.*://)))
# %.o: %.c $$($$@_DEPS)
# 	$(CC) $(FLAGS) $(CFLAGS) $(LIBS) $(DEBUGFLAGS) -c -o $@ $<
 
%.o: %.c $(HEADERS) $(COMMON)
	$(CC) $(FLAGS) $(CFLAGS) $(DEBUGFLAGS) -c -o $@ $<

.PHONY : all profile release install install-strip uninstall clean distclean