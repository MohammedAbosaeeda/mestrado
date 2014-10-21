// EPOS Semaphore Abstraction Declarations

#ifndef __task_agent_h
#define __task_agent_h

#include <message.h>
#include <response.h>
#include <object_table.h>
#include <task.h> 
__BEGIN_SYS


class Task_Agent
{
public:
    static Syscall_Response constructor(Object_Table& tb, Syscall_Message& m);
    static Syscall_Response destructor(Object_Table& tb, Syscall_Message& m);
};

__END_SYS

#endif
