// EPOS Mult Abstraction Test Program

#include <components/dummy_caller.h>
#include <machine.h>
#include <periodic_thread.h>

//#define SIM_TIMER
//#define TIMER_INT_SCHED_TEST


__USING_SYS

OStream cout;

volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS);
volatile unsigned int * REAL_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS+4);

enum{
    TIMES = 10
};

enum{
    PROC_ITERATIONS = 22
};


const bool caller_harware = Implementation::Traits<Implementation::Dummy_Caller>::hardware;
const bool callee_harware = Implementation::Traits<Implementation::Dummy_Callee>::hardware;

#define SW_HW_OVERHEAD(name, func, arg, ret_check) \
    cout << name << ":\n"; \
    timer_acc = 0; \
    for (int i = 0; i < TIMES; ++i) { \
        timer_prev = *TIMER_REG; \
        int ret_val = caller.func(arg); \
        timer_curr = *TIMER_REG; \
        if(ret_val != ret_check){ \
            cout << "ERROR: return value = " << ret_val << ", it should be " << ret_check << "\n"; \
            break; \
        } \
        cout << "\t\t" << timer_curr - timer_prev << "\n"; \
        timer_acc += timer_curr - timer_prev; \
    } \
    cout << "\t" << timer_acc/TIMES << "\n"

#define HW_SW_OVERHEAD(name, func, ret_check) \
    cout << name << ":\n"; \
    timer_acc = 0; \
    for (int i = 0; i < TIMES; ++i) { \
        timer_prev = *TIMER_REG; \
        int ret_val = caller.rsp_tem_tudo(safe_pkt,dec_pkt,tone,func); \
        timer_curr = *TIMER_REG; \
        if(ret_val != ret_check){ \
            cout << "ERROR: return value = " << ret_val << ", it should be " << ret_check << "\n"; \
            break; \
        } \
        cout << "\t\t" << timer_curr - timer_prev << "\n"; \
        timer_acc += timer_curr - timer_prev; \
    } \
    cout << "\t" << timer_acc/TIMES << "\n"

#define HW_PROC_TEST(name, func) \
    cout << name << ":\n"; \
    timer_acc = 0; \
    cout << "\t\t"; \
    for (int i = 0; i < PROC_ITERATIONS; ++i) { \
        timer_prev = *TIMER_REG; \
        int ret_val = caller.rsp_tem_tudo_faz_tudo(safe_pkt,dec_pkt,0,func); \
        timer_curr = *TIMER_REG; \
        cout << "[ret(" << ret_val << "), time(" << timer_curr - timer_prev << ")]; "; \
        timer_acc += timer_curr - timer_prev; \
    } \
    cout << "\n"; \
    cout << "\t" << timer_acc/PROC_ITERATIONS << "\n"

namespace sw_imps {
     enum{
        __dtmf_FRAMESIZE = 32*22,
        __dtmf_TONES = Implementation::DTMF_Algorithm::TONES
    };
    typedef Implementation::DTMF_Algorithm::sample_t sample_t;
    Implementation::AES __aes;
    Implementation::ADPCM_Decoder __adpcm;
    unsigned short __dtmf_buffer_idx;
    sample_t __dtmf_samples[__dtmf_FRAMESIZE];
    bool __dtmf_tone_responses[__dtmf_TONES];

    void init(){
        for (int i = 0; i < __dtmf_FRAMESIZE; ++i) __dtmf_samples[i] = 0;
        for (int i = 0; i < __dtmf_TONES; ++i) __dtmf_tone_responses[i] = false;

        __dtmf_buffer_idx = 0;
    }

    int aes_decipher(Implementation::safe_pkt_t &pkt, Implementation::decoded_pkt_t &dec_pkt,unsigned char tone){
        __aes.add_key(Implementation::AES::DEFAULT_CIPHER_KEY);
        __aes.cipher_block(pkt.data, pkt.data);
        return (int)(pkt.data[0]+pkt.data[15]);
    }

    int adpcm_decode(Implementation::safe_pkt_t &pkt, Implementation::decoded_pkt_t &dec_pkt,unsigned char tone){
        for (int i = 0; i < 16; ++i) {
            unsigned int s1 = pkt.data[i] & 0xF;
            unsigned int s2 = pkt.data[i] >> 4;

            dec_pkt.data[i*2] = __adpcm.decode(s1);
            dec_pkt.data[(i*2)+1] = __adpcm.decode(s2);
        }
        return (int)(dec_pkt.data[0]+dec_pkt.data[31]);
    }

    int dtmf_add_sample(Implementation::safe_pkt_t &pkt, Implementation::decoded_pkt_t &dec_pkt,unsigned char xx_tone){
        for (int i = 0; i < 32; ++__dtmf_buffer_idx, ++i) {
            __dtmf_samples[__dtmf_buffer_idx] = dec_pkt.data[i];
        }
        if(__dtmf_buffer_idx == __dtmf_FRAMESIZE){

            for(int i = 0; i < __dtmf_TONES; ++i){
                __dtmf_tone_responses[i] = Implementation::DTMF_Algorithm::goertzel(i,__dtmf_samples);
            }

            unsigned char detected_signal = Implementation::DTMF_Algorithm::analyze_responses(__dtmf_tone_responses);

            // convert from [0, 15] to [1,2,3,..,A,B,..,F] using a lookup table
            // 'I' for invalid signal
            unsigned char tone = 'I';
            if(detected_signal != Implementation::DTMF_Algorithm::INVAL) {
                tone = Implementation::DTMF_Algorithm::button_names[detected_signal];
            }

            __dtmf_buffer_idx = 0;

            return tone;
        }
        else{
            return 0;
        }
    }

