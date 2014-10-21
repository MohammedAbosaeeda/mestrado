#ifndef NATIVE_SEMAPHORE_H
#define NATIVE_SEMAPHORE_H



#include "stack.h"
#include "threadmapper.h"
#include "threadwrapper.h"

void native_semaphore_init(StackVm* stack);
void native_semaphore_invoke(u08_t mref, StackVm* stack, u16_t);
void native_semaphore_p(StackVm*, u16_t);
void native_semaphore_v(StackVm*, u16_t);

#endif // NATIVE_SEMAPHORE_H
