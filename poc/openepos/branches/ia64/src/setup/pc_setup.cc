// EPOS PC SETUP
#include <utility/elf.h>
#include <utility/string.h>
#include <utility/ostream.h>
#include <utility/debug.h>
#include <machine.h>

// PC_BOOT assumes offset "0" to be the entry point of PC_SETUP
ASMV("jmp _start");

// LIBC Heritage
__USING_SYS
extern "C" {
    void _exit(int s) { 
        CPU32::int_enable();
        CPU32::halt();
    }

    void __cxa_pure_virtual() { 
        CPU32::int_enable();
        CPU32::halt();
    }
}

extern "C" { void _start(); }

__BEGIN_SYS

class MemoryBuilder {
public:
    typedef MMU::Page Page;
    typedef CPU64::Phy_Addr Phy_Addr;
    typedef CPU64::GDT_Entry GDT_Entry;
    typedef CPU64::IDT_Entry IDT_Entry;
    
    static void build_pmm();
    static void setup_idt();
    static void setup_gdt();
    static void setup_basic_paging();
    static void setup_sys_pd();
    static void setup_sys_pt();
};

void MemoryBuilder::build_pmm() {
    char * bi = reinterpret_cast<char *>(Traits<PC>::BOOT_IMAGE_ADDR);
    System_Info<PC> * si = reinterpret_cast<System_Info<PC> *>(bi);

    Phy_Addr top_page = MMU::pages(si->bm.mem_top);
    
    top_page -= 1;
    si->pmm.idt = top_page * sizeof(Page);

    top_page -= 1;
    si->pmm.gdt = top_page * sizeof(Page);

    top_page -= 1;
    si->pmm.sys_pt = top_page * sizeof(Page);

    top_page -= 1;
    si->pmm.sys_pd = top_page * sizeof(Page);

    top_page -= 1;
    si->pmm.sys_pdp = top_page * sizeof(Page);

    top_page -= 1;
    si->pmm.sys_pml4 = top_page * sizeof(Page);

    top_page -= 1;
    si->pmm.apic_pt = top_page * sizeof(Page);

    top_page -= 1;
    si->pmm.sys_info = top_page * sizeof(Page);

    // Page tables to map the whole physical memory
    // = NP/NPTE_PT * sizeof(Page)
    //   NP = size of physical memory in pages
    //   NPTE_PT = number of page table entries per page table
    unsigned int mem_size = MMU::pages(si->bm.mem_top - si->bm.mem_base);
    top_page -= (mem_size + MMU::PT_ENTRIES - 1) / MMU::PT_ENTRIES;
    si->pmm.phy_mem_pts = top_page * sizeof(Page);

// TODO: PCI Aperture
//    // Page tables to map the IO address space
//    // = NP/NPTE_PT * sizeof(Page)
//    // NP = size of PCI address space in pages
//    // NPTE_PT = number of page table entries per page table
//    pci_aperture(&si->pmm.io_mem_base, &si->pmm.io_mem_top);
//    unsigned int io_mem_size =
//    MMU::pages(si->pmm.io_mem_top - si->pmm.io_mem_base);
//    top_page -= (io_mem_size + MMU::PT_ENTRIES - 1) / MMU::PT_ENTRIES;
//    si->pmm.io_mem_pts = top_page * sizeof(Page);

// TODO: This cannot be done here because we don't have ELF information.
//       This is bad... :(
//    // SYSTEM code segment
//    top_page -= MMU::pages(si->lm.sys_code_size);
//    si->pmm.sys_code = top_page * sizeof(Page);
//
//    // SYSTEM data segment
//    top_page -= MMU::pages(si->lm.sys_data_size);
//    si->pmm.sys_data = top_page * sizeof(Page);
//
    // SYSTEM stack segment
    top_page -= MMU::pages(si->lm.sys_stack_size);
    si->pmm.sys_stack = top_page * sizeof(Page);

    // The memory allocated so far will "disapear" from the system as we
    // set mem_top as follows:
    si->pmm.mem_base = si->bm.mem_base;
    si->pmm.mem_top = top_page * sizeof(Page);

    // Free chuncks (passed to MMU::init)
//    si->pmm.free1_base =
//            MMU::align_page(si->lm.app_code + si->lm.app_code_size);
//    si->pmm.free1_top = MMU::align_page(si->lm.app_data);
//    si->pmm.free2_base =
//            MMU::align_page(si->lm.app_data + si->lm.app_data_size);
//    si->pmm.free2_top = MMU::align_page(si->pmm.mem_top);

    si->pmm.free1_base = 0x01000000;
    si->pmm.free1_top  = 0x02000000;

    if(si->lm.has_ext) {
        si->pmm.ext_base = si->lm.ext;
        si->pmm.ext_top = si->lm.ext + si->lm.ext_size;
    } else {
        si->pmm.ext_base = 0;
        si->pmm.ext_top = 0;
    }
}

