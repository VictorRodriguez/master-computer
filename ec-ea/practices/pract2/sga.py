#!/usr/bin/env python3

import argparse
import random
import time

def bin(number):
    return "{0:5b}".format(number).replace(' ','0')


def initialize(population):
    return [bin(random.randint(0,31)) for x in range(0, population)]


def evaluate(population):
    tuples = []
    suma = 0
    end = False
    for chaval in population:
        value = int(chaval, 2)
        y = value**2
        tuples.append((value, y, 0))
        suma += y
        if value == 31:
            end = True
    return tuples, suma, end

def generate_prob(population,suma):
    tuples = []
    for chaval in population:
        probability = round(chaval[1] / suma,2)
        tuples.append((chaval[0], chaval[1], probability))
    return tuples


def ruleta(population):
    random.shuffle(population)
    random.shuffle(population)
    rand_num = random.randint(1,100)
    try:
        rand_inv = 1 / rand_num
    except ZeroDivisionError:
        rand_inv = 0

    #print("random_ruleta: %f" % rand_inv)
    suma = 0
    chaval = population[-1]
    for idx,chaval in enumerate(population):
        suma += chaval[2]
        if rand_inv <= suma:
            break
        
    return chaval

def crossover(mom,dad):

    point = random.randint(0,4)
    mom_bin = bin(mom[0])
    dad_bin = bin(dad[0])
    
    f_half_dad = dad_bin[:point]
    s_half_dad = dad_bin[point:]

    f_half_mom = mom_bin[:point]
    s_half_mom = mom_bin[point:]
    
    child_1 = f_half_mom + s_half_dad
    child_2 = f_half_dad + s_half_mom

    return child_1,child_2


def main(ngenerations):
    initial = initialize(4)

    evaluated,suma,end = evaluate(initial)

    evaluated_with_p = generate_prob(evaluated,suma)

    generations = {}

    last_generation = 0
    
    for x in range(0, ngenerations):
        last_generation += 1
        try:
            generations[str(initial)] += 1
        except KeyError:
            generations[str(initial)] = 1
            child_1,child_2 = crossover(ruleta(evaluated_with_p),ruleta(evaluated_with_p))
            child_3,child_4 = crossover(ruleta(evaluated_with_p),ruleta(evaluated_with_p))
            initial = [child_1, child_2,
                       child_3, child_4]
            evaluated,suma,end = evaluate(initial)
            evaluated_with_p = generate_prob(evaluated,suma)
            if end:
                break


    print("Last Generation: #%d" % last_generation)
    for child in evaluated_with_p:
        print(child)
                
    for generation in generations.items():
        print(generation)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-g', "--generations", help="Generations", type=int)

    args = parser.parse_args()

    if args.generations:
        main(args.generations)
    else:
        parser.print_help()
