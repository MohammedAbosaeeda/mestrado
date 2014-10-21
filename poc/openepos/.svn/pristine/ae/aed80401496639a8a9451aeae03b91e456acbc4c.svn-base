/*KESO--HEADER--KESO*/
#include "global.h"
object_t* keso_irr_alloc(class_id_t class_id, obj_size_t size, obj_size_t roff);

typedef struct _irr_listel_s {
	volatile unsigned short size;        /* size in slots, used by the classid in objects */
	volatile unsigned char  mode;        /* Locking mode, unused in objects. */
	volatile unsigned char  alloccolor;  /* Allocation color */
	volatile struct _irr_listel_s *next; /* Points to the next listelement */
} irr_listel_t;

/*KESO--CFILE--KESO*/
#define KESO_EOFML ((irr_listel_t*) -1)
#define KESO_IRR_BYTES2SLOTS(__bytesize__, __slotsize__) ((__slotsize__-1+__bytesize__)/__slotsize__)

void keso_irr_listwalker(domain_t*, int (*) (irr_listel_t*, unsigned char, irr_listel_t volatile **, domain_t*, void*), void *);
int keso_irr_alloccbfkt(irr_listel_t *, unsigned char, irr_listel_t volatile **, domain_t*, void **);

#ifdef KESO_IRRGCT_MODE_LAZY
static unsigned char gctpaused=1;
#endif

/*
 * Allocator function of the IRR Heap.
 *
 * This will return a pointer to a free memory area on the
 * heap of the current domain with @size bytes.
 *
 * This will throw an error if no suitable memory block can
 * be found.
 */
object_t* TRICORE_PSPR_SECTION keso_irr_alloc(class_id_t class_id, obj_size_t size, obj_size_t roff) {
	/* Used to pass the required size to the allocator in the one direction,
	 * and to pass back the pointer to the allocated memory in the other
	 * direction. If no memory block is found, KESO_EOFML is passed back.
	 */
	void *retval;
	object_t* obj;
	domain_t *domain;

	domain = &DOMAINDESC(KESO_CURRENT_DOMAIN);
	size = KESO_IRR_BYTES2SLOTS(size,domain->heap.irr.slotSize);
	retval = (void *) (unsigned int) size; /* Pass size to call back function */

	keso_irr_listwalker(domain, (int (*) (irr_listel_t*, unsigned char, irr_listel_t volatile **, domain_t*, void*)) keso_irr_alloccbfkt, (void *) &retval);

	/* TODO maybe we should return NULL here (and in all other heap specific
	 * allocs, too) and throw the out of memory error in allocObject */
#ifndef NO_WRITE
    if (retval == KESO_EOFML) keso_throw_error("out_of_memory\n");
#else
    if (retval == KESO_EOFML) keso_throw_error((char *) 0);
#endif
    
	DisableAllInterrupts();
	domain->heap.irr.sasls += size;
	domain->heap.irr.freeslots -= size;
	EnableAllInterrupts();

	/* Clear allocated memory, except first word with colorbit! */
	size *= domain->heap.irr.slotSize/sizeof(int);
	while(--size>0) ((int*)retval)[size]=0;

#ifdef KESO_IRRGCT_MODE_LAZY
	if(0<gctpaused) {
		gctpaused=0;
		ActivateTask(keso_irr_gc);
	}
#endif
	{
		obj = (object_t*)retval;
		unsigned char colorbit=*(unsigned char*)obj;
		*(int*) obj = 0;
		obj = (object_t*) ((object_t**)obj+roff);
		obj->class_id=class_id;
		obj->gcinfo = colorbit;

	}

	return obj;
}

/*
 * Allocator callback function for the listwalker.
 * The parameter *retval initially contains the required size in slots
 * for the memory block that needs to be found.
 * When a block large enough for the request is found, it is either split
 * or removed from the list, and a pointer to the allocated area is returned
 * in *retval.
 */
