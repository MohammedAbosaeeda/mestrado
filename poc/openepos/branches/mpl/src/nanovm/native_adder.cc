#include "types.h"
#include "debug.h"
#include "config.h"
#include "error.h"

#include "stack.h"
#include "native.h"
#include "native_adder.h"

#include <utility/ostream.h>

#define NATIVE_METHOD_initadder 17
#define NATIVE_METHOD_put 144

extern System::OStream cout;

void native_adder_init(void)
{
    cout << "Contemplem a criacao!\n";
    // create empty stringbuf object and push reference onto stack
    stack_push(NVM_TYPE_HEAP | heap_alloc(false, 1));
    // stack_pop();
}


void native_adder_invoke(u08_t mref)
{
    if (mref == NATIVE_METHOD_put) {
        /*
        nvm_int_t a = stack_pop_int();
        nvm_int_t b = stack_pop_int();
        stack_push(nvm_int2stack(a + b));
        */
        // cout << "Eu sou o mestre\n";        
        stack_pop(); // obj
        
        stack_push(nvm_int2stack(22));
        int dst = stack_pop_int(); // dst
        
        stack_push(nvm_int2stack(22));
        int prot = stack_pop_int(); // prot
        
        //int data = stack_pop_int(); // data
        //int size = stack_pop_int(); // size
        
        stack_push(nvm_int2stack(22));
    }
    else if (mref == NATIVE_METHOD_initadder) {
        // cout << "Estou acima dos 88 cavaleiros\n";
        stack_pop(); // obj
        
        stack_push(nvm_int2stack(22));
        int dst = stack_pop_int(); // dst
        
        stack_push(nvm_int2stack(22));
        int prot = stack_pop_int(); // prot
        
        //int data = stack_pop_int(); // data
        //int size = stack_pop_int(); // size
        
        stack_push(nvm_int2stack(22));
    }
    else {
        error(ERROR_NATIVE_UNKNOWN_METHOD);
    }
}
