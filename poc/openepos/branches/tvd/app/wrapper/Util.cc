#include "../mux2/includes/MuxWrapper.h"

#include <utility/string.h>
#include <alarm.h>
#include <thread.h>

__USING_SYS

OStream util_o;

void wprintf(char * ptr) { util_o << ptr; }

void wdie(char * ptr) {
        wprintf(ptr);
        CPU::halt();
}

void * wmalloc(size_t size) { return malloc(size); }
void wfree(void * ptr) { free(ptr); }

void * wmemset(void *s, int c, size_t n) { return memset(s, c, n); }
void * wmemcpy(void *dest, const void *src, size_t n) { return memcpy(dest, src, n); }
char * wstrncpy(char *dest, const char *src, size_t n) { return strncpy(dest, src, n); }
int wstrncmp(const char *dest, const char *src, size_t n) { return strncmp(dest, src, n); }

void wusleep(int t) {

        Alarm::delay(t);


//        Thread::yield();

}
