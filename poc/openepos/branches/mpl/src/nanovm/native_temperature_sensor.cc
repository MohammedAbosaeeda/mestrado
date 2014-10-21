#include "types.h"
#include "debug.h"
#include "config.h"
#include "error.h"

#include "stack.h"
#include "native.h"
#include "native_temperature_sensor.h"

#include <sensor.h>

void native_temperature_sensor_init(void)
{
    stack_push(NVM_TYPE_HEAP | heap_alloc(false, 1));    
}

void native_temperature_sensor_invoke(u08_t mref)
{
    System::Temperature_Sensor* sensor;
    sensor = new System::Temperature_Sensor();
    
    if (mref == NATIVE_METHOD_SAMPLE) {
        stack_pop(); // obj
        int r = sensor->sample();
        stack_push(nvm_int2stack(r));
    }
}
