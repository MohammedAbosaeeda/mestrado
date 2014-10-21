// EPOS Mult Abstraction Test Program

#include <components/rsp_eth.h>
#include <chronometer.h>

__USING_SYS

OStream cout;

volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS);
volatile unsigned int * REAL_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS+4);

//#define SIM_TIMER

int main()
{
    CPU::int_enable();//WTF ????

    //removes scheduler jiter
    EPOSSOC_IC::disable(EPOSSOC_IC::IRQ_TIMER);

    cout << "\nRSP app\n\n";

    RSP_ETH eth(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    cout << "Calling ETH start\n";
    unsigned int sim_time = 0;
    unsigned int real_time = 0;
#ifdef SIM_TIMER
    sim_time = *REAL_REG;
#endif
    real_time = *TIMER_REG;

    int n_pkt = eth.start();
    while (Component_Manager::rsp_flag < 2);

    real_time = *TIMER_REG - real_time;
#ifdef SIM_TIMER
    sim_time = *REAL_REG - sim_time;
#endif

    cout << "Real time " << real_time << " cycles\n";
    cout << "Sim time " << sim_time << " ms\n";


    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;

    return 0;
}
