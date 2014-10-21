#ifndef __bignum_base_h
#define __bignum_base_h
#include <cassert>
#include <iostream>

using namespace std;

class Bignum_Base
{
	public:
	// Some operations assume base 256
//	static const unsigned int base = 256;

	typedef unsigned int digit;
	typedef unsigned long long double_digit;
	static const unsigned int sz_digit = sizeof(digit);
	static const unsigned int bits_in_digit = sz_digit * 8;

	protected:
	static unsigned int barrett_reduce(digit * n0, unsigned int len0, 
			const digit * mod, unsigned int modlen,
			const digit * u, unsigned int ulen
			) __attribute__((noinline))
	{
		digit q[modlen*2],
			  q1[modlen*2],
			  r1[modlen*2],
			  r2[modlen*2],
			  r[modlen*2],
			  bk[modlen*2];
		digit q2[modlen*2+1],
			  q3[modlen*2+1];
		unsigned int lenq, lenq1, lenr1, lenr2, lenr, lenq2, lenq3, lenbk;	
		lenq1 = base_div(q1, n0, len0, modlen-1);
		lenq2 = mult(q2, q1, lenq1, u, ulen);
		lenq3 = base_div(q3, q2, lenq2, modlen+1);

		// r1 = n0 % (base^(modlen+1))
		lenr1 = copy(r1, n0, len0);
		for(int i=modlen*2-1;i>=modlen+1;i--)
			r1[i] = 0;
		while((lenr1 > 0) && (r1[lenr1-1] == 0)) lenr1--;
		/*
		// r1 = n0 % (base^(modlen+1))
		lenr1 = 0;
		for(int i = 0; i < modlen*2; i++)
		{
			r1[i] = 0;
			if(i < len0-1 && i <= modlen) 
			{
				r1[i] = n0[i];
				if(r1[i] != 0) lenr1 = i+1;
			}
		}
		*/
		lenr2 = mult(r2, q3, lenq3, mod, modlen);
		for(int i = modlen*2-1; i >= modlen+1; i--)
			r2[i] = 0;
		while((lenr2 > 0) && (r2[lenr2-1] == 0)) lenr2--;
		for(int i = 0; i<modlen*2; i++)
			bk[i] = 0;
		bk[modlen+1] = 1;
		lenbk = modlen+2;
		int c = cmp(r1, lenr1, r2, lenr2);
		if(c < 0)
			lenr1 = add(r1, r1, lenr1, bk, lenbk);
		lenr1 = sub(r1, r1, lenr1, r2, lenr2);
		while(cmp(r1, lenr1, mod, modlen) >= 0)
			lenr1 = sub(r1, r1, lenr1, mod, modlen);
		copy(n0, r1, lenr1);
		return lenr1;
	}

	static unsigned int from_uint(digit *ret, unsigned int n) //__attribute__( ( noinline ) )
	{
		unsigned int i;
		if(sizeof(n) <= sz_digit)
		{
			ret[0] = n;
			return (n != 0);
		}
		else
		{
			for(i=0; n != 0 ; i++)
			{
				ret[i] = n;
				n >>= bits_in_digit;
			}
			return i;
		}
	}
	/*
	   unsigned int to_uint(const unsigned char *n, unsigned int len)
	   {
	   unsigned int ret = 0;
	   unsigned int i;
	   unsigned int mult = 1;
	   for(i=0;i<len;i++)
	   {
	   ret += len[i] * mult;
	   mult *= base;
	   }
	   return ret;
	   }
	   unsigned int from_dec_string(unsigned char *ret, const char *dec_string, unsigned int string_size)
	   {
	   unsigned int ret_len = 0;
	   char ten = 10;
	   unsigned int i=0;
	   ret_len = add(ret, ret_len, &(dec_string[i]-'0'), 1);
	   for(i++; i<string_size; i++)
	   {
	   ret_len = mult(ret, ret_len, &ten, 1);
	   ret_len = add(ret, ret_len, &(dec_string[i]-'0'), 1);
	   }
	   return ret_len;
	   }
	   */

	static int cmp(const digit *n0, unsigned int len0,
			const digit *n1, unsigned int len1) __attribute__( ( noinline ) )
			{
				if(len0 > len1) return 1;
				if(len0 < len1) return -1;
				return cmp_equal_length(n0, n1, len1);
			}

	// If inlining cmp, noinline this one for a better size X performance.
	static int cmp_equal_length(const digit *n0, const digit *n1, 
			unsigned int len) //__attribute__( ( noinline ) )
	{
		for(int i=len-1; i>=0; i--)
		{
			if(n0[i] > n1[i]) return 1;
			if(n0[i] < n1[i]) return -1;
		}
		return 0;
	}