void MemoryBuilder::setup_idt() {
    // Note: CPU64::IDT_ENTRIES * sizeof(CPU64::IDT_Entry) has exactly the size
    //       of 4096 (default page size).
    CPU32::idtr(
    	CPU64::IDT_ENTRIES * sizeof(CPU64::IDT_Entry),
    	Memory_Map<PC>::IDT
    );
}

void MemoryBuilder::setup_gdt() {
    char * bi = reinterpret_cast<char *>(Traits<PC>::BOOT_IMAGE_ADDR);
    System_Info<PC> * si = reinterpret_cast<System_Info<PC> *>(bi);
    
    GDT_Entry * gdt = reinterpret_cast<GDT_Entry *>(si->pmm.gdt);
    
    gdt[CPU64::GDT_NULL] = CPU64::GDT_Entry(0, 0x00000, 0x00, 0x00);
    gdt[CPU64::GDT_SYS_CODE] = CPU64::GDT_Entry(0, 0x00000, 0x98, 0xb0);
    gdt[CPU64::GDT_SYS_DATA] = CPU64::GDT_Entry(0, 0xfffff, 0x92, 0x90);
}

void MemoryBuilder::setup_basic_paging() {
    char * bi = reinterpret_cast<char *>(Traits<PC>::BOOT_IMAGE_ADDR);
    System_Info<PC> * si = reinterpret_cast<System_Info<PC> *>(bi);
    
    /* Page Map Lvl 4 */
    MMU::PML4_Entry * pml4 =
        reinterpret_cast<MMU::PML4_Entry *>(si->pmm.sys_pml4);
    pml4[0] = si->pmm.sys_pdp | MMU::Flags::SYS;

    /* Page Directory Pointer */
    MMU::PDP_Entry * pdp =
            reinterpret_cast<MMU::PDP_Entry *>(si->pmm.sys_pdp);
    pdp[0] = si->pmm.sys_pd | MMU::Flags::SYS;

    /* Page Directory */
    MMU::PD_Entry * sys_pd =
        reinterpret_cast<MMU::PD_Entry *>(si->pmm.sys_pd);

    /* System (GDT and IDT are here!) */
    sys_pd[MMU::directory(Memory_Map<PC>::SYS)] =
        si->pmm.sys_pt | MMU::Flags::SYS;

    /* Physical memory (Starting from PHY_MEM) */
    unsigned long long mem_size = Memory_Map<PC>::MEM_TOP - Memory_Map<PC>::MEM_BASE;
    unsigned long long phy_mem_base = MMU::directory(MMU::align_directory(
            Memory_Map<PC>::PHY_MEM));
    unsigned long long phy_mem_top = MMU::directory(MMU::align_directory(
            Memory_Map<PC>::PHY_MEM + mem_size));
    for(unsigned long long i = phy_mem_base; i < phy_mem_top; i++)
    	sys_pd[i] = (si->pmm.phy_mem_pts +
    	    (i - phy_mem_base) * sizeof(MMU::Page)) | MMU::Flags::SYS;

    /* Physical memory (Starting from MEM_BASE) */
    unsigned long long mem_base = MMU::directory(
    	MMU::align_directory(Memory_Map<PC>::MEM_BASE));
    unsigned long long mem_top =
    	MMU::directory(MMU::align_directory(Memory_Map<PC>::MEM_TOP));
    for(unsigned long long i = mem_base; i < mem_top; i++)
         sys_pd[i] =
        	(si->pmm.phy_mem_pts + i * sizeof(Page)) | MMU::Flags::APP;

    /* System page tables */
    MMU::PT_Entry * sys_pt =
            reinterpret_cast<MMU::PT_Entry *>(si->pmm.sys_pt);

    /* IDT */
    sys_pt[MMU::page(Memory_Map<PC>::IDT)] = si->pmm.idt | MMU::Flags::SYS;

    /* GDT */
    sys_pt[MMU::page(Memory_Map<PC>::GDT)] = si->pmm.gdt | MMU::Flags::SYS;

    /* SYS_INFO */
    sys_pt[MMU::page(Memory_Map<PC>::SYS_INFO)] =
    	si->pmm.sys_info | MMU::Flags::SYS;

    /* System Stack */
    sys_pt[MMU::page(Memory_Map<PC>::SYS_STACK)] =
    	si->pmm.sys_stack | MMU::Flags::SYS;

    /* Physical memory page tables*/
    MMU::PT_Entry * phy_mem_pts =
            reinterpret_cast<MMU::PT_Entry *>(si->pmm.phy_mem_pts);
    unsigned int mem_pt_base = MMU::pages(Memory_Map<PC>::MEM_BASE);
    unsigned int mem_pt_size = MMU::pages(Memory_Map<PC>::MEM_TOP - Memory_Map<PC>::MEM_BASE);
    unsigned int i = 0;
    for(i = mem_pt_base; i < mem_pt_size; i++)
    	phy_mem_pts[i] = (i * sizeof(Page)) | MMU::Flags::APP;
}

