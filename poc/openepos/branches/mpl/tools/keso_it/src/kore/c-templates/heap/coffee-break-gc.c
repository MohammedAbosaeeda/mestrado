/*KESO--HEADER--KESO*/

/*KESO--CFILE--KESO*/
#include <keso_support.h> 

#ifdef KESO_OBJECTARRAYCLASS_AVAILABLE
#include "object_array.h"
#endif

#define REFONHEAP(__obj__) (heap_begin<=(__obj__)&&(__obj__)<heap_end)
#define COFFEE_HEAPDESC DOMAINDESC(DOMAINID()).heap.coffee
#define OBJCOLORED(__obj__) ((__obj__->gcinfo) == COFFEE_HEAPDESC.colorbit)
#define COLOROBJ(__obj__) (__obj__->gcinfo = COFFEE_HEAPDESC.colorbit)

#define COFFEE_ADDR2SLOT(_addr_) ((((unsigned int)_addr_)-(unsigned int)heap_begin)/COFFEE_HEAPDESC.slotSize)
#define COFFEE_SLOT2ADDR(_slot_) ((coffee_listel_t*)((char*)heap_begin + _slot_ * COFFEE_HEAPDESC.slotSize)); 

/* uncomment this if you expect a lot of free space */
#define KESO_SWEEP_WIN32 1
/* #define KESO_SWEEP_WIN24 1  */
/* #define KESO_SWEEP_WIN16 1  */
/* #define KESO_SWEEP_WIN8 1  */

/* uncomment this if you expect many living objects */
/* #define KESO_SWEEP_WIN32 1 */
/* #define KESO_SWEEP_WIN8_B 1  */

#ifndef KESO_ALLOC_CLEAR_OBJ_REFS
/* #define KESO_SWEEP_CLEAR_HEAP 1 */
#endif

/* #define COFFEEGC_SECTION TRICORE_PSPR_SECTION this is broken */
#define COFFEEGC_SECTION

/* Index of the next free slot on the stack (one above
 * the top element of the stack) */
static unsigned int sp=0;

/* very conservative estimation of the maximum number of objects that might be allocated */
static object_t *stack[KESO_COFFEE_MAXSLOTS];

/* internal variables */
static object_t *heap_begin, *heap_end;

/* index in domain[] to currently processed domainid,
 * this is NOT the domainid itself */
static unsigned char curdom = 0;

/* internal functions */
static void sweep();

static void pushObject(object_t*);
static void markObj(object_t*);
static void chooseDomain();
#ifdef KESO_NEED_FINALIZE
static void callFinalize(unsigned short bstart, unsigned short size);
#endif


/* This is the main GC function of the COFFEE Heap.
 * The task is chaining itself and run periodically,
 * collection garbage on the heap of one domain at
 * each run.
 */
