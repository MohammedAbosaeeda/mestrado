#ifndef NATIVE_THREAD_H
#define NATIVE_THREAD_H

#include "stack.h"
#include "threadwrapper.h"
#include "native_thread.h"
#include <utility/hash.h>


__BEGIN_SYS

class Native_Thread {

    Native_Thread() : _i(0){}
    static Native_Thread * _instance;
    short int _i;
    typedef Simple_Hash<ThreadWrapper* , Traits<VM>::MAX_THREADS, unsigned int>::Element HashElement;
    HashElement * _e[Traits<VM>::MAX_THREADS];
    Simple_Hash<ThreadWrapper* , Traits<VM>::MAX_THREADS, unsigned int> _mapper;
    ThreadWrapper*  _o[Traits<VM>::MAX_THREADS];
    void thread_init(StackVm* stack);
    void thread_start(StackVm*, u16_t);

public:

    static Native_Thread * instance();
    void native_thread_invoke(u08_t mref, StackVm* stack, u16_t);


};

__END_SYS

#endif // NATIVE_THREAD_H
