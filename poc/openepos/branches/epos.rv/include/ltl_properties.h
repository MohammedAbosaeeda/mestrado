/*
 * ltl_properties.h
 *
 *  Created on: May 2, 2013
 *      Author: mateus
 */

#ifndef LTL_PROPERTIES_H_
#define LTL_PROPERTIES_H_

#include <traits.h>
#include <utility/handler.h>
#include <system/kmalloc.h>

__BEGIN_SYS

class Alarm; /* alarm.h cannot be included from here, or else it will cause a
              * circular dependency between alarm.h and thread.h (alarm.h
              * needs thread.h and thread.h is still not complete and needs
              * alarm.h).
              *
              * Circular dependency:
              * alarm.h --> semaphore.h --> synchronizer.h --> thread.h
              * --> verified_thread.h --> ltl_properties.h --> alarm.h
              *
              * Where A --> B means: file A includes file B.
              *
              * So, this requires the creation of ltl_properties.cc
              *
              */

class Null_Property {};

template<typename, bool> class Absence_Of_Starvation;

template<typename Schedulable, bool enabled>
void starve(Absence_Of_Starvation<Schedulable, enabled>* property);

/*
 * Absence of starvation (Fairness)
 *
 * A[] not starvation
 *
 */
template<typename Schedulable, bool enabled>
class Absence_Of_Starvation
{
    typedef Absence_Of_Starvation<Schedulable, enabled> This;

    static const unsigned long STARVATION_DEADLINE;

    friend void starve<Schedulable, enabled>(Absence_Of_Starvation<Schedulable, enabled>* property);


public:
    Absence_Of_Starvation(Schedulable* schedulable)
    {
        db<Absence_Of_Starvation_Traits>(TRC) << "Absence_Of_Starvation creation: " << this << " \n";

        _schedulable = schedulable;
        _starving = false;
        _starvation_handler = new (kmalloc(sizeof(Functor_Handler<This>))) Functor_Handler<This>(&starve, this);
        _starvation_alarm = 0;
    }

public:
    void enable_starvation_alarm();

    // <<atomic>>
    void disable_starvation_alarm();

    void starving(bool v)
    {
        _starving = v;
        // use GooMFLibrary around here: For all threads: A[] not _starving
    }

    bool starving()
    {
        return _starving;
    }

    Schedulable* schedulable()
    {
        return _schedulable;
    }


public:
    static void begin_atomic()
    {
        CPU::int_disable();
    }

    static void end_atomic()
    {
        CPU::int_enable();
    }

    static unsigned int number_of_starvations()
    {
    	return _number_of_starvations;
    }


private:
    Schedulable* _schedulable;
    bool _starving;

private:
    Functor_Handler<This>* _starvation_handler;
    Alarm* _starvation_alarm;

private:
    static unsigned int _number_of_starvations;
};


// <<atomic>>
template<typename Schedulable, bool enabled>
void starve(Absence_Of_Starvation<Schedulable, enabled>* property)
{
    Absence_Of_Starvation<Schedulable, enabled>::begin_atomic();
    db<Absence_Of_Starvation_Traits>(TRC) << "Absence_Of_Starvation::check_starvation: " << property << " \n";


    property->starving(true);
    db<Absence_Of_Starvation_Traits>(ERR) <<
      "Schedulable: " << property->schedulable() << " id(" << property->schedulable()->id() << ")" << " is starving!\n";

    if (property->_starvation_alarm)
    {
        property->disable_starvation_alarm();
        property->_starvation_alarm = 0;
    }

    property->_number_of_starvations ++;

    Absence_Of_Starvation<Schedulable, enabled>::end_atomic();
}


template<typename Schedulable>
class Absence_Of_Starvation<Schedulable, false> : public Null_Property
{
public:
    void enable_starvation_alarm()
    {
    }

    void disable_starvation_alarm()
    {
    }

};





__END_SYS

#endif /* LTL_PROPERTIES_H_ */

