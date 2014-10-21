/*! @file
 *  @brief EPOS Timer Mediator Common Package
 *
 *  CVS Log for this file:
 *  \verbinclude include/timer_h.log
 */
#ifndef __timer_h
#define __timer_h

#include <tsc.h>

__BEGIN_SYS

class Timer_Common
{
protected:
    Timer_Common() {}

public:
    typedef TSC::Hertz Hertz;
    typedef TSC::Hertz Tick;
};

__END_SYS

#ifdef __TIMER_H
#include __TIMER_H
#endif

#endif
