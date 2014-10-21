// EPOS APU MMU Mediator Implementation

#include <arch/x86/mmu.h>

__BEGIN_SYS

// Class attributes
CPU_MMU::List CPU_MMU::_free;
CPU_MMU::Page_Directory * CPU_MMU::_master;

__END_SYS
