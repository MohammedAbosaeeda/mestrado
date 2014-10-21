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
//  This file contains the heap. It can be requested to
//  create/delete objects on the heap and does some
//  simple garbage collection.
//
//  The heap is being used top-to-bottom allowing the
//  virtual machines stack to grow inside the heap from bottom to
//  top
//

//#include <string.h>


#include "config.h"
#include "debug.h"
#include "error.h"
#include <utility/malloc.h>
#include "utils.h"
#include "heap.h"
//#include "stack.h"
//#include "vm.h"


__USING_SYS
HeapVM * HeapVM::__instance = 0x0;

HeapVM * HeapVM::getInstance() {

    if (!__instance)
        __instance = new HeapVM;
    return __instance;
}

HeapVM::HeapVM() : __heap_base(0) {}

// return the real heap base (where memory can be "stolen"
// from
u08_t * HeapVM::heap_get_base(void) {
  return __heap;
}

#ifdef NVM_USE_MEMCPY_UP
// a version of memcpy that can only copy overlapping chunks
// if the target address is higher
void heap_memcpy_up(u08_t *dst, u08_t *src, u16_t len) {
  dst += len;  src += len;
  while(len--) *--dst = *--src;
}
#endif


//#ifdef UNIX
/*
void HeapVM::heap_show(void) {
  u16_t current = __heap_base;

   db<VM>(INF) << "Este método so deve ser compilado com debug ativado"
          << "HeapVM:\n";
  while(current < sizeof(__heap)) {
    heap_t *h = (heap_t*)&__heap[current];
    u16_t len = h->len & HEAP_LEN_MASK;
    if(h->id == HEAP_ID_FREE) {
        db<VM>(INF) << "- " << len << " free bytes\n";
    } else {
       db<VM>(INF) << "- chunk id " << h->id << "with " <<
               len << " bytes:\n";

      if(len > sizeof(__heap))
        db<VM>(ERR) << (ERROR_HEAP_ILLEGAL_CHUNK_SIZE);

      // DEBUG_HEXDUMP(h+1, len);
    }

    if(len + sizeof(heap_t) > sizeof(__heap) - current) {
      // DEBUGF("heap_show(): total size error\n");
      error(ERROR_HEAP_CORRUPTED);
    }

    current += len + sizeof(heap_t);
  }

    heap_t *h = (heap_t*)&__heap[__heap_base];
    db<VM> (INF) << "heap_t ->" << h << endl;
  db<VM>(INF) << "- " << __heap_base << "bytes stolen\n";
}
*/
//#endif

// search for chunk with id in heap and return chunk header
// address
HeapVM::heap_t * HeapVM::heap_search(heap_id_t id) {
  u16_t current = __heap_base;

  while(current < sizeof(__heap)) {
    heap_t * h = (heap_t*)&__heap[current];
    if(h->id == id) return h;
    current += (h->len & HEAP_LEN_MASK) + sizeof(heap_t);
  }
  return(heap_t*)NULL;
}

#ifdef NVM_USE_HEAP_IDMAP

void heap_init_ids(void) {
  memset(heap_idmap, 0, sizeof(heap_idmap));
  heap_idmap[0] = 0x01;  // mark HEAP_ID_FREE
}

void heap_mark_id(heap_id_t id) {
  // DEBUGF("  heap_mark_id(id=0x%04x)\n", id);
  heap_idmap[id/8] |= heap_idmap_mask[id%8];
}

u08_t heap_id_marked(heap_id_t id) {
  return heap_idmap[id/8] & heap_idmap_mask[id%8];
}

heap_id_t heap_new_id(void) {
  u08_t byte,bit;

  for(byte=0;;byte++) {
    if(heap_idmap[byte] != 0xFF)
      for(bit=0;;bit++)
	if(!(heap_idmap[byte] & heap_idmap_mask[bit])) {
	  heap_idmap[byte] |= heap_idmap_mask[bit];
	  return byte*8+bit;
	}

    // check failure here before incrementing
    // to allow for id maps with 256 elements
    if(byte == sizeof(heap_idmap)-1)
      return 0;
  }
}

