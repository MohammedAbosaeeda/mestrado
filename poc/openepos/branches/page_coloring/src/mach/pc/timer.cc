// EPOS PC Timer Mediator Implementation

#include <machine.h>
#include <timer.h>
//#include <semaphore.h>

__BEGIN_SYS

// Class attributes
PC_Timer * PC_Timer::_channels[CHANNELS];
//Semaphore sem;

// Class methods
void PC_Timer::int_handler(unsigned int i)
{       
    if(Traits<Thread>::smp) {
        //TSC::Time_Stamp start = TSC::time_stamp();
      if(_channels[ALARM]) {
         //TSC::Time_Stamp end = TSC::time_stamp();
         //if(Machine::cpu_id() == 0) 
         //    kout << "Timer::int_handler = " << end - start << "\n";
        _channels[ALARM]->_handler();
      }
    } else
      if((Machine::cpu_id() == 0) && _channels[ALARM])
      _channels[ALARM]->_handler();
    
    //if(Traits<Thread>::pmu_sampling) {
    //    if(_channels[Machine::cpu_id()] && (--_channels[Machine::cpu_id()]->_current[Machine::cpu_id()] <= 0)) {
            /*if(Machine::cpu_id() == 3) {
              kout << "Timer handler CPU ID = " << Machine::cpu_id() 
              << " _channels[Machine::cpu_id()]->_current =  " << _channels[Machine::cpu_id()]->_current << " ";
            }*/
    /*        _channels[Machine::cpu_id()]->_current[Machine::cpu_id()] = _channels[Machine::cpu_id()]->_initial;
            _channels[Machine::cpu_id()]->_handler();    
        }
    }*/
   
    if(_channels[SCHEDULER] && (--_channels[SCHEDULER]->_current[Machine::cpu_id()] <= 0)) {
      //sem.p();
      //kout << "CPU ID = " << Machine::cpu_id() << endl;
      //sem.v();
	_channels[SCHEDULER]->_current[Machine::cpu_id()] = _channels[SCHEDULER]->_initial;
	_channels[SCHEDULER]->_handler();
    }
    
}

__END_SYS
