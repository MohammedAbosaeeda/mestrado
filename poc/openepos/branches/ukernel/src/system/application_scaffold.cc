// EPOS Application Scaffold and Application Abstraction Implementation

#include <utility/ostream.h>
#include <application.h>
#include <system/framework.h>

extern "C" {
    void _panic() {
        _API::Thread::exit(-1); // Could be replaced by a throw, since exceptions should not be a problem for user space
    }

    void _exit(int s) {
        _API::Thread::exit(s); for(;;);
    }

    void _print(const char * s) {

    }

    int _syscall(void * m) {
        return _SYS::CPU::syscall(m);
    }
}

__BEGIN_SYS

// Application class attributes
char Application::_preheap[];
Heap * Application::_heap;

OStream kerr;

__END_SYS

__BEGIN_API

// Global objects
OStream cout;
OStream cerr;

__END_API