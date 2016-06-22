
all:
	gcc -fopenmp omp_version.c -o omp_version
	gcc -fopenmp omp_array.c -o omp_array
	gcc -fopenmp array.c -o array
	gcc -fopenmp omp_loop.c -o omp_loop
	gcc -fopenmp loop.c -o loop
	gcc -fopenmp omp_point_product.c -o omp_point_product
	gcc -fopenmp point_product.c -o point_product
	gcc -fopenmp omp_matrix_vector.c -o omp_matrix_vector
	gcc -fopenmp matrix_vector.c -o matrix_vector
	gcc -fopenmp omp_matrix_matrix.c -o omp_matrix_matrix
	gcc -fopenmp matrix_matrix.c -o matrix_matrix
clean: 
	find . -executable -type f -exec rm -rf {} \;

