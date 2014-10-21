//
//  NanoVM, a tiny java VM for the Atmel AVR family
//  Copyright (C) 2005 by Till Harbaum <Till@Harbaum.org>
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
// 

//
//  native_impl.c
//


#include "types.h"
#include "debug.h"
#include "config.h"
#include "error.h"

#ifndef ASURO

//#include "vm.h"
#include "nvmfile.h"
//#include "unix/native.h"
#include "native.h"
#include "native_impl.h"

//__USING_SYS

#ifdef AVR
#include "native_avr.h"
#endif

#ifdef LCD
#include "native_lcd.h"
#endif

#ifdef NVM_USE_STDIO
#include "native_stdio.h"
#endif

#ifdef NVM_USE_MATH
#include "native_math.h"
#endif

//#include "native_adder.h"

#ifdef NVM_USE_FORMATTER
#include "native_formatter.h"
#endif

#ifdef NVM_USE_THREAD
#include "native_thread.h"
#endif

#ifdef  NVM_USE_ALARM
#include "native_alarm.h"
#endif

#ifdef NVM_USE_SEMAPHORE
#include "native_semaphore.h"
#endif

__USING_SYS

void native_java_lang_object_invoke(u08_t mref, StackVm * stack) {
  if(mref == NATIVE_METHOD_INIT) {
    /* ignore object constructor ... */
    stack->stack_pop();  // pop object reference
  } else 
  {
   db<VM>(ERR) << "ERROR NATIVE_UNKNOWN METHOD INIT" << endl;
   error(ERROR_NATIVE_UNKNOWN_METHOD);
  }
}
  
void native_new(u16_t mref, StackVm * stack) {
  if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_STRINGBUFFER) {
    // create empty stringbuf object and push reference onto stack
      stack->stack_push(NVM_TYPE_HEAP | HeapVM::getInstance()->heap_alloc(false, 1));
  } else  if (NATIVE_ID2CLASS(mref) == NATIVE_CLASS_THREAD){
      db<VM>(TRC) << "Put thread reference on the HEAP ->" << endl;

      stack->stack_push(NVM_TYPE_HEAP | HeapVM::getInstance()->heap_alloc(false, 1));

  } else if (NATIVE_ID2CLASS(mref) == NATIVE_CLASS_SEMAPHORE) {

      unsigned int id =  (NVM_TYPE_HEAP | HeapVM::getInstance()->heap_alloc(false, 1));
      db<VM>(TRC) << "Put SEMAPHORE reference on the HEAP -> Is that OK?, estou colocando na stack da Thread,  not good" << hex <<
                id << endl;
      stack->stack_push(id);//NVM_TYPE_HEAP | HeapVM::getInstance()->heap_alloc(false, 1));
       //a stack é da thread, pode ser, talvez, mas preciso dar um jeito de colocar os objetos numa HEAP de verdade/??

  } else {
   db<VM>(TRC) << "UNKNOWN CLASS \n";
    error(ERROR_NATIVE_UNKNOWN_CLASS);
}
}

void native_invoke(u16_t mref, StackVm * stack) {
  // check for native classes/methods
  if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_OBJECT) {
    native_java_lang_object_invoke(NATIVE_ID2METHOD(mref), stack);


#ifdef NVM_USE_THREAD
} else if (NATIVE_ID2CLASS(mref) == NATIVE_CLASS_THREAD) {
      //Native_Thread * n = Native_Thread::instance();
      Native_Thread::instance()->native_thread_invoke(NATIVE_ID2METHOD(mref), stack, mref);
#endif

#ifdef NVM_USE_ALARM
  } else if (NATIVE_ID2CLASS(mref) == NATIVE_CLASS_ALARM) {
      native_alarm_invoke(NATIVE_ID2METHOD(mref), stack);
#endif

#ifdef NVM_USE_SEMAPHORE
  } else if (NATIVE_ID2CLASS(mref) == NATIVE_CLASS_SEMAPHORE) {
        native_semaphore_invoke(NATIVE_ID2METHOD(mref), stack, mref);

#endif

#ifdef NVM_USE_STDIO
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_PRINTSTREAM) {
    native_java_io_printstream_invoke(NATIVE_ID2METHOD(mref), stack);
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_INPUTSTREAM) {
  //  native_java_io_inputstream_invoke(NATIVE_ID2METHOD(mref));
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_STRINGBUFFER) {
 //   native_java_lang_stringbuffer_invoke(NATIVE_ID2METHOD(mref));
#endif

#ifdef NVM_USE_MATH
    // the math class
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_MATH) {
    native_math_invoke(NATIVE_ID2METHOD(mref));
#endif

#ifdef  NVM_USE_ADDER
    // the Adder class
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_ADDER) {
    native_adder_invoke(NATIVE_ID2METHOD(mref));
#endif

#ifdef NVM_USE_FORMATTER
    // the formatter class
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_FORMATTER) {
    native_formatter_invoke(NATIVE_ID2METHOD(mref));
#endif

#if defined(AVR) && !defined(ASURO)
    // the avr specific classes 
    // (not used in asuro, although its avr based)
//  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_AVR) {
 //   native_avr_avr_invoke(NATIVE_ID2METHOD(mref));
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_PORT) {
    native_avr_port_invoke(NATIVE_ID2METHOD(mref), stack);
  /*} else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_TIMER) {
    native_avr_timer_invoke(NATIVE_ID2METHOD(mref));
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_ADC) {
    native_avr_adc_invoke(NATIVE_ID2METHOD(mref));
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_PWM) {
    native_avr_pwm_invoke(NATIVE_ID2METHOD(mref));
*/
#endif

#if defined(LCD)
  } else if(NATIVE_ID2CLASS(mref) == NATIVE_CLASS_LCD) {
    native_lcd_invoke(NATIVE_ID2METHOD(mref));

#endif
} else{
    db<VM> (ERR) << "UNKNOWN CLASS method invoke" << endl;
    error(ERROR_NATIVE_UNKNOWN_CLASS);
}

}


#endif // ASURO
