// EPOS Mult Abstraction Test Program

#include <machine.h>
#include <components/rsp_dtmf.h>
#include <components/dtmf_detector.h>

__USING_SYS

OStream cout;

volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS);

int main()
{

    cout << "\nRSP DTMF\n\n";

    DTMF_Detector dtmf(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);
    RSP_DTMF rsp_dtmf(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);
    Implementation::decoded_pkt_t safe_pkt;

    for (int i = 0; i < 32; ++i) {
        safe_pkt.data[i] = i;
    }


    cout << "Running SW DTMF\n";
    unsigned int tmp = *TIMER_REG;
    int i = 0;
    while(!dtmf.add_sample(safe_pkt.data[i])) {
        if(i >= 32) i = 0;
        else ++i;
    }
    char tone = dtmf.do_dtmf_detection();
    tmp = *TIMER_REG - tmp;
    cout << "Finished in " << tmp << " cycles"<<tone<<"\n";

    cout << "Running RSP_DTMF\n";
    tmp = *TIMER_REG;
    for (int j = 0; j < 22; ++j) {
        rsp_dtmf.add_sample(safe_pkt);
    }
    tmp = *TIMER_REG - tmp;
    cout << "Finished in " << tmp << " cycles\n";


    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;

    return 0;
}
