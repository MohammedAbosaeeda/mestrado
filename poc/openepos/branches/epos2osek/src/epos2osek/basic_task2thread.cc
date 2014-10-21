/* Adapter */
#include <epos2osek/basic_task2thread.hh>
#include "app_hooks.h"

__USING_SYS

ApplicationModes BasicTask2Thread::appMode = OSDEFAULTAPPMODE;
BasicTask2Thread ** BasicTask2Thread::__tasks = 0;

void BasicTask2Thread::startOS(ApplicationModes appMode) {
    db<Thread>(TRC) << "BasicTask2Thread::startOS\n";
    BasicTask2Thread::appMode = appMode;
    StartupHook();
    int numberOfTasks = 0;
    __tasks = CreateAllTasks(numberOfTasks);

	db<Thread>(TRC) << "Number of tasks: " << numberOfTasks << "\n";

	for (int i = 0; i < numberOfTasks; ++ i) {
		db<Thread>(TRC) << "task number: " << i << "\n";
	}

	db<Thread>(TRC) << "_thread_count: " << _thread_count << "\n";
	db<Thread>(TRC) << "_scheduler::schedulables: " << _scheduler.schedulables() << "\n";
    db<Thread>(TRC) << "Gostrefrema\n";
    
//
    // task1.activate(true);
	// Reschedule();
	
//
}

StatusType BasicTask2Thread::terminate() {   
    BasicTask2Thread * aTask = (BasicTask2Thread *) running();
    aTask->suspend();
    
    return E_OK;
}

StatusType BasicTask2Thread::activate(TaskType taskID) {
    // Basically restart and resume the selected task
    // Also rescheduling
    db<Thread>(TRC) << "BasicTask2Thread::activate\n";
    db<Thread>(TRC) << "getting task: " << taskID << "\n";
    BasicTask2Thread * aTask = __tasks[taskID];
    db<Thread>(TRC) << "got task: " << aTask->identity() << "\n";

    //~ aTask->state(OSEK_READY);
    aTask->restart();
    aTask->resume(); // But if resume and rescheduling, never will be a return, won't it?
    
    return E_OK; // ?
}

void BasicTask2Thread::__activate() {
    /* resume without rescheduling */
    /* basically "copy paste" from Thread::resume */
    db<Thread>(TRC) << "BasicTask2Thread::__activate\n";

    _state = READY;
	_scheduler.resume(this);
    
}

StatusType BasicTask2Thread::chain(TaskType taskID) {
    db<Thread>(TRC) << "BasicTask2Thread::chain\n";
    db<Thread>(TRC) << "getting task: " << taskID << "\n";
    BasicTask2Thread * aTask = __tasks[taskID];
    db<Thread>(TRC) << "got task: " << aTask->identity() << "\n";

    prevent_scheduling();
    
    aTask->__activate();
    terminate();

    allow_scheduling();
    
    return E_OK; // ?
}

void BasicTask2Thread::restart() {
    db<Thread>(TRC) << "BasicTask2Thread::restart\n";
    prevent_scheduling();
	_context = CPU::init_stack(_stack, STACK_SIZE, &implicit_exit, __entry);
	allow_scheduling();
}

