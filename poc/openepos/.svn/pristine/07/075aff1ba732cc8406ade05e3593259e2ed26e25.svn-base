#include <inttypes.h>
#include <avr/io.h>

#include "event.h"
#include "interrupt.h"


volatile uint8_t __event_mask = 0;

/**
 * This will be called from interrupts.
 */
StatusType SetEvent(TaskRefType task_name, EventMaskType mask) {
    __event_mask |= mask;
    return E_OK;
}


/**
 * This will be called in normal program flow
 */
StatusType ClearEvent(TaskRefType task_name, EventMaskType mask) {
    SuspendAllInterrupts();
    __event_mask &= (~mask);
    ResumeAllInterrupts();
    return E_OK;
}


StatusType WaitEvent(TaskRefType task_name, EventMaskType mask) {
    while ((__event_mask & mask) == 0);
    return E_OK;
}

