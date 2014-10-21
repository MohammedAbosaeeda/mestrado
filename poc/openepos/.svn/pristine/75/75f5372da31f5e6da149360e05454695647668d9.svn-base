#include "bignum.h"
#include "diffiehellman.h"
#include "stack_tst.cpp"
#include <iostream>

using namespace std;

void alice()
{
	cout << "===== Alice" << endl;
	const int seed = 2;
	srand(seed);
	Diffie_Hellman a;
	cout << "SEED: " << seed << endl << "Public: "; //<< a.Y() << endl;

//  Bob's public parameters for unsigned char.
//	unsigned char x[] = {91, 142, 145, 157, 180, 140, 202, 70, 210, 42, 251, 114, 7, 35, 45, 172};
//	unsigned char y[] = {20, 242, 137, 94, 206, 43, 233, 106, 134, 158, 114, 211, 166, 53, 202, 68};
		
//  Bob's public parameters for unsigned short??
//	unsigned char x[] ={175, 62, 41, 129, 89, 64, 105, 6, 53, 193, 216, 75, 36, 94, 58, 121};
//	unsigned char y[] ={244, 126, 134, 9, 199, 39, 215, 104, 77, 127, 146, 185, 103, 144, 184, 183};

//  Bob's public parameters for unsigned int.
//	unsigned char x[] ={98, 66, 93, 10, 243, 114, 95, 200, 182, 16, 253, 162, 217, 14, 136, 195};
//	unsigned char y[] ={162, 50, 228, 227, 2, 240, 169, 206, 105, 116, 57, 229, 64, 236, 200, 227};
	unsigned char x[] ={217, 14, 136, 195, 182, 16, 253, 162, 243, 114, 95, 200, 98, 66, 93, 10};
	unsigned char y[] ={64, 236, 200, 227, 105, 116, 57, 229, 2, 240, 169, 206, 162, 50, 228, 227};

	Diffie_Hellman::ECC_Point Yb;
	Yb.x.set_data(x,sizeof(x));
	Yb.y.set_data(y,sizeof(y));
	Yb.z = Bignum<1>(1);
	a.calculate_key(Yb);
	cout << Yb << endl;
}
void bob()
{
	cout << "===== Bob" << endl;
	const int seed = 1;
	srand(seed);
	Diffie_Hellman a;
	cout << "SEED: " << seed << endl << "Public: " << a.Y() << endl;

//  Alice's public parameters for unsigned char.
//	unsigned char x[] = {134, 163, 100, 235, 205, 244, 62, 7, 124, 79, 109, 95, 51, 45, 131, 191};
//	unsigned char y[] = {48, 173, 61, 116, 163, 229, 15, 139, 118, 67, 174, 81, 69, 143, 242, 71};

//  Alice's public parameters for unsigned short??
//	unsigned char x[] ={253, 59, 42, 189, 184, 107, 244, 20, 192, 190, 84, 220, 194, 195, 208, 5};
//	unsigned char y[] ={58, 7, 124, 85, 5, 241, 107, 207, 44, 173, 230, 110, 204, 152, 31, 57};

//  Alice's public parameters for unsigned int.
	unsigned char x[] ={128, 45, 129, 74, 67, 127, 119, 65, 33, 10, 83, 194, 134, 123, 153, 63};
	unsigned char y[] ={126, 99, 57, 17, 73, 50, 194, 101, 116, 151, 182, 96, 165, 81, 36, 193};
//	unsigned char x[] ={134, 123, 153, 63, 33, 10, 83, 194, 67, 127, 119, 65, 128, 45, 129, 74};
//	unsigned char y[] ={165, 81, 36, 193, 116, 151, 182, 96, 73, 50, 194, 101, 126, 99, 57, 17};


	Diffie_Hellman::ECC_Point Yb;
	Yb.x.set_data(x,sizeof(x));
	Yb.y.set_data(y,sizeof(y));
	Yb.z = Bignum<1>(1);
	a.calculate_key(Yb);
	cout << endl;
}

int main()
{
//	alloc_stack(2048);
	alice();
//	cout << check_stack(2048) << endl;
	bob();
	return 0;
}
