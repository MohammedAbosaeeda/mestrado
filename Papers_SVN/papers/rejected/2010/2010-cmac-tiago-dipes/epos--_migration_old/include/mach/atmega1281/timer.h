// EPOS-- ATMega1281 Timer Mediator Declarations

#ifndef __atmega1281_timer_h
#define __atmega1281_timer_h

#include "../avr_common/timer.h"

__BEGIN_SYS

class ATMega1281_Timer: public Timer_Common, private AVR_Timer
{
private:
    static const unsigned int CLOCK = Traits<Machine>::CLOCK >> 10;

public:
    // Register Settings
    enum {
    	TIMER_PRESCALE_1    = CSn0,
	TIMER_PRESCALE_8    = CSn1,
	TIMER_PRESCALE_32   = CSn1 | CSn0,
	TIMER_PRESCALE_64   = CSn2,
	TIMER_PRESCALE_128  = CSn2 | CSn0,
	TIMER_PRESCALE_256  = CSn2 | CSn1,
	TIMER_PRESCALE_1024 = CSn2 | CSn1 | CSn0
    };

public:
    ATMega1281_Timer() {}

    ATMega1281_Timer(const Hertz & f) {
	db<PC_Timer>(TRC) << "ATMega1281_Timer(f=" << f << ")\n";
	frequency(f);
    }

    Hertz frequency() const { return count2freq(ocr2a()); }
    void frequency(const Hertz & f) {
	ocr2a(freq2count(f));
	tccr2a(WGM21);
	tccr2b(TIMER_PRESCALE_1024);
    };

    void reset() { tcnt2(0); }

    void enable(){ timsk2(timsk2() | OCIE2A); }
    void disable(){ timsk2(timsk2() & ~OCIE2A); }

    Tick read() { return tcnt2(); }

protected:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }
};


class ATMega1281_Timer_2: public Timer_Common, private AVR_Timer
{
private:
    static const unsigned int CLOCK = Traits<Machine>::CLOCK >> 10;

public:
    ATMega1281_Timer_2() {}

    ATMega1281_Timer_2(const Hertz & f) {
	frequency(f);
    }

    Hertz frequency() const { return count2freq(ocr0a()); }
    void frequency(const Hertz & f) {
	ocr0a(freq2count(f));
	tccr0a(WGM01);
	tccr0b(TIMER_PRESCALE_1024);
    };

    void reset() { tcnt0(0); }

    void enable(){ timsk0(timsk0() | OCIE0A); }
    void disable(){ timsk0(timsk0() & ~OCIE0A); }

    Tick read() { return tcnt0(); }

protected:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }
};



class ATMega1281_Timer_3: public Timer_Common, private AVR_Timer
{
private:
    static const unsigned int CLOCK = Traits<Machine>::CLOCK >> 10;

public:
    ATMega1281_Timer_3() {}

    ATMega1281_Timer_3(const Hertz & f) {
	frequency(f);
    }

    void enable(){ timsk3(timsk3() | OCIE3A); }
    void disable(){timsk3(timsk3() & ~OCIE3A);}

    Hertz frequency() const { return count2freq(ocr3a());}
    void frequency(const Hertz & f) {
	ocr3a(freq2count(f));
	tccr3b(TIMER_PRESCALE_1024|WGMn2);
    };

    void reset() { tcnt3(0); }
    Tick read() { return tcnt3(); }

protected:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }

};

__END_SYS

#endif

