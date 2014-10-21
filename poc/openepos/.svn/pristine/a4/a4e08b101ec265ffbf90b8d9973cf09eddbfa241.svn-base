// EPOS-- Thread Abstraction Implementation

#include <thread.h>
#include <mmu.h>

__BEGIN_SYS

// Class attributes

Thread * volatile Thread::_running;
Thread * Thread::_idle;
Thread::Queue Thread::_ready;
Thread::Queue Thread::_suspended;

// Methods

int Thread::join()
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::join(this=" << this
		    << ",state=" << _state << ")\n";

    if(_state != FINISHING) {
		_joining = _running;
		_joining->suspend(); // implicitly allows scheduling
    }

    allow_scheduling();

    return *static_cast<int *>(_stack);
}

void Thread::pass()
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::pass(this=" << this << ")\n";

    if(_ready.remove(this)) { // this is ready to receive the CPU
	_running->_state = READY;
	_ready.insert(&_running->_link);
	switch_to(this);
    } else 
	db<Thread>(WRN) << "Thread::pass => thread (" << this 
			<< ") not ready\n";

    allow_scheduling();
}

void Thread::suspend()
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::suspend(this=" << this << ")\n";
	
    _state = SUSPENDED;
    _suspended.insert(&_link);

	//db<Thread>(TRC) << "Thread::suspend(_ready.size()=" << _ready.size() << ")\n";
    if(this == _running)
		switch_to(_ready.remove()->object());
    else
		_ready.remove(this);
    
    allow_scheduling();
}	    

void Thread::resume(bool interrupt)
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::resume(this=" << this << ")\n";

    if(_state != SUSPENDED) {
		allow_scheduling();
		return;
    }

    if(_suspended.remove(this)) {
		_state = READY;
		_ready.insert(&_link);
    } else // the thread has terminated while suspended (e.g. by delete)
		db<Thread>(WRN) << "Thread::resume called with defunct thread!\n";
    
    allow_scheduling();

    if(preemptive)
	{
		if(interrupt)
			reschedule(true);
		else
			reschedule();
	}
}


// Class methods

void Thread::yield()
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::yield(running=" << _running << ")\n";

    if(!_ready.empty()) {
	_running->_state = READY;
	_ready.insert(&_running->_link);
	switch_to(_ready.remove()->object());
    }

    allow_scheduling();
}

void Thread::exit(int status)
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::exit(running=" << _running 
		    <<",status=" << status << ")\n";

    *static_cast<int *>(_running->_stack) = status;
    _running->_state = FINISHING;
    if(_running->_joining) {
		Thread * tmp = _running->_joining;
		_running->_joining = 0;
		tmp->resume(); // implicitly allows scheduling
		prevent_scheduling();
    }

    switch_to(_ready.remove()->object());

    allow_scheduling();
}

void Thread::sleep(Queue * q)
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::sleep(running=" << _running
		    << ",q=" << q << ")\n";

    _running->_state = WAITING;
    q->insert(&_running->_link);
    _running->_waiting = q;

    switch_to(_ready.remove()->object());
    
    allow_scheduling();
}

void Thread::wakeup(Queue * q) 
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::wakeup(running=" << _running
		    << ",q=" << q << ")\n";

    if(!q->empty()) {
	Thread * t = q->remove()->object();
	t->_state = READY;
	t->_waiting = 0;
	_ready.insert(&t->_link);
    }

    allow_scheduling();

    if(preemptive)
	reschedule();
}

void Thread::wakeup_all(Queue * q) 
{
    prevent_scheduling();

    db<Thread>(TRC) << "Thread::wakeup_all(running=" << _running
		    << ",q=" << q << ")\n";

    while(!q->empty()) {
	Thread * t = q->remove()->object();
	t->_state = READY;
	t->_waiting = 0;
	_ready.insert(&t->_link);
    }

    allow_scheduling();

    if(preemptive)
	reschedule();
}

void Thread::reschedule(bool interrupt)
{
    prevent_scheduling(); 
	db<Thread>(TRC) << "----> Reeschedule" << "\n";

    Queue::Element * e = _ready.head();
    if(e && e->rank() <= _running->_link.rank()) {
		//db<Thread>(TRC) << "----> Reeschedule SIM vou trocar!" << "\n";
		//db<Thread>(TRC) << "----> Trocando: ready size =  " << _ready.size() << "\n";
		//db<Thread>(TRC) << "----> Trocando: suspe size =  " << _suspended.size() << "\n";
		_running->_state = READY;
		_ready.insert(&_running->_link);
		switch_to(_ready.remove()->object(), interrupt);
    }
	//db<Thread>(TRC) << "----> Reeschedule NAO vou trocar!" << "\n";
}

void Thread::reschedule_interrupt()
{
	//db<Thread>(TRC) << "----> Reeschedule INTERRUPT!" << "\n";
	reschedule(true);
}


void Thread::implicit_exit() 
{
	exit(CPU::fr()); 
}

void Thread::switch_to(Thread * n, bool interrupt) 
{
    // scheduling must be disabled at this point!

    Thread * o = _running;
    db<Thread>(TRC) << "Thread::switch_to(o=" << o << ",n=" << n << ")\n";

//     o->_context->save(); // can be used to force an update
     //db<Thread>(INF) << "old={" << o << "," << *o->_context << "}\n";
     //db<Thread>(INF) << "new={" << n << "," << *n->_context << "}\n";

    n->_state = RUNNING;
    _running = n;

    if(n == _idle)
		db<Thread>(TRC) << "Thread::idle()\n";

	//db<Thread>(TRC) << "Thread::switching\n";
	/*db<Thread>(TRC) << "Thread:: _running = " << _running << " o=" << o <<"\n";
	db<Thread>(TRC) << "Thread:: o->_context = " << o->_context <<"\n";
	db<Thread>(TRC) << "Thread:: &o->_context = " << &o->_context <<"\n";
	db<Thread>(TRC) << "Thread::switch_to(oo=" << oo << ",nn=" << nn << ")\n";
	db<Thread>(TRC) << "Thread::switching(o=" << o->_context << ",n=" << n->_context << ")\n";
	for(;;);*/
	if (interrupt)
	{
		db<Thread>(TRC) << "Thread::loading!" << "\n";
		_running->_context->load(&_running->_context);
	}
	else
	{
		CPU::switch_context(o->_context, n->_context);
	}
    allow_scheduling();
}

int Thread::idle()
{
	while(true);// db<Thread>(WRN) << "I";
    /*while(true) {
		//while(true) db<Thread>(WRN) << "I";
		//CPU::halt();
		if(!_ready.empty())
			yield();
		else
			if(_suspended.empty()) { 
				db<Thread>(WRN) << "The last thread has exited!\n";
				db<Thread>(WRN) << "Halting the CPU ...\n";
				CPU::int_disable();
			}
	}*/
    return 0;
}

__END_SYS
