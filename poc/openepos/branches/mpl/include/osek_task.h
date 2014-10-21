#ifndef __osek_task_h
#define __osek_task_h

#include <thread.h>
#include <osek_defs.h>
#include <alarm_osek.h>


__BEGIN_SYS

class OSEK_Task: public Thread {

private:
    short numRecActs;

public:
    EventMaskType eventsWaitingFor;
    EventMaskType eventsSet;

    bool isWaiting;    
    static OSEK_Task * tasks[];
    static AlarmOSEK * alarms[];
    static TickType alarmCycle[];
    TaskStateType taskState;

private:
    Queue * _waiting;
    OSEK_Task * volatile _joining;

private:
    TaskType id;
    static AppModeType appMode;
    static PriorityType resourcePriority[][2];

    bool mustReset;
    int (* _entry)();
    unsigned int _stack_size;

public:
    OSEK_Task(int (* entry)(),
    		const TaskType taskID,
    		const State & state,
    		const Criterion & criterion,
    		unsigned int stack_size = STACK_SIZE
    		) :
    			Thread(entry, state, criterion, stack_size),
				eventsWaitingFor(0),
                eventsSet(0),
                isWaiting(false),
				id(taskID)
    {
    	// kout << "\nosek\n";
    	db<OSEK_Task>(TRC) << "OSEK_Task::OSEK_Task\n";
    	db<OSEK_Task>(TRC) << "OSEK_Task(entry=" << (void *)entry << "}) => "
    			<< this << "\n";

        mustReset = false;
        numRecActs = 0;
        _entry = entry;
        _stack_size = stack_size;
    }

    static void setAppMode(AppModeType mode) { appMode = mode; }
    
    void activate(bool reschedule);
    void chain();
    static void terminate();
    static void schedule();
    static void getTaskID(TaskRefType taskRef);
    static void getTaskState(TaskType taskID, TaskStateRefType state);
    
    static void enableAllInterrupts();
    static void disableAllInterrupts();
    static void resumeAllInterrupts();
    static void suspendAllInterrupts();
    static void resumeOSInterrupts();
    static void suspendOSInterrupts();

    static void getResource(ResourceType resource);
    static void releaseResource(ResourceType resource);

    static StatusType setEvent(TaskType TaskId, EventMaskType mask);
    static StatusType getEvent(TaskType TaskId, EventMaskRefType maskRef);
    static StatusType clearEvent(EventMaskType mask);
    static StatusType waitEvent(EventMaskType mask);

    static void getAlarmBase(AlarmType alarmId, AlarmBaseRefType info);
    static void getAlarm(AlarmType alarmId, TickRefType tick);
    static StatusType setRelAlarm(AlarmType alarmId,
                                  TickType increment, 
                                  TickType cycle);
    static StatusType setAbsAlarm(AlarmType alarmId, //f
                                  TickType start, 
                                  TickType cycle);
    static StatusType cancelAlarm(AlarmType alarmId); //f
    
    static AppModeType getActiveApplicationMode(); //f
    static void startOS(AppModeType mode);
    static void shutdownOS(StatusType error);
    
 public:
    void osek_reset();

    static void exit(int status = 0);

    static void osek_reschedule(bool preempt);
    static void osek_reschedule(bool preempt, OSEK_Task * prev);

 private:
    static void osek_dispatch(OSEK_Task * prev, OSEK_Task * next);

    static void implicit_exit();

    static void prevent_scheduling() {
        if(active_scheduler) {
            CPU::int_disable();
        }
    }


    static void allow_scheduling() {
        if(active_scheduler) {
            CPU::int_enable();
        }
    }

    /* This method is almost equal to Thread::resume. The only difference is
	 * that this method does not try call reschedule. */
	void osek_resume();

	/* This method is very similar to Thread::suspend. The main difference is
	 * that this method does not switch to other thread. */
	void osek_suspend();

	void osek_wait();

#if 0
	static bool osek_idleRunning();
#endif

#if 0
	static void osek_printQueue(Queue q);
#endif


};



__END_SYS

#endif
