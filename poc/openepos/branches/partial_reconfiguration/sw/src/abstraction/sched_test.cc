// EPOS Add Abstraction Test Program

/*
#include <scheduler.h>
#include <thread.h>
#include <utility/ostream.h>
#include <sched.h>
#include <component_manager.h>

__USING_SYS

enum{
    NUM_THREADS = 8
};

OStream cout;

unsigned int change_priority(unsigned int param){
    unsigned int ret;
    if(param >= 255)
        ret = 0;
    else
        ret = param + 1;
    return ret;
}

template<class T_Link, class S_Link, class T>
bool handle_scheduler_int(T *sched, int max){
    static int state = 0;

    Thread* t_old = 0;
    Thread* t_new = 0;

    t_old = sched->chosen();

    if(state == 0) {//use choose()
        sched->choose();
        state = 1;
    }else if (state == 1){ //use choose_another()
        sched->choose_another();
        state = 2;
    }else if(state == 2){ //use choose(obj)
        sched->choose((S_Link)t_old->link());
        state = 0;
    }

    t_new = sched->chosen();

    cout << "Chosen was (" << t_old->state() << ", " << sched->get_rank((S_Link)t_old->link()) << ")"
         << " new one is (" << t_new->state() << ", " << sched->get_rank((S_Link)t_new->link()) << ")\n";

    if(((int)(t_new->state())) == max)
        return true;

    if(t_old != t_new){
        unsigned int val = change_priority(sched->get_rank((S_Link)t_old->link()));
        sched->suspend((S_Link)t_old->link());
        sched->update_rank((S_Link)t_old->link(), val);
        sched->resume((S_Link)t_old->link());
        //sched.reset_quantum();
    }

    return false;
}

template<class T_Link, class S_Link, class T>
void simple_test(T *sched){

    cout << "Creating threads\n";

    Thread *threads[NUM_THREADS];
    for (unsigned int i = 0; i < NUM_THREADS; ++i) {
        threads[i] = new Thread(i);
        threads[i]->link((T_Link)sched->insert(threads[i], i));
    }
    cout << "Threads:\n";
    for (unsigned int i = 0; i < NUM_THREADS; ++i) {
        cout << "(" << threads[i]->state() << ", " << sched->get_rank((S_Link)threads[i]->link()) << ")\n";
    }

    //debug_list();



    for (int i = 7; i >= 0; --i) {
        cout << "Schedulables: " << sched->schedulables() << "\n";
        cout << "Wait until all threads are scheduled at least once\n";
        while(!handle_scheduler_int<T_Link,S_Link>(sched, i));
        cout << "Removed thread(" << threads[i]->state() << ", " << sched->get_rank((S_Link)threads[i]->link()) << ")\n";
        sched->remove((S_Link)threads[i]->link());
    }

    cout << "Schedulables: " << sched->schedulables() << "\n";

}

int main()
{

    cout << "\n\nTesting Scheduler<Thread*>\n";
    Scheduler<Thread*> *sched_current = new Scheduler<Thread*>;
    simple_test<Scheduler<Thread*>::Link, Scheduler<Thread*>::Link>(sched_current);

    cout << "\n\nTesting Scenario_Adapter<Implementation::Sched<Thread*> >\n";
    Sched_Thread *sched_unif = new Sched_Thread;
    simple_test<Scheduler<Thread*>::Link, Sched_Thread::Link>(sched_unif);

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;

    return 0;
}
*/
