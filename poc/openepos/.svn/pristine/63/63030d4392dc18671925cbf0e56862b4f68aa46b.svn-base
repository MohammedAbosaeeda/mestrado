// OpenEPOS ARMv4TDMI_MMU Mediator Implementation

#include <mmu.h>
#include <traits.h>

__BEGIN_SYS

// Class attributes
ARMv4TDMI_MMU::List ARMv4TDMI_MMU::_free;

ARMv4TDMI_MMU::Phy_Addr ARMv4TDMI_MMU::alloc(unsigned int bytes) {
	Phy_Addr phy(0);
	if (bytes) {
		List::Element * e = _free.search_decrementing(bytes);
		if (e)
			//phy = e->object() + e->size();
			phy = (unsigned int) e->object() + (unsigned int) e->size();
		else
			db<ARMv4TDMI_MMU>(WRN) << "ARMv4TDMI_MMU::alloc() failed!\n";
	}
	db<ARMv4TDMI_MMU>(TRC) << "ARMv4TDMI_MMU::alloc(bytes=" << bytes << ") => "
			<< (void *) phy << "\n";
	return phy;
}

void ARMv4TDMI_MMU::free(Phy_Addr addr, int n) {
	db<ARMv4TDMI_MMU>(TRC) << "ARMv4TDMI_MMU::free(addr=" << (void *) addr
			<< ",n=" << n << ")\n";

	if (addr % 4 != 0) {
		db<ARMv4TDMI_MMU>(ERR) << "Unaligned address to be freed!\n";
		CPU::halt();
	}

	if (addr && n) {
		List::Element * e = new (addr) List::Element(addr, n);
		List::Element * m1, *m2;
		_free.insert_merging(e, &m1, &m2);
	}
}
__END_SYS
