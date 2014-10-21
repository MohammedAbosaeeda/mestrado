// EPOS CPU Time-Stamp Counter Mediator Declarations

#ifndef __cpu_tsc_h
#define __cpu_tsc_h

#include <cpu.h>
#include <tsc.h>

__BEGIN_SYS

class CPU_TSC: public TSC_Common
{
public:
    CPU_TSC() {}

    static Hertz frequency() { return CPU::clock(); }

    static Time_Stamp time_stamp() {
        unsigned int low;
        unsigned int high;
        asm volatile("rdtsc" : "=d"(high), "=a"(low) : : );
        return (((unsigned long long)high << 32) | low);
    }
};

__END_SYS

#endif