TASK(keso_coffee_gc) {

	while (1) {
		unsigned int i;
		unsigned int heap_size = 0;

		/* choose the domain that will be scanned */
		chooseDomain();

		/* we stop the whole world for the mark phase */
		COFFEE_GC_LOCK();

		GC_LOG_START_EVENT(DOMAINID(), COFFEE_HEAPDESC.freeslots);

		/* Migrate Task to Target Domain */
		/* keso_coffee_gc has no task object !
		   KESO_CURRENT_TASK->_e_domain_id = DOMAINID(); 
		   */

		/* heap begin and end are e.g. used to determine if an object reference is
		 * on the domain heap or not */
		heap_size = COFFEE_HEAPDESC.numslots * COFFEE_HEAPDESC.slotSize;
		heap_begin = (object_t*) COFFEE_HEAPDESC.heap_top;
		heap_end = (object_t*) ((char*) heap_begin + heap_size);

		sp=0;
		COFFEE_HEAPDESC.colorbit ^= 2;

		/* record new allocations from this point */
		COFFEE_HEAPDESC.sasls = 0;

		/* scan stacks and tasks */
		i=KESO_MAX_TASK-1;
		while(i-->0) { /* skips INVALID_TASK */
			KESO_TASKCLASSTYPE* task;
			keso_stack_t* tstack;

			/* skip keso_coffee_gc task */
			if (i==keso_coffee_gc) continue;

			task = keso_task_index[i];
			if (task==NULL) continue;

			if (task!=NULL && task->_domain_id==DOMAINID()) 
				pushObject((object_t*)task);

			tstack = keso_stack_index[i];
			while (tstack!=NULL) {
				if (tstack->domain_id!=DOMAINID()) continue;
				KESO_STACK_WALK(tstack, pushObject);
				tstack=tstack->next;
			}
		}

		/* Scan statics */
#if KESO_NUM_STATIC_REFS>0
		for(i=0; i<KESO_NUM_STATIC_REFS; i++)
			pushObject((object_t*)STATICREF(DOMAINID())[i]);
#endif

		/* The root set is on the working stack now, scan and mark
		 * the objects until the working stack is empty */
		while (sp>0) {
			object_t *curobj;
			signed int curref;

			--sp;
			curobj = stack[sp];
			stack[sp]=NULL;

			if (!keso_isObjectArrayClass(curobj->class_id)) {
				curref = -(signed int) CLS_ROFF(curobj->class_id);
				while (curref<0) pushObject( ((object_t**)curobj)[curref++] );
#ifdef KESO_OBJECTARRAYCLASS_AVAILABLE 
			} else {
				curref = ((object_array_t*)curobj)->size;
				while (curref-->0) pushObject( ((object_array_t*)curobj)->data[curref] );
#endif
			}

			/* Finally, mark the object's bits in the bitmap */
			markObj(curobj);
		}

		GC_LOG_MARK_END_EVENT(DOMAINID(), COFFEE_HEAPDESC.freeslots);

		/* sweep heap */
		sweep();

		GC_LOG_SWEEP_END_EVENT(DOMAINID(), COFFEE_HEAPDESC.freeslots);

		/* we wake up the whole world */

		COFFEE_GC_UNLOCK();

		KESO_SEND_GCRUN_EVENTS();

		/* ChainTask(keso_coffee_gc); */
	}
}

/* Choose the domain that needs garbage collection
 * the most of all managed domains, determined by
 * a metric called the "need" here.
 *
 * The need of a domain for a GC is determined by
 * the slots allocated since the last garbage collection (sasls)
 * compared to the remaining space. The remaining
 * space should not be less than the sasls plus a grace
 * area of 25% of the heap size. Thus, the "need" is calculated
 * freeslots - sasls - (heapsize>>2).
 * The function goes RoundRobin through the domain array and chooses
 * the first found domain that violates the above criterium (need<0). By
 * doing this instead of simply choosing the domain with the most severe
 * need, we can assure that all domains that have a need actually receive
 * a garbage collection (fairness to some degree).
 * If every domain has need>=0, the domain with the lowest need is taken.
 */
static void chooseDomain() {
	signed int need;
	domain_t *dom;
	unsigned char i=curdom;
	
	while(1) {
		if(++i >= MANAGEDDOMAINS) i=0;

		dom = &DOMAINDESC(domains[i]);

		need = COFFEE_HEAPDESC.freeslots-
			COFFEE_HEAPDESC.sasls-
			(COFFEE_HEAPDESC.numslots>>2);

		/* if need<0, this domain receives a GC treatment */
		if(need<0) { curdom=i; return; }

		/* The need of every domain is >0, sleep */
		if(i == curdom) {
			gctpaused=1;
			TerminateTask();
		}
	}
}

/* Marks the bits of the slots occupied by @obj.
 * If obj is an object outside the domain heap,
 * nothing is done (immortal objects).
 */
