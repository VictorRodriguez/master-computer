import itertools
import sys
import optparse
from CSV_Handler import CSV_Handler

def createallpairs(states):
    #Create all possible pairs from states, First Column
    matrix = []
    if len(states) == 1:
        matrixpossiblepairs = states
    else:
        matrixpossiblepairs = list(itertools.combinations(states, 2))
    return matrixpossiblepairs


def createautomata(autom):
    colum1 = []
    for i in autom:
        for j in autom:
            colum1[0][j]
    return colum1


def firstrun(matrix, accepting_states, matrixacceptstate):
    matrixelim = []
    matrixnoelim = []
    estadolength = len(accepting_states)
    parejanomatriz = ""
    validarmatriz = 0
    for pareja in matrix:
        for estacpt in accepting_states:
            if estacpt in pareja:
                matrixelim.append(pareja)
                for matrizestados in matrixacceptstate:
                    if matrizestados == pareja:
                        matrixelim.remove(pareja)

 
    newmatrix = []
    for pareja in matrix:
        if pareja not in matrixelim:
            newmatrix.append(pareja) 
  
    matrixnoelim = newmatrix
            
    return matrixelim, matrixnoelim


def gettransition(state, transition, matrix):
    column = 0
    newstate = ""
    for i in matrix[0]:
        if i[-1] == transition and i not in 'State':
            break
        column += 1
    for i in matrix:
        if i[0] == state:
            newstate = i[column]
            break
    return newstate


def createminmatrix(matrix, matrixnoelim, alpha):
    newmatrix = []
    for i in matrixnoelim:
        row = []
        row.append(i)
        for j in alpha:
            trans = tuple(sorted((gettransition(i[0], j, matrix), gettransition(i[1], j, matrix))))
            row.append(trans)
        newmatrix.append(row)
    return newmatrix


def nrun(matrixnoelim, matrixelim):
    newmatrix = []
    for row in matrixnoelim:
        found = False
        for column in row[1:]:
            if column in matrixelim:
                matrixelim.append(row[0])
                found = True
            else:
               for i in matrixelim:
                   if column[0] in i and row[0] not in matrixelim:
                       matrixelim.append(row[0])
                       found = True
        if not found:
            newmatrix.append(row)
    print "\n"+str(matrixelim)
    print "\n"+str(newmatrix)  
    return newmatrix, matrixelim


def updatematrix(rowmatrix, lastmatrix, firstrun):
    newmatrix = []
    found = False
    tupple = rowmatrix[0]
    for row in lastmatrix:
        newrow = []
        for column in row:
            for i in list(tupple):
                if i in column and column != tupple:
                    column = tuple(set(list(column)).union(set(list(tupple))))
                    found = True
            newrow.append(column)
        newmatrix.append(newrow)
        if not found and firstrun:
            newmatrix.append(rowmatrix)
    return newmatrix


def finalstates(nmatrix):
    lastmatrix = []
    finalmatrix = []
    lastmatrix.append(nmatrix[0])
    for row in nmatrix[1:]:
        lastmatrix = updatematrix(row, lastmatrix, True)
    finalmatrix = lastmatrix
    for row in finalmatrix:
        finalmatrix = updatematrix(row, finalmatrix, False)
    return finalmatrix


def get_states(numrow, numcolumn, matrix, file):
    states = []
    matrix = CSV_Handler.generate_matrix(file)
    for row in range(1, numrow):
        states.append(matrix[row][0])
    return states


def get_alpha(matrix, file):
    alpha = []
    matrix = CSV_Handler.generate_matrix(file)
    for row in matrix[0]:
        if "State" not in row:
            alpha.append(row[-1])
    return alpha


def supermatrix(chidamatrix, matrixorigin, states, alpha):
    superfinalmatrix = chidamatrix
    firststate = states[0]
    newstates = [row[0] for row in chidamatrix]
    change = True

    for state in newstates:
        if firststate in state:
            change = False

    if change:
        createstates = [firststate]
        for state in createstates:
            newcreatestates, newrow = createrowfinalmatrix(newstates, matrixorigin, state)
            superfinalmatrix.append(newrow)
            createstates += newcreatestates
            newstates = [row[0] for row in superfinalmatrix]

    # Ordering final Matrix
    superfinalmatrix = ordermatrix(superfinalmatrix)
    
    for row in superfinalmatrix:
        newrow = []
        for column in row[1:]:
            if tuple(list(column)) not in newstates:
                newrow.append(column)
                for i in alpha:
                    newtran = gettransition(column[0], i, matrixorigin)
                    for state in newstates:
                        if type(state) != type((1,2)):
                            state = (state, state)
                        if newtran in state:
                            newtran = list(state)
                    if len(newtran) == 1:
                        newtran =  [newtran,newtran]
                    newrow.append(newtran) 
        if newrow:
            superfinalmatrix.append(newrow)

    superfinalmatrix = ordermatrix(superfinalmatrix)
    cleanmatrix = []
    
    for row in superfinalmatrix:
        if row not in cleanmatrix:
            cleanmatrix.append(row) 

    return cleanmatrix


