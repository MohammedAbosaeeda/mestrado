#include "bignum_base.h"

unsigned int Bignum_Base::from_uint(unsigned char *ret, unsigned int n)
{
	unsigned int i;
	for(i=0; n != 0 ; i++)
	{
		ret[i] = n % base;
		n /= base;
	}
	return i;
}

int Bignum_Base::cmp(const unsigned char *n0, unsigned int len0,
		const unsigned char *n1, unsigned int len1)
{
	if(len0 > len1) return 1;
	if(len0 < len1) return -1;
	for(int i=len0-1; i>=0; i--)
	{
		if(n0[i] > n1[i]) return 1;
		if(n0[i] < n1[i]) return -1;
	}
	return 0;
}

int Bignum_Base::cmp(const unsigned char *n0, unsigned int len0,
					 unsigned int b)
{
	int ret = 0;
	unsigned int i;
	for(i=0; i<len0; i++)
	{
		int now = b % base;
		if(n0[i] > now) ret = 1;
		else if(n0[i] < now) ret = -1;
		b /= base;
	}
	if(b > 0) return -1;
	else return ret;
}

unsigned int Bignum_Base::add(unsigned char *ret, 
		const unsigned char *n0, unsigned int len0,
		const unsigned char *n1, unsigned int len1)
{
	unsigned int gs = (len0 > len1 ? len0 : len1);
	unsigned int i, tmp;
	unsigned int lret = 0;
	char carry = 0; 
	for(i=0; i<gs; i++)
	{
		tmp = carry;
		if(len0 > i)
			tmp += n0[i];
		if(len1 > i)
			tmp += n1[i];
		if((ret[i] = tmp % base) > 0)
			lret = i+1;
		carry = tmp / base;
	}
	if(carry != 0)
		if((ret[i] = carry) > 0)
			lret = i+1;
	return lret;
}

unsigned int Bignum_Base::sub(unsigned char *res, 
		const unsigned char *n0, unsigned int len0,
		const unsigned char *n1, unsigned int len1)
{
	int tmp = cmp(n0, len0, n1, len1);
	unsigned int ret = 0;
	if(tmp==0)
	{ 
		res[0] = 0;
		return 0;
	}
	else if(tmp < 0) 
		return sub(res, n1, len1, n0, len0);

	unsigned int i;
	int borrow = 0;
	for(i=0;i<len1;i++)
	{
		int anow = ((int)n0[i]) - borrow;
		int bnow = n1[i];
		borrow = (anow < bnow);
		if((res[i] = anow + (borrow * base) - bnow) > 0) ret=i+1;		
	}
	for(; borrow>0 || i<len0; i++)
	{
		int anow = ((int)n0[i]) - borrow;
		borrow = (anow < 0);
		if((res[i] = anow + (borrow * base)) > 0) ret=i+1;
	}
	return ret;
}

