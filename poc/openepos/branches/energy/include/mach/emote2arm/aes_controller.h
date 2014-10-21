// OpenEPOS EMote2ARM_AES_Controller Mediator Declarations
// TODO: needs review, refactor (Singleton, Semaphore)

#ifndef __emote2arm_aes_controller_h
#define __emote2arm_aes_controller_h

#include "asm_controller.h"
#include <semaphore.h>

__BEGIN_SYS

class EMote2ARM_AES_Controller {
private:
	EMote2ARM_AES_Controller() {
		_controller = EMote2ARM_ASM_Controller::get_instance();
	}

public:
	static EMote2ARM_AES_Controller *get_instance();

	bool encrypt(const char *data, const char *key, char *encrypted_data,
			char *mac = 0);

	bool decrypt(const char *data, const char *key, char *decrypted_data) {
		return encrypt(data, key, decrypted_data);
	}

	bool check_mac(const char *data, const char *key, const char *mac);

private:
	static EMote2ARM_AES_Controller *_instance;

	EMote2ARM_ASM_Controller *_controller;
	Semaphore _semaphore;
};

__END_SYS

#endif

