// EPOS Math Utility Declarations

#ifndef __math_h
#define __math_h

#include <system/config.h>

__BEGIN_SYS

// Needed by lua
#define	INT_MAX		2147483647
#define	HUGE_VAL	INT_MAX
#define	ULONG_MAX	0xFFFFFFFFUL
#define	ceil(x)		x
#define	floor(x)	x
#define ldexp(x,n)	(x * pow(2,n))
#define fmod(x,y)	(x % y)
// ---

namespace Math {

#if 0 /* Drastic measure to compile Lua@EPOS  XXX Refatorate this! */

static const float E = 2.71828183;

float logf(float num, float base = E, float epsilon = 1e-12) {
    float integer = 0;
    if (num == 0) return 1;

    if (num < 1  && base < 1) return 0;

    while (num < 1) {
        integer--;
        num *= base;
    }

    while (num >= base) {
        integer++;
        num /= base;
    }

    float partial = 0.5;
    num *= num;
    float decimal = 0.0;
    while (partial > epsilon) {
        if (num >= base) {
            decimal += partial;
            num /= base;
        }
        partial *= 0.5;
        num *= num;
    }
    return (integer + decimal);
}

#endif

// Needed by lua
int pow(int x, int y);
int sqrt(int x);
int modf(int x, int* ip);
int fabs(int x);
// ---

};


__END_SYS

#endif

