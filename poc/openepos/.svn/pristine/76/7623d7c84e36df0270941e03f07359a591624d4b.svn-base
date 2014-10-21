#include "diffiehellman.h"
#include <iostream>
#include <cassert>

//Diffie_Hellman::DHBignum::digit Diffie_Hellman::_prime.data[] = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253, 255, 255, 255};

using namespace std;
/* Constants for Montgomery exponentiation
 * These must be re-set if the prime is changed!
 * Some code assumes that the base is 256.
 *
 * R = (DHBignum::base)^(KEY_SIZE) 
 *
 * R is only needed to compute the 3 constants below,
 * so you don't need to actually store it.
 *
 * Rmodm  = R % _prime
 * R2modm = (R^2) % _prime
 *
 * mp = -_prime^(-1) % DHBignum::base 
 * (where p^(-1) % m is the modular multiplicative inverse of p modulo m)
 */
/*
Diffie_Hellman::Diffie_Hellman(
	//	unsigned char private_key_raw_data[KEY_SIZE],
		unsigned char prime_raw_data[KEY_SIZE],
		unsigned char primitive_root_raw_data[KEY_SIZE],
		unsigned char Rmodm_raw_data[KEY_SIZE],
		unsigned char R2modm_raw_data[KEY_SIZE],
		unsigned int _mp
		)
{
	_prime.set_data(prime_raw_data, KEY_SIZE);
	_primitive_root.set_data(primitive_root_raw_data, KEY_SIZE);
	Rmodm.set_data(Rmodm_raw_data, KEY_SIZE);
	R2modm.set_data(R2modm_raw_data, KEY_SIZE);
	mp = _mp;
	calculate_private();
}
*/


