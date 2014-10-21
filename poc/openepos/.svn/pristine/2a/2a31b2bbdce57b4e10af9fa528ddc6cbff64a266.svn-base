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
//  vm.c
//db<VM> (TRC) <<"Lendo args do nvmFile\n" <<


#include <utility/string.h>

//#include "types.h"
#include "debug.h"
#include "config.h"
#include "error.h"

#include "vm.h"
#include "opcodes.h"
#include "native_impl.h"
#include "native.h"

#include "nvmfeatures.h"
#include <utility/malloc.h>
//#include <wrapper_jvm.h>
#ifdef NVM_USE_ARRAY
#include "array.h"
#endif

#ifdef NVM_USE_32BIT_WORD
# define DBG_INT "0x" DBG32
#else
# define DBG_INT "0x" DBG16
#endif
__BEGIN_SYS


VM* VM::__instance = 0x0;


VM* VM::getInstance()
{
    if ( __instance == 0x0) {
        __instance = new VM;
    }

    return __instance;
}


VM::VM()
{
    __heapVm = HeapVM::getInstance();
    _nvmFile = NvmFile::instance();
}


nvm_stack_t* VM::locals()
{
    return __locals;
}


void VM::locals(nvm_stack_t* s)
{
    __locals = s;
}



nvm_stack_t VM::local(int i)
{
    return __locals[i];
}


void VM::local(int i, nvm_stack_t s)
{
    __locals[i] = s;
}

void VM::vm_init(void) {

    int nStaticFields =  _nvmFile->nvmfile_get_static_fields();
    db<VM>(INF) << "vm_init() with "
            << nStaticFields << " static fields\n";

    // init heap
  __heapVm-> heap_init();

  //Nao sei o que acontece aqui.
  //Isso deve ser mudado drasticamente?
  //pensar em mais de um vm rodando resolveria?
  //Inicializando a stack, , acredito que aqui deveria ser
   //inicializado uma thread?

//  _stack = new StackVm(); //Uma vm multiThread nao deve ter uma stack e sim a thread.

 // db<VM>(TRC) << "Criou uma Stack\n";

  // get stack space from heap and setup stack
//  _stack->stack_init(_nvmFile->nvmfile_get_static_fields());

 // db<VM>(TRC) << "Inicializou a stack\n";

 //IMPORTANTE _stack->stack_push(0);

  db<VM>(TRC) << "deu um push na stack\n";
  // args parameter to main (should be a string array)
}

void * VM::vm_get_addr(nvm_ref_t ref) {
  if(!(ref & NVM_IMMEDIATE_MASK))
    error(ERROR_VM_ILLEGAL_REFERENCE);

  if((ref & NVM_TYPE_MASK) == NVM_TYPE_HEAP)
    return __heapVm->heap_get_addr(ref & ~NVM_TYPE_MASK);

  // return nvmfile address and set marker indicating
  // that this is inside the nvm file (and may have
  // to be accessed in a special manner)
  return NVMFILE_SET(_nvmFile->nvmfile_get_addr(ref & ~NVM_TYPE_MASK));
}

// expand 15 bit immediate to 16 bits (or 31 to 32)
nvm_int_t VM::nvm_stack2int(nvm_stack_t val) {
  if(val & (NVM_IMMEDIATE_MASK>>1))
    val |= NVM_IMMEDIATE_MASK;   // expand sign bit
  return val;
}

#ifdef NVM_USE_FLOAT
nvm_stack_t VM::nvm_float2stack(nvm_float_t val)
{
  nvm_union_t v;
  v.f[0]=val;
  //printf("float = %f == 0x%x", v.f[0], v.i[0]);
  uint8_t msb = (v.b[3]&0x80)?0x40:0x00;
  v.b[3] &= 0x7f;
  if (v.b[3]==0x7f && (v.b[2]&0x80)==0x80)
    msb |= 0x3f;
  else if (v.b[3]!=0x00 || (v.b[2]&0x80)!=0x00)
    msb |= v.b[3]-0x20;
  v.b[3]=msb;
  //printf(" -> encoded = 0x%x\n", v.i[0]);
  return v.i[0];
}

nvm_float_t VM::nvm_stack2float(nvm_stack_t val)
{
  nvm_union_t v;
  v.i[0]=val;
  //printf("encoded = 0x%x", v.i[0]);
  uint8_t msb = (v.b[3]&0x40)?0x80:0x00;
  v.b[3] &= 0x3f;
  if (v.b[3]==0x3f && (v.b[2]&0x80)==0x80)
    msb |= 0x7f;
  else if (v.b[3]!=0x00 || (v.b[2]&0x80)!=0x00)
    msb |= v.b[3]+0x20;
  v.b[3]=msb;
  //printf(" -> float = %f == 0x%x\n", v.f[0], v.i[0]);
  return v.f[0];
}
#endif



__END_SYS
