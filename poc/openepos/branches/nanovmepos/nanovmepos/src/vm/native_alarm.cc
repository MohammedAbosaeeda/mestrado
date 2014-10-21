#include "native_alarm.h"
#include "native.h"
//#include "types.h"
#include "alarm.h"
#include "vm.h"

__USING_SYS

void native_thread_init(){


}


void native_alarm_invoke(u08_t mref, StackVm* stack){

    if (mref == NATIVE_METHOD_DELAY) {

        int time = stack->stack_pop_int();
        db<VM>(TRC) << "Delay " << time << endl;
        Alarm::delay(time);
      //  native_alarm_delay();
    } else {
        db<VM> (ERR) << "NATIVE UNKNOWN METHOD" << endl;
      //  error(ERROR_NATIVE_UNKNOWN_METHOD);
    }

}


void native_alarm_delay(){

}
