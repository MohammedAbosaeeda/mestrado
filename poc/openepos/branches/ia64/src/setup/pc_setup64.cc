// EPOS PC SETUP FOR Intel X64_64 PROCESSORS

#include <utility/elf.h>
#include <utility/string.h>
#include <utility/ostream.h>
#include <utility/debug.h>
#include <machine.h>

ASMV("jmp _start");

__USING_SYS
extern "C" {
    void _exit(int s) { 
        db<Setup>(ERR) << "_exit(" << s << ") called!\n"; 
        Machine::panic(); for(;;);
    }

    void __cxa_pure_virtual() { 
        db<Setup>(ERR) << "__cxa_pure_virtual() called!\n"; 
        Machine::panic();
    }

    void _print(const char * s) {
        Display::puts(s);
    }
}

__BEGIN_SYS

OStream kout, kerr;
OStream::Endl endl;

volatile char * Stacks;

extern "C" { void _start(); }
extern "C" { void setup(char* bi); }

class PC_Setup {
public:
    PC_Setup(char * boot_image);
private:
    static const unsigned int BOOT_IMAGE_ADDR = Traits<PC>::BOOT_IMAGE_ADDR;
    static const unsigned int SYS_STACK_SIZE = Traits<System>::STACK_SIZE;

    static const unsigned int SYS_INFO = Memory_Map<PC>::SYS_INFO;
    static const unsigned int IDT = Memory_Map<PC>::IDT;
    static const unsigned int GDT = Memory_Map<PC>::GDT;
    static const unsigned int PHY_MEM = Memory_Map<PC>::PHY_MEM;
    static const unsigned int IO_MEM = Memory_Map<PC>::IO;
    // TODO: Fix this
    static const unsigned int APIC_MEM = 0x2f400000;
    static const unsigned int SYS_PT = Memory_Map<PC>::SYS_PT;
    static const unsigned int SYS_PD = Memory_Map<PC>::SYS_PD;
    static const unsigned int SYS = Memory_Map<PC>::SYS;
    static const unsigned int SYS_DATA = Memory_Map<PC>::SYS_DATA;
    static const unsigned int SYS_CODE = Memory_Map<PC>::SYS_CODE;
    static const unsigned int SYS_STACK = Memory_Map<PC>::SYS_STACK;

    typedef CPU::Reg32 Reg32;
    typedef CPU::Phy_Addr Phy_Addr;
    typedef CPU::Log_Addr Log_Addr;
    typedef CPU::GDT_Entry GDT_Entry;
    typedef CPU::IDT_Entry IDT_Entry;
    typedef MMU::Page Page;
    typedef MMU::Page_Table Page_Table;
    typedef MMU::Page_Directory Page_Directory;
    typedef MMU::PT_Entry PT_Entry;
    typedef MMU::Flags Flags;

    typedef System_Info<PC>::Boot_Map BM;
    typedef System_Info<PC>::Physical_Memory_Map PMM;
    typedef System_Info<PC>::Logical_Memory_Map LMM;
    typedef System_Info<PC>::Load_Map LM;

    void calibrate_timers();
    void build_lm();
    //build_pmm();
    void build_lmm();
    //get_node_id();
    //say_hi();
    void load_parts();
    void call_next();
    
    static void panic() { Machine::panic(); }
    
    char * bi;
    System_Info<PC> * si;
};

PC_Setup::PC_Setup(char * boot_image)
{
	// Get boot image loaded by the bootstrap
    bi = reinterpret_cast<char *>(boot_image);
    si = reinterpret_cast<System_Info<PC> *>(bi);
    
    // Disable hardware interrupt triggering at PIC.
    // WARNING: This code was not tested (And I don't know how to test it)!
    i8259A::reset();
    
    // Calibrate timers
    calibrate_timers();
    
    // Build the memory model
    build_lm();
    // Since we already setup pages earlier, lets ignore this step for now.
    // TODO: Come back here and think better about that!!
    //build_pmm();
    build_lmm();

    // Nothing to do here... (Original EPOS does nothing with this method).
    //get_node_id();
    
    // I'll do this latter... Not really important.
    //say_hi();

    load_parts();
    call_next();
    
    // Never reach, but, "just in case ... :-)"
    CPU::halt();
}