def ordermatrix(matrix):
    newmatrix = []
    for row in matrix:
        newrow = []
        for column in row:
            column = sorted(column)
            newrow.append(column)
            newmatrix.append(newrow)
    return newmatrix  

def createrowfinalmatrix(newstates, matrixorigin, state):
    createstates = []
    found = False
    newrow = [[state,state]]
    indexrow = [row[0] for row in matrixorigin].index(state)
    for column in matrixorigin[indexrow][1:]:
        for i in newstates:
            if column in i:
                newtuple = tuple(set([column]).union(set(list(i))))
                newrow.append(newtuple)
                found = True
 
        if not found: 
            createstates.append(column)
            column = (column,column)
            newrow.append(column)
        found = False  

    return createstates, newrow

import locale
locale.setlocale(locale.LC_NUMERIC, "")

def format_num(num):
    """Format a number according to given places.
    Adds commas, etc. Will truncate floats into ints!"""

    try:
        inum = int(num)
        return locale.format("%.*f", (0, inum), True)

    except (ValueError, TypeError):
        return str(num)

def get_max_width(table, index):
    """Get the maximum width of the given column index"""
    return max([len(format_num(row[index])) for row in table])

def pprint_table(out, table):
    """Prints out a table of data, padded for alignment
    @param out: Output stream (file-like object)
    @param table: The table to print. A list of lists.
    Each row must have the same number of columns. """
    col_paddings = []

    for i in range(len(table[0])):
        col_paddings.append(get_max_width(table, i))

    for row in table:
        # left col
        print >> out, str(row[0]).ljust(col_paddings[0] + 1),
        # rest of the cols
        for i in range(1, len(row)):
            col = format_num(row[i]).rjust(col_paddings[i] + 2)
            print >> out, col,
        print >> out

def pretty_print(matrix, start_state="", end_states=[], headers=[]):
    states = [row for row in matrix]
    matrix = [headers] + matrix 
    
    print "======================================================="
    out = sys.stdout
    pprint_table(out, matrix)
    print "======================================================="
    for state in states:
        if start_state in state[0]:
            start_state = state[0]
    print "Start State => " + str(start_state)
    print "======================================================="
    end_states_arr = []
    for end_state in end_states:
        for state in states:
            if end_state in state[0]:
                end_state = state[0]
        if end_state not in end_states_arr:
            end_states_arr.append(end_state)
    print "End State => " + str(end_states_arr)   
    print "======================================================="

def minimize(file, accepting_states):
    nmatrix = []
    accepting_states = sorted(accepting_states)
    numrow = CSV_Handler.get_rows_length(file)
    numcolumn = CSV_Handler.get_columns_length(file)
    matrixorigin = CSV_Handler.generate_matrix(file)
    headers = matrixorigin[0]
    states = get_states(numrow, numcolumn, matrixorigin, file)
    alpha = get_alpha(matrixorigin, file)
    matrix = createallpairs(states)
    acceptedstates = createallpairs(accepting_states)
    matrixelim, matrixnoelim = firstrun(matrix, accepting_states, acceptedstates)
    newmatrix = createminmatrix(matrixorigin, matrixnoelim, alpha)
    firstnewmatrix, firstmatrixelim = nrun(newmatrix, matrixelim)

    while (firstnewmatrix != nmatrix):
        if nmatrix != []:
            firstnewmatrix = nmatrix
        nmatrix, nmatrixelim = nrun(firstnewmatrix, firstmatrixelim)
        if not nmatrix:
            break
         
    if not (nmatrix):
        final = matrixorigin[1:]
    else:
        chidamatrix = finalstates(nmatrix)
        final = supermatrix(chidamatrix, matrixorigin, states, alpha)
    
    print "\n"+"="*55
    print "INITIAL DETERMINISTIC AUTOMATA"
    print "="*55
    pretty_print(matrixorigin[1:], matrixorigin[1][0], accepting_states, headers)
    print "\n"+"="*55
    print "MINIMAL DETERMINISTIC FINITE AUTOMATA"
    print "="*55
    pretty_print(final, matrixorigin[1][0], accepting_states, headers)
    #for row in final:
    #    print row
    print 

if __name__ == '__main__':
    file = ''   
    parser = optparse.OptionParser()
    parser.add_option('--file', dest='file', help='Specify the automata file')
    parser.add_option('--accepting_states', dest='accepting_states', help='Specify the accepting_states')

    (options, args) = parser.parse_args()   
    if not options.file: 
        print "================================================="
        print "Missing File"
        print "================================================="
        parser.print_help()
        sys.exit(1)
    else:
        file = options.file
        print "================================================="
        print "file => %s " % (file)
        print "================================================="

    if not options.accepting_states:
        print "================================================="
        print "Missing Accepting States"
        print "================================================="
        parser.print_help()
        sys.exit(1)
    else:
        print options.accepting_states 
        accepting_states = options.accepting_states.split(" ")
        print accepting_states 
        print "================================================="
        print "accepting_states => %s " % (accepting_states)
        print "================================================="
    
   
    minimize(file,accepting_states)


