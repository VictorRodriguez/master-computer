#!/bin/bash
set -x

# Re-Compile All
make clean
make all

# Kill old orbd processes
ps aux | grep orbd | grep -v grep | awk '{print $2}' | xargs kill -9

# Start ORBD
orbd -ORBInitialPort 1050 -ORBInitialHost $1&
sleep 3

# Start Server Application
java -cp ./ Server -ORBInitialPort 1050 
