// EPOS-- ATMega1281 Timer Mediator Declarations

#ifndef __atmega1281_timer_h
#define __atmega1281_timer_h

#include "../avr_common/timer.h"
#include <rtc.h>
#include <ic.h>

__BEGIN_SYS

class ATMega1281_Timer: public Timer_Common, private AVR_Timer
{
public:
    static int init();
};


class ATMega1281_Timer_1: public Timer_Common, private AVR_Timer
{
public:
    static const unsigned int FREQUENCY = Traits<ATMega1281_Timer_1>::FREQUENCY;
private:
    static const Hertz MACHINE_CLOCK = Traits<Machine>::CLOCK;
    static const unsigned int CLOCK = Traits<Machine>::CLOCK >> 10;

    typedef RTC::Microsecond Microsecond;

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
    ATMega1281_Timer_1() {}

    ATMega1281_Timer_1(const Hertz & f) {
	db<PC_Timer>(TRC) << "ATMega1281_Timer(f=" << f << ")\n";
	frequency(f);
    }

    ATMega1281_Timer_1(const Handler * handler) {
    frequency(FREQUENCY);
    _handler = handler;
    enable();
    }

    ATMega1281_Timer_1(const Microsecond & quantum, const Handler * handler) {
    frequency(1000000 / quantum);
    _handler = handler;
    enable();
    }

    const void clock(const Hertz clock) {
        tccr2a(WGM21);
        if(clock == MACHINE_CLOCK)
            tccr2b(TIMER_PRESCALE_1);
        else if (clock == (MACHINE_CLOCK >> 3))
            tccr2b(TIMER_PRESCALE_8);
        else if (clock == (MACHINE_CLOCK >> 6))
            tccr2b(TIMER_PRESCALE_64);
        else if (clock == (MACHINE_CLOCK >> 8))
            tccr2b(TIMER_PRESCALE_256);
        else
            tccr2b(TIMER_PRESCALE_1024);
    }

    Hertz frequency() const { return count2freq(ocr2a()); }
    void frequency(const Hertz & f) {
	ocr2a(freq2count(f));
	clock(CLOCK);
    };

    void reset() { tcnt2(0); }

    void enable(){ timsk2(timsk2() | OCIE2A); }
    void disable(){ timsk2(timsk2() & ~OCIE2A); }

    Tick read() { return tcnt2(); }

    void handler(Handler * handler) { _handler = handler; }

    static int init();

private:
    static void int_handler(unsigned int irq);

protected:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }

protected:
    static Handler * _handler;
};


class ATMega1281_Timer_2: public Timer_Common, private AVR_Timer
{
public:
    static const unsigned int FREQUENCY = Traits<ATMega1281_Timer_2>::FREQUENCY;
private:
    static const Hertz MACHINE_CLOCK = Traits<Machine>::CLOCK;
    static const unsigned int CLOCK = Traits<Machine>::CLOCK >> 10;

    typedef RTC::Microsecond Microsecond;

public:
    ATMega1281_Timer_2() {}

    ATMega1281_Timer_2(const Hertz & f) {
	frequency(f);
    }

    ATMega1281_Timer_2(const Handler * handler) {
    frequency(FREQUENCY);
    _handler = handler;
    enable();
    }

    ATMega1281_Timer_2(const Microsecond & quantum, const Handler * handler) {
    frequency(1000000 / quantum);
    _handler = handler;
    enable();
    }

    const void clock(const Hertz clock) {
        tccr0a(WGM01);
        if(clock == MACHINE_CLOCK)
            tccr0b(TIMER_PRESCALE_1);
        else if (clock == (MACHINE_CLOCK >> 3))
            tccr0b(TIMER_PRESCALE_8);
        else if (clock == (MACHINE_CLOCK >> 6))
            tccr0b(TIMER_PRESCALE_64);
        else if (clock == (MACHINE_CLOCK >> 8))
            tccr0b(TIMER_PRESCALE_256);
        else
            tccr0b(TIMER_PRESCALE_1024);
    }

    Hertz frequency() const { return count2freq(ocr0a()); }
    void frequency(const Hertz & f) {
	ocr0a(freq2count(f));
	clock(CLOCK);
    };

    void reset() { tcnt0(0); }

    void enable(){ timsk0(timsk0() | OCIE0A); }
    void disable(){ timsk0(timsk0() & ~OCIE0A); }

    Tick read() { return tcnt0(); }

    void handler(Handler * handler) { _handler = handler; }

    static int init();

private:
    static void int_handler(unsigned int irq);

protected:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }

protected:
    static Handler * _handler;

};



class ATMega1281_Timer_3: public Timer_Common, private AVR_Timer
{
public:
    static const unsigned int FREQUENCY = Traits<ATMega1281_Timer_3>::FREQUENCY;

private:
    static const Hertz MACHINE_CLOCK = Traits<Machine>::CLOCK;
    static const unsigned int CLOCK = Traits<Machine>::CLOCK >> 10;

    typedef RTC::Microsecond Microsecond;

public:
    ATMega1281_Timer_3() {}

    ATMega1281_Timer_3(const Hertz & f) {
	frequency(f);
    }

    ATMega1281_Timer_3(const Handler * handler) {
    frequency(FREQUENCY);
    _handler = handler;
    enable();
    }

    const void clock(const Hertz clock) {
        if(clock == MACHINE_CLOCK)
            tccr3b(TIMER_PRESCALE_1|WGMn2);
        else if (clock == (MACHINE_CLOCK >> 3))
            tccr3b(TIMER_PRESCALE_8|WGMn2);
        else if (clock == (MACHINE_CLOCK >> 6))
            tccr3b(TIMER_PRESCALE_64|WGMn2);
        else if (clock == (MACHINE_CLOCK >> 8))
            tccr3b(TIMER_PRESCALE_256|WGMn2);
        else
            tccr3b(TIMER_PRESCALE_1024|WGMn2);
    }

    void enable(){ timsk3(timsk3() | OCIE3A); }
    void disable(){timsk3(timsk3() & ~OCIE3A);}

    Hertz frequency() const { return count2freq(ocr3a());}
    void frequency(const Hertz & f) {
	ocr3a(freq2count(f));
	clock(CLOCK);
    };

    void reset() { tcnt3(0); }
    Tick read() { return tcnt3(); }

    void handler(Handler * handler) { _handler = handler; }

    static int init();

private:
    static void int_handler(unsigned int irq);

protected:
    static Hertz count2freq(const Count & c) { return CLOCK / c; }
    static Count freq2count(const Hertz & f) { return CLOCK / f; }

protected:
    static Handler * _handler;

};

__END_SYS

#endif

