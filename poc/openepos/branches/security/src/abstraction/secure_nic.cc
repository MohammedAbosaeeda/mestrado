#include <secure_nic.h>
//#include <clock.h>
#include <tsc.h>
#include <bignum.h>

__USING_SYS;

// Sends a secure message to an already authenticated destination.
int Secure_NIC::send(const NIC::Address &dst, const char *data, unsigned int size)
{
	// Round up to a multiple of 16, for AES
	unsigned int sz = size + 15 - (size - 1) % 16;

	// Header + (size rounded up to multiple of 16)
	char msg[1+sz];

	// Message header
	msg[0] = USER_MSG;

	// Copy user data and zero-pad
	unsigned int i;
	for(i=1;i<=size;i++) msg[i] = data[i-1];
	for(;i<sz;i++) msg[i]=0;

	// Encrypt the message
	if(!_encrypt(msg+1, msg+1, sz, dst))
		return -3;

	// Give the message to lower layer
	return _send(dst, msg, 1+sz);
}

// Used by the sensor.
// Requests to start the protocol and establish a key.
// Sends the public Diffie-Hellman key.
int Secure_NIC::send_key_request(NIC::Address dest)
{
	// Header + public key
	char msg[1 + PUBLIC_KEY_SIZE];
	msg[0] = DH_REQUEST;

	// Fetch the public Diffie-Hellman key
	get_public((unsigned char *)(msg+1), PUBLIC_KEY_SIZE);

	_waiting_dh_response = true;
	_waiting_dh_from = dest;
	
	// Give the message to lower layer
	return _send(dest, msg, sizeof(msg));
}

// Response to DH_REQUEST
// Sends the public Diffie-Hellman key
int Secure_NIC::_send_dh_response(NIC::Address dest)
{
	// Header + public key
	char msg[1 + PUBLIC_KEY_SIZE];
	msg[0] = DH_RESPONSE;
	
	// Fetch the public Diffie-Hellman key
	get_public((unsigned char *)(msg+1), PUBLIC_KEY_SIZE);

	// Give the message to lower layer
	return _send(dest, msg, sizeof(msg));
}

// Calculates and sends AUTH + OTP
int Secure_NIC::_send_otp(NIC::Address dest)
{
	//Header + AUTH + OTP
	char msg[1 + 16 + 16];	
	msg[0] = AUTH_REQUEST;

	// Fetch Master Secret
	if(!_keydb->addr_to_ms(msg+1, dest))
		return -1;

	// Calculate and attach OTP
	if(!_derive_key(msg+17, msg+1, _serial_number))
		return -2;

	// Attach AUTH
	for(int i=0;i<16;i++)
		msg[i+1] = _auth[i];

	// Give the message to lower layer
	return _send(dest, msg, sizeof(msg));
}

// Derive the current key and use it to encrypt the message
bool Secure_NIC::_encrypt(char *encrypted_msg, const char *msg, unsigned int size, NIC::Address dst)
{
	// Master Secret
	char ms[SECRET_SIZE];
	// Current Key
	char key[16];

	// Fetch master secret
	if(!_keydb->addr_to_ms(ms,dst))
		return false;
	/*
	   kout << "ms = [";
	   for(int i=0;i<SECRET_SIZE;i++)
	   kout << " ," << (int)ms[i];
	   kout << "]" << endl;
	*/
	
	if(_is_gateway)
	{
		// Fetch the ID associated to that master secret
		char sn[ID_SIZE];
		if(!_keydb->ms_to_sn(sn,ms))
			return false;
		/*
	   kout << "sn22 = [";
	   for(int i=0;i<ID_SIZE;i++)
	   kout << " ," << (int)sn[i];
	   kout << "]" << endl;
	   */
		// Calculate the current key
		if(!_derive_key(key, ms, sn))
			return false;
	}
	// Calculate the current key
	else if(!_derive_key(key, ms, _serial_number))
			return false;

	/*
	   kout << "ms = [";
	   for(int i=0;i<SECRET_SIZE;i++)
	   kout << " ," << (int)ms[i];
	   kout << "]" << endl;
	   kout << "key = [";
	   for(int i=0;i<16;i++)
	   kout << " ," << (int)key[i];
	   kout << "]" << endl;
	   kout << "msg = [";
	   for(int i=0;i<size;i++)
	   kout << " ," << (int)msg[i];
	   kout << "]" << endl;
	*/

	// Encrypt the message, 16 bytes at a time
	char tmp[16];
	char tmp2[16];
	unsigned int i, j;
	for(i=0;i<size;i+=16)
	{
		for(j=0;j<16;j++)
		{
			if(j+i < size)
				tmp2[j] = msg[j+i];
			else
				tmp2[j] = 0;
		}

		if(!_cipher->encrypt(tmp2, key, tmp)) 
			return false;

		for(j=0;(j<16) && (j+i < size);j++)
			encrypted_msg[j+i] = tmp[j];
	}
	/*
	   kout << "encrypted msg = [";
	   for(int i=0;i<size;i++)
	   kout << " ," << (int)encrypted_msg[i];
	   kout << "]" << endl;
	*/
	return true;
}

