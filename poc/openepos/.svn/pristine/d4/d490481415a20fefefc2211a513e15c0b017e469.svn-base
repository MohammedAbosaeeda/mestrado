#include "native_thread.h"

__USING_SYS

Native_Thread* Native_Thread::_instance = 0x0;

Native_Thread* Native_Thread::instance()
{
    if (!_instance)
        _instance = new Native_Thread;
    return _instance;
}

//Maps the java-thread reference with a epos-thread,
void Native_Thread::thread_init(StackVm * stack)
{
   unsigned int ref = stack->stack_pop();

   db<VM> (TRC) << "new thread (address): " << hex <<
          ref << endl;

   ThreadWrapper * thread  = new ThreadWrapper(ref);
   _o[_i] = thread;
   _e[_i] = new HashElement(&_o[_i], ref) ;
   _mapper.insert(_e[_i]);
   _i++;
}

void Native_Thread::thread_start(StackVm * stack, u16_t args)
{
    unsigned int  adr = stack->stack_pop();

    db<VM> (TRC) << "Executing thread start" << endl <<
                    "Address ref: " << hex << adr << endl << hex <<
                    "args: " << (int) args << endl;

    //Faz com que o PC pule o método run
    int methodId = ThreadWrapper::adjustPC();//Traits<VM>::PC_MAGIC_JUMP);
    ThreadWrapper * th = *(_mapper.search_key(adr)->object());
    th->methodAddress(methodId);
    th->copy(stack);
    th->start();
}

void Native_Thread::native_thread_invoke(u08_t mref, StackVm * stack, u16_t args)
{

    if (mref == NATIVE_METHOD_INIT) {
        thread_init(stack);
    } else if (mref == NATIVE_METHOD_START) {
        thread_start(stack, args);
    } else {
        db<VM> (ERR) << "NATIVE UNKNOWN METHOD" << endl;
    }
}

