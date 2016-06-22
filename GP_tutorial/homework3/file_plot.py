
# Need to import the plotting package:
import matplotlib.pyplot as plt
from pylab import *
import numpy as np 


# Read the file.
f2 = open('data.dat', 'r')
# read the whole file into a single variable, 
# which is a list of every row of the file.
lines = f2.readlines()
f2.close()

# initialize some variable to be lists:
x1 = []
y1 = []

# scan the rows of the file stored in lines, 
# and put the values into some variables:
for line in lines:
    p = line.split(',')
    x1.append(float(p[0]))
    y1.append(float(p[1]))
   
xv = np.array(x1)
yv = np.array(y1)

# now, plot the data:
plt.plot(xv, yv)

# Set up
xlabel('Iterations')
ylabel('Value')
title('GA behaivor')
grid(True)

plt.show()
