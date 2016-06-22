
/******************************************************************************
 * * FILE: omp_version.c
 * * DESCRIPTION:
 * *    OMP VERSION - C/C++ Version
 * * AUTHOR: Victor Rodriguez  
 * ******************************************************************************/
#include <omp.h>
#include <stdio.h>
#include <stdlib.h>

int main (int argc, char *argv[]) 
{
        printf("La  version  instalada  de  OpenMP  es:  %d\n",_OPENMP);
        int  NCPU,tid,NPR,NTHR,nthreads;

        omp_set_dynamic(0);  
        omp_set_num_threads(16);

        /* Fork a team of threads giving them their own copies of variables */
        #pragma omp parallel private(NPR,tid)
        {
            /* Obtain thread number */
            tid = omp_get_thread_num();
            printf("Hello World from thread = %d\n", tid);
            NCPU=omp_get_num_procs();   //get  the  number  of  available  cores
            NTHR=omp_get_max_threads(); //get  number  of  threads  requested
            
            /* Only master thread does this */
            if (tid == 0) 
                    {
                                nthreads = omp_get_num_threads();
                                printf("Number of threads = %d\n", nthreads);
                                printf("%i:NCPU\t=%i\n",tid,NCPU);
                                printf("%i:NTHR\t=%i\n",tid,NTHR);
                   }

       }  /* All threads join master thread and disband */
}


