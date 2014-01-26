'''
Created on Feb 3, 2011

@author: arliones
'''

class OutputReporter:
    '''
    classdocs
    Output levels:
    0 - error
    1 - warning
    2 - info
    3 - debug
    '''

    off = -1
    error = 0
    warning = 1
    optimzer = 2
    info = 3
    debug = 4
    verbosity = 0

    def __init__(self):
        '''
        Constructor
        '''

    def Output(self, level, output):
        if self.verbosity >= level:
            print output,

    def OutputLn(self, level, output):
        if self.verbosity >= level:
            print output
