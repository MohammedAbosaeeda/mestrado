#ifndef __bignum_h
#define __bignum_h

//#define DEBUG_BIGNUM

#include "bignum_base.h"
#include <ostream>
#include <iostream>
#include <cstdlib>

using namespace std;

template<bool condition, typename Then, typename Else>
struct IF 
{ typedef Then Result; };

template<typename Then, typename Else>
struct IF<false, Then, Else>
{ typedef Else Result; };

template <unsigned int bytes = 16>
class Bignum : public Bignum_Base
{
	public:
	
	// size must be greater than 0	
	static const typename IF < (bytes > 0), unsigned int, void* >::Result size = ((bytes + sz_digit - 1) / sz_digit);

	digit data[size];

	Bignum(unsigned int number = 0)
	{
		_length = from_uint(data, number);
	}
	template<unsigned int bbytes>
	Bignum(const Bignum<bbytes> &b)
	{
		_length = copy(data, b.data, (b.length() > size ? size : b.length()));
	}
	Bignum(const unsigned char *raw_data, unsigned int data_size)
	{
		set_data(raw_data, data_size);
	}
	/*
	Bignum(const char *dec_string, unsigned int string_size)
	{
		int i;
		for(i=0;i<size;i++) data[i] = 0;
		_length = 0;
		Bignum<1> ten(10);
		i=0;
		*this += (dec_string[i]-'0');
		for(i++; i<string_size; i++)
		{
			*this *= ten;
			*this += (dec_string[i]-'0');
		}
		// Length is computed by add and mult
	}
	*/
	~Bignum() { }
	
	inline Bignum<bytes>& operator=(unsigned int number)
	{ 
		_length = from_uint(data, number);
		return *this;
	}
	template<unsigned int bbytes>
	inline Bignum<bytes>& operator=(Bignum<bbytes> b)
	{
		_length = copy(data, b.data, b.length());
		return *this;
	}

	template<unsigned int bbytes>
	inline bool operator==(const Bignum<bbytes>& b) const
	{ return (cmp(data,length(),b.data,b.length()) == 0); }
	template<unsigned int bbytes>
	inline bool operator!=(const Bignum<bbytes>& b) const
	{ return (cmp(data,length(),b.data,b.length()) != 0); }
	template<unsigned int bbytes>
	inline bool operator>=(const Bignum<bbytes>& b) const
	{ return (cmp(data,length(),b.data,b.length()) >= 0); }
	template<unsigned int bbytes>
	inline bool operator<=(const Bignum<bbytes>& b) const
	{ return (cmp(data,length(),b.data,b.length()) <= 0); }
	template<unsigned int bbytes>
	inline bool operator>(const Bignum<bbytes>& b) const
	{ return (cmp(data,length(),b.data,b.length()) > 0); }
	template<unsigned int bbytes>
	inline bool operator<(const Bignum<bbytes>& b) const
	{ return (cmp(data,length(),b.data,b.length()) < 0); }

	inline bool operator==(unsigned int b) const
	{ return (cmp(data,length(),b) == 0); }
	inline bool operator!=(unsigned int b) const
	{ return (cmp(data,length(),b) != 0); }
	inline bool operator>=(unsigned int b) const
	{ return (cmp(data,length(),b) >= 0); }
	inline bool operator<=(unsigned int b) const
	{ return (cmp(data,length(),b) <= 0); }
	inline bool operator<(unsigned int b) const
	{ return (cmp(data,length(),b) < 0); }
	inline bool operator>(unsigned int b) const
	{ return (cmp(data,length(),b) > 0); }

	/*
	Bignum<bytes>& operator+=(unsigned int b)
	{
		unsigned char carry = 0; 
		unsigned int tmp;
		bool zero = false;
		unsigned int i;
		for(i=0; i<bytes; i++)
		{
			tmp = b % base;
			b /= base;
			tmp += data[i] + carry;
			data[i] = tmp % base; 
			if((!zero) && (data[i] == 0))
				_length = i;
			zero = (data[i] == 0);
			carry = tmp / base;
		}
		if(!zero) _length = i;
		return *this;
	}
	*/
	inline Bignum<bytes>& operator+=(unsigned int b)
	{
		if(sizeof(b) <= sz_digit)
		{
			_length = add(data, data, length(), (digit*)&b, 1);
			return *this;
		}
		else
			return (*this += Bignum<sizeof(b)>(b));
	}

