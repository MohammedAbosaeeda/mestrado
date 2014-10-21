// EPOS CPU Affinity Scheduler Abstraction Implementation

#include <scheduler.h>
#include <alarm.h>

__BEGIN_SYS

// Class attributes
int Scheduling_Criteria::CPU_Affinity::_next_cpu;
int Scheduling_Criteria::PEDF::_next_cpu;
int Scheduling_Criteria::PRM::_next_cpu;
int Scheduling_Criteria::CEDF::_next_cluster;
int Scheduling_Criteria::CEDF::_next_cpu;

//class atributes
//unsigned int Scheduling_Criteria::Shared_Data::_bitmap[Traits<MMU>::colors] = { 0 };

Scheduling_Criteria::EDF::EDF(const RTC::Microsecond & d) 
: Priority(Alarm::get_elapsed() + Alarm::ticks(d)), _deadline(d)
{
};



__END_SYS
