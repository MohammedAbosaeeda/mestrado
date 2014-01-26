'''
Created on Jan 13, 2011

@author: arliones
'''

class TaskLine:
    '''
    classdocs
    '''

    task_count = 6

    def __init__(self, task, now):
        '''
        Constructor
        '''
        self.task = task
#        self.seq_st = []
#        self.seq_len = []
        self.dots_x = []
        self.dots_y = []
        self.high   = TaskLine.task_count
        self.low    = TaskLine.task_count - 1
        
        self.dots_x.append(0.0)
        self.dots_y.append(self.low)

        TaskLine.task_count -= 2

    def AddDot(self, x, y):
        if len(self.dots_y) > 1 and y == self.dots_y[-1] == self.dots_y[-2]:
            self.dots_x[-1] = x
        else:
            self.dots_x.append(x)
            self.dots_y.append(y)

    def LastX(self):
        return self.dots_x[-1]

    def LastY(self):
        return self.dots_y[-1]
