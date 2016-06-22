#!/bin/bash

javac -cp . -d . MergeSortServer.java
java MergeSortServer $1 $2
