//#include "threadmapper.h"
//#include "threadwrapper.h"
//#include "types.h"
//#include "debug.h" //debug zuando coisas ciclicas
//#include "config.h"
//#include "error.h"
//#include "stack.h"
#include "native.h"
#include <semaphore.h>
#include "native_semaphore.h"
#include<alarm.h>
//#include "vm.h"
__USING_SYS

//#define NATIVE_METHOD_INIT 0


//Mapea a referencia da thread com uma thread epos,
//para depois executar o start
void native_semaphore_init(StackVm* stack)
{

    db<VM>(TRC) << "Semaphore INIT " << endl;
   unsigned int arg = stack->stack_pop();
   unsigned int id = stack->stack_pop();
   db<VM> (TRC) << "new Semaphore (" << arg << ") " << endl <<
                   "id -> " << hex <<  id << endl;

   ThreadMapper<Semaphore* > *  m = ThreadMapper<Semaphore *>::instance();
   Semaphore * t  = new Semaphore(arg);
   m->insertElement(t, id);
}

void native_semaphore_p(StackVm* stack, u16_t args)
{
    unsigned int beforePop = (unsigned int) (stack->stack_get_sp());
    unsigned int  adr = stack->stack_pop();
    unsigned int valueAdd = (unsigned int) stack->stack_get_sp();
    db<VM> (TRC) << "Executing Semaphore P" << endl <<
                    "SP before pop: " << hex <<  beforePop << endl <<
                    "SP: " << hex <<     valueAdd << endl <<
            "Address ref: " << hex << adr << endl << hex <<
            "args: " << (int) args   <<endl;
            endl;

    ThreadMapper<Semaphore *> * m = ThreadMapper<Semaphore *>::instance();
    //int methodId = ThreadWrapper::adjustPC(4); //Faz com que o PC pule o método run
    Semaphore * s = m->search_key(adr);
  // ((ThreadWrapper *) th)->methodAddress(methodId);
    stack->print();
  //  Alarm::delay(1000000);
    db<VM>(TRC) << " PAROU!!!!!!!!!! " << endl;
    s->p();
    db<VM>(TRC) << " VERDE!!!!!!!!!! " << endl;


}

void native_semaphore_v(StackVm * stack, u16_t args){
    unsigned int  adr = stack->stack_pop();
    db<VM> (TRC) << "Executing Semaphore V "<< endl <<
            "Address ref: " << hex << adr << endl << hex <<
            "args: " << (int) args   <<endl;
            endl;

    ThreadMapper<Semaphore *> * m = ThreadMapper<Semaphore *>::instance();
            //int methodId = ThreadWrapper::adjustPC(4); //Faz com que o PC pule o método run
    Semaphore * s = m->search_key(adr);
    s->v();
}


void native_semaphore_invoke(u08_t mref, StackVm* stack, u16_t args)
{

    if (mref == NATIVE_METHOD_INIT) {
        native_semaphore_init(stack);
    } else if (mref == NATIVE_METHOD_P) {
        native_semaphore_p(stack, args);
    } else if (mref == NATIVE_METHOD_V) {
        native_semaphore_v(stack, args);
    } else {
        db<VM> (ERR) << "NATIVE UNKNOWN METHOD" << endl;
      //  error(ERROR_NATIVE_UNKNOWN_METHOD);
    }
}

