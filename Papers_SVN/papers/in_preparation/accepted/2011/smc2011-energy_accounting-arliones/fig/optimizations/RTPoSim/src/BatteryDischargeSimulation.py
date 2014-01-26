'''
Created on Jan 19, 2011

@author: arliones
'''
import matplotlib.pyplot as plt

def ProcessFile(file):
    print 'Processing file \'' + file + '\'... ',
    file = open('../data/' + file, 'r')
    array = []
    for v in file:
        array.append(float(v))
    print str(len(array)) + ' registries found.'
    return array

def CutOff(limit, voltage, charge, time):
    print 'Cutting Off to ' + str(limit) + ' V... ',
    i = 0
    while voltage[i] >= limit:
        i += 1
    voltage = voltage[0:i]
    charge = charge[0:i]
    time = time[0:i]
    print 'arrays truncated to ' + str(i) + ' registries'
    return time, voltage, charge

def TimeToYears(time):
    new_time = []
    for t in time:
        new_time.append(t/(60*60*24))
    return new_time

if __name__ == '__main__':
    time    = ProcessFile('time.csv')
    voltage = ProcessFile('voltage.csv')
    charge  = ProcessFile('charge_percent.csv')

    time, voltage, charge = CutOff(2.0, voltage, charge, time)

    # Extract plotable points
    time_adc    = []
    charge_adc  = []

    time_adc.append(time[0])
    charge_adc.append(charge[0])

    diff = 0.003
    pivot = voltage[0] - 2*diff
    i = 1
    while i < len(time):
        if voltage[i] >= pivot:
            i += 1
            continue
        i += 1
        pivot = voltage[i] - diff
        time_adc.append(time_adc[-1])
        charge_adc.append(charge[i])
        time_adc.append(time[i])
        charge_adc.append(charge[i])

    time_adc.append(time[-1])
    charge_adc.append(charge[-1])

    voltage_adc = []
    for t in time_adc:
        i = time.index(t)
        voltage_adc.append(voltage[i])

    time_adc = TimeToYears(time_adc)
    time = TimeToYears(time)

    #Plot
    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    ax2 = ax1.twinx()

    l1, = ax1.plot(time, voltage, 'k-.', label='Voltage drop', linewidth=2)
    l2, = ax2.plot(time_adc, charge_adc, 'k-', label='Sensed charge', linewidth=2)
    l3, = ax2.plot(time, charge, 'k--', label='Real charge', linewidth=2)

    plt.title('Sampled X Real Battery Discharge')
    ax1.set_xlabel('time (days)')
    ax1.set_ylabel('voltage (V)')
    ax2.set_ylabel('charge (%)')

    lines = [l1, l2, l3]
    ax1.legend(lines, [l.get_label() for l in lines])
#    ax1.legend(bbox_to_anchor=(0.9,1))
#    ax2.legend(bbox_to_anchor=(0.9,0.9))

    plt.show()

    print '(time, charge, voltage)'
    for i in range(len(time_adc)):
        if time_adc[i] != time_adc[i-1]:
            print '(' + str(time_adc[i]) + ',' + str(charge_adc[i]) + ',' + str(voltage_adc[i]) + ')'

