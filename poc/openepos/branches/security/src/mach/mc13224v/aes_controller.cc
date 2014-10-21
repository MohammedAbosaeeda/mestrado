#include <mach/mc13224v/aes_controller.h>
#include <thread.h>

__BEGIN_SYS

bool AES_Controller::mode(Cipher::Mode m)
{
	bool ok = (m == Cipher::CBC) ||
			  (m == Cipher::CTR) ||
			  (m == Cipher::CTRMAC);
	if(ok) _mode = m;
	return ok;
}

/*
AES_Controller *AES_Controller::_instance = 0;

AES_Controller *AES_Controller::get_instance()
{
    if (_instance == 0)
        _instance = new AES_Controller();

    return _instance;
}
*/

bool AES_Controller::encrypt(const char *data, const char *key, char *encrypted_data)
{
    _semaphore.p();

	bool ret;

    _controller->set_key(key);
	switch(_mode)
	{
		case Cipher::CTR:
			_controller->set_ctr_mode();
			_controller->set_data(data);
			_controller->set_counter();
			ret = _controller->encrypt(encrypted_data, 0);
			break;
		case Cipher::CBC:
			_controller->set_cbc_mode();
			_controller->set_data(data);			
			ret = _controller->encrypt(0, encrypted_data);
			break;
		case Cipher::CTRMAC:
			_controller->set_ctr_mode();
			_controller->set_cbc_mode();
			_controller->set_data(data);
			_controller->set_counter();
			ret = _controller->encrypt(encrypted_data, encrypted_data+16);
			break;
	}

	/*
    _controller->set_ctr_mode();
    if (mac)
        _controller->set_cbc_mode();
    _controller->set_data(data);
    _controller->set_counter();

    encrypted_data[0] = 0;
    bool ret = _controller->encrypt(encrypted_data, mac);
	*/

	//OStream cout;
	/*
	cout << "Out = ";
	for(int i=0;encrypted_data[i]!='\0' && i<16;i++)
		cout << (unsigned char)encrypted_data[i] << " ";
	cout << '\n';
	*/

	/*
	cout << "Mac = ";
	for(int i=0;mac[i]!='\0' && i<16;i++)
		cout << (unsigned char)mac[i] << " ";
	cout << '\n';
	*/

    _semaphore.v();
    return ret;
}

bool AES_Controller::check_mac(const char *data, const char *key, const char *mac)
{
    _semaphore.p();

    _controller->set_key(key);
    _controller->set_cbc_mode();
    _controller->set_data(data);

    bool ret = _controller->check_mac(mac);

    _semaphore.v();
    return ret;
}

__END_SYS

