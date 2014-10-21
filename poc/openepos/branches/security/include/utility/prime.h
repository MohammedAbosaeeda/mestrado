// EPOS Prime Utility Declarations

#ifndef __prime_h
#define __prime_h

#include <system/config.h>
#include <utility/math.h>
__BEGIN_SYS

namespace Prime {

/*
 * Erastotenes Sierve to compute prime numbers, actually this is to verify if it is a prime number
 */
bool isPrime(int n){
	unsigned int max_value;// = 10000;
	unsigned int v[max_value+1];
	unsigned int i,j;
	float square;
	int primes[100]; //array com 100 números primos

	square = Math::sqrt((const float)max_value);
	for(i=2;i<=max_value;i++)
		v[i] = i;
	for(i=2;i<=square;i++){
		if(v[i] == i){
			primes[i] = i;//adicionar i aos primos
			for(j=i+j;j<=max_value;j+=i)
				v[j] = 0;
		}
	}
	bool isPrime = false;
	for(i=0;i<sizeof(primes);i++){
		if(primes[i] == n || n == 1)
			isPrime = true;
	}

	return isPrime;

}
};

__END_SYS

#endif
