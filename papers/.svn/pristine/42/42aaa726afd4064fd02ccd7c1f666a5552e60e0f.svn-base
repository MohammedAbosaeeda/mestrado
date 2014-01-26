#!/usr/bin/python2.6

import sys, os


class Individual:
    '''
    classdocs
    '''

    def __init__(self, freq, bet, batt):
        '''
        Constructor
        '''
        self.freq = freq
        self.bet  = bet
        self.batt = batt

        def __lt__(self, other):
            return self.freq < other.freq

        def __le__(self, other):
            return self.freq <= other.freq

        def __eq__(self, other):
            return self.freq == other.freq

        def __ne__(self, other):
            return self.freq != other.freq

        def __gt__(self, other):
            return self.freq > other.freq

        def __ge__(self, other):
            return self.freq >= other.freq

        def __cmp__(self, other):
            if self.freq < other.freq:  return -1
            if self.freq == other.freq: return  0
            if self.freq > other.freq:  return  1


def ParseFile(file):
    individuals = []
    for line in file:
        if line[0:9] == "Iteration": continue
        line = str(line)
        sc_pos = line.find(':')
        com_pos = line.find(' , ')
        freq = float(line[0:sc_pos])
        bet  = 1.0 - float(line[sc_pos+2:com_pos])
        batt = float(line[com_pos+3:-1])
        if bet > 0.000000000000001 and batt < 0.99999999999999:
            individuals.append(Individual(freq, bet, batt))
    return individuals


def PrintIndividuals(inds):
    for ind in inds:
        print("(" + str(ind.freq) + "," + str(ind.bet) + "," + str(ind.batt) + ")")

def OutputFile(file, inds):
    outf = open(file, 'w')
    outf.write("# Frequency\r\n# BET Utilization\r\n# Residual battery charge\r\n")
    for ind in inds:
        outf.write(str(ind.freq) + "\t" + str(ind.bet) + "\t" + str(ind.batt) + "\r\n")

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: ./adesd_data_extractor <data_file>")
        exit(-1)

    if not os.path.exists(sys.argv[1]):
        print("Bad file: " + sys.argv[1] + "\n")
        exit(-2)

    if os.path.exists(sys.argv[1]+"_out"):
        print("Bad output file: " + sys.argv[1] + "_out already exists!\n")
        exit(-2)

    file = open(sys.argv[1], 'r')
    individuals = ParseFile(file)
    OutputFile(sys.argv[1]+"_out", individuals)
