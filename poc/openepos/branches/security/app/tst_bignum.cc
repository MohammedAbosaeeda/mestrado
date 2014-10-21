#include <utility/string.h>
#include <alarm.h>
#include <utility/bignum.h>
#include <utility/random.h>

__USING_SYS;

OStream cout;

int main()
{
	Alarm::delay(2000000);
	unsigned int i,k,r;
	const unsigned int size = 32;
//	cout << "Number: " << number << " Length: " << b.length() << '\n';
//	cout << "=======\n";
	int seed = 79;//time(NULL);
	cout<<"Seed = "<<seed<<'\n';
	Pseudo_Random::seed(seed);
	bool tst;
	unsigned int INT_MAX = -1;

	Bignum<size> b;
	Bignum<size> bb;
	for(i=0; i<INT_MAX; i++)
	{
		if(!(i%50000))
			cout<<i<<'\n';
		r = Pseudo_Random::random()%(INT_MAX-i);
		b = i;
		bb = r;
		b += bb;
		k = b.to_int();
		tst = (i+r == k);
//		cout<<"OKIS1\n";
		if(!tst)
		{
			cout << "Error3\n";
			cout << i << " " << r << " " << k << '\n';
			cout << b << '\n';
			cout << bb << '\n';
			return 1;
		}

		r = Pseudo_Random::random()%(INT_MAX);
		b = i;
		bb = r;
		b -= bb;
		k = b.to_int();
		tst = (i-r==k) || (r-i==k);
//		cout<<"OKIS3\n";
		if(!tst)
		{			
			cout << "Error5\n";
			cout << i << " " << r << " " << k << '\n';
			cout << b << '\n';
			cout << bb << '\n';
			return 1;
		}

		if(i>0)
			r = Pseudo_Random::random()%((INT_MAX)/i);		
		b = i;
		bb = r;

		b *= bb;
		k = b.to_int();
		tst = (i*r==k);
//		cout<<"OKIS5\n";
		if(!tst)
		{			
			cout << "Error7\n";
			cout << i << " " << r << " " << k << " " << i*r << '\n';
			cout << b << '\n';
			cout << b.to_int() << '\n';
			b = i*r;
			cout << b << '\n';
			cout << b.to_int() << '\n';
			cout << bb << '\n';
			cout << bb.to_int() << '\n';
			return 1;
		}

		r = Pseudo_Random::random()%(INT_MAX-i)+i;
		b = i;
		bb = r;
		b /= bb;
		k = b.to_int();
		tst = (i/r==k);
//		cout<<"OKIS7\n";
		if(!tst)
		{			
			cout << "Error9\n";
			cout << i << " " << r << " " << k << '\n';
			cout << b << '\n';
			cout << bb << '\n';
			cout << i/r << '\n';
			return 1;
		}

		r = Pseudo_Random::random();
		if(r==0) r++;
		b = i;
		bb = r;
		b %= bb;
		k = b.to_int();
		tst = ((i%r)==k);
//		cout<<"OKIS9\n";
		if(!tst)
		{			
			cout << "Error11\n";
			cout << i << " " << r << " " << k << '\n';
			cout << b << '\n';
			cout << bb << '\n';
			cout << i%r << '\n';
			return 1;
		}
	//	cout << b << '\n';
	//	cout << i << " + " << j << " == " << (int)k << '\n';
	}
//	cout << "Add ok\n\n\n";
	return 0;
}
