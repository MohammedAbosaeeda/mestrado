/*KESO--HEADER--KESO*/

/**
 * THIS IS A TINY SHARED MEMORY IMPLEMENTATION
 */

#define KESO_NOBLOCK ((unsigned int)0)
#define KESO_BLOCK ((unsigned int)-1)
#define KESO_UNPRED ((unsigned int)-2)

#define KESO_HAS_DYN_SHARED_MEMORY 

void *keso_alloc_shared_memory(jint size, jint timeout);
#ifdef KESO_MEM_NO_GC
#define keso_require_shared_memory(_mem_)
#define keso_release_shared_memory(_mem_, _size_)
#else 
void keso_require_shared_memory(void *mem);
void keso_release_shared_memory(void *mem, jint size);
#endif
jboolean keso_is_shared_memory(void *mem);

/*KESO--CFILE--KESO*/

/**
 * THIS IS A TINY SHARED MEMORY IMPLEMENTATION
 *
 * - IT IS NOT MULTI REENTRANT!
 */

static char keso_shared_memory[KESO_SHARED_MEM_SIZE];

#ifdef KESO_MEM_NO_GC
static char* keso_shared_free = ((void*)-1);
#else 
typedef struct keso_shared_s {
	unsigned int  rcount;
	struct keso_shared_s* next;
} keso_shared_t;

#define CHUNKS(_size_) ((((_size_)+sizeof(keso_shared_t)-1) / sizeof(keso_shared_t))+1)
static keso_shared_t* keso_shared_free    = ((void*)-1); 
#endif

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
 */ 
void *keso_alloc_shared_memory(jint size, jint timeout) {
#ifdef KESO_MEM_NO_GC
	void* new_ptr;

	if (keso_shared_free==(void*)-1) {
		/* init memory */
		/* TODO: do this in the start up hook */
		keso_shared_free = keso_shared_memory;
	}

	new_ptr=keso_shared_free;
	keso_shared_free+=size;

	return new_ptr;
#else 
	keso_shared_t **fptr, *nptr;
	unsigned int rsize, i;

	/* we allocate the memory allways in chunks */ 
	rsize = CHUNKS(size) + 1;

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

		/* from this point on we are using
		   rcount as reference counter */
		for (i=0;i<rsize;i++) { nptr[i].rcount=0; nptr[i].next=NULL; }
		nptr->rcount = 1; 
		nptr = nptr + 1;
	}

	return (void*)nptr;
#endif
}

/**
 * check if the memory array is part of the shared memory
 */
jboolean keso_is_shared_memory(void *mem) {
	return (mem>keso_shared_memory && mem<(keso_shared_memory+KESO_SHARED_MEM_SIZE));
}

#ifndef KESO_MEM_NO_GC
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
	keso_shared_t **fptr;
	keso_shared_t *next;
	keso_shared_t *ptr = ((keso_shared_t*)mem)-1;

	if (--(ptr->rcount)) return;

	ptr->rcount=CHUNKS(size);

	fptr=&keso_shared_free;
	if (*fptr==NULL) {
		*fptr=ptr;
		return;
	} 
	
	/* find perfect place */
	while ((*fptr)->next!=NULL && (*fptr)->next<ptr) fptr = &((*fptr)->next);

	/* ASSERT(ptr!=NULL && *fptr!=NULL); */

	next=(*fptr)->next;
	if (((*fptr)+(*fptr)->rcount)==ptr) {
		/* join */
		(*fptr)->rcount+=ptr->rcount;
		ptr=*fptr;
	} else {
		/* link */
		(*fptr)->next=ptr;
	}

	if ((ptr+ptr->rcount)==next) { 
		/* ptr!=NULL -> next!=NULL */
		/* join */
		ptr->rcount+=next->rcount;
	} else {
		/* link */
		ptr->next = next;
	}
}
#endif
