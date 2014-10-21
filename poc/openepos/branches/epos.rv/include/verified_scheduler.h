/*
 * verified_scheduler.h
 *
 *  Created on: May 2, 2013
 *      Author: mateus
 */

#ifndef VERIFIED_SCHEDULER_H_
#define VERIFIED_SCHEDULER_H_

#include "ltl_properties.h"

__BEGIN_SYS

template<typename T>
class Scheduler_Contract
{
public:
    void invariants(Imp::Scheduler<T>* thiz)
    {
        assert(thiz->size() >= 0);
    }
};


template<typename T>
class Scheduler_Insert_Contract /* : public Scheduler_Contract */
{
public:

    void preconditions(Imp::Scheduler<T>* thiz, T* obj)
    {
        assert(! thiz->contains(obj));
        _size_at_pre = thiz->size();
    }

    void postconditions(Imp::Scheduler<T>* thiz, T* obj)
    {
        assert(thiz->contains(obj));
        assert(thiz->size() == _size_at_pre + 1);
    }

private:
    unsigned int _size_at_pre;

};

// <<Scenario_Adapter>>, <<Verified_Scenario>>
template<typename T>
class Verified_Scheduler : public Imp::Scheduler<T>,
                           protected Scheduler_Contract<T>,
                           protected Scheduler_Insert_Contract<T>
{
protected:
    typedef Imp::Scheduler<T> Base;

public:

    unsigned int schedulables()
    {
        return Base::schedulables();
    }


    T* volatile chosen()
    {
        return Base::chosen();
    }

    void insert(T* obj)
    {
        db<Scheduler_Traits>(TRC) << "Verified_Scheduler::insert: " << obj << " \n";

        // Scheduler_Contract::invariants(this);
        // Scheduler_Insert_Contract::preconditions(this, obj);
        Base::insert(obj);
        // Scheduler_Insert_Contract::postconditions(this, obj);
        // Scheduler_Contract::invariants(this);

        /* LTL properties */
        obj->enable_starvation_alarm();
    }


    T* remove(T* obj)
    {

        T* result = Base::remove(obj);

        /* LTL properties */
        if (result)
        {
            result->disable_starvation_alarm();
        }

        return result;
    }


    void suspend(T* obj)
    {
        db<Scheduler_Traits>(TRC) << "Verified_Scheduler::suspend: " << obj << " \n";

        Base::suspend(obj);

        /* LTL properties */
        obj->disable_starvation_alarm();
    }


    void resume(T* obj)
    {
        Base::resume(obj);

        /* LTL properties */
        obj->enable_starvation_alarm();
    }


    T* choose()
    {
        T* result = Base::choose();

        /* LTL properties */
        result->disable_starvation_alarm();

        return result;
    }


    T* choose_another()
    {
        T* result = Base::choose_another();

        /* LTL properties */
        result->disable_starvation_alarm();

        return result;
    }


    T* choose(T* obj)
    {
        T* result = Base::choose(obj);

        /* LTL properties */
        if (result)
        {
            result->disable_starvation_alarm();
        }

        return result;
    }
};

__END_SYS

#endif /* VERIFIED_SCHEDULER_H_ */