// in some cases, references to heap objects may be inside
// other heap objects. this currently happens only when
// a clazz is instanciated and this clazz contains fields.
// the heap element created by the constructor is marked with
// the fieldref bit and it is searched for references during
// garbage collections
void heap_mark_child_ids(void) {
  bool_t again;

  do {
    u16_t current = ____heap_base;
    again = false;

    // DEBUGF("heap_mark_child_ids(): starting heap walk\n");
    while(current < sizeof(heap)) {
      heap_t *h = (heap_t*)&heap[current];

      // check for elements with the fieldref flag
      if(h->len & HEAP_FIELDREF_MASK && heap_id_marked(h->id)) {
	u08_t fields = (u08_t)((h->len & HEAP_LEN_MASK) / sizeof(nvm_ref_t));
	u08_t i;

	// check all fields in the heap element
	// DEBUGF("- checking id 0x%04x\n", h->id);
	for(i=0;i<fields;i++) {
	  nvm_ref_t ref = ((nvm_ref_t*)(h+1))[i];

	  // mark heap element only if field actually references
	  // a heap element and that wasn't already marked before
	  if((ref & NVM_TYPE_MASK) == NVM_TYPE_HEAP &&
	     !heap_id_marked(ref & ~NVM_TYPE_MASK)) {
	    heap_mark_id(ref & ~NVM_TYPE_MASK);

	    // we could check here if any of the newly marked heap elements
	    // has the fieldref flag set but doing would require walking the
	    // heap for every single one (!) so it's cheaper to just do the
	    // walk here again as soon as we newly marked any heap elements
	    again = true;
	  }
	}
      }

      current += (h->len & HEAP_LEN_MASK) + sizeof(heap_t);
    }
} while(again);
}

#else // NVM_USE_HEAP_IDMAP

heap_id_t HeapVM::heap_new_id(void) {
  heap_id_t id;

  for(id=1;id;id++) 
    if(heap_search(id) == NULL) 
      return id;

  return 0;
}

// in some cases, references to heap objects may be inside
// other heap objects. this currently happens only when
// a clazz is instanciated and this clazz contains fields.
// the heap element created by the constructor is marked with
// the fieldref bit and it is searched for references during
// garbage collections
bool_t HeapVM::heap_fieldref(heap_id_t id) {
  nvm_ref_t id16 = id | NVM_TYPE_HEAP;
  u16_t current = __heap_base;

  // walk through the entire heap
  while(current < sizeof(__heap)) {
    heap_t *h = (heap_t*)&__heap[current];

    // check for entries with the fieldref flag
    if(h->len & HEAP_FIELDREF_MASK) {
      u08_t entries = (u08_t)((h->len & HEAP_LEN_MASK) / sizeof(nvm_ref_t));
      u08_t i;

      // check all entries in the heap element for
      // the reference we are searching for
      for(i=0;i<entries;i++) {
	if(((nvm_ref_t*)(h+1))[i] == id16)
	  return true;
      }
    }

    current += (h->len & HEAP_LEN_MASK) + sizeof(heap_t);
  }
  
  return false;
}

#endif // NVM_USE_HEAP_IDMAP

bool_t HeapVM::heap_alloc_internal(heap_id_t id, bool_t fieldref, u16_t size) {
  u16_t req = size + sizeof(heap_t);  // total mem required

  // search for free block
  heap_t *h = (heap_t*)&__heap[__heap_base];

  // (no HEAP_LEN_MASK required for free chunk)
  if(h->len >= req) {
    // reduce the size of the free chunk
    // (no HEAP_LEN_MASK required for free chunk)
    h->len -= req;

    // and create the new chunk behind this one
    // (no HEAP_LEN_MASK required for free chunk)
    h = (heap_t*)&__heap[__heap_base + sizeof(heap_t) + h->len];
    h->id = id;
    h->len = fieldref ? size | HEAP_FIELDREF_MASK : size & HEAP_LEN_MASK;
#ifdef NVM_INITIALIZE_ALLOCATED
    // fill memory with zero
    u08_t *ptr = (void*)(h+1);
    while(size--)
      *ptr++ = 0;
#endif
    return true;
  }

  // DEBUGF("heap_alloc_internal(%d): out of memory\n", size);
  return false;
}

