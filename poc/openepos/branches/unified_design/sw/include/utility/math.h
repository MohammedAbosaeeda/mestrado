// EPOS Math Utility Declarations

#ifndef __math_h
#define __math_h

#include <system/config.h>

__BEGIN_SYS

namespace Math {

const float E       = 2.71828183;
const float PI      = 3.1415926535897f;
const float PI2     = 6.2831853071795f;
const float PIO2    = 1.5707963267948f;
const float PI_SQR  = 9.8696044010893f;
const float nan = (0.0/0.0);

int findMedianInt(short *data, int arraySize);
float filterSmooth(float currentData, float previousData, float smoothFactor);

const double sq2p1 = 2.414213562373095048802e0;
const double sq2m1  = .414213562373095048802e0;

const double p4  = .161536412982230228262e2;
const double p3  = .26842548195503973794141e3;
const double p2  = .11530293515404850115428136e4;
const double p1  = .178040631643319697105464587e4;
const double p0  = .89678597403663861959987488e3;
const double q4  = .5895697050844462222791e2;
const double q3  = .536265374031215315104235e3;
const double q2  = .16667838148816337184521798e4;
const double q1  = .207933497444540981287275926e4;
const double q0  = .89678597403663861962481162e3;

float logf(float num, float base = E , float epsilon = 1e-12) ;
float radians(float);
float degrees(float radians);

double mxatan(double arg);

double msatan(double arg);

float abs(float x) ;

float sqrt(float x);

double atan(double arg);

float pow(float num, float expo);

template <typename T>
T exp(T const & x);

float factorial(int num);

float cosineTaylor(float num, float precision = 10);

double atan2(double arg1, double arg2) ;

double asin(double arg);

float fast_log2(float val);

float fast_log(const float &val);

float fast_rsqrt( float number );

template <typename T>
T fast_sqrt(T const & x);

template <typename T>
T fast_sin(const T& theta);

template <typename T>
T fast_cos(const T& theta);

//////////////////////////////////////////////

template <typename T>
T fast_sqrt(T const & x){
    return (T)(1/fast_rsqrt((float)x));
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
T fast_sin(const T& theta) {
    T x = theta;
    return x - ((x * x * x) / T(6)) + ((x * x * x * x * x) / T(120)) - ((x * x * x * x * x * x * x) / T(5040));
}

template <typename T>
T fast_cos(const T& theta) {
    T x = theta;
    return T(1) - (x * x * T(0.5)) + (x * x * x * x * T(0.041666666666)) - (x * x * x * x * x * x * T(0.00138888888888));
}





}


__END_SYS

#endif
