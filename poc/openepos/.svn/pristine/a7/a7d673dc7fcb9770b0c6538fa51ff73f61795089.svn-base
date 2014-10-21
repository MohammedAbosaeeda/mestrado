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
//  main() for NanoVM runtime
//

//#include <wrapper_jvm.h>
//#include "types.h"
#include "config.h"
#include "loader.h"
//#include "nvmfile.h"
#include "vm.h"

__USING_SYS

// hooks for init routines

#include "native_impl.h"


int jvm_main() {

 db<VM>(TRC) << "NanoVM  runtime (c) 2005-2007 by Till Harbaum <till@harbaum.org>\n";

#ifdef NVM_USE_DEFAULT_FILE
        db<VM>(TRC) << ("running pre-installed default\n");
#else // NVM_USE_DEFAULT_FILE
    epos_prints("Usage: NanoVM [-options] nvm-file\n"
           "Options:\n"
#endif // NVM_USE_DEFAULT_FILE

  NvmFile * nvmFile = NvmFile::instance();
  nvmFile->nvmfile_init();
  VM::getInstance()->vm_init();
  db<VM>(TRC) << "Finalizou a inicializacao vm (vm_init())\n";

  nvmFile->nvmfile_call_main();

//#ifdef UNIX
  // the following is only being done to detect memory leaks and
  // heap corruption and can thus be omitted on the real thing (tm)
  // give main args back to heap
  HeapVM * heapVM = HeapVM::getInstance();

 // db<VM>(TRC) << "UnSteal do heap\n";
 // heapVM->heap_unsteal(1*sizeof(nvm_stack_t));

 // heapVM->heap_garbage_collect();
//  heapVM->heap_show();

//#endif // UNIX

  db<VM>(TRC) << "main() returned\n";

//#if !defined(UNIX) && !defined(__CC65__)
  // don't care for anything after this ...
//  for(;;);  // reset wdt if in use//
//#endif

  return 0;
}


