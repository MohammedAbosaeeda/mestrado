
#ifndef __aes_unified_h
#define __aes_unified_h


namespace Implementation {

class AES {
public:
    enum{
        EXPANDED_SIZE = 176,
        CIPHER_SIZE = 16,
        NK = 4,
        NB = 4,
        NR = 10 // devido se 128-bit AES
    };

private://static const members
    static const unsigned char _sBox[256];

    static const unsigned char _inv_sBox[256];

    static const unsigned char _Rcon[256]; //Round constant array pre calculated

private:
    unsigned char _cipher_key[EXPANDED_SIZE];

private:

    void _rotate(unsigned char* in);

    void _scheduleCore(unsigned char* in, unsigned char i);

    unsigned char _expandKey(unsigned char* in);

    void _mixColumns(unsigned char* in);

    void _shiftRows(unsigned char* in);

    void _addRoundKey(unsigned char* state, unsigned char* cipherKey, unsigned char round);

    void _subBytes(unsigned char* in);

    unsigned char* _cipher(unsigned char in[4*NB], unsigned char* key_schedule);

    //++++++++++++++++++++++++INVERSE CIPHER+++++++++++++++++++++++++++++++

    void _invMixColumns(unsigned char* in);

    void _invSubBytes(unsigned char* in);

    void _invShiftRows(unsigned char* in);

    unsigned char* _invCipher(unsigned char in[4*NB], unsigned char* key_schedule);

public:

    static const unsigned char DEFAULT_CIPHER_KEY[AES::CIPHER_SIZE];

    void add_key(const unsigned char* in){
        for (int i = 0; i < CIPHER_SIZE; ++i) {
            _cipher_key[i] = in[i];
        }
        _expandKey(_cipher_key);
    }

    void cipher_block(const unsigned char in[4*NB], unsigned char out[4*NB]){
        for (int i = 0; i < CIPHER_SIZE; ++i) {
            out[i] = in[i];
        }
        _cipher(out,_cipher_key);
    }

};

};


#endif /* MULT_H_ */
