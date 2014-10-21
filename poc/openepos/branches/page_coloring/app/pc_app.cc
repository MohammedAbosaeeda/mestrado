#include <utility/ostream.h>
#include <thread.h>
#include <semaphore.h>
#include <alarm.h>
#include <display.h>

__USING_SYS

const int iterations = 100;

OStream cout;
Display d;

const int BUF_SIZE = 16;
char buffer[BUF_SIZE];
Semaphore empty(BUF_SIZE);
Semaphore full(0);

typedef unsigned int Reg32;
typedef unsigned long long Reg64;

enum {
    SYSENTER_CS = 0x0174,
    SYSENTER_EIP = 0x0175,
    SYSENTER_ESP = 0x0176
};

Reg64 rdmsr(Reg32 reg) {
    Reg64 v;
    asm volatile("rdmsr" : "=A" (v) : "c" (reg));
    return v;
}

void wrmsr(CPU::Reg32 reg, CPU::Reg64 v) {
    CPU::Reg32 low, high;
    
    (void) ((low) = (CPU::Reg32) v);
    (void) ((high) = (CPU::Reg32) (v >> 32));

    cout << "reg: " << (void *) reg << "\n";
    cout << "high: " << (void *) high << "\n";
    cout << "low: " << (void *) low << "\n";

    /**
     * "c" stands for ecx, "a" for eax and "d" for edx
     * asm volatile and "memory" creates a memory barrier that don't let gcc reorder the reads and writes around
     * the inline assembly
     *
     * MSR[reg] <- high:low;
     */
    ASMV("wrmsr" : /*no outputs*/ : "c" (reg), "a" (low), "d" (high) : "memory");
}

int consumer()
{
	//cout << "CONSUMER APIC timer value = " << (void *) APIC::read(APIC::LVT_TIMER) << "\n";
	cout << "CPU id consumer = " << Machine::cpu_id() << endl;
    int out = 0;
    for(int i = 0; i < iterations; i++) {
	full.p();
	cout << buffer[out];
	out = (out + 1) % BUF_SIZE;
 	Alarm::delay(1000 * out);
	empty.v();
    }
}

int main()
{	
    d.clear();
	cout << "CPU id main = " << Machine::cpu_id() << endl;
    
    Thread * cons = new Thread(&consumer);

    // producer
    int in = 0;
    for(int i = 0; i < iterations; i++) {
	empty.p();
 	cout << "P";
 	Alarm::delay(1000 * in);
	buffer[in] = 'a' + in;
	in = (in + 1) % BUF_SIZE;
	full.v();
    }
    cons->join();

    delete cons;
    
    cout << "SYS_CODE: " << (void *) Memory_Map<PC>::SYS_CODE << " CS MSR = " << (void *) SYSENTER_CS << "\n";

    /*Reg64 cs_value = (Reg64) Memory_Map<PC>::SYS_CODE;
    wrmsr(SYSENTER_CS, cs_value); //The lower 16 bits of this MSR are the segment selector for the privilege level 0 code segment.
    
    Reg64 cs = rdmsr(SYSENTER_CS);
    cout << "cs MSR = " << (void *) cs << "\n";*/
    
    unsigned int p_value = 0x40000000 + SYSENTER_CS;
    char * ptr = (char *) (p_value);
    *ptr = Memory_Map<PC>::SYS_CODE;
    cout << "ptr = " << (void *) *ptr << "\n";


	while(1);

    return 0;
}
