'''
Created on Mar 22, 2012

@author: arliones

'''

import sys, os

if __name__ == '__main__':
    
    mote_data = file("../data/mote_data.log", "r")
    
    output = file ("../data/mote.dat", "w")
    ''' TIME;TEMPERATURE;VOLTAGE;CURRENT; '''
    
    time = 0
    last_time = 0
    
    for line in mote_data:
        params = line.split('\t')
        print params
        if len(params) < 4: continue
        output.write(str(time) + ";" + params[1] + ";" + params[2] + ";" + params[3] + ";\r\n")
        time += 1

