#ifdef AVR
#include "vm.h"
#include "native.h"
#include "native_avr.h"
#include <machine.h>

#include <cpu.h>

__USING_SYS

void led3_on() {  CPU::out8(Machine::IO::PORTB, (1 << 5));
              //    chron->start();
               }//   chron->lap();}
unsigned int led3_off() {
    CPU::out8(Machine::IO::PORTB, (Machine::IO::PORTB,
                                   Machine::IO::PORTB & ~(1 << 5)));


   return 1;//chron->read();
}

void led1_on() {  CPU::out8(Machine::IO::PORTB, (1 << 7));
              //    chron->start();
               }//   chron->lap();}


void led1_off() {
    CPU::out8(Machine::IO::PORTB, (Machine::IO::PORTB,
                                   Machine::IO::PORTB & ~(1 << 7)));



}



void native_avr_port_invoke(u08_t mref, StackVm* stack) {
  if(mref == NATIVE_METHOD_SETINPUT) {
    u08_t bit  = stack->stack_pop();
    u08_t port = stack->stack_pop();
    db<VM>(WRN) << "native setinput " << bit << "\n" << port << endl;
   // *ddrs[port] &= ~_BV(bit);
  }/* else if(mref == NATIVE_METHOD_SETOUTPUT) {
    u08_t bit  = stack_pop();
    u08_t port = stack_pop();
    DEBUGF("native setoutput %bd/%bd\n", port, bit);
    *ddrs[port] |= _BV(bit);
  }*/ else if(mref == NATIVE_METHOD_SETBIT) {
      u08_t bit  = stack->stack_pop();
      u08_t port = stack->stack_pop();
       led1_on();

  } else if(mref == NATIVE_METHOD_CLRBIT) {
      u08_t bit  = stack->stack_pop();
      u08_t port = stack->stack_pop();
        led1_off();
  } else
    db<VM>(ERR) << "ERROR_NATIVE_UNKNOWN_METHOD";
}
#endif
