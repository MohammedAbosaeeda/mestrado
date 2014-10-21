#include <osek_task.h>
#include <osek_defs.h>


extern "C" void StartupHook();
extern "C" void PreTaskHook();


__USING_SYS

__BEGIN_SYS

void OSEK_Task::activate(bool reschedule) {
	db<OSEK_Task>(TRC) << "OSEK_Task::activate-begin (this: " << this << ")\n";
	db<OSEK_Task>(TRC) << "OSEK_Task::activate, reschedule is: " << reschedule << "\n";

	numRecActs++; // record activation requests

	if(numRecActs == 1) { // if not activated
		db<OSEK_Task>(TRC) << "OSEK_Task::activate, will call resume (this: " << this << ")\n";

		taskState = READY;
		osek_resume(); // put t into READY queue without implicit rescheduling
	}

	if(reschedule) {
		db<OSEK_Task>(TRC) << "OSEK_Task::activate, will call osek_reschedule (this: " << this << ")\n";

		OSEK_Task::osek_reschedule(true);
	}

	db<OSEK_Task>(TRC) << "OSEK_Task::activate-end (this: " << this << ")\n";
}


void OSEK_Task::terminate() {
	db<OSEK_Task>(TRC) << "OSEK_Task::terminate-begin\n";

    // fetch pointer to running task
	OSEK_Task * me = static_cast<OSEK_Task*>(OSEK_Task::self());

	db<OSEK_Task>(TRC) << "OSEK_Task::terminate, me (running) is: " << me << ")\n";

    me->mustReset = true;
    me->numRecActs--;

    if(me->numRecActs==0) { 
    	db<OSEK_Task>(TRC) << "OSEK_Task::terminate, will call osek_suspend\n";

		me->taskState = SUSPENDED;
        me->osek_suspend();
    }

    db<OSEK_Task>(TRC) << "OSEK_Task::terminate, will call osek_reschedule\n";

    OSEK_Task::osek_reschedule(true, me);

    db<OSEK_Task>(TRC) << "OSEK_Task::terminate-end\n";
}


void OSEK_Task::chain() {
	db<OSEK_Task>(TRC) << "OSEK_Task::chain-begin (this: " << this << ")\n";


	db<OSEK_Task>(TRC) << "OSEK_Task::chain, will call activate (this: " << this << ")\n";
	// set task into READY queue without 
	// rescheduling (or record multiple activation request)
	activate(false);

	db<OSEK_Task>(TRC) << "OSEK_Task::chain, will call terminate (this: " << this << ")\n";
	terminate(); // terminate running (and btw calling) task 

	db<OSEK_Task>(TRC) << "OSEK_Task::chain-end (this: " << this << ")\n";
}


void OSEK_Task::schedule() {
	db<OSEK_Task>(TRC) << "OSEK_Task::schedule-begin\n";

	OSEK_Task::osek_reschedule(true);

	db<OSEK_Task>(TRC) << "OSEK_Task::schedule-end\n";
}


void OSEK_Task::getTaskID(TaskRefType taskRef) {
	db<OSEK_Task>(TRC) << "OSEK_Task::getTaskID\n";

	OSEK_Task * m = static_cast<OSEK_Task*>(OSEK_Task::self());
	*taskRef = m->id;
}


void OSEK_Task::getTaskState(TaskType taskID, TaskStateRefType state) {
	db<OSEK_Task>(TRC) << "OSEK_Task::getTaskState\n";

	state = &(tasks[taskID]->taskState);
}


void OSEK_Task::enableAllInterrupts() {
	//db<OSEK_Task>(TRC) << "OSEK_Task::enableAllInterrupts\n";

	CPU::int_enable();
}


void OSEK_Task::disableAllInterrupts() {
	//db<OSEK_Task>(TRC) << "OSEK_Task::disableAllInterrupts\n";

	CPU::int_disable();
}


void OSEK_Task::resumeAllInterrupts() {
	db<OSEK_Task>(TRC) << "OSEK_Task::resumeAllInterrupts\n";

	enableAllInterrupts();
}


void OSEK_Task::suspendAllInterrupts() {
	db<OSEK_Task>(TRC) << "OSEK_Task::suspendAllInterrupts\n";

	disableAllInterrupts();
}


void OSEK_Task::resumeOSInterrupts() {
	db<OSEK_Task>(TRC) << "OSEK_Task::resumeOSInterrupts\n";

	enableAllInterrupts();
}


void OSEK_Task::suspendOSInterrupts() {
	db<OSEK_Task>(TRC) << "OSEK_Task::suspendOSInterrupts\n";

	disableAllInterrupts();
}


void OSEK_Task::getResource(ResourceType resource) {
	db<OSEK_Task>(TRC) << "OSEK_Task::getResource\n";

	OSEK_Task * m = static_cast<OSEK_Task*>(OSEK_Task::self()); 
	int resPriority = resourcePriority[resource][0];
	resourcePriority[resource][1] = m->priority();
	if(resPriority < m->priority()) {
		m->priority(resPriority); // set ceiling priority
	}
}