void PC_Setup::calibrate_timers()
{
    db<Setup>(TRC) << "PC_Setup::calibrate_timers()\n";
    // Disable speaker so we can use channel 2 of i8253
    i8255::port_b(i8255::port_b() & ~(i8255::SPEAKER | i8255::I8253_GATE2));
    // Program i8253 channel 2 to count 50 ms
    i8253::config(2, i8253::CLOCK/20, false, false);
    // Enable i8253 channel 2 counting
    i8255::port_b(i8255::port_b() | i8255::I8253_GATE2);
    // Read CPU clock counter
    TSC::Time_Stamp t0 = TSC::time_stamp();
    // Wait for i8253 counting to finish
    while(!(i8255::port_b() & i8255::I8253_OUT2));
    // Read CPU clock counter again
    TSC::Time_Stamp t1 = TSC::time_stamp(); // ascending
    si->tm.cpu_clock = (t1 - t0) * 20;
    db<Setup>(INF) << "PC_Setup::calibrate_timers:CPU clock="
           << dec << si->tm.cpu_clock / 1000 << " KHz\n";
    
    // Disable speaker so we can use channel 2 of i8253
    i8255::port_b(i8255::port_b() & ~(i8255::SPEAKER | i8255::I8253_GATE2));
    // Program i8253 channel 2 to count 50 ms
    i8253::config(2, i8253::CLOCK/20, false, false);
    // Program APIC_Timer to count as long as it can
    APIC_Timer::config(0, APIC_Timer::Count(-1), false, false);
    // Enable i8253 channel 2 counting
    i8255::port_b(i8255::port_b() | i8255::I8253_GATE2);
    // Read APIC_Timer counter
    APIC_Timer::Count t3 = APIC_Timer::read(0); // descending
    // Wait for i8253 counting to finish
    while(!(i8255::port_b() & i8255::I8253_OUT2));
    // Read APIC_Timer counter again
    APIC_Timer::Count t2 = APIC_Timer::read(0);
    si->tm.bus_clock = (t3 - t2) * 20 * 16; // APIC_Timer is prescaled by 16
    db<Setup>(INF) << "PC_Setup::calibrate_timers:BUS clock="
            << si->tm.bus_clock / 1000 << " KHz\n";
}

