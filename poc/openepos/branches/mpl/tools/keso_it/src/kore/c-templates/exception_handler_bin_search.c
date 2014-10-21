/*KESO--HEADER--KESO*/

void keso_throw_exception_l2(object_t* exception) NORETURN ;

/*KESO--CFILE--KESO*/

/**
 * The exception table have to be created with mkexptab.pl.
 *
 * ex. mkexptab.pl objdump keso_main_g
 *
 */
#include "exception_table.inc"

/*
 * binary search in the exception table.
 * 
 * O(1+log2N) + O(M) 
 */
#ifdef BIN_SEARCH
static void* keso_lookup_handler(void* return_addr, class_id_t cls) {
	class_id_t exp_type;
	exp_ndx_t mid;
	exp_ndx_t left = 0;
	exp_ndx_t right = (EXP_TABLE_SIZE-1);

	if (return_addr==0) return (void*)-1;

	while (left <= right) {
		mid = (left+right) >> 1;
retry_range:
		if (mid>=EXP_TABLE_SIZE) break;
		if (return_addr>=(exception_table[mid].start)) {
			if (return_addr>=(exception_table[mid].end)) {
				left = mid + 1;
				continue;
			} 
			/* (return_addr < (exception_table[mid].end)) */
			/* check exception type */
			if (!((exp_type=exception_table[mid].cls_id)<=cls&&cls<=CLASS(exp_type).type_range)) {
				mid++;
				goto retry_range;
			}
			/* found handler */
			return exception_table[mid].handler; 
		} else { 
			/* (return_addr < (exception_table[mid].start)) */
			right = mid - 1;
		}
	}

	/* not found */
	return NULL; 
}
#else 
static void* keso_lookup_handler(void* return_addr, class_id_t cls) {
	class_id_t exp_type;
	exp_ndx_t i;

	if (return_addr==0) return (void*)-1;

	for (i=0;i<EXP_TABLE_SIZE;i++) {
		if ((exception_table[i].start)<=return_addr&&return_addr<(exception_table[i].end)) {
			/* check exception type */
			if (!((exp_type=exception_table[i].cls_id)<=cls&&cls<=CLASS(exp_type).type_range)) {
				continue;	
			}
			/* found handler */
			return exception_table[i].handler; 
		}
	}

	/* not found */
	return NULL; 
}
#endif

#ifndef NO_WRITE
__const__ static char panic[] = "panic: uncaught exception\n";
#endif

typedef unsigned long addr_t;

void keso_throw_exception_l2(object_t* exception) {
	addr_t *sp, *ebp, *eip;
	void* handler;
	class_id_t exp_cls = exception->class_id;

	/* search frame and exception handler */
	/* ebp = (addr_t*)&exception + 1; */
	ebp = (addr_t *) & exception - 2;

	/* skip local frame */
	ebp = (addr_t *) *ebp;

	do {
		sp = ebp;
		ebp = (addr_t *) *sp++;
		eip = (addr_t *) *sp++;
	} while ((handler=keso_lookup_handler(eip,exp_cls))==NULL);

	if (handler==-1) {
		/* TODO: uncaught exception -> global exception handler */
#ifndef NO_WRITE
		write(1,panic,sizeof(panic));
#endif
		while (1) { __asm__ __volatile__ ("nop"); };
	}

/*
TODO:
	  movl    4(%esp),%ebp    == __builtin_frame_address(level+1)
	  movl    12(%esp),%eax   == handler
	  movl    8(%esp),%esp    == __builtin_frame_address(level) -/+ method parameters
	  jmp     *%eax
FIXME: update stack pointer !
*/
	__asm__ __volatile__ ("movl %0, %%eax"::"r"(eip));
	__asm__ __volatile__ ("movl %0, %%ebp"::"r"(ebp));
	__asm__ __volatile__ ("jmp *%eax");

	/* never reached */
	while (1) { __asm__ __volatile__ ("nop"); };
}

