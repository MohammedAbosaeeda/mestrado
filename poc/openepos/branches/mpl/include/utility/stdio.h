#ifndef stdio_h
#define stdio_h

#define		BUFSIZ		8192

int nprintf(char *str, const char *format, int n);
int sprintf(char* s, const char* fmt, const char ** va_list);

#endif
