all:
	nvcc addVector.cu -o  addVector
	nvcc addVector_shared.cu -o  addVector_shared

clean: 
	find . -executable -type f -exec rm -rf {} \;
	find . -iname "*~" -exec rm -rf {} \;
