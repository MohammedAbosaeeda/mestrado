#define DEBUG_BIGNUM
#include "bignum.h"
#include <iostream>
#include <cstdlib>

using namespace std;

int main()
{
	Bignum<32> a;
	Bignum<16> b;
	unsigned char adat[16];
	unsigned char bdat[16];
	while(1)
	{
		for(int i=0; i<sizeof(adat); i++)
			adat[i] = random();
		for(int i=0; i<sizeof(bdat); i++)
			bdat[i] = random();
		a.set_data(adat, sizeof(adat));
		b.set_data(bdat, sizeof(bdat));

//		a *= a;
//		a /= b;
		a += b;
		a -= b;
	}
	return 0;	
}
