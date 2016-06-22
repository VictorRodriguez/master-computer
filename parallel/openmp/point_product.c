#include <stdio.h>
#include <omp.h>
#define N     100000

main ()  
{

    int i,tid,NTHR;
    float a[N],b[N],c[N];
    float sum = 0;
    /* Some initializations */
    for (i=0; i < N; i++)
          a[i] = b[i] = i * 1.0;

    double start = omp_get_wtime();
    
            for (i=0; i < N; i++)
                    sum += a[i] * b[i];

    double end = omp_get_wtime();

    printf("start time = %f\n",start);
    printf("end time = %f\n",end);
    printf("diff time = %f\n",end - start);

}
