/*
 * verified_thread.h
 *
 *  Created on: May 7, 2013
 *      Author: mateus
 */

#ifndef VERIFIED_THREAD_H_
#define VERIFIED_THREAD_H_

#include <traits.h>
#include <ltl_properties.h>
#include <verified_scheduler.h>

__BEGIN_SYS

// <<Scenario_Adapter>>, <<Verified_Scenario>>
class Verified_Thread : public Imp::Thread,
                        public Absence_Of_Starvation<Verified_Thread, Traits<Imp::Thread >::verify_absence_of_starvation >
{
public:
    // Friendship
    // friend class Imp::Scheduler<Verified_Thread>; // maybe must have this too
    friend class Verified_Scheduler<Verified_Thread>;
    // friend class Synchronizer_Common; // must have this

protected:
    typedef Absence_Of_Starvation<Verified_Thread,
        Traits<Imp::Thread>::verify_absence_of_starvation > AoS;

public:
    // Constructors
    Verified_Thread(int id,
            int (* entry)(),
            const Imp::Thread::State & state = READY,
            const Imp::Thread::Criterion & criterion = NORMAL,
            unsigned int stack_size = STACK_SIZE) : Imp::Thread(id, entry, state, criterion, stack_size),
                                                    AoS(this)
    {
        _id = id;
    }


    template<typename T1>
    Verified_Thread(int id,
            int (* entry)(T1 a1), T1 a1,
            const Imp::Thread::State & state = READY,
            const Imp::Thread::Criterion & criterion = NORMAL,
            unsigned int stack_size = STACK_SIZE) : Imp::Thread(id, entry, a1, state, criterion, stack_size),
                                                    AoS(this)
    {
        _id = id;
    }


    template<typename T1, typename T2>
        Verified_Thread(int id,
                int (* entry)(T1 a1, T2 a2), T1 a1, T2 a2,
            const Imp::Thread::State & state = READY,
            const Imp::Thread::Criterion & criterion = NORMAL,
            unsigned int stack_size = STACK_SIZE) : Imp::Thread(id, entry, a1, a2, state, criterion, stack_size),
                                                    AoS(this)
    {
        _id = id;
    }


    template<typename T1, typename T2, typename T3>
            Verified_Thread(int id,
                    int (* entry)(T1 a1, T2 a2, T3 a3), T1 a1, T2 a2, T3 a3,
            const Imp::Thread::State & state = READY,
            const Imp::Thread::Criterion & criterion = NORMAL,
            unsigned int stack_size = STACK_SIZE) : Imp::Thread(id, entry, a1, a2, a3, state, criterion, stack_size),
                                                    AoS(this)
    {
        _id = id;
    }

public:
    // Public methods
    int id()
    {
        return _id;
    }

    /*!
     * If using Verified_Thread as base class of this class, returns true if and
     * only if this thread is the main thread or the idle thread.
     *
     * If not using Verified_Thread as base class of this class, returns always
     * false.
     *
     * Only works while using the Verified_Thead as baseclass.
     * Because if not using Verified_Thread, there is no id.
     *
     *
     */
    bool system_thread()
    {
        return (id() == MAIN_ID) || (id() == IDLE_ID);
    }

protected:
    // Instance variables
    int _id;

};

__END_SYS

#endif /* VERIFIED_THREAD_H_ */
