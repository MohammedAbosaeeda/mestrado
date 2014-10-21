#include <osek_task.h>
#include <thread.h>

#include <utility/ostream.h> /* Just for DMEC App. */

__USING_SYS

OStream cout; /* Just for DMEC App. */

extern "C" int func_keso_epos_d_dom1_t_task1(void);
OSEK_Task* task_keso_epos_d_dom1_t_task1;

OSEK_Task* OSEK_Task::tasks[1] = {
	task_keso_epos_d_dom1_t_task1
};


PriorityType OSEK_Task::resourcePriority[][2] = {
	-1, 0
};


AlarmOSEK* OSEK_Task::alarms[0] = {};
TickType OSEK_Task::alarmCycle[0] = {};




int main() {
    // cout << "\nMAIN -- os.cc\n";
    task_keso_epos_d_dom1_t_task1 = new OSEK_Task(func_keso_epos_d_dom1_t_task1, 0, Thread::SUSPENDED, Scheduling_Criteria::OSEK_Priority::HIGH);

    // cout << "...startOS BEGIN\n";
	OSEK_Task::startOS(OSDEFAULTAPPMODE);
    // cout << "...startOS END\n";

    // cout << "...activate BEGIN\n";
	task_keso_epos_d_dom1_t_task1->activate(false);
    // cout << "...activate END\n";

    // cout << "...osek_reschedule BEGIN\n";
    OSEK_Task::osek_reschedule(true);
    // cout << "...osek_reschedule END\n";

    while(true)
    {
        Thread::yield();       
    }
}



StatusType OSEK_Task::setRelAlarm(AlarmType alarmId,
                                  TickType increment,
                                  TickType cycle) {

	Function_Handler* h;
	switch(alarmId) {
	}
}


// extern "C" StatusType extC_activate(TaskType task);
extern "C" void extC_activate(TaskType task);
extern "C" void extC_chain(TaskType task);
extern "C" void extC_terminate();
extern "C" void extC_schedule();
extern "C" void extC_getTaskID(TaskRefType taskRef);
extern "C" void extC_getTaskState(TaskType taskID, TaskStateRefType state);
extern "C" void extC_enableAllInterrupts();
extern "C" void extC_disableAllInterrupts();
extern "C" void extC_resumeAllInterrupts();
extern "C" void extC_suspendAllInterrupts();
extern "C" void extC_resumeOSInterrupts();
extern "C" void extC_suspendOSInterrupts();
extern "C" void extC_getResource(ResourceType resource);
extern "C" void extC_releaseResource(ResourceType resource);
extern "C" StatusType extC_setEvent(TaskType TaskId, EventMaskType mask);
extern "C" StatusType extC_getEvent(TaskType TaskId, EventMaskRefType maskRef);
extern "C" StatusType extC_clearEvent(EventMaskType mask);
extern "C" StatusType extC_waitEvent(EventMaskType mask);
extern "C" void extC_getAlarmBase(AlarmType alarmId, AlarmBaseRefType info);
extern "C" void extC_getAlarm(AlarmType alarmId, TickRefType tick);
extern "C" StatusType extC_setRelAlarm(AlarmType alarmId, TickType increment, TickType cycle);
extern "C" StatusType extC_setAbsAlarm(AlarmType alarmId, TickType start, TickType cycle);
extern "C" StatusType extC_cancelAlarm(AlarmType alarmId);
extern "C" void extC_startOS(AppModeType mode);
extern "C" void extC_shutdownOS(StatusType error);
extern "C" void extC_setAppMode(AppModeType mode);

void extC_activate(TaskType task) {
	OSEK_Task::tasks[task]->activate(true);
}

void extC_chain(TaskType task) {
	OSEK_Task::tasks[task]->chain();
}

void extC_terminate() {
	OSEK_Task::terminate();
}

void extC_schedule() {
	OSEK_Task::schedule();
}

void extC_getTaskID(TaskRefType taskRef) {
	OSEK_Task::getTaskID(taskRef);
}

void extC_getTaskState(TaskType taskID, TaskStateRefType state) { 
	OSEK_Task::getTaskState(taskID, state);
}

void extC_enableAllInterrupts() {
	OSEK_Task::enableAllInterrupts();
}

void extC_disableAllInterrupts() {
	OSEK_Task::disableAllInterrupts();
}

void extC_resumeAllInterrupts() {
	OSEK_Task::resumeAllInterrupts();
}

void extC_suspendAllInterrupts() {
	OSEK_Task::suspendAllInterrupts();
}

void extC_resumeOSInterrupts() {
	OSEK_Task::resumeOSInterrupts();
}

void extC_suspendOSInterrupts() {
	OSEK_Task::suspendOSInterrupts();
}

void extC_getResource(ResourceType resource) { 
	OSEK_Task::getResource(resource);
}

void extC_releaseResource(ResourceType resource) { 
	OSEK_Task::releaseResource(resource);
}

StatusType extC_setEvent(TaskType TaskId, EventMaskType mask) { 
	return OSEK_Task::setEvent(TaskId, mask);
}

StatusType extC_getEvent(TaskType TaskId, EventMaskRefType maskRef) { 
	return OSEK_Task::getEvent(TaskId, maskRef);
}

StatusType extC_clearEvent(EventMaskType mask) { 
	return OSEK_Task::clearEvent(mask);
}

StatusType extC_waitEvent(EventMaskType mask) { 
	return OSEK_Task::waitEvent(mask);
}

void extC_getAlarmBase(AlarmType alarmId, AlarmBaseRefType info) { 
	return OSEK_Task::getAlarmBase(alarmId, info);
}

void extC_getAlarm(AlarmType alarmId, TickRefType tick) { 
	return OSEK_Task::getAlarm(alarmId, tick);
}

StatusType extC_setRelAlarm(AlarmType alarmId,
                           TickType increment, 
                           TickType cycle) { 
	return OSEK_Task::setRelAlarm(alarmId, increment, cycle);
}

void extC_startOS(AppModeType mode) { 
	OSEK_Task::startOS(mode);
}

void extC_shutdownOS(StatusType error) { 
	OSEK_Task::shutdownOS(error);
}

void extC_setAppMode(AppModeType mode) { 
	OSEK_Task::setAppMode(mode);
}


extern "C" int write(int fd, const char *buf, int count);
int write(int fd, const char *buf, int count) {
	OStream cout; cout << buf;
}


extern "C" int print(const char *buf);
int print(const char *buf) {
	OStream cout; cout << buf;
}
