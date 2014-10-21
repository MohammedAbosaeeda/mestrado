/*! @file
 *  @brief EPOS PPC32 CPU Mediator Declarations
 *
 *  CVS Log for this file:
 *  \verbinclude include/arch/ppc32/cpu_h.log
 */
#ifndef __ppc32_h
#define __ppc32_h

#include <cpu.h>
#include <utility/debug.h>

__BEGIN_SYS


class PPC32: public CPU_Common
{
private:
    static const unsigned int CLOCK = Traits<Machine>::CLOCK;

public:
    typedef unsigned int IO_Port;
    typedef unsigned int IO_Irq;

    /// Machine State Register Bit Definition
    enum MSR {
        MSR_WE      = 0x00040000,       ///< Put CPU in Wait State
        MSR_CE      = 0x00020000,       ///< Enable Critical Interrupts
        MSR_EE      = 0x00008000,       ///< Enable External Interrupts
        MSR_PR      = 0x00004000,       ///< Put CPU in Problem State (a.k Supervisor Mode)
        MSR_ME      = 0x00001000,       ///< Enable Machine Check
        MSR_DWE     = 0x00000400,       ///< Enable %Debug Wait Mode
        MSR_DE      = 0x00000200,       ///< Enable %Debug Interrupts
        MSR_IR      = 0x00000020,       ///< Enable Instruction Address Relocation
        MSR_DR      = 0x00000010,       ///< Enable Data Address Relocation
        MSR_DEFAULT = (MSR_CE | MSR_EE) ///< Default Machine State
    };

    /// Exception Syndrome Register Bit Definition
    enum ESR {
        ESR_MCI     = 0x80000000,  ///< Instruction Machine Check Executed
        ESR_PIL     = 0x08000000,  ///< Illegal Instruction Executed
        ESR_PPR     = 0x04000000,  ///< Privileged Instruction Executed
        ESR_PTR     = 0x02000000,  ///< Trap Instruction Executed
        ESR_DST     = 0x00800000,  ///< Data Store Fault Occurred
        ESR_DIZ     = 0x00400000,  ///< Zone Fault Occurred
        ESR_U0F     = 0x00008000   ///< Instruction caused a U0 Fault
    };

    /// Special Purpose Registers Addresses
    enum SPR {
        SLER  = 0x3BB,  ///< Storage Little Endian Register
        SGR   = 0x3B9,  ///< Storage Guarded Register
        ESR   = 0x3D4,  ///< Exception Syndrome Register
        EVPR  = 0x3D6,  ///< Exception Vector Prefix Register
        SSR0  = 0x01A,  ///< Save/Restore Register 0
        SSR1  = 0x01B,  ///< Save/Restore Register 1
        SSR2  = 0x3DE,  ///< Save/Restore Register 2
        SSR3  = 0x3BC,  ///< Save/Restore Register 3
        TSR   = 0x3D8,  ///< Timer Status Register
        TCR   = 0x3DA,  ///< Timer Control Register
        PIT   = 0x3DB   ///< Programmable Interval Timer Counter
    };

    class Context
    {
      public:
        Reg32 _r[32];   ///< General Purpouse Registers
        Reg32 _msr;     ///< Machine State Register
        Reg32 _lr;      ///< Link Register
        Reg32 _ctr;     ///< Counter Register
        Reg32 _cr;      ///< Conditional Register
        Reg32 _xer;     ///< Fixed Point Exception Register
        Reg32 _srr0;    ///< Save/Restore Register 0
        Reg32 _srr1;    ///< Save/Restore Register 1
        Reg32 _srr2;    ///< Save/Restore Register 2
        Reg32 _srr3;    ///< Save/Restore Register 3

      public:
        Context(Log_Addr entry): _msr(PPC32::MSR_DEFAULT),
            _lr(entry), _ctr(0), _cr(0), _xer(0), _srr0(0),
            _srr1(0), _srr2(0), _srr3(0) { };
        friend Debug & operator << (Debug & db, const Context & c) {
            db << "{msr="    << (void *)c._msr
               << ",lr="     << (void *)c._lr
               << ",ctr="    << (void *)c._ctr
               << ",xer="    << (void *)c._xer
               << ",srr0="   << (void *)c._srr0
               << ",srr1="   << (void *)c._srr1
               << ",srr2="   << (void *)c._srr2
               << ",srr3="   << (void *)c._srr3
               << ",cr="     << (void *)c._cr
               << "}" ;
            return db;
        }
        void save() volatile;
        void load() const volatile;
      };

public:
    PPC32() {}
    ~PPC32() {}

    static Hertz clock() { return CLOCK; }

