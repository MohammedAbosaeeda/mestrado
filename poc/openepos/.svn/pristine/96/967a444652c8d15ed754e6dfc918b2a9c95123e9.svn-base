/*KESO--HEADER--KESO*/

#define GC_LOG_INFO_PARAMETER

/*KESO--CFILE--KESO*/

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>

#define GC_LOG_INFO (0)

#define GC_LOG_ALLOC_EVENT(_tid_, _aid_, _class_id_, _freespace_, _size_) gc_log_alloc(_tid_, _aid_, _class_id_, _freespace_, _size_)

#define GC_LOG_START_EVENT(_did_, _freespace_) gc_log('s', _did_, _freespace_)
#define GC_LOG_MARK_END_EVENT(_did_, _freespace_) gc_log('m', _did_, _freespace_)
#define GC_LOG_SWEEP_END_EVENT(_did_, _freespace_) gc_log('e', _did_, _freespace_)

static FILE *lfd = 0;
static unsigned long long time_stamp_s =0;
static unsigned long total_alloc = 0;

static unsigned long rd_time() {
	unsigned long long cts;
	__asm__ __volatile__ ("rdtsc": "=A" (cts) );
	return (unsigned long)(cts>>10);
}

static void gc_log_init() {
	lfd = fopen("gc.log", "w");
	time_stamp_s = rd_time();
}

void gc_log_alloc(int tid, int aid, class_id_t class_id, unsigned int free_space, obj_size_t size) {
	unsigned long ts;

	if (lfd<0) return;
	if (!lfd) gc_log_init();

	ts = rd_time() - time_stamp_s;

	total_alloc += size;
	/* format "event:time:total:free:size:domain id:thread id:position:class id" */
	fprintf(lfd, "a:%u:%u:%u:%u:", ts, total_alloc, free_space, size);
	fprintf(lfd, "0:%u:%u:%u\n", tid, aid, class_id);
}

void gc_log(char mark, int did, unsigned int free_space) {
	unsigned long ts;

	if (lfd<0) return;
	if (!lfd) gc_log_init();
	
	ts = rd_time() - time_stamp_s;

	fprintf(lfd, "%c:%u:%u:%u:0:", mark, ts, total_alloc, free_space);
	fprintf(lfd, "%u:0:0:0\n", did);

	if (mark=='e') {
		fflush(lfd);
		fsync(fileno(lfd));
	}
}

