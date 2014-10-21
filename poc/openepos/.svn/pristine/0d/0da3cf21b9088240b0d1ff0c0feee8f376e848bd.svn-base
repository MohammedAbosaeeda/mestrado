// EPOS-- SoftMIPS Machine Mediator Initialization

#include <mach/softmips/machine.h>

__BEGIN_SYS

int SoftMIPS::init(System_Info * si)
{
    db<SoftMIPS>(TRC) << "SoftMIPS::init()\n";

    // Set all interrupt handlers to int_not()
    for(unsigned int i = 0; i < INT_VECTOR_SIZE; i++)
		_int_vector[i] = int_not;

    /* Since C++ denies use of sizeof() of functions, we will guess that *
     * main exception handler has 68 instructions (in fact, it has 61,   * 
     * but we leave some room for compiler generated ones) and copy that *
     * guessed much to (*SYS_INTR). Remember: MIPS have no intr. vector! */

    memcpy(reinterpret_cast<void *>(Memory_Map<Machine>::SYS_INTR),
	   (void *)&int_wrap, 1024 * sizeof(int));

    return 0;
}

__END_SYS
