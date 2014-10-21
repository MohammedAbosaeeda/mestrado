/*
 * scheduler_factory.cc
 *
 *  Created on: May 10, 2013
 *      Author: mateus
 */

#include <scheduler_factory.h>
#include <traits.h>
#include <scheduler.h>
#include <thread.h>
#include <verified_scheduler.h>
#include <verified_thread.h>

__BEGIN_SYS

void* Scheduler_Factory::create_scheduler()
{
    if (Traits<Scheduler_Traits>::enable_verification)
    {
        db<Scheduler_Traits>(TRC) << "Scheduler_Factory::create_scheduler: Creating Verified_Scheduler\n";

        return static_cast<Verified_Scheduler<Verified_Thread>*>(kmalloc(sizeof(Verified_Scheduler<Verified_Thread>)));
    }
    else
    {
        db<Scheduler_Traits>(TRC) << "Scheduler_Factory::create_scheduler: Creating Non-Verified Scheduler\n";

        return static_cast<Imp::Scheduler<Imp::Thread>*>(kmalloc(sizeof(Imp::Scheduler<Imp::Thread>)));
    }
}

__END_SYS

