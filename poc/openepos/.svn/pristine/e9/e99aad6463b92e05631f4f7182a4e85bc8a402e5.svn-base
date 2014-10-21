// EPOS Performance Monitoring Unit Abstraction Declarations

#ifndef __perfmon_h
#define __perfmon_h

#include <pmu.h>
#include <chronometer.h>

__BEGIN_SYS

class Perf_Mon
{
public:
    typedef PMU::Reg32 Reg32;
    typedef PMU::Reg64 Reg64;
    
    static const unsigned int MAX_DATA = 8000;
    
    typedef struct {
      CPU::Reg64 llc_miss[MAX_DATA];
      CPU::Reg64 xnsp_hit[MAX_DATA];
      CPU::Reg64 llc_hit[MAX_DATA];
      CPU::Reg64 l2_hit[MAX_DATA];
      CPU::Reg64 cycles[MAX_DATA];
      unsigned int counter;
    } perf_data_buffers;
    
public:
    Perf_Mon() : _start0(0), _stop0(0), _start1(0), _stop1(0), _counter_enable(false) {
      _ovf_control_0.v = 0;
       _ovf_control_1.v = 0;
    }
    
    
    void overflow_control(void) {
        //_ovf_control += PMU::rdpmc(PMU::PMC0);
        _ovf_control_0.v += (unsigned long long) Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR1);
        //_ovf_control_1.v += (unsigned long long) Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR0);
        //if(print)
        //kout << "_ovf_control_0.v = " <<  _ovf_control_0.v << " _ovf_control_0.hi = " <<  _ovf_control_0.hi << "_ovf_control_0.low = " <<  _ovf_control_0.low << endl;
       // << " _ovf_control_1.hi = " <<  _ovf_control_1.hi << " _ovf_control_1.low = " <<  _ovf_control_1.low << endl;
        reset_fixed_ctr1();
        //reset_fixed_ctr0();
        //reset(PMU::PMC0);
    }
    
    //void bus_snoops(unsigned short snoop);
    
    void bus_snoops0(unsigned short snoop) {
        //if(COUNT == 0)
          Intel_Core_Micro_PMU::config(PMU::EVTSEL0, (Intel_Core_Micro_PMU::EXT_SNOOPS | snoop | PMU::USR | PMU::OS | Intel_Core_Duo_PMU::THIS_AGENT | PMU::ENABLE)); 
        //else
          //Intel_Core_Micro_PMU::config(PMU::EVTSEL1, (Intel_Core_Micro_PMU::EXT_SNOOPS | snoop | PMU::USR | PMU::OS | Intel_Core_Duo_PMU::ALL_AGENTS | PMU::ENABLE)); 
    }
    
    //Reg64 bus_snoops(void);
    
    Reg64 bus_snoops0(void) {
        unsigned long long bus = read_pmc0();
        //kout << "**bus_snoops() read = " << bus << 
        //" _ovf_control = " << _ovf_control << "\n";
        //return bus + _ovf_control;
        return bus;
    }
    
    void bus_snoops1(unsigned short snoop) {
        //if(COUNT == 0)
          Intel_Core_Micro_PMU::config(PMU::EVTSEL1, (Intel_Core_Micro_PMU::EXT_SNOOPS | snoop | PMU::USR | PMU::OS | Intel_Core_Duo_PMU::THIS_AGENT | PMU::ENABLE)); 
        //else
          //Intel_Core_Micro_PMU::config(PMU::EVTSEL1, (Intel_Core_Micro_PMU::EXT_SNOOPS | snoop | PMU::USR | PMU::OS | Intel_Core_Duo_PMU::ALL_AGENTS | PMU::ENABLE)); 
    }
    
    //Reg64 bus_snoops(void);
    
    Reg64 bus_snoops1(void) {
        unsigned long long bus = read_pmc1();
        //kout << "**bus_snoops() read = " << bus << 
        //" _ovf_control = " << _ovf_control << "\n";
        //return bus + _ovf_control;
        return bus;
    }
    
    void l1_data_cache_snooped(unsigned short snoop_type) {
          //Intel_Core_Micro_PMU::config(PMU::EVTSEL0, (Intel_Core_Micro_PMU::CMP_SNOOP | snoop_type | PMU::USR | PMU::OS | Intel_Core_Duo_PMU::ALL_CORES | PMU::ENABLE)); 
          /*unsigned long long flags = (
            //Intel_Sandy_Bridge_PMU::DMND_RFO |
            //Intel_Sandy_Bridge_PMU::PF_RFO |
            Intel_Sandy_Bridge_PMU::PF_LLC_RFO |
            Intel_Sandy_Bridge_PMU::PF_LLC_DATA_RD |
            Intel_Sandy_Bridge_PMU::LLC_HITM |
            //Intel_Sandy_Bridge_PMU::LLC_HITS |
            //Intel_Sandy_Bridge_PMU::LLC_HITF |
            Intel_Sandy_Bridge_PMU::HITM
          );*/
          //unsigned long long flags = (Intel_Sandy_Bridge_PMU::HITM | Intel_Sandy_Bridge_PMU::PF_LLC_RFO);
          //Intel_Sandy_Bridge_PMU::config_uncore(0, 0);
    }
    
    Reg64 l1_data_cache_snooped(void) {
        return read_pmc0();
    }
    
    void l2_store_lock(void) {
        Intel_Sandy_Bridge_PMU::config(PMU::EVTSEL0, (Intel_Sandy_Bridge_PMU::L2_STORE_LOCK_RQSTS_ALL | PMU::USR | PMU::OS | PMU::ENABLE));
    }
    
    Reg64 get_l2_store_lock(void) {
        return read_pmc0();
    }
    
    void bus_snoop_stall(void) {
          Intel_Core_Duo_PMU::config(PMU::EVTSEL0, (Intel_Core_Duo_PMU::BUS_SNOOP_STALL | Intel_Core_Micro_PMU::ALL_AGENTS | Intel_Core_Micro_PMU::ALL_CORES | PMU::USR | PMU::OS | PMU::ENABLE)); 
    }
    
    Reg64 get_bus_snoop_stall(void) {
        return read_pmc0();
    }
    
    void llc_misses(void) {
        //if(COUNT == 0)
          Intel_Core_Micro_PMU::config(PMU::EVTSEL0, (Intel_Sandy_Bridge_PMU::LLC_MISSES | PMU::USR | PMU::OS | PMU::ENABLE)); 
        //else
          //Intel_Core_Micro_PMU::config(PMU::EVTSEL1, (Intel_Core_Micro_PMU::LLC_MISSES | PMU::USR | PMU::OS | PMU::ENABLE));
    }
    
    Reg64 get_llc_misses(void) {
        return read_pmc0();
    }
    
    void mem_load_uops_llc_hit_retired(void) {
      PMU::config(PMU::EVTSEL0, (Intel_Sandy_Bridge_PMU::XSNP_HITM | PMU::USR | PMU::OS | PMU::ENABLE));
    }
    
    Reg64 get_mem_load_uops_llc_hit_retired(void) {
        return read_pmc0();
    }
    
    void pebs_test(void) {
      //if(!_counter_enable) {
        //kout << "initializing counters on cpu " << Machine::cpu_id() << endl;
        PMU::config(PMU::EVTSEL0, (Intel_Sandy_Bridge_PMU::MEM_LOAD_UOPS_RETIRED_L2_HIT | 
                                  Intel_Sandy_Bridge_PMU::USR | Intel_Sandy_Bridge_PMU::OS |
                                  Intel_Sandy_Bridge_PMU::ENABLE
        ));
        PMU::config(PMU::EVTSEL1, (Intel_Sandy_Bridge_PMU::MEM_LOAD_UOPS_RETIRED_L3_HIT | 
                                  Intel_Sandy_Bridge_PMU::USR | Intel_Sandy_Bridge_PMU::OS |
                                  Intel_Sandy_Bridge_PMU::ENABLE
        ));
        for(int i = 0; i < 0xf; i++); //for some reason, the hardware needs to wait
        PMU::config(PMU::EVTSEL2, (Intel_Sandy_Bridge_PMU::XSNP_HIT | 
                                  Intel_Sandy_Bridge_PMU::USR | Intel_Sandy_Bridge_PMU::OS |
                                  Intel_Sandy_Bridge_PMU::ENABLE
        ));
        PMU::config(PMU::EVTSEL3, (Intel_Sandy_Bridge_PMU::MEM_LOAD_UOPS_MISC_RETIRED_LLC_MISS | 
                                  Intel_Sandy_Bridge_PMU::USR | Intel_Sandy_Bridge_PMU::OS |
                                  Intel_Sandy_Bridge_PMU::ENABLE
        ));
        for(int i = 0; i < 0xf; i++); //for some reason, the hardware needs to wait
        Intel_Sandy_Bridge_PMU::enable_pebs_pmc0();
        Intel_Sandy_Bridge_PMU::enable_pebs_pmc1();
        Intel_Sandy_Bridge_PMU::enable_pebs_pmc2();
        Intel_Sandy_Bridge_PMU::enable_pebs_pmc3();
        //cpu_clk_unhalted_core();
        //_counter_enable = true;
        //kout << "ok\n";
      /*} else {
        reset_fixed_ctr1();
        reset_pmc0();
        reset_pmc1();
        reset_pmc2();
        reset_pmc3();
      }*/
    }
    
    void get_pebs_test(volatile perf_data_buffers * t, bool merge) {
        /*Reg64 cycles = get_cpu_clk_unhalted_core();
        Reg64 l2_hit = read_pmc0() * 12;
        Reg64 llc_hit = read_pmc1() * 31;
        Reg64 xnsp_hit = read_pmc2() * 43;
        //Reg64 xnsp_hitm = read_pmc2() * 60;
        Reg64 llc_miss = read_pmc3() * 180;*/
        Reg64 l2_hit = read_pmc0();
        Reg64 llc_hit = read_pmc1();
        Reg64 xnsp_hit = read_pmc2();
        //Reg64 xnsp_hitm = read_pmc2() * 60;
        Reg64 llc_miss = read_pmc3();
        //kout << " L2 HIT = " << l2_hit << " LLC HIT = " << llc_hit << " XNSP HIT = " << xnsp_hit
        //<< " LLC MISS = " << llc_miss << " cycles = " << cycles << endl;
        //if(!merge) {
          t->l2_hit[t->counter] = l2_hit;
          t->llc_hit[t->counter] = llc_hit;
          t->xnsp_hit[t->counter] = xnsp_hit;
          t->llc_miss[t->counter] = llc_miss;
          //t->cycles[t->counter] = cycles;
          t->counter++;
        /*} else {
          t->l2_hit[t->counter-1] = t->l2_hit[t->counter-1] + l2_hit;
          t->llc_hit[t->counter-1] = t->llc_hit[t->counter-1] + llc_hit;
          t->xnsp_hit[t->counter-1] = t->xnsp_hit[t->counter-1] + xnsp_hitm;
          t->llc_miss[t->counter-1] = t->llc_miss[t->counter-1] + llc_miss;
          t->cycles[t->counter-1] = t->cycles[t->counter-1] + cycles;
        }*/
        /*reset_fixed_ctr1();
        reset_pmc0();
        reset_pmc1();
        reset_pmc2();
        reset_pmc3();*/
    }
    
    void cache_misses_impact(void) {
       PMU::config(PMU::EVTSEL0, (Intel_Sandy_Bridge_PMU::LLC_MISSES | PMU::USR | PMU::OS | PMU::ENABLE));
    }
    
    Reg64 get_cache_misses_impact() {
       return read_pmc0();
    }
    
    void pipeline_slots_retired_per_cycle(void) {
      PMU::config(PMU::EVTSEL1, (Intel_Sandy_Bridge_PMU::UOPS_RETIRED_RETIRE_SLOTS | PMU::USR | PMU::OS | PMU::ENABLE));
      PMU::config(PMU::EVTSEL2, (Intel_Sandy_Bridge_PMU::CPU_CLK_UNHALTED_THREAD_P | PMU::USR | PMU::OS | PMU::ENABLE));
      //PMU::config(PMU::EVTSEL3, (Intel_Sandy_Bridge_PMU::INST_RETIRED_ANY_P | PMU::USR | PMU::OS | PMU::ENABLE));
      instructions_retired();
    }
    
    float get_pipeline_slots_retired_per_cycle(void) {
        Reg64 uops_retired = read_pmc1();
        Reg64 clk_unhalted = read_pmc2();   
        //Reg64 ins_retired_any = read_pmc3();
        Reg64 ins_retired_any = get_instructions_retired();
        
        kout << "uops_retired = " << uops_retired << " clk_unhalted = " << clk_unhalted << 
        " ins_retired_any " << ins_retired_any << endl;
        
        return (float) (uops_retired / (4 * clk_unhalted));
    }
    
    void contested_access(void) {
        cpu_clk_unhalted_core(); //fixed counter
        //instructions_retired(); //fixed counter
        PMU::config(PMU::EVTSEL0, (Intel_Sandy_Bridge_PMU::XSNP_HITM | PMU::USR | PMU::OS | PMU::ENABLE));
        PMU::config(PMU::EVTSEL1, (Intel_Sandy_Bridge_PMU::XSNP_HIT | PMU::USR | PMU::OS | PMU::ENABLE));
    }
    
    void get_contested_access(void) {
        //Reg64 inst_retired = get_instructions_retired();
        //Reg64 cpu_unhalted = get_cpu_clk_unhalted_core();
        Reg64 hitm = read_pmc0();
        Reg64 hit = read_pmc1();
        
        //_ovf_control_0 += (unsigned long long) Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR1);
        //_ovf_control_1 += Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR0);
        overflow_control();
        
        kout << "_ovf_control_0.hi = " << _ovf_control_0.hi <<  " _ovf_control_0.low = " << _ovf_control_0.low;
        //kout << " _ovf_control_1.hi = " << _ovf_control_1.hi <<  " _ovf_control_1.low = " << _ovf_control_1.low << endl;
        
        //kout << "cpu_unhalted = " << _ovf_control_0.v
        //<< " inst_retired.any = " << _ovf_control_1.v
        kout << " XSNP_HITM = " << hitm 
        << " XSNP_HIT = " << hit 
        << endl;
    }
    
    void instructions_retired(void) {
        Intel_PMU_Version3::enable_fixed_ctr0();
    }
    
    Reg64 get_instructions_retired(void) {
        //if(Intel_Core_Micro_PMU::fixed_ctr0_overflow())
            //kout << "OVERFLOW\n";
        return Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR0);
    }
    
    void cpu_clk_unhalted_core(void) {
        Intel_PMU_Version3::enable_fixed_ctr1();
    }
    
    Reg64 get_cpu_clk_unhalted_core(void) {
        return Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR1);
    }
    
    void cpu_clk_unhalted_ref(void) {
        Intel_PMU_Version3::enable_fixed_ctr2();
    }
    
    Reg64 get_cpu_clk_unhalted_ref(void) {
        return Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR2);
    }
    

    
