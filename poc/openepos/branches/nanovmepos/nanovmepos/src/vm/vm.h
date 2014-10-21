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
//  vm.h
//

#ifndef VM_H
#define VM_H

//#include "types.h"
//#include "nvmtypes.h"
#include "heap.h"
#include "nvmfile.h"
#include "stack.h"
#include <system/config.h>
__BEGIN_SYS

class VM {


private:

    VM();
    static VM* __instance;
    nvm_stack_t* __locals;
    HeapVM * __heapVm;
    NvmFile * _nvmFile;
    void vm_new(u16_t mref);
    StackVm * _stack;

    // we prefetch arguments from the program storage
    // and this is the type it is stored into
    typedef union {
      s16_t w;
      struct {
        s08_t bl, bh;
      } z;
      nvm_int_t tmp;
    } vm_arg_t;


public:
    static VM* getInstance();

public:

    nvm_stack_t* locals();
    void locals(nvm_stack_t* s);
    void vm_init();
    nvm_stack_t local(int i);
    void local(int i, nvm_stack_t s);
    void vm_run(u16_t mref);
    void * vm_get_addr(nvm_ref_t ref);

#define nvm_int2stack(x) (~NVM_IMMEDIATE_MASK & (x))
nvm_int_t nvm_stack2int(nvm_stack_t val);

#define nvm_ref2stack(x) (x)
#define nvm_stack2ref(x) (x)

#ifdef NVM_USE_FLOAT
nvm_stack_t nvm_float2stack(nvm_float_t val);
nvm_float_t nvm_stack2float(nvm_stack_t val);
#endif
#define VM_CLASS_CONST_ALLOC  1

};

__END_SYS
// additional items to be allocated on heap during constructor call


//void   vm_init(void);
//void   vm_run(u16_t mref);

// expand types


//#define nvm_int2stack(x) (~NVM_IMMEDIATE_MASK & (x))
//nvm_int_t nvm_stack2int(nvm_stack_t val);

//#define nvm_ref2stack(x) (x)
//#define nvm_stack2ref(x) (x)

//#ifdef NVM_USE_FLOAT
//nvm_stack_t nvm_float2stack(nvm_float_t val);
//nvm_float_t nvm_stack2float(nvm_stack_t val);
//#endif



#endif // VM_H
