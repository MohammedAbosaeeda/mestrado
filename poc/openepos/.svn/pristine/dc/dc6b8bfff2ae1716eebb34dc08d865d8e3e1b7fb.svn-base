/*
 * ltl_properties.cc
 *
 *  Created on: May 8, 2013
 *      Author: mateus
 */

#include <alarm.h>
#include <ltl_properties.h>
#include <traits.h>

__BEGIN_SYS

template<typename Schedulable, bool enabled>
unsigned int Absence_Of_Starvation<Schedulable, enabled>::_number_of_starvations = 0;

template<typename Schedulable, bool enabled>
const unsigned long Absence_Of_Starvation<Schedulable, enabled>::STARVATION_DEADLINE = Traits<Absence_Of_Starvation_Traits>::STARVATION_DEADLINE;

template<typename Schedulable, bool enabled>
void Absence_Of_Starvation<Schedulable, enabled>::enable_starvation_alarm()
{
    if (Traits<Absence_Of_Starvation_Traits>::check_starvation_of_system_threads || ! schedulable()->system_thread())
    {
        db<Absence_Of_Starvation_Traits>(TRC) << "Absence_Of_Starvation::enable_starvation_alarm: " << this << " \n";
        _starvation_alarm = new (kmalloc(sizeof(Alarm))) Alarm(STARVATION_DEADLINE, _starvation_handler, 1);
    }
}

// <<atomic>>
template<typename Schedulable, bool enabled>
void Absence_Of_Starvation<Schedulable, enabled>::disable_starvation_alarm()
{
    if (Traits<Absence_Of_Starvation_Traits>::check_starvation_of_system_threads || ! schedulable()->system_thread())
    {
        begin_atomic();
        db<Absence_Of_Starvation_Traits>(TRC) << "Absence_Of_Starvation::disable_starvation_alarm: " << this << " \n";
        if (_starvation_alarm)
        {
            kfree(_starvation_alarm);
            _starvation_alarm = 0;
        }
        end_atomic();
    }
}

// Explicit template instantiation
template class Absence_Of_Starvation<Verified_Thread, true>;


__END_SYS
