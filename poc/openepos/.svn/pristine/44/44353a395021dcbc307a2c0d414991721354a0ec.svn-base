// EPOS Semaphore Abstraction Declarations

#ifndef __semaphore_agent_h
#define __semaphore_agent_h

#include <message.h>
#include <response.h>
#include <object_table.h>
#include <semaphore.h> 
__BEGIN_SYS


class Semaphore_Agent
{
public:
    static Syscall_Response constructor(Object_Table& tb, Syscall_Message& m);
    static Syscall_Response destructor(Object_Table& tb, Syscall_Message& m);

    static Syscall_Response p(Object_Table& tb, Syscall_Message& m);
    static Syscall_Response v(Object_Table& tb, Syscall_Message& m);
};

__END_SYS

#endif
