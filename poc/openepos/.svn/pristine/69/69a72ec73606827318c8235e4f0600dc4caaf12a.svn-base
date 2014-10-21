// EPOS IA32 CPU Mediator Initialization

#include <cpu.h>
#include <mmu.h>
#include <machine.h>
#include <system.h>
#include <system/info.h>

extern "C" { void __epos_library_app_entry(void); }

__BEGIN_SYS

void IA32::init()
{
    db<Init, IA32>(TRC) << "IA32::init()" << endl;

    _cpu_clock = System::info()->tm.cpu_clock;
    _bus_clock = System::info()->tm.bus_clock;

    // Initialize the MMU
    if(Traits<IA32_MMU>::enabled)
        IA32_MMU::init();
    else
        db<Init, IA32>(WRN) << "MMU is disabled!" << endl;

    // Initialize the CPU's Fast System Call mechanism
    // by setting up the corresponding MSRs
    if(Traits<System>::mode == Traits<Build>::KERNEL) {
        // IA32_SYSENTER_CS (MSR address 174H)
        wrmsr(0x174, SEL_SYS_CODE);
        // IA32_SYSENTER_ESP (MSR address 175H)
        wrmsr(0x175, Memory_Map<PC>::SYS_STACK + Traits<System>::STACK_SIZE * (Machine::cpu_id() + 1) - 2 * sizeof(int));
        // IA32_SYSENTER_EIP (MSR address 176H)
        wrmsr(0x176, reinterpret_cast<Reg64>(&syscall_entry));
        db<IA32>(INF) << "IA32::init() => MSR="
            << "{MSR[CS]=" << hex << rdmsr(0x174)
            << ",MSR[EIP]=" << hex << rdmsr(0x175)
            << ",MSR[ESP]=" << hex << rdmsr(0x176)
            << "}\n";
    }
}

__END_SYS