void OSEK_Task::releaseResource(ResourceType resource) {
	db<OSEK_Task>(TRC) << "OSEK_Task::releaseResource\n";

	OSEK_Task * m = static_cast<OSEK_Task*>(OSEK_Task::self());
	m->priority(resourcePriority[resource][1]); // set priority to task priority
	OSEK_Task::osek_reschedule(true); // reschedule after releasing resource
}


StatusType OSEK_Task::setEvent(TaskType id, EventMaskType mask) {
	db<OSEK_Task>(TRC) << "OSEK_Task::setEvent\n";

	OSEK_Task* t = tasks[id];
	t->eventsSet |= mask;
	if(t->isWaiting && (t->eventsSet & t->eventsWaitingFor)) {
		t->isWaiting = false;
		t->taskState = READY;
		t->osek_resume();
		OSEK_Task::osek_reschedule(true);
		/* t->osek_reschedule(); */
	}
	return E_OK;
}


StatusType OSEK_Task::clearEvent(EventMaskType mask) {
	db<OSEK_Task>(TRC) << "OSEK_Task::clearEvent\n";

	OSEK_Task * m = static_cast<OSEK_Task*>(OSEK_Task::self());
	m->eventsSet &= ~mask;
	return E_OK;
}


StatusType OSEK_Task::getEvent(TaskType id, EventMaskType *mask) {
	db<OSEK_Task>(TRC) << "OSEK_Task::getEvent\n";

	*mask = tasks[id]->eventsSet;
	return E_OK;
}


StatusType OSEK_Task::waitEvent(EventMaskType mask) {
	db<OSEK_Task>(TRC) << "OSEK_Task::waitEvent\n";

	OSEK_Task * m = static_cast<OSEK_Task*>(OSEK_Task::self());
	if(m->eventsSet & mask) {
		return E_OK;
	}
	m->eventsWaitingFor = mask;
	m->isWaiting = true;
	m->taskState = WAITING;
	m->osek_wait();
	/* m->osek_reschedule(); */
	OSEK_Task::osek_reschedule(true);
	return E_OK;
}


void OSEK_Task::getAlarmBase(AlarmType alarmID, AlarmBaseRefType info) {
	db<OSEK_Task>(TRC) << "OSEK_Task::getAlarmBase\n";

	info->maxallowedvalue = 1;
	info->ticksperbase = 1;
	info->mincycle = 0;
}


void OSEK_Task::getAlarm(AlarmType alarmId, TickRefType tick) {
	db<OSEK_Task>(TRC) << "OSEK_Task::getAlarm\n";

	alarms[alarmId]->osek_getTicks(tick);
}


AppModeType OSEK_Task::getActiveApplicationMode() {
	db<OSEK_Task>(TRC) << "OSEK_Task::getActiveApplicationMode\n";

	return appMode;
}


void OSEK_Task::shutdownOS(StatusType error) {
	db<OSEK_Task>(TRC) << "OSEK_Task::shutdownOS\n";

	CPU::int_disable();
	CPU::halt();
}

void OSEK_Task::startOS(AppModeType mode) {
	db<OSEK_Task>(TRC) << "OSEK_Task::startOS, mode: " << mode << "\n";

	OSEK_Task::appMode = mode;
	StartupHook();
}

AppModeType OSEK_Task::appMode = 0;


void OSEK_Task::osek_reset() {
	lock();

	db<OSEK_Task>(TRC) << "OSEK_Task::osek_reset\n";

	_context = CPU::init_stack(_stack, _stack_size, &implicit_exit, _entry);

	unlock();
}


void OSEK_Task::osek_suspend()
{
	lock();
	// kout << "\nSUSP\n"; /* TRACE */
	db<OSEK_Task>(TRC) << "OSEK_Task::suspend(this=" << this << ")\n";

    _scheduler.suspend(this);
	_state = SUSPENDED;

	 unlock();
}

void OSEK_Task::osek_resume()
{
	lock();

	db<OSEK_Task>(TRC) << "OSEK_Task::resume(this=" << this << ")\n";

	if(_state == SUSPENDED) {
		_state = READY;
		_scheduler.resume(this);

		// kout << "...resumed(" << this->__name << ")\n"; /* TRACE */
	}
	else {
		db<OSEK_Task>(WRN) << "Resume called for unsuspended object!\n";
	}

	unlock();
}


