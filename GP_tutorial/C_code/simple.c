#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

#include <time.h>
#include <ctype.h>


void print_chromosome(int *chromosome, int chromosome_size);
const char* program_name;

void print_usage (FILE* stream, int exit_code)
{
    fprintf (stream, "Usage: %s options [ inputfile ... ]\n", program_name);
    fprintf (stream,
        " -h Display this usage information.\n"
        " -i Number of iterations.\n"
        " -c Size of the chromosome (genotype).\n"
        " -p Size of the population.\n"
        " -v Print verbose messages.\n");
exit (exit_code);
}



typedef struct unit_class_struct{
    int * chromosome;
    int fitnes;

} gen;


gen * generate_random_population(int size, int chromosome_size)
{
    int r =0;
    srand(time(NULL));
    gen * array = NULL;

    array = calloc(size, sizeof(gen));

    for (int pop_count=0;pop_count<size;pop_count++){
        array[pop_count].chromosome = calloc(chromosome_size, sizeof(int));
        for (int count = 0; count < chromosome_size  ; count ++){
            //random number betwen 0-1
            r = rand() % 2;
            array[pop_count].chromosome[count] = r;
        }
    }

    return array;
}

void free_population(gen *pop)
{
    free(pop);
}

void print_chromosome(int *chromosome, int chromosome_size){
    for (int i=0;i < chromosome_size;i++) {
        printf("%d",chromosome[i]);
    }
    printf("\n");
}

void print_population(gen * array, int population_size, int chromosome_size){
    for (int pop_count=0;pop_count<population_size;pop_count++){
        print_chromosome(array[pop_count].chromosome,chromosome_size);
    }
    printf("\n");
}

int * mutate_chromosome(int * chromosome, int chromosome_size){

    int new_val = rand() % 2;
    int rand_gen = rand() % chromosome_size;
    chromosome[rand_gen]=1;
    return chromosome;
}

int fitnes_function(int * chromosome, int chromosome_size){
    int value = 0;
    for (int i=0;i < chromosome_size;i++) {
        value += chromosome[i];
    }
    return value;
}


int measure_pop_fitnes(gen* population,int population_size, int chromosome_size){

    int fitnes;
    int best_fitnes;

    for (int pop_count=0;pop_count<population_size;pop_count++){
        fitnes = fitnes_function(population[pop_count].chromosome,chromosome_size);
        population[pop_count].fitnes=fitnes;
        if (fitnes >best_fitnes){
            best_fitnes = fitnes;
        }
    }
    return best_fitnes;

}

int main( int argc, char *argv[] )  {

    int iterations = 0;
    int population_size = 0;
    int chromosome_size = 0;
    int opt;

    while ((opt = getopt(argc, argv, "hc: p: i:")) != -1) {
        switch (opt) {
            case 'i':
               iterations = atoi(optarg);
               break;

            case 'c':
               chromosome_size = atoi(optarg);
               break;

            case 'p':
               population_size = atoi(optarg);
               break;

            case 'h':
               print_usage (stdout, 0);
               break;

            default: /* '?' */
               print_usage (stdout, 0);
               exit(-1);
           }
       }



    printf("Welcome to a basic GA\n");

    printf("Number of Iterations: %d\n",iterations);
    printf("Chromosome size: %d\n",chromosome_size);
    printf("Population size: %d\n",population_size);

    gen * population = NULL;
    gen * population_mut = NULL;

    //generate random population
    printf("\nInitial Population:\n");
    population = generate_random_population(population_size,chromosome_size);
    print_population(population,population_size,chromosome_size);

    //measure fitnes function
    int fitnes;
    int best_fitnes = measure_pop_fitnes(population,population_size,chromosome_size);
    printf("best fitness: %d\n",best_fitnes);

    int generation = 0;


    while(generation < iterations){

        //mutate random
        int random_chrom = rand() % population_size + 1;
        int * chromosome = population[random_chrom].chromosome;
        fitnes = fitnes_function(chromosome,chromosome_size);


        int * new_chromosome = mutate_chromosome(chromosome,chromosome_size);
        int new_fitnes = fitnes_function(new_chromosome,chromosome_size);

        if (new_fitnes > fitnes){
            for (int count = 0; count < chromosome_size ; count ++){
            population[random_chrom].chromosome[count] = new_chromosome[count];
        }

            //printf("Found a better one: %d\n",fitnes);
            //printf("Fitnes: %d\n",fitnes);
            //printf("New Fitnes : %d\n",new_fitnes);
        }

        generation++;
    }

    best_fitnes = measure_pop_fitnes(population,population_size,chromosome_size);
    printf("\nBest fitness after mutation: %d\n",best_fitnes);
    print_population(population,population_size,chromosome_size);


    free(population);
    return 0;
}

