// SoftMIPS SETUP

// SETUP is responsible for bringing the machine into a usable state. In
// MIPS, it just setups some stuff, load drivers in memory, and copy exception
// handling functions to SYS_INTR (we cannot setup registers to point somewhere
// else, unfortunatelly).

#include <utility/elf.h>
#include <utility/string.h>
#include <utility/ostream.h>
#include <utility/debug.h>
#include <machine.h>

__USING_SYS

// System_Info Imports
typedef System_Info::Physical_Memory_Map PMM;
typedef System_Info::Logical_Memory_Map LMM;
typedef System_Info::Boot_Map BM;
typedef Memory_Map<SoftMIPS> MM;
typedef Traits<SoftMIPS> TR;

typedef MMU::Page Page;

typedef CPU::Log_Addr Log_Addr;
typedef CPU::Phy_Addr Phy_Addr;

// Prototypes
extern "C" { void _start(); }

int  main(char *, unsigned int, char *);
void call_next(Log_Addr, unsigned int);
void panic();

__BEGIN_SYS

OStream kout, kerr;
bool has_init;
bool has_system;

struct boot_data
{
	unsigned int size;
	char * addr;
};
__END_SYS


ASM("j _start");

//========================================================================
// _start
//
// Desc: In order to support larger boot images, PC_BOOT uses all memory
//	 below 640 Kb. In order to have more freedom to setup the system,
//	 we move PC_SETUP to a more convenient location.
//	 "_start" MUST BE PC_SETUP's entry point, so, if your compiler
//	 doesn't assume "_start" to be the entry point (GCC does), you
//	 somehow have to arrange for this.
//	 The initial stack pointer is inherited from PC_BOOT (i.e.,
//	 somewhere below 0x7c00).
//	 We can't "kout" here because the data segment is unreachable
//	 and "kout" has static data.
//	 THIS FUNCTION MUST BE RELOCATABLE, because it won't run at the
//	 address it has been compiled for.
//------------------------------------------------------------------------
void _start()
{
	register struct boot_data * tt;
	ASM(	"	add %0, $0, $28	\n"
			: : "r"(tt)
	   );
	struct boot_data transfer = *tt;

	
	register char * addr;
	register unsigned int size;
	
	addr = (char *)0x10001200;
	size = transfer.size;	
	
 	//panic();
	kout << "--------- Starting SETUP ---------\n";
	//panic();
    // The boot strap loaded the boot image at BOOT_IMAGE_ADDR
    char * bi = reinterpret_cast<char *>(TR::BOOT_IMAGE_ADDR);
	//kout << "Size = " << (void *)size << " addr = " << (void *)addr << "\n";

    // Get the System_Info  (first thing in the boot image)
    System_Info * si = reinterpret_cast<System_Info *>(bi);
	

    // Check SETUP integrity and get information about its ELF structure
    //ELF * elf = reinterpret_cast<ELF *>(&bi[si->bm.setup_off]);
    //if(!elf->valid())
		//panic();
	//else kout << "Elf Valid!!!\n";
	//	panic();
	char * entry = reinterpret_cast<char *>(addr);
    //if(elf->segments() != 1)
		//panic();

    // Test if we can access the address for which SETUP has been compiled
    //*entry = 'G';
    //if(*entry != 'G')
		//panic();

    // Load SETUP considering the address in the ELF header
    // Check if this wouldn't destroy the boot image
//register char * addr = reinterpret_cast<char *>(0x10001200);
//register int size = 1;
    /*if(addr <= &bi[si->bm.img_size])
	panic();
    if(elf->load_segment(0) < 0)
	panic();*/

    // Move the boot image to after SETUP, so there will be nothing else
    // below SETUP to be preserved
    // SETUP code + data + stack)
	
    //register char * dst = entry + size*2 + sizeof(Page);
	register char * dst = (char *)MM::SYS_INFO;
	/*kout << "Entry = " << (void *) entry << "\n";
	kout << "Vou copiar para = " << (void *) dst << "\n";
	kout << "Img size =  " << (void *) si->bm.img_size << "\n";*/
    memcpy(dst, bi, si->bm.img_size);

//     // Setup a single page stack for SETUP after its data segment
//     // SP = "entry" + "size" + sizeof(Page)
//     // Be carefull: we'll lost our stack, so everything must be in regs!
//     ASM("movl %0, %%esp" : : "r" (dst));

    // Call main passing 
    main(addr, size, dst);
//     // Pass the boot image to SETUP
//     ASM("pushl %0" : : "r" (dst));

//     // Pass SETUP its size
//     ASM("pushl %0" : : "r" (size));

//     // Pass SETUP its loading address
//     ASM("pushl %0" : : "r" (addr));

//     // Call main() (the assembly is necessary because the compiler generates
//     // relative calls and we need an absolute)
//     ASM("call *%0" : : "r" (&main));
}

