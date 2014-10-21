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
//  stack.h
//

#ifndef STACK_H
#define STACK_H

//#include "nvmtypes.h"
#include "heap.h"

class StackVm {

private:
    static int _magic;
    //era static
    nvm_stack_t *stack;     // the pysical base of the whole stack (incl. statics)
    nvm_stack_t *sp;        // the current stack pointer
    nvm_stack_t *stackbase; // the base of the runtime stack (excl. statics)
  //  nvm_stack_t *sp_saved;

public:
    nvm_stack_t *sp_saved;

    void print();
    StackVm() ;
    unsigned int getPhysicalBase() {
        return *stack;
    }

    int copy(StackVm * stack);
    void stack_save_base(void);
    void stack_init(u08_t static_fields);
    void init();
    void stack_push(nvm_stack_t val);
    nvm_stack_t stack_pop(void);
    nvm_int_t stack_pop_int(void);
    nvm_stack_t stack_peek(u08_t index);
    nvm_int_t stack_peek_int(u08_t index);
    void * stack_pop_addr(void);
    void * stack_peek_addr(u08_t index);
    //#ifdef NVM_USE_STACK_CHECK
    void stack_save_sp(void);
    void stack_verify_sp(void);
    nvm_stack_t *stack_get_sp(void);
    void stack_add_sp(s08_t offset);
    nvm_stack_t stack_get_static(u16_t index);
    void stack_set_static(u16_t index, nvm_stack_t value);

    bool_t stack_is_empty(void);


//    #ifdef DEBUG
    u16_t stack_get_depth(void);
  //  #endif
    #ifdef NVM_USE_HEAP_IDMAP
    void stack_mark_heap_root_ids(void);
    #else
    bool_t stack_heap_id_in_use(heap_id_t id);
    #endif


# ifdef NVM_USE_FLOAT
nvm_float_t stack_pop_float(void);
nvm_float_t stack_peek_float(u08_t index);
# endif


#define nvm_ref2stack(x) (x)
#define nvm_stack2ref(x) (x)

#define nvm_int2stack(x) (~NVM_IMMEDIATE_MASK & (x))
nvm_int_t nvm_stack2int(nvm_stack_t val);

#ifdef NVM_USE_FLOAT
nvm_stack_t nvm_float2stack(nvm_float_t val);
nvm_float_t nvm_stack2float(nvm_stack_t val);
#endif

    //#endif
};


//void stack_init(u08_t static_fields);

/*#ifdef NVM_USE_STACK_CHECK
void stack_save_sp(void);
void stack_verify_sp(void);
#endif
*/
// various stack operations

/*
# ifdef NVM_USE_FLOAT
nvm_float_t stack_pop_float(void);
nvm_float_t stack_peek_float(u08_t index);
# endif
*/


#endif // STACK_H
