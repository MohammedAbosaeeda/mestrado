// EPOS Semaphore Abstraction Declarations

#ifndef __task_proxy_h
#define __task_proxy_h

#include <utility/handler.h>
#include <synchronizer.h>
__BEGIN_SYS


class Task_Proxy
{
public:
    Task_Proxy(int (*func)(void*), void* arg);
    ~Task_Proxy();

private:
    Object_Id _id;
};

__END_SYS

#endif
