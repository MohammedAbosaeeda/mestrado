'''
Created on Jan 14, 2011

@author: arliones
'''


class BatteryMonitor:
    '''
    classdocs
    '''


    def __init__(self, initial_charge, lifetime, sim):
        '''
        Constructor
        '''
        self.reserved = []
        self.reserved.append(0.0)
        self.lifetime = lifetime
        self.charge = []
        self.charge.append(initial_charge)
        self.time   = []
        self.time.append(0.0)
        self.recharge = []
        self.recharge.append(0.0)
        self.accounted_charge = initial_charge
        self.sim = sim

    def ReportRecharge(self, amount, time_step):
        if self.LastTime() == self.sim.Now():
            self.charge.append(self.charge.pop() + amount)
            self.recharge.append(self.recharge.pop() + amount)
        else:
            self.charge.append(self.LastCharge() + amount)
            self.recharge.append(amount)
            self.time.append(self.LastTime() + time_step)
        self.reserved.append(self.LastReserved())

    def ReportConsumption(self, consumed, time_step, wcec, hard):
        if self.LastTime() == self.sim.Now():
            self.charge.append(self.charge.pop() - consumed)
            if hard == 1:
                self.reserved.append(self.reserved.pop() - wcec)
        else:
            self.charge.append(self.LastCharge() - consumed)
            self.time.append(self.LastTime() + time_step)
            if hard == 1:
                self.reserved.append(self.LastReserved() - wcec)
            else:
                self.reserved.append(self.LastReserved())

    def Reserve(self, charge, period):
        self.reserved.append(self.LastReserved() + ((self.lifetime - self.LastTime()) / period) * charge)
        self.charge.append(self.LastCharge())
        self.time.append(self.LastTime())

    def LastCharge(self):
        return self.charge[-1]
    
    def LastTime(self):
        return self.time[-1]

    def LastReserved(self):
        return self.reserved[-1]

    def TotalConsumed(self):
        return self.charge[0] - self.LastCharge()

    def AccountedCharge(self):
        return self.accounted_charge

    def UpdateAccountedCharge(self):
        self.accounted_charge = self.LastCharge()

    def LifetimeCheck(self):
        return (self.LastReserved() <= self.AccountedCharge())


#        discharge_rate = 0
#        if self.LastTime() != 0:
#            discharge_rate = (self.charge[0] - self.LastCharge())/self.LastTime()
#        remaining_time = self.lifetime - self.LastTime()
#        to_be_consumed = remaining_time * discharge_rate
#        print str(to_be_consumed)
#        return (to_be_consumed > self.reserved)