	template <unsigned int bbytes>
	inline Bignum<bytes>& operator+=(const Bignum<bbytes> &b)
	{
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "+" << endl;
		cout << b << endl;
#endif
		_length = add(data, data, length(), b.data, b.length());		
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	/*
	Bignum<bytes>& operator-=(unsigned int b)
	{
		int tmp = cmp(b);
		if(tmp==0)
		{ 
			for(unsigned int i=0;i<bytes;i++) 
				data[i]=0; 
			_length = 0;
			return; 
		}
		else if(tmp < 0) 
		{
			Bignum<bytes> c(b);
			c -= *this;
			*this = c;
			return;
		}

		unsigned int i;
		int borrow = 0;
		for(i=0; b>0 ;i++)
		{
			int anow = ((int)data[i]) - borrow;
			int bnow = b % base;
			b /= base;
			borrow = 0;
			while(anow < bnow)
			{
				anow += base;
				borrow++;
			}
			data[i] = anow-bnow;
		}
		for(;i<bytes && borrow>0 ;i++)
		{
			int anow = ((int)data[i]) - borrow;
			borrow = 0;
			while(anow < 0) 
			{
				anow += base;
				borrow++;
			}
			data[i] = anow;
		}
		compute_length();
		return *this;
	}
	*/

	inline Bignum<bytes>& operator-=(unsigned int b)
	{
		if(sizeof(b) <= sz_digit)
		{
			_length = sub(data, data, length(), (digit*)&b, 1);
			return *this;
		}
		else
			return (*this -= Bignum<sizeof(b)>(b));
	}

	template <unsigned int bbytes>
	inline Bignum<bytes>& operator-=(const Bignum<bbytes>& b)
	{
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "-" << endl;
		cout << b << endl;
#endif
		_length = sub(data, data, length(), b.data, b.length());
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	template<unsigned int bbytes, unsigned int bbbytes>
	inline Bignum<bytes>& eq_mult(const Bignum<bbytes> &b, const Bignum<bbbytes> &c)
	{
		_length = mult(data, b.data, b.length(), c.data, c.length());
		return *this;
	}

	inline Bignum<bytes>& operator*=(unsigned int b)
	{
		if(sizeof(b) <= sz_digit)
		{
			_length = mult(data, data, length(), (digit*)&b, 1);
			return *this;
		}
		else
			return (*this *= Bignum<sizeof(b)>(b));
	}

	template <unsigned int bbytes>
	inline Bignum<bytes>& operator*=(const Bignum<bbytes>& b)
	{
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "*" << endl;
		cout << b << endl;
#endif
		_length = mult(data, data, length(), b.data, b.length());		
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	/*
	Bignum<bytes>& operator/=(unsigned int rhs)
	{
		Bignum<bytes> tmp;
		div_mod(tmp, rhs);
		return *this;
	}
	*/

	template <unsigned int bbytes>
	inline Bignum<bytes>& operator/=(const Bignum<bbytes>& b)
	{
		digit a[b.size];
		unsigned int c;
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "//" << endl;
		cout << b << endl;
#endif
		_length = Bignum_Base::div_mod(data, a, c, data, length(), b.data, b.length());		
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	/*
	Bignum<bytes>& operator%=(unsigned int rhs)
	{
		Bignum<bytes> tmp(*this);
		tmp.div_mod(*this, rhs);
		return *this;
	}
	*/

	template <unsigned int bbytes, unsigned int bbbytes>
	inline Bignum<bytes>& barrett_reduce(const Bignum<bbytes>& mod, const Bignum<bbbytes>& u)
	{
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "%" << endl;
		cout << mod <<  endl;
#endif		
		_length = Bignum_Base::barrett_reduce(data, _length, mod.data, mod.length(), u.data, u.length());
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	template <unsigned int bbytes>
	inline Bignum<bytes>& operator%=(const Bignum<bbytes>& b)
	{
		digit a[size];
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "%" << endl;
		cout << b << endl;
#endif		
		if(*this >= b)
			int k = Bignum_Base::div_mod(a, data, _length, data, length(), b.data, b.length());
		//int k = Bignum_Base::barrett_reduction(a, data, _length, data, length(), b.data, b.length());
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	// Multiply by base to the power of n (shift left)
	// Shifting right because of little-endianness
	inline Bignum<bytes>& operator<<=(unsigned int n)
	{
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "<< " << bits_in_digit << "*" << endl;
		cout << Bignum<1>(n) << endl;
#endif		
		_length = base_mult(data, data, _length, n);
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	// Divide by base to the power of n (shift right)
	// Shifting left because of little-endianness
	inline Bignum<bytes>& operator>>=(unsigned int n)
	{ 		
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << ">> " << bits_in_digit << "*" << endl;
		cout << Bignum<1>(n) << endl;
#endif		
		_length = base_div(data, data, _length, n);
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;
#endif
		return *this;
	}

	template <unsigned int rbytes, unsigned int bbytes>
	inline Bignum<bytes>& div_mod(Bignum<rbytes> &rem, const Bignum<bbytes> &b)
	{
#ifdef DEBUG_BIGNUM
		cout << "HERE";
		cout << *this << endl;
		cout << "//" << endl;
		cout << b << endl;
#endif
		_length = Bignum_Base::div_mod(data, rem, rem._length, data, length(), b.data, b.length());
#ifdef DEBUG_BIGNUM
		cout << "==" << endl;
		cout << *this << endl;

		cout << "HERE";
		cout << *this << endl;
		cout << "%" << endl;
		cout << b << endl;
		cout << "==" << endl;
		cout << rem << endl;
#endif
		return *this;
	}
		
	/*
	template <unsigned int rbytes>
	void div_mod(Bignum<rbytes> &rem, unsigned int b)
	{
		if(b==0) return;
		int i;

		int c = cmp(b);
		if(c == 0)
		{
			data[0] = 1; 
			_length = 1;
			for(i=1;i<bytes;i++)
				data[i] = 0;
			rem.data[0] = 0; 
			rem._length = 0;
			return;
		}
		if(c < 0)
		{
			rem = *this;
			data[0] = 0;
			_length = 0;
			for(i=1;i<bytes;i++)
				data[i] = 0;
			return;
		}
		unsigned int n = length();
		if(n==0)
		{
			rem._length = 0;
			for(i=0;i<rem.size();i++)
				rem.data[i] = 0;
			return;
		}
		Bignum<bytes> acc(0u);
		rem = (unsigned int)data[n-1];
		unsigned int nxt = bytes-1;
		for(i=n-2; ; i--)
		{
			acc=(unsigned int)0;
			for(int j=0; j<base; j++)
			{
				acc += b;
				if(acc.cmp(rem) > 0)
				{
					data[nxt--] = j;
					break;
				}
			}
			acc -= b;
			rem -= acc;

			if(i < 0)
				break;

			unsigned int len = ++rem._length;
			int j;
			for(j=len-1;j>0;j--)
				rem.data[j] = rem.data[j-1];
			rem.data[0] = data[i];
		}
		int j = 0;
		for(i=nxt+1;i<bytes;i++)
			data[j++] = data[i];
		_length = j;
		while((_length>0) && (data[_length-1]==0)) _length--;
		while(j < bytes)
			data[j++] = 0;
	}
	*/

	inline unsigned int length() const 
	{ return _length; }

	inline void zero_pad()
	{ 
		for(unsigned int i=_length; i<size; i++)
			data[i] = 0;
	}

	inline void set_data(const unsigned char *raw_data, unsigned int data_size)
	{		
		_length = 0;
		unsigned int byte_size = size * sz_digit;
		unsigned char * byte_data = (unsigned char *)data;
		int l=(data_size > byte_size ? byte_size : data_size)-1;
		for(int i=l+1; i<byte_size; i++)
			byte_data[i] = 0;
		for(;l>=0;l--)
			byte_data[l] = raw_data[l];
		for(_length = size; _length > 0 && data[_length-1]==0; _length--);
	}

	friend ostream &operator<< (ostream &out, const Bignum<bytes> &b) 
	{
		int i;
		out << b.length();
		out << '[';
		for(i=0;i<b.length();i++)
		{
			out << (int)b.data[i];
		    if(i < b.length()-1)
				out << ", ";
		}
		out << ']';
		return out; 
	}

	inline unsigned int to_uint() const
	{
		unsigned int as = length();
		unsigned int ret = 0;
		unsigned int i;
		unsigned int mult = 1;
		for(i=0;i<as;i++)
		{
			ret += data[i] * mult;
			mult <<= bits_in_digit;
		}
		return ret;
	}
	inline void random()
	{
		do
		{
			data[size-1] = rand();
		} while(data[size-1]==0);
		for(int i=size-2; i>=0; i--)
			data[i] = rand();
		_length = size;
	}

	inline void compute_length()
	{
		_length = 0;
		for(int i=size-1;i>=0;i--)
			if((data[i] != 0)) 
			{
				_length = i+1;
				break;
			}
	}

	unsigned int _length;
	private:
	/*
	int cmp(unsigned int b) const
	{
		int ret = 0;
		unsigned int i, al = length();
		for(i=0; i<al; i++)
		{
			int now = b % base;
			if(data[i] > now) ret = 1;
			else if(data[i] < now) ret = -1;
			b /= base;
		}
		if(b > 0) return -1;
		else return ret;
	}
	template <unsigned int bbytes>
	int cmp(const Bignum<bbytes> &b) const
	{
		int i;
		unsigned int al = length();
		unsigned int bl = b.length();
		if(al > bl) return 1;
		if(al < bl) return -1;
		for(i=al-1; i>=0; i--)
		{
			if(data[i] > b.data[i]) return 1;
			if(data[i] < b.data[i]) return -1;
		}
		return 0;
	}
	*/
};

#endif
