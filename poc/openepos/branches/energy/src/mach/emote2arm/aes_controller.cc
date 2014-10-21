// OpenEPOS EMote2ARM_AES_Controller Mediator Implementation

#include <mach/emote2arm/aes_controller.h>

__BEGIN_SYS

EMote2ARM_AES_Controller *EMote2ARM_AES_Controller::_instance = 0;

EMote2ARM_AES_Controller *EMote2ARM_AES_Controller::get_instance() {
	if (_instance == 0)
		_instance = new EMote2ARM_AES_Controller();

	return _instance;
}

bool EMote2ARM_AES_Controller::encrypt(const char *data, const char *key,
		char *encrypted_data, char *mac) {
	_semaphore.p();

	_controller->set_key(key);
	_controller->set_ctr_mode();
	if (mac)
		_controller->set_cbc_mode();
	_controller->set_data(data);
	_controller->set_counter();

	encrypted_data[0] = 0;
	bool ret = _controller->encrypt(encrypted_data, mac);

	_semaphore.v();
	return ret;
}

bool EMote2ARM_AES_Controller::check_mac(const char *data, const char *key,
		const char *mac) {
	_semaphore.p();

	_controller->set_key(key);
	_controller->set_cbc_mode();
	_controller->set_data(data);

	bool ret = _controller->check_mac(mac);

	_semaphore.v();
	return ret;
}

__END_SYS

