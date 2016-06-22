#include <omp.h>
#include <stdio.h>
#define R 100
#define C 100

main ()  
{
    double mat[R][C];
    double vec[C];
    double tmp; 
    int i=0,j=0; 

    for(i=0; i<R; i++){
        for(j=0; j<C; j++){
            mat[i][j]=1;
            }
    }
    for(i=0; i<C; i++)
        vec[i]=1;

    double start = omp_get_wtime();

    for(i=0; i<R; i++){
        tmp=0;
        for(j=0; j<C; j++){
            tmp += mat[i][j]*vec[j];
        }
        printf("Resultado[%d]=%lf\n", i, tmp);
    }

    double end = omp_get_wtime();

    
    printf("start time = %f\n",start);
    printf("end time = %f\n",end);
    printf("diff time = %f\n",end - start);
}
