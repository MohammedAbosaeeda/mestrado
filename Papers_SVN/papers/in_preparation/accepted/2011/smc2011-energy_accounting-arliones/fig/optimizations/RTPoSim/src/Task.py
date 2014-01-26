'''
Created on Jan 13, 2011

@author: arliones
'''

class Task:
    '''
    classdocs
    '''


    def __init__(self, name, period, wcet, wcec, hard):
        '''
        Constructor
        '''
        self.name       = name
        self.period     = period
        self.wcet       = wcet
        self.wcec       = wcec
        self.prio       = 0
        self.time_to_go = period
        self.runned     = 0
        self.hard       = hard
        self.energy     = 0
