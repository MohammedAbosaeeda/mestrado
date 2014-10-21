// EPOS CPU PMU Mediator Implementation

#include <arch/x86/pmu.h>

__BEGIN_SYS

// Class attributes
int CPU_PMU::_version;
int CPU_PMU::_max_events;
CPU_PMU::Reg32 CPU_PMU::_num_counters;
CPU_PMU::Reg32 CPU_PMU::_num_counters_fixed;
int CPU_PMU::_cntval_bits;
CPU_PMU::Reg64 CPU_PMU::_cntval_mask;
CPU_PMU::Reg64 CPU_PMU::_max_period;
CPU_PMU::Reg64 CPU_PMU::_intel_ctrl;
CPU_PMU::perf_capabilities CPU_PMU::_intel_cap;
CPU_PMU::cpuinfo_x86 CPU_PMU::_cpuinfo;

__END_SYS