    static void switch_context(Context * volatile * o, Context * volatile n);

    static void int_enable() {
      volatile Reg32 value;
      ASMV("mfmsr %0; sync" : "=r" (value) : );
      value |= (MSR_CE);
      ASMV("mtmsr %0; sync" :: "r" (value) );
      ASMV("wrteei 1");
    };

    static void int_disable() {
      ASMV("wrteei 0");
      volatile Reg32 value;
      ASMV("mfmsr %0; sync" : "=r" (value) : );
      value &= ~(MSR_CE);
      ASMV("mtmsr %0; sync" :: "r" (value) );
    };

    static void halt() {
      volatile Reg32 value;
      ASMV("mfmsr %0; sync" : "=r" (value) : );
      value |= MSR_WE;
      ASMV("sync; mtmsr %0;" : : "r" (value) );
    } 

    static bool tsl(volatile bool & lock) {
      int_disable();
      bool ret = CPU_Common::tsl(lock);
      int_enable();
      return ret;
    }

    static int finc(volatile int & number) {
      int_disable();
      int ret = CPU_Common::finc(number);
      int_enable();
      return ret;
    }

    static int fdec(volatile int & number) {
      int_disable();
      int ret = CPU_Common::fdec(number);
      int_enable();
      return ret;
    }

    static Reg32 htonl(Reg32 v) { return v; }
    static Reg16 htons(Reg16 v) { return v; }
    static Reg32 ntohl(Reg32 v) { return v; }
    static Reg16 ntohs(Reg16 v) { return v; }

    static Reg32 cpu_to_le32(Reg32 v) { return swap32(v); }
    static Reg16 cpu_to_le16(Reg16 v) { return swap16(v); }
    static Reg32 le32_to_cpu(Reg32 v) { return swap32(v); }
    static Reg16 le16_to_cpu(Reg16 v) { return swap16(v); }

    static Context * init_stack(
	Log_Addr stack, unsigned int size, void (* exit)(),
	int (* entry)()) {
        void (*wrapper)() = &CPU::entry_wrapper;
	Log_Addr sp = stack + size;
	sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
        sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
        sp -= 2 * sizeof(int); //Stack Frame Header
	sp -= sizeof(Context);
	return  new (sp) Context(wrapper);
    }

    template<typename T1>
    static Context * init_stack(
	Log_Addr stack, unsigned int size, void (* exit)(),
	int (* entry)(T1 a1), T1 a1) {
        void (*wrapper)(T1) = &CPU::entry_wrapper;
	Log_Addr sp = stack + size;
	sp -= sizeof(T1); *static_cast<T1 *>(sp) = a1;
	sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
        sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
        sp -= 2 * sizeof(int); //Stack Frame Header
	sp -= sizeof(Context);
	return new (sp) Context(wrapper);
    }

    template<typename T1, typename T2>
    static Context * init_stack(
	Log_Addr stack, unsigned int size, void (* exit)(),
	int (* entry)(T1 a1, T2 a2), T1 a1, T2 a2) {
        void (*wrapper)(T1,T2) = &CPU::entry_wrapper;
	Log_Addr sp = stack + size;
	sp -= sizeof(T2); *static_cast<T2 *>(sp) = a2;
	sp -= sizeof(T1); *static_cast<T1 *>(sp) = a1;
	sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
        sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
        sp -= 2 * sizeof(int); //Stack Frame Header
	sp -= sizeof(Context);
	return new (sp) Context(wrapper);
    }

    template<typename T1, typename T2, typename T3>
    static Context * init_stack(
	Log_Addr stack, unsigned int size, void (* exit)(),
	int (* entry)(T1 a1, T2 a2, T3 a3), T1 a1, T2 a2, T3 a3) {
        void (*wrapper)(T1,T2,T3) = &CPU::entry_wrapper;
	Log_Addr sp = stack + size;
	sp -= sizeof(T3); *static_cast<T3 *>(sp) = a3;
	sp -= sizeof(T2); *static_cast<T2 *>(sp) = a2;
	sp -= sizeof(T1); *static_cast<T1 *>(sp) = a1;
	sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
        sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
        sp -= 2 * sizeof(int); //Stack Frame Header
	sp -= sizeof(Context);
	return new (sp) Context(wrapper);
    }

    static Reg32 fr(){ return gpr(3); }

    static void fr(const Reg32 fr) { gpr(3, fr); }

    static Reg32 sp() { return gpr(1); }

    static void sp(const Reg32 sp) { gpr(1, sp); }

    static Reg32 pdp() { return 0; }
    static void pdp(Reg32 pdp) {}

