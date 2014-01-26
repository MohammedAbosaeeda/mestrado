/** Light and Temperature sensor demo
 *  Things to change at EPOS head: 
 *	- At $EPOS/include/mach/atmega128/mts300.h at the 2 sample functions,
 *	  the delay between adc's conversions must be increased (0xffff, instead
 *	  of 0xfff) or one reading will cause noise at the next one.
 *
 *	- In order to increase the resolution of readings for the graphic tool,
 *	  at the same functions, it should be returned the raw value, 
 *	  without conversion.
 *	  Instead of "return convert_int(v);", just "return v;"
 *	  
 */

#include <display.h>
#include <machine.h>
#include <nic.h>

__USING_SYS

struct Message {
    unsigned int src;
    unsigned int dst;
    unsigned int accel_x;
    unsigned int accel_y;
    unsigned int temperature;
    unsigned int light;
};

Display disp;

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

static unsigned int FIRST_ID = 0x01;
static unsigned int LAST_ID  = 0x02;

static unsigned int MY_ID    = 0x01;
static unsigned int SINK_ID  = 0x33;

void sensor() {
    CPU::out8(Machine::IO::DDRA, 0x07);
    CPU::out8(Machine::IO::PORTA, ~0);

    unsigned char count;

    Accelerometer accel;
    Temperature_Sensor temperature;
    Photo_Sensor photo;

    NIC nic;

    unsigned char src, prot;
    unsigned int size;

    Message msg;

    CPU::out8(Machine::IO::PORTA, 0x5);
    while(1) {
        if(nic.receive(&src, &prot, &msg, sizeof(msg)) && (msg.dst == MY_ID)) {

            CPU::out8(Machine::IO::PORTA, ~++count);

            msg.src = MY_ID;
            msg.dst = SINK_ID;
            msg.accel_x = accel.sample_x();
            msg.accel_y = accel.sample_y();
            msg.temperature = temperature.sample();
            msg.light = photo.sample();	   

            nic.send(0, 0, &msg, sizeof(msg)); 
        }

        memset(&msg,sizeof(msg),0);
    }
}

int sink(){
    CPU::out8(Machine::IO::DDRA, 0x07);
    CPU::out8(Machine::IO::PORTA, ~0);

    NIC nic;

    Message msg;

    unsigned char src, prot, count=0;
 
    CPU::out8(Machine::IO::PORTA, 0x2);
    for(;;){
            msg.src = SINK_ID;
            msg.dst = MY_ID;
	    
	do {
            nic.send(0, 0, &msg, sizeof(msg));
	} while (!(nic.receive(&src, &prot, &msg, sizeof(msg)) && (msg.dst == SINK_ID)));
    
	CPU::out8(Machine::IO::PORTA, (unsigned char)~++count);

	puti(msg.light);
	disp.puts("\t");
	puti(msg.temperature);
	disp.puts("\n");
	for(int i=0; i<0xffff; i++);
    }
}

int main() {
	sensor();
//	sink();
}

