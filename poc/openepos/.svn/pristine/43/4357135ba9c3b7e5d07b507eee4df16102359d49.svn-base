// EPOS AES Abstraction Declarations

#ifndef __aes_unified_h
#define __aes_unified_h

#include "component.h"
#include "src/aes.h"

namespace Implementation {

class AES : public Component, public AES_Common {
public:
    enum {
        OP_CIPHER       = 0xF0,
        OP_DECIPHER     = 0xF1,
        OP_ADD_KEY      = 0xF2,
        OP_DUMMY_CIPHER = 0xF3
    };

public:
    AES(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<AES>::n_ids])
        : Component(rx_ch, tx_ch, iid[0]) { }

public:
    //void add_key_2(safe_pkt_t pkt) { AES::add_key(pkt.data); }

    //safe_pkt_t cipher(safe_pkt_t pkt) {
        //AES::cipher_block(pkt.data, pkt.data);

        //return pkt;
    //}

    unsigned int dummy_cipher(safe_pkt_t pkt) {
        AES::cipher_block(pkt.data, pkt.data);

        return (pkt.data[1] + pkt.data[2] + pkt.data[3] + pkt.data[4] +
                pkt.data[5] + pkt.data[6] + pkt.data[7] + pkt.data[8] +
                pkt.data[9] + pkt.data[10] + pkt.data[11] + pkt.data[12] +
                pkt.data[13] + pkt.data[14] + pkt.data[15] + pkt.data[16]);
    }
};

HANDLE_BEGIN(AES)
    //void add_key_2(safe_pkt_t pkt) { Base::call<AES::OP_ADD_KEY, safe_pkt_t>(pkt); }

    //safe_pkt_t cipher(safe_pkt_t pkt) {
        //return Base::call_r<AES::OP_CIPHER, safe_pkt_t, safe_pkt_t>(pkt);
    //}

    unsigned int dummy_cipher(safe_pkt_t pkt) {
        return Base::call_r<AES::OP_DUMMY_CIPHER, unsigned int, safe_pkt_t>(pkt);
    }
HANDLE_END

PROXY_BEGIN(AES)
    //void add_key_2(safe_pkt_t pkt) { Base::call<AES::OP_ADD_KEY, safe_pkt_t>(pkt); }

    //safe_pkt_t cipher(safe_pkt_t pkt) {
        //return Base::call_r<AES::OP_CIPHER, safe_pkt_t, safe_pkt_t>(pkt);
    //}

    unsigned int dummy_cipher(safe_pkt_t pkt) {
        return Base::call_r<AES::OP_DUMMY_CIPHER, unsigned int, safe_pkt_t>(pkt);
    }
PROXY_END

AGENT_BEGIN(AES)
    //D_CALL_1(add_key_2, OP_ADD_KEY, safe_pkt_t)
    //D_CALL_R_1(cipher, OP_CIPHER, safe_pkt_t, safe_pkt_t)
    D_CALL_R_1(dummy_cipher, OP_DUMMY_CIPHER, unsigned int, safe_pkt_t)
AGENT_END

};

DECLARE_RECFG_COMPONENT(AES);

#endif
