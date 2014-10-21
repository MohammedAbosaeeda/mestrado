#include <utility/ostream.h>

// This work is licensed under the EPOS Software License v1.0.
// A copy of this license is available at the EPOS system source tree root.
// A copy of this license is also available online at:
// http://epos.lisha.ufsc.br/EPOS+Software+License+v1.0
// Note that EPOS Software License applies to both source code and executables.
#include <thread.h>
#include <semaphore.h>
#include <alarm.h>
#include <vm/NanoVM.h>
__USING_SYS


int main()
{

jvm_main();

    /*Thread * cons = new Thread(&consumer);

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
*/
    return 0;
}
