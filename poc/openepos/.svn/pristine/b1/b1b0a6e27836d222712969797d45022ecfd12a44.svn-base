// EPOS-- MIPS MMU Mediator Initialization

#include <mmu.h>

__BEGIN_SYS

int MIPS32_MMU::init(System_Info * si)
{
    db<MIPS32_MMU>(TRC) << "MIPS32_MMU::init()\n";

    db<MIPS32_MMU>(INF) << "MIPS32_MMU:: memory size = " 
		      << si->mem_size << " pages\n";
    db<MIPS32_MMU>(INF) << "MIPS32_MMU:: free memory = " 
		      << si->mem_free << " pages\n";
    db<MIPS32_MMU>(INF) << "MIPS32_MMU:: application's memory base = " 
		      << (void *) si->pmm.app_lo << "\n";
    db<MIPS32_MMU>(INF) << "MIPS32_MMU:: free chunk = {base=" 
		      << (void *) si->pmm.mach2 << ",size="
		      << (void *) si->pmm.mach3 << "}\n";
    db<MIPS32_MMU>(INF) << "MIPS32_MMU:: free chunk = {base=" 
		      << (void *) si->pmm.free << ",size="
		      << (void *) si->pmm.free_size << "}\n";

    // Insert all free memory into the _free list
    List::Element * e, * m1, * m2;
    e = new (phy2log(reinterpret_cast<void *>(si->pmm.mach2)))
	List::Element(reinterpret_cast<Page *>(si->pmm.mach2),
		      si->pmm.mach3);
    _free.insert_merging(e, &m1, &m2);

    e = new (phy2log(reinterpret_cast<void *>(si->pmm.free)))
	List::Element(reinterpret_cast<Page *>(si->pmm.free),
		      si->pmm.free_size);
    _free.insert_merging(e, &m1, &m2);

    db<MIPS32_MMU>(INF) << "Free Frames => " << _free.grouped_size() << "\n";

    return 0;
}

__END_SYS

