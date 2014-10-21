/* Target */
#ifndef BASIC_TASK_INTERFACE_HH
#define BASIC_TASK_INTERFACE_HH

#include <epos2osek_defs.h>

// Interface
class BasicTaskInterface {
protected:
    BasicTaskInterface() {}

// All methods should be private
private:
    static void startOS(ApplicationModes appMode);
    static StatusType terminate();
    static StatusType activate(TaskType taskID);
    static StatusType chain(TaskType taskID);
    
    // Task's attributes in access methods form
    // --- search
    TaskStateType state();
    SomeTaskPriorities priority();
    bool preemptable();
    // --- modification
    void state(TaskStateType taskState);
    
    // Access Methods
    // --- search
    TaskType identity();
    
    
// Task's attributes
protected:
    TaskType _identity;
    //~ TaskStateType state;
    //~ SomeTaskPriorities priority;
    //~ bool preemptable;
};

#endif


