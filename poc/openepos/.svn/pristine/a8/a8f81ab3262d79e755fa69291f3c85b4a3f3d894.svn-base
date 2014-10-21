// EPOS-- Buffer Abstraction Declarations

// This work is licensed under the Creative Commons 
// Attribution-NonCommercial-NoDerivs License. To view a copy of this license, 
// visit http://creativecommons.org/licenses/by-nc-nd/2.0/ or send a letter to 
// Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.

#ifndef __buffer_h
#define __buffer_h

#include <mutex.h>
#include <condition.h>

__BEGIN_SYS

class Buffer
{
public:
	Buffer(int size)
	: _start(0), _end(-1), _empty(),
	  _full(), _mutex(), _size(size)
	{
		_mutex.lock();
		_buffer = (char *)malloc(size);
		_mutex.unlock();
	}

    ~Buffer() {}

	bool write_nb (unsigned char byte)
	{
		if (!_mutex.try_lock())
			return false;

		if (_start == (_end+1))
		{
			_mutex.unlock();
			return false;
		};

		write_common(byte);
		_mutex.unlock();

		return true;
	}

	void write (unsigned char byte)
	{
		CPU::int_disable();
		_mutex.lock();

		while (_start == (_end+1))
			_full.wait_mutex(&_mutex);

		write_common(byte);
		_mutex.unlock();
	}

	bool read_nb (unsigned char *byte)
	{
		if (!_mutex.try_lock())
			return false;

		if (_end == -1)
		{
			_mutex.unlock();
			return false;
		};

		read_common(byte);
		_mutex.unlock();

		return true;
	}

	void read (unsigned char *byte)
	{
		_mutex.lock();

		while (_end == -1)
			_empty.wait_mutex(&_mutex);

		read_common(byte);
		_mutex.unlock();
	}

	bool empty()
	{
		return (_end == -1);
	}

    int size()
	{
		return _size;
	}

//  static int init(System_Info *si);

private:
	void write_common(unsigned char byte)
	{
		// kout << "write_common()";

		if (_end == -1) /* was -1 */
			_empty.broadcast();

		_end = (_end + 1) % _size;
		_buffer[_end] = byte;
	};

	void read_common(unsigned char *byte)
	{
		*byte = _buffer[_start];

		if (_start == _end)
		{
			_start =  0;
			_end   = -1;
		}
		else
		{
			_start = (_start + 1) % _size;
			_full.broadcast();
		}
	};

private:
	char		*_buffer;
	int			 _start, _end;
	Condition	 _empty, _full;
	Mutex		 _mutex;
	int			 _size;
};

__END_SYS

#endif
