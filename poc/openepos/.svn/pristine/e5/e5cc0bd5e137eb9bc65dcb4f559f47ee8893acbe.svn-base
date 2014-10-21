// EPOS IA32 MMU Mediator Declarations

#ifndef __ia32_mmu_h
#define __ia32_mmu_h

#include <system/memory_map.h>
#include <utility/string.h>
#include <utility/list.h>
#include <utility/debug.h>
#include <cpu.h>
#include <mmu.h>

__BEGIN_SYS

class IA32_MMU: public MMU_Common<10, 10, 12>
{
private:
    typedef Grouping_List<Frame> List;

    static const unsigned int PHY_MEM = Memory_Map<Machine>::PHY_MEM;
    
    static const unsigned int COLORED_MASK = 0x7F; // 7 bits used by page coloring
public:
    // Page Flags
    class IA32_Flags
    {
    public:
	enum {
	    PRE  = 0x001, // Presence (0=not-present, 1=present)
	    RW   = 0x002, // Write (0=read-only, 1=read-write)
	    USR  = 0x004, // Access Control (0=supervisor, 1=user)
	    PWT  = 0x008, // Cache Mode (0=write-back, 1=write-through)
	    PCD  = 0x010, // Cache Disable (0=cacheable, 1=non-cacheable)
	    ACC  = 0x020, // Accessed (0=not-accessed, 1=accessed
	    DRT  = 0x040, // Dirty (only for PTEs, 0=clean, 1=dirty)
	    PS   = 0x080, // Page Size (for PDEs, 0=4KBytes, 1=4MBytes)
	    GLB  = 0X100, // Global Page (0=local, 1=global)
	    EX   = 0x200, // User Def. (0=non-executeable, 1=executeable)
	    CT   = 0x400, // User Def. (0=non-contiguous, 1=contiguous)
	    IO   = 0x800, // User Def. (0=memory, 1=I/O)
	    APP  = (PRE | RW  | ACC | USR),
        SYS  = (PRE | RW  | ACC),
        PCI  = (SYS | PCD | IO),
        APIC = (SYS | PCD),
        VGA = (SYS | PCD),
        DMA  = (SYS | PCD | CT),
	};

    public:
	IA32_Flags() {}
	IA32_Flags(const IA32_Flags & f) : _flags(f._flags) {}
	IA32_Flags(unsigned int f) : _flags(f) {}
	IA32_Flags(Flags f) : _flags(PRE | ACC |
				     ((f & Flags::RW)  ? RW  : 0) |
				     ((f & Flags::USR) ? USR : 0) |
				     ((f & Flags::CWT) ? PWT : 0) |
				     ((f & Flags::CD)  ? PCD : 0) |
				     ((f & Flags::CT)  ? CT  : 0) |
				     ((f & Flags::IO)  ? PCI : 0) ) {}

	operator unsigned int() const { return _flags; }

	friend Debug & operator << (Debug & db, IA32_Flags f)
	    { db << (void *)f._flags; return db; }

    private:
	unsigned int _flags;
    };

    // Page_Table
    class Page_Table 
    {
    public:
	Page_Table() {}
	
	PT_Entry & operator[](unsigned int i) { return _entry[i]; }
	PT_Entry get_entry(unsigned int i) { return _entry[i]; }
	
	void map(int from, int to, IA32_Flags flags) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Page_Table::map()\n";
	    Phy_Addr * addr = alloc(0, to - from);
	    if(addr) {
          remap(addr, from, to, flags);
        } else {
          for( ; from <= to; from++) {
              _entry[from] = alloc(0) | flags;
          }
        }
	}
	
