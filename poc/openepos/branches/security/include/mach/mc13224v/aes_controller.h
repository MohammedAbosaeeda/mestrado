#ifndef __aes_controller_h
#define __aes_controller_h

#include <cipher.h>
#include <system/config.h>
#include <semaphore.h>
#include <mach/mc13224v/asm_controller.h>
#include <utility/malloc.h>

__BEGIN_SYS

class AES_Controller: public Cipher
{
public:
     AES_Controller(Cipher::Mode m) {
		 mode(m);
         _controller = ASM_Controller::get_instance();
     }

     AES_Controller() {
		 mode(Cipher::CBC);
         _controller = ASM_Controller::get_instance();
     }

    virtual bool encrypt(const char *data, const char *key, char *encrypted_data);

    virtual bool decrypt(const char *data, const char *key, char *decrypted_data) {
		if(_mode != Cipher::CTR) return false;
        return encrypt(data, key, decrypted_data);
    }

	virtual bool mode(Mode m);

    bool check_mac(const char *data, const char *key, const char *mac);

private:
    ASM_Controller *_controller;
    Semaphore _semaphore;
};

__END_SYS

#endif

