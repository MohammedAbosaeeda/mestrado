#ifndef time_h
#define time_h

#define		CLOCKS_PER_SEC		1000000l

struct tm{
	unsigned int tm_sec;
	unsigned int tm_min;
	unsigned int tm_hour;
	unsigned int tm_mday;
	unsigned int tm_mon;
	unsigned int tm_year;
	unsigned int tm_wday;
	unsigned int tm_yday;
	unsigned int tm_isdst;
	char * tm_tzone;
	unsigned long int tm_gmtoff;
};

typedef int time_t;

typedef unsigned long clock_t;

time_t time(time_t* tp);
time_t difftime(time_t time2, time_t time1);
time_t mktime(struct tm* tp);
struct tm* gmtime(const time_t* tp);
struct tm* localtime(const time_t* tp);
unsigned int strftime(char* str, unsigned int size, const char* fmt, const struct tm* tp);

#endif