	static int cmp(const digit *n0, unsigned int len0,
			unsigned int b)__attribute__( ( noinline ) )
			{
				int ret = 0;
				unsigned int i;
				if(sizeof(b) <= sz_digit)
				{
					if(len0 > 1) return 1;
					else return n0[0] > (digit)b;
				}
				else
				{
					for(i=0; i<len0; i++)
					{
						digit now = b;
						if(n0[i] > now) ret = 1;
						else if(n0[i] < now) ret = -1;
						b >>= bits_in_digit;
					}
					if(b > 0) return -1;
					else return ret;
				}
			}

	static unsigned int add(digit *ret, 
			const digit *n0, unsigned int len0,
			const digit *n1, unsigned int len1)__attribute__( ( noinline ) )
			{
				unsigned int gs = (len0 > len1 ? len0 : len1);
				unsigned int i;
				unsigned int lret = 0;
				digit carry = 0;
				double_digit tmp;
				for(i=0; i<gs; i++)
				{
					tmp = carry;
					if(len0 > i)
						tmp += n0[i];
					if(len1 > i)
						tmp += n1[i];
					if((ret[i] = tmp) > 0)
						lret = i+1;
					carry = tmp >> bits_in_digit;
				}
				if(carry != 0)
					if((ret[i] = carry) > 0)
						lret = i+1;
				return lret;
			}

