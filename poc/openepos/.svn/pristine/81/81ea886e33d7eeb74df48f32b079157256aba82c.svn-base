#include <inttypes.h>
#include <avr/io.h>

volatile uint8_t sreg_save = 0;



__inline__ void DisableAllInterrupts() {
    __asm__ __volatile__ ("cli");
}

__inline__ void EnableAllInterrupts() {
    __asm__ __volatile__ ("sei");
}



__inline__ void SuspendAllInterrupts() {
    sreg_save = SREG;
    __asm__ __volatile__ ("cli");
} 

__inline__ void ResumeAllInterrupts() {
    SREG = sreg_save;
}


