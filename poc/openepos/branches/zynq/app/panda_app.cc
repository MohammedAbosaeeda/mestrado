// Dummy application for the PandaBoard platform

#include <utility/ostream.h>
#include <alarm.h>
#include <chronometer.h>
__USING_SYS

OStream cout;
/*
void activate_cpu_interface(){
	CPU::out32(0xF8F01000, 1);
}*/
int main() {
	int x;
	long long y;

	cout << ">>Main begin<<\n";
	
	//this first delay is here to give enough time to
	//_elapsed to increment to at least 1
	Alarm::delay(10000);

	Chronometer chrono;
	chrono.start();

	x=1000*1000*1000; //microseconds

	cout << "Counting for "<<x/1000000<<" seconds." << endl;
	Alarm::delay(x);
	
	int v[2*1024*1024]; //2 mbs
	int w[2*1024]; //2 mbs
	int z[2*1024*1024]; //2 mbs
	for(int i=0; i<2*1024*1024; ++i)
	for(int j=0; j<1024/10; ++j)
	//for(int k=0; i<2*1024*1024; ++k)
	{
		v[i]=(i*j);//it is going to overflow, but I don't care
		w[j]=(i);
		//z[k]=(j*k);
	}



	x = chrono.read();
	chrono.stop();

	cout << "x = "<< x << " (" << x/1000000 << " seconds)." << '\n';
	
	kout << "Main end!\n";
	kout << "Entering infinite loop\n";
	while(1);
	return 0;
}