void PC_Setup::build_lm()
{    
    // Get boot image structure
    si->lm.has_stp = (si->bm.setup_offset != -1);
    si->lm.has_ini = (si->bm.init_offset != -1);
    si->lm.has_sys = (si->bm.system_offset != -1);
    si->lm.has_app = (si->bm.application_offset != -1);
    si->lm.has_ext = (si->bm.extras_offset != -1);

    // Check SETUP integrity and get the size of its segments
    si->lm.stp_entry = 0;
    si->lm.stp_code = ~0U;
    si->lm.stp_code_size = 0;
    si->lm.stp_data = ~0U;
    si->lm.stp_data_size = 0;
    
    if(si->lm.has_stp) {
        ELF64 * stp_elf = reinterpret_cast<ELF64 *>(&bi[si->bm.setup_offset]);
        if(!stp_elf->valid()) {
            db<Setup>(ERR) << "SETUP ELF image is corrupted!\n";
            panic();
        }
        
        si->lm.stp_entry = stp_elf->entry();
        si->lm.stp_code = stp_elf->segment_address(0);
        si->lm.stp_code_size = stp_elf->segment_size(0);
        if(stp_elf->segments() > 1) {
            for(int i = 1; i < stp_elf->segments(); i++) {
                if(stp_elf->segment_type(i) != PT_LOAD) {
                    continue;
                }
                if(stp_elf->segment_address(i) < si->lm.stp_data) {
                    si->lm.stp_data = stp_elf->segment_address(i);
                }
                si->lm.stp_data_size += stp_elf->segment_size(i);
            }
        }
    }

    // Check INIT integrity and get the size of its segments
    si->lm.ini_entry = 0;
    si->lm.ini_segments = 0;
    si->lm.ini_code = ~0U;
    si->lm.ini_code_size = 0;
    si->lm.ini_data = ~0U;
    si->lm.ini_data_size = 0;
    if(si->lm.has_ini) {
        ELF64 * ini_elf = reinterpret_cast<ELF64 *>(&bi[si->bm.init_offset]);
        if(!ini_elf->valid()) {
            db<Setup>(ERR) << "INIT ELF image is corrupted!\n";
            panic();
        }
    
        si->lm.ini_entry = ini_elf->entry();
        si->lm.ini_code = ini_elf->segment_address(0);
        si->lm.ini_code_size = ini_elf->segment_size(0);
        if(ini_elf->segments() > 1) {
            for(int i = 1; i < ini_elf->segments(); i++) {
                if(ini_elf->segment_type(i) != PT_LOAD) {
                    continue;
                }
                if(ini_elf->segment_address(i) < si->lm.ini_data) {
                    si->lm.ini_data = ini_elf->segment_address(i);
                }
                si->lm.ini_data_size += ini_elf->segment_size(i);
            }
        }
    }

    // Check SYSTEM integrity and get the size of its segments
    si->lm.sys_entry = 0;
    si->lm.sys_segments = 0;
    si->lm.sys_code = ~0U;
    si->lm.sys_code_size = 0;
    si->lm.sys_data = ~0U;
    si->lm.sys_data_size = 0;
    si->lm.sys_stack = SYS_STACK;
    si->lm.sys_stack_size = SYS_STACK_SIZE * si->bm.n_cpus;
    
    if(si->lm.has_sys) {
        ELF64 * sys_elf = reinterpret_cast<ELF64 *>(&bi[si->bm.system_offset]);
        if(!sys_elf->valid()) {
            db<Setup>(ERR) << "OS ELF image is corrupted!\n";
            panic();
        }
        
        si->lm.sys_entry = sys_elf->entry();
        si->lm.sys_code = sys_elf->segment_address(0);
        si->lm.sys_code_size = sys_elf->segment_size(0);
        if(sys_elf->segments() > 1) {
            for(int i = 1; i < sys_elf->segments(); i++) {
                if(sys_elf->segment_type(i) != PT_LOAD) {
                    continue;
                }
                if(sys_elf->segment_address(i) < si->lm.sys_data) {
                    si->lm.sys_data = sys_elf->segment_address(i);
                }
                si->lm.sys_data_size += sys_elf->segment_size(i);
            }
        }
    
        if(si->lm.sys_code != SYS_CODE) {
            db<Setup>(ERR) << "OS code segment address do not match "
                   << "the machine's memory map!\n";
            panic();
        }
        if(si->lm.sys_code + si->lm.sys_code_size > si->lm.sys_data) {
            db<Setup>(ERR) << "OS code segment is too large!\n";
            panic();
        }
        if(si->lm.sys_data != SYS_DATA) {
            db<Setup>(ERR) << "OS code segment address do not match "
                   << "the machine's memory map!\n";
            panic();
        }
        if(si->lm.sys_data + si->lm.sys_data_size > si->lm.sys_stack) {
            db<Setup>(ERR) << "OS data segment is too large!\n";
            panic();
        }
        if(si->lm.sys_data + si->lm.sys_data_size > si->lm.sys_stack) {
            db<Setup>(ERR) << "OS data segment is too large!\n";
            panic();
        }
        
        
        if(MMU::page_tables(MMU::pages(si->lm.sys_stack
                           - SYS + si->lm.sys_stack_size)) > 1) {
            db<Setup>(ERR) << "OS stack segment is too large!\n";
            panic();
        }
    }

    // Check APPLICATION integrity and get the size of its segments
    si->lm.app_entry = 0;
    si->lm.app_segments = 0;
    si->lm.app_code = ~0U;
    si->lm.app_code_size = 0;
    si->lm.app_data = ~0U;
    si->lm.app_data_size = 0;

    if(si->lm.has_app) {
        ELF64 * app_elf =
            reinterpret_cast<ELF64 *>(&bi[si->bm.application_offset]);
        if(!app_elf->valid()) {
            db<Setup>(ERR) << "Application ELF image is corrupted!\n";
            panic();
        }
        si->lm.app_entry = app_elf->entry();
        si->lm.app_code = app_elf->segment_address(0);
        si->lm.app_code_size = app_elf->segment_size(0);
        if(app_elf->segments() > 1) {
            for(int i = 1; i < app_elf->segments(); i++) {
                if(app_elf->segment_type(i) != PT_LOAD) {
                    continue;
                }
                if(app_elf->segment_address(i) < si->lm.app_data) {
                    si->lm.app_data = app_elf->segment_address(i);
                }
                si->lm.app_data_size += app_elf->segment_size(i);
            }
        }
    }

    // Check for EXTRA data in the boot image       
    if(si->lm.has_ext) {
        si->lm.ext = Phy_Addr(&bi[si->bm.extras_offset]);
        si->lm.ext_size = si->bm.img_size - si->bm.extras_offset;
    }
}

