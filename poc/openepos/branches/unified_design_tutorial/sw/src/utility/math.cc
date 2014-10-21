
#include <utility/math.h>

__BEGIN_SYS

namespace Math {

float logf(float num, float base, float epsilon) {
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

float Q_rsqrt( float number ) {
    long i;
    float x2, y;
    const float threehalfs = 1.5F;

    x2 = number * 0.5F;
    y  = number;
    i  = * ( long * ) &y;                       // evil floating point bit level hacking
    i  = 0x5f3759df - ( i >> 1 );               // what the fuck?
    y  = * ( float * ) &i;
    y  = y * ( threehalfs - ( x2 * y * y ) );   // 1st iteration
    y  = y * ( threehalfs - ( x2 * y * y ) );   // 2nd iteration, this can be removed

    return y;
}

};

__END_SYS
