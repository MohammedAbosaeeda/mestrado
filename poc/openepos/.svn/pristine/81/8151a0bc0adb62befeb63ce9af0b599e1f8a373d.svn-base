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

#ifndef HEAP_H
#define HEAP_H
#include "config.h"
#include "types.h"
#include "nvmtypes.h"
// Each heap element consists of a header and the actual data. This header
// consists of the heap id and a 2 byte combined length/flag value. With a
// 1 byte heap id and and the border case of 1 byte data each heap element
// occupies 4 bytes so with a heap size <= 1024 bytes there are never more
// than 256 heap elements possible meaning a 1 byte heap id is sufficient.
#if HEAPSIZE <= 1024
typedef u08_t heap_id_t;
#else
typedef u16_t heap_id_t;
#endif 


class HeapVM {

private:

    typedef struct {
      heap_id_t id;
      u16_t len;  // actually 15 bits for the len and 1 bit for the fieldref flag
    } PACKED heap_t;
    u16_t __heap_base;
    HeapVM();
    static HeapVM * __instance;
    u08_t __heap[HEAPSIZE];

public:
    u08_t * heap_get_base(void);
    static HeapVM * getInstance();
    heap_t * heap_search(heap_id_t);
    heap_id_t heap_new_id();
    bool_t heap_fieldref(heap_id_t id);
    bool_t heap_alloc_internal(heap_id_t id, bool_t fieldref, u16_t size);
    heap_id_t heap_alloc(bool_t fieldref, u16_t size);
    void heap_realloc(heap_id_t id, u16_t size);
    u16_t heap_get_len(heap_id_t id);
    void * heap_get_addr(heap_id_t id);
    void heap_init(void);
    void heap_garbage_collect(void);
    void heap_steal(u16_t bytes);
    void heap_unsteal(u16_t bytes);
    void heap_check(void);
//#ifdef UNIX
    //este metodo deve ser utilizado so para debug, como fazer isto com
    //traits??
//    void      heap_show(void);
//#endif

};





#define HEAP_ID_FREE       0
#define HEAP_LEN_MASK      0x7FFF
#define HEAP_FIELDREF_MASK 0x8000

#ifdef NVM_USE_HEAP_IDMAP
// A heap id map must not be larger than 256 elements meaning a limit of 2048
// heap elements. Each heap element consists of the header (heap_t) and the
// actual data which makes up at least 1 byte. Limiting the heap size to 10kB
// makes sure that are always heap ids available.
#if HEAPSIZE > 10240
#error The maximum heap size is 10kB when using a heap id map.
#endif
u08_t heap_idmap[(HEAPSIZE/(sizeof(heap_t)+1))/8];
const u08_t heap_idmap_mask[8] = {0x01, 0x02, 0x04, 0x08,
                                  0x10, 0x20, 0x40, 0x80};
#endif



#ifdef DEBUG_JVM
//void      heap_check(void);
#define HEAP_CHECK()  heap_check()
#else
#define HEAP_CHECK() 
#endif

#ifdef UNIX
//void      heap_show(void);
#endif

#ifdef NVM_USE_HEAP_IDMAP
//void      heap_mark_id(heap_id_t id);
#endif

#endif // HEAP_H