heap_id_t HeapVM::heap_alloc(bool_t fieldref, u16_t size) {
  heap_id_t id = heap_new_id();

  // DEBUGF("heap_alloc(size=%d) -> id=0x%04x\n", size, id);
  if(!id) error(ERROR_HEAP_OUT_OF_IDS);

  if(!heap_alloc_internal(id, fieldref, size)) {
    heap_garbage_collect();

    // we need to reallocate heap id, gc. threw away the old one...
    id = heap_new_id();
    if(!id) error(ERROR_HEAP_OUT_OF_IDS);

    if(!heap_alloc_internal(id, fieldref, size))
      error(ERROR_HEAP_OUT_OF_MEMORY);
    // DEBUGF("heap_alloc(size=%d) -> id=0x%04x successfull after gc\n", size, id);
  }

  return id;
}

void HeapVM::heap_realloc(heap_id_t id, u16_t size) {
  heap_t *h, *h_new;

  // DEBUGF("heap_realloc(id=0x%04x, size=%d)\n", id, size);

  // check free mem and call garbage collection if required
  h = (heap_t*)&__heap[__heap_base];
  // (no HEAP_LEN_MASK required for free chunk)
  if(h->len < size + sizeof(heap_t))
    heap_garbage_collect();

  // get info on old chunk
  h = heap_search(id);

  // allocate space for bigger one
  if(!heap_alloc_internal(id, h->len & HEAP_FIELDREF_MASK ? true : false, size))
    error(ERROR_HEAP_OUT_OF_MEMORY);

  h_new = heap_search(id);

  utils_memcpy(h_new+1, h+1, h->len & HEAP_LEN_MASK);

  // this chunk is not immediately available for new allocation
  // but it will be removed by the garbage collection next time 
  h->id = HEAP_ID_FREE;
}

u16_t HeapVM::heap_get_len(heap_id_t id) {
  heap_t *h = heap_search(id);
  if(!h) error(ERROR_HEAP_CHUNK_DOES_NOT_EXIST);
  return h->len & HEAP_LEN_MASK;
}

void * HeapVM::heap_get_addr(heap_id_t id) {
  heap_t *h = heap_search(id);
  if(!h) db<VM> (TRC) << "(ERROR_HEAP_CHUNK_DOES_NOT_EXIST) "<< endl;
  return h+1;
}

void HeapVM::heap_init(void) {
  heap_t *h;
  // just one big free block


  h = (heap_t*)&__heap[0];
  db<VM> (INF) << "Heap Init: " << endl <<
                  "__heap[0] -> " << hex <<(int)&__heap[0] << endl <<
                  "heap_t * -> " << h << endl;
  h->id = HEAP_ID_FREE;
  // (no HEAP_LEN_MASK required for free chunk)
  h->len = sizeof(__heap) - sizeof(heap_t);

  db<VM>(INF) << "Heap init. Size => " << h->len <<endl;

#ifdef NVM_USE_HEAP_IDMAP
  heap_init_ids();
#endif
}

