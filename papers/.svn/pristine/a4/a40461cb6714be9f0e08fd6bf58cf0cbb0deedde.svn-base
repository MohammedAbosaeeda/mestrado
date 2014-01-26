'''
Created on Jan 13, 2011

@author: arliones
'''

import Job, OutputReporter, random

class RM_Scheduler:
    '''
    This is a Preemptive Rate Monotonic Scheduler
    '''


    def __init__(self, quantum, battery):
        '''
        Constructor
        '''
        self.quantum = quantum
        self.taskset = []
        self.queue   = []
        self.time_table_obs = 0
        self.last_update = 0
        self.en_budget = 1
        self.battery = battery
        self.reporter = OutputReporter.OutputReporter()
#        self.random_gen = random.random()
#        self.random_gen.seed(1)
        random.seed(1)


    def schedQueueInsertOrdered(self, job):
        j = 0
        for j in self.queue:
            if job.prio < j.prio:
                self.queue.insert(self.queue.index(j), job)
                return
        self.queue.append(job)
        
#        print("Ready queue: "),
#        for i in self.queue:
#            print(i.T.name + " "),
#        print()

    def AddTask(self, task):
        self.taskset.append(task)
        if self.time_table_obs != 0: self.time_table_obs.IncludeTask(task)
        if task.hard == 1: self.battery.Reserve(task.wcec, task.period)
        # Rate Monotonic priority attribution
        task.prio = task.period
        # Create Critical Instant
        task.time_to_go = 0
        self.triggerTasks(0)

    def triggerTasks(self, time_step):
        for t in self.taskset:
            t.time_to_go -= time_step;
            if t.time_to_go <= 0:
#                print("job of task "+t.name)
                if t.hard == 1 or (t.hard == 0 and self.battery.LifetimeCheck() == 1):
                    self.schedQueueInsertOrdered(Job.Job(t))
                    t.runned += 1
                t.time_to_go += t.period

    def runJob(self, job, time):
#        print('Running task ' + job.T.name)# ' - ' + str(time) + ' seconds')
        if job.C > time:
            job.C -= time
        elif job.C == time:
            job.C = 0
            self.queue.remove(job)
        else:
            time = job.C
            self.queue.remove(job)
        if job.T.name == 'C': self.battery.UpdateAccountedCharge()
        return time

    def updateSchedule(self):
        time_to_allocate = self.quantum
        while time_to_allocate > 0 and len(self.queue) > 0:
            job = self.queue[0]
            time_run = self.runJob(job, time_to_allocate)
            if self.time_table_obs != 0: self.time_table_obs.ReportTask(job.T, time_run)
            #update energy
            consumed = job.T.wcec
            if random.uniform(0.0,1.0) >= 0.75:
                consumed *= 0.5 #random.uniform(0.5,1.0)
            consumed *= (time_run/job.T.wcet)
            self.battery.ReportConsumption(consumed, time_run, (time_run/job.T.wcet)*job.T.wcec, job.T.hard)
            job.T.energy += consumed

            time_to_allocate -= time_run
        if time_to_allocate > 0 and self.time_table_obs != 0: self.time_table_obs.ReportTask(0, time_to_allocate)

    def AttachTimeTableObserver(self, obs):
        self.time_table_obs = obs

    def TimeStep(self, time):
        ''' Process one simulation step. '''
        self.triggerTasks(time - self.last_update)
        self.updateSchedule()
        self.last_update = time

    def NotifyEndOfSimulation(self):
        activations = 0
        should_run  = 0
        for t in self.taskset:
            self.reporter.OutputLn(self.reporter.info, "Task " + t.name + " runs: " + str(t.runned))
            if t.hard == 0:
                activations += t.runned
                should_run  += (1/t.period) * self.battery.lifetime
        if self.time_table_obs != 0: self.time_table_obs.NotifyEndOfSimulation()

        self.total = 0
        for t in self.taskset:
            self.reporter.OutputLn(self.reporter.info, "Task " + t.name + " energy consumption: " + str(t.energy))
            self.total += t.energy
        self.reporter.OutputLn(self.reporter.info, "Total energy consumption: " + str(self.total))

#        if self.reporter.verbosity == self.reporter.optimzer:
        self.reporter.OutputLn(self.reporter.optimzer, should_run - activations)
        self.reporter.OutputLn(self.reporter.optimzer, self.total - self.battery.charge[0])