Diffie_Hellman::Diffie_Hellman()
{	
	if(KEY_SIZE == 16)
	{
		unsigned char barrett_u_raw_data[] = {17, 0, 0, 0, 8, 0, 0, 0, 4, 0, 0, 0, 2, 0, 0, 0, 1};
		//prime: 340282366762482138434845932244680310783			
		unsigned char prime_raw_data[] = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 253, 255, 255, 255};
//		unsigned char primitive_root_raw_data[] = {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//		unsigned char Rmodm_raw_data[] = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2};
//		unsigned char R2modm_raw_data[] = {17, 0, 0, 0, 8, 0, 0, 0, 4, 0, 0, 0, 36};
//		unsigned int _mp = 1;
		
		unsigned char x_raw_data[] = {134, 91, 44, 165, 124, 96, 40, 12, 45, 155, 137, 139, 82, 247, 31, 22};
		unsigned char y_raw_data[] = {131, 122, 237, 221, 146, 162, 45, 192, 19, 235, 175, 91, 57, 200, 90, 207};

		barrett_u.set_data(barrett_u_raw_data, sizeof(barrett_u_raw_data));
		_prime.set_data(prime_raw_data, sizeof(prime_raw_data));
//		_primitive_root.set_data(primitive_root_raw_data, sizeof(primitive_root_raw_data));
//		Rmodm.set_data(Rmodm_raw_data, sizeof(Rmodm_raw_data));
//		R2modm.set_data(R2modm_raw_data, sizeof(R2modm_raw_data));
//		mp = _mp;
		_base_point.x.set_data(x_raw_data, sizeof(x_raw_data));
		_base_point.y.set_data(y_raw_data, sizeof(y_raw_data));
		calculate_private();

		/*
		// 204957496037838376002989446472925645899: 128-bit prime 
		// in little-endian format.
		unsigned char prime_raw_data[] = 
		{ 0x4B, 0xC0, 0xC7, 0xFE, 0x01, 0x46, 0x6A, 0xD7, 0xF7, 0xE3, 0x29, 0xB3, 0xBD, 0x60, 0x31, 0x9A, };
		unsigned char primitive_root_raw_data[] =
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		unsigned char Rmodm_raw_data[] = 
		{ 181, 63, 56, 1, 254, 185, 149, 40, 8, 28, 214, 76, 66, 159, 206, 101 };
		unsigned char R2modm_raw_data[] = 
		{ 44, 124, 93, 135, 12, 129, 243, 86, 23, 247, 34, 98, 36, 225, 147, 14 };		
		unsigned int _mp = 157;
		_prime.set_data(prime_raw_data, KEY_SIZE);
		_primitive_root.set_data(primitive_root_raw_data, KEY_SIZE);
		Rmodm.set_data(Rmodm_raw_data, KEY_SIZE);
		R2modm.set_data(R2modm_raw_data, KEY_SIZE);
		mp = _mp;
		calculate_private();		
		*/
	}

	/*
	else if(KEY_SIZE == 20)
	{
		//160-bit parameters
		unsigned char prime_raw_data[] = {
			0xD3, 0xF2, 0xE9, 0x95, 0xA9, 0xA2, 0x48, 0x16, 0xBA,	0x6D, 0x29, 0xD3, 0xE4, 0x3E, 0x1A, 0x9F, 0x3E, 0x69, 0xB7, 0xD3, 
		};
		unsigned char primitive_root_raw_data[] =
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		unsigned char Rmodm_raw_data[] = 
		{ 45 , 13 , 22 , 106 , 86 , 93 , 183 , 233 , 69 , 146 , 214 , 44 , 27 , 193 , 229 , 96 , 193 , 150 , 72 , 44 };
		unsigned char R2modm_raw_data[] = 
		{ 7 , 13 , 215 , 72 , 25 , 15 , 121 , 176 , 94 , 53 , 130 , 18 , 1 , 107 , 156 , 111 , 3 , 163 , 145 , 32 , };
		unsigned int _mp = 165;
		_prime.set_data(prime_raw_data, KEY_SIZE);
		_primitive_root.set_data(primitive_root_raw_data, KEY_SIZE);
		Rmodm.set_data(Rmodm_raw_data, KEY_SIZE);
		R2modm.set_data(R2modm_raw_data, KEY_SIZE);
		mp = _mp;
		calculate_private();
	}

	else if(KEY_SIZE == 24)
	{
// 192-bit parameters
		unsigned char prime_raw_data[] = {211, 224, 137, 65, 144, 93, 149, 34, 13, 80, 86, 101, 49, 224, 223, 89, 220, 138, 173, 173, 21, 174, 136, 141};
		unsigned char primitive_root_raw_data[] = {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		unsigned char Rmodm_raw_data[] = {45, 31, 118, 190, 111, 162, 106, 221, 242, 175, 169, 154, 206, 31, 32, 166, 35, 117, 82, 82, 234, 81, 119, 114};
		unsigned char R2modm_raw_data[] = {99, 189, 67, 141, 78, 129, 211, 99, 65, 241, 58, 180, 205, 149, 247, 243, 75, 107, 61, 131, 82, 48, 197, 125};
		unsigned int _mp = 165;
		_prime.set_data(prime_raw_data, KEY_SIZE);
		_primitive_root.set_data(primitive_root_raw_data, KEY_SIZE);
		Rmodm.set_data(Rmodm_raw_data, KEY_SIZE);
		R2modm.set_data(R2modm_raw_data, KEY_SIZE);
		mp = _mp;
		calculate_private();
	}

	else if(KEY_SIZE == 32)
	{
		// 256-bit parameters
		unsigned char prime_raw_data[] = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 255, 255, 255, 255};
		unsigned char primitive_root_raw_data[] = {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		unsigned char Rmodm_raw_data[] = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 254, 255, 255, 255};
		unsigned char R2modm_raw_data[] = {3, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 251, 255, 255, 255, 254, 255, 255, 255, 255, 255, 255, 255, 253, 255, 255, 255, 4};
		unsigned int _mp = 1;

		unsigned char x_raw_data[] = {150, 194, 152, 216, 69, 57, 161, 244, 160, 51, 235, 45, 129, 125, 3, 119, 242, 64, 164, 99, 229, 230, 188, 248, 71, 66, 44, 225, 242, 209, 23, 107};
		unsigned char y_raw_data[] = {245, 81, 191, 55, 104, 64, 182, 203, 206, 94, 49, 107, 87, 51, 206, 43, 101, 22, 158, 15, 124, 74, 235, 231, 142, 155, 127, 26, 254, 226, 66, 227, 79};

		_prime.set_data(prime_raw_data, sizeof(prime_raw_data));
		_primitive_root.set_data(primitive_root_raw_data, KEY_SIZE);
		Rmodm.set_data(Rmodm_raw_data, KEY_SIZE);
		R2modm.set_data(R2modm_raw_data, KEY_SIZE);
		mp = _mp;
		_base_point.x.set_data(x_raw_data, sizeof(x_raw_data));
		_base_point.y.set_data(y_raw_data, sizeof(y_raw_data));
		calculate_private();
		*/
		/*
		// 256-bit parameters
		unsigned char prime_raw_data[] = {19, 225, 139, 22, 207, 183, 114, 185, 6, 128, 128, 37, 226, 243, 244, 23, 173, 31, 77, 196, 78, 209, 213, 39, 68, 193, 197, 82, 206, 142, 212, 248};
		unsigned char primitive_root_raw_data[] = {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		unsigned char Rmodm_raw_data[] = {237, 30, 116, 233, 48, 72, 141, 70, 249, 127, 127, 218, 29, 12, 11, 232, 82, 224, 178, 59, 177, 46, 42, 216, 187, 62, 58, 173, 49, 113, 43, 7};
		unsigned char R2modm_raw_data[] = {181, 21, 30, 196, 247, 50, 115, 228, 114, 121, 173, 21, 60, 201, 76, 14, 216, 184, 171, 138, 185, 228, 47, 254, 229, 139, 147, 151, 83, 50, 137, 215};
		unsigned int _mp = 229;
		_prime.set_data(prime_raw_data, KEY_SIZE);
		_primitive_root.set_data(primitive_root_raw_data, KEY_SIZE);
		Rmodm.set_data(Rmodm_raw_data, KEY_SIZE);
		R2modm.set_data(R2modm_raw_data, KEY_SIZE);
		mp = _mp;
		calculate_private();
		*/
	/*
	}
	else if(KEY_SIZE == 256)
	{
// 2048-bit parameters
		unsigned char prime_raw_data[] = {67, 79, 227, 173, 4, 246, 49, 238, 132, 242, 56, 43, 59, 13, 10, 56, 144, 180, 73, 6, 160, 56, 111, 16, 111, 87, 243, 50, 174, 187, 247, 41, 232, 79, 110, 249, 138, 22, 43, 102, 77, 118, 89, 249, 20, 49, 23, 97, 188, 188, 69, 67, 195, 200, 213, 251, 177, 242, 117, 231, 7, 81, 116, 82, 115, 102, 222, 125, 120, 79, 249, 130, 49, 160, 77, 223, 217, 100, 104, 136, 213, 61, 216, 150, 76, 1, 139, 206, 28, 103, 245, 78, 94, 151, 255, 247, 4, 194, 98, 159, 0, 109, 113, 146, 237, 194, 119, 110, 148, 97, 57, 45, 212, 113, 163, 246, 157, 204, 93, 155, 166, 177, 194, 156, 26, 44, 199, 60, 156, 38, 245, 253, 69, 60, 112, 54, 246, 220, 70, 132, 182, 72, 59, 54, 99, 225, 121, 120, 138, 116, 22, 188, 191, 17, 247, 28, 28, 223, 178, 48, 7, 56, 36, 175, 176, 12, 184, 203, 109, 83, 26, 167, 145, 14, 225, 55, 83, 195, 80, 222, 137, 43, 128, 73, 200, 170, 34, 224, 142, 25, 75, 17, 60, 21, 130, 84, 137, 223, 14, 33, 167, 6, 251, 103, 12, 93, 227, 181, 56, 97, 95, 245, 239, 207, 144, 116, 40, 103, 137, 238, 194, 210, 99, 61, 85, 79, 242, 147, 135, 9, 176, 35, 176, 127, 216, 230, 60, 209, 59, 223, 119, 90, 203, 77, 56, 19, 104, 43, 178, 71, 56, 140, 104, 6, 114, 238};
		unsigned char primitive_root_raw_data[] = {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		unsigned char Rmodm_raw_data[] = {189, 176, 28, 82, 251, 9, 206, 17, 123, 13, 199, 212, 196, 242, 245, 199, 111, 75, 182, 249, 95, 199, 144, 239, 144, 168, 12, 205, 81, 68, 8, 214, 23, 176, 145, 6, 117, 233, 212, 153, 178, 137, 166, 6, 235, 206, 232, 158, 67, 67, 186, 188, 60, 55, 42, 4, 78, 13, 138, 24, 248, 174, 139, 173, 140, 153, 33, 130, 135, 176, 6, 125, 206, 95, 178, 32, 38, 155, 151, 119, 42, 194, 39, 105, 179, 254, 116, 49, 227, 152, 10, 177, 161, 104, 0, 8, 251, 61, 157, 96, 255, 146, 142, 109, 18, 61, 136, 145, 107, 158, 198, 210, 43, 142, 92, 9, 98, 51, 162, 100, 89, 78, 61, 99, 229, 211, 56, 195, 99, 217, 10, 2, 186, 195, 143, 201, 9, 35, 185, 123, 73, 183, 196, 201, 156, 30, 134, 135, 117, 139, 233, 67, 64, 238, 8, 227, 227, 32, 77, 207, 248, 199, 219, 80, 79, 243, 71, 52, 146, 172, 229, 88, 110, 241, 30, 200, 172, 60, 175, 33, 118, 212, 127, 182, 55, 85, 221, 31, 113, 230, 180, 238, 195, 234, 125, 171, 118, 32, 241, 222, 88, 249, 4, 152, 243, 162, 28, 74, 199, 158, 160, 10, 16, 48, 111, 139, 215, 152, 118, 17, 61, 45, 156, 194, 170, 176, 13, 108, 120, 246, 79, 220, 79, 128, 39, 25, 195, 46, 196, 32, 136, 165, 52, 178, 199, 236, 151, 212, 77, 184, 199, 115, 151, 249, 141, 17};
		unsigned char R2modm_raw_data[] = {84, 88, 123, 66, 202, 82, 199, 61, 229, 59, 119, 110, 59, 177, 12, 164, 70, 55, 72, 59, 44, 175, 202, 85, 52, 247, 54, 13, 111, 80, 187, 191, 137, 86, 145, 202, 135, 230, 161, 197, 165, 66, 197, 238, 139, 207, 187, 148, 89, 212, 92, 243, 40, 129, 4, 154, 176, 15, 110, 216, 211, 192, 155, 30, 107, 112, 120, 203, 225, 23, 221, 226, 109, 75, 254, 83, 45, 24, 81, 242, 207, 62, 121, 99, 0, 209, 206, 213, 88, 212, 98, 75, 198, 200, 177, 151, 33, 61, 177, 171, 125, 206, 95, 78, 138, 54, 210, 58, 134, 55, 190, 24, 37, 92, 175, 67, 18, 192, 174, 0, 115, 229, 213, 92, 130, 104, 198, 160, 18, 145, 0, 6, 88, 106, 56, 194, 221, 140, 117, 100, 128, 175, 64, 86, 87, 134, 168, 16, 119, 24, 186, 185, 87, 222, 92, 66, 105, 173, 60, 189, 104, 142, 127, 153, 169, 109, 33, 182, 127, 13, 130, 82, 41, 225, 78, 230, 223, 77, 92, 255, 124, 46, 75, 79, 156, 241, 193, 238, 37, 139, 85, 245, 91, 60, 65, 11, 72, 204, 165, 194, 198, 111, 247, 117, 193, 249, 23, 163, 130, 89, 233, 224, 118, 165, 3, 33, 155, 13, 52, 170, 53, 25, 82, 175, 177, 137, 77, 221, 52, 20, 44, 165, 25, 165, 161, 173, 69, 128, 168, 147, 29, 165, 126, 95, 193, 66, 106, 1, 155, 156, 174, 161, 68, 201, 114, 56};
		unsigned int _mp = 149;
		_prime.set_data(prime_raw_data, KEY_SIZE);
		_primitive_root.set_data(primitive_root_raw_data, KEY_SIZE);
		Rmodm.set_data(Rmodm_raw_data, KEY_SIZE);
		R2modm.set_data(R2modm_raw_data, KEY_SIZE);
		mp = _mp;
		calculate_private();
	}
	*/
	else cout << "Unsupported key size! " << KEY_SIZE << '\n';
}

