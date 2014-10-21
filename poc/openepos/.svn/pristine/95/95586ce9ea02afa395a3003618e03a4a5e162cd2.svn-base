#include <alarm.h>
#include <nic.h>
//#include <my_transceiver.h>

__USING_SYS;
OStream cout;

int main()
{
	cout<<"On!\n";
	NIC *t = new NIC();
//	Transceiver_Controller *t = new Transceiver_Controller();
//	t->init();

	/*
	char mmsg[45];
	for(int i=0;i<40;i++)
		mmsg[i]='0'+i;
	while(true)
	{
		Alarm::delay(1000000);
		cout << "Sending\n";
		t->send((unsigned char *)mmsg,40);
	}
	return 0;

	*/

	char msg[45];
	while(true)
	{
//		Alarm::delay(500000);
		cout << "Receiving\n";
		int sz = t->receive((unsigned char *)msg);
		cout << "Received: \n";
		for(int i=0;i<sz;i++)
			cout	   << msg[i];
		cout<<'\n'<<sz<<'\n';
	}

	return 0;
}
