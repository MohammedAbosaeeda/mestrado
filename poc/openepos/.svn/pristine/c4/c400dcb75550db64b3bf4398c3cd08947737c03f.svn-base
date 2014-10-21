//EPOS Syscall Handler Abstraction Implementation

#include <syscall_handler.h>
#include <message.h>
#include <response.h>
#include <syscall_types.h>
#include <thread.h>
#include <object_table.h>
#include <system.h>
#include <semaphore_agent.h>
#include <task_agent.h>
#include <cpu.h>
#include <utility/ostream.h>
#include <msrs.h>

__BEGIN_SYS

typedef CPU::GDT_Entry GDT_Entry;

Syscall_Handler::Syscall_Handler() {}
Syscall_Handler::~Syscall_Handler() {}

Syscall_Response Syscall_Handler::sysenter(Syscall_Message &message) {
	Syscall_Response response;
	OStream cout;

	//TODO GPF probably because of MSRs values. Check all of them
	wrmsr(IA32_SYSENTER_CS, IA32::SEL_SYS_CODE); //The lower 16 bits of this MSR are the segment selector for the privilege level 0 code segment.
	wrmsr(IA32_SYSENTER_EIP, ((CPU::Reg64) &handle) & 0x00000000FFFFFFFF);
	wrmsr(IA32_SYSENTER_ESP, Memory_Map<PC>::SYS_STACK);

	//Checking if values are being recorded in the MSRs
	cout << (void *) rdmsr(IA32_SYSENTER_CS) << " - CS\n";
	cout << (void *) rdmsr(IA32_SYSENTER_EIP) << " - EIP\n";
	cout << (void *) rdmsr(IA32_SYSENTER_ESP) << " - ESP\n";

	//TODO Load parameter (Syscall_Message) into %eax, app stack pointer
	//ASMV("movl %0, %%eax" : : "r"(&message));
	//ASMV("movl %0, %ecx"); //StackPointer
	//ASMV("movl %0, %edx"); //Return address

	cout << "Before sysenter\n";

	//TODO GPF here
	ASMV("SYSENTER");

	cout << "After sysenter\n";

	//TODO get return from register
	return response;
}

void Syscall_Handler::sysexit(Syscall_Message &message) {
	wrmsr(IA32_SYSENTER_CS, CPU::GDT_SYS_CODE);

	ASMV("popal");
	ASMV("SYSEXIT");
}

void Syscall_Handler::wrmsr(CPU::Reg32 reg, CPU::Reg64 v) {
	CPU::Reg32 low, high;
	OStream cout;

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

CPU::Reg64 Syscall_Handler::rdmsr(CPU::Reg32 reg) {
        CPU::Reg64 v;
        asm volatile("rdmsr" : "=A" (v) : "c" (reg));
        return v;
}

Syscall_Response handle() {
	OStream cout;

	cout << "INSIDE HANDLE - GOT IT\n";

	//TODO Read Syscall_Message from register %eax

    Task* s = Task::self();    
    Syscall_Message message;

    Syscall_Response r;

    switch(message.method_id) {
        case SEMAPHORE_CONSTRUCTOR:
            r = Semaphore_Agent::constructor(s->table_semaphore(), message);
            break;
        
        case SEMAPHORE_DESTRUCTOR:
            r = Semaphore_Agent::destructor(s->table_semaphore(), message);
            break;
 
        case SEMAPHORE_P:
            r = Semaphore_Agent::p(s->table_semaphore(), message);
            break;
 
        case SEMAPHORE_V:
            r = Semaphore_Agent::v(s->table_semaphore(), message);
            break;
        
        case TASK_CONSTRUCTOR:
            cout << "Parametro no syscall: " << *(void**)message.params[1] << " : " <<*(int*)(*(void**)(message.params[1])) << "\n";
            r = Task_Agent::constructor(s->table_task(), message);
            break;
        
        case TASK_DESTRUCTOR:
            r = Task_Agent::destructor(s->table_task(), message);
            break;
    }

    return r;
}

__END_SYS