int TRICORE_PSPR_SECTION keso_irr_alloccbfkt(irr_listel_t *element, unsigned char mode,
		irr_listel_t volatile **prevNextPointer, domain_t *domain, void **retval) {
	unsigned short size;

	if(element == KESO_EOFML) { *retval=KESO_EOFML; return 0; }

	size = (unsigned short) (unsigned int) *retval;

	/***************************************************************************
	SPLITTING AN ELEMENT
	If the element's size is larger than the requested size, we split it by
	simply reducing its size.
	This subtraction must, however, happen atomically. There are the following
	scenarios that might happen:
	 (1) The interrupt might happen before writing back the substracted value,
	  but after having read the original value of size. In this case, we would
	  overwrite the modified value and hand out the same piece of memory twice.
	 (2) The interrupt might happen after writing back the modified value. In
	  this case, we would notice that somebody else performed a split, too, and
	  would not have a problem.
	Atomic substraction  would be possible with an compare-and-swap instruction,
	but the tricore architecture does not offer any instruction like this. We
	therefore have to disable the interrupts for a short period here.
	***************************************************************************/
	if(element->size > size) {
		unsigned short localsize;

		DisableAllInterrupts();
		localsize = element->size;
		if(localsize>size) {       /* enough memory to satisfy the request */
			localsize -= size;
			element->size = localsize;
			EnableAllInterrupts();
			/* Allocated memory is directly at element+new size */
			*retval =  (char*)element + (localsize * domain->heap.irr.slotSize);
			/* Pass allocation color in first byte of allocated memory.
			 * Always set bit0 so the object header looks differnt than
			 * references
			 */
			*((unsigned char*) *retval) = element->alloccolor | 1;
			return 0;
		}
		/* interrupting alloc stole our memory */
		EnableAllInterrupts();
	}

	/***************************************************************************
	REMOVAL OF AN EXACTLY FITTING ELEMENT
	If we encounter an element that exactly fits, we have to remove it. In order
	to do this, we have to do the following:
	 (1) Check that the element had no locks at all set by an interrupted alloc
	 (2) Remove the element in a critical section.
	While setting the next pointer of the previous element to the next pointer
	of this element, the next element may be removed. The next pointer of the
	prevElement would point to an object not in the queue anymore, and yet another
	interrupting alloc could run over the inconsistent queue before we can fix that.
	Therefore we need to disable Interrupts here, again.
	***************************************************************************/
	if(element->size==size && mode==0) {
		DisableAllInterrupts();
		if(element->size==size) {   /* make sure the element wasn't split */
			*prevNextPointer = element->next;
			EnableAllInterrupts();
			*retval = element;
			*((unsigned char*) *retval) = element->alloccolor | 1;
			return 0;
		}
		EnableAllInterrupts();
	}
	return 1;
}

/* Interrupt-friendly walk over the memory list, applying
 * a callback function to each list-element.
 *
 * The callback function is called for each element. When reaching
 * the end of the queue, it is called with KESO_EOFML as element.
 * The listwalker will terminate after that.
 *
 * The callback function shall return 1 if it wishes further processing
 * of the queue, or 0 if it is done and the run should be aborted.
 * An optional pointer parameter is passed through to the callback function and
 * may be used for parameters to the callback or return values from the
 * callback. When aborting, the mode of the previous element will be restored,
 * and so will the mode of the current element unless the next pointer of the
 * previous Element was changed by the callback or curElement was KESO_EOFML.
 * If the callback function updates *prevNextPointer but curElement remains
 * within the queue, the callback function must restore the original mode,
 * passed to the callback function as the mode parameter. After restoring the
 * original mode, the callback function cannot assume that the element will not
 * be removed and therefore should not perform any actions on the element after
 * restoring the original mode.
 *
 * Interrupts are enabled when calling the callback, however locking bits will
 * prevent the previous and the current Element from being removed by interrupting
 * instances.
 *
 * The callback function may remove the current element if the old mode does not
 * forbid it, but must ensure that there are no problems by overlapping listwalkers,
 * i.e. the removal must happen atomically.
 * If the callback function removes an element and requests
 * further walking over the queue, it will be called with the same prevElement
 * and the new element it is pointing to as curElement.
 *
 * A callback function may insert new elements, but only between the prevElement
 * and the curElement. The next pointer must always point to an element at a
 * higher address to keep the list sorted. When the
 * callback function inserts one or more elements and requests further queue
 * walking, it will be called with the same prevElement and the new element that
 * it now points to the next time and from thereon proceed normally, i.e. all
 * inserted elements will be passed to the callback function unless removed by
 * interrupting allocators in the meantime.
 *
 * In any case, if the callback function changes the next pointer of the
 * previous element, it MUST RESTORE THE ORIGINAL MODE OF CURELEMENT IF
 * CURELEMENT IS STILL A MEMBER OF THE LIST, since this will not be done
 * by the listwalker in order to not modify the memory of removed elements.
 * I.e. when inserting a new element and having prevNextPointer pointing to
 * the new element, the mode of curElement must be restored in the callback
 * function or the element will stay locked forever.
 */
