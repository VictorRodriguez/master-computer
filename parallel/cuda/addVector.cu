#include<stdio.h>
#include <time.h>
#define N 256

//
// Based on:
// http://www.nvidia.com/docs/IO/116711/sc11-cuda-c-basics.pdf
//

__global__ void addvec(int*a, int*b, int*c) { 

    if(threadIdx.x<N) 
        c[threadIdx.x]=a[threadIdx.x]+b[threadIdx.x]; 
} 

int main(void){

    clock_t begin, end;
    double time_spent;
    begin = clock();

    int a[N], b[N], c[N];       //  host variables
    int *dev_a, *dev_b, *dev_c; //  device variables
  

    //allocate memory on the GPU 
    cudaMalloc((void**)&dev_a,N * sizeof(int)); 
    cudaMalloc((void**)&dev_b,N * sizeof(int)); 
    cudaMalloc((void**)&dev_c,N * sizeof(int)); 

    //Fill arrays with data
    for(int i=0; i<N;i++){ 
        a[i]=i; 
        b[i]=i+1; 
    }

    //copy arrays to GPU
    cudaMemcpy(dev_a,a,N * sizeof(int),cudaMemcpyHostToDevice); 
    cudaMemcpy(dev_b,b,N * sizeof(int),cudaMemcpyHostToDevice); 

    //START KERNEL
    addvec<<<2,N>>>(dev_a,dev_b,dev_c); 

    //Copy result array to host 
    cudaMemcpy(c,dev_c,N * sizeof(int),cudaMemcpyDeviceToHost); 

    //Print the result ( comment if you want to mesure time ) 
    /*for(int i=0; i<N; i++){ 
        printf("%d+%d=%d\n",a[i],b[i],c[i]); 
    } */

    // Free device memory
    cudaFree(dev_a); 
    cudaFree(dev_b); 
    cudaFree(dev_c); 

    end = clock();
    time_spent = (double)(end - begin) / CLOCKS_PER_SEC;

    printf(" Time : %f\n", time_spent );

    return 0; 
} 
