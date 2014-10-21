// PC_LOADER
//
// Desc: LOADER is created by INIT as the first process when the system
//	 is configured to support multiple tasks. It creates all
//	 application tasks from the image received in ??? and then waits
//	 wait for them to finish.
//
// Author: guto
// Documentation: $EPOS/doc/loader			Date: 22 May 2003

#include <utility/ostream.h>
#include <utility/malloc.h>

//#include <mach/pc/nic.h>

#include "mux2/includes/MuxWrapper.h"
#include "wrapper/WStream.h"

__USING_SYS

int main() 
{


	OStream o;	
	o << "This is EPOS testing ethernet packet loss!\n";
	
	WFILE * f = wfopen("/exp", "r");
	
	char buf[1500];

	int i = 0;

//	while(i < 56000) {
	for(; i < 3; i++) {

		wfread_searching(buf, 1500, 1, f, 'B');

		//if(!(i % 1000))
		o << i++ << '\n';


	}

	while(1);

/*
	OStream o;
        o << "This is EPOS testing malloc!\n";

	unsigned char * b;
	int i = 0;

	while(1) {

		b = new unsigned char[5000];
		o << i++ << '\n';
		delete(b);

	}
*/

/*
        wprintf("This is EPOS running mux2!\n");
	mux_main();
	

	Thread * t = Thread::self();
	t->suspend(); // =D

	while(1)
		wprintf("HUR\n");

*/

/*
	wprintf("This is EPOS testing receive buffers!\n");

        WFILE * f = wfopen("xu", "r");
        char buf[500];

//	for(int i = 0; i < 6; i++)
	while(1)
                wfread(buf, 500, 1, f);

*/
/*
	OStream cout;

        cout << "This is EPOS testing pretty fucking basic logic!\n";

	PC_NIC receive_nic(0);
	PC_NIC send_nic(1);

	PC_NIC::Address receiver(0x00, 0x11, 0xD8, 0x17, 0xCB, 0x79, 0x00, 0x00);
	PC_NIC::Protocol prot = 24952;

	unsigned char * buf = (unsigned char *) malloc(1500);
	unsigned char * buf_ptr = buf;

	int i = 0;

	while(i < 21) {

		while(buf_ptr != buf + 188 * 7) {

			cout << "Iteration " << i << " buf: " << buf << " buf_ptr: " << buf_ptr << " buf + off:  " << buf + 188 * 7 << '\n';

			memset(buf, 'A', 188);
			buf_ptr += 188;

			i++;

		}

		cout << "Before send! buf: " << buf << " buf_ptr: " << buf_ptr << " buf + off:  " << buf + 188 * 7 << '\n';
		send_nic.send(receiver, prot, buf, 188 * 7);
		cout << "After send! buf: " << buf << " buf_ptr: " << buf_ptr << " buf + off:  " << buf + 188 * 7 << '\n';
		buf_ptr = buf;
		cout << "After ptr update! buf: " << buf << " buf_ptr: " << buf_ptr << " buf + off:  " << buf + 188 * 7 << '\n';
	}

	cout << "done\n";
	while(1);
  */ 
/*
	wprintf("This is EPOS relaying stuff through the wrapper!\n");

        WFILE * f = wfopen("/exp", "r");
        WFILE * f2 = wfopen("outf", "w");

	char buf[1500];

	while(1) {
                wfread(buf, 1500, 1, f);
		wfwrite(buf, 1500, 1, f2);
	}
*/

/*
	OStream cout;

	cout << "This is EPOS detecting MAC addresses!\n";

        PC_NIC receive_nic(0);
        PC_NIC send_nic(1);

        PC_NIC::Address receiver(0x00, 0x11, 0xD8, 0x17, 0xCB, 0x79, 0x00, 0x00);
        PC_NIC::Protocol prot = 24952;

	char frase1[] = "Sou a send NIC\n";
	char frase2[] = "Sou a recv NIC\n";

	while(1) {
	        send_nic.send(receiver, prot, frase1, sizeof(frase1));
        	receive_nic.send(receiver, prot, frase2, sizeof(frase2));

		cout << "Sending!\n";
	}
*/

	
	return 0;  // pelo amor de deus, isso nunca ocorre =D -augusto

}