	void map_colored(int from, int to, IA32_Flags flags, unsigned int color) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Page_Table::map_colored()\n";
        if(allocable(color) > (unsigned int) (to - from)) {
            Phy_Addr * addr = alloc(color, to - from);
            if(addr)
                remap(addr, from, to, flags);
        } else {
          //kout << "Page_Table::map_colored() mapping from = " << from << " to = " << to << "\n";
          for( ; from <= to; from++) {
          //for( ; from < to; from++) { //to = number of pages
              //if(Traits<IA32_MMU>::page_coloring)
              //kout << "Page_Table::map() from = " << from << "\n";
              _entry[from] = alloc(color) | flags;
          }
        }
    }
	
	void map_contiguous(int from, int to, IA32_Flags flags) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Page_Table::map_contiguous()\n";
	    remap(alloc(0, to - from), from, to, flags);
	}
	void remap(Phy_Addr addr, int from, int to, IA32_Flags flags) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Page_Table::remap()\n";
	    addr = align_page(addr);
	    for( ; from <= to; from++) {
		_entry[from] = addr | flags;
		addr+= sizeof(Page);
	    }
	}
	
	/*void remap_log_to_phy(Log_Addr laddr, Phy_Addr paddr, IA32_Flags flags) {
        _entry[page(laddr)] = paddr | flags;
        //kout << "entry = " << (void *) _entry[page(laddr)];
    }*/
	
	void unmap(int from, int to) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Page_Table::unmap()\n";
	    for( ; from <= to; from++) {
		free(_entry[from]);
		_entry[from] = 0;
	    }
	}

	friend Debug & operator << (Debug & db, Page_Table & pt) {
	    db << "{\n";
	    int brk = 0;
	    for(unsigned int i = 0; i < PT_ENTRIES; i++)
		if(pt[i]) {
		    db << "[" << i << "]=" << pt[i] << "  ";
		    if(!(++brk % 4))
			db << "\n";
		}
	    db << "\n}";
	    return db;
	}
	
    private:
	PT_Entry _entry[PT_ENTRIES];
    };

    // Chunk (for Segment)
    class Chunk
    {
    public:
      Chunk() {}
      Chunk(unsigned int bytes, Flags flags, unsigned int color = 1)
	    : _from(0), _to(pages(bytes)), _pts(page_tables(_to - _from)),
	      _flags(IA32_Flags(flags)) {
        //kout << "_pts = " << _pts << "\n";
        _pt = calloc(_pts, 0);
	    if(flags & IA32_Flags::CT)
            _pt->map_contiguous(_from, _to, _flags);
	    else {
            if(Traits<IA32_MMU>::page_coloring) {
                //kout << "map_colored() color = " << color 
                //<< " _pts = " << _pts << " \n";
                _pt->map_colored(_from, _to, _flags, color);
            } else
                _pt->map(_from, _to, _flags);
        }
      }

      /*Chunk(Phy_Addr phy_addr, unsigned int bytes, Flags flags)
	    : _from(0), _to(pages(bytes)), _pts(page_tables(_to - _from)),
	      _flags(IA32_Flags(flags)), _pt(calloc(_pts)) {
	    _pt->remap(phy_addr, _from, _to, flags);
      }*/
	~Chunk() {
	    if(!(_flags & IA32_Flags::IO)) {
		if(_flags & IA32_Flags::CT)
		    free((*_pt)[_from], _to - _from);
		else
		    for(unsigned int i = _from; i < _to; i++)
			free((*_pt)[i]);
	    }
	    free(_pt, _pts);
	}

	unsigned int pts() const { return _pts; }
	IA32_Flags flags() const { return _flags; }
	Page_Table * pt() const { return _pt; }
	PT_Entry pt_entry(unsigned int entry) { 
      PT_Entry pe = _pt->get_entry(entry); 
      return pe; 
    }
	unsigned int size() const { return (_to - _from) * sizeof(Page); }
	Phy_Addr phy_address() const {
        db<IA32_MMU>(TRC) << "IA32_MMU::Chunk::phy_address()\n";
	    return (_flags & IA32_Flags::CT) ?
		Phy_Addr(indexes((*_pt)[_from])) : Phy_Addr(false);
	}

	int resize(unsigned int amount) {
	    if(_flags & IA32_Flags::CT)
		return 0;

	    unsigned int pgs = pages(amount);
	    if((_pts * PT_ENTRIES - _to - 1) < pgs)
		return 0;

	    _pt->map(_to + 1, _to + 1 + pgs, _flags);
	    _to += pgs;

	    return pgs * sizeof(Page);
	}

    private:
	unsigned int _from;
	unsigned int _to;
	unsigned int _pts;
	IA32_Flags _flags;
	Page_Table * _pt;
    };

    // Page Directory
    typedef Page_Table Page_Directory;

    // Directory (for Address_Space)
    class Directory 
    {
    public:
	Directory() : _pd(calloc(1)), _free(true) {
	    for(unsigned int i = 0; i < PD_ENTRIES; i++)
		(*_pd)[i] = (*_master)[i];
	}
	Directory(Page_Directory * pd) : _pd(pd), _free(false) {}
	~Directory() { if(_free) free(_pd); }
	
	Page_Table * pd() const { return _pd; }

	void activate() { IA32::pdp(reinterpret_cast<IA32::Reg32>(_pd)); }

	Log_Addr attach(const Chunk & chunk) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Directory::attach()\n";
	    for(unsigned int i = 0; i < PD_ENTRIES; i++)
		if(attach(i, chunk.pt(), chunk.pts(), chunk.flags()))
		    return i << DIRECTORY_SHIFT;
	    return false;
	}
	Log_Addr attach(const Chunk & chunk, Log_Addr addr) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Directory::attach()\n";
	    unsigned int from = directory(addr);
	    if(!attach(from, chunk.pt(), chunk.pts(), chunk.flags()))
		return Log_Addr(false);

	    return from << DIRECTORY_SHIFT;
	}
 	void detach(const Chunk & chunk) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Directory::detach()\n";
 	    for(unsigned int i = 0; i < PD_ENTRIES; i++)
		if(indexes((*_pd)[i]) == indexes(chunk.pt())) {
		    detach(i, chunk.pt(), chunk.pts());
		    return;
		}
	    db<IA32_MMU>(WRN) << "IA32_MMU::Directory::detach(pt=" 
			      << chunk.pt() << ") failed!\n";
 	}
 	void detach(const Chunk & chunk, Log_Addr addr) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Directory::detach()\n";
	    unsigned int from = directory(addr);
	    if(indexes((*_pd)[from]) != indexes(chunk.pt())) {
		db<IA32_MMU>(WRN) << "IA32_MMU::Directory::detach(pt=" 
				  << chunk.pt() << ",addr="
				  << addr << ") failed!\n";
		return;
	    }
	    detach(from, chunk.pt(), chunk.pts());
 	}

	Phy_Addr physical(Log_Addr addr) {
        db<IA32_MMU>(TRC) << "IA32_MMU::Directory::physical()\n";
	    Page_Table * pt = (Page_Table *)(void *)(*_pd)[directory(addr)];
	    return (*pt)[page(addr)] | offset(addr);
	}

    private:
	bool attach(unsigned int from, const Page_Table * pt,
		    unsigned int n, IA32_Flags flags) {
        //kout << "IA32_MMU::Directory::attach() from = " << from << " to " << from + n << "\n";
	    for(unsigned int i = from; i < from + n; i++)
		if((*_pd)[i])
		    return false;
	    for(unsigned int i = from; i < from + n; i++, pt++)
		(*_pd)[i] = Phy_Addr(pt) | flags;
	    return true;
	}
	void detach(unsigned int from, const Page_Table * pt, unsigned int n) {
	    for(unsigned int i = from; i < from + n; i++)
		(*_pd)[i] = 0;
	}

    private:
	Page_Directory * _pd;
	bool _free;
    };

    // DMA_Buffer
    class DMA_Buffer: public Chunk
    {
    public:
	DMA_Buffer(unsigned int s) : Chunk(s, IA32_Flags::DMA) {
	    Directory dir(current());
	    _log_addr = dir.attach(*this);
	    db<IA32_MMU>(TRC) << "IA32_MMU::DMA_Buffer() => " 
			      << *this << "\n";
	}
	DMA_Buffer(unsigned int s, const Log_Addr & d)
	    : Chunk(s, IA32_Flags::DMA) {
	    Directory dir(current());
	    _log_addr = dir.attach(*this);
	    memcpy(_log_addr, d, s);
	    db<IA32_MMU>(TRC) << "IA32_MMU::DMA_Buffer(phy=" << *this 
			      << " <= " << d <<"\n";
	}
	
	Log_Addr log_address() const { return _log_addr; }

	friend Debug & operator << (Debug & db,
				    const DMA_Buffer & b) {
	    db << "{phy=" << b.phy_address()
	       << ",log=" << b.log_address()
	       << ",size=" << b.size() 
	       << ",flags=" << b.flags() << "}";
	    return db;
	}

    private:
	Log_Addr _log_addr;
    };

