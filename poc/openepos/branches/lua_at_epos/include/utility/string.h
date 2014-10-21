// EPOS-- String Utility Declarations

#ifndef __string_h
#define __string_h

int memcmp(const void * a1, const void * a2, unsigned int size);
void * memcpy(void * to, const void * from, unsigned int size);
void * memset(void * block, int c, unsigned int size);
int strcmp(const char * s1, const char * s2);
int strncmp(const char * s1, const char * s2, unsigned int size);
char * strcpy(char * to, const char * from);
char * strncpy(char * to, const char * from, unsigned int size);
unsigned int strlen(const char * s);

char * strstr (const char *haystack, const char *needle);
char * strchr (const char *string, int c);
char * strrchr (const char *string, int c);
char * strcat (char * to, const char * from);
unsigned int strcspn (const char *string, const char *stopset);
char * strncat (char * to, const char * from, unsigned int size);
void * memchr (const void *block, int c, unsigned int size);
char * strpbrk (const char *string, const char *stopset);

/*
inline int memcmp(void * d, const void * s, unsigned int n) {
    return __builtin_memcmp(d, s, n);
}
inline void * memcpy(void * d, const void * s, unsigned int n) {
    return __builtin_memcpy(d, s, n);
}
inline void * memset(void * d, int c, unsigned int n) {
    return __builtin_memset(d, c, n);
}
inline int strcmp(char * d, const char * s) {
    return __builtin_strcmp(d, s);
}
inline int strncmp(char * d, const char * s, unsigned int n) {
    return __builtin_memcmp(d, s, n);
}
inline char * strcpy(char * d, const char * s) {
    return __builtin_strcpy(d, s);
}
inline char * strncpy(char * d, const char * s, unsigned int n) {
    return (char *)__builtin_memcpy(d, s, n);
}
inline unsigned int strlen(const char * s) {
    return __builtin_strlen(s);
}
*/

#endif
