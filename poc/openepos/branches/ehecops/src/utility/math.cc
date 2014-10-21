#include <utility/math.h>
#include<utility/ostream.h>

__USING_SYS

float Math::degrees(float radian){
    return radian * 57.2957795;

}

float Math::filterSmooth(float currentData, float previousData, float smoothFactor){
    if(smoothFactor != 1.0) {
        return (previousData * (1.0 -smoothFactor) + (currentData * smoothFactor));
    }
    return currentData;
}


int Math::findMedianInt(short *data, int arraySize)
{
  int temp;
  bool done = 0;
  unsigned char i;

   // Sorts numbers from lowest to highest
  while (done != 1)
  {
    done = 1;
    for (i=0; i<(arraySize-1); i++)
    {
      if (data[i] > data[i+1])
      {     // numbers are out of order - swap
        temp = data[i+1];
        data[i+1] = data[i];
        data[i] = temp;
        done = 0;
     //   db<ITG3200> (TRC) << data[i] << ", " ;
      }
    }
  }

  return data[arraySize/2]; // return the median value
}

float Math::logf(float num, float base , float epsilon ) {
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

float Math::radians(float degrees){
    return degrees * 0.0174532925;

}




double Math::mxatan(double arg)
    {
        double argsq, value;

        argsq = arg*arg;
        value = ((((p4*argsq + p3)*argsq + p2)*argsq + p1)*argsq + p0);
        value = value/(((((argsq + q4)*argsq + q3)*argsq + q2)*argsq + q1)*argsq + q0);
        return value*arg;
    }

double Math::msatan(double arg)
   {
       if(arg < sq2m1)
           return mxatan(arg);
       if(arg > sq2p1)
           return PIO2 - mxatan(1/arg);
           return PIO2/2 + mxatan((arg-1)/(arg+1));
   }
float Math::abs(float x) {
    if (x < 0.0) return 0.0f - x;
    return x;

}
int Math::abs(int x) {
    if (x < 0) return 0 - x;
    return x;

}

long long Math::abs(long long x) {
    if (x < 0) return 0 - x;
    return x;
}

float Math::sqrt(float x){
    OStream cout;
  //  cout << " --- Square root ---" << endl;
    float left = 0;
    float right = x;
     float testValue = 0;
    while (abs(left - right) >=  0.01){//1e-15) {
       // cout << " left: " << (int)left << " right "<< (int)right << endl;
        testValue = (right + left)/2.0;
     //  cout << "TestValue: " << (int)testValue << endl;
     if (testValue * testValue <= x)
       left = testValue;
     else
      right = testValue;
    }
    return left;

}

double Math::atan(double arg)
    {
        if(arg > 0)
            return msatan(arg);
        return -msatan(-arg);
    }

double Math::atan2(double arg1, double arg2) {
    if(arg1+arg2 == arg1)
           {
               if(arg1 >= 0)
               return PIO2;
                   return -PIO2;
           }
           arg1 = atan(arg1/arg2);
           if(arg2 < 0)
          {
               if(arg1 <= 0)
                   return arg1 + PI;
               return arg1 - PI;
           }
           return arg1;
}


double Math::asin(double arg)
    {
        double temp;
        int sign;

        sign = 0;
        if(arg < 0)
        {
            arg = -arg;
            sign++;
        }
        if(arg > 1)
            return (0.0/0.0);
        temp = sqrt(1 - arg*arg);
        if(arg > 0.7)
            temp = PIO2 - atan(temp/arg);
        else
            temp = atan(arg/temp);
        if(sign > 0)
            temp = -temp;
        return temp;
    }


float Math::factorial(int num){
    double fact = 1;
    for(int i = 1; i <= num; i++){
        fact *= i;
    }
    return fact;
}

float Math::pow(float num, float exp){
    if (exp == 0) return 1;
    for (int i = 1; i < exp; ++i){
        num *= num;
    }
   return num;

}

float Math::cosineTaylor(float num, float precision ){
    float value = 0;
    for(int n = 0; n < precision; n++){
        value += pow(-1.0, n) * pow(num, 2*n)/factorial(2*n);
    }
    return value;
}

float Math::fast_log2(float val)
{
   int * const exp_ptr = reinterpret_cast <int *> (&val);
   int x = *exp_ptr;
   const int log_2 = ((x >> 23) & 255) - 128;
   x &= ~(255 << 23);
   x += 127 << 23;
   (*exp_ptr) = x;

   val = ((-1.0f/3) * val + 2) * val - 2.0f/3;

   return (val + log_2);
}

float Math::fast_log(const float &val)
{
    static const float ln_2 = 0.69314718f;
    return (fast_log2(val) * ln_2);
}


//Taylor series cos(x) = (-1)^n*x^(2n)/(2n)!


/*
    What you'll see:

    0.540302                        0.540302
    -0.416147                       -0.416147
    -0.989992                       -0.989992
    -0.653644                       -0.653644
    0.283662                        0.283662
    0.96017                         0.96017
    0.753902                        0.753902
    -0.1455                         -0.1455
    -0.91113                        -0.91113
    -0.839072                       -0.839072

    Requires +1 to precision as 19 would give -0.839071 for the last number
*/

// recursive version of fact
int Math::fact(int a)
{
    if(a==0) return 1;
    return a*fact(a-1);
}

// Taylor series, more accurate than the ones above
// Warning: this must be used w/ rads, example:
//  double alpharad,pi=3.14, alpha=xx;
//  alpharad=aplha*(pi/180);
//  cos(alpharad);
//  sin(alpharad);
double Math::sine(double sx)
{
    int pv=1,pn=0;
    double sums=0,c=1;
    while(c>0.000001)
    {
        c=(Math::pow(sx,pv)/fact(pv));
        sums=sums+c*Math::pow(-1,pn);
        pv=pv+2;
        pn++;
    }
    return sums;
}

double Math::cosine(double cx)
{
    int pv=0,pn=0;
    double sumc=0,c=1;
    while(c>0.000001)
    {
        c=(Math::pow(cx,pv)/fact(pv));
        sumc=sumc+c*Math::pow(-1,pn);
        pv=pv+2;
        pn++;
    }
    return sumc;
}

/*
 * ====================================================
 * Copyright (C) 2004 by Sun Microsystems, Inc. All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this
 * software is freely granted, provided that this notice
 * is preserved.
 * ====================================================
 */

/* The code below was imported from fdlibm
 *  http://www.netlib.org/fdlibm/
 *  All rights reserved to Sun Microsystems.
 */

#define __HI(x) *(1+(int*)&x)
#define __LO(x) *(int*)&x
#define __HIp(x) *(1+(int*)x)
#define __LOp(x) *(int*)x

#ifdef __STDC__
static const double
#else
static double
#endif
one = 1.0,
halF[2] = {0.5,-0.5,},
huge    = 1.0e+300,
twom1000= 9.33263618503218878990e-302,     /* 2**-1000=0x01700000,0*/
o_threshold=  7.09782712893383973096e+02,  /* 0x40862E42, 0xFEFA39EF */
u_threshold= -7.45133219101941108420e+02,  /* 0xc0874910, 0xD52D3051 */
ln2HI[2]   ={ 6.93147180369123816490e-01,  /* 0x3fe62e42, 0xfee00000 */
         -6.93147180369123816490e-01,},/* 0xbfe62e42, 0xfee00000 */
ln2LO[2]   ={ 1.90821492927058770002e-10,  /* 0x3dea39ef, 0x35793c76 */
         -1.90821492927058770002e-10,},/* 0xbdea39ef, 0x35793c76 */
invln2 =  1.44269504088896338700e+00, /* 0x3ff71547, 0x652b82fe */
P1   =  1.66666666666666019037e-01, /* 0x3FC55555, 0x5555553E */
P2   = -2.77777777770155933842e-03, /* 0xBF66C16C, 0x16BEBD93 */
P3   =  6.61375632143793436117e-05, /* 0x3F11566A, 0xAF25DE2C */
P4   = -1.65339022054652515390e-06, /* 0xBEBBBD41, 0xC5D26BF1 */
P5   =  4.13813679705723846039e-08; /* 0x3E663769, 0x72BEA4D0 */


#ifdef __STDC__
    double __ieee754_exp(double x)  /* default IEEE double exp */
#else
    double __ieee754_exp(x) /* default IEEE double exp */
    double x;
#endif
{
    double y,hi,lo,c,t;
    int k,xsb;
    unsigned hx;

    hx  = __HI(x);  /* high word of x */
    xsb = (hx>>31)&1;       /* sign bit of x */
    hx &= 0x7fffffff;       /* high word of |x| */

    /* filter out non-finite argument */
    if(hx >= 0x40862E42) {          /* if |x|>=709.78... */
            if(hx>=0x7ff00000) {
        if(((hx&0xfffff)|__LO(x))!=0)
             return x+x;        /* NaN */
        else return (xsb==0)? x:0.0;    /* exp(+-inf)={inf,0} */
        }
        if(x > o_threshold) return huge*huge; /* overflow */
        if(x < u_threshold) return twom1000*twom1000; /* underflow */
    }

    /* argument reduction */
    if(hx > 0x3fd62e42) {       /* if  |x| > 0.5 ln2 */
        if(hx < 0x3FF0A2B2) {   /* and |x| < 1.5 ln2 */
        hi = x-ln2HI[xsb]; lo=ln2LO[xsb]; k = 1-xsb-xsb;
        } else {
        k  = (int)(invln2*x+halF[xsb]);
        t  = k;
        hi = x - t*ln2HI[0];    /* t*ln2HI is exact here */
        lo = t*ln2LO[0];
        }
        x  = hi - lo;
    }
    else if(hx < 0x3e300000)  { /* when |x|<2**-28 */
        if(huge+x>one) return one+x;/* trigger inexact */
    }
    else k = 0;

    /* x is now in primary range */
    t  = x*x;
    c  = x - t*(P1+t*(P2+t*(P3+t*(P4+t*P5))));
    if(k==0)    return one-((x*c)/(c-2.0)-x);
    else        y = one-((lo-(x*c)/(2.0-c))-hi);
    if(k >= -1021) {
        __HI(y) += (k<<20); /* add k to y's exponent */
        return y;
    } else {
        __HI(y) += ((k+1000)<<20);/* add k to y's exponent */
        return y*twom1000;
    }
}

/******************************************************************************/

double Math::exp(double x) {
    return __ieee754_exp(x) ;
}

