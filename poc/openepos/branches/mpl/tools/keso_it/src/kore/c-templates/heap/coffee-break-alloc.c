/*KESO--HEADER--KESO*/
#include "global.h"
object_t* keso_coffee_alloc(class_id_t class_id, obj_size_t size, obj_size_t roff GC_LOG_INFO_PARAMETER);

typedef struct _coffee_listel_s {
	 unsigned int size;        /* size in slots */
	 struct _coffee_listel_s *next; /* Points to the next listelement */
} coffee_listel_t;

#define KESO_ALLOC_CLEAR_OBJ_REFS 1

/*KESO--CFILE--KESO*/

#define KESO_COFFEE_BYTES2SLOTS(__bytesize__, __slotsize__) ((__slotsize__-1+__bytesize__)/__slotsize__)
static unsigned char gctpaused=1;

/*
 * Allocator function of the COFFEE Heap.
 *
 * Returns a pointer to a free memory area on the
 * heap of the current domain with @size bytes.
 *
 * Throws an error if no suitable memory block can
 * be found.
 */
object_t* TRICORE_PSPR_SECTION keso_coffee_alloc(class_id_t class_id, obj_size_t size, obj_size_t roff GC_LOG_INFO_PARAMETER) {
	domain_t *domain;
	coffee_listel_t **prev_ptr;
	coffee_listel_t *slot;
	object_t* obj;

	domain = &DOMAINDESC(KESO_CURRENT_DOMAIN);
	size = KESO_COFFEE_BYTES2SLOTS(size, domain->heap.coffee.slotSize);

#ifdef KESO_GCMODE_ENFORCEONDEMAND
	do {
		COFFEE_GC_LOCK();

		GC_LOG_ALLOC_EVENT(KESO_CURRENT_TASK, GC_LOG_INFO, class_id, domain->heap.coffee.freeslots, size); 
		
		for (prev_ptr = &(domain->heap.coffee.freemem);*prev_ptr!=NULL;prev_ptr=&((*prev_ptr)->next)) {
			if ((*prev_ptr)->size>=size) break;
		}

		if (unlikely(*prev_ptr==NULL)) {
			COFFEE_GC_UNLOCK();
			WaitEvent(KESO_GCRun);
			ClearEvent(KESO_GCRun);
			if (domain->heap.coffee.freemem==NULL) 
				KESO_THROW_ERROR("out_of_memory\n");
			continue;
		}
	} while (0);
#else
	COFFEE_GC_LOCK();

	GC_LOG_ALLOC_EVENT(KESO_CURRENT_TASK, GC_LOG_INFO, class_id, domain->heap.coffee.freeslots, size); 

	for (prev_ptr = &(domain->heap.coffee.freemem);*prev_ptr!=NULL;prev_ptr=&((*prev_ptr)->next)) {
		if ((*prev_ptr)->size>=size) break;
	}

	if (*prev_ptr==NULL) {
		 COFFEE_GC_UNLOCK();
		 KESO_THROW_ERROR("out_of_memory");
	}
#endif
	slot = *prev_ptr;

	if (slot->size > size) {
		/* split block */
		slot->size -= size;
		slot = (coffee_listel_t*)((char*)slot+(slot->size * domain->heap.coffee.slotSize)); 
	} else {
		/* unlink */
		*prev_ptr = slot->next;
		slot->size = 0;
		slot->next = NULL;
	}

#ifdef KESO_ALLOC_CLEAR_OBJ_REFS
	obj = (object_t*) slot;
	while (roff-->0) { ((object_t**)obj)[0] = NULL; obj = (object_t*) (((object_t**)obj)+1); }
#else 
	obj = (object_t*) ((object_t**)slot+roff);
#endif
	obj->class_id=class_id;
	obj->gcinfo = domain->heap.coffee.colorbit;

	domain->heap.coffee.sasls += size;
	domain->heap.coffee.freeslots -= size;

	COFFEE_GC_UNLOCK();

	if(0<gctpaused) {
		gctpaused=0;
		ActivateTask(keso_coffee_gc);
	}

	return obj;
}