void PC_Setup::build_lmm()
{
    si->lmm.app_entry = si->lm.app_entry;
}

void PC_Setup::load_parts()
{
    // Relocate System_Info
    if(sizeof(System_Info<PC>) > sizeof(Page))
        db<Setup>(WRN) << "System_Info is bigger than a page ("
                       << sizeof(System_Info<PC>) << ")!\n";
    memcpy(reinterpret_cast<void *>(SYS_INFO), si, sizeof(System_Info<PC>));

	// Load INIT
    if(si->lm.has_ini) {
        db<Setup>(TRC) << "PC_Setup::load_init()\n";
        ELF64 * ini_elf = reinterpret_cast<ELF64 *>(&bi[si->bm.init_offset]);
        if(ini_elf->load_segment(0) < 0) {
            db<Setup>(ERR)
                << "INIT code segment was corrupted during SETUP!\n";
            panic();
        }
        for(int i = 1; i < ini_elf->segments(); i++)
            if(ini_elf->load_segment(i) < 0) {
                db<Setup>(ERR)
                    << "INIT data segment was corrupted during SETUP!\n";
                panic();
            }
    }

    // Load SYSTEM
    if(si->lm.has_sys) {
        db<Setup>(TRC) << "PC_Setup::load_os()\n";
        ELF64 * sys_elf = reinterpret_cast<ELF64 *>(&bi[si->bm.system_offset]);
        if(sys_elf->load_segment(0) < 0) {
            db<Setup>(ERR)
            << "OS code segment was corrupted during SETUP!\n";
            panic();
        }
        for(int i = 1; i < sys_elf->segments(); i++)
            if(sys_elf->load_segment(i) < 0) {
                db<Setup>(ERR)
                    << "OS data segment was corrupted during SETUP!\n";
                panic();
            }
    }

    // Load APP
    if(si->lm.has_app) {
        ELF64 * app_elf =
            reinterpret_cast<ELF64 *>(&bi[si->bm.application_offset]);

        db<Setup>(TRC) << "PC_Setup::load_app()\n";
        if(app_elf->load_segment(0) < 0) {
            db<Setup>(ERR) <<
                "Application code segment was corrupted during SETUP!\n";
            panic();
        }

        for(int i = 1; i < app_elf->segments(); i++) {
            if(app_elf->load_segment(i) < 0) {
                db<Setup>(ERR) <<
                    "Application data segment was corrupted during SETUP!\n";
                panic();
            }
        }
    }
}

void PC_Setup::call_next()
{
    int cpu_id = Machine::cpu_id();

    // Check for next stage and obtain the entry point
    register Log_Addr ip;
    if(si->lm.has_ini) {
		db<Setup>(TRC) << "Executing system global constructors ...\n";
		reinterpret_cast<void (*)()>((void *)si->lm.sys_entry)();
		ip = si->lm.ini_entry;
    } else if(si->lm.has_sys) {
    	ip = si->lm.sys_entry;
    } else {
    	ip = si->lm.app_entry;
    }

    // Arrange a stack for each CPU to support stage transition
    // Boot CPU uses a full stack, while non-boot get reduced ones
    // The 2 integers on the stacks are room for return addresses used
    // in some EPOS architecures
    Log_Addr sp =
    		SYS_STACK + SYS_STACK_SIZE * (cpu_id + 1) - 2 * sizeof(int);

    db<Setup>(TRC) << "PC_Setup::call_next(CPU=" << cpu_id
		   << ",ip=" << ip
		   << ",sp=" << (void *)sp << ") => ";
    if(si->lm.has_ini)
    	db<Setup>(TRC) << "INIT\n";
    else if(si->lm.has_sys)
    	db<Setup>(TRC) << "SYSTEM\n";
    else
    	db<Setup>(TRC) << "APPLICATION\n";

    db<Setup>(INF) << "Setup ends here!\n\n";

    Machine::smp_barrier(si->bm.n_cpus);

    // Set SP and call next stage
    CPU::sp(sp);
    static_cast<void (*)()>(ip)();

    if(Machine::cpu_id() == 0) { // Boot strap CPU (BSP)
		// This will only happen when INIT was called and Thread was disabled
		// Note we don't have the original stack here anymore!
		reinterpret_cast<void (*)()>(si->lm.app_entry)();
    }
}

