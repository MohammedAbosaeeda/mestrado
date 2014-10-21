/*! @file
 *  @brief EPOS Spin Lock Utility Declarations
 *
 *  CVS Log for this file:
 *  \verbinclude include/utility/spin_h.log
 */
#ifndef __spin_h
#define __spin_h

#include <cpu.h>

__BEGIN_SYS

class Spin
{
public:
    Spin(): _lock(false) {}

    void acquire() { while(CPU::tsl(_lock)); }
    void release() { _lock = false; }

private:
    volatile bool _lock;
};

__END_SYS

#endif
