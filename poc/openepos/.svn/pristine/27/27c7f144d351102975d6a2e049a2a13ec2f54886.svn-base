// EPOS IA32 MMU Mediator Implementation

#include <arch/ia32/mmu.h>

__BEGIN_SYS

// Class attributes
IA32_MMU::List IA32_MMU::_free[IF_INT<Traits<IA32_MMU>::page_coloring, Traits<IA32_MMU>::colors+1, 1>::Result];
//IA32_MMU::List IA32_MMU::_free[1];
IA32_MMU::Page_Directory * IA32_MMU::_master;

__END_SYS
