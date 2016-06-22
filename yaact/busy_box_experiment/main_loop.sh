#!/bin/bash

echo "#--------------------------------------#"
echo "Start"
echo "#--------------------------------------#"

COVERAGE_GOAL=50
COUNTER=0 
echo "" > coverage.txt
echo "" > best_coverage.txt


while [ $COUNTER -lt 2 ];do
	let COUNTER=COUNTER+1

	#clean
	make distclean
	
	#build for coverage
	make coverage

	#get genetic

	#run tests
	python genetic_algorithm.py -i list_test.txt -p prev_list_test.txt
	TEST_SUITE=$(cat new_pop.csv | sed -r 's/[,]+/ /g' | cut -d"=" -f2)
	PREV_LIST=$(cat new_pop.csv | sed -r 's/[ ]+//g' | tr [:upper:] [:lower:])
	PREV_LIST=$(echo ${PREV_LIST%?})
	echo $PREV_LIST > prev_list_test.txt
	echo $TEST_SUITE

	NEW_TEST_SUITE="$(python gen_generator.py "$TEST_SUITE")"
	echo $NEW_TEST_SUITE

	CURRENT_DIR=$(pwd)
	cd busybox-1.22.1/testsuite && ./runtest $NEW_TEST_SUITE
	cd $CURRENT_DIR

	#get coverage
	make get_coverage | tee stdout.txt
	COVERAGE=$(cat stdout.txt | grep "lines" | cut -d":" -f2 | cut -d"%" -f1)
	echo "coverage=$COVERAGE"
	echo "coverage=$COVERAGE" >> coverage.txt
	echo "coverage=$COVERAGE" | sed -r 's/[ ]+//g' >> prev_list_test.txt

	#have we reach the goal ? 
	if [ $(echo "$COVERAGE>$COVERAGE_GOAL" | bc) -eq 1 ]
	then 
		echo "goal reeached"
		echo "COVERAGE = $COVERAGE" >> best_coverage.txt
		break
	fi

done

