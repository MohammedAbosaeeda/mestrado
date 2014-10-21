// EPOS DTMF detector Abstraction Declarations

#ifndef __dtmf_detector_unified_h
#define __dtmf_detector_unified_h

#include "component.h"
#include "dtmf_algorithm.h"

namespace Implementation {

class DTMF_Detector: public Component {

public:
    enum {
        OP_ADD_SAMPLE = 0xF0,
        OP_DO_DTMF_DETECTION = 0xF1
    };

    enum{
        FRAMESIZE = DTMF_Algorithm::FRAMESIZE,
        TONES = DTMF_Algorithm::TONES
    };

    typedef DTMF_Algorithm::sample_t sample_t;

public:

    DTMF_Detector(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<DTMF_Detector>::n_ids])
        :Component(rx_ch, tx_ch, iid[0])
    {

        for (int i = 0; i < FRAMESIZE; ++i) samples[i] = 0;
        for (int i = 0; i < TONES; ++i) tone_responses[i] = false;
        buffer_idx = 0;
    }

private:

    sample_t samples[FRAMESIZE];
    bool tone_responses[TONES];
    unsigned int buffer_idx;

public:
    bool add_sample(sample_t sample){
        samples[buffer_idx] = sample;
        if(buffer_idx == FRAMESIZE-1){
            buffer_idx = 0;
            return true;
        }
        else{
            buffer_idx++;
            return false;
        }

    }

    char do_dtmf_detection(){

        GOERTZEL: for(int i = 0; i < TONES; ++i){
            tone_responses[i] = DTMF_Algorithm::goertzel(i,samples);
        }

        unsigned char detected_signal = DTMF_Algorithm::analyze_responses(tone_responses);

        // convert from [0, 15] to [1,2,3,..,A,B,..,F] using a lookup table
        // 'I' for invalid signal
        if(detected_signal == DTMF_Algorithm::INVAL) {
            return 'I';
        } else {
            return DTMF_Algorithm::button_names[detected_signal];
        }

    }

};

HANDLE_BEGIN(DTMF_Detector)
    bool add_sample(DTMF_Detector::sample_t sample){
        return Base::call_r<DTMF_Detector::OP_ADD_SAMPLE,bool>(sample);
    }
    unsigned char do_dtmf_detection(){
        return Base::call_r<DTMF_Detector::OP_DO_DTMF_DETECTION,unsigned char>();
    }
HANDLE_END

PROXY_BEGIN(DTMF_Detector)
    bool add_sample(DTMF_Detector::sample_t sample){
        return Base::call_r<DTMF_Detector::OP_ADD_SAMPLE,bool>(sample);
    }
    unsigned char do_dtmf_detection(){
        return Base::call_r<DTMF_Detector::OP_DO_DTMF_DETECTION,unsigned char>();
    }
PROXY_END

AGENT_BEGIN(DTMF_Detector)
    D_CALL_R_1(add_sample, OP_ADD_SAMPLE, unsigned char, sample_t)
    D_CALL_R_0(do_dtmf_detection, OP_DO_DTMF_DETECTION, unsigned char)
AGENT_END

};

DECLARE_RECFG_COMPONENT(DTMF_Detector);

#endif
