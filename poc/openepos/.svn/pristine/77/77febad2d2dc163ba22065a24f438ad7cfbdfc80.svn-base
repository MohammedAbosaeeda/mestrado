#include <components/dtmf_detector.h>
#include <utility/ostream.h>
#include <system/types.h>
#include <chronometer.h>
#include <master_chronometer.h>
#include <alarm.h>

__USING_SYS

OStream cout;

void call_dtmf(DTMF_Detector &dtmf) {
    Chronometer chrono;
    unsigned int arg = 125;
    unsigned result;
    char tone;

    cout << "Calling dtmf" << endl;

    chrono.reset();
    chrono.start();
    tone = dtmf.do_dtmf_detection();
    chrono.stop();

    cout << "Result = " << tone << " (in " << chrono.ticks() << " ticks)" << endl;
    cout << "ticks_calc_recfg_time  " << dtmf.ticks_calc_recfg_time << endl;
    cout << "ticks_acquire          " << dtmf.ticks_acquire << endl;
    cout << "ticks_alloc_hw_res     " << dtmf.ticks_alloc_hw_res << endl;
    cout << "ticks_recfg            " << dtmf.ticks_recfg << endl;
    cout << "ticks_get_state        " << dtmf.ticks_get_state << endl;
    cout << "ticks_change_domain_hw " << dtmf.ticks_change_domain_hw << endl;
    cout << "ticks_set_state        " << dtmf.ticks_set_state << endl;
    cout << "ticks_method           " << dtmf.ticks_method << endl;
    cout << "ticks_release          " << dtmf.ticks_release << endl;
}

int main()
{
    volatile unsigned int * gpio = (volatile unsigned int *)(Traits<Machine>::LEDS_ADDRESS);

    *gpio = (1<<31) | (1<<7);

    DTMF_Detector dtmf(Component_Manager::dummy_channel, Component_Manager::dummy_channel, 0);

    cout << "DTMF_Detector Test" << endl;

    call_dtmf(dtmf);

    cout << "The End" << endl;

    return 0;
}