    //PowerPC Especific Methods.
    static Reg32 spr(const unsigned int addr){
      Reg32 value;
      ASMV("mfspr %0,%1" :"=r" ( value ): "i" ( addr ) );
      return value;
    };

    static void spr(const unsigned int addr, unsigned int value) {
      ASMV("mtspr %0,%1" :: "i" ( addr ), "r" ( value ) );
    };

    static void sync_io(){
        ASMV("eieio");
    }

    //Entry Wrappers for passing parameters through registers...
    static void entry_wrapper(){
        Log_Addr sp = static_cast<Log_Addr>(CPU::sp());
        Log_Addr backchain = *((Log_Addr *)sp);
        //unsigned int frame_size = (unsigned int)(backchain - sp);
        //kout << frame_size << "\n";
        //Destroys this Function Frame Stack since it's useless on this context :)
        sp = *((Log_Addr *)sp);
        sp += 2 * sizeof(int);//(Frame Header)
        Log_Addr entry_point = *static_cast<unsigned int *>(sp);
        sp += sizeof(int);
        Log_Addr implicit_exit = *static_cast<unsigned int *>(sp);
        CPU::sync_io();
        int (*entry)() = (int (*)())entry_point;
        (*entry)();
        void (*ret_addr)() = (void (*)())implicit_exit;
        (*ret_addr)();
    }

    template<class T1>
    static void entry_wrapper(T1 a1){
        Log_Addr sp = static_cast<Log_Addr>(CPU::sp());
        //Destroys this Function Frame Stack since it's useless on this context :)
        sp = *((Log_Addr *)sp);
        sp += 2 * sizeof(int); //(Frame Header)
        Log_Addr entry_point = *static_cast<unsigned int *>(sp);
        sp += sizeof(int);
        Log_Addr implicit_exit = *static_cast<unsigned int *>(sp);
        int (*entry)(T1) = (int (*)(T1))entry_point;
        sp += sizeof(T1);
        T1 p1 = *static_cast<T1 *>(sp);
        CPU::sync_io();
        (*entry)(p1);
        void (*ret_addr)() = (void (*)())implicit_exit;
        (*ret_addr)();
    }

    template<class T1, class T2>
    static void entry_wrapper(T1 a1, T2 a2){
        Log_Addr sp = static_cast<Log_Addr>(CPU::sp());
        //Destroys this Function Frame Stack since it's useless on this context :)
        sp = *((Log_Addr *)sp);
        sp += 2 * sizeof(int); //(Frame Header)
        Log_Addr entry_point = *static_cast<unsigned int *>(sp);
        sp += sizeof(int);
        Log_Addr implicit_exit = *static_cast<unsigned int *>(sp);
        int (*entry)(T1,T2) = (int (*)(T1,T2))entry_point;
        sp += sizeof(T1);
        T1 p1 = *static_cast<T1 *>(sp);
        sp += sizeof(T2);
        T2 p2 = *static_cast<T2 *>(sp);
        CPU::sync_io();
        (*entry)(p1, p2);
        void (*ret_addr)() = (void (*)())implicit_exit;
        (*ret_addr)();
    }

    template<class T1, class T2, class T3>
    static void entry_wrapper(T1 a1, T2 a2, T3 a3){
        Log_Addr sp = static_cast<Log_Addr>(CPU::sp());
        //Destroys this Function Frame Stack since it's useless on this context :)
        sp = *((Log_Addr *)sp);
        sp += 2 * sizeof(int); //(Frame Header)
        Log_Addr entry_point = *static_cast<unsigned int *>(sp);
        sp += sizeof(int);
        Log_Addr implicit_exit = *static_cast<unsigned int *>(sp);
        int (*entry)(T1,T2,T3) = (int (*)(T1,T2,T3))entry_point;
        sp += sizeof(T1);
        T1 p1 = *static_cast<T1 *>(sp);
        sp += sizeof(T2);
        T2 p2 = *static_cast<T2 *>(sp);
        sp += sizeof(T3);
        T3 p3 = *static_cast<T3 *>(sp);
        CPU::sync_io();
        (*entry)(p1, p2, p3);
        void (*ret_addr)() = (void (*)())implicit_exit;
        (*ret_addr)();
    }

// PPC32 implementation methods
private:
    static Reg32 gpr(unsigned int addr){
      Reg32 value;
      ASM("addi %0, %1, 0  \n" : "=r"(value) : "i"(addr));
      return value;
    };

    static void gpr(unsigned int addr, Reg32 value){
      ASM("addi %1, %0, 0 \n" : : "r"(value), "i"(addr));
    };

// PPC32 attributes
private:


};

__END_SYS

#endif