	static unsigned int sub(digit *res, 
			const digit *n0, unsigned int len0,
			const digit *n1, unsigned int len1)__attribute__( ( noinline ) )
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
				digit borrow = 0;
				for(i=0;i<len1;i++)
				{
					double_digit anow = n0[i];
					double_digit bnow = (double_digit)n1[i] + (double_digit)borrow;
					borrow = (anow < bnow);
					if((res[i] = anow + (borrow * ((double_digit)(((double_digit)1)<<bits_in_digit))) - bnow) > 0) ret=i+1;		
				}
				for(; borrow>0 || i<len0; i++)
				{
					double_digit anow = n0[i];
					if(anow < (double_digit)borrow)
					{
						anow += (double_digit)(((double_digit)1)<<bits_in_digit) - borrow;
						borrow = 1;
					}
					else
					{
						anow -= borrow;
						borrow = 0;
					}
					if((res[i] = anow) > 0) ret=i+1;
				}
				return ret;
			}

	// Assuming quo != n1 and rem != n1
	static unsigned int div_mod(digit *quo,
			digit *rem, unsigned int &rem_len,
			const digit *n0, unsigned int len0,
			const digit *n1, unsigned int len1)__attribute__( ( noinline ) )
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
					digit aux[len0];
					ret = div_mod(aux, rem, rem_len, n0, len0, n1, len1);
					for(i=0;i<ret;i++)
						quo[i] = aux[i];
					return ret;
				}
				if(rem == n0)
				{
					digit aux[len0];
					unsigned int aux1;
					ret = div_mod(quo, aux, aux1, n0, len0, n1, len1);
					for(i=0;i<aux1;i++)
						rem[i] = aux[i];
					rem_len = aux1;
					return ret;
				}

				/*
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
				*/
				digit acc[len0];
				unsigned int acc_len;
				rem[0] = n0[len0-1];
				rem_len = 1;
				for(i=len0-2; ; i--)
				{
					acc_len = 0;
					acc[0] = 0;
					acc_len = add(acc, acc, acc_len, n1, len1);
					if(cmp(acc,acc_len,rem,rem_len) > 0)
						quo[ret++] = 0;
					else
					{
						// will overflow when j==base, yielding j==0
						for(digit j=1; j>0; j++)
						{
							acc_len = add(acc, acc, acc_len, n1, len1);
							if(cmp(acc,acc_len,rem,rem_len) > 0)
							{
								quo[ret++] = j;
								break;
							}
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
	static unsigned int digit_mult(
			unsigned char *n0, unsigned int len0,
			unsigned char a)
	{
		for(int i=0; i<len0; i++)
		{
			int m = (int)a * (int)n0[i];
			int n = (int)n0[i] + m;
			res[i] = (char)n;
			n >> sizeof(char);
			res[i+1] += n;
			m >> sizeof(char);
			res[i+1]
		}
	}

	// res = n0 + a*n1
	static unsigned int mult_acc(unsigned char *res, 
			unsigned char *n0, unsigned int len0,
			unsigned char a,
			unsigned char *n1, unsigned int len1)
	{
		
	}
	*/
	static unsigned int mult(digit *res, 
			const digit *n0, unsigned int len0,
			const digit *n1, unsigned int len1) __attribute__( ( noinline ) )
			{
				unsigned int i;
				unsigned int ret = 0;

				if((len0==0) || (len1==0)) return 0;

				/*
				digit a[len0+len1];
				for(i=0;i<len0+len1;i++) a[i] = 0;
				*/

				if(res == n0)
				{
					base_mult(res, res, len0, len1-1);
					n0 += len1-1;
					if(res == n1)
						n1 += len1-1;
				}

				unsigned int ss = len0 + len1;
				unsigned int j, k;
				double_digit carry = 0;
				double_digit r0=0, r1=0, r2=0;

				for(i=0;i<ss-1;i++)
				{
					for(j=0;j<len0 && j<=i;j++)
					{
						k = i - j;
						if(k>=0 && k<len1)
						{
							double_digit n0j = n0[j];
							double_digit n1k = n1[k];
							double_digit prod = n0j * n1k;
							r0 += (digit)prod;
							r1 += (prod >> bits_in_digit) + (r0 >> bits_in_digit);
							r0 = (digit)r0;
							r2 += r1 >> bits_in_digit;
							r1 = (digit)r1;

							/*
							double_digit n0j, n1k;
							n0j = n0[j];
							n1k = n1[k];
							double_digit quewry = carry;
							cout << carry << endl << n0j << endl << n1k <<endl;
							carry += (n0j * n1k);
							cout << carry << endl;
							assert(carry >= quewry);
							*/
						}
					}
					if((res[i] = r0) != 0)
						ret = i+1;
					r0 = r1;
					r1 = r2;
					r2 = 0;
					/*
					if((res[i] = carry) != 0)
						ret = i+1;
					carry >>= bits_in_digit;
					*/
				}
				if(r0 > 0)
				{
					res[i] = r0;
					ret = i+1;
				}
				/*
				if(carry != 0)
				{
					res[i] = carry;
					ret = i+1;
				}
				carry >>= bits_in_digit;
				assert(carry == 0);
				*/
				return ret;
			}

	// Multiply by base to the power of n1 (shift left)
	// Shifting right because of little-endianness
	static unsigned int base_mult(digit *res, 
			const digit *n0, unsigned int len0,
			unsigned int n1)//__attribute__( ( noinline ) )
			{
				if(len0 == 0)
					return 0;
				if(n1 == 0)
				{
					if(res == n0) return len0;
					else return copy(res, n0, len0);
				}
				unsigned int ret = len0 + n1;
				int i;
				for(i=ret-1; i>=n1; i--)
				{
					res[i] = n0[i-n1];
					res[i-n1] = 0;
				}
				for(;i>=len0;i--) res[i] = 0;
				return ret;
			}

	// Divide by base to the power of n (shift right)
	// Shifting left because of little-endianness
	static unsigned int base_div(digit *res, 
			const digit *n0, unsigned int len0,
			unsigned int n1)//__attribute__( ( noinline ) )
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

	static unsigned int copy(digit *n0, const digit *n1, unsigned int len1)
		__attribute__((noinline))
	{ unsigned int i; for(i=0; i<len1; i++) n0[i] = n1[i]; return i;}

};
/*
   template <unsigned int bbytes>
   Bignum<bytes>& operator*=(const Bignum<bbytes>& b)
   {
/*
x = x1B^m + x0
y = y1B^m + y0
xy = z2B^(2m) + z1B^m + z0
z2 = x1y1
z0 = x0y0
z1 = (x1 + x0)(y1 + y0) - z2 - z0
*/
/*
   const unsigned int max = IF_INT < (bbytes > bytes) , bbytes , bytes >::Result;
   if(max <= 2)
   return ( *this = this->to_int() * b.to_int() );
   else
   {
   int i;
   const unsigned int m = max/2;
   const unsigned int mx = (m > bytes ? m : bytes);
   const unsigned int my = (m > bbytes ? m : bbytes);
   Bignum<m> x0, y0;
   Bignum<m+2> x1(0), y1(0);
   Bignum<max> z0, z1, z2;

   x0.set_data(data, mx);
   if(mx < bytes)
   x1.set_data(data+mx, bytes-mx);

   y0.set_data(data, my);
   if(my < bbytes)
   y1.set_data(b.data+my, bbytes-my);

   z2 = x1; z2 *= y1;
   z0 = x0; z0 *= y0;

   z1 = x1; z1 += x0;
   y1 += y0;
   z1 *= y1;
   z1 -= z2;
   z1 -= z0;

 *this = z2;
 *this <<= m;
 *this += z1;
 *this <<= m;
 *this += z0;

 return *this;
 }
 */

#endif