/* From here, GDT is already set up and CPU is already in long mode, which
   means 4lvl pagination is enabled. What we must do in _start is load the
   elf segment and correct the instruction pointer by calling the setup
   function. */
void _start()
{
    /* Get some system information.
       Remember: The System info is at the "first sector" (first 512 Bytes) of
       the boot image. For more info see the boot file. */
    char * bi = reinterpret_cast<char *>(Traits<PC>::BOOT_IMAGE_ADDR);
    System_Info<PC> * si = reinterpret_cast<System_Info<PC> *>(bi);

    /* Check SETUP integrity and get information about its ELF structure.
       Acctually, this just test the first bytes of the pointer to see if there
       is "ELF" written on it. This is the "magic" identification for ELF
       files... */
    ELF64 * elf = reinterpret_cast<ELF64 *>(&bi[si->bm.setup64_offset]);
    if(!elf->valid()) {
        CPU::halt();
    }

    /* Check if addr is below the image size (which would replace the code we
       are executing). This will probably be false, since we configured addr
       to be 0x100000. */
    char * entry = reinterpret_cast<char *>(elf->entry());
    char * addr = reinterpret_cast<char *>(elf->segment_address(0));
    int size = elf->segment_size(0);
    if(addr <= &bi[si->bm.img_size]) {
        CPU::halt();
    }

    /* Try to load the segment 0 (Our code) to its default addr (0x100000).
       If the return value is less than 0, an error occurred.
       
       WARNING: This is odd, since EPOS load_segment() method return 0 in case
       of an error. I changed the method to return -1 instead, so this can
       work properly. */
    if(elf->load_segment(0) < 0) {
        CPU::halt();
    }

    // Move the boot image to after SETUP, so there will be nothing else
    // below SETUP to be preserved
    // SETUP code + data + 1 stack per CPU
    char * dst = MMU::align_page(entry + size + sizeof(MMU::Page));
    memcpy(dst, bi, si->bm.img_size);
    Stacks = dst;

    // Setup a single page stack for SETUP after its data segment
    // Boot strap CPU gets the highest address stack
    // SP = "entry" + "size" + #CPU * sizeof(Page)
    // Be careful: we'll loose our old stack now, so everything we still
    // need to reach PC_Setup() must be in regs or globals!
    register char * sp = const_cast<char *>(Stacks);
    ASM("mov %0, %%rsp" : : "r" (sp));
    
    // Pass the boot image to SETUP
    ASM("mov %0, %%rdi" : : "r" (Stacks));
    
    /* We are now trying to long jump to setup, to correct the instruction
       pointer. We are now probably somewhere between 0x8000 and 0x9000, but
       since the elf segment is now loaded, we can jump to the correct address
       (probably 0x100000). */
    ASM("call *%0" : : "r" (&setup));
}

/* The house is almost clean. We can now setup the kout (print stuff in the
   screen) and load the rest of the system. */
void setup(char* bi)
{
    Display::remap();

    // TODO: This should be changed when working with the MMU, so we can
    //        setup_apic_pt()!! For now we are using identity mapped page
    //        tables.    
//    APIC::reset(APIC::LOCAL_APIC_PHY_ADDR);
    
    kout << "EPOS64 - Hello :)\n";

    PC_Setup((char *)bi);
}

__END_SYS