__END_SYS

//========================================================================
// _start
//
// "_start" MUST BE PC_SETUP's first function, since PC_BOOT assumes 
// offset "0" to be the entry point. It is a kind of bridge between the 
// assembly world of PC_BOOT and the C++ world of PC_SETUP.
//
// THIS FUNCTION MUST BE RELOCATABLE, because it won't run at the
// address it has been compiled for.
//------------------------------------------------------------------------
void _start()
{
    /*
     * Since we got here, we're in protected mode (running 32bit code).
     * We have to decide if we're going to switch to ia-32e mode or keep
     * in protected mode.
     */
    
    // Set EFLAGS
    CPU32::flags(CPU32::flags() & CPU32::FLAG_CLEAR);
    // Disable interrupts
    CPU32::int_disable();
    // Disable paging
    CPU32::cr0(CPU32::cr0() & ~(CPU32::CR0_PG));
    // Enable PAE (Physical Address Extension)
    CPU32::cr4(CPU32::cr4() | CPU32::CR4_PAE);
    
    // TODO: Move assembly code to somewhere else (maybe CPU32 or
    //        CPU32::cpuid)
    // Verify if intel64 bit is set
    ASMV("mov $0x80000001, %eax");
    ASMV("cpuid");
    ASMV("or $0x20000000, %edx"); // bit 29 = intel64 support?
    ASMV("jz intel64_failed");
    ASMV("jmp intel64_passed");
    ASMV("intel64_failed:");
    ASMV("hlt");
    ASMV("intel64_passed:");
    // Verify if instructions RDMSR and WRMSR are present
    ASMV("mov $0x01, %eax");
    ASMV("cpuid");
    ASMV("or $0x20, %edx"); // bit 5 = msr?
    ASMV("jz msr_failed");
    ASMV("jmp msr_passed");
    ASMV("msr_failed:");
    ASMV("hlt");
    ASMV("msr_passed:");
    // Set bit IA32_EFER.LME (bit 8)
    ASMV("mov $0xC0000080, %ecx");
    ASMV("rdmsr");
    ASMV("or $0x100, %eax");
    ASMV("wrmsr");

    char * bi = reinterpret_cast<char *>(Traits<PC>::BOOT_IMAGE_ADDR);
    System_Info<PC> * si = reinterpret_cast<System_Info<PC> *>(bi);
    
    MemoryBuilder::build_pmm();
    MemoryBuilder::setup_idt();
    MemoryBuilder::setup_gdt();
    
    MemoryBuilder::setup_basic_paging();
    // TODO: Setup APIC Page Table
//    MemoryBuilder::setup_apic_pt();

    // Setup CR3
    CPU32::cr3(si->pmm.sys_pml4);
    // Enable Paging
    CPU32::cr0(CPU32::cr0() | CPU32::CR0_PG);
    
    // Load new GDT with GDT's linear address
    CPU64::gdtr(
            CPU64::GDT_SIZE * sizeof(CPU64::GDT_Entry),
            Memory_Map<PC>::GDT
    );

    // Reload data segments
    ASMV("mov $0x10, %ax");
    ASMV("mov %ax, %ds");
    ASMV("mov %ax, %ss");
    ASMV("mov %ax, %es");
    ASMV("mov %ax, %fs");
    ASMV("mov %ax, %gs");
    
    // Since we don't have ljmp instruction in long mode, we need to jump to
    // the next 64bit ELF from here. (Also, this will update the code segment).
    const unsigned int PC_SETUP64_ENTRY_POINT = 0x8E80;
    ASMV("ljmp $0x0008, %0" : : "i"(PC_SETUP64_ENTRY_POINT));
}
