// EPOS IA32 MMU Mediator Initialization

#include <mmu.h>
#include <system.h>

__BEGIN_SYS

void IA32_MMU::init()
{
    System_Info<PC> * si = System::info();

    db<Init, IA32_MMU>(INF) << "IA32_MMU::memory={base=" 
			    << (void *) si->pmm.mem_base << ",size="
			    << (si->bm.mem_top - si->bm.mem_base) / 1024
			    << "KB}\n";
    db<Init, IA32_MMU>(INF) << "IA32_MMU::free1={base=" 
			    << (void *) si->pmm.free1_base << ",size="
			    << (si->pmm.free1_top - si->pmm.free1_base) / 1024
			    << "KB}\n";
    db<Init, IA32_MMU>(INF) << "IA32_MMU::free2={base=" 
			    << (void *) si->pmm.free2_base << ",size="
			    << (si->pmm.free2_top - si->pmm.free2_base) / 1024
			    << "KB}\n";
    
    // BIG WARING HERE: INIT (i.e. this program) will be part of the free
    // storage after the following is executed, but it will remain alive
    // This only works because the _free.insert_merging() only
    // touchs the first page of each chunk and INIT is not there
    
    if(Traits<IA32_MMU>::page_coloring) {
      // Insert all free memory into the _free[color] list
      
      // jumps the memory space used by INIT
      // avoids the new Element created by the MMU::free() to overwrite initial
      // bytes of every page that is used by INIT
      //kout << "top1 - free1 = " << (void *) si->pmm.free1_top - si->pmm.free1_base << "\n";
      //si->pmm.free1_base = si->pmm.free1_base + 0x100000;
      //si->pmm.page_col_base = si->pmm.free1_base - 0x20000; // 128KB
      
      si->pmm.page_col_base = si->pmm.free1_base + 0x20000;
      si->pmm.page_col_base += 0x40000; 
      
      kout << "IA32_MMU::init() top 1 = " << (void *) si->pmm.free1_top << " base 1 = " << (void *) si->pmm.free1_base
      << " top 2 = " << (void *) si->pmm.free2_top << " base 2 = " << (void *) si->pmm.free2_base 
      << " top 3 = " << (void *) si->pmm.free3_top << " base 3 = " << (void *) si->pmm.free3_base << "\n";
      
      kout << "Releasing free memory..";
      int page = 0;
      while(si->pmm.free1_base < si->pmm.free1_top) {
        //kout << "\nIA32_MMU::init() free from " << (void *) si->pmm.free1_base << " to " 
        //<< (void *) (si->pmm.free1_base + MMU::PAGE_SIZE) << " page = " << page++ << "\n";
        free(si->pmm.free1_base);
        si->pmm.free1_base += MMU::PAGE_SIZE;
        page++;
      }

      //page = 1;
      //si->pmm.free2_top = si->pmm.free2_top - 0x1388000; //minus 5000 pages
      //si->pmm.free2_top = si->pmm.free2_top - 0xBB8000;
      
        kout << "chunk 2\n";
        while(si->pmm.free2_base < si->pmm.free2_top) {
            //if(page > 60000 && (page % 5000) == 0)
            //kout << "\nIA32_MMU::init() from " << (void *) si->pmm.free2_base << " to " 
            //<< (void *) (si->pmm.free2_base + MMU::PAGE_SIZE) << " page = " << page << "\n";
            free(si->pmm.free2_base);
            si->pmm.free2_base += MMU::PAGE_SIZE;
            page++;
            //if(page == 61441) break; //sistema reinicia na pagina 61442 from 0x0f402000 to 0x0f403000
            //if(page == 60000) break;
        }
        
        if(Traits<IA32_MMU>::uncolored_system_heap) {
            uncolored_free(si->pmm.free3_base, pages(Traits<Machine>::SYSTEM_HEAP_SIZE + (3 * 1024 * 1024)));
            si->pmm.free3_base += (Traits<Machine>::SYSTEM_HEAP_SIZE + (3 * 1024 * 1024));
        } else {
            uncolored_free(si->pmm.free3_base, pages(3 * 1024 * 1024));
            si->pmm.free3_base += (3 * 1024 * 1024) + 4096;
        }
        
        kout << "chunk 3\n";
        while(si->pmm.free3_base < si->pmm.free3_top) {
            //if(page > 60000 && (page % 5000) == 0)
            //kout << "\nIA32_MMU::init() from " << (void *) si->pmm.free2_base << " to " 
            //<< (void *) (si->pmm.free2_base + MMU::PAGE_SIZE) << " page = " << page << "\n";
            free(si->pmm.free3_base);
            si->pmm.free3_base += MMU::PAGE_SIZE;
            page++;
        }
    
        kout << "... done! pages = " << page << "\n";
        //while(1);
      /*for(int i = 0; i < Traits<MMU>::colors; i++) {
          kout << "_free[" << i << "] grouped_size = " << _free[i].grouped_size()
          << " size = " << _free[i].size() 
          << " head = " << (void *) _free[i].head() << " color = " 
          << color_from_physical(Phy_Addr(_free[i].head())) 
          << " next = " << (void *) _free[i].head()->next() << " color = " 
          << color_from_physical(Phy_Addr(_free[i].head()->next())) << "\n";
      }
      while(1);*/
    } else {
        // Insert all free memory into the _free[0] list
        //kout << "Releasing free space...";
        free(si->pmm.free1_base, pages(si->pmm.free1_top - si->pmm.free1_base));
        free(si->pmm.free2_base, pages(si->pmm.free2_top - si->pmm.free2_base));
        free(si->pmm.free3_base, pages(si->pmm.free3_top - si->pmm.free3_base));

        //kout << "done\n";
      
    }

    // Remeber the master page directory (created during SETUP)
    _master = reinterpret_cast<Page_Directory *>(CPU::pdp());
    
    //CPU::cr4(CPU::cr4() | 0x80); //set bit 7 - Global page entry 

    db<Init, IA32_MMU>(INF) << "IA32_MMU::master page directory=" 
			    << _master << "\n";
}

__END_SYS

