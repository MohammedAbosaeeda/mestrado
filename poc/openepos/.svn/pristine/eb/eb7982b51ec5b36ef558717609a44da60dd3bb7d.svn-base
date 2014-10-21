#include <utility/random.h>
#include <utility/ostream.h>
#include <thread.h>
#include <clock.h>

__USING_SYS

OStream cout;
Clock clock;

int main()
{
	unsigned long int ini = clock.now();
	cout << "Unix time right now is " << ini << ". Let it be our initial seed.\n";
	unsigned long int seed = Pseudo_Random::random(ini);
    cout << "Therefore, our first random number is also " << seed << ".\n\n";
    
    cout << "Now we're gonna try to generate 10 new random numbers.\n"
    	 << "Our n will be the Unix time above plus the amount of already\n"
    	 << "generated numbers. Hence, our first n will be the Unix time itself.\n";
	int i = 0;
	for (;i < 10;i++){
		seed = Pseudo_Random::random(ini+i);
		cout << seed << "\n";
	}
	int d = 6;
	cout << "\nAnd those were our numbers. Now let's keep generating new\n"
		 << "numbers this way until we have at least 10 numbers with\n"
		 << d << " digits. \n"
		 << "To accomplish that, the next n will be the Unix time plus the\n"
		 << "amount of trials. So our first n will be the Unix time again.\n\n";
	int j = 0;
	i = 0;
	unsigned long int k = 0;
	while (i < 10){
		seed = Pseudo_Random::random(ini+k);
		if ((seed > 99999)&&(seed < 1000000)){
			cout << seed << "\n";
			i++;
		}
		j++;
		k++;
		if (k == 4294967295){
			cout << "Stop! Patience Overflow! Sorry, something went wrong. Bye!\n";
			Thread::self()->exit();
		}
	}
	cout << "\nDone. We had to generate " << j << " numbers until\n"
		 << "at least 10 of them were numbers with " << d << " digits.\n\n";

    return 0;
}
