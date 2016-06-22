import random
import argparse

TARGET_VALUE = 50

def generate_random_population(size):
   
    population = []

    min_limit = (size /2) * -1
    max_limit = (size /2)

    for count  in range(0, size):
        population.append(random.randint(min_limit, max_limit))

    return population

def mutate_best(population):
    
    tmp_dict = evaluate_function(population)
    y_values = tmp_dict.keys()
    x_values = tmp_dict.values()

    y_values.sort(key = lambda x: abs(x-TARGET_VALUE))

    best_y = y_values[0]
    best_x = tmp_dict.get(best_y)

    random_delta = round(random.uniform(-1,1),2)

    tmp = best_x + random_delta

    population.append(tmp)

    return population

def clean_population(population):
    
    tmp_dict = evaluate_function(population)
    final_dict = {}

    y_values = tmp_dict.keys()
    x_values = tmp_dict.values()

#    print y_values
#    print "\n"
#    print x_values

    y_values.sort(key = lambda x: abs(x-TARGET_VALUE))

#    print "\n"
#    print y_values

    del y_values[-1]

#    print "\n"
#    print y_values


    best_y = y_values[0]
    best_x = tmp_dict.get(best_y,None)

    for y in y_values:
        final_dict[y] = tmp_dict.get(y,None)

#    print "\n"
#    print final_dict

    return final_dict,best_y,best_x
    

def get_overal_best(population):
    return overal_best

def save_population(population):
    return True

def evaluate_function(population):
    tmp_dict = {}
    for count  in range(0, len(population)):
#        print str(population[count]) + " : " + str(formula(population[count]))
#        tmp_dict [population[count]] = (formula(population[count]))
        tmp_dict [formula(population[count])] = (population[count])
    return tmp_dict

def formula(value):
    return 50 - (value**2)


if __name__ == "__main__":
    
    parser = argparse.ArgumentParser(description='Welcome to basic GA ... ')
    parser.add_argument('--population_size', action="store",
            dest="population_size", type=int)
    parser.add_argument('--iterations', action="store", \
            dest="iterations", type=int)
   
    args = parser.parse_args()

    if args.population_size:
        size = args.population_size
    else:
        size = 100

    if args.iterations:
        iterations = args.iterations
    else:
        iterations = 200

    generation = 0

    population = generate_random_population(size)
    print "\nInitial population"
    print population

    while generation< iterations :
    
        population = mutate_best(population)
        #print "\nNew population with one mutated"
        #print population

        dict_population,best_y,best_x = clean_population(population)
        #print "\nFittest elemnts"
        x_values = dict_population.values()
        #print x_values
        print "Generation: " + str(generation) + "  Best X : " + str(best_x) + " = " + str(best_y)

        population = x_values

        generation = generation + 1

        if best_y == TARGET_VALUE: 
            break
        else:
            continue


