#!/bin/env python3

import random

def rand_list(low, high, length):
    return [round(random.uniform(low, high), 2)
            for x in range(0, length)]


def main():
    l = rand_list(-100, 100, 50)
    print(l)

if __name__ == '__main__':
    main()
            
