#ifndef __WRAPPER_HH
#define __WRAPPER_HH

#ifdef __cplusplus
extern "C" {
#endif

#define NULL 0

typedef unsigned int size_t;

typedef unsigned char uint8_t;
typedef unsigned short uint16_t;
typedef unsigned int uint32_t;
typedef unsigned long uint64_t;

typedef void WFILE;
typedef void* wpthread_mutex_t;
typedef void* wsem_t;

typedef void wpthread_t;

int mux_main();

WFILE * wfopen(const char * path, const char * mode);

size_t wfread(void * ptr, size_t size, size_t nmemb, WFILE * stream);
size_t wfread_searching(void * ptr, size_t size, size_t nmemb, WFILE * stream, char needle);

size_t wfwrite(const void * ptr, size_t size, size_t nmemb, WFILE * stream);

void wprintf(char * ptr);
void wdie(char * ptr);

void * wmalloc(size_t size);
void wfree(void *  ptr);
void * wmemset(void *s, int c, size_t n);
void * wmemcpy(void *dest, const void *src, size_t n);

char * wstrncpy(char *dest, const char *src, size_t n);
int wstrncmp(const char *dest, const char *src, size_t n);

void wusleep(int t);

void wpthread_mutex_init(wpthread_mutex_t* m);
void wpthread_mutex_lock(wpthread_mutex_t* m);
void wpthread_mutex_unlock(wpthread_mutex_t* m);

void wsem_init(wsem_t* s, unsigned int v);
void wsem_post(wsem_t* s);
void wsem_wait(wsem_t* s);

void wpthread_create(int (*entry)(void *), void * arg);

#ifdef __cplusplus
};
#endif

#endif

