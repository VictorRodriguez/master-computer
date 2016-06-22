
import numpy as np
import random

import common

def es_mutate_steps(steps, p):
    steps_size = len(steps)
    new_steps = []
    t1 = np.sqrt(2 * steps_size) ** -1 
    t2 = np.sqrt(2 * np.sqrt(steps_size)) ** -1 
    for idx,step in enumerate(steps):
        new_step = step * np.exp( (t1 * common.gaussian(0,1))
                                 + (t2 * common.gaussian(0,1)) )

        sigma = new_step * p
        if sigma > 2:
            sigma = sigma % 2
        sigma = 1 - sigma
        new_steps.append(sigma)
    return new_steps

    
def es_mutate_individual(individual, steps, minmax):
    data = individual[0]
    new_data = []

    mutated_idx = random.randint(0,len(data))

    for idx,value in enumerate(data):
        if mutated_idx == idx:
            new_value = value + (steps[idx] * common.gaussian(0,1))
        else:
            new_value = value
        new_data.append(common.round(new_value))

    return (new_data, steps)
    

def es_mutation(individual, minmax, p):
    new_steps = es_mutate_steps(individual[1], p)
    new_individual = es_mutate_individual(individual, new_steps, minmax)
    return new_individual

