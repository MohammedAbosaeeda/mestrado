
#include <components/dtmf_detector.h>
#include <utility/ostream.h>

__USING_SYS

int collector_idx = 0;
int collector_sin = 0;

int collect_sample(void) {
    collector_idx = (collector_idx < 7) ? collector_idx + 1 : 0;
    collector_sin = !collector_sin;
    if(collector_sin) return Implementation::DTMF_Algorithm::SINES[collector_idx];
    else              return Implementation::DTMF_Algorithm::COSINES[collector_idx];
}

void simple_test(){
    OStream cout;

    cout << "\nDTMF_Detector Simple Test\n\n";

    DTMF_Detector dtmf(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    for (int i = 0; i < 10; ++i) {
        int count = 0;
        while(true){
            ++count;
            if(dtmf.add_sample(collect_sample())) break;
        }

        char result = dtmf.do_dtmf_detection();

        cout << count << " samples produced " << result << "\n";
    }

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;
}


int main()
{

    simple_test();

    return 0;
}
