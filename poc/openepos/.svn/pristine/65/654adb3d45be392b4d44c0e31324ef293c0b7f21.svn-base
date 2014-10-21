#include "../mux2/includes/MuxWrapper.h"

#include "WPthread.h"

__USING_SYS

void wpthread_mutex_init(wpthread_mutex_t *m) {

        WMutex * wm = new WMutex();
        wm->m = new Mutex();

        *m = (wpthread_mutex_t) wm;

}

void wpthread_mutex_lock(wpthread_mutex_t *m) {

        WMutex * tmp = (WMutex *) *m;
        tmp->m->lock();

}

void wpthread_mutex_unlock(wpthread_mutex_t *m) {

        WMutex * tmp = (WMutex *) *m;
        tmp->m->unlock();

}

void wsem_init(wsem_t *s, unsigned int value) {

        WSem * ws = new WSem();
        ws->s = new Semaphore(value);

        *s = (wsem_t) ws;

}

void wsem_wait(wsem_t *s) {

        WSem * tmp = (WSem *) *s;
        tmp->s->p();

}

void wsem_post(wsem_t *s) {

        WSem * tmp = (WSem *) *s;
        tmp->s->v();

}


void wpthread_create(int (*entry)(void *), void * arg) {

        WThread * wt = new WThread(); // memleak, who cares?
        wt->t = new Thread(entry, arg);

}

