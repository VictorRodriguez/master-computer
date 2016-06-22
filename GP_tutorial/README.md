# GP_tutorial
Genetic Programing Tutorial

The way you can run the code is : 

vrodri3@vrodri3-mac01 Evolutivos $ python one_dimention_ga.py --help

Welcome to basic GA ...

optional arguments:
  -h, --help            show this help message and exit
  --population_size POPULATION_SIZE
  --iterations ITERATIONS
  --target TARGET
  --random ( if you dont declare random it will mutate the best of that generation ) 
  
  
You have to manually change the formula in line : 

108 def formula(value):
109 #    return 50 - (value**2)
110     return (value**3)
111 #    return value * math.sin(value)


An example coudl be : 

python one_dimention_ga.py --population=100 --iterations=100 --target=50

and will have results as ( in case of f(x) = 50 -x^2 ):

Mutationg just the best: 

Generation: 69  Best X : -0.002 = 49.999996
Generation: 70  Best X : -0.002 = 49.999996
Generation: 71  Best X : -0.002 = 49.999996
Generation: 72  Best X : -0.002 = 49.999996
Generation: 73  Best X : -0.002 = 49.999996
Generation: 74  Best X : -0.002 = 49.999996
Generation: 75  Best X : -0.002 = 49.999996
Generation: 76  Best X : -0.002 = 49.999996
Generation: 77  Best X : -0.002 = 49.999996
Generation: 78  Best X : -3.64291929955e-17 = 50.0

Mutationg the random:

python one_dimention_ga.py --population=100 --iterations=100 --target=50 --random


Generation: 83  Best X : -1 = 49
Generation: 84  Best X : -1 = 49
Generation: 85  Best X : -0.54 = 49.7084
Generation: 86  Best X : -0.54 = 49.7084
Generation: 87  Best X : -0.54 = 49.7084
Generation: 88  Best X : -0.54 = 49.7084
Generation: 89  Best X : -0.54 = 49.7084
Generation: 90  Best X : -0.54 = 49.7084
Generation: 91  Best X : -0.54 = 49.7084
Generation: 92  Best X : -0.54 = 49.7084
Generation: 93  Best X : -0.54 = 49.7084
Generation: 94  Best X : -0.54 = 49.7084
Generation: 95  Best X : -0.54 = 49.7084
Generation: 96  Best X : -0.13 = 49.9831
Generation: 97  Best X : -0.13 = 49.9831
Generation: 98  Best X : -0.13 = 49.9831
Generation: 99  Best X : -0.13 = 49.9831


IMPORTANT: 

This code is just for one dimentional problem , need improvements fro 2 dimentional formulas
