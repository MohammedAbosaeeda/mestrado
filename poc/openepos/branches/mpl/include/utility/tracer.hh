#ifndef TRACER_HH
#define TRACER_HH

/* C++ header */

#include <system/config.h>

__BEGIN_SYS


class Tracer {

 public:
    static void trace(const char * message);
    static void trace_i(const char * message, int value);
	static void trace_s_ulong_s(const char* m1, unsigned long l1, const char* m2);

};

__END_SYS

#endif // TRACER_HH