void TRICORE_PSPR_SECTION keso_irr_listwalker(domain_t *domain,
		int (*elementCallback) (irr_listel_t*, unsigned char, irr_listel_t volatile **, domain_t*, void*),
		void *cbparam) {
	irr_listel_t volatile **prevNextPointer;
	irr_listel_t *curElement, *prevElement;
	irr_listel_t scratch;
	unsigned char pmode=0, mode;
	int cbreturn=1;

	prevNextPointer = &domain->heap.irr.freemem;
	prevElement = &scratch;           /* hack to make mode restoration work on first element */

	while(cbreturn) {
		/*****************************************************************************
		ADVANCING TO THE NEXT ELEMENT
		At this point, we have prevNextPointer pointing to the memory slot with the pointer
		to the next element in the list, which may also be KESO_EOFML.
		The memory slot of prevNextElement itself is protected and cannot be removed.
		Initially, this is due to the fact that the head pointer of the list is in the
		domain_desc and cannot be removed. The task is now to advance to the next
		element, and aquire a simple lock on it.
		The locking mode is saved in the mode member of the listelement. The object
		could be removed while aquiring the lock and writing mode to the already
		handed out object could cause inconsistencies. To avoid this, we must disable
		Interrupts here for a short portion of code.
		*****************************************************************************/
		DisableAllInterrupts();
		curElement = (irr_listel_t *) *prevNextPointer;
		if(curElement == KESO_EOFML) {
			EnableAllInterrupts();
			elementCallback(curElement, 1, prevNextPointer, domain, cbparam);
			break;
		}
		mode = curElement->mode;        /* save mode for later restoration */
		curElement->mode = mode | 1;    /* aquire a lock */
		EnableAllInterrupts();

		/* curElement is now locked and can be processed */
		cbreturn = elementCallback(curElement, mode, prevNextPointer, domain, cbparam);

		/* If prevElement still points to curElement
		 *   move previousElement to the current element and unlock previousElement
		 * else
		 *   keep prevElement and recall callback with the new element it points to
		 *
		 * curElement and prevElement are protected from being removed, however, the
		 * callback function is allowed to remove curElement if the previous mode
		 * allows it. The callback function is also allowed to insert new elements
		 * between prevElement and curElement. Both cases change the content of
		 * prevElement->next, and we call the callback again with the same
		 * prevElement and the new element that it points to.
		 * We do not need to care about restoring the mode of curElement here, since
		 * this is the task of the callback function.
		 */
		if(*prevNextPointer==curElement) {
			prevElement->mode = pmode;      /* restore original mode of previous element */
			prevElement = curElement;       /* advance one element */
			prevNextPointer = &curElement->next;
			pmode = mode;                   /* backup mode of new previous element */
		}
		/* in case we keep prevElement we also need to keep it locked */
	}
	/* If processing is aborted without changed prevNextPointer, prevElement
	 * was updated with curElement and we restore its mode here. */
	prevElement->mode = pmode;
}
