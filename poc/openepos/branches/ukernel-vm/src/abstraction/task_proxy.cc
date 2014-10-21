// EPOS Semaphore Abstraction Declarations

#include <task_proxy.h>
#include <message.h>
#include <syscall_types.h>
#include <syscall_handler.h>

__BEGIN_SYS


Task_Proxy::Task_Proxy(int (*func)(void*), void* args) {
    void* par[2] = {&func, &args};

    Syscall_Message m = {
        /* .method_id = */TASK_CONSTRUCTOR,
        /* .object = */   0,
        /* .params = */   par,
        /* .nparams = */  2
    };

    Syscall_Response r = Syscall_Handler::sysenter(m);
    if(r.id < 0); //TODO ERRO

    OStream cout;
    cout << "Criando id: " << r.id << " com arg: " << *(int*)args << "\n";
    
    this->_id = r.id;
    
}

Task_Proxy::~Task_Proxy() {
    OStream cout;
    cout << "Destructing task id: " << _id;
    Syscall_Message m = {
        /* .method_id = */TASK_DESTRUCTOR,
        /* .object = */   _id,
        /* .params = */   0,
        /* .nparams = */  0
    };

    Syscall_Response r = Syscall_Handler::sysenter(m);
    if(r.error_code < 0); //TODO ERRO
    
}

__END_SYS
