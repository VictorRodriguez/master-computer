#!/bin/bash

javac -cp . -d . MergeSortWorker.java

ps aux | grep rmireg | grep -v grep | awk '{print $2}' | xargs kill -9
rmiregistry &

java -cp ./ -Djava.rmi.server.codebase=file:./ -Djava.rmi.server.hostname=10.0.0.1$1 MergeSortWorker
