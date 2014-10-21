/*KESO--HEADER--KESO*/

#define GC_LOG_INFO_PARAMETER

/*KESO--CFILE--KESO*/

#define GC_LOG_INFO (0)

#define GC_LOG_ALLOC_EVENT(_tid_, _aid_, _class_id_, _freespace_, _size_) gc_log(0,_tid_, _class_id_, _size_, _freespace_)

#define GC_LOG_START_EVENT(_did_, _freespace_) gc_log(1, _did_, 0, 0, _freespace_)
#define GC_LOG_MARK_END_EVENT(_did_, _freespace_) gc_log(2, _did_, 0, 0, _freespace_)
#define GC_LOG_SWEEP_END_EVENT(_did_, _freespace_) gc_log(3, _did_, 0, 0, _freespace_)

#ifndef GC_LOG_SIZE
/* this is too big "time-out, cannot set registers" */
/*#define GC_LOG_SIZE (1024*1024) */
/* #define GC_LOG_SIZE (64*1024) */
#define GC_LOG_SIZE (4*1024) 
#endif

typedef struct {
	unsigned char mark;
	unsigned int who; /* tid or did */
	class_id_t class_id;
	obj_size_t size; 
	unsigned int free;
	/* int aid; */
} log_element_t;

static log_element_t log[GC_LOG_SIZE];
static unsigned int log_pos = 0;

void gc_log(char mark, int id, class_id_t class_id, obj_size_t size, unsigned int free_space) {
	__asm__ __volatile__ (".global $time_mark_gc_log$");
	__asm__ __volatile__ ("$time_mark_gc_log$:");

	log[log_pos].mark = mark;
	log[log_pos].who = id;
	log[log_pos].class_id = class_id;
	log[log_pos].size = size;
	log[log_pos].free = free_space;

	log_pos++;
	if (log_pos>=GC_LOG_SIZE) {
		__asm__ __volatile__ (".global gc_log_overrun");
		__asm__ __volatile__ ("gc_log_overrun:");
		log_pos=0;
	}
}


