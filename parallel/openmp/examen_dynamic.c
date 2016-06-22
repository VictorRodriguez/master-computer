#include <omp.h>
#include <stdio.h>

int main ()  
{
    int iam=0,np=1,i=0;

    double start = omp_get_wtime();
    
    #pragma omp parallel private(iam,np,i)
    {

        #if defined (_OPENMP)
            np = omp_get_num_threads();
            iam = omp_get_thread_num();
        #endif
        printf("Hello from thread %d out of %d \n",iam,np);
        #pragma omp for schedule(dynamic)
        for(i=0;i<(np*2);i++)
        {
            printf("Thread %d,contador %d\n",iam,i);

        }
    }

    double end = omp_get_wtime();
    printf("start time = %f\n",start);
    printf("end time = %f\n",end);
    printf("diff time = %f\n",end - start);
}
