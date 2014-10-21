// EPOS Mult Abstraction Test Program

#include <machine.h>
#include <components/rsp_adpcm.h>
#include <components/adpcm_codec.h>

__USING_SYS

OStream cout;

volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS);

int main()
{

    cout << "\nRSP ADPCM\n\n";

    Implementation::ADPCM_Decoder adpcm;
    RSP_ADPCM rsp_adpcm(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);
    Implementation::safe_pkt_t safe_pkt;


    cout << "Running SW ADPCM\n";
    unsigned int tmp = *TIMER_REG;
    unsigned int rst = tmp;
    for (int i = 0; i < 16; ++i) {
        rst += adpcm.decode(rst);
    }
    tmp = *TIMER_REG - tmp;
    cout << "Finished in " << tmp*2 << " cycles"<<rst<<"\n";

    cout << "Running RSP_ADPCM\n";
    tmp = *TIMER_REG;
    rsp_adpcm.decode(safe_pkt);
    tmp = *TIMER_REG - tmp;
    cout << "Finished in " << tmp << " cycles\n";


    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;

    return 0;
}
