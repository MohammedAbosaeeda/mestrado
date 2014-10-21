// EPOS-- LEON2 Timer Mediator Declarations

#ifndef __leon2_timer_h
#define __leon2_timer_h

#include <timer.h>

__BEGIN_SYS

class LEON2_Timer: public Timer_Common
{
private:
    static const unsigned int CLOCK = Traits<Machine>::CLOCK / 0x3ff;

    // Timer 1 registers
    enum {
      COUNTER  = Traits<LEON2_Timer>::BASE_ADDRESS + 0x00,
      RELOAD   = Traits<LEON2_Timer>::BASE_ADDRESS + 0x04,
      CONTROL  = Traits<LEON2_Timer>::BASE_ADDRESS + 0x08,
      PRE_SCAL = Traits<LEON2_Timer>::BASE_ADDRESS + 0x24,
    };

    // Control Register Bits
    enum {
        EN = 0x1, // Enable
        RL = 0x2, // Reload (wait overflow before reloading)
        LD = 0x4  // Load (immediatly)
    };

public:
    // The timer's counter
    typedef CPU::Reg32 Count;

public:
    LEON2_Timer() {}

    LEON2_Timer(const Hertz & f) {
	db<LEON2_Timer>(TRC) << "LEON2_Timer(f=" << f << ")\n";
	frequency(f);
	reset();
    }
    
    Hertz frequency() const { return count2freq(_count); }
    void frequency(const Hertz & f) { 
	_count = freq2count(f);
	reset();
    }

    void reset() {
        *(reinterpret_cast<unsigned int *>(CONTROL)) |= LD; 
        *(reinterpret_cast<unsigned int *>(PRE_SCAL)) = 0x3ff;
        *(reinterpret_cast<unsigned int *>(RELOAD)) = _count;
    }
   
    void enable() { 
        *(reinterpret_cast<unsigned int *>(CONTROL)) |= EN;
    }
    void disable() { 
        *(reinterpret_cast<unsigned int *>(CONTROL)) &= ~EN;
    }

    Tick read() { return *(reinterpret_cast<Tick *>(COUNTER)); }

    static int init(System_Info * si) { return 0; }

private:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }

private:
    Count _count;
};

__END_SYS

#endif
