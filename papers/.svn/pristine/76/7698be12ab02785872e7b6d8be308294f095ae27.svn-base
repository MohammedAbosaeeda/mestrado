'''
Created on Feb 3, 2011

@author: arliones
'''

class SysParams:
    '''
    classdocs
    '''


    def __init__(self, argv):
        '''
        Constructor
        '''
        self.verbosity = -1
        self.collector_period = 1
        self.sim_length = 3600
        self.enable_graph = 0
        self.solar_data = "irrad.dat"

        for i in range(1, len(argv)):
            if argv[i] == '--verbosity':
                self.verbosity = int(argv[i+1])
            elif argv[i] == '--collector_period':
                self.collector_period = float(argv[i+1])
            elif argv[i] == '--sim_length':
                self.sim_length = float(argv[i+1])
            elif argv[i] == '--enable_graph':
                self.enable_graph = 1;
            elif argv[i] == '--solar_data':
                self.solar_data = argv[i+1];