    int contrl_tone_detected(Implementation::safe_pkt_t &pkt, Implementation::decoded_pkt_t &dec_pkt,unsigned char tone){
        cout << (void*)tone;
        return 0;
    }

    int empty(Implementation::safe_pkt_t &pkt, Implementation::decoded_pkt_t &dec_pkt,unsigned char tone){
        return tone;
    }



};

#define SW_PROC_TEST(name, func) \
    cout << name << ":\n"; \
    timer_acc = 0; \
    cout << "\t\t"; \
    for (int i = 0; i < PROC_ITERATIONS; ++i) { \
        timer_prev = *TIMER_REG; \
        int ret_val = sw_imps::func(safe_pkt,dec_pkt,tone); \
        timer_curr = *TIMER_REG; \
        cout << "[ret(" << ret_val << "), time(" << timer_curr - timer_prev << ")]; "; \
        timer_acc += timer_curr - timer_prev; \
    } \
    cout << "\n"; \
    cout << "\t" << timer_acc/PROC_ITERATIONS << "\n"


int main()
{
    //comp_controller_test();

    ///*

    //Component_Manager::init_ints();
    //EPOSSOC_IC::enable(EPOSSOC_IC::IRQ_COMP_CONTRL);

    CPU::int_enable();//WTF ????

    //removes scheduler jiter
    EPOSSOC_IC::disable(EPOSSOC_IC::IRQ_TIMER);

    unsigned int sim_time = 0;
    unsigned int real_time = 0;
#ifdef SIM_TIMER
    sim_time = *REAL_REG;
#endif
    real_time = *TIMER_REG;

    if(!caller_harware || callee_harware){
        cout << "ERROR: for this test use CALLER->HW and CALLEE->SW\n";
        cout << "\nThe End\n";
        *((volatile unsigned int*)0xFFFFFFFC) = 0;
        return 0;
    }


    Dummy_Caller caller(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    unsigned int timer_prev = 0;
    unsigned int timer_curr = 0;
    unsigned int timer_acc = 0;

    Implementation::safe_pkt_t safe_pkt;
    for (int i = 0; i < 16; ++i) {
        safe_pkt.data[i] = i;
    }
    int safe_pkt_ret = (int)(safe_pkt.data[0]+safe_pkt.data[15]);
    Implementation::decoded_pkt_t dec_pkt;
    for (int i = 0; i < 32; ++i) {
        dec_pkt.data[i] = i;
    }
    int dec_pkt_ret = (int)(dec_pkt.data[0]+dec_pkt.data[31]);
    unsigned char tone = 'a';
    int tone_ret = (int)tone;


    cout << "\n\nSW->HW overhead test\n\n";

    SW_HW_OVERHEAD("aes_decipher", rsp_aes_decipher, safe_pkt, safe_pkt_ret);
    SW_HW_OVERHEAD("adpcm_decode", rsp_adpcm_decode, safe_pkt, safe_pkt_ret);
    SW_HW_OVERHEAD("dtmf_add_sample", rsp_dtmf_add_sample, dec_pkt, dec_pkt_ret);
    SW_HW_OVERHEAD("controller_tone_detected", rsp_controller_tone_detected, tone, tone_ret);

    cout << "\nHW->SW overhead test\n\n";

    HW_SW_OVERHEAD("aes_decipher", Implementation::Dummy_Caller::OP_RSP_AES_DECIPHER, safe_pkt_ret);
    HW_SW_OVERHEAD("adpcm_decode", Implementation::Dummy_Caller::OP_RSP_ADPCM_DECODE, safe_pkt_ret);
    HW_SW_OVERHEAD("dtmf_add_sample", Implementation::Dummy_Caller::OP_RSP_DTMF_ADD_SAMPLE, dec_pkt_ret);
    //HW_SW_OVERHEAD("controller_tone_detected", Implementation::Dummy_Caller::OP_RSP_CONTROLL_TONE, tone_ret);
    int select_ret = (int)0xFF;
    HW_SW_OVERHEAD("select_overhead", 0xFF, select_ret);


    cout << "\nHW processing test\n\n";

    HW_PROC_TEST("aes_decipher", Implementation::Dummy_Caller::OP_RSP_AES_DECIPHER);
    HW_PROC_TEST("adpcm_decode", Implementation::Dummy_Caller::OP_RSP_ADPCM_DECODE);
    HW_PROC_TEST("dtmf_add_sample", Implementation::Dummy_Caller::OP_RSP_DTMF_ADD_SAMPLE);

    cout << "\nSW processing test\n\n";

    SW_PROC_TEST("aes_decipher", aes_decipher);
    SW_PROC_TEST("adpcm_decode", adpcm_decode);
    SW_PROC_TEST("dtmf_add_sample", dtmf_add_sample);

    cout << "contrl_tone_detected" << ":\n";
    cout << "\t\t";
    timer_prev = *TIMER_REG;
    int ret_val = sw_imps::contrl_tone_detected(safe_pkt,dec_pkt,tone);
    timer_curr = *TIMER_REG;
    cout << "[ret(" << ret_val << "), time(" << timer_curr - timer_prev << ")]; ";
    timer_acc = timer_curr - timer_prev;
    cout << "\n";
    cout << "\t" << timer_acc << "\n";

    SW_PROC_TEST("call_overhead", empty);


    real_time = *TIMER_REG - real_time;
#ifdef SIM_TIMER
    sim_time = *REAL_REG - sim_time;
#endif
    cout << "\nApp real time " << real_time << " cycles\n";
    cout << "App sim time " << sim_time << " ms\n";

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;

    return 0;
}

