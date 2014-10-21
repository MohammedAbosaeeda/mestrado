#ifndef TRACER_C_H
#define TRACER_C_H

/* C header */
void tracer_trace(const char * message);
void tracer_trace_i(const char * message, int value);
void tracer_trace_s_ulong_s(const char* m1, unsigned long l1, const char* m2);

#endif
