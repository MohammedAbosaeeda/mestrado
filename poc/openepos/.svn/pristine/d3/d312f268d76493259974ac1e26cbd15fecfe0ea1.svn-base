#include <utility/math.h>

__BEGIN_SYS

namespace Math {

#if defined(LUA_FP)

#else

static int UEXP; //flag indicating a positive int was expected but received a negative one

int pow(int x, int y)
{
	if (y < 0){
		y = -y;	//only unsigned exponents
		UEXP = 1;
	}
	int flag;
	unsigned int x2;
	if (x < 0){
		flag = -1;
		x2 = -x;
	}else if (x > 0){
		if (x == 1) return 1;
		flag = 1;
		x2 = x;
	}else
		return 0;
	int increasing = 0;
	int decreasing = INT_MAX;
	int result = 1;
	while (increasing < y){
		increasing++;
		decreasing /= x2;
		if (decreasing <= 1)
			return INT_MAX;
		result *= x2;
	}
	return result * flag;
}

int sqrt(int x)
{
	if (x < 0){
		x = -x;	//only unsigned exponents
		UEXP = 1;
	}
	int i = 0;
	while (i < INT_MAX){
		if (pow(i,2) > x)
			return --i;
		i++;
	}
	return -1;
}

int modf(int x, int* ip)
{
	ip = &x;
	return 0;
}

int fabs(int x)
{
	if (x < 0)
		return -x;
	else
		return x;
}

#endif

}

__END_SYS
