// EPOS-- LEON2 Setup

#include <utility/elf.h>
#include <utility/string.h>
#include <utility/ostream.h>
#include <utility/debug.h>
#include <machine.h>

__USING_SYS

// SPARC32 Imports
typedef CPU::Phy_Addr Phy_Addr;
typedef CPU::Log_Addr Log_Addr;

// System_Info Imports
typedef System_Info::Logical_Memory_Map LMM;
typedef Memory_Map<LEON2> MM;
typedef Traits<LEON2> TR;

// Prototypes
typedef void (Function)(void);
extern "C" { void _start();       	}
extern "C" { void __setup_entry(); 	}
extern "C" { static int * _end; 	}
void setup_lmm(LMM *,  Log_Addr, Log_Addr);
void setup_ttable(Phy_Addr);
void call_next(register Log_Addr);
void panic();

__BEGIN_SYS

bool has_system;
bool has_appelf;

__END_SYS

//========================================================================
// _setup_entry
//         
//                                                             
//------------------------------------------------------------------------

void __setup_entry() {		

    SPARC32 cpu;
    LEON2_IC ic;
    System_Info * si = (System_Info *) MM::SYS_INFO;

    // Set EFLAGS
    cpu.flags(cpu.flags() | SPARC32::FLAG_CLEAR);

    // Setup IC
    IC::init(si);

    has_system = false;
    has_appelf = false;
    void * app_entry = (void *) &_start;

    // Say hi! :-)
    kout << "Setting up this node as follow: \n";
    kout << "  Processor: SPARC32\n";
    kout << "  Memory:    " << si->bm.mem_size/1024 << " Kbytes\n";

    si->mem_size  = si->bm.mem_size;
    si->mem_free  = si->mem_size;

    // SYS_DATA
    si->mem_free -= (0x40800000 - MM::SYS_DATA);
    si->pmm.sys_data = MM::SYS_DATA;

    // SYS_CODE
    si->mem_free -= (MM::SYS_DATA - MM::SYS_CODE);
    si->pmm.sys_code = MM::SYS_CODE;

    // INT_VEC
    si->mem_free -= (MM::SYS_CODE - MM::INT_VEC);
    si->pmm.int_vec = MM::INT_VEC;

    // SYS_PT
    si->mem_free -= (MM::INT_VEC - MM::SYS_PT);
    si->pmm.sys_pt = MM::SYS_PT;

    // SYS_PD
    si->mem_free -= (MM::SYS_PT - MM::SYS_PD);
    si->pmm.sys_pd = MM::SYS_PD;

    // SYS_STACK
    si->mem_free -= (MM::SYS_PD - MM::SYS_STACK);
    si->pmm.sys_stack = MM::SYS_STACK;    

    // All memory bellow this is free to applications
    si->pmm.app_lo = MM::APP_LO;
    si->pmm.app_hi = MM::APP_HI;   
    si->pmm.sys_info  = reinterpret_cast<unsigned>(si);
    si->pmm.free      = reinterpret_cast<unsigned>(&_end);
    si->pmm.free_size = si->pmm.sys_stack - si->pmm.free;

    // Setup the IDT
    setup_ttable(si->pmm.int_vec);

    // Set new TTBL
    cpu.ttbr(si->pmm.int_vec);

    // Setup LMM. SI already is in the right place 
    setup_lmm(&si->lmm, app_entry, si->pmm.app_hi);
    
    // Enable the Interrupt Controller to propagate interrups but keep
    // them disabled on the CPU
    CPU::int_disable();
//    IC::enable();

    call_next((Log_Addr) app_entry);
}

//========================================================================
// setup_ttable
//
// Desc: 
//
// Parm: 
//------------------------------------------------------------------------

void setup_ttable(Phy_Addr addr)
{
    db<Setup>(TRC) << "setup_ttbl(ttbl=" << (void *)addr << ")\n";

    //In xmb_vritex we just move the initial ttable to the right place,
    //since EPOS is ELF image
    //But do not forget, this is a incomplete ttable
    memcpy(((void*) addr), ((void*) MM::MEM_BASE), 112);
}

//========================================================================
// setup_lmm                                                            
//                                                                      
// Desc: Setup the Logical_Memory_ap in System_Info. 
//                                                                      
// Parm: lmm    -> logical memory map
//       app_hi -> highest logicall RAM address available to applications
//------------------------------------------------------------------------
void setup_lmm(LMM * lmm, Log_Addr app_entry, Log_Addr app_hi)
{
        lmm->app_entry  = app_entry;   
        lmm->app_hi 	= app_hi;
}

//========================================================================
// call_next
//                                                                      
// Desc: Setup a stack for the next boot stage and call it.
//                                                                      
// Parm: entry -> call target
//------------------------------------------------------------------------
void call_next(register Log_Addr entry)
{ 
    db<Setup>(TRC) << "call_next(e=" << (void *)entry << ")\n";    

    entry = entry;

    ASM("wr %g0, 0x0, %wim	\n"
	"nop; nop; nop		\n"
	"rd %psr, %g1		\n"
	"andn %g1, 0x1f, %g1	\n"
	"wr %g1, %psr  		\n" //CWP = 0
        "wr %g0, 0x002, %wim  	\n" //WIM = 1
	"nop; nop; nop		");

    ASM("set %0, %%g1" : : "i"(MM::SYS_STACK + 
                               TR::SYSTEM_STACK_SIZE - 4 * sizeof(int)));
    ASM("mov %g1, %fp           \n"
	"sub %fp, 128, %sp      \n"); 

    _start();
}

//========================================================================
// panic
//
// Desc: This function is called if something goes wrong during setup,
//       including uncaught interrupts.
//------------------------------------------------------------------------
void panic()
{
    ASM("ta 0");
}

