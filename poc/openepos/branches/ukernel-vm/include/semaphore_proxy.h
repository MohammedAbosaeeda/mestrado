// EPOS Semaphore Abstraction Declarations

#ifndef __semaphore_h
#define __semaphore_h

#include <utility/handler.h>
#include <synchronizer.h>
__BEGIN_SYS


class Semaphore_Proxy
{
public:
    Semaphore_Proxy(int v = 1);
    ~Semaphore_Proxy();

    void p();
    void v();

private:
    Object_Id _id;
};

__END_SYS

#endif
