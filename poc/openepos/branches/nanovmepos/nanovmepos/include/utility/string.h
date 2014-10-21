// EPOS String Utility Declarations
// This work is licensed under the EPOS Software License v1.0.
// A copy of this license is available at the EPOS system source tree root.
// A copy of this license is also available online at:
// http://epos.lisha.ufsc.br/EPOS+Software+License+v1.0
// Note that EPOS Software License applies to both source code and executables.
#ifndef __string_h
#define __string_h
extern "C" {

    int memcmp(const void * m1, const void * m2, unsigned int n);
    void * memcpy(void * d, const void * s, unsigned int n);
    void * memset(void * m, int c, unsigned int n);
    void * memchr(const void * m, int c, unsigned int n);
    int strcmp(const char * s1, const char * s2);
    int strncmp(const char * s1, const char * s2, unsigned int n);
    char * strcpy(char * d, const char * s);
    char * strncpy(char * d, const char * s,unsigned int n);
    char * strcat(char * d, const char * s);
    char * strchr(const char * s, int c);
    char * strrchr(const char * s, int c);
    unsigned int strlen(const char * s);
    long atol(const char *s);
    char *itoa(int v, char *s);
    int utoa(unsigned long v,char * d);
}

#endif
