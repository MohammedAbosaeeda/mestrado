#ifndef THREADWRAPPER_H
#define THREADWRAPPER_H
#include "utility/malloc.h"
#include "active.h"
#include "opcodes.h"
#include "vm.h"
#include "error.h"
#include "native.h"
#include "native_impl.h"

__BEGIN_SYS
class ThreadWrapper : public Active
{

private:
    static int const pc_magic_jump = 4;
    unsigned int _id; //maybe not needed
    unsigned int _methodAddress;
    StackVm * _stack;
    nvm_stack_t * _locals;
    u08_t  *pc;
    void vm_run(u16_t mref);
    void vm_new(u16_t);
    typedef union {
      s16_t w;
      struct {
        s08_t bl, bh;
      } z;
      nvm_int_t tmp;
    } vm_arg_t;

    vm_arg_t arg0;

public:

    static int adjustPC();
    ThreadWrapper(unsigned int id, unsigned int methodAddress = 0);
    void methodAddress(unsigned int address) { _methodAddress = address; }
    void copy(StackVm * stack);
    int run();
};

__END_SYS
#endif // THREADWRAPPER_H
