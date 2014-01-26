'''
Created on Jan 13, 2011

@author: arliones
'''

import TaskLine, OutputReporter
import random

import matplotlib.pyplot as plt


class TimeTablePlotter:
    '''
    classdocs
    '''

    def __init__(self, battery):
        '''
        Constructor
        '''
        self.now = 0
        self.tasks = []
        self.battery = battery
        self.reporter = OutputReporter.OutputReporter
        random.seed(1)

    def Search(self, task):
        for tl in self.tasks:
            if tl.name == task.name: return tl
        return 0

    def LowAllBut(self, task_name, time):
        for t in self.tasks:
            if t.name == task_name:
                t.AddDot(self.now, t.high)
                t.AddDot(self.now + time, t.high)
            else:
                t.AddDot(self.now, t.low)
                t.AddDot(self.now + time, t.low)

    def IncludeTask(self, task):
        tl = TaskLine.TaskLine(task, self.now)
        self.tasks.append(tl)

    def ReportTask(self, task, time):
        if task == 0:
            self.LowAllBut('', time)
            self.battery.ReportConsumption(0, time, 0, 0)
        else:
            tl = self.Search(task)
            self.LowAllBut(tl.name, time)

        self.now += time

    def PlotText(self):
        for tl in self.tasks:
            self.reporter.Output(self.reporter.info, "Task " + tl.name + ": ")
            for i in range(len(tl.dots_x)):
                self.reporter.Output(self.reporter.info,  "(" + str(tl.dots_x[i]) + "," + str(tl.dots_y[i]) + ") ")
            self.reporter.OutputLn(self.reporter.info, '')

    def PlotGraph(self):
        ax1 = plt.subplot(211)
        plt.plot(self.tasks[0].dots_x, self.tasks[0].dots_y, 'red'
               , self.tasks[1].dots_x, self.tasks[1].dots_y, 'blue'
               , self.tasks[2].dots_x, self.tasks[2].dots_y, 'green'
               )
        plt.title('(a) Rate Monotonic Schedule')
        plt.axis([0, self.tasks[0].LastX(), 0, 7])
        ax1.set_yticks([1.5, 3.5, 5.5])
        ax1.set_yticklabels([self.tasks[2].name, self.tasks[1].name, self.tasks[0].name])
        ax1.set_xlabel('time (s)')
        ax1.set_ylabel('task set')

        ax2 = plt.subplot(212)
        plt.plot(self.battery.time, self.battery.charge  , 'black'
#               , self.battery.time, self.battery.reserved, 'red'
               )
        plt.axis([0, self.battery.LastTime(), self.battery.LastCharge(), self.battery.charge[0]])
        plt.title('(b) Battery Discharge')
        ax2.set_xlabel('time (s)')
        ax2.set_ylabel('battery charge (mAh)')
#        one_year_en = (3600*24*365*self.total)/(self.battery.LastTime())
#        plt.text(0.8, 0.9,'1-year energy = ' + str(one_year_en),
#                 bbox=dict(facecolor='white', alpha=0.5),
#                 horizontalalignment='center', verticalalignment='center',
#                 transform = ax2.transAxes)
        plt.text(0.7, 0.9,'Consumed capacity = ' + str(round(self.total*1000,4)) + ' uAh',
                 bbox=dict(facecolor='white', alpha=0.5),
                 horizontalalignment='center', verticalalignment='center',
                 transform = ax2.transAxes)

        plt.show()

#        fig = plt.figure()
#        ax = fig.add_subplot(111)
#        x, y = range(2)
#        line = lines.Line2D()
#        line = lines.Line2D(x, y, mfc='red', ms=12, label='my line')
#        line.text.set_color('blue')
#        line.text.set_fontsize(12)
#        
#        ax.add_line(line)
#        
#        plt.show()

    def NotifyEndOfSimulation(self):
        '''
        ...
        '''