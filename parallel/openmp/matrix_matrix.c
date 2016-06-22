#include <omp.h>
#include <stdio.h>
#define R_1 300
#define R_2 300
#define C_1 300
#define C_2 300



int main ()  
{

    int m = R_1, n = C_1, p = R_2, q = C_2, c, d, k; 

    double first[R_1][C_1] = {0};  
    double second[R_2][C_2] = {0}; 
    double multiply[R_1][C_2] = {0};

    for (  c = 0 ; c < m ; c++ )
        for ( d = 0 ; d < n ; d++ )
            first[c][d] = 1;

    for ( c = 0 ; c < p ; c++ )
        for ( d = 0 ; d < q ; d++ )
            second[c][d] = 1 ; 


   
    if (n != p ){ 
        return -1; 
    }
   
    double start = omp_get_wtime();

        for ( c = 0 ; c < m ; c++ ) {
            for ( d = 0 ; d < q ; d++ ) {
                for ( k = 0 ; k < p ; k++ ){
                    multiply[c][d]  += first[c][k] * second[k][d];
                }
            }
        }

    double end = omp_get_wtime();

    printf("start time = %f\n",start);
    printf("end time = %f\n",end);
    printf("diff time = %f\n",end - start);

    return 0; 
}
