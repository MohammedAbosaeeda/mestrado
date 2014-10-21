#include <utility/stdlib.h>
#include <utility/math.h>
#include <utility/ctype.h>
#include <utility/random.h>
#include <thread.h>
#include <clock.h>

__USING_SYS

/* sorry, it only works with base 10. :-( */
long int strtol(const char *string, char **tailptr, int base)
{
	char * pos = (char *) string;
	while (isspace(*pos)) pos++;
	int ltz = 0;
	if (*pos == '-'){
		ltz = 1;
		pos++;
	}
	int counter = 0;
	while ((*pos >= '0') && (*pos <= '9')){
		counter++;
		pos++;
	}
	if (tailptr) *tailptr = pos;
	pos--;
    long int result = 0;
	int i = 0;
	for (i = 0; i < counter; i++)
		result += ((*pos--)-48) * Math::pow(10,i);
    if (ltz) return -result;
	return result;
}

/* sorry, it only works with base 10. :-( */
unsigned long int strtoul(const char *string, char **tailptr, int base)
{
	return (unsigned long int)(strtol(string, tailptr, base));
}

int abs(int n)
{
	if (n < 0)
		return -n;
	else
		return n;
}

void exit(int status)
{
	Thread::exit(status);
}

int rand(void)
{
	return (int)Pseudo_Random::random();
}

void srand(unsigned int seed)
{
	Pseudo_Random::seed((unsigned long int)seed);
}

int atoi(const char* s)
{
	return (int)(strtol((char *)s, 0, 0));
}

char * itoa(unsigned int val, unsigned int base){
	static char buf[32] = {0};
	int i = 30;
	for(; val && i ; --i, val /= base)
		buf[i] = "0123456789abcdef"[val % base];	
	return (char *) &buf[i+1];
}

#define BUFLIMIT 64
char * llitoa(long long int v, unsigned int base)
{
	static char buf[BUFLIMIT] = {0};
    unsigned int i = 0;
    for (; i < BUFLIMIT; i++) buf[i] = '\0';
	i = 0;
	
    if(v < 0) {
		v = -v;
		buf[i++] = '-';
    }
    
    unsigned int j;

    if(!v) {
		buf[0] = '0';
		return (char *) &buf[0];
    }

    if(base == 8 || base == 16)
		buf[i++] = '0';
    if(base == 16)
		buf[i++] = 'x';

    for(j = v; j != 0; i++, j /= base);
    for(j = 0; v != 0; j++, v /= base)
	buf[i - 1 - j] = "0123456789abcdef"[v % base];

	return (char *) &buf[0];
} 

char * llutoa(unsigned long long int v, unsigned int base)
{
	static char buf[BUFLIMIT] = {0};
    unsigned int j;
    unsigned int i = 0;
    for (; i < BUFLIMIT; i++) buf[i] = '\0';
	i = 0;

    if(!v) {
		buf[0] = '0';
		return (char *) &buf[0];
    }

    if(base == 8 || base == 16)
		buf[i++] = '0';
    if(base == 16)
		buf[i++] = 'x';

    for(j = v; j != 0; i++, j /= base);
    for(j = 0; v != 0; j++, v /= base)
	buf[i - 1 - j] = "0123456789abcdef"[v % base];
	
	return (char *) &buf[0];
}


int strtod(const char* s, char ** tailptr)
{
	return strtol(s,tailptr,10);
}