public:
    IA32_MMU() {}
    
    static unsigned int color_from_physical(Phy_Addr addr) {
        if(Traits<IA32_MMU>::page_coloring) 
          return ((addr >> PAGE_SHIFT) & COLORED_MASK) % Traits<IA32_MMU>::colors;
        else
          return 0;
    }
    
    static unsigned int color_from_logical(Log_Addr laddr) {
        if(Traits<IA32_MMU>::page_coloring) {
          //Phy_Addr addr = physical(laddr);
          Page_Directory * pd = current();
          //unsigned int dir = directory(laddr);
          Page_Table * pt = (*pd)[directory(laddr)];
          Phy_Addr addr = (*pt)[page(laddr)] | offset(laddr);
          unsigned int color = ((addr >> PAGE_SHIFT) & COLORED_MASK) % Traits<IA32_MMU>::colors;
          //kout << "MMU::color_from_logical() pd = " << (void *) pd << " pt = " << (void *) pt << " dir = " << dir
          //<< " addr = " << (void *) addr << " color = " << color << "\n";
          return color;
        } else
          return 0;
    }

    static Phy_Addr alloc(unsigned int color, unsigned int frames = 1) {
        Phy_Addr phy(false);
        if(frames) {
            //kout << "IA32_MMU::alloc(color=" << color << " frames=" << frames << ") => " << (void *)phy 
            //<< " _free[" << color << "] grouped_size = " << _free[color].grouped_size() << "\n";
            List::Element * e = _free[color].search_decrementing(frames);
            if(e)
                phy = e->object() + e->size();
            else {
                db<IA32_MMU>(WRN) << "IA32_MMU::alloc() failed!\n";
                /*kout << " IA32_MMU::alloc() failed! color = " 
                    << color << " frames = " << frames 
                    << " _free[color].grouped_size = " << _free[color].grouped_size() 
                    << " _free[color].size = " << _free[color].size() << "\n";
                while(1);*/
            }
        }
        db<IA32_MMU>(TRC) << "IA32_MMU::alloc(color=" << color << " frames=" << frames << ") => " << (void *)phy << "\n";
        //kout << "IA32_MMU::alloc(color=" << color << " frames=" << frames << ") => " << (void *)phy << "\n";
        return phy;
    }
    static Phy_Addr calloc(unsigned int frames = 1, unsigned int color = 0) {
        //kout << "calloc() frames = " << frames << " color = " 
        //    << color << " allocable = " << allocable(color) << "\n";
        Phy_Addr phy = alloc(color, frames);
        /*if(phy == 0) {
            kout << "calloc() frames = " << frames << " color = " 
           << color << " allocable = " << allocable(color) << "\n";
           while(1);
        }*/
        memset(phy2log(phy), sizeof(Frame) * frames, 0);
        return phy;	
    }
    
    static void free(Phy_Addr frame, unsigned int n = 1) {
        // Clean up MMU flags in frame address
        frame = indexes(frame); 
        
        unsigned int color = 0;
        
        if(Traits<MMU>::page_coloring)
            color = color_from_physical(frame) + 1;

        db<IA32_MMU>(TRC) << "IA32_MMU::free(frame=" << (void *)frame << ",n=" << n << ")\n";
              
        //kout << "IA32_MMU::free(frame=" << (void *)frame << ",n=" << n << ") color = " << color;

        if(frame && n) {
            List::Element * e = new (phy2log(frame)) List::Element(frame, n);
            List::Element * m1, * m2;
            _free[color].insert_merging(e, &m1, &m2);
        }
    }
    
    static void uncolored_free(Phy_Addr frame, unsigned int n = 1) {
        // Clean up MMU flags in frame address
        frame = indexes(frame); 

        db<IA32_MMU>(TRC) << "IA32_MMU::free(frame=" << (void *)frame << ",n=" << n << ")\n";
              
        //kout << "IA32_MMU::free(frame=" << (void *)frame << ",n=" << n << ") color = " << color;

        if(frame && n) {
            List::Element * e = new (phy2log(frame)) List::Element(frame, n);
            List::Element * m1, * m2;
            _free[0].insert_merging(e, &m1, &m2);
        }
    }
    
    static unsigned int allocable(unsigned int color = 0) { return _free[color].head() ? _free[color].head()->size() : 0; }

    static Page_Directory * volatile current() {
        db<IA32_MMU>(TRC) << "\nIA32_MMU::current()\n";
        return reinterpret_cast<Page_Directory * volatile>(CPU::pdp());
    }

    static Phy_Addr physical(Log_Addr addr) {
      db<IA32_MMU>(TRC) << "\nIA32_MMU::physical()\n";
        Page_Directory * pd = current();
        Page_Table * pt = (*pd)[directory(addr)];
        return (*pt)[page(addr)] | offset(addr);
    }

    static void flush_tlb() {
        db<IA32_MMU>(TRC) << "IA32_MMU::flush_tlb()\n";

        ASMV("movl %cr3,%eax");
        ASMV("movl %eax,%cr3");
        /*ASMV("movl %%cr3, %%eax \n"
            "movl %%eax, %%cr3 \n"
            : : : "eax", "memory");*/
    }
    static void flush_tlb(Log_Addr addr) {
        db<IA32_MMU>(TRC) << "IA32_MMU::flush_tlb(" << addr << ")\n";
        ASMV("invlpg %0" : : "m"(addr) : "memory");
    }
    
    /*static void change_page_flags(Log_Addr addr, int size, IA32_Flags flags) { 
      db<IA32_MMU>(TRC) << "IA32_MMU::change_page_flags(" << addr << ", " << size << ", " << flags << ")\n";
      Page_Directory * pd = current();
      Page_Table * pt = (*pd)[directory(addr)];
      
      kout << "\nAddress = " << (void *) addr << " pd = " << (void *) pd << " pt = " << (void * ) pt <<
      " Directory = " << directory(addr) << " page(addr) = " << page(addr) << 
        " physical = " << (void *) physical(addr) << endl;
        kout << "(*pt)[page(addr)] antes = " << (void *) (*pt)[page(addr)] << "\n";
        
        //if(!((*pt)[page(addr)] & IA32_Flags::PCD)) {
          
        (*pt)[page(addr)] = (*pt)[page(addr)] | (IA32_Flags::PCD);
          //(*pt)[page(addr)] = 0x20902710;
          //kout << "(*pt)[page(addr)] antes Flush_tlb = " << (void *) (*pt)[page(addr)] << "\n";
          //flush_tlb(addr);
          flush_tlb();
          //kout << "(*pt)[page(addr)] depois Flush_tlb = " << (void *) (*pt)[page(addr)] << "\n";
        //}
              
        //kout << "(*pt)[page(addr)] depois = " << (void *) (*pt)[page(addr)] << "\n";
        //while(1);

        //chamar print page directory debug - verificar flags pc_setup
    }*/
   
    /*static void map_page_log_to_phy(Log_Addr laddr, Phy_Addr paddr) {
        Page_Directory * pd = current();
        Page_Table * pt = (*pd)[directory(laddr)];
        
        //kout << "Mapping " << (void *) laddr << " to " << (void *) paddr;
        
        pt->remap_log_to_phy(laddr, paddr, IA32_Flags::APP);
        
        //kout << " pd = " << (void *) pd << " pt = " << (void *) pt;
       
        flush_tlb(laddr);
        //flush_tlb();
        
        //kout << " index = " << page(laddr) << "\n";
    }*/
   

    static void init();

private:
    static Log_Addr phy2log(Phy_Addr phy) { return phy | PHY_MEM; }

private:
    static List _free[IF_INT<Traits<IA32_MMU>::page_coloring, Traits<IA32_MMU>::colors+1, 1>::Result];
    static Page_Directory * _master;
};

__END_SYS

#endif
