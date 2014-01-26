'''
Created on Jan 13, 2011

@author: arliones
'''

import Simulator

class Job:
    '''
    classdocs
    '''


    def __init__(self, task):
        '''
        Constructor
        '''
        self.C = task.wcet
        self.E = task.wcec
        self.D = Simulator.Simulator.simulation_time + task.period
        self.T = task
        self.prio = task.prio