//void Diffie_Hellman::prime(DHBignum p) { _prime = p; }
//void Diffie_Hellman::primitive_root(DHBignum p) { _primitive_root = p; }

void Diffie_Hellman::calculate_private()
{
	_private.random();
	cout << "randomized"<< endl;
	barrett_reduce(_private);//_private %= _prime;
	   cout << "Private " << _private << endl;
	   cout << "Prime " << _prime << endl;
//	   cout << "Primitive Root " << _primitive_root << endl;
	   cout << "Base Point " << _base_point << endl;
//	Chronometer c;
//	c.start();
	_Y = ECC_mult(_private, _base_point);
	//_Y = _base_point; //For debugging only
//	_Y = montgomery_exp(_primitive_root, _private);
//	c.stop();
//	   cout << "Time to modpow: " << c.read() << endl;
	   cout << "Send to Slave " << _Y << endl;
}

void Diffie_Hellman::calculate_key(ECC_Point  Yb)
{
	cout <<"DH Calculate_key " << Yb << endl;
//	Chronometer c;
//	c.start();
	Yb = ECC_mult(_private, Yb);
//	cout << "Result: "<< Yb << endl;
	for(int i=0;i<_key.size;i++)
		_key.data[i] = Yb.x.data[i] ^ Yb.y.data[i];
	_key._length = _key.size;
//	c.stop();
//	cout << "Time to modpow: " << c.read() << endl;
	cout << "Key " << _key << endl;
}
void Diffie_Hellman::calculate_key(DHBignum  Yb)
{
//	cout <<"DH Calculate_key " << Yb << endl;
//	Chronometer c;
//	c.start();
//	_key = montgomery_exp(Yb, _private);
//	c.stop();
//	cout << "Time to modpow: " << c.read() << endl;
//	cout << "Key " << _key << endl;
}

