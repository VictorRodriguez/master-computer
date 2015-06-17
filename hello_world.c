#include <stdio.h>
#include <time.h>
#include <math.h>
#include <stdlib.h>
#include <sys/time.h>
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

void bubble_sort (int *a, int n) {
    int i, t, s = 1;
    while (s) {
        s = 0;
        for (i = 1; i < n; i++) {
            if (a[i] < a[i - 1]) {
                t = a[i];
                a[i] = a[i - 1];
                a[i - 1] = t;
                s = 1;
            }
        }
    }
}

void sort_array(){
	printf("Bubble sorting array of %d elements\n", ARRAY_LEN);
	srand(time(NULL));
	int data[ARRAY_LEN], i;
	for(i=0;i<ARRAY_LEN;++i){
		data[i] = rand();
	}
	bubble_sort(data, ARRAY_LEN);
}

void calculate_pi_1(){
	printf("Calculating PI method 1 with %d digits \n", PI_LEN_MIN);
	int i, j, k;
	int len = floor(10 * PI_LEN_MIN/3) + 1;
	int A[len];

	for(i = 0; i < len; ++i) {
	A[i] = 2;
	}

	int nines    = 0;
	int predigit = 0;

	for(j = 1; j < PI_LEN_MIN + 1; ++j) {        
		int q = 0;

		for(i = len; i > 0; --i) {
			int x  = 10 * A[i-1] + q*i;
			A[i-1] = x % (2*i - 1);
			q = x / (2*i - 1);
		}

		A[0] = q%10;
		q    = q/10;

		if (9 == q) {
		  ++nines;
		}
		else if (10 == q) {
		  //printf("%d", predigit + 1);

		  for (k = 0; k < nines; ++k) {
		    //printf("%d", 0);
		  }
		  predigit, nines = 0;
		}
		else {
		  //printf("%d", predigit);
		  predigit = q;

		  if (0 != nines) {    
		    for (k = 0; k < nines; ++k) {
		      //printf("%d", 9);
		    }

		    nines = 0;
		  }
		}
	}
	//printf("%d", predigit);
}

void calculate_pi_2(){
	printf("Calculating PI method 2 with %d digits\n", PI_LEN_MAX);
	int r[PI_LEN_MAX + 1];
    int i, k;
    int b, d;
    int c = 0;

    for (i = 0; i < PI_LEN_MAX; i++) {
        r[i] = 2000;
    }

    for (k = PI_LEN_MAX; k > 0; k -= 14) {
        d = 0;

        i = k;
        for (;;) {
            d += r[i] * 10000;
            b = 2 * i - 1;

            r[i] = d % b;
            d /= b;
            i--;
            if (i == 0) break;
            d *= i;
        }
        // printf("%.4d", c + d / 10000);
        c = d % 10000;
    }

    //printf("\n");

}

void multiply_matrix(){
	printf("Multiplying matrix of %dx%d\n", MATRIX_SIZE, MATRIX_SIZE);
	int c, d, k, sum = 0;
	int first[MATRIX_SIZE][MATRIX_SIZE], second[MATRIX_SIZE][MATRIX_SIZE], 
multiply[MATRIX_SIZE][MATRIX_SIZE];
	srand(time(NULL));

	//printf("Entering the elements of first matrix\n");

	for (c = 0; c < MATRIX_SIZE; c++)
		for (d = 0; d < MATRIX_SIZE; d++)
			first[c][d] = rand();

	//printf("Entering the elements of second matrix\n");

	for (c = 0; c < MATRIX_SIZE; c++)
		for (d = 0; d < MATRIX_SIZE; d++)
			second[c][d] = rand();

	for (c = 0; c < MATRIX_SIZE; c++) {
	  for (d = 0; d < MATRIX_SIZE; d++) {
	    for (k = 0; k < MATRIX_SIZE; k++) {
	      sum = sum + first[c][k]*second[k][d];
	    }

	    multiply[c][d] = sum;
	    sum = 0;
	  }
	}

	// printf("Product of entered matrices:-\n");

	// for (c = 0; c < MATRIX_SIZE; c++) {
	//   for (d = 0; d < MATRIX_SIZE; d++)
	//     printf("%d\t", multiply[c][d]);

	//   printf("\n");
	// }
}

int main(){
	start();
	sort_array();
	calculate_pi_1();
	calculate_pi_2();
	multiply_matrix();
	stop();
	return 0;
}
