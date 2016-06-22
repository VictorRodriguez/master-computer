#!/usr/bin/env python

import argparse
import numpy as np
import operator
import os

import common
import mutation


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-n', help="N value for Ackley Function",
                        dest='N', type=int)
    parser.add_argument("--min", help="Minimal value", type=int)
    parser.add_argument("--max", help="Maximum value", type=int)
    parser.add_argument('-g', "--generations", help="Number of Generations",
                        dest='gens', type=int)

    args = parser.parse_args()
    
    minmax = (args.min, args.max)
    N = args.N
    gens = args.gens

    solution = common.initialize(N,minmax)
    best_fitness = common.fitness(solution[0])

    p = 1.5
    
    for gen in range(1, gens):
        mutated = mutation.es_mutation(solution, minmax, p)
        fitness = common.fitness(mutated[0])

        if fitness <= best_fitness:
            best_fitness = fitness
            solution = mutated
            p = 1.5
            common.write_data(gen, fitness, 'data.dat')
        else:
            p = 1.5 ** (-1/4)
        
        if fitness == 0:
            break

