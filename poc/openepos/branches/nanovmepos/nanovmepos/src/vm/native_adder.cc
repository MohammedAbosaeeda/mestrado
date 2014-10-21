#include "types.h"
#include "debug.h"
#include "config.h"
#include "error.h"

#include "stack.h"
#include "native.h"
#include "native_adder.h"

#include <stdio.h>


#define NATIVE_METHOD_st_sum 1

void native_adder_init(void)
{
}


void native_adder_invoke(u08_t mref)
{
    if (mref == NATIVE_METHOD_st_sum) {
        nvm_int_t a = stack_pop_int();
        nvm_int_t b = stack_pop_int();
        stack_push(nvm_int2stack(a + b));
    } 
    else {
        error(ERROR_NATIVE_UNKNOWN_METHOD);
    }
}

