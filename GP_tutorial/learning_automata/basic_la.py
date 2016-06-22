from __future__ import division
import random
import argparse
import math
import sys

TARGET_VALUE = None

def generate_random_population(size):
    population = []
    min_limit = (size /2) * -1
    max_limit = (size /2)
    for count  in range(0, size):
        population.append(random.randint(min_limit, max_limit))
    return population

def mutate_fittest(tuple_population,prob_array):
    population = list(tuple_population)
    random_delta = round(random.uniform(-1,1),2)
    position = prob_array.index(max(prob_array))
    population[position] = population[position] + random_delta
    return population

    

def fitness_function(chromosome):
    best_fittest = (0 -(chromosome[0])-81.3**2)
    best_fittest_pos = 0
    for value in chromosome:
        fittest = 0 - (value-81.3)**2
        if fittest > best_fittest:
            best_fittest = fittest
            best_fittest_pos = chromosome.index(value)
    
    return best_fittest_pos,best_fittest

def write_file(line,filename):
    f = open(filename, 'a')
    f.write(line)
    f.close()

def generate_prob_array(size):
    prob_array = []
    prob = float(1/size)
    for count  in range(0, size):
        prob_array.append(prob)
    return prob_array



if __name__ == "__main__":

    parser = argparse.ArgumentParser(description='Welcome to basic GA ... ')
    parser.add_argument('--population_size', action="store",
            dest="population_size", type=int)
    parser.add_argument('--iterations', action="store", \
            dest="iterations", type=int)
    parser.add_argument('--target', action="store", \
            dest="target", type=int)

    args = parser.parse_args()

    if args.population_size:
        size = args.population_size
    else:
        size = 100

    if args.iterations:
        iterations = args.iterations
    else:
        iterations = 200

    if args.target:
        TARGET_VALUE = args.target
    else:
        TARGET_VALUE = None

    generation = 0
    open("data.dat", 'w').close()
    open("prob.dat", 'w').close()

    population = generate_random_population(size)
    prob_array = generate_prob_array(size)
    fittess_pos,fitness = fitness_function(population)

    while generation< iterations:
        new_population = mutate_fittest(tuple(population),prob_array)
        new_fittess_pos,new_fitness = fitness_function(new_population)

        if new_fitness >= fitness:
            population = new_population
            fitness = new_fitness
            fittess_pos = new_fittess_pos
            prob_array[fittess_pos] =  prob_array[fittess_pos] + 0.1

        print "Generation %s x: %s  -> %s" % \
                (generation,str(population[fittess_pos]),str(fitness))
        

        line = str(generation) + "," + str(fitness) + "\n"
        write_file(line,"data.dat")


        line = str(generation) + "," + str(prob_array[fittess_pos]) + "\n"
        write_file(line,"prob.dat")
        generation = generation + 1

