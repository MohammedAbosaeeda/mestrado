// EPOS Math Utility Declarations

#ifndef __math_h
#define __math_h

#include <system/config.h>

__BEGIN_SYS

namespace Math {

const float E = 2.71828183;

const float PI          = 3.1415926535897f;
const float PI2         = 6.2831853071795f;
const float PID2        = 1.5707963267948f;
const float PI_SQR      = 9.8696044010893f;

float logf(float num, float base = E, float epsilon = 1e-12);

//Fast inverse square root implementation from quake III
float Q_rsqrt( float number );

template <typename T>
T sqrt(T const & x);


template <typename T>
T exp(T const & x);

template <typename T>
T sin(const T& theta);

template <typename T>
T cos(const T& theta);

template <typename T>
T sqrt(T const & x){
    return (T)(1/Q_rsqrt((float)x));
}

template <typename T>
T exp(T const & x){
    //using Taylor series
    T eps = 0.0000000000000000001;
    T elem = 1.0;
    T sum = 0.0;
    bool negative = false;
    int i = 1;
    sum = 0.0;
    T x1 = x;
    if (x < 0) {
        negative = true;
        x1 = -x;
    }
    do {
        sum += elem;
        elem *= x1 / i;
        i++;
        if (sum > 1E305) break;
    } while (elem >= eps);
    if (sum > 1E305) {
        // TODO: Handle large input case here
    }

    if (negative) {
        return 1.0 / sum;
    } else {
        return sum;
    }
}


template <typename T>
T sin(const T& theta) {
    T x = theta;
    return x - ((x * x * x) / T(6)) + ((x * x * x * x * x) / T(120)) - ((x * x * x * x * x * x * x) / T(5040));
}

template <typename T>
T cos(const T& theta) {
    T x = theta;
    return T(1) - (x * x * T(0.5)) + (x * x * x * x * T(0.041666666666)) - (x * x * x * x * x * x * T(0.00138888888888));
}


};


__END_SYS

#endif
