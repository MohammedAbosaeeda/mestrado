// EPOS CPU MMU Mediator Initialization

#include <mmu.h>
#include <system.h>

__BEGIN_SYS

void CPU_MMU::init()
{
    System_Info<PC> * si = System::info();

    // TODO: Reconsider two (or three??) free segments.
    // db<Init, CPU_MMU>(INF)
    kout << "CPU_MMU::free1={base="
			    << (void *) si->pmm.free1_base << ",size="
			    << (si->pmm.free1_top - si->pmm.free1_base) / 1024
			    << "KB}\n";
    
    // BIG WARING HERE: INIT (i.e. this program) will be part of the free
    // storage after the following is executed, but it will remain alive
    // This only works because the _free.insert_merging() only
    // touchs the first page of each chunk and INIT is not there

    // TODO: Reconsider two (or three??) free segments.
    // Insert all free memory into the _free list
    free(si->pmm.free1_base, pages(si->pmm.free1_top - si->pmm.free1_base));

    // Remember the master page directory (created during SETUP)
    _master = reinterpret_cast<Page_Directory *>(CPU::pdp());

    db<Init, CPU_MMU>(INF) << "CPU_MMU::master page directory="
			    << _master << "\n";
}

__END_SYS

