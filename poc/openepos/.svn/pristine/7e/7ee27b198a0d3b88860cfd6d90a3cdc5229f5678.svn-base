#include <display.h>
#include <machine.h>
#include <nic.h>

__USING_SYS

NIC nic;
Display disp;

unsigned int count;

unsigned char src, prot;
const unsigned int size = 2;

void puti(unsigned long v) {
	
    unsigned long major = 1000000000;
    unsigned char digit;
    
    while(major >= 10) {
	digit = '0';
	while(v >= major) {
	    digit++;
	    v -= major;
	}
	disp.putc(digit);
	major /= 10;
    }
    disp.putc('0' + v);
    
}

void send() {
    count = 0;
    while(1) {
	CPU::out8(Machine::IO::PORTA, ~(unsigned char)++count);
	nic.send(0, 0, &count, 2);
    }

}

void recv() {

    unsigned int i = 0;
    while(i < 0xff){
	count = 0;

	if(nic.receive(&src, &prot, &count, 2))
	    i++;

	CPU::out8(Machine::IO::PORTA, ~(unsigned char)count);
    }

    disp.puts("Sent: ");
    puti(count);

    disp.puts("     Recv: ");
    puti(nic.statistics().rx_packets);

    disp.puts("     Drop: ");
    puti(nic.statistics().dropped_packets);

    disp.putc('\n');
	
}



int main()
{
    CPU::out8(Machine::IO::DDRA, 0xff);
    CPU::out8(Machine::IO::PORTA, ~0);


		
    send();
    //recv();


}
