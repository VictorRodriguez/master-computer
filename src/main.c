#include <stdio.h>
#include <sys/time.h>
#include "bubble_sort.h"
#include "pi_calculation.h"
#include "matrix_multiplication.h"
#define ARRAY_LEN 30000
#define PI_LEN_MIN 10000
#define PI_LEN_MAX 100000
#define MATRIX_SIZE 800

static struct timeval tm1;

static inline void start()
{
    gettimeofday(&tm1, NULL);
}

static inline void stop()
{
    struct timeval tm2;
    gettimeofday(&tm2, NULL);

    unsigned long long t = 1000 * (tm2.tv_sec - tm1.tv_sec) + (tm2.tv_usec - tm1.tv_usec) / 1000;
    printf("%llu ms\n", t);

}


int main(){
	start();
	int array[ARRAY_LEN];
	generate_array(array, ARRAY_LEN);
	bubble_sort(array, ARRAY_LEN);
	calculate_pi_1(PI_LEN_MIN);
	calculate_pi_2(PI_LEN_MAX);
	multiply_matrix(MATRIX_SIZE);
	stop();
	return 0;
}
