// EPOS Semaphore Abstraction Proxy

#include <semaphore_proxy.h>
#include <message.h>
#include <syscall_types.h>
#include <syscall_handler.h>

__BEGIN_SYS


Semaphore_Proxy::Semaphore_Proxy(int v) {
    void* par[1] = {&v};
    OStream cout;

    Syscall_Message m = {
        /* .method_id = */SEMAPHORE_CONSTRUCTOR,
        /* .object = */   0,
        /* .params = */   par,
        /* .nparams = */  1
    };

    cout << "Before sysenter\n";

    Syscall_Response r = Syscall_Handler::sysenter(m);

    cout << "After sysenter\n";

    if(r.id < 0); //TODO ERRO
    
    this->_id = r.id;
    

    cout << "Id: " << _id << "\n"; 
}

Semaphore_Proxy::~Semaphore_Proxy() {
    Syscall_Message m = {
        /* .method_id = */SEMAPHORE_DESTRUCTOR,
        /* .object = */   _id,
        /* .params = */   0,
        /* .nparams = */  0
    };

    Syscall_Response r = Syscall_Handler::sysenter(m);
    if(r.error_code < 0); //TODO ERRO
    
    OStream cout;
    cout << "Destructing Id: " << _id << "\n"; 
}

void Semaphore_Proxy::p() {
    Syscall_Message m = {
        /* .method_id = */SEMAPHORE_P,
        /* .object = */   _id,
        /* .params = */   0,
        /* .nparams = */  0
    };

    Syscall_Response r = Syscall_Handler::sysenter(m);
    if(r.error_code < 0); //TODO ERRO
    
    OStream cout;
    cout << "P-ing Id: " << _id << "\n"; 

}

void Semaphore_Proxy::v() {
    Syscall_Message m = {
        /* .method_id = */SEMAPHORE_V,
        /* .object = */   _id,
        /* .params = */   0,
        /* .nparams = */  0
    };

    Syscall_Response r = Syscall_Handler::sysenter(m);
    if(r.error_code < 0); //TODO ERRO
    
    OStream cout;
    cout << "v-ing Id: " << _id << "\n"; 


}

__END_SYS
