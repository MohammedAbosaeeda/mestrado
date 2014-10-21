#include <key_database.h>
#include <nic.h>

__USING_SYS;

Key_Database::Key_Database()
{
	for(int i=0;i<16;i++)
	{
		_peers[i].free = true;
		_weak_peers[i].free = true;
		_known_nodes[i].free = true;
		_known_nodes[i].validated = false;
	}
}

bool Key_Database::insert_peer(const char *sn, const char *auth)
{
	for(int i=0;i<16;i++)
	{
		if(_known_nodes[i].free)
		{
			_known_nodes[i].free = false;
			for(int j=0;j<ID_SIZE;j++)			
				_known_nodes[i].serial_number[j] = sn[j];				
			for(int j=0;j<16;j++)
				_known_nodes[i].auth[j] = auth[j];			
			return true;
		}
	}
	return false;
}

bool Key_Database::remove_peer(const char *sn, const char *auth)
{
	for(int i=0;i<16;i++)
	{
		if(!_known_nodes[i].free && equals(_known_nodes[i].serial_number, ID_SIZE, sn, ID_SIZE))
		{
			_known_nodes[i].free = true;
			return true;
		}
	}
	return false;
}

bool Key_Database::insert_peer(const char *ms, const NIC::Address a)
{
	for(int i=0;i<16;i++)
	{
		if(_weak_peers[i].free)
		{
			_weak_peers[i].free = false;
			_weak_peers[i].addr = a;
			for(int j=0;j<KEY_SIZE;j++)
			{
				_weak_peers[i].master_secret[j] = ms[j];
			}
			return true;
		}
	}
	return false;
}

bool Key_Database::remove_peer(const char *ms, const NIC::Address a)
{
	for(int i=0;i<16;i++)
	{
		if(!_weak_peers[i].free && (a == _weak_peers[i].addr))
		{
			_weak_peers[i].free = true;
			return true;
		}
	}
	return false;
}

bool Key_Database::auth_to_sn(char *serial_number, const char *auth, const NIC::Address addr)
{
	for(int i=0;i<16;i++)
	{
		if(!_known_nodes[i].free && equals(_known_nodes[i].auth, 16, auth, 16))
		{
			for(int j=0;j<ID_SIZE;j++)
				serial_number[j] = _known_nodes[i].serial_number[j];
			_peers[i].addr = addr;
			return true;
		}
	}
	return false;
}

bool Key_Database::addr_to_ms(char *master_secret, const NIC::Address addr)
{
	for(int i=0;i<16;i++)
	{
		if(!_peers[i].free && (_peers[i].addr == addr))
		{
			for(int j=0;j<KEY_SIZE;j++)
				master_secret[j] = _peers[i].master_secret[j];
			return true;
		}
		if(!_weak_peers[i].free && (_weak_peers[i].addr == addr))
		{
			for(int j=0;j<KEY_SIZE;j++)
				master_secret[j] = _weak_peers[i].master_secret[j];
			return true;
		}
	}
	return false;
}

bool Key_Database::ms_to_sn(char *serial_number, const char *master_secret)
{
	for(int i=0;i<16;i++)
		if(!_peers[i].free && equals(_peers[i].master_secret, KEY_SIZE, master_secret, KEY_SIZE))
		{
			for(int j=0;j<ID_SIZE;j++)
				serial_number[j] = _peers[i].node->serial_number[j];
			return true;
		}
	return false;
}

bool Key_Database::validate_peer(const char *sn, const char *ms, const char *auth, const NIC::Address a)
{
	for(int i=0;i<16;i++)
	{
		if(!_weak_peers[i].free && (_weak_peers[i].addr == a) && (equals(_weak_peers[i].master_secret, KEY_SIZE, ms, KEY_SIZE)))
		{
			for(int j=0;j<16;j++)
			{
				if(!_known_nodes[j].free && !_known_nodes[j].validated && equals(_known_nodes[j].serial_number, ID_SIZE, sn, ID_SIZE))
				{
					for(int k=0;k<16;k++)
					{
						if(_peers[k].free)
						{
							_peers[k].free = false;
							_peers[k].addr = a;
							_peers[k].node = &_known_nodes[j];
							for(int l=0;l<KEY_SIZE;l++)
								_peers[k].master_secret[l] = ms[l];
							_weak_peers[i].free = true;
							_known_nodes[j].validated = true;
							return true;
						}
					}
				}
			}
		}
	}
	return false;
}
/*
bool Key_Database::sn_to_ms(char *master_secret, const char *serial_number, NIC::Address addr)
{
	for(int i=0;i<16;i++)
		if(!_peers[i].free && (_peers[i].addr == addr) && !strcmp(_peers[i].node->serial_number, serial_number))
		{
			_peers[i].addr = addr;
			master_secret = _peers[i].master_secret;
			return true;
		}
	return false;
}
*/
