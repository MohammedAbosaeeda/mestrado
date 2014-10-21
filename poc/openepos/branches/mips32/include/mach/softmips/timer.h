// EPOS-- SoftMIPS Timer Mediator Declarations

#ifndef __softmips_timer_h
#define __softmips_timer_h

#include <timer.h>

__BEGIN_SYS

class SoftMIPS_Timer:  public Timer_Common
{
private:
    //static const unsigned char MASK_TIMER = 0x80;

    static const unsigned int CLOCK = Traits<Machine>::CLOCK;
	static const unsigned int FREQUENCY = Traits<Timer>::FREQUENCY;
public:
    // The timer's counter
    typedef CPU::Reg32 Count;

public:
    SoftMIPS_Timer()
	{
		//db<SoftMIPS_Timer>(TRC) << "AAAAAAAAAAAAAAAAAAAAASoftMIPS_Timer Default_Freq(f=" << FREQUENCY << ")\n";
		//frequency(FREQUENCY);
	}

    SoftMIPS_Timer(const Hertz & f) {
		//db<SoftMIPS_Timer>(TRC) << "SoftMIPS_Timer(f=" << f << ")\n";
		frequency(f);
		reset();
    }

    Hertz frequency() const
	{
		//db<SoftMIPS_Timer>(TRC) << "SoftMIPS_Timer ###DEBUG ==> _count= " << _count << ")\n";
		//db<SoftMIPS_Timer>(TRC) << "SoftMIPS_Timer ###DEBUG ==> this= " << (void *)this << ")\n";
		return count2freq(_count);
	}

	void frequency(const Hertz & f) {
		//db<SoftMIPS_Timer>(TRC) << "SoftMIPS_Timer ###DEBUG ==> f= " << f << ")\n";
		//db<SoftMIPS_Timer>(TRC) << "SoftMIPS_Timer ###DEBUG ==> _count= " << _count << ")\n";
		//db<SoftMIPS_Timer>(TRC) << "SoftMIPS_Timer ###DEBUG ==> this= " << (void *)this << ")\n";
		_count = freq2count(f);
		//db<SoftMIPS_Timer>(TRC) << "SoftMIPS_Timer ###DEBUG ==> _count= " << _count << ")\n";
		reset();
    }
    
    void reset() { // R4000 only
		//CPU::flags<CPU::CP0_COMPARE>(((CLOCK >> 1) / frequency()) + CPU::flags<CPU::CP0_COUNT>()); //ver isso david
    }

    void enable();
    void disable();

    Tick read();

    static int init(System_Info * si) { return 0; }

private:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }

private:
    Count _count;
};

__END_SYS

#endif