void Diffie_Hellman::barrett_reduce(unsigned char *xx, unsigned int xlen)
{
	Bignum<KEY_SIZE*3> x(xx,xlen);
	Bignum<KEY_SIZE*3> q1(x), r1, r2, r;
	Bignum<KEY_SIZE*3 +1> q3;
	int i;
	q1 >>= _prime.size-1;
	q3.eq_mult(q1, barrett_u);
	q3 >>= _prime.size+1;
	r1 = x;
	for(i=r1.size-1;i>=_prime.size+1;i--)
		r1.data[i] = 0;
	while((r1._length > 0) && (r1.data[r1._length-1] == 0)) r1._length--;
	r2.eq_mult(q3,_prime);
	for(i=r2.size-1;i>=_prime.size+1;i--) 
		r2.data[i] = 0;
	while((r2._length > 0) && (r2.data[r2._length-1] == 0)) r2._length--;
	Bignum<KEY_SIZE*2> bk(1);
	bk <<= _prime.size+1;
	x = r1;
	if(x < r2)
		x += bk;
	x -= r2;
	while(x >= _prime)
		x -= _prime;
}
/*
//Output: x * y * (R^(-1)) mod _prime
Diffie_Hellman::DHBignum Diffie_Hellman::montgomery_mult(const DHBignum &x, const DHBignum &y)
{
	if((x.size != y.size) || (x.size!=_prime.size)) 
	{
		cout << "=======ERROR ON MONTGOMERY_MULT\n";
		cout << "=======" << x.size << '\n';
		cout << "=======" << y.size << '\n';
		cout << "=======" << _prime.size << '\n';
	}
	if((!(x < _prime)) || (!(y < _prime))) cout << "=======ERROR 2 ON MONTGOMERY_MULT\n" << x << endl << y << endl;
	Bignum<KEY_SIZE+3> A(0u);
	Bignum<KEY_SIZE+1> maux(0u);
	Bignum<KEY_SIZE+1> yaux(0u);
	unsigned int y0 = ((y.length() == 0) ? 0 : y.data[0]);
	unsigned int A0, xi, ui;
	for(unsigned int i=0;i<_prime.size;i++)
	{
		A0 = ((A.length() == 0) ? 0 : A.data[0]);
		xi = ((i < x.length()) ? x.data[i] : 0);
		ui = (DHBignum::digit)((A0 + xi*y0) * mp); 
		maux = ui;
		yaux = xi;
		maux *= _prime;
		yaux *= y;
		A += yaux;
		A += maux;
		A >>= 1;
//		cout << A << endl;
	}
	if(A >= _prime)
		A -= _prime;
	DHBignum ret(A);
	return ret;
}
*/

