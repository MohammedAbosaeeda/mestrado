'''
Created on Jan 13, 2011

@author: arliones

UNITS:
TIME -> seconds
ENERGY (BATT CAPACITY) -> mAh

'''

import sys, Simulator, RM_Scheduler, Task, TimeTablePlotter, BatteryMonitor, OutputReporter, SysParams, SolarPanel

def RunSimulation(sys_params):

    sim = Simulator.Simulator(0.100)

    batt = BatteryMonitor.BatteryMonitor(0.5055, sys_params.sim_length, sim)
    panel = SolarPanel.SolarPanel(sys_params.solar_data, batt)
    sched = RM_Scheduler.RM_Scheduler(0.100, batt)
    sim.AttachObserver(panel)
    sim.AttachObserver(sched)


    if sys_params.enable_graph:
        plot = TimeTablePlotter.TimeTablePlotter(batt)
        sched.AttachTimeTableObserver(plot)

    t1 = Task.Task('H1', 0.100, 0.025, 0.0010, 1)
    t2 = Task.Task('H2', 0.150, 0.025, 0.0014, 1)
    t3 = Task.Task('B1', 0.200, 0.050, 0.0020, 0)

    colector = Task.Task('C', sys_params.collector_period, 0.0000000453, 0.00000000004156, 1)

    sched.AddTask(t1)
    sched.AddTask(t2)
    sched.AddTask(t3)
    sched.AddTask(colector)

    sim.Run(sys_params.sim_length)

    if sys_params.enable_graph: plot.PlotGraph()


if __name__ == '__main__':

    sys_params = SysParams.SysParams(sys.argv)
    OutputReporter.OutputReporter.verbosity = sys_params.verbosity

    RunSimulation(sys_params)
