// EPOS Add Abstraction Test Program

#include <components/dummy_caller.h>
#include <components/dummy_callee.h>
#include <utility/ostream.h>
#include <timer.h>

__USING_SYS

volatile unsigned int *tsc = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS);

unsigned int no_arg_latency = 0;
unsigned int one_arg_latency = 0;
unsigned int two_arg_latency = 0;
unsigned int four_arg_latency = 0;
unsigned int eight_arg_latency = 0;

enum{
    //About 14 cycles are due to measurement overhead and are disconsidered
    MEASUMENT_OVERHEAD = 14
};

typedef Implementation::IF<Traits<Implementation::Dummy_Caller>::hardware, Dummy_Caller, Dummy_Callee>::Result Dummy_Call;

void latency_test(){

    /*
     * Using pointer to methods to avoid inline
     *
     * For SW->SW and SW->HW the overheads are final
     *
     *
     */

    //Timer::stop();//stop system timer to avoid scheduler jitter

    unsigned int tsc_prev = 0;
    unsigned int latency = 0;

    Dummy_Call *callee = new Dummy_Call(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);
    typedef unsigned int (Dummy_Call::*NO_ARG_METHOD) ();
    typedef unsigned int (Dummy_Call::*ONE_ARG_METHOD) (unsigned int);
    typedef unsigned int (Dummy_Call::*TWO_ARG_METHOD) (unsigned int,unsigned int);
    typedef unsigned int (Dummy_Call::*FOUR_ARG_METHOD) (unsigned int,unsigned int,unsigned int,unsigned int);
    typedef unsigned int (Dummy_Call::*EIGHT_ARG_METHOD) (unsigned int,unsigned int,unsigned int,unsigned int,unsigned int,unsigned int,unsigned int,unsigned int);

    OStream cout;

    if(Traits<Implementation::Dummy_Caller>::hardware)
        cout << "\nSW->HW->SW latency test\n\n";
    else if(Traits<Implementation::Dummy_Callee>::hardware)
        cout << "\nSW->HW latency test\n\n";
    else
        cout << "\nSW->SW latency test\n\n";

    //TODO has errors
    /*cout << "No arg\n";
    NO_ARG_METHOD f = &Dummy_Call::func_0;
    for (int i = 0; i < 10; ++i) {
        tsc_prev = *tsc;
        (callee->*f)();
        latency = *tsc - tsc_prev;
        cout << "\t" << i << ": " << latency << "\n";
        no_arg_latency += latency;
    }
    no_arg_latency /= 10;
    cout << "\tavg: " << no_arg_latency - MEASUMENT_OVERHEAD << "\n";


    cout << "One arg\n";

    ONE_ARG_METHOD f0 = &Dummy_Call::func_0;
    tsc_prev = *tsc;
    (callee->*f0)(0);
    latency = *tsc - tsc_prev;
    cout << "\t" << 0 << ": " << latency << "\n";
    one_arg_latency += latency;

    ONE_ARG_METHOD f1 = &Dummy_Call::func_1;
    tsc_prev = *tsc;
    (callee->*f1)(1);
    latency = *tsc - tsc_prev;
    cout << "\t" << 1 << ": " << latency << "\n";
    one_arg_latency += latency;

    ONE_ARG_METHOD f4 = &Dummy_Call::func_4;
    tsc_prev = *tsc;
    (callee->*f4)(4);
    latency = *tsc - tsc_prev;
    cout << "\t" << 2 << ": " << latency << "\n";
    one_arg_latency += latency;

    ONE_ARG_METHOD f8 = &Dummy_Call::func_8;
    tsc_prev = *tsc;
    (callee->*f8)(8);
    latency = *tsc - tsc_prev;
    cout << "\t" << 4 << ": " << latency << "\n";
    one_arg_latency += latency;

    ONE_ARG_METHOD f2 = &Dummy_Call::func_2;
    tsc_prev = *tsc;
    (callee->*f2)(2);
    latency = *tsc - tsc_prev;
    cout << "\t" << 8 << ": " << latency << "\n";
    one_arg_latency += latency;

    one_arg_latency /= 5;
    cout << "\tavg: " << one_arg_latency - MEASUMENT_OVERHEAD << "\n";


    cout << "Two arg\n";
    TWO_ARG_METHOD f_two = &Dummy_Call::func_0;
    for (int i = 0; i < 2; ++i) {
        for (int j = 0; j < 2; ++j) {
            tsc_prev = *tsc;
            (callee->*f_two)(i,j);
            latency = *tsc - tsc_prev;
            cout << "\t" << i << "," << j << ": " << latency << "\n";
            two_arg_latency += latency;
        }
    }
    two_arg_latency /= 2*2;
    cout << "\tavg: " << two_arg_latency - MEASUMENT_OVERHEAD << "\n";

    cout << "Four arg\n";
    FOUR_ARG_METHOD f_four = &Dummy_Call::func_0;
    for (int i = 0; i < 2; ++i) {
        for (int j = 0; j < 2; ++j) {
            unsigned int idx = (2*i)+j;
            tsc_prev = *tsc;
            (callee->*f_four)(idx,i,j,i);
            latency = *tsc - tsc_prev;
            cout << "\t" << idx << ": " << latency << "\n";
            four_arg_latency += latency;
        }
    }
    four_arg_latency /= 2*2;
    cout << "\tavg: " << four_arg_latency - MEASUMENT_OVERHEAD << "\n";

    cout << "Eight arg\n";
    EIGHT_ARG_METHOD f_eight = &Dummy_Call::func_0;
    for (int i = 0; i < 2; ++i) {
        for (int j = 0; j < 2; ++j) {
            unsigned int idx = (2*i)+(2*j)+(2*i)+j;
            tsc_prev = *tsc;
            (callee->*f_eight)(idx,i,j,i,j,i,j,i);
            latency = *tsc - tsc_prev;
            cout << "\t" << idx << ": " << latency << "\n";
            eight_arg_latency += latency;
        }
    }
    eight_arg_latency /= 2*2;
    cout << "\tavg: " << eight_arg_latency - MEASUMENT_OVERHEAD << "\n";*/

    *((volatile unsigned int*)0xFFFFFFFC) = 0;

}


int main()
{
    latency_test();
    return 0;
}
