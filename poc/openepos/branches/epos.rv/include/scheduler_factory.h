/*
 * scheduler_factory.h
 *
 *  Created on: May 10, 2013
 *      Author: mateus
 */

#ifndef SCHEDULER_FACTORY_H_
#define SCHEDULER_FACTORY_H_

#include <system/config.h>

__BEGIN_SYS

class Scheduler_Factory
{
public:
    static void* create_scheduler();
};

__END_SYS

#endif /* SCHEDULER_FACTORY_H_ */