// Derive the current key and use it to decrypt the message
bool Secure_NIC::_decrypt(char *decrypted_msg, const char *msg, unsigned int size, NIC::Address from)
{
	// Master Secret
	char ms[SECRET_SIZE];
	// Current Key
	char key[16];

	// Fetch master secret
	if(!_keydb->addr_to_ms(ms,from))
		return false;
	
	if(_is_gateway)
	{
		char sn[ID_SIZE];
		// Fetch the ID associated to that master secret
		if(!_keydb->ms_to_sn(sn,ms))
			return false;
		// Calculate the current key
		if(!_derive_key(key, ms, sn))
			return false;
	}
	// Calculate the current key
	else if(!_derive_key(key, ms, _serial_number))
			return false;

	/*
	   kout << "ms = [";
	   for(int i=0;i<SECRET_SIZE;i++)
	   kout << " ," << (int)ms[i];
	   kout << "]" << endl;
	   kout << "key = [";
	   for(int i=0;i<16;i++)
	   kout << " ," << (int)key[i];
	   kout << "]" << endl;
	   kout << "msg = [";
	   for(int i=0;i<size;i++)
	   kout << " ," << (int)msg[i];
	   kout << "]" << endl;
	*/
	// Decrypt the message, 16 bytes at a time
	char tmp[16];
	char tmp2[16];
	unsigned int i, j;
	for(i=0;i<size;i+=16)
	{
		for(j=0;j<16;j++)
		{
			if(j+i < size)
				tmp2[j] = msg[j+i];
			else
				tmp2[j] = 0;
		}

		if(!_cipher->decrypt(tmp2, key, tmp)) 
			return false;

		for(j=0;(j<16) && (j+i < size);j++)
			decrypted_msg[j+i] = tmp[j];
	}
	/*
	   kout << "decrypted msg = [";
	   for(int i=0;i<size;i++)
	   kout << " ," << (int)decrypted_msg[i];
	   kout << "]" << endl;
	*/
	return true;
}

// Calculate OTP
bool Secure_NIC::_derive_key(char *key, char *ms, char *sn)
{
	char _sn[16];
	char _ms[16];
	int i;
	for(i=0;i<ID_SIZE && i<16;i++)
		_sn[i] = sn[i];
	for(;i<16;i++)
		_sn[i] = 0;
	for(i=0;i<SECRET_SIZE && i<16;i++)
		_ms[i] = ms[i];
	for(;i<16;i++)
		_ms[i] = 0;

	// Calculating OTP
	_poly->r(_ms);
	_poly->k(_sn);
		
	union
	{
		char time[16];
		unsigned long long s;
	};

	for(int i=0;i<16;i++)
		time[i] = 0;

	s = TSC::getMicroseconds();
	// Round up to a multiple of the time window
	s = s + Traits<Secure_NIC>::TIME_WINDOW - 1 - (s - 1) % Traits<Secure_NIC>::TIME_WINDOW;

	kout << "s = " << s << endl;

	const unsigned int maxsz = ID_SIZE > SECRET_SIZE ? ID_SIZE : SECRET_SIZE;
	char m[maxsz];
	for(unsigned int i=0; i<maxsz; i++)
		m[i] = ms[i % SECRET_SIZE] ^ sn[i % ID_SIZE];

	_poly->authenticate(key, time, m, maxsz);

	return true;
}

// signed_msg = AUTH + OTP.
// Calculate the OTP and check if it matches the one in the msg
bool Secure_NIC::_authenticate(const char *signed_msg, NIC::Address dst)
{
	db<Secure_NIC>(TRC) << "Secure_NIC::_authenticate()" << endl;
	if(!accepting_connections) 
	{
		db<Secure_NIC>(WRN) << "Secure_NIC::_authenticate() - not accepting connections" << endl;
		return false;
	}

	char sn[ID_SIZE];
	char ms[SECRET_SIZE];
	char otp[16];

	// Fetch the ID associated to this AUTH
	if(!_keydb->auth_to_sn(sn, signed_msg, dst))
	{
		db<Secure_NIC>(ERR) << "Secure_NIC::_authenticate() - failed to get sn" << endl;
		kout << "Secure_NIC::_authenticate() - failed to get sn" << endl;
		return false;
	}
	   kout << "sn = [";
	   for(int i=0;i<ID_SIZE;i++)
	   kout << " ," << (int)sn[i];
	   kout << "]" << endl;

	// Fetch the address associated to this master secret
	if(!_keydb->addr_to_ms(ms, dst))
	{
		db<Secure_NIC>(ERR) << "Secure_NIC::_authenticate() - failed to get ms" << endl;
		return false;
	}

	// Calculate OTP
	if(!_derive_key(otp, ms, sn))
	{
		db<Secure_NIC>(ERR) << "Secure_NIC::_authenticate() - failed to get otp" << endl;
		return false;
	}

	db<Secure_NIC>(INF) << "Secure_NIC::authenticating..." << endl;

	// Check if calculated OTP matches the received one
	if( _poly->isequal(signed_msg+16, otp) )
	{
		 db<Secure_NIC>(INF)<< "Secure_NIC::authentication granted" << endl;
		_keydb->validate_peer(sn, ms, signed_msg, dst);

		// Tell the sensor that it is authenticated
		char msg[1 + ID_SIZE];
		if(!_encrypt(msg+1, sn, ID_SIZE, dst))
		{
			db<Secure_NIC>(ERR)<< "Secure_NIC - encryption failed" << endl;
			return false;
		}
		msg[0] = AUTH_OK;

		_send(dst, msg, 1 + ID_SIZE);

		return true;
	}
	else
	{
		db<Secure_NIC>(ERR) << "Secure_NIC::authentication failed" << endl;
		_keydb->remove_peer(ms, dst);
		return false;
	}
}

