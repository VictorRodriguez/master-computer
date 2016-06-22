#!/bin/env python3

import random

def rand_list(low, high, length):
    return [round(random.uniform(low, high), 2)
            for x in range(0, length)]


def rand_queen_list(low, high):
    queen_list = [[], []]
    rows = [x for x in range(low, high + 1)]
    columns = [y for y in range(low, high + 1)]
    length = len(rows)

    for idx in range(0, length):
        queen_list[0].append(rows.pop(random.randint(0, length - idx -1)))
        queen_list[1].append(columns.pop(random.randint(0, length - idx -1)))
        
    return queen_list


def get_diagonals(x, y, n):
    diagonals = []
    for delta in range(-x + 1, n - x + 1):
        position = (x + delta, y + delta)
        if not (position[0] > n or position[1] > n) and (x,y) != position \
           and (position[0] > 0 and position[1] > 0):
            diagonals.append(position)

    last = [100,100]
    for delta in range(-x + 1, n - x + 1):
        position = (x + delta, abs(delta - y))
        if not (position[0] > n or position[1] > n) and (x,y) != position \
           and (position[0] > 0 and position[1] > 0) \
           and (last[1] > position[1]):
            diagonals.append(position)
        last = position
    return diagonals


def validate_diagonals(queens_list):
    diagonals_check = 0
    qtuples = [(row, queens_list[1][idx])
               for idx,row in enumerate(queens_list[0])]
    length = len(qtuples)

    for qtuple in qtuples:
        diagonals = get_diagonals(qtuple[0], qtuple[1], length)
        diagonals_check += len(list(set(diagonals).intersection(set(qtuples))))
        
    return diagonals_check
    

def sort_generation(queens_lists):
    ordered_queens_list = []
    for idx1, queens_list in enumerate(queens_lists):
        diagonal_checks1 = validate_diagonals(queens_list)
        inserted = False
        for idx2, qlist in enumerate(ordered_queens_list):
            diagonal_checks2 = validate_diagonals(qlist)
            if diagonal_checks2 == diagonal_checks1 or \
               diagonal_checks2 > diagonal_checks1:
                ordered_queens_list.insert(idx2, queens_list)
                inserted = True
                break
            else:
                continue
        if not inserted:
            ordered_queens_list.append(queens_list)
            
    return ordered_queens_list
                
                
        

        
def initialize(population = 5):
    queens = []
    for x in range(0, 5):
        queens.append(rand_queen_list(1,8))
    return queens


def eight_queens(population = 5, iterations = 1):
    queens_lists = initialize()

    for iteration in range(0, iterations):
        queens_lists = sort_generation(queens_lists)
        for qlist in queens_lists:
            print(qlist[0])
            print(qlist[1])
            print(validate_diagonals(qlist))


def main():
    eight_queens(5)


if __name__ == '__main__':
    main()
            