void Diffie_Hellman::ECC_jacobian_double(ECC_Point &a)
{
	Bignum<KEY_SIZE*2> A, B, C, aux, aux2, Y;

	/*
	A = montgomery_reduce(Bignum<1>(4));
	A = montgomery_mult(A, a.x);
	A = montgomery_mult(A, a.y);
	A = montgomery_mult(A, a.y);

	B = montgomery_mult(a.y, a.y);
	B = montgomery_mult(B, B);
	B = montgomery_mult(B, montgomery_reduce(Bignum<1>(8)));

	C = a.x;
	aux = montgomery_mult(a.z, a.z);
	if(C < aux)
		C += _prime;
	C -= aux;
	C = montgomery_mult(C, montgomery_reduce(Bignum<1>(3)));
	aux2 = a.x;
   	aux2 += aux;
	if(aux2 >= _prime)
		aux2 -= _prime;
	C = montgomery_mult(C, aux2);

	X = montgomery_mult(C,C);
	aux = montgomery_mult(A, montgomery_reduce(Bignum<1>(2)));
	if(X < aux)
		X += _prime;
	X -= aux;

	Y = A;
	if(Y < X)
		Y += _prime;
	Y -= X;
	Y = montgomery_mult(Y,C);
	if(Y < B)
		Y += _prime;
	Y -= B;

	Z = montgomery_mult(a.y, montgomery_reduce(Bignum<1>(2)));	
	Z = montgomery_mult(Z, a.z);
	*/
	
	A.eq_mult(a.y,a.y);
   	barrett_reduce(A);//A %= _prime;
	B.eq_mult(A,A);
   	barrett_reduce(B);//A %= _prime;
	B *= 8; barrett_reduce(B);

	A *= 4;
	barrett_reduce(A);
	A *= a.x;
	barrett_reduce(A);

	C = a.x;
	aux.eq_mult(a.z, a.z);
	barrett_reduce(aux);
	if(C < aux)
		C += _prime;
	C -= aux;
	aux2 = a.x;
	aux2 += aux; barrett_reduce(aux2);
	C *= aux2; barrett_reduce(C);
	C *= 3; barrett_reduce(C);

	a.x.eq_mult(C,C);
	barrett_reduce(a.x);
	aux = A;
	aux *= 2; barrett_reduce(aux);
	if(a.x < aux)
		a.x += _prime;
	a.x -= aux;
	
	if(A < a.x)
		A += _prime;
	A -= a.x;
	Y.eq_mult(C,A);
	barrett_reduce(Y);
	if(Y < B)
		Y += _prime;
	Y -= B;
	
	a.z.eq_mult(a.y,a.z);
	barrett_reduce(a.z);
	a.z *= 2; barrett_reduce(a.z);
	
	a.y = Y;
}