void Secure_NIC::update(Conditionally_Observed * o, int p)
{
	_acquire_lock(_update_lock);
	db<Secure_NIC>(TRC) << "Secure_NIC::update()" << endl;
	if(p == Traits<Secure_NIC>::PROTOCOL_ID)
	{
		NIC::Protocol prot;
		// Get the message from lower layer
		_received_length = _nic->receive(&_received_from, &prot, _received_data, sizeof(_received_data));
		if(!(prot == Traits<Secure_NIC>::PROTOCOL_ID))
		{
			_release_lock(_update_lock);
			return;
		}
		if(_received_length <= 0) 
		{
			_release_lock(_update_lock);
			return;
		}
		db<Secure_NIC>(INF) << "Secure_NIC - Received msg of size " << _received_length << ", header " << (int)_received_data[0] << endl;
		
		unsigned char aux[SECRET_SIZE];
		switch(_received_data[0])
		{
			case USER_MSG:
				// Decrypt the message
				if(!_decrypt(_received_data, _received_data+1, _received_length, _received_from))
					break;
				_has_received_data = true;
				// Notify upper layer
				notify(p);
				break;
			case DH_REQUEST:
				if(!accepting_connections || _received_length != PUBLIC_KEY_SIZE + 1) 
					break;
				// Send back Diffie_Hellman public key
				_send_dh_response(_received_from);
				// Set a Diffie-Hellman Master Secret
				calculate_key(aux, SECRET_SIZE, (unsigned char *)(_received_data+1), PUBLIC_KEY_SIZE);
				/*
				kout << "dh ms = [";
				for(int i=0;i<SECRET_SIZE;i++)
					kout << " ," << (int)aux[i];
				kout << "]" << endl;
				*/
				_keydb->insert_peer((char *)aux, _received_from);
				break;
			case DH_RESPONSE:
				if(!_waiting_dh_response || _received_length != PUBLIC_KEY_SIZE + 1) 
					break;
				if(!(_waiting_dh_from == NIC::BROADCAST || _waiting_dh_from == _received_from)) 
					break;
				_waiting_dh_response = false;
				// Set a Diffie-Hellman Master Secret
				calculate_key(aux, SECRET_SIZE, (unsigned char *)(_received_data+1), PUBLIC_KEY_SIZE);
				_keydb->insert_peer((char *)aux, _received_from);
				_waiting_auth_response = true;
				_waiting_auth_from = _received_from;
				// Send authentication message
				_send_otp(_received_from);
				break;
			case AUTH_REQUEST:
				if(_received_length < 33) 
					break;
				_authenticate(_received_data+1, _received_from);
				break;
			case AUTH_OK:
				if(!_waiting_auth_response || _received_length != 1+ID_SIZE)
				{
					db<Secure_NIC>(ERR) << "Secure_NIC - unexpected msg" << endl;
					break;
				}
				if(!(_waiting_auth_from == _received_from)) 
				{
					db<Secure_NIC>(ERR) << "Secure_NIC - address mismatch" << endl;
					break;
				}
				
				char tmp[ID_SIZE];
				if(!_decrypt(tmp, (const char *)(_received_data+1), ID_SIZE, _received_from))
				{
					db<Secure_NIC>(ERR) << "Secure_NIC - decryption failed" << endl;
					kout << "Secure_NIC - decryption failed" << endl;
					break;
				}
				int i;
				for(i=0; i<ID_SIZE; i++)
					if(tmp[i] != _serial_number[i])
						break;
				if(i<ID_SIZE) 
				{
					db<Secure_NIC>(ERR) << "Secure_NIC - AUTH_OK msg wrong" << endl;
					kout << "Secure_NIC - AUTH_OK msg wrong" << endl;
					break;
				}
				_waiting_auth_response = false;
				_authenticated = true;
				// Sensor is authenticated
				db<Secure_NIC>(INF) << "Secure_NIC - Authenticated" << endl;
				break;
			default:
				break;
		}
	}
	_release_lock(_update_lock);
}
