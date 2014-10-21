/*KESO--HEADER--KESO*/

#define KESO_NOBLOCK ((unsigned int)0)
#define KESO_BLOCK ((unsigned int)-1)
#define KESO_UNPRED ((unsigned int)-2)

#define KESO_HAS_DYN_SHARED_MEMORY 

void *keso_alloc_shared_memory(jint size, jint timeout);
void keso_require_shared_memory(void *mem);
void keso_release_shared_memory(void *mem, jint size);
jboolean keso_is_shared_memory(void *mem);

/*KESO--CFILE--KESO*/

static jint keso_shared_wait(jint amount, jint timeout);
static jint keso_shared_gc_step();

static char keso_shared_memory[KESO_SHARED_MEM_SIZE];

typedef struct keso_shared_s {
	unsigned int  rcount;
	struct keso_shared_s* next;
} keso_shared_t;

#define CHUNKS(_size_) ((((_size_)+sizeof(keso_shared_t)-1) / sizeof(keso_shared_t))+1)
static keso_shared_t* keso_shared_free    = ((void*)-1); 
static keso_shared_t* keso_shared_garbage = ((void*)0); 

/**
 * This function allocates a shared memory block of @size from the system.
 * If no suitable memory block is available it might wait for @timeout seconds.
 *
 * This function has a maximum complexity O(n) to find a free block and to
 * defragment the heap. Where n depends on the fragmentation of the memory.
 * The max. value of n is as followed
 *    n_max = (total memory size / 2*sizeof(keso_shared_t)).
 *
 * Therefore do not allocate shared memory in a time critical
 * execution path.
 *
 * TODO: implementation of timeout
 */ 
void *keso_alloc_shared_memory(jint size, jint timeout) {
	keso_shared_t **fptr, *nptr;
	unsigned int rsize, i;

	/* we allocate the memory allways in chunks */ 
	rsize = CHUNKS(size) + 1;

#ifdef KESO_DEBUG
	if (rsize>=KESO_SHARED_MEM_SIZE/sizeof(keso_shared_t)) return NULL;
#endif

	do {
		LOCK_SHARED_MEMORY();

		if (keso_shared_free==(void*)-1) {
			/* init memory */
			/* TODO: do this in the start up hook */
			keso_shared_free = (keso_shared_t*)keso_shared_memory;
			keso_shared_free->rcount = (KESO_SHARED_MEM_SIZE/sizeof(keso_shared_t));
			keso_shared_free->next = NULL;
		}

		/* find free space (first fit) */
		for (fptr = &keso_shared_free; (*fptr)!=NULL && (*fptr)->rcount<rsize; fptr = &((*fptr)->next));
		nptr = *fptr;

		if (nptr!=NULL) {
			if (nptr->rcount==rsize) {
				/* unlink memory */
				*fptr = nptr->next; 
			} else {
				/* split memory */
				*fptr = nptr + rsize;
				(*fptr)->next = nptr->next;
				(*fptr)->rcount = nptr->rcount - rsize;
			}

			UNLOCK_SHARED_MEMORY();

			/* from this point on we are using
			   rcount as reference counter */
			for (i=0;i<rsize;i++) { nptr[i].rcount=0; nptr[i].next=NULL; }
			nptr->rcount = 1; 
			nptr = nptr + 1;

		} else {
			UNLOCK_SHARED_MEMORY();
		}

		/* if no space available wait or/and defrag memory */
	} while (nptr==NULL && keso_shared_wait(rsize, timeout));

	return (void*)nptr;
}

/**
 * check if the memory array is part of the shared memory
 */
jboolean keso_is_shared_memory(void *mem) {
	return (mem>keso_shared_memory && mem<(keso_shared_memory+KESO_SHARED_MEM_SIZE));
}

/**
 * increase the reference counter
 *
 * This function have to be called if the reference is 
 * stored in a new memory proxy object.
 */
void keso_require_shared_memory(void *mem) {
	((keso_shared_t*)mem)[-1].rcount++;
}

/**
 * decrease the reference counter and mark memory as free if
 * the counter is zero.
 *
 * This function have to be called if a memory object is
 * finalized by the garbage collector.
 */
void keso_release_shared_memory(void *mem, jint size) {
	keso_shared_t *ptr = ((keso_shared_t*)mem)-1;

	if (--(ptr->rcount)) return;

	ptr->rcount=CHUNKS(size);

	LOCK_SHARED_MEMORY_GQ();

	ptr->next=keso_shared_garbage;
	keso_shared_garbage=mem;

	UNLOCK_SHARED_MEMORY_GQ();
}

/* TODO: implement timeout, implement wait */
static jint keso_shared_wait(jint amount, jint timeout) {
	jint done;

	if (timeout==KESO_NOBLOCK) return 0;

	while ((done=keso_shared_gc_step())>0 && (done<amount)) ;

	return done; 
}

static jint keso_shared_gc_step() {
	keso_shared_t *ptr;
	keso_shared_t **fptr;
	keso_shared_t *next;

	LOCK_SHARED_MEMORY_GQ();
	ptr = keso_shared_garbage;
	if (ptr!=NULL) keso_shared_garbage = ptr->next;
	UNLOCK_SHARED_MEMORY_GQ();

	if (ptr==NULL) return 0;

	LOCK_SHARED_MEMORY();

	fptr=&keso_shared_free;
	if (*fptr==NULL) {
		*fptr=ptr;
	} else {
		/* find perfect place */
		while ((*fptr)->next!=NULL && (*fptr)->next<ptr) fptr = &((*fptr)->next);

		/* ASSERT(ptr!=NULL && *fptr!=NULL); */

		next=(*fptr)->next;
		if (((*fptr)+(*fptr)->rcount)==ptr) {
			/* append ptr */
			(*fptr)->rcount+=ptr->rcount;
			ptr=*fptr;
		} else {
			/* link */
			(*fptr)->next=ptr;
		}

		if (ptr+ptr->rcount==next) { 
			/* ptr!=NULL -> next!=NULL */
			ptr->rcount+=next->rcount;
		} else {
			ptr->next = next;
		}
	}

	UNLOCK_SHARED_MEMORY();

	return ptr->rcount;
}
