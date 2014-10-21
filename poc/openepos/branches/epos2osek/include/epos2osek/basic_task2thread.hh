/* Adapter */
#ifndef BASIC_TASK2THREAD_HH
#define BASIC_TASK2THREAD_HH

#include <system/config.h>
__USING_SYS

#include <epos2osek/basic_task_interface.hh>
#include <thread.h>

class BasicTask2Thread : public BasicTaskInterface, private Thread {
public:
    /* BasicTask constructor parameters:
     *  @entry : function pointer
     *  @taskIdentity : TaskType
     *  @taskState : TaskStateType
     *  @taskPriority : SomeTaskPriorities
     */
    BasicTask2Thread(int (* entry)(), 
        TaskType taskIdentity, 
        TaskStateType taskState, 
        SomeTaskPriorities taskPriority) : Thread(entry, 
                                                toEPOS_State(taskState),
                                                toEPOS_Criterion(taskPriority)
                                            )
    {
        _identity = taskIdentity;
        // keeping entry to use on restart
        __entry = entry;
        db<Thread>(TRC) << "BasicTask2Thread::constructorOK\n";
    }

    static void startOS(ApplicationModes appMode);
    static StatusType terminate();
    static StatusType activate(TaskType taskID);
    static StatusType chain(TaskType taskID);
    
    // Task's attributes in access methods form
    // --- search
    TaskStateType state() {
        return fromEPOS_State(Thread::state());
    }
    SomeTaskPriorities priority() {
        return fromEPOS_Criterion(Thread::criterion());
    }
    bool preemptable() {
        return preemptive; // This comes from EPOS traits
    }
    
    // --- modification
    void state(TaskStateType taskState) {
        _state = toEPOS_State(taskState);
    }
    
    // Access methods
    // --- search
    TaskType identity() { return _identity; }

    // --- modification
    
private:
    static ApplicationModes appMode;
    static BasicTask2Thread ** __tasks;
    int (* __entry)();
    
private:
    void restart();
    void __activate();

	TaskStateType fromEPOS_State(State threadState) {
        switch(threadState) {
            case RUNNING:
                return OSEK_RUNNING;
            case WAITING:
                return OSEK_WAITING;
            case READY:
                return OSEK_READY;
            case SUSPENDED:
                return OSEK_SUSPENDED;
			default:
				db<Thread>(TRC) << "BasicTask2Thread::fromEPOS_State, unknow threadState, assuming: OSEK_INVALID_TASK!\n";
				return OSEK_INVALID_TASK;
        }
    }
	
    State toEPOS_State(TaskStateType taskState) {
        switch(taskState) {
            case OSEK_RUNNING:
                return RUNNING;
            case OSEK_WAITING:
                return WAITING;
            case OSEK_READY:
                return READY;
            case OSEK_SUSPENDED:
                return SUSPENDED;
			case OSEK_INVALID_TASK:
				db<Thread>(TRC) << "BasicTask2Thread::toEPOS_State, OSEK_INVALID_TASK!\n";
				return SUSPENDED;
        }
    }
    
	SomeTaskPriorities fromEPOS_Criterion(Criterion criterion) {
		switch (criterion) {
			case Priority::LOW:
				return OSEK_LOWEST;
			case Priority::IDLE:
				return OSEK_IDLE;
			case Priority::NORMAL:
				return OSEK_NORMAL;
			case Priority::HIGH:
				return OSEK_HIGH;
		}		
		
		db<Thread>(TRC) << "BasicTask2Thread::fromEPOS_Criterion, error unknow thread criterion!\n";
    }

    Criterion toEPOS_Criterion(SomeTaskPriorities taskPriority) {
		switch (taskPriority) {
			case OSEK_LOWEST:
				return Priority::LOW;
			case OSEK_IDLE:
				return Priority::IDLE;
			case OSEK_NORMAL:
				return Priority::NORMAL;
			case OSEK_HIGH:
				return Priority::HIGH;			
		}
		db<Thread>(TRC) << "BasicTask2Thread::toEPOS_Criterion, error unknow task priority!\n";
    }

};

#endif
