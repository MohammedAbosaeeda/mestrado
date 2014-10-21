#ifndef NATIVE_ALARM_H
#define NATIVE_ALARM_H

#include "stack.h"
#include "native.h"

void native_alarm_init();
void native_alarm_invoke(u08_t mref, StackVm* stack );
void native_alarm_delay();


#endif // NATIVE_ALARM_H
