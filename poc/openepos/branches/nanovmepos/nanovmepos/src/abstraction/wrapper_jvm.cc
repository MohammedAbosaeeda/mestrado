#include <utility/ostream.h>
#include <wrapper_jvm.h>
#include <cpu.h>
//#include <flash.h>

#include <machine.h>

__USING_SYS
OStream wrapper_cout;
//Flash flash;
//void epos_memcpy_P(/*const void* src, u16_t len*/){
  //  CPU::out8(Machine::IO::DDRB, 0xff);
  //  CPU::out8(Machine::IO::PORTB, 0x00);

//	while(1);
 /*	int i;
	
	u08_t* source = (u08_t*)src;
	unsigned char* dst; //= (u08_t*) dest;		

	for(i = 0; i < len; i++){
		*dst = (char) flash.read((unsigned int)source);
	//	dst++;
		source++;
	}

//	dst -= len;
*///	return 0;
//}

//static void c_int_enable(){
//	CPU::int_enable();
//}

//static void c_int_disable(){
//	CPU::int_disable();
//}

void epos_putc(char a) {
	wrapper_cout << a;
}

void epos_printi_hex(int a){
	wrapper_cout << (void*) a << "\n";
}

void epos_prints(char * str) {
	wrapper_cout << str;
}

void epos_printi(int a){
	wrapper_cout << a << "\n";
}
