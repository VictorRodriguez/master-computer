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

def mutate_best(population):
    
    tmp_array_tup = evaluate_function(population)

    y_values = list(x[1] for x in tmp_array_tup)

    if TARGET_VALUE:
        y_values.sort(key = lambda x: abs(x-TARGET_VALUE))
    else:
        y_values.sort()

    best_y = y_values[0]
    best_x = [item for item in tmp_array_tup if item[1] == best_y][0][0]

    random_delta = round(random.uniform(-1,1),3)

    tmp = best_x + random_delta

    population.append(tmp)

    return population

def mutate_random(population):
    
    random_pos = random.randint(0, len(population)-1)
    random_delta = round(random.uniform(-1,1),2)

    tmp = population[random_pos] + random_delta

    population.append(tmp)

    return population

def clean_population(population):

    tmp_array_tup = evaluate_function(population)
    final_array = []

    y_values = list(x[1] for x in tmp_array_tup)
    #print "\n"
    #print y_values
    #print len(y_values)

    if TARGET_VALUE:
        y_values.sort(key = lambda x: abs(x-TARGET_VALUE))
        del y_values[-1]
    else:
        y_values.sort()
        del y_values[0]
    
    #print "\n"
    #print y_values
    #print len(y_values)


    best_y = y_values[0]
    best_x = [item for item in tmp_array_tup if item[1] == best_y][0][0]

    #print "\n"
    #print best_y
    #print best_x

    for y in y_values:
        x = [item for item in tmp_array_tup if item[1] == y][0][0]
        tup = (x,y)
        #print "\n"
        #print tup
        final_array.append(tup)

    return final_array,best_y,best_x
    

def get_overal_best(population):
    return overal_best

def save_population(population):
    return True

def evaluate_function(population):
    tup = ()
    tmp_array = []
    for count  in range(0, len(population)):
        #print str(population[count]) + " : " + str(formula(population[count]))
        tup = (population[count],formula(population[count]))
        tmp_array.append(tup)

    return tmp_array

def formula(value):
    return 50 - (value**2)
#    return (value**3)
#    return value * math.sin(value)

if __name__ == "__main__":
    
    global TARGET_VALUE

    parser = argparse.ArgumentParser(description='Welcome to basic GA ... ')
    parser.add_argument('--population_size', action="store",
            dest="population_size", type=int)
    parser.add_argument('--iterations', action="store", \
            dest="iterations", type=int)
    parser.add_argument('--target', action="store", \
            dest="target", type=int)
    parser.add_argument('--random', action="store_true", \
            dest="random")

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

    population = generate_random_population(size)
    print "\nInitial population"
    print population
  

    while generation< iterations :
        
        if args.random :
            population = mutate_random(population)
        else:
            population = mutate_best(population)

        #print "\n"
        #print population

        array_population,best_y,best_x = clean_population(population)
        x_values = list(x[0] for x in array_population)
        #print "\n"
        #print x_values

        print "Generation: " + str(generation) + "  Best X : " + str(best_x) + " = " + str(best_y)

        population = x_values

        generation = generation + 1

        if best_y == TARGET_VALUE: 
            break
        else:
            continue


