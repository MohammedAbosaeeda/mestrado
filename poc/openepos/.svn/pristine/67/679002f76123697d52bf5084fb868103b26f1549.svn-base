// EPOS-- MIPS32 MMU Mediator Declarations

#ifndef __mips32_mmu_h
#define __mips32_mmu_h

#include <cpu.h>
#include <mmu.h>
#include <utility/string.h>
#include <utility/list.h>
#include <utility/debug.h>
#include __MEMORY_MAP_H

__BEGIN_SYS

class MIPS32_MMU: public MMU_Common<0, 0, 2>
{
private:
    typedef Grouping_List<Frame> List;

public:
    // Page_Table
    class Page_Table {};

    // Chunk (for Segment)
    class Chunk
    {
    public:
        Chunk() {}
        Chunk(unsigned int bytes, Flags flags)
	    : _phy_addr(alloc(pages(bytes))), _bytes(bytes), _flags(flags) {}
	Chunk(Phy_Addr phy_addr, unsigned int bytes, Flags flags)
	    : _phy_addr(phy_addr), _bytes(bytes), _flags(flags) {}
	~Chunk() { free(_phy_addr, _bytes); }

	unsigned int pts() const { return 0; }
	Flags flags() const { return _flags; }
	Page_Table * pt() const { return 0; }
	unsigned int size() const { return _bytes; }
	Phy_Addr phy_address() const { return _phy_addr; } // always CT

	int resize(unsigned int amount) { return 0; } // no resize with CT

    private:
        Phy_Addr _phy_addr;
        unsigned int _bytes;
        Flags _flags;
    };

    // Page Directory
    typedef Page_Table	Page_Directory;

    // Directory (for Address_Space)
    class Directory 
    {
    public:
	Directory() {}
	Directory(Page_Directory * pd) {}
	~Directory() {}
	
	Page_Table * pd() const { return 0; }

	void activate() { }

	Log_Addr attach(const Chunk & chunk) { return chunk.phy_address(); }
	Log_Addr attach(const Chunk & chunk, Log_Addr addr) {
	    return (addr == chunk.phy_address())? addr : Log_Addr(false);
	}
 	void detach(const Chunk & chunk) {}
 	void detach(const Chunk & chunk, Log_Addr addr) {}

	Phy_Addr physical(Log_Addr addr) { return addr;	}
    };

public:
    MIPS32_MMU() {}

    static void flush_tlb() {}
    static void flush_tlb(Log_Addr addr) {}

    static Phy_Addr alloc(unsigned int frames = 1) {
		Phy_Addr phy(false);
	
		if(frames) {
			List::Element * e = _free.search_decrementing(frames);
			if(e)
				phy = e->object() + e->size();
			else
				db<MIPS32_MMU>(WRN) << "MIPS32_MMU::alloc() failed!\n";
		}
	
			db<MIPS32_MMU>(TRC) << "MIPS32_MMU::alloc(frames=" << frames << ") => "
				<< (void *)phy << "\n";
		
		return phy;
    }

    static Phy_Addr calloc(unsigned int frames = 1) {
		Phy_Addr phy = alloc(frames);
		memset(phy2log(phy), sizeof(Frame) * frames, 0);
		return phy;	
    }

    static void free(Phy_Addr frame, int n = 1)	{
        db<MIPS32_MMU>(TRC) << "MIPS32_MMU::free(frame=" << (void *)frame 
			  << ",n=" << n << ")\n";

		if(frame && n) {
			List::Element * e = new (phy2log(frame)) List::Element(frame, n);
			List::Element * m1, * m2;
			_free.insert_merging(e, &m1, &m2);
		}
    }

    static Phy_Addr physical(Log_Addr addr) { return addr; }

public:
    static int init(System_Info * si);

private:
    static Phy_Addr align(Phy_Addr addr) { return (addr & 0xfffffffc); }
    static Log_Addr phy2log(Phy_Addr log) { return log; }

    static List _free;
};

__END_SYS

#endif
