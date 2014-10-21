#include <semaphore_agent.h>
#include <system.h>
__BEGIN_SYS


Syscall_Response Semaphore_Agent::constructor(Object_Table& tb, Syscall_Message& m) {
    Syscall_Response r;
    if(m.nparams != 1); //TODO
    Semaphore* s =  new (SYSTEM) Semaphore(*((int*)m.params[0]));
    Object_Id id = tb.insert_object((Object_Address)s);
    r.id = id;
    
    if (id == -1) //ERRO
        r.error_code = -1;

    return r;
}

Syscall_Response Semaphore_Agent::destructor(Object_Table& tb, Syscall_Message& m) {
    Syscall_Response r;
    if(m.nparams != 0); //TODO
    Semaphore* s = (Semaphore*) tb.delete_object(m.object);
    delete s;
    r.error_code = 0;
    return r;
}

Syscall_Response Semaphore_Agent::p(Object_Table& tb, Syscall_Message& m) {
    Syscall_Response r;
    if(m.nparams != 0); //TODO
    Semaphore* s = (Semaphore*) tb.get_object(m.object);
    s->p();
    r.error_code = 0;
    return r; 
}

Syscall_Response Semaphore_Agent::v(Object_Table& tb, Syscall_Message& m) {
    Syscall_Response r;
    if(m.nparams != 0); //TODO
    Semaphore* s = (Semaphore*) tb.get_object(m.object);
    s->v();
    r.error_code = 0;
    return r;
}

__END_SYS