void printchar(char ch)
{
	while((*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS) & IRQ_UART_WRITE_AVAILABLE) == 0);
	
	*reinterpret_cast<volatile unsigned int *>(0x20000000) = ch;
}

void printhex2(int i)
{
	printchar("0123456789abcdef"[(i >> 4) & 15]);
	printchar("0123456789abcdef"[i & 15]);
}

void printhex4(int i)
{
	printhex2(i >> 8);
	printhex2(i & 255);
}


void show_stuff(char * bi)
{
	for(int i = 0; i < 512; i++)
	{
		if ((i % 16) == 0) {
			printhex4(i);
			kout << " ";
		}
		kout << " ";
		char ch = bi[i];
		printhex2(ch);
		if ((i % 16) == 15) {
			int j;
			kout << " ";
			for (j = i-15; j <= i; j++) {
				ch = bi[j];
				if (ch < 32 || ch >= 127)
					ch = '.';
				printchar(ch);
			}
			kout << "\n";
		}
	}
}


//========================================================================
// main
//
// Desc: We have much to do here, you'd better take a look at the remarks
//	 below. Important for now is that we've got a single-page stack
//	 and we don't check for overflows, so be careful!
//
// Parm: setup_addr -> a pointer to SoftMIPS_SETUP's loaded image
//	 setup_size -> SoftMIPS_SETUP's size in memory
//	 bi -> a pointer to the image loaded from disk by SoftMIPS_BOOT
//------------------------------------------------------------------------
int main(char * setup_addr, unsigned int setup_size, char * bi)
{
    ELF * elf;
	//kout << "Main!!!\n";

    db<Setup>(TRC) << "SoftMIPS_Setup(stp=" << (void *)setup_addr
		   << ",stp_sz=" << setup_size
		   << ",bi=" << (void *)bi << ")\n";
    
	// System_Info is the first thing in the boot image

    System_Info * si = reinterpret_cast<System_Info *>(bi);

    has_init = (si->bm.init_off != -1);
    has_system = (si->bm.system_off != -1);
	
    if(!has_system)
		db<Setup>(WRN) << "No SYSTEM in boot image, assuming EPOS is a library!\n";
    // Check INIT integrity and get the size of its code+data segment
    Log_Addr init_entry = 0;
    unsigned int init_segments = 0;
    unsigned int init_size = 0;
    if(has_init) {
		elf = reinterpret_cast<ELF *>(&bi[si->bm.init_off]);
		if(!elf->valid()) {
			db<Setup>(ERR) << "INIT ELF image is corrupted!\n";
			panic();
		}
		init_entry = elf->entry();
		init_segments = elf->segments();
		init_size = elf->segment_size(0);
		if(init_segments > 1) {
			db<Setup>(ERR) << "INIT ELF image has more than one segment ("
				<< elf->segments() << ")!\n";
			panic();
		}
    }

    // Check OS integrity and get the size of its code and data segments
    Log_Addr sys_entry = 0;
    unsigned int sys_segments = 0;
    Log_Addr sys_code = 0;
    unsigned int sys_code_size = 0;
    Log_Addr sys_data = 0;
    unsigned int sys_data_size = 0;
    Log_Addr sys_stack = MM::SYS_STACK;
    unsigned int sys_stack_size = TR::SYSTEM_STACK_SIZE;
    if(has_system)
	{
		elf = reinterpret_cast<ELF *>(&bi[si->bm.system_off]);
		if(!elf->valid()) {
			db<Setup>(ERR) << "OS ELF image is corrupted!\n";
			panic();
		}
		sys_entry = elf->entry();
		sys_segments = elf->segments();
		sys_code = elf->segment_address(0);
		sys_code_size = elf->segment_size(0);
		sys_data = elf->segment_address(1);
		for(unsigned int i = 1; i < sys_segments; i++) {
			if(elf->segment_address(i) < sys_data)
			sys_data = elf->segment_address(i);
			sys_data_size += elf->segment_size(i);
		}
		if(sys_code != MM::SYS_CODE) {
			db<Setup>(ERR) << "OS code segment address do not match "
				<< "the machine's memory map!\n";
			panic();
		}
		if(sys_code + sys_code_size > sys_data) {
			db<Setup>(ERR) << "OS code segment is too large!\n";
			panic();
		}
	
		if(sys_data != MM::SYS_DATA) {
			db<Setup>(ERR) << "OS code segment address do not match "
				<< "the machine's memory map!\n";
			panic();
		}
	
		if(sys_data + sys_data_size > sys_stack) {
			db<Setup>(ERR) << "OS data segment is too large!\n";
			panic();
		}
		if(sys_data + sys_data_size > sys_stack) {
			db<Setup>(ERR) << "OS data segment is too large!\n";
			panic();
		}
		if(MMU::page_tables(MMU::pages(sys_stack - (MM::SYS + sys_stack_size))) > 1)
		{
			db<Setup>(ERR) << "OS stack segment is too large!\n";
			panic();
		}
    }

    // Check APP integrity and get the size of all its segments
    elf = reinterpret_cast<ELF *>(&bi[si->bm.loader_off]);
    if(!elf->valid()) {
        db<Setup>(ERR) << "Application ELF image is corrupted!\n";
        panic();
    }
    Log_Addr app_entry = elf->entry();
    unsigned int app_segments = elf->segments()+1;
    Log_Addr app_code = elf->segment_address(0);
    unsigned int app_code_size = elf->segment_size(0);
    Log_Addr app_data = elf->segment_address(1);
    unsigned int app_data_size = 0;
    for(unsigned int i = 1; i < app_segments; i++) {
		if(elf->segment_address(i) < app_data)
			app_data = elf->segment_address(i);
		app_data_size += elf->segment_size(i);
    }
	/*kout << "DATA!!! ==> " << (void *)app_segments << "\n";
	kout << "DATA!!! ==> " << (void *)app_data_size << "\n";
	kout << "DATA!!! ==> " << (void *)app_data << "\n";*/

    // If we didn't get our node's id in the boot image, we'll to try to
    // get if from an eventual BOOPT reply used to boot up the system before
    // we allocate more memory
    // if(si->bm.host_id == (unsigned short) -1)
    // get_bootp_info(&si->bm.host_id);
	si->bm.node_id = -1;

    // Say hi! :-)
    kout << "Setting up this machine as follows: \n";
    kout << "  Processor: Plasma MIPS32\n";
    kout << "  Memory:    " << si->bm.mem_size/1024 << " Kbytes\n";
    kout << "  Node Id:   ";
	
    if(si->bm.node_id != -1)
		kout << si->bm.node_id << " (" << si->bm.n_nodes << ")\n";
    else
		kout << "will get from the network!\n";
	
    kout << "  Setup:     " << setup_size << " bytes\n";
    kout << "  Init:      " << init_size << " bytes\n";
    kout << "  OS code:   " << sys_code_size << " bytes";
    kout << "\tdata: " << sys_data_size << " bytes";
    kout << "\tstack: " << sys_stack_size << " bytes\n";
    kout << "  APP code:  " << app_code_size << " bytes";
    kout << "\tdata: " << app_data_size << " bytes\n";

    // Setup the interrupt controller
    IC::init(si);

    // Align and convert the following sizes to pages
    sys_code_size = MMU::pages(sys_code_size);
    sys_data_size = MMU::pages(sys_data_size);
    sys_stack_size = MMU::pages(sys_stack_size);
    app_code_size = MMU::pages(app_code_size);
    app_data_size = MMU::pages(app_data_size);
    si->mem_size  = MMU::pages(si->bm.mem_size);
    si->mem_free = si->mem_size;

	si->pmm.sys_info  = reinterpret_cast<unsigned>(si);
	//kout << "Sys info !!!! = " << (void *)si->pmm.sys_info << "\n";
	
	

    // Allocate (reserve) memory for all entities we have to setup. 

    // Setup addresses and sizes.
    si->pmm.app_lo = MM::APP_LO;
    si->lmm.app_hi = MM::APP_HI;
    si->pmm.app_hi = MM::APP_HI;

    si->lmm.app_entry = app_entry;
	//kout << "app_entry !!!! = " << (void *)si->lmm.app_entry << "\n";
    si->pmm.phy_mem_pts = 0;
    si->pmm.io_mem_pts = 0;
    si->pmm.sys_pt = 0;
    si->pmm.sys_pd = 0;
    
    si->pmm.mach1 = 0;
	si->pmm.mach2 = MMU::align_page(app_code + (app_code_size * sizeof(Page)));
    si->pmm.mach3 = MMU::align_page(MMU::pages(app_data - app_code) - app_code_size);
    si->pmm.free = MMU::align_page(app_data + (app_data_size * sizeof(Page)));
    si->pmm.free_size = MMU::align_page(MMU::pages(MM::APP_HI - app_data) - app_data_size);

    si->mem_size = MMU::pages(MM::MEM_SIZE);
    si->mem_free = (si->pmm.mach3 + si->pmm.free_size);
    
	//kout << "Mem_free = " << si->mem_free << "\n";
	//kout << "Page = " << sizeof(Page) << " real_memfree = " << (void *)(si->mem_free * sizeof(Page) +MM::BASE) << "\n";
	//kout << "Setup_addr=" << (void *)setup_addr << " Setup_1mais=" << (void *)(reinterpret_cast<unsigned int>(setup_addr) + setup_size) << "\n";
    // Test if we didn't overlap SETUP and the boot image
	if(si->mem_free * sizeof(Page) + MM::BASE <=
       reinterpret_cast<unsigned int>(setup_addr) + setup_size)
	{
		db<Setup>(ERR) << "SETUP would have been overwritten!\n";
		//panic();
    }
	//kout << "si->mem_size - si->mem_free = " << si->mem_size - si->mem_free << "\n";
    // Zero the memory allocated to the system
	//memset(reinterpret_cast<char *>(si->mem_free * sizeof(Page) + MM::BASE), 0, (si->mem_size - si->mem_free) * sizeof(Page));
	//kout << "Passei!" << "\n";
    // Load INIT
    /*if(has_init) {
		db<Setup>(TRC) << "SoftMIPS_Setup::load_init()\n";
		elf = reinterpret_cast<ELF *>(&bi[si->bm.init_off]);
		if(elf->load_segment(0) < 0) {
			db<Setup>(ERR)
			<< "INIT code+data segment was corrupted during SETUP!\n";
			panic();
		}
    }

    // Load OS
    if(has_system) {
		db<Setup>(TRC) << "SoftMIPS_Setup::load_os()\n";
		elf = reinterpret_cast<ELF *>(&bi[si->bm.system_off]);
		if(elf->load_segment(0) < 0) {
			db<Setup>(ERR)
			<< "OS code segment was corrupted during SETUP!\n";
			panic();
		}
		for(unsigned int i = 1; i < sys_segments; i++)
			if(elf->load_segment(i) < 0) {
				db<Setup>(ERR)
					<< "OS data segment was corrupted during SETUP!\n";
				panic();
			}
	}*/

    // Load APP
    elf = reinterpret_cast<ELF *>(&bi[si->bm.loader_off]);
    db<Setup>(TRC) << "SoftMIPS_Setup::load_app()\n";
	
	if(!elf->valid())
	{
		kout << "NOT Valid ELF\n";
		panic();
	}
	//else kout << "Valid ELF\n";
	
	if(elf->load_segment(0) < 0) {
        db<Setup>(ERR)
	    << "Application code segment was corrupted during SETUP!\n";
        panic();
	}
	
    for(unsigned int i = 1; i < app_segments; i++)
	{
        if(elf->load_segment(i) < 0) {
           db<Setup>(ERR)
	       << "Application data segment was corrupted during SETUP!\n";
           panic();
        }
	}
    // Startup the FPU 
//    CPU::init_fpu();
	
	
	//for(char * i= (char *)0x10040000;i<(char *)0x10040100;i++) *i=0;
	//*(char *)0x1004001c = -1;
	
	//show_stuff((char *)0x100076f8);
	
    // SETUP ends here
    if(has_init) {
		db<Setup>(TRC) << "Executing system global constructors ...\n";
		static_cast<void (*)()>(sys_entry)();
		call_next(init_entry, 0);
    } else if(has_system)
		call_next(sys_entry, sys_data);
    else
	{
		kout << "Calling this: " << (void *)app_entry << "\n";
		call_next(app_entry, app_data);
	}
}

