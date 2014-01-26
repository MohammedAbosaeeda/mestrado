'''
Created on Mar 13, 2012

@author: arliones

'''

import sys, os

if __name__ == '__main__':
    
    irrad_data1 = file("../data/irrad/16-03-2012.csv", "r")
    irrad_data2 = file("../data/irrad/17-03-2012.csv", "r")
    irrad_data3 = file("../data/irrad/18-03-2012.csv", "r")
    irrad_data4 = file("../data/irrad/19-03-2012.csv", "r")
    
    output = file ("../data/irrad.dat", "w")
    ''' TIME;IRRADIANCE;TEMPERATURE '''
    
    time = 0
    
    for line in irrad_data1:
        params = line.split(';')
        if len(params) < 3: continue
        output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
        time += 1
        while ((time-77) % 300) != 0:
            output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
            time += 1
    
    for line in irrad_data2:
        params = line.split(';')
        if len(params) < 3: continue
        output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
        time += 1
        while ((time-77) % 300) != 0:
            output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
            time += 1
    
    for line in irrad_data3:
        params = line.split(';')
        if len(params) < 3: continue
        output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
        time += 1
        while ((time-77) % 300) != 0:
            output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
            time += 1
    
    for line in irrad_data4:
        params = line.split(';')
        if len(params) < 3: continue
        output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
        time += 1
        while ((time-77) % 300) != 0:
            output.write(str(time) + ";" + str(params[1]) + ";" + str(params[10]) + ";\r\n")
            time += 1
    