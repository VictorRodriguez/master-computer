import random
import re
import os
import sys
import subprocess
import operator
import collections
from libs.Process import Process
from optparse import OptionParser, OptionGroup

class OptsError(Exception):
    pass

def check_opts(optparser):
    """Check for consistency in provided options"""
    if not optparser.list_test and not optparser.list_new:
        raise OptsError("Specify list of coverage to start execution")


def write_file(file_name, new_population):
    with open(file_name, 'w') as cov:
        cov.write("test_list = ")
        for i in new_population:
            cov.write("{0},".format(i))

class Selection():
    
    @staticmethod
    def get_best_worst_fitness(population):
        """Return the best coverage of the population"""
        population = population.sort() #sort by value
        best = population[-1]
        worst = population[0]
        return best, worst
    
    @staticmethod
    def fitness(new_pop, best, worst):
        print  
    
    @staticmethod
    def mutation(parent_pop, size):
        """Mutation: Random modification of the parent"""
        print "***Mutation Calculation***"
        children_pop = parent_pop[:]
        mut_index_1 = 0
        mut_index_2 = 0
        while (mut_index_1 == mut_index_2):
            mut_index_1 = random.sample(range(size), 1)
            mut_index_2 = random.sample(range(size), 1)
        mut_value = children_pop[mut_index_1[0]]
        children_pop[mut_index_1[0]] = children_pop[mut_index_2[0]]
        children_pop[mut_index_2[0]] = mut_value
        print "Mutation:\n{0}".format(children_pop)
        return children_pop

    @staticmethod
    def crossover(parent_pop, children_pop, size):
        """Crossover: Recombination of parent_pop and children_pop"""
        print "***Crossover Calculation***"
        parent_1 = parent_pop[:]
        parent_2 = children_pop[:]
        if size %2 == 0:
            children_1 = parent_1[:size/2]           
        else:
            children_1 = parent_1[:(size-1)/2]
        [parent_2.remove(i) for i in children_1]
        new_children_pop = parent_2 + children_1
        print "Parent:\n{0}\nChildren:\n{1}".format(parent_pop,
                                                    children_pop)
        print "Crossover:\n{0}".format(new_children_pop)
        return new_children_pop
    

class Population():
    old_pop = ""
    
    def fitness_function():
        """Evaluate the fitness for each population"""
        
        
    def create_member_and_add_to_end(self, opt):
        num = len(self.members)
        assert num<self.size
        assert len(self.members) < self.size
        while num == len(self.members):
            assert num<self.size
            newMember = Member(self.specie)
            newMember.fenotype(self.randData, opt)
            if self.specie.duplicateMembers or self.notDupFound(self,newMember):
                self.members.append(newMember)
            else:
                del newMember

    @staticmethod
    def initialize_population(opt):
        """Initialize population: generate ramdom population of n chromosomes
        """
        index = 0
        new_pop = {}
        with open(opt) as cov:
            cov_text = cov.read()
            cov.close()
        test = cov_text.splitlines()[0].split("=")[1].strip() #test_suite list
        test_list = [int(i) for i in test.split(",")]
        cov_num = cov_text.splitlines()[1].split("=")[1].strip() #% of coverage
        size = len(test_list)
        new_pop = random.sample(range(1,size),size-1)
        print "Test Cases list:\n{0}".format(test_list)
        print "Initialize Population:\n{0}".format(new_pop)
        print "Size of population: {0}".format(size)
        return new_pop, test_list, cov_num, size

    @staticmethod
    def create_population(new_pop):
        """Create a new population"""
        mutation(new_pop)


if __name__ == '__main__':
    parser = OptionParser()
    list_coverage = {}
    index = 0
    gen_pop = OptionGroup(parser, "Genetic Algorithm options")

    gen_pop.add_option("-i", dest="list_test",
                       action="store",
                       help="File with list of tests to execute with its coverage")
    gen_pop.add_option("-p", dest="list_new",
                       action="store",
                       help="File with list of tests with the new coverage")

    parser.add_option_group(gen_pop)

    opts = parser.parse_args()[0]

    try:
        if len(sys.argv) < 1:
            raise OptsError("Missing arguments")

        check_opts(opts)
        if opts.list_test and not opts.list_new:
            init_population, test_list, cov_per, pop_size = Population.initialize_population(opts.list_test)
            new_children_mut = Selection.mutation(init_population, pop_size)
            new_children_cross = Selection.crossover(init_population, new_children_mut, pop_size)
            write_file('new_pop.csv', new_children_cross)  
        if opts.list_test and opts.list_new:
            init_population, test_list, init_cov_per, pop_size = Population.initialize_population(opts.list_test)
            second_pop, second_test_list, new_cov_per, pop_size = Population.initialize_population(opts.list_new) 
            if init_cov_per < new_cov_per:
                best_cov = new_cov_per
                children_mut = Selection.mutation(second_pop, pop_size)
                children_cross = Selection.crossover(second_pop, children_mut, pop_size)
                write_file('new_pop.csv', children_cross)
            else:
                best_cov = init_cov_per
                children_mut = Selection.mutation(init_population, pop_size)
                children_cross = Selection.crossover(init_population, children_mut, pop_size)
                write_file('new_pop.csv', children_cross)
        print "END"

    except OptsError as e:
        parser.print_help()
        sys.stderr.write("\nError: %s\n" % str(e))
        sys.exit(-1)

    except Exception as e:
        sys.stderr.write("Error: %s\n" % str(e))
        sys.exit(-1)

