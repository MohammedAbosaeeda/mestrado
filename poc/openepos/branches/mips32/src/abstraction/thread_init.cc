// EPOS-- Thread Abstraction Initialization

#include <system/kmalloc.h>
#include <thread.h>
#include <alarm.h>

__BEGIN_SYS

int Thread::init(System_Info * si)
{
    db<Init, Thread>(TRC) << "Thread::init(entry="
			  << (void *)si->lmm.app_entry << ")\n";

    prevent_scheduling();

    if(active_scheduler)
		Alarm::master(QUANTUM, &reschedule_interrupt);

	db<Init, Thread>(TRC) << "Criando Thread APP\n";
    _running = 	new(malloc(sizeof(Thread)))
		Thread(reinterpret_cast<int (*)()>(si->lmm.app_entry), RUNNING);
	db<Init, Thread>(TRC) << "Criando Idle\n";
	_idle = new(kmalloc(sizeof(Thread))) Thread(&idle, READY, IDLE);

	IC::enable();
	//allow_scheduling();
	db<Init, Thread>(TRC) << "Carregando contexto\n";
	db<Init, Thread>(TRC) << "Context address: " << (void *) &_running->_context << "\n";
	db<Init, Thread>(TRC) << "Context address2: " << (void *) _running->_context << "\n";
	_running->_context->load(&_running->_context);
	

    allow_scheduling();

    return 0;
}

__END_SYS
