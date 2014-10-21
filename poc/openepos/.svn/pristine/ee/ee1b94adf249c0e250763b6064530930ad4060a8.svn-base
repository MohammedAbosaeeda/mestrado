// EPOS Semaphore Abstraction Declarations

#ifndef __semaphore_h
#define __semaphore_h

#include <utility/handler.h>
#include <synchronizer.h>

__BEGIN_SYS

class Semaphore: protected Synchronizer_Common
{
public:
    Semaphore(int v = 1) : _value(v) {
	db<Synchronizer>(TRC) << "Semaphore(value=" << _value << ") => "
			      << this << "\n";
    }

    ~Semaphore() {
	db<Synchronizer>(TRC) << "~Semaphore(this=" << this << ")\n";
    }

    void p() { 
	db<Synchronizer>(TRC) << "Semaphore::p(this=" << this 
			      << ",value=" << _value << ")\n";

	begin_atomic();
	if(fdec(_value) < 1)
	    sleep(); // implicit end_atomic()
	else
	    end_atomic();
    }

    void v() {
	db<Synchronizer>(TRC) << "Semaphore::v(this=" << this
			      << ",value=" << _value << ")\n";

	begin_atomic();
	if(finc(_value) < 0)
	    wakeup();  // implicit end_atomic()
	else
	    end_atomic();
    }

private:
    volatile int _value;
};


// An event handler that triggers a semaphore (see handler.h)
class Semaphore_Handler: public Handler
{
public:
    Semaphore_Handler(Semaphore * h) : _handler(h) {}
    ~Semaphore_Handler() {}

    void operator()() { _handler->v(); }
	
private:
    Semaphore * _handler;
};

//Semaphore whose p() return the value passed to v()
template<typename T, int BUFFER_SIZE>
class Semaphore_Channel: private Semaphore
{
public:
    Semaphore_Channel() : Semaphore(0), _begin(0), _end(0) {

    }

    ~Semaphore_Channel() {

    }

    T* p() {
        Semaphore::p();
        return _buffer[turn_around(_begin)];
    }

    void v(T *value) {
        _buffer[turn_around(_end)] = value;
        if(_end == _begin) db<Synchronizer>(WRN) << "Semaphore_Channel::v(this=" << this << ", value=" << value << ") Buffer overflow\n";
        Semaphore::v();
    }

private:
    int _begin;
    int _end;

    int turn_around(int &val){
        int old = val;
        val = (val + 1) % BUFFER_SIZE;
        return old;
    }

    T*  _buffer[BUFFER_SIZE];
};

__END_SYS

#endif
