
#include <components/adpcm_codec.h>
#include <utility/ostream.h>

__USING_SYS

unsigned int abs(int val){
    return (unsigned int)((val < 0) ? (val*(-1)) : val);
}

void simple_test(){
    OStream cout;

    cout << "ADPCM Simple Test\n\n";

    ADPCM_Codec adpcm(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    int samples[6] = {
       55,
       512,
       5000,
       16000,
       -16000,
       -5000
    };

    for (int i = 0; i < 6; ++i) {
        for (int j = 0; j < 6; ++j) {
            int pcm_in = samples[i];
            unsigned int adpcm_out = adpcm.encode(pcm_in);
            int pcm_out = adpcm.decode(adpcm_out);

            cout << "sample=" << pcm_in << ", encoded=" << adpcm_out << ", decoded=" << pcm_out << "\n";

        }
    }

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;
}


int main()
{

    simple_test();

    return 0;
}