static void COFFEEGC_SECTION markObj(object_t *obj) {
	unsigned int slot,bm_int,bm_bit, size;

	if (!REFONHEAP(obj)) return; 

	size = keso_objSize(COFFEE_HEAPDESC.slotSize,obj);
	slot=COFFEE_ADDR2SLOT(((object_t**)obj-CLS_ROFF(obj->class_id)));
	while (size--) {
		bm_int = slot >> 5;
		bm_bit = 1 << ((slot) & 0x1f);
		bitmap[bm_int] |= bm_bit;
		slot++;
	}
}

/* Scan a block that is about to be freed for objects and
 * call the finalize() method of the found objects
 */
#ifdef KESO_NEED_FINALIZE
static void COFFEEGC_SECTION callFinalize(unsigned short bstart, unsigned short size) {
	object_t **blockbegin = (object_t**) ((char*)heap_begin + 
			( (unsigned int) bstart * (unsigned int) COFFEE_HEAPDESC.slotSize));
	unsigned short refsize = (unsigned short)
		(((unsigned int) size * (unsigned int) COFFEE_HEAPDESC.slotSize)/(unsigned int) sizeof(object_t*));
	unsigned short index = 0;
	unsigned short objbegin=index;
	object_t *obj;

	while(index < refsize) {
		if( (((unsigned int) blockbegin[index])&1) == 0 ) { index++; continue; }
	
		obj = (object_t*)(blockbegin+index);

		ASSERTCLASSID(obj->class_id);

		KESO_ASSERT(CLS_ROFF(obj->class_id)==(index-objbegin));

		/* call finalizer
		 *
		 * ISSUES
		 *  - getTaskID will return the task object of the coffee-gc Task. This object
		 *    does not contain any fields modifyable from the Java application.
		 *  - WaitEvent should not be allowed in a finalize() method. If it is used,
		 *    it will fail anyway.
		 */
		KESO_INVOKE_FINALIZE(obj)(obj);

		/* position behind found object */
		objbegin += keso_objSize(COFFEE_HEAPDESC.slotSize,obj) * (COFFEE_HEAPDESC.slotSize/sizeof(object_t*));
		index = objbegin;
	}

	KESO_ASSERT(index == refsize && index == objbegin);
}
#endif

/* Push a white object on the stack.
 * If the object is not white, this function
 * will do nothing.
 *
 * THIS IS THE ONLY PLACE WHERE OBJECTS ARE PUSHED
 * ON THE WORKING STACK.
 */
static void COFFEEGC_SECTION pushObject(object_t *obj) {
	ASSERTOBJ(obj);
	if(obj==NULL) return;
	if(OBJCOLORED(obj)) return; 

	COLOROBJ(obj);
	stack[sp++] = obj;
}

/**
 * sweep()
 *
 * - scan bitmap for free slots
 * - create the a new free list 
 * - clear the bitmap for later reuse
 */   
