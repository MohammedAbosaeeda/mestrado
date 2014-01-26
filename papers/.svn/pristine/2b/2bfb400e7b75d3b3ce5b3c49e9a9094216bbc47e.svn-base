'''
Created on Mar 13, 2012

@author: arliones

'''

import sys, os, MyGraph

if __name__ == '__main__':
    
    mote_data = file("../data/mote.dat", "r")
    irrad_data = file("../data/irrad.dat", "r")
#    battery_data = file("../data/battery_data.csv", "r")
    
    output = file ("../data/photomote_characterization.dat", "w")
    
    Time = []
    Temp = []
    V_batt = []
    I_input = []
    I_output = []
#    Batt = []
    Irrad = []
    
    time = 0
    
    mydict1={}
    mydict1["Irradiance"]=[]
    mydict1["Current"]=[]
    G1=MyGraph.MyGraph("Irradiance","Current",logx=False, logy=False)
    
    mote_lines = mote_data.readlines()
    irrad_lines = irrad_data.readlines()
    size = len(mote_lines)
    if len(irrad_lines) < size: size = len(irrad_lines)
    for i in range(size):
        mote_p = mote_lines[i].split(';')
        irrad_p = irrad_lines[i].split(';')
#        batt_p = batt.split('\t')
        
        Time.append(time)
        Temp.append(float(irrad_p[2]))
        V_batt.append(float(mote_p[2]))
        I_input.append(float(mote_p[3]) + 0.057)
        I_output.append(0.057)
#        Batt.append(float(batt))
        Irrad.append(float(irrad_p[1]))
        
        mydict1["Current"].append(float(mote_p[3]) + 0.057)
        mydict1["Irradiance"].append(irrad_p[1])
        
        if time % 1000 == 0: print time
        time += 1
        
    G1.appendplot(mydict1)

        
    

