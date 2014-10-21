#include <components/adpcm_codec.h>
#include <utility/ostream.h>
#include <system/types.h>
#include <chronometer.h>
#include <master_chronometer.h>
#include <alarm.h>

__USING_SYS

OStream cout;

void call_adpcm(ADPCM_Codec &adpcm) {
    Chronometer chrono;
    unsigned int arg = 125;
    unsigned result;

    cout << "Calling adpcm.encode(arg)" << endl;

    chrono.reset();
    chrono.start();
    result = adpcm.decode(arg);
    chrono.stop();

    cout << "Result = " << result << " (in " << chrono.ticks() << " ticks)" << endl;
    cout << "ticks_calc_feasability " << adpcm.ticks_calc_feasability << endl;
    cout << "ticks_acquire          " << adpcm.ticks_acquire << endl;
    cout << "ticks_change_domain_hw " << adpcm.ticks_change_domain_hw << endl;
    cout << "ticks_recfg            " << adpcm.ticks_recfg << endl;
    cout << "ticks_method           " << adpcm.ticks_method << endl;
    cout << "ticks_get_state        " << adpcm.ticks_get_state << endl;
    cout << "ticks_change_domain_sw " << adpcm.ticks_change_domain_sw << endl;
    cout << "ticks_set_state        " << adpcm.ticks_set_state << endl;
    cout << "ticks_free_hw_res      " << adpcm.ticks_free_hw_res << endl;
    cout << "ticks_release          " << adpcm.ticks_release << endl;
}

int main()
{
    volatile unsigned int * gpio = (volatile unsigned int *)(Traits<Machine>::LEDS_ADDRESS);

    *gpio = (1<<31) | (1<<7);

    ADPCM_Codec adpcm(Component_Manager::dummy_channel, Component_Manager::dummy_channel, 0);

    cout << "ADPCM_Codec Test" << endl;

    call_adpcm(adpcm);

    cout << "The End" << endl;

    return 0;
}