void Diffie_Hellman::ECC_add_jacobian_affine(ECC_Point &a, const ECC_Point &b)
{
	Bignum<KEY_SIZE*2> aux, aux2, X, Y, Z;
	Bignum<KEY_SIZE*2> C, D;

	/*
	aux = montgomery_mult(a.z,a.z);
	A = montgomery_mult(b.x, aux);

	B = montgomery_mult(b.y, montgomery_mult(aux, a.z));

	C = A;
	if(C < a.x)
		C += _prime;
	C -= a.x;
	
	D = B;
	if(D < a.y)
		D += _prime;
	D -= a.y;

	X = montgomery_mult(D,D);
	aux2 = montgomery_mult(C,C);
	aux = montgomery_mult(aux2,C);
	aux2 = montgomery_mult(aux2, montgomery_reduce(Bignum<1>(2)));
	aux2 = montgomery_mult(aux2, a.x);
	aux += aux2; 
	if(aux >= _prime)
		aux -= _prime;
	if(X < aux)
		X += _prime;
	X -= aux;

	Y = montgomery_mult(C,C);	
	aux = montgomery_mult(Y,C);
	Y = montgomery_mult(Y, a.x);
	if(Y < X)
		Y += _prime;
	Y -= X;
	Y = montgomery_mult(Y, D);
	aux2 = montgomery_mult(aux, a.y);
	if(Y < aux)
		Y += _prime;
	Y -= aux;

	Z = montgomery_mult(a.z, C);
	*/
	aux.eq_mult(a.z, a.z);
	barrett_reduce(aux);

	aux2.eq_mult(aux, a.z);
	barrett_reduce(aux2);

	C.eq_mult(b.x, aux);
	barrett_reduce(C);
	if(C < a.x)
		C += _prime;
	C -= a.x;
	
	D.eq_mult(b.y, aux2);
	barrett_reduce(D);
	if(D < a.y)
		D += _prime;
	D -= a.y;

	X.eq_mult(D,D);
	barrett_reduce(X);
	aux.eq_mult(C,C);
	barrett_reduce(aux);	
	aux2 = aux;
	aux *= C; barrett_reduce(aux);
	aux2 *= 2; barrett_reduce(aux2);
	aux2 *= a.x; barrett_reduce(aux2);
	aux += aux2; barrett_reduce(aux);
	if(X < aux)
		X += _prime;
	X -= aux;
	
	aux.eq_mult(a.y, C);
	a.y.eq_mult(a.x, C);
	barrett_reduce(a.y);
	a.y *= C; barrett_reduce(a.y);
	if(a.y < X)
		a.y += _prime;
	a.y -= X;
	a.y *= D; barrett_reduce(a.y);
	aux.barrett_reduce(_prime, barrett_u);
	aux *= C; barrett_reduce(aux);
	aux *= C; barrett_reduce(aux);
	if(a.y < aux)
		a.y += _prime;
	a.y -= aux;
	
	a.z *= C;
	barrett_reduce(a.z);
	

	a.x = X;
}

/*
Diffie_Hellman::ECC_Point Diffie_Hellman::ECC_mult(const DHBignum& k, const ECC_Point& p)
{
	ECC_Point res;
	ECC_Point pp;
	
	res.x = p.x;
	res.y = p.y;
	res.z = Bignum<1>(1);
	
	pp.x = res.x;
	pp.y = res.y;
	pp.z = res.z;
	int t = _prime.bits_in_digit+1;
	char naf[t]; // NAF representation of now
	unsigned char now = k.data[k.length() - 1];
	for(j=0; now >= 1; j++)
	{
		if(now % 2)
		{
			naf[j] = 2 - (now % 4);
			now -= naf[j];
		}
		else naf[j] = 0;
		now /= 2;
	}
	t = j;
	assert(false);

	for(int i=k.length()-1;i>=0;i--)
	{
		for(;t<_prime.bits_in_digit;t++)
		{
//			res = ECC_add_affine_affine(res, res);
			res = ECC_jacobian_double(res);
			//cout << "res: " << res << '\n';
			if(bin[t])
				res = ECC_add_jacobian_affine(res,p);
//				res = ECC_add_affine_affine(res, pp);
		}
		if(i>0)
		{
			now = k.data[i-1];
			for(int j=_prime.bits_in_digit-1;j>=0;j--)
			{
				bin[j] = now%2;
				now/=2;
			}
			t=0;
		}
	}
	/*
	res.x = montgomery_unreduce(res.x);
	res.y = montgomery_unreduce(res.y);
	res.z = Bignum<1>(1);
	*/

