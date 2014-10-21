#ifndef _UTIL_H_
#define _UTIL_H_
#include <stdarg.h>

Display disp;

#ifdef _EPOS_
void usleep(long x){
	for(int i=0;i<10;i++)
		for(int j=0;j<x;j++);
}
#endif

void printf(char *str, ...);
template <class T>
T isqrt(T x){ /// Newton-Raphson method for square root calculation
	if(x==1 || x==0) return x;
#ifndef _EPOS_
	if(x<0){ printf("raiz(%l) ??? te liga!\n", x); _exit(3); }
#endif
	int i;
	T xi = x/2, xa = 0xffff;
	for(i=0; i<25; i++){
		xi = (xi+x/xi) / 2;
		if(xi == xa) break;
		xa = xi;
	}
	return xi;
}

template <class T>
inline int x2(T x){
	return (x * x);
}

template <class T>
T distance(T x1, T y1, T x_2, T y2){
	return isqrt(x2(x1-x_2)+x2(y1-y2));
}

template <class T>
inline int min(T a, T b){
	return ( a<b ? a : b );
}

template <class T>
inline int max(T a, T b){
	return ( a>b ? a : b );
}

template <class T>
inline int abs(T a){
	return ( a < 0 ? -a : a );
}

template <class T>
void puti(T x){
	char n[10];
	int i;
	if(x==0) { disp.putc('0'); }
	if(x<0){ x=-x; disp.putc('-'); }
	for(i=0;x>0;x/=10) n[i++]='0'+x%10;
	while(i>0)         disp.putc(n[--i]);
}


int strlen(char *s){
	int tam = 0;
	while(*s++) tam++;
	return tam;
}

void putsss(char *s, int i, int f){
	while(i<=f) disp.putc(s[i++]);
}

void printf(char *str, ...){
	va_list ap;
	va_start(ap, str);
	int init = 0, size = strlen(str);
	for(int i=0;i<size;i++)
		if(str[i] == '%'){
			putsss(str,init,i-1);
			switch(str[++i]){
				case 'c':
					disp.putc(va_arg(ap, int));
					break;
				case 'd':
					puti(va_arg(ap, int));
					break;
				case 'l':
					puti(va_arg(ap, long));
					break;
				case 's':
					char *s = va_arg(ap, char*);
					putsss(s,0,strlen(s)-1);
					break;
				default:
					break;
			}
			init = i + 1;
		}
	putsss(str,init,size-1);
	va_end(ap);
}


#endif
