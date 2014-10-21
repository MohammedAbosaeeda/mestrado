
#include <utility/random.h>
#include <utility/ostream.h>
#include <utility/math.h>
#include <timer.h>
#include "mibench_fft/include/fourier.h"

enum{
    MAXSIZE = 4096,
    MAXWAVES = 4,

    INVERSE = 0
};

volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(System::Traits<System::EPOSSOC_Timer>::BASE_ADDRESS);

int main() {

    System::OStream cout;
    cout << "\nRunning mibench-fft\n";
    System::Timer::stop();//stop system timer to avoid scheduler jitter
    unsigned int time = *TIMER_REG;
    //*((volatile unsigned int*)0xFFFFFFF8) = 0; //for virtual platform only

    unsigned int i,j;
    float *RealIn;
    float *ImagIn;
    float *RealOut;
    float *ImagOut;
    float *coeff;
    float *amp;

    System::Pseudo_Random::seed(1);

    RealIn= new float[MAXSIZE];
    ImagIn= new float[MAXSIZE];
    RealOut= new float[MAXSIZE];
    ImagOut= new float[MAXSIZE];
    coeff= new float[MAXWAVES];
    amp= new float[MAXWAVES];

    /* Makes MAXWAVES waves of random amplitude and period */
    for(i=0;i<MAXWAVES;i++)
    {
        coeff[i] = System::Pseudo_Random::random()%1000;
        amp[i] = System::Pseudo_Random::random()%1000;
    }
    for(i=0;i<MAXSIZE;i++)
    {
        /*   RealIn[i]=rand();*/
        RealIn[i]=0;
        for(j=0;j<MAXWAVES;j++)
        {
            /* randomly select sin or cos */
            if (System::Pseudo_Random::random()%2)
            {
                RealIn[i]+=coeff[j]*System::Math::cos(amp[j]*i);
            }
            else
            {
                RealIn[i]+=coeff[j]*System::Math::sin(amp[j]*i);
            }
            ImagIn[i]=0;
        }
    }

    cout << "RealIn:\n";
    for (i=0;i<MAXSIZE;i++)
        if((i%(MAXSIZE/2)) == 0) cout << "[" << i << "]=" << (int)RealIn[i] << "\n";
    cout << "\n";

    cout << "ImagIn:\n";
    for (i=0;i<MAXSIZE;i++)
        if((i%(MAXSIZE/2)) == 0) cout << "[" << i << "]=" << (int)ImagIn[i] << "\n";
    cout << "\n";

    /* regular*/
    cout << "Performing a " << MAXSIZE << " point ";
    if(INVERSE == 1) cout << "inverse";
    cout << " fft\n";
    mibench::fft_float (MAXSIZE,INVERSE,RealIn,ImagIn,RealOut,ImagOut);

    cout << "RealOut:\n";
    for (i=0;i<MAXSIZE;i++)
        if((i%(MAXSIZE/2)) == 0) cout << "[" << i << "]=" << (int)RealOut[i] << "\n";
    cout << "\n";

    cout << "ImagOut:\n";
    for (i=0;i<MAXSIZE;i++)
        if((i%(MAXSIZE/2)) == 0) cout << "[" << i << "]=" << (int)ImagOut[i] << "\n";
    cout << "\n";

    delete[] RealIn;
    delete[] ImagIn;
    delete[] RealOut;
    delete[] ImagOut;
    delete[] coeff;
    delete[] amp;

    time = *TIMER_REG-time;
    cout << "\nFinished in " << time;
    //*((volatile unsigned int*)0xFFFFFFFC) = 0; //for virtual platform only

    return 0;

}

//TODO WTF gcc ????
void * memcpy(void * dst0, const void * src0, size_t len0)
    {
    char *dst = reinterpret_cast<char *> (dst0);
    const char *src = reinterpret_cast<const char *> (src0);
    long *aligned_dst;
    const long *aligned_src;
    size_t len = len0;

    if(!((len) < (sizeof(long) << 2)) && !(((long) src & (sizeof(long) - 1))
        | ((long) dst & (sizeof(long) - 1)))) {
        aligned_dst = (long*) dst;
        aligned_src = (long*) src;

        while(len >= (sizeof(long) << 2)) {
        *aligned_dst++ = *aligned_src++;
        *aligned_dst++ = *aligned_src++;
        *aligned_dst++ = *aligned_src++;
        *aligned_dst++ = *aligned_src++;
        len -= (sizeof(long) << 2);
        }

        while(len >= (sizeof(long))) {
        *aligned_dst++ = *aligned_src++;
        len -= (sizeof(long));
        }

        dst = (char*) aligned_dst;
        src = (char*) aligned_src;
    }

    while(len--)
        *dst++ = *src++;

    return dst0;

    }
