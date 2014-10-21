//EPOS Syscall Handler Declarations

#ifndef syscall_handler_h
#define syscall_handler_h

#include <message.h>
#include <response.h>
#include <thread.h>

__BEGIN_SYS

Syscall_Response handle();

class Syscall_Handler {
    typedef CPU::Reg32 Reg32;
    typedef CPU::Reg64 Reg64;
    friend class Thread;

public:
    Syscall_Handler();
    ~Syscall_Handler();

//    static void interrupt(unsigned int sig, Reg32 error, Syscall_Message& message);

//    static Syscall_Response int80(Syscall_Message& message);

    /*
     * Uses new syscall features available from Pentium II.
     * More efficient way of jumping to ring 0.
     */
    static Syscall_Response sysenter(Syscall_Message &message);
    static void sysexit(Syscall_Message &message);

    static void wrmsr(Reg32 reg, Reg64 v);
    static Reg64 rdmsr(Reg32 reg);
};

__END_SYS

#endif
