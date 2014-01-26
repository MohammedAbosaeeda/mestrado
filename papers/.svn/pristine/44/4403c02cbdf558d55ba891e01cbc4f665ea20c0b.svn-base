    ...
    class Perf_Mon
    {
    public:
    ...
    void cpu_clk_unhalted_bus(void) {
	    //configure PMC0 to monitor the CPU_CLK_UNHALTED_BUS event
	    Intel_Core_Micro_PMU::config(PMU::EVTSEL0, (Intel_Core_Micro_PMU::CPU_CLK_UNHALTED_BUS | PMU::USR | PMU::OS | PMU::ENABLE));      
    }

    Reg64 get_cpu_clk_unhalted_bus(void) {
	   return read_pmc0();
    }
    ...