/*
	Bignum<KEY_SIZE*2> X, Z, Zi, Z3;
	/*
	Zi = mod_inv(montgomery_unreduce(res.z));
	Z = Zi; 
	Z *= Zi; Z %= _prime;
	Z3 = Z;
	Z3 *= Zi; Z3 %= _prime;
	X = montgomery_unreduce(res.x);
	X *= Z; X %= _prime;
	res.x = X;

	X = montgomery_unreduce(res.y);
	X *= Z3; X %= _prime;
	res.y = X;
	res.z = Bignum<1>(1);
	*/
/*
	Zi = mod_inv(res.z);
	Z = Zi; 
	Z *= Zi; barrett_reduce(Z);
	Z3 = Z;
	Z3 *= Zi; Z3.barrett_reduce(_prime, barrett_u);
	X = res.x;
	X *= Z; barrett_reduce(X);
	res.x = X;

	X = res.y;
	X *= Z3; barrett_reduce(X);
	res.y = X;
	res.z = Bignum<1>(1);

	return res;
}
*/
Diffie_Hellman::ECC_Point Diffie_Hellman::ECC_mult(const DHBignum& k, const ECC_Point& p)
{
	// Finding last '1' bit of k
	//cout << "Multiplying:\n" << k << '\n' << p << '\n';
	int t = _prime.bits_in_digit;
//	DHBignum k = montgomery_reduce(kk);
	DHBignum::digit now = k.data[k.length() - 1];
	bool bin[t]; // Binary representation of now
	ECC_Point res;
	ECC_Point pp;
	
	res.x = p.x;
	res.y = p.y;
	res.z = 1;

	pp.x = res.x;
	pp.y = res.y;
	pp.z = res.z;
	for(int j=_prime.bits_in_digit-1;j>=0;j--)
	{
		if(now%2) t=j+1;
		bin[j] = now%2;
		now/=2;
	}

	for(int i=k.length()-1;i>=0;i--)
	{
		for(;t<_prime.bits_in_digit;t++)
		{
			ECC_jacobian_double(res);
			if(bin[t])
				ECC_add_jacobian_affine(res,p);
		}
		if(i>0)
		{
			now = k.data[i-1];
			for(int j=_prime.bits_in_digit-1;j>=0;j--)
			{
				bin[j] = now%2;
				now/=2;
			}
			t=0;
		}
	}
	/*
	res.x = montgomery_unreduce(res.x);
	res.y = montgomery_unreduce(res.y);
	res.z = Bignum<1>(1);
	*/

	Bignum<KEY_SIZE*2> X, Z, Zi, Z3;
	/*
	Zi = mod_inv(montgomery_unreduce(res.z));
	Z = Zi; 
	Z *= Zi; Z %= _prime;
	Z3 = Z;
	Z3 *= Zi; Z3 %= _prime;
	X = montgomery_unreduce(res.x);
	X *= Z; X %= _prime;
	res.x = X;

	X = montgomery_unreduce(res.y);
	X *= Z3; X %= _prime;
	res.y = X;
	res.z = Bignum<1>(1);
	*/
	Zi = mod_inv(res.z);
	Z = Zi; 
	Z *= Zi; barrett_reduce(Z);
	Z3 = Z;
	Z3 *= Zi; barrett_reduce(Z3);
	X = res.x;
	X *= Z; barrett_reduce(X);
	res.x = X;

	X = res.y;
	X *= Z3; barrett_reduce(X);
	res.y = X;
	res.z = 1;

	return res;
}

// returns: (x^(-1)) mod _prime
Diffie_Hellman::DHBignum Diffie_Hellman::mod_inv(const DHBignum &a)
{	
	Bignum<KEY_SIZE*2> newt(1);
	Bignum<KEY_SIZE*2> newr(a);
	Bignum<KEY_SIZE*2> aux2;
	DHBignum t(0);
	DHBignum r(_prime);
	DHBignum quo, aux;

	while(newr != 0)
	{
		quo = r;
		quo /= newr;
		aux = newt;
		newt *= quo;
		barrett_reduce(newt);//newt %= _prime;
		aux2 = t;
		if(aux2 < newt)
			aux2 += _prime;
		aux2 -= newt;
		newt = aux2;
		t = aux;

		aux = newr;
		newr *= quo;
		barrett_reduce(newr);//newr %= _prime;
		aux2 = r;
		if(aux2 < newr)
			aux2 += _prime;
		aux2 -= newr;
		newr = aux2;
		r = aux;
	}

//	cout << "HERE" << a << endl << _prime << endl << t << endl;
	return t;
	
	/*
	DHBignum u(a), v(_prime);
	Bignum<KEY_SIZE*2> A(1u), C(0u);
	Bignum<1> two(2u);
	while(u != 0)
	{
		while(!(u.data[0] % 2))
		{
			u /= two;
			if(A.data[0] % 2) 
				A += _prime;
			A /= two;			
		}
		while(!(v.data[0] % 2))
		{
			v /= two;
			if(C.data[0] % 2)
				C += _prime;
			C /= two;
		}
		if(u >= v)
		{
			u -= v;
			A -= C;
		}
		else
		{
			v -= u;
			C -= A;
		}
	}
	C %= _prime;
	*/
	/*
	if(C != t)
	{
		cout << a << endl;
		cout << "C =  " << C << endl << "Right = " << t << endl;
	}
	assert(C == t);
//	cout << "HERE" << x << endl << _prime << endl << C << endl;
	return C;
	*/
}

