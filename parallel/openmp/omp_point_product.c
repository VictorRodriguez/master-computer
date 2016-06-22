#include <omp.h>
#include <stdio.h>
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
    
    omp_set_dynamic(0);  
    omp_set_num_threads(4);

    /*
        The REDUCTION clause performs a reduction on the variables 
        that appear in its list.

        A private copy for each list variable is created for each thread.
        At the end of the reduction, the reduction variable is applied to 
        all private copies of the shared variable, and the final result is 
        written to the global shared variable. 

        SOURCE:  https://computing.llnl.gov/tutorials/openMP/
    */

    #pragma omp parallel shared(sum,b,c) private(i)
      {
            #pragma omp for schedule(static) reduction (+:sum)
            for (i=0; i < N; i++)
                    sum += a[i] * b[i];

              }  /* end of parallel section */



    double end = omp_get_wtime();

    printf("start time = %f\n",start);
    printf("end time = %f\n",end);
    printf("diff time = %f\n",end - start);

}
