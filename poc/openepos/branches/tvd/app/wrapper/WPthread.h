#ifndef _WPTHREAD_H_
#define _WPTHREAD_H_

#include <thread.h>
#include <mutex.h>
#include <semaphore.h>

__USING_SYS

struct WMutex {
        Mutex * m;
};

struct WSem {
        Semaphore * s;
};


struct WThread {
        Thread * t;
};

#endif
