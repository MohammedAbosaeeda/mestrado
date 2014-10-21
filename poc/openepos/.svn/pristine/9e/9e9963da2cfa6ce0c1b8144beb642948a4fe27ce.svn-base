// EPOS-- Alarm Abstraction Implementation

#include <system/kmalloc.h>
#include <display.h>
#include <alarm.h>
#include <thread.h>

__BEGIN_SYS

// Class attributes
Timer Alarm::_timer;
volatile Alarm::Tick Alarm::_elapsed;
Alarm::Master Alarm::_master;
Alarm::Queue Alarm::_requests;
unsigned int Alarm::_n_quantums = 0;

// Methods
Alarm::Alarm(const Microsecond & time, Handler * handler, int times)
    : _ticks(ticks(time)), _handler(handler), _times(times), 
      _link(this, _ticks)
{
    db<Alarm>(TRC) << "Alarm(t=" << time 
		   << ",ticks=" << _ticks
		   << ",h=" << (void *)handler
		   << ",x=" << times << ") => " << this << "\n";
    if(_ticks) {
	CPU::int_disable();
	_requests.insert(&_link);
	CPU::int_enable();
    } else
	(*handler)();
}

Alarm::Alarm(const Microsecond & init_time, const Microsecond & time, Handler * handler, int times)
    : _ticks(ticks(time)), _handler(handler), _times(times), 
      _link(this, _ticks)
{
    db<Alarm>(TRC) << "Alarm(t=" << time 
		   << ",ticks=" << _ticks
		   << ",h=" << (void *)handler
		   << ",x=" << times << ") => " << this << "\n";

    if(init_time){
        _link.rank(ticks(init_time));
    } else {
	if(_times != INFINITE){
	   _times--;
	}
    }

    if(_ticks) {
	CPU::int_disable();
	_requests.insert(&_link);
	CPU::int_enable();
    } else
	(*handler)();
}



Alarm::Alarm(const Microsecond & time, Handler * handler, int times, 
	     bool int_enable)
    : _ticks(ticks(time)), _handler(handler), _times(times), 
      _link(this, _ticks)
{
    db<Alarm>(TRC) << "Alarm(t=" << time 
		   << ",ticks=" << _ticks
		   << ",h=" << (void *)handler
		   << ",x=" << times << ") => " << this << "\n";
    if(_ticks) {
	CPU::int_disable();
	_requests.insert(&_link);
	if(int_enable)
	    CPU::int_enable();
    } else
	(*handler)();
}

Alarm::~Alarm() {
    db<Alarm>(TRC) << "~Alarm()\n";

    CPU::int_disable();
    _requests.remove(this);
    CPU::int_enable();
}

void Alarm::master(const Microsecond & time, Handler::Function * handler)
{
    db<Alarm>(TRC) << "Alarm::master(t=" << time << ",h="
		   << (void *)handler << ")\n";

    CPU::int_disable();
    _master = Master(time, handler);
    CPU::int_enable();
}

void Alarm::delay(const Microsecond & time)
{
    db<Alarm>(TRC) << "Alarm::delay(t=" << time << ")\n";
    if(time > 0) 
        if(__SYS(Traits)<Thread>::idle_waiting) {
	    CPU::int_disable();
	    Handler_Thread handler(Thread::self());
	    Alarm alarm(time, &handler, 1, false);
	    Thread::self()->suspend();
	    CPU::int_enable();
        } else {
	    Tick t = _elapsed + (time + period() / 2) / period();
	    while(_elapsed < t)
		Thread::yield();
        }
}

void Alarm::int_handler(unsigned int)
{
    // This is a very special interrupt handler, for the master alarm handler
    // called at the end might trigger a context switch (e.g. when it is set
    // to call the thread scheduler). In this case, int_handler won't finish
    // within the expected time (i.e., it will finish only when the preempted
    // thread return to the CPU). For this NOT to be an issue, the following
    // conditions MUST be met:
    // 1 - The interrupt dispatcher must acknowledge the handling of interrupts
    //     before invoking the respective handler, thus enabling subsequent
    //     interrupts to be handled even if a previous didn't come to an end
    // 2 - Handlers (e.g. master) must be called after incrementing _elapsed
    // 3 - The manipulation of alarm queue must be guarded (e.g. int_disable)

    CPU::int_disable();

    Handler * handler = 0;

    _elapsed++;
    
    if(visible) {
	Display display;
	int lin, col;
	display.position(&lin, &col);
	display.position(0, 79);
	display.putc(_elapsed);
	display.position(lin, col);
    }

    if(!_requests.empty()) {
	// rank can be negative whenever multiple handlers get created for the
	// same time tick
	if (!_requests.empty() && _requests.head()->promote() <= 0) {
	    do {
	    	Queue::Element * e = _requests.remove();
	    	Alarm * alarm = e->object();

	    	if(alarm->_times != INFINITE)
			alarm->_times--;
	    	if(alarm->_times) {
			e->rank(alarm->_ticks);
			_requests.insert(e);
	    	}

	    	handler = alarm->_handler;
	    	db<Alarm>(TRC) << "Alarm::handler(h=" << alarm->_handler << ")\n";
		//TODO: separate handlers. It can loop over thread handlers, but not over function handlers
	    	(*handler)(); //this call does not causes reschedule -- thread will not run yet -- (polymorphic handler - problems to AVR)
	   } while (!_requests.empty() && _requests.head()->rank() <= 0);
	}
    }

    CPU::int_enable();

    _master();	// thread will be reescheduled
}

__END_SYS
