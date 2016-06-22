#!/bin/bash
set -x

# Kill old client orbd processes
#ps aux | grep orbd | grep -v grep | grep -i client | awk '{print $2}' | xargs kill -9

# Start Client Application
java -cp ./ Client $1 $2 -ORBInitialHost $3 -ORBInitialPort $4
