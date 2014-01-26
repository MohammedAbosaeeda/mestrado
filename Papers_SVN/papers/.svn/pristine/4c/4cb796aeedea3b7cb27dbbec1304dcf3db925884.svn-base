'''
Created on Jan 13, 2011

@author: arliones
'''

import OutputReporter

class Simulator:
    '''
    classdocs
    '''

    simulation_time = 0

    def __init__(self, step):
        '''
        Constructor
        '''
        self.step = step
        self.observers = []
        self.reporter = OutputReporter.OutputReporter()
        

    def Now(self):
        return self.simulation_time

    def AttachObserver(self, obs):
        self.observers.append(obs)

    def Run(self, until):
        total_steps = until / self.step
        one_percent_steps = total_steps / 100
        percent_counter = one_percent_steps
        steped = 0

        self.reporter.OutputLn(self.reporter.info, 'Simulation started')

        while self.simulation_time < until:
            for obs in self.observers:
                obs.TimeStep(self.Now())
            self.simulation_time += self.step
            steped += 1
            percent_counter -= 1
            if percent_counter <= 0:
                percent_complete = 100 * (steped / total_steps)
                self.reporter.OutputLn(self.reporter.debug, str(percent_complete) + '% complete')
                percent_counter = one_percent_steps

        self.reporter.OutputLn(self.reporter.info, 'Simulation complete (' + str(until) + ' time units)')
        for obs in self.observers:
            obs.NotifyEndOfSimulation()