public:
  
    void reset_fixed_ctr0(void) {
        Intel_PMU_Version3::disable_fixed_ctr0();
        PMU::reset(PMU::FIXED_CTR0);
        Intel_PMU_Version3::enable_fixed_ctr0();
    }
  
    void reset_fixed_ctr1(void) {
        Intel_PMU_Version3::disable_fixed_ctr1();
        PMU::reset(PMU::FIXED_CTR1);
        Intel_PMU_Version3::enable_fixed_ctr1();
    }
  
    void reset(Reg32 reg) {
        //if(COUNT == 0)
        Intel_Core_Micro_PMU::disable(PMU::EVTSEL0);
        //else
          //Intel_Core_Micro_PMU::disable(PMU::EVTSEL1);
        PMU::reset(reg + PMU::PMC_BASE_ADDR);
        //if(COUNT == 0)
        Intel_Core_Micro_PMU::enable(PMU::EVTSEL0);
        //else
          //Intel_Core_Micro_PMU::enable(PMU::EVTSEL1);
    }
    
    void reset_pmc0() {
        PMU::disable(PMU::EVTSEL0);
        PMU::reset(PMU::PMC0 + PMU::PMC_BASE_ADDR);
        PMU::enable(PMU::EVTSEL0);
    }
    
    void reset_pmc1() {
        PMU::disable(PMU::EVTSEL1);
        PMU::reset(PMU::PMC1 + PMU::PMC_BASE_ADDR);
        PMU::enable(PMU::EVTSEL1);
    }
    
    void reset_pmc2() {
        PMU::disable(PMU::EVTSEL2);
        PMU::reset(PMU::PMC2 + PMU::PMC_BASE_ADDR);
        PMU::enable(PMU::EVTSEL2);
    }
    
    void reset_pmc3() {
        PMU::disable(PMU::EVTSEL3);
        PMU::reset(PMU::PMC3 + PMU::PMC_BASE_ADDR);
        PMU::enable(PMU::EVTSEL3);
    }
    
    Reg64 read_pmc0() {
        Reg64 count;
        count = PMU::rdpmc(PMU::PMC0);
        //_stop0 = PMU::rdpmc(PMU::PMC0);
        //count = _stop0 - _start0;
        //_start0 = _stop0;
        reset(PMU::PMC0);
        return count;
    }
    
    Reg64 read_pmc1() {
        Reg64 count;
        count = PMU::rdpmc(PMU::PMC1);
        //_stop1 = PMU::rdpmc(PMU::PMC1);
        //count = _stop1 - _start1;
        //_start1 = _stop1;
        reset(PMU::PMC1);
        return count;
    }
    
    Reg64 read_pmc2() {
        Reg64 count;
        count = PMU::rdpmc(PMU::PMC2);
        //_stop1 = PMU::rdpmc(PMU::PMC2);
        //count = _stop1 - _start1;
        //_start1 = _stop1;
        reset(PMU::PMC2);
        return count;
    }
    
    Reg64 read_pmc3() {
        Reg64 count;
        count = PMU::rdpmc(PMU::PMC3);
        //_stop1 = PMU::rdpmc(PMU::PMC2);
        //count = _stop1 - _start1;
        //_start1 = _stop1;
        reset(PMU::PMC2);
        return count;
    }
    
    Reg64 read() {
        //if(COUNT == 0)
          return read_pmc0();
        //else 
          //return read_pmc1();
    }
    
    typedef union {
      unsigned long long v;
      struct {
      unsigned int low;
      unsigned int hi;   
      };
    } overflow;

private:
    volatile Reg64 _start0;
    volatile Reg64 _stop0;
    volatile Reg64 _start1;
    volatile Reg64 _stop1;
    
    volatile overflow _ovf_control_0;
    volatile overflow _ovf_control_1;
    bool _counter_enable;
};

__END_SYS

#endif