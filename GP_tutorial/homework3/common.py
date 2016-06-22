
import math
import numpy as np
import random

import matplotlib.pyplot as plt
from pylab import *


def initialize(population, minmax):
    minmax_diff = minmax[1] - minmax[0]
    values = [round(x)
              for x in np.random.ranf(population) * minmax_diff - minmax[1]]
    steps = [float("%.2f" % x)
              for x in np.random.ranf(population)]
    return values, steps
    

def gaussian(mean, stdv):
    return np.random.normal(mean, stdv)

def round(value):
    return float('%.3f' % value)


def fitness(data, c1=20.0, c2=0.2, c3=2*math.pi):
    sum1 = 0.0
    sum2 = 0.0
    N = len(data)
    for value in data:
        sum1 += value ** 2.0
        sum2 += math.cos(c3 * value)

    result = -c1 * np.exp(-c2 * np.sqrt(sum1 / N)) - np.exp(sum2 / N) + c1  + math.e
    return round(result) 

def write_data(generation, fitness, filename):
    f = open(filename, 'a')
    f.write('%d, %.3f\n' % (generation, fitness))
    f.close()
    
