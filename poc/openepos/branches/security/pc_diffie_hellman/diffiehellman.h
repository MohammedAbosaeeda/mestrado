#ifndef DIFFIEHELLMAN_H
#define DIFFIEHELLMAN_H

#include "bignum.h"
#include <ostream>

using namespace std;

class Diffie_Hellman
{
public:
	const static unsigned int KEY_SIZE = 16;
//	const static unsigned int KEY_SIZE = 20;
//	const static unsigned int KEY_SIZE = 24;
//	const static unsigned int KEY_SIZE = 32;
//	const static unsigned int KEY_SIZE = 256;
	typedef Bignum<KEY_SIZE> DHBignum;
	// Using Jacobian coordinates, where:
	// (X,Y) = (x/(z^2), y/(z^3))
	struct ECC_Point
	{
		DHBignum x, y, z;
		ECC_Point &operator= (const ECC_Point &b)
		{
			this->x = b.x;
			this->y = b.y;
			this->z = b.z;
			return *this;
		};
	};

protected:
	DHBignum _private;
	DHBignum _prime; //public prime
//	DHBignum _primitive_root;
	DHBignum _key;
//	DHBignum _Y;

	// Constants for Montgomery exponentiation
//	DHBignum Rmodm;
//	DHBignum R2modm;
	Bignum<KEY_SIZE+1> barrett_u;
//	unsigned int mp;


	ECC_Point _base_point;
	ECC_Point _Y;
	
	friend ostream &operator<< (ostream &out, const ECC_Point &b) 
	{
		unsigned int i;
		out << "Point:\nx: ";
		out << b.x;
		out << "\ny: ";
		out << b.y;
		out << "\nz: ";
		out << b.z;
		return out; 
	}

public:
	/*
	Diffie_Hellman(
	//	unsigned char private_key_raw_data[KEY_SIZE],
		unsigned char prime_raw_data[KEY_SIZE],
		unsigned char primitive_root_raw_data[KEY_SIZE],
		unsigned char Rmodm_raw_data[KEY_SIZE],
		unsigned char R2modm_raw_data[KEY_SIZE],
		unsigned int _mp
		);
		*/
	Diffie_Hellman();

	void calculate_key(DHBignum yb);
	void calculate_key(ECC_Point yb);
	void calculate_private();
	// Getters

//	DHBignum Y() { return _Y; }
	ECC_Point Y() { return _Y; }
	DHBignum key() { return _key; }
	DHBignum prime() { return _prime ; }
//	DHBignum primitive_root() { return _primitive_root; }
	// Setters
//	void prime(DHBignum p);
//	void primitive_root(DHBignum p);

	// Utils
	// x and y should have the same size
	template<unsigned int bytes>
	void barrett_reduce(Bignum<bytes> &x){ x.barrett_reduce(_prime, barrett_u); }//barrett_reduce((unsigned char *)x.data, x.length()*x.sz_digit);};
	void barrett_reduce(unsigned char *x, unsigned int len);
	/*
	DHBignum montgomery_mult(const DHBignum &x, const DHBignum &y);
	DHBignum montgomery_exp(const DHBignum &x, const DHBignum &exponent);
	DHBignum montgomery_reduce(const DHBignum &x)
	{
		return montgomery_mult(x, R2modm);
	}
	DHBignum montgomery_unreduce(const DHBignum &x)
	{
		return montgomery_mult(x, DHBignum(1u));
	}
	*/
	
	ECC_Point ECC_mult(const DHBignum& k, const ECC_Point& p);
	DHBignum mod_inv(const DHBignum &x);
	void ECC_jacobian_double(ECC_Point &a);
	void ECC_add_jacobian_affine(ECC_Point &a, const ECC_Point &b);
	ECC_Point& ECC_add_affine_affine(ECC_Point &a, const ECC_Point &b);
};

#endif // DIFFIEHELLMAN_H