static void COFFEEGC_SECTION sweep() {
	coffee_listel_t **prev_free;
	unsigned int cur_slot,bm_int,bm_bit;

	/* reset free list */
	COFFEE_HEAPDESC.freemem = NULL;
	COFFEE_HEAPDESC.freeslots=0;

	prev_free = &COFFEE_HEAPDESC.freemem;
	for (cur_slot=0,bm_int=0,bm_bit=1;cur_slot<COFFEE_HEAPDESC.numslots;) {
		if (!(bitmap[bm_int] & bm_bit)) {
			/* current slot is free -> add to free list */
			int size = cur_slot;
			*prev_free = COFFEE_SLOT2ADDR(cur_slot);

			do {
				/* join all free slots */
#ifdef KESO_SWEEP_WIN32
				if (bm_bit==1 && bitmap[bm_int]==0) {
					/* fast path */
					/* bitmap[bm_int]=0; bitmap is allready cleared! */
					cur_slot+=32;
					bm_int++;
				} else
#endif
#ifdef KESO_SWEEP_WIN24
				if (bm_bit==1 && ((bitmap[bm_int] & 0x00ffffff)==0)) {
					cur_slot+=24;
					bm_bit = 1 << 24;
				} else if (bm_bit==(1<<8) && ((bitmap[bm_int] & 0xffffff00)==0)) {
					bitmap[bm_int]=0; 
					cur_slot+=24;
					bm_bit = 1;
				} else
#endif
#ifdef KESO_SWEEP_WIN16
				if (bm_bit==1 && ((bitmap[bm_int] & 0x0000ffff)==0)) {
					cur_slot+=16;
					bm_bit = 1 << 16;
				} else if (bm_bit==(1<<8) && ((bitmap[bm_int] & 0x00ffff00)==0)) {
					cur_slot+=16;
					bm_bit = 1 << 24;
				} else if (bm_bit==(1<<16) && ((bitmap[bm_int] & 0xffff0000)==0)) {
					bitmap[bm_int]=0; 
					cur_slot+=16;
					bm_bit = 1;
				} else
#endif
#ifdef KESO_SWEEP_WIN8
				if (bm_bit==1 && ((bitmap[bm_int] & 0x000000ff)==0)) {
					cur_slot+=8;
					bm_bit = 1 << 8;
				} else if (bm_bit==(1<<8) && ((bitmap[bm_int] & 0x0000ff00)==0)) {
					cur_slot+=8;
					bm_bit = 1 << 16;
				} else if (bm_bit==(1<<16) && ((bitmap[bm_int] & 0x00ff0000)==0)) {
					cur_slot+=8;
					bm_bit = 1 << 24;
				} else if (bm_bit==(1<<24) && ((bitmap[bm_int] & 0xff000000)==0)) {
					bitmap[bm_int]=0; 
					cur_slot+=8;
					bm_bit = 1;
				} else
#endif
				{
					if (bm_bit & (1<<31)) bitmap[bm_int]=0;
					cur_slot++;
					bm_int = cur_slot >> 5;
					bm_bit = 1 << (cur_slot & 0x1f);
				}
			} while (!(bitmap[bm_int] & bm_bit) && cur_slot<COFFEE_HEAPDESC.numslots); 
			size = cur_slot - size;

			/* create free list header */
			(*prev_free)->size = size;
			(*prev_free)->next = NULL;

#ifdef KESO_SWEEP_CLEAR_HEAP
			{
				int i;
				int* freemem;
				freemem = (char*)*prev_free;
				for (i=(8/sizeof(int));i<(size*COFFEE_HEAPDESC.slotSize/sizeof(int));i++)
					freemem[i]=0;
			}
#endif

			prev_free = &((*prev_free)->next);
			COFFEE_HEAPDESC.freeslots+=size;
		} else {
			/* current slot is NOT free */
#ifdef KESO_SWEEP_WIN32_B
			if (bm_bit==1 && bitmap[bm_int]==0xffffffff) {
				/* fast path */
				bitmap[bm_int]=0;
				cur_slot+=32;
				bm_int++;
			} else
#endif
#ifdef KESO_SWEEP_WIN8_B
			if (bm_bit==1 && ((bitmap[bm_int] & 0x000000ff)==0x000000ff)) {
				cur_slot+=8;
				bm_bit = 1 << 8;
			} else if (bm_bit==(1<<8) && ((bitmap[bm_int] & 0x0000ff00)==0x0000ff00)) {
				cur_slot+=8;
				bm_bit = 1 << 16;
			} else if (bm_bit==(1<<16) && ((bitmap[bm_int] & 0x00ff0000)==0x00ff0000)) {
				cur_slot+=8;
				bm_bit = 1 << 24;
			} else if (bm_bit==(1<<24) && ((bitmap[bm_int] & 0xff000000)==0xff000000)) {
				bitmap[bm_int]=0; 
				cur_slot+=8;
				bm_bit = 1;
			} else
#endif
			{
				if (bm_bit & (1<<31)) bitmap[bm_int]=0;
				cur_slot++;
				bm_int = cur_slot >> 5;
				bm_bit = 1 << (cur_slot & 0x1f);
			}
		}
	}
}