void OSEK_Task::osek_reschedule(bool preempt, OSEK_Task * prev) {
	db<OSEK_Task>(TRC) << "OSEK_Task::osek_reschedule v2, running task is:" << static_cast<OSEK_Task*>(running()) << "\n";


	// just debug - begin
	if (running()->_state == SUSPENDED) {
		db<OSEK_Task>(TRC) << "TRC at OSEK_Task::osek_reschedule: running state cannot be SUSPENDED!\n";
		db<OSEK_Task>(ERR) << "Error at OSEK_Task::osek_reschedule: running state cannot be SUSPENDED!\n";
	}
	// just debug - end

	if(preempt) {
		Thread * next = _scheduler.choose();

		OSEK_Task * old = prev;
		OSEK_Task * first = static_cast<OSEK_Task*>(next);

		if((old != first) && (first->mustReset)) {
			first->osek_reset();
			first->mustReset = false;
		}

		osek_dispatch(old, first);
	}
	else if(static_cast<OSEK_Task*>(running())->mustReset) {
	    	static_cast<OSEK_Task*>(running())->mustReset = false;
	        PreTaskHook();

	        db<OSEK_Task>(TRC) << "TaskOSEK::osek_reschedule, calling CPU::selfrestart\n";
#if 0
			CPU::selfrestart(running()->_stack, static_cast<OSEK_Task*>(running())->_stack_size, static_cast<OSEK_Task*>(running())->_entry);
#endif
	}
	else {
		unlock();
	}

}


void OSEK_Task::osek_reschedule(bool preempt) {
	db<OSEK_Task>(TRC) << "OSEK_Task::osek_reschedule, running task is:" << static_cast<OSEK_Task*>(running()) << "\n";


	// just debug - begin
	if (running()->_state == SUSPENDED) {
		db<OSEK_Task>(TRC) << "TRC at OSEK_Task::osek_reschedule: running state cannot be SUSPENDED!\n";
		db<OSEK_Task>(ERR) << "Error at OSEK_Task::osek_reschedule: running state cannot be SUSPENDED!\n";
	}
	// just debug - end

	if(preempt) {
		Thread * prev = running();
		Thread * next = _scheduler.choose();

		OSEK_Task * old = static_cast<OSEK_Task*>(prev);
		OSEK_Task * first = static_cast<OSEK_Task*>(next);

		if((old != first) && (first->mustReset)) {
			first->osek_reset();
			first->mustReset = false;
		}

		osek_dispatch(old, first);
	}
	else if(static_cast<OSEK_Task*>(running())->mustReset) {
	    	static_cast<OSEK_Task*>(running())->mustReset = false;
	        PreTaskHook();

	        db<OSEK_Task>(TRC) << "TaskOSEK::osek_reschedule, calling CPU::selfrestart\n";
#if 0
			CPU::selfrestart(running()->_stack, static_cast<OSEK_Task*>(running())->_stack_size, static_cast<OSEK_Task*>(running())->_entry);
#endif
	}
	else {
		unlock();
	}

}

void OSEK_Task::osek_dispatch(OSEK_Task * prev, OSEK_Task * next)
{
    // scheduling must be disabled at this point!
	db<OSEK_Task>(TRC) << "OSEK_Task::osek_dispatch\n";

	if(prev != next) {
		if(prev->_state == RUNNING) {
			prev->_state = READY;
			(static_cast<OSEK_Task*>(running()))->taskState = READY;
		}

		next->_state = RUNNING;
		//~ (static_cast<OSEK_Task*>(running()))->taskState = RUNNING; /* Deveria ser para READY, não? xxx */

		db<OSEK_Task>(TRC) << "OSEK_Task::dispatch(prev=" << prev << ",next=" << next << ")\n";

		PreTaskHook();

		if(smp) { _lock.release(); }

		CPU::switch_context(&prev->_context, next->_context);

	}
	else if(smp) { _lock.release(); }
	CPU::int_enable();
}

void OSEK_Task::osek_wait() {
	lock();
	db<OSEK_Task>(TRC) << "OSEK_Task::wait\n";

	_state = SUSPENDED;

	unlock();
}


#if 0
bool OSEK_Task::osek_idleRunning() {
	return (running() == getIdle());
}
#endif


#if 0
void OSEK_Task::osek_printQueue(Queue q) {
	Queue::Element* e;

	for (e = q.head(); e; e = e->next()) {
		db<OSEK_Task>(WRN) << e->object() << " ";
	}

	db<OSEK_Task>(WRN) << "\n";
}
#endif


void OSEK_Task::implicit_exit()
{
	db<OSEK_Task>(TRC) << "OSEK_Task::implicit_exit\n";
	exit(CPU::fr());
}


void OSEK_Task::exit(int status)
{
	lock();

    db<OSEK_Task>(TRC) << "OSEK_Task::exit(running=" << running()
			    <<",status=" << status << ")\n";

    Thread * runn = running();
    _scheduler.remove(runn);
	*static_cast<int *>(runn->_stack) = status;
	runn->_state = FINISHING;

	_thread_count --;

	if(runn->_joining) {
		runn->_joining->_state = READY;
		OSEK_Task * tmp = static_cast<OSEK_Task*>(runn->_joining);
		tmp->osek_resume();
		runn->_joining = 0;
	}

	OSEK_Task * r = static_cast<OSEK_Task*>(runn);
	OSEK_Task * n = static_cast<OSEK_Task*>(_scheduler.choose());

	osek_dispatch(r, n);

}


__END_SYS

