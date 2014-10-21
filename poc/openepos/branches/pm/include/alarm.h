// EPOS-- Alarm Abstraction Declarations

#ifndef __alarm_h
#define __alarm_h

#include <utility/queue.h>
#include <utility/handler.h>
#include <tsc.h>
#include <rtc.h>
#include <timer.h>

__BEGIN_SYS

class Alarm
{
protected:
    typedef TSC::Time_Stamp Time_Stamp;
    typedef Timer::Tick Tick;

    typedef Relative_Queue<Alarm, int, Traits<Thread>::smp> Queue;

    static const int FREQUENCY = Traits<Timer>::FREQUENCY;

    static const bool visible = Traits<Alarm>::visible;

public:
    typedef TSC::Hertz Hertz;
    typedef RTC::Microsecond Microsecond;

    // Infinite times (for alarms)
    enum { INFINITE = -1 };

    class Master
    {
    public:
	Master() {}
	Master(const Microsecond & time, Handler::Function * handler)
	    : _ticks(ticks(time)), _to_go(_ticks), _handler(handler) {}

	void operator()() { 
	    if(_ticks && (_to_go-- <= 0)) {
		_to_go = _ticks;
		//Contador de quantums
		if(Traits<Battery_Lifetime>::enabled){
			Alarm::_n_quantums++;
		}
		_handler(); 
	    }
	}

	int reset() {
	    Tick percentage = _to_go * 100 / _ticks;
	    _to_go = _ticks; 
	    return percentage;
	}

    private:
	Tick _ticks;
	volatile Tick _to_go;
	Handler::Function * _handler;
    };

public:
    Alarm(const Microsecond & time, Handler * handler, int times = 1);

    Alarm(const Microsecond & init_time, const Microsecond & time, Handler * handler, int times = 1);

    ~Alarm();

    static Hertz frequency() {return _timer.frequency(); }
    static void delay(const Microsecond & time);

    static void master(const Microsecond & time, Handler::Function * handler);
    static int reset_master() { return _master.reset(); }

    static unsigned int reset_n_quantums() {
        unsigned int last_n_quantums = _n_quantums;
	_n_quantums = 0;
	return (last_n_quantums);
    }

    static void int_handler(unsigned int irq);

    static int init();

    static Microsecond  time(Tick tics) { return (period() * ( 2*tics -1)) / 2;}  // derived from equation time=tics.period - period/2

    static volatile Tick elapsed() {return _elapsed;}

    static Tick ticks(const Microsecond & time) {
	return (time + period() / 2) / period();
    }


private:
    Alarm(const Microsecond & time, Handler * handler, int times,
          bool int_enable);

    static Microsecond period() { 
	return 1000000 / frequency();
    }


private:
    Tick _ticks;
    Handler * _handler;
    int _times;
    Queue::Element _link;

    static Timer _timer;
    static volatile Tick _elapsed;
    static Master _master;
    static Queue _requests;
    static unsigned int _n_quantums;
};

__END_SYS

#endif