//========================================================================
// call_next
//
// Desc: Setup a stack for the next boot stage and call it.
//
// Parm: entry -> call target
//------------------------------------------------------------------------
void call_next(Log_Addr entry, unsigned int data)
{
    db<Setup>(TRC) << "call_next(ip=" << (void *)entry
			<< ",dt=" << (void *)data
		   << ",sp=" << (void *)(MM::SYS_STACK + TR::SYSTEM_STACK_SIZE
					 - 2 * sizeof(int))
		   << ")\n";

    db<Setup>(INF) << "SoftMIPS_Setup ends here!\n";
	db<Setup>(INF) << "--------------------------------------------------\n";
	kout << "---------- Starting APP ----------\n";

	ASM("	add $29, $0, %0	\n"
		"	add $28, $0, %1	\n"
		"	jr	 %2				\n"
		: : "r"(MM::SYS_STACK + TR::SYSTEM_STACK_SIZE - 2 * sizeof(int)),
	  	"r"(data), "r"(static_cast<unsigned int>(entry))
	);
}

//========================================================================
// panic
//
// Desc: This function is called if something goes wrong during setup,
//	 including uncaught interrupts.
//------------------------------------------------------------------------
void panic()
{
	Display dis;
	dis.puts("PANIC\n");

    /*unsigned char * fb = reinterpret_cast<unsigned char *>(
	Traits<SoftMIPS_Display>::FRAME_BUFFER_ADDRESS);

    char * str = "\nPANIC!\n";
	
    for (int i = 0; str[i] != 0; i++)
	*fb = str[i];*/
    
    CPU::halt();
//    halt = reinterpret_cast<volatile unsigned char *>(0xb0000010);
//    *halt = 0;
}
