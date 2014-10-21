/*! @file
 *  @brief EPOS ML310 Timer Mediator Declarations
 *
 *  CVS Log for this file:
 *  \verbinclude include/mach/ml310/timer_h.log
 */
#ifndef __ml310_timer_h
#define __ml310_timer_h

#include <cpu.h>
#include <ic.h>
#include <timer.h>

__BEGIN_SYS

class ML310_Timer:  public Timer_Common
{
private:
    // PPC405_Timer private imports, types and constants
    static const int CLOCK = Traits<ML310>::CLOCK;
    typedef CPU::Reg32 Reg32;
    typedef Reg32 Count;

public:
    ML310_Timer(int unit = 0) {} // actual initialization is up to init
    ~ML310_Timer() {}
    Hertz frequency() {  return cnt2freq(_count); }
    void frequency(const Hertz & f) {
        _count = freq2cnt(f);
        reset();
        db<ML310_Timer>(INF) << "ML310_Timer::resolution(res=" << frequency()
                             << ",cnt=" << _count << ")\n";
    }

    void enable() { //Verify latter ...
        PPC32::int_disable();
        volatile Reg32 value;
        value = PPC32::spr(CPU::TCR);
        value |= 0x04400000;
        PPC32::spr(CPU::TCR, value);
        PPC32::int_enable();
    }

    void disable() { //Verify latter ...
        PPC32::int_disable();
        volatile PPC32::Reg32 value;
        value = PPC32::spr(CPU::TCR);
        value &= ~0x04000000;
        PPC32::spr(CPU::TCR, value);
        PPC32::int_enable();
    }

    void reset() {
        PPC32::spr(CPU::PIT, _count);
    }

    static void init();

private:
    // ML310_Timer implementation methods
    static Hertz cnt2freq(unsigned int c) { return CLOCK / c; }
    static unsigned int freq2cnt(const Hertz & f) { return CLOCK / f; }

private:
    // ML310_Timer attributes
    Count _count;

};

__END_SYS

#endif
