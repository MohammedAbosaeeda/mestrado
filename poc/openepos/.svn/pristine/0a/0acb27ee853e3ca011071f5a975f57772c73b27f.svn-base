#include "types.h"
#include "debug.h"
#include "config.h"
#include "error.h"

#include "stack.h"
#include "native.h"
#include "native_nic.h"

#include <utility/malloc.h>
#include <machine.h>

void native_nic_init(void)
{
    stack_push(NVM_TYPE_HEAP | heap_alloc(false, 1));
}

void native_nic_invoke(u08_t mref)
{
    System::NIC* nic;
    nic = new System::NIC();
    
    if (mref == NATIVE_METHOD_SEND) {
        stack_pop(); // obj
        char c[1] = {'m'};
        int r = nic->send(System::NIC::BROADCAST, (System::NIC::Protocol) 1, c, sizeof(char));
        stack_push(nvm_int2stack(r));
    }
    
}