// walk through the heap, check for every object
// if it's still being used and remove it if not
void HeapVM::heap_garbage_collect(void) {
  u16_t current = __heap_base;
  heap_t *h;
  // (no HEAP_LEN_MASK requ _nvmFile->nvmfile_get_static_fields()ired for free chunk)
  // DEBUGF("heap_garbage_collect() free space before: %d\n", ((heap_t*)&heap[__heap_base])->len);

#ifdef NVM_USE_HEAP_IDMAP
  heap_init_ids();
  stack_mark_heap_root_ids();
  heap_mark_child_ids();
#endif

  // set curre _nvmFile->nvmfile_get_static_fields()nt to stack-top
  // walk through the entire heap
  while(current < sizeof(__heap)) {
    u16_t len;
    h = (heap_t*)&__heap[current];
    len = (h->len & HEAP_LEN_MASK) + sizeof(heap_t);

    // found an entry
    if(h->id != HEAP_ID_FREE) {
      // check if it's still used
#ifdef NVM_USE_HEAP_IDMAP
      if(!heap_id_marked(h->id)) {
#else
  //    if((!stack_heap_id_in_use(h->id))&&(!heap_fieldref(h->id))) {
#endif
	// it is not used, remove it
	// DEBUGF("HEAP: removing unused object with id 0x%04x (len %d)\n", h->id, len);
      
	// move everything before to the top
#ifdef NVM_USE_MEMCPY_UP
        heap_memcpy_up(__heap+__heap_base+len, __heap+__heap_base, current-__heap_base);
#else
        memmove(heap+__heap_base+len, heap+__heap_base, current-__heap_base);
#endif

	// add freed mem to free chunk
        h = (heap_t*)&__heap[__heap_base];
	// (no HEAP_LEN_MASK required for free chunk)
	h->len += len;
   //   }
    }
    current += len;
  }

  if(current != sizeof(__heap)) {
    // DEBUGF("heap_garbage_collect(): total size error\n");
    error(ERROR_HEAP_CORRUPTED);
  }

  // (no HEAP_LEN_MASK required for free chunk)
  // DEBUGF("heap_garbage_collect() free space after: %d\n", ((heap_t*)&heap[__heap_base])->len);
}

// "steal" some bytes from the bottom of the heap (where
// the free chunk is)
  void HeapVM::heap_steal(u16_t bytes) {
  heap_t *h = (heap_t*)&__heap[__heap_base];
  u16_t len;

   db<VM> (TRC) << "HEAP: request to steal " << bytes << "bytes\n";

  if(h->id != HEAP_ID_FREE) {
    // DEBUGF("heap_steal(%d): start element not free element\n", bytes);
    error(ERROR_HEAP_CORRUPTED);
  }

  // try to make space if necessary
  // (no HEAP_LEN_MASK required for free chunk)
  len = h->len;
  if(len < bytes) 
    heap_garbage_collect();

  // (no HEAP_LEN_MASK required for free chunk)
  len = h->len;
  if(len < bytes) 
    error(ERROR_HEAP_OUT_OF_STACK_MEMORY);

   // finally steal ...
  __heap_base += bytes;
  h = (heap_t*)&__heap[__heap_base];
  h->id = HEAP_ID_FREE;
  // (no HEAP_LEN_MASK required for free chunk)
  h->len = len - bytes;
}

// someone wants us to give some bytes back :-)
  void HeapVM::heap_unsteal(u16_t bytes) {
  heap_t *h = (heap_t*)&__heap[__heap_base];
  u16_t len;

  if(h->id != HEAP_ID_FREE) {
    // DEBUGF("heap_unsteal(%d): start element not free element\n", bytes);
    error(ERROR_HEAP_CORRUPTED);
  }

  // DEBUGF("HEAP: request to unsteal %d bytes\n", bytes);

  if(__heap_base < bytes) {
    // DEBUGF("stack underrun by %d bytes\n", bytes - __heap_base);
    error(ERROR_HEAP_STACK_UNDERRUN);
  }

  // finally unsteal ...
  // (no HEAP_LEN_MASK required for free chunk)
  len = h->len;
  __heap_base -= bytes;
  h = (heap_t*)&__heap[__heap_base];
  h->id = HEAP_ID_FREE;
  // (no HEAP_LEN_MASK required for free chunk)
  h->len = len + bytes;
}


#ifdef DEBUG_JVM
// make some sanity checks on the heap in order to detect
// heap corruption as early as possible
  void HeapVM::heap_check(void) {
  u16_t current = __heap_base;
  heap_t *h = (heap_t*)&__heap[current];
  u16_t len;

  if(h->id != HEAP_ID_FREE) {
    // DEBUGF("heap_check(): start element not free element\n");
    error(ERROR_HEAP_CORRUPTED);
  }

  // (no HEAP_LEN_MASK required for free chunk)
  current += h->len + sizeof(heap_t);

  while(current < sizeof(heap)) {
    h = (heap_t*)&__heap[current];
    len = h->len & HEAP_LEN_MASK;
    if(len > sizeof(__heap)) {
      // DEBUGF("heap_check(): single chunk too big\n");
      heap_show();
      error(ERROR_HEAP_ILLEGAL_CHUNK_SIZE);
    }

    if(len + sizeof(heap_t) > sizeof(__heap) - current) {
      // DEBUGF("heap_check(): total size error\n");
      heap_show();
      error(ERROR_HEAP_CORRUPTED);
    }

    current += len + sizeof(heap_t);
  }

  if(current != sizeof(__heap)) {
    // DEBUGF("heap_check(): heap sum mismatch\n");
    heap_show();
    error(ERROR_HEAP_CORRUPTED);
  }
}
#endif