// Assuming quo != n1 and rem != n1
unsigned int Bignum_Base::div_mod(unsigned char *quo,
		unsigned char *rem, unsigned int &rem_len,
		const unsigned char *n0, unsigned int len0,
		const unsigned char *n1, unsigned int len1)
{
	if(len1 == 0) return 0;
	unsigned int ret = 0;
	if(len0 == 0)
	{
		quo[0] = 0;
		ret = 0;
		rem[0] = 0; 
		rem_len = 0;
		return ret;
	}
	int i;
	if(quo == n0)
	{
		unsigned char aux[len0];
		ret = div_mod(aux, rem, rem_len, n0, len0, n1, len1);
		for(i=0;i<ret;i++)
			quo[i] = aux[i];
		return ret;
	}
	if(rem == n0)
	{
		unsigned char aux[len0];
		unsigned int aux1;
		ret = div_mod(quo, aux, aux1, n0, len0, n1, len1);
		for(i=0;i<aux1;i++)
			rem[i] = aux[i];
		rem_len = aux1;
		return ret;
	}

	int c = cmp(n0, len0, n1, len1);
	if(c == 0)
	{
		quo[0] = 1; 
		ret = 1;
		rem[0] = 0; 
		rem_len = 0;
		return ret;
	}
	if(c < 0)
	{
		for(i=0; i<len0; i++)
			rem[i]=n0[i];
		rem_len = len0;
		quo[0] = 0;
		ret = 0;
		return ret;
	}
	unsigned char acc[len0];
	unsigned int acc_len;
	rem[0] = n0[len0-1];
	rem_len = 1;
	for(i=len0-2; ; i--)
	{
		acc_len = 0;
		for(int j=0; j<base; j++)
		{
			acc_len = add(acc, acc, acc_len, n1, len1);
			if(cmp(acc,acc_len,rem,rem_len) > 0)
			{
				quo[ret++] = j;
				break;
			}
		}
		acc_len = sub(acc, acc, acc_len, n1, len1);
		rem_len = sub(rem, rem, rem_len, acc, acc_len);			

		if(i < 0)
			break;

		unsigned int len = ++rem_len;
		int j;
		for(j=len-1;j>0;j--)
			rem[j] = rem[j-1];
		rem[0] = n0[i];
	}
	int j = ret-1;
	// inverting quo
	for(i=0; i<j; i++, j--)
	{			
		quo[i] ^= quo[j];
		quo[j] ^= quo[i];
		quo[i] ^= quo[j];
	}

	for(; ret > 0 && quo[ret-1]==0; ret--);
	return ret;
}
/*
unsigned int Bignum_Base::mult(unsigned char *res, 
		const unsigned char *n0, unsigned int len0,
		const unsigned char *n1, unsigned int len1)
{
	unsigned int i;
	unsigned int ret = 0;

	if((len0==0) || (len1==0)) return 0;
	if(res == n0)
	{
		base_mult(res, res, len0, len1-1);
		n0 += len1-1;
	}
	
	if(true)//(len0 <= 8) || (len1 <= 8))
	{
//			kout << "BASE\n";
//	kout << "len0 " << len0 << " len1 " << len1 << '\n';
//		kout << len1 << " " << len0 << '\n';
		unsigned int ss = len0 + len1;
		unsigned int j, k;
		unsigned int carry = 0;

		for(i=0;i<ss-1;i++)
		{
			for(j=0;j<len0 && j<=i;j++)
			{
				k = i - j;
				if(k>=0 && k<len1)
				{
					unsigned int n0j, n1k;
					n0j = n0[j];
					n1k = n1[k];
					carry += (n0j * n1k);
				}
			}		
			if((res[i] = (carry % base)) != 0)
				ret = i+1;
			carry /= base;
		}
		if(carry != 0)
		{
			res[i] = carry;
			ret = i+1;
		}
	}
	return ret;
}
*/
// Multiply by base to the power of n1 (shift left)
// Shifting right because of little-endianness
unsigned int Bignum_Base::base_mult(unsigned char *res, 
		const unsigned char *n0, unsigned int len0,
		unsigned int n1)
{
	if(len0 == 0)
	{
		res[0] = 0;
		return 0;
	}
	if(n1 == 0)
	{
		if(res == n0) return len0;
		else return copy(res, n0, len0);
	}
	unsigned int ret = len0 + n1;
	for(int i=ret; i>=n1; i--)
	{
		res[i] = n0[i-n1];
		res[i-n1] = 0;
	}
	return ret;
}
// Divide by base to the power of n (shift right)
// Shifting left because of little-endianness
unsigned int Bignum_Base::base_div(unsigned char *res, 
		const unsigned char *n0, unsigned int len0,
		unsigned int n1)
{ 
	if(len0 <= n1)
	{
		res[0] = 0;
		return 0;
	}
	unsigned int ret = len0 - n1;
	for(int i=0;i<ret;i++)
		res[i] = n0[i+n1];
	return ret;
}
