#!/bin/bash

for i in `seq 1 1000`;
do
    COUNTER=1000000
    while [ $COUNTER -lt 11000000 ]; do
	#echo `runServer.sh $i $COUNTER` 
        ./runServer.sh $i $COUNTER 
	let COUNTER=COUNTER+1000000
    done
done 


#./runServer.sh 1 1000000
