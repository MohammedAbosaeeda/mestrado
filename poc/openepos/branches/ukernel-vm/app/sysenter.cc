// EPOS Syscall with sysenter test program

#include <utility/ostream.h>
#include <syscall_handler.h>
#include <semaphore_proxy.h>
#include <utility/ostream.h>

__USING_SYS

OStream cout;

int main(void) {

	cout << "Entered main\n";

	Semaphore_Proxy semaphore;

	return 0;

}
