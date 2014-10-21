// EPOS-- SPARC32 MMU Mediator Initialization

#include <mmu.h>

__BEGIN_SYS

int SPARC32_MMU::init(System_Info * si)
{
    db<Init, SPARC32_MMU>(TRC) << "SPARC32_MMU::init()\n";

    db<Init, SPARC32_MMU>(INF) << "SPARC32_MMU:: memory size = " 
			    << si->mem_size << " bytes\n";
    db<Init, SPARC32_MMU>(INF) << "SPARC32_MMU:: free memory = " 
			    << si->mem_free << " bytes\n";
    db<Init, SPARC32_MMU>(INF) << "SPARC32_MMU:: application's memory base = " 
			    << (void *) si->pmm.app_lo << "\n";
    
    SPARC32_MMU::free(si->pmm.free, si->pmm.free_size);

    return 0;
}

__END_SYS