/*
Diffie_Hellman::DHBignum Diffie_Hellman::montgomery_exp(const DHBignum &x, const DHBignum &exponent)
{
	if((!(_prime > x)) || (x==0u)) 
		cout << "=======ERROR ON MONTGOMERY_EXP\n" << x << '\n' << _prime << '\n';
	DHBignum A(0u);

	DHBignum xp(montgomery_mult(x, R2modm));
	A = Rmodm;

	// Finding last '1' bit of exponent
	// Assuming base 256
	int t = 8;
	unsigned char now = exponent.data[exponent.length() - 1];
	bool bin[8]; // Binary representation of now
	for(int j=7;j>=0;j--)
	{
		if(now%2) t=j; 
		bin[j] = now%2;
		now/=2;
	}
	//cout << "t = "<<t<<endl;
	for(int i=exponent.length()-1;i>=0;i--)
	{
		for(;t<8;t++)
		{
			A = montgomery_mult(A,A);
			if(bin[t])
				A = montgomery_mult(A,xp);
		}
		if(i>0)
		{
			now = exponent.data[i-1];
			for(int j=7;j>=0;j--)
			{
				bin[j] = now%2;
				now/=2;
			}
			t=0;
		}
	}
	A = montgomery_mult(A, DHBignum(1u));
	return A;
}
*/

Diffie_Hellman::ECC_Point& Diffie_Hellman::ECC_add_affine_affine(ECC_Point &a, const ECC_Point &b)
{	
	/*
	DHBignum l;
	Bignum<KEY_SIZE*2> num, l2;
	Bignum<KEY_SIZE+1> den;
	/*
	DHBignum l;
	DHBignum num, l2;
	DHBignum den;
	DHBignum three(3u), two(2u);
	*/
/*
	if((a.x == b.x) && (a.y == b.y))
	{
		// TODO: can I use montgomery_mult here?
		/*
		num = montgomery_mult(a.x, a.x);
		num *= montgomery_reduce(three);
		if(num < three)
			num += _prime;
		num -= three;
		den = montgomery_mult(a.y, montgomery_reduce(two));
		*/
	/*
		num = a.x;
		num *= a.x;
		num %= _prime;
//		num = barrett_reduce(num);
		
//		num %= _prime;
		//num = montgomery_exp(num, two);
		num *= 3;
		num %= _prime;
//		num = barrett_reduce(num);
		if(num < 3)
			num += _prime;
		num -= 3;

		den = a.y;
		den *= 2;
		den %= _prime;		
//		den = barrett_reduce(den);
	}
	else
	{
		num = b.y;
		if(num < a.y)
			num += _prime;
		num -= a.y;

		den = b.x;
		if(den < a.x)
			den += _prime;
		den -= a.x;
	}

	/*
	den = montgomery_unreduce(den);
	den = mod_inv(den);
	den = montgomery_reduce(den);
	num = montgomery_mult(num, den);
	l = num;	
	l2 = montgomery_mult(l,l);

	if(l2 < a.x)
		l2 += _prime;
	l2 -= a.x;

	if(l2 < b.x)
		l2 += _prime;
	l2 -= b.x;

	Bignum<KEY_SIZE*2> y3(a.x);
	a.x = l2;

	if(y3 < a.x)
		y3 += _prime;
	y3 -= a.x;

	y3 = montgomery_mult(y3, l);
	if(y3 < a.y)
		y3 += _prime;
	y3 -= a.y;

	a.y = y3;
	*/
/*	
	den = mod_inv(den);
	num *= den;
	num %= _prime;
//	num = barrett_reduce(num);
	l = num;	
	l2 = l;
//	l2 = montgomery_exp(l2, two);
	l2 *= l2;
	l2 %= _prime;
//	l2 = barrett_reduce(l2);

	if(l2 < a.x)
		l2 += _prime;
	l2 -= a.x;

	if(l2 < b.x)
		l2 += _prime;
	l2 -= b.x;


	Bignum<KEY_SIZE*2> y3(a.x);
	a.x = l2;

	if(y3 < a.x)
		y3 += _prime;
	y3 -= a.x;

	y3 *= l;
	y3 %= _prime;
//	y3 = barrett_reduce(y3);
	if(y3 < a.y)
		y3 += _prime;
	y3 -= a.y;

	a.y = y3;
*/
	return a;
}
