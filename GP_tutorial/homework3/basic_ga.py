import random
import argparse
import math
import sys

TARGET_VALUE = None
FILENAME = "data.dat"

def generate_random_population(size):
    population = []
    min_limit = (size /2) * -1
    max_limit = (size /2)
    for count  in range(0, size):
        population.append(random.randint(1, max_limit))
    return population

def mutate_random(tuple_population):
    population = list(tuple_population)    
    random_pos = random.randint(0, len(population)-1)
    random_delta = round(random.uniform(-1,1),2)
    population[random_pos] = population[random_pos] + random_delta
    return population

def fitness_function(chromosome):
	firstSum = 0.0
	secondSum = 0.0
	for c in chromosome:
		firstSum += c**2.0
		secondSum += math.cos(2.0*math.pi*c)
	n = float(len(chromosome))
	return -20.0*math.exp(-0.2*math.sqrt(firstSum/n)) - math.exp(secondSum/n) + 20 + math.e

def write_file(generation,value):
    f = open(FILENAME, 'a')
    line = str(generation) + "," + str(value) + "\n"
    f.write(line)
    f.close()

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
    open(FILENAME, 'w').close()

    population = generate_random_population(size)
    fitness = fitness_function(population)

    while generation< iterations: 
        new_population = mutate_random(tuple(population))
        new_fitness = fitness_function(new_population)

        if new_fitness <= fitness:
            print "We found a better one"
            population = new_population
            fitness = new_fitness

        print "Generation %s -> %s" % (generation,str(fitness))
        write_file(generation,fitness)
        generation = generation + 1

