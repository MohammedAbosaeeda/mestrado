// EPOS-- MIPS32 CPU Mediator Declarations

#ifndef __mips32_h
#define __mips32_h

#include <cpu.h>
#include <utility/debug.h>

__BEGIN_SYS

class MIPS32: public CPU_Common
{
private:
    static const unsigned int CLOCK = Traits<Machine>::CLOCK;

public:
    //static const unsigned int INT_MASK			= 0x0000ff00;
	//static const unsigned int INT_MASK			= 0x0000000f;
    //static const unsigned int EXC_MASK			= 0x0000007c;

    //static const unsigned char INT_SHIFT		= 8;
    //static const unsigned char EXC_SHIFT		= 2;

    static const unsigned char MAX_INTERRUPT	= 4;

    //static const unsigned char MAX_EXCEPTION	= 32;

    typedef Reg32 Flags;

    // CP0_SR Flags
    /*enum {
		SR_IEC		= 0x00000001, // Interrupt Enable Current
		SR_KUC		= 0x00000002, // Kernel Mode Current
		SR_IEP		= 0x00000004, // Interrupt Enable Previus
		SR_KUP		= 0x00000008, // Kernel Mode Previus
		SR_IEO		= 0x00000010, // Interrupt Enable Old
		SR_KUO		= 0x00000020, // Kernel Mode Old
		SR_IM		= 0x0000ff00, // Interrupt Mask N
		SR_ISC		= 0x00010000, // Isolate (data) cache
		SR_SWC		= 0x00020000, // Swap caches
		SR_PZ		= 0x00040000, // Zero parity bits (fossil)
		SR_CM		= 0x00080000, // Last load on cache
		SR_PE		= 0x00100000, // Cache parity error ocurred
		SR_TS		= 0x00200000, // TLB shutdown, run for your life
		SR_BEV		= 0x00400000, // Boot Exception Vectors
		SR_RE		= 0x02000000, // Reverse endianess (litte)
		SR_CU0		= 0x10000000, // Coprocessor 0 usable
		SR_CU1		= 0x20000000, // Coprocessor 1 usable
		SR_DEFAULT	= 0x0040ff01, 
		FLAG_DEFAULTS	= SR_DEFAULT
	};*/

    // CP0_CAUSE Flags
    /*enum {
		CAUSE_EXC	= 0x0000007c,
		CAUSE_IP	= 0x0000ff00,
		CAUSE_CE	= 0x30000000,
		CAUSE_BD	= 0x80000000,
	};*/

    // CAUSE_EXC (>> 2)
    /*enum {
		EXC_INTR	= 0x00000000,
		EXC_MOD		= 0x00000001,
		EXC_TLBL	= 0x00000002,
		EXC_TLBS	= 0x00000003,
		EXC_ADEL	= 0x00000004,
		EXC_ADES	= 0x00000005,
		EXC_IBE		= 0x00000006,
		EXC_DBE		= 0x00000007,
		EXC_SYSCALL	= 0x00000008,
		EXC_BP		= 0x00000009,
		EXC_RI		= 0x0000000a,
		EXC_CPU		= 0x0000000b,
		EXC_OV		= 0x0000000c,
		EXC_TRAP	= 0x0000000d,
		EXC_VCEI	= 0x0000000e,
		EXC_FPE		= 0x0000000f,
		EXC_C2E		= 0x00000010,
		EXC_RES0	= 0x00000011,
		EXC_RES1	= 0x00000012,
		EXC_RES2	= 0x00000013,
		EXC_RES3	= 0x00000014,
		EXC_RES4	= 0x00000015,
		EXC_RES5	= 0x00000016,
		EXC_WATCH	= 0x00000017,
		EXC_RES6	= 0x00000018,
		EXC_RES7	= 0x00000019,
		EXC_RES8	= 0x0000001a,
		EXC_RES9	= 0x0000001b,
		EXC_RES10	= 0x0000001c,
		EXC_RES11	= 0x0000001d,
		EXC_RES12	= 0x0000001e,
		EXC_VCED	= 0x0000001f,
	};*/

    // IRQ in SR and CAUSE (>> 8)
    enum IRQ {
		IRQ1		= 0x00000001,
		IRQ2		= 0x00000002,
		IRQ3		= 0x00000004,
		IRQ4		= 0x00000008,
		IRQ5		= 0x00000010,
		IRQ6		= 0x00000020,
		IRQ7		= 0x00000040,
		IRQ8		= 0x00000080,
    };
	
	enum ADDRESSES {
		IO_BASE			= 0x20000000,
		UART_WRITE		= IO_BASE,
		UART_READ		= IO_BASE,
		IRQ_MASK		= IO_BASE + 0x10,
		IRQ_STATUS		= IO_BASE + 0x20,
		GPIO0_OUT		= IO_BASE + 0x30,
		GPIO1_OUT		= IO_BASE + 0x40,
		GPIOA_IN		= IO_BASE + 0x50,
		COUNTER_REG		= IO_BASE + 0x60,
	};
	
	enum IRQ_SPEC {
		IRQ_UART_READ_AVAILABLE 	= 0x01,
		IRQ_UART_WRITE_AVAILABLE	= 0x02,
		IRQ_COUNTER18_NOT			= 0x04,
		IRQ_COUNTER18				= 0x08,
		IRQ_GPIO30_NOT				= 0x10,
		IRQ_GPIO31_NOT				= 0x20,
		IRQ_GPIO30					= 0x40,
		IRQ_GPIO31					= 0x80,
		IRQ_MASK_DEFAULT			= IRQ_COUNTER18, //IRQ_COUNTER18_NOT | IRQ_COUNTER18,
		IRQ_MASK_ALL				= 0xFF,
		IRQ_MASK_DISABLED			= 0x0
	};

    // Coprocessor error.
    /*enum {
		CP0		= 0x00000000,
		CP1		= 0x10000000,
		CP2		= 0x20000000,
		CP3		= 0x30000000,
	};*/

    enum {
		CP0_INDEX		= 0,
		CP0_RANDOM		= 1,
		CP0_ENTRYLO0	= 2,
		CP0_ENTRYLO1	= 3,
		CP0_CONTEXT		= 4,
		CP0_COUNT		= 9,  // R4000-only. Shame on R3000!
		CP0_ENTRYHI		= 10,
		CP0_COMPARE		= 11, // R4000-only. Shame on R3000!
		CP0_SR			= 12,
		CP0_CAUSE		= 13,
		CP0_EPC			= 14,
		CP0_PRID		= 15,
	};

    // CPU Context
    class Context {
    public:
		Context(Log_Addr entry) : _ip(entry) /*, _flags(FLAG_DEFAULTS)*/ {}
	
		Context(Reg32 ip, Reg32 at, Reg32 v0, Reg32 v1, Reg32 a0,
				Reg32 a1, Reg32 a2, Reg32 a3, Reg32 t0, Reg32 t1,
				Reg32 t2, Reg32 t3, Reg32 t4, Reg32 t5, Reg32 t6,
				Reg32 t7, Reg32 s0, Reg32 s1, Reg32 s2, Reg32 s3,
				Reg32 s4, Reg32 s5, Reg32 s6, Reg32 s7, Reg32 t8,
				Reg32 t9, Reg32 gp, Reg32 sp, Reg32 fp, Reg32 ra)
				/*,Reg32 flags)*/
			: _ip(ip), _at(at), _v0(v0), _v1(v1), _a0(a0), _a1(a1), _a2(a2),
			_a3(a3), _t0(t0), _t1(t1), _t2(t2), _t3(t3), _t4(t4), _t5(t5),
			_t6(t6), _t7(t7), _s0(s0), _s1(s1), _s2(s2), _s3(s3), _s4(s4),
			_s5(s5), _s6(s6), _s7(s7), _t8(t8), _t9(t9), _gp(gp), _sp(sp),
			_fp(fp), _ra(ra)/*, _flags(flags)*/ {}
	
		void save(Context * volatile * o) volatile;
		void load(Context * volatile * o) const volatile;
		void sp(Reg32 sp) {_sp = sp;};
		void ra(Reg32 ra) {_ra = ra;};
		void gp(Reg32 gp) {_gp = gp;};
	
		friend Debug & operator << (Debug & db, const Context & c) {
			db << "{ip=" << (void *)c._ip
			<< ",at=" << c._at << ",v0=" << c._v0 << ",v1=" << c._v1
			<< ",a0=" << c._a0 << ",a1=" << c._a1 << ",a2=" << c._a2
			<< ",a3=" << c._a3 << ",t0=" << c._t0 << ",t1=" << c._t1
			<< ",t2=" << c._t2 << ",t3=" << c._t3 << ",t4=" << c._t4
			<< ",t5=" << c._t5 << ",t6=" << c._t6 << ",t7=" << c._t7
			<< ",s0=" << c._s0 << ",s1=" << c._s1 << ",s2=" << c._s2
			<< ",s3=" << c._s3 << ",s4=" << c._s4 << ",s5=" << c._s5
			<< ",s6=" << c._s6 << ",s7=" << c._s7 << ",t8=" << c._t8
			<< ",t9=" << c._t9 << ",gp=" << (void *)c._gp
			<< ",sp=" << (void *)c._sp   << ",fp=" << (void *)c._fp
			<< ",ra=" << (void *)c._ra << "}" ;
			return db;
		}
	
		private:
		Reg32 _ip;	//31 or exc reg, in mtc0 $14
	
		Reg32 _at;	//1
		Reg32 _v0;	//2
		Reg32 _v1;	//3
		Reg32 _a0;	//4
		Reg32 _a1;	//5
		Reg32 _a2;	//6
		Reg32 _a3;	//7
		Reg32 _t0;	//8
		Reg32 _t1;	//9
		Reg32 _t2;	//10
		Reg32 _t3;	//11
		Reg32 _t4;	//12
		Reg32 _t5;	//13
		Reg32 _t6;	//14
		Reg32 _t7;	//15
		Reg32 _s0;	//16
		Reg32 _s1;	//17
		Reg32 _s2;	//18
		Reg32 _s3;	//19
		Reg32 _s4;	//20
		Reg32 _s5;	//21
		Reg32 _s6;	//22
		Reg32 _s7;	//23
		Reg32 _t8;	//24
		Reg32 _t9;	//25
		
		Reg32 _gp;	//28
		Reg32 _sp;	//29
		Reg32 _fp;	//30
		Reg32 _ra;	//31
		Reg32 _mhi;	//mfhi
		Reg32 _mlo;	//mflo
		Reg32 _pc;	//pc
	
		//Reg32 _flags;
    };

public:
    MIPS32() {}

    static Hertz clock() { return CLOCK; }

	static void int_enable(unsigned int mask = IRQ_MASK_DEFAULT)
	{
		Reg32 value;
		//db<SoftMIPS_IC>(TRC) << "CPU::int_enable()\n";
		ASMV(	".set noreorder		\n"
				"add	%0, $0,0x001	\n"
				"mtc0  %0, $12	\n"
				"nop			\n"
				".set reorder		\n"
				: "=r"(value)
			);
		//CPU::regs<CPU::IRQ_MASK>(mask);
    }

    static bool int_disable() {
		
		//CPU::regs<CPU::IRQ_MASK>(IRQ_MASK_DISABLED);
		Flags fl = flags<12>();
		bool was = fl;
		//db<SoftMIPS_IC>(TRC) << "CPU::int_disable()\n";
		//db<SoftMIPS_IC>(TRC) << "CPU::int_disable() " << fl << "\n";
		ASMV(	".set noreorder		\n"
				"mtc0  $0, $12	\n"
				"nop			\n"
				".set reorder		\n"
			);
		
		return (was);
    }

    static void switch_context(Context * volatile o, Context * volatile n);

    static bool tsl(volatile bool & lock) {
		bool was = int_disable();
		bool olock = CPU_Common::tsl(lock);
		if(was) int_enable();
		return olock;
    }
    static int finc(volatile int & number) {
		bool was = int_disable();
		int old = CPU_Common::finc(number);
		if(was) int_enable();
		return old;
    }
    static int fdec(volatile int & number) {
		bool was = int_disable();
		int old = CPU_Common::fdec(number);
		if(was) int_enable();
		return old;
    }

    // Coprocessor 0 registers
    template <int REG> static Flags volatile flags() {
		Flags value; ASMV("mfc0 %0, $%1" : "=r"(value) : "i"(REG)); return value;
    }
    template <int REG> static void flags(Flags value) {
		ASMV("mtc0 %0, $%1" : : "r"(value), "i"(REG));
	}
	
	template <unsigned int address> static int regs()
	{
		return *reinterpret_cast<volatile unsigned int*>(address);
	}

	template <unsigned int address> static void regs(unsigned int value)
	{
		*reinterpret_cast<volatile unsigned int*>(address) = value;
	}
	
	static void unset_mask(unsigned int flag)
	{
		unsigned int mask = regs<IRQ_MASK>();
		regs<IRQ_MASK>(mask & (~flag));
	}
	
	static void set_mask(unsigned int flag)
	{
		unsigned int mask = regs<IRQ_MASK>();
		regs<IRQ_MASK>(mask | flag);
	}
	
	static unsigned int get_bk_mask()
	{
		return regs<IRQ_MASK>();
	}

	static void set_bk_mask(unsigned int mask)
	{
		return regs<IRQ_MASK>(mask);
	}


    // Processor registers

    static Reg32 sp() {
		Reg32 value; ASMV("add %0,$0,$29" : "=r"(value)); return value;
    }
    static void sp(Reg32 value) {
		ASMV("add $29,%0,$0" : : "r"(value));
    }

    static Reg32 gp() {
		Reg32 value; ASMV("add %0,$0,$28" : "=r"(value)); return value;
    }
    static void gp(Reg32 value) {
		ASMV("add $28,%0,$0" : : "r"(value));
    }

	static Reg32 ra() {
		Reg32 value; ASMV("add %0,$0,$31" : "=r"(value)); return value;
	}
	static void ra(Reg32 value) {
		ASMV("add $31,%0,$0" : : "r"(value));
	}

    static Reg32 fr() {
		Reg32 value; ASMV("add %0,$0,$2" : "=r"(value)); return value;
    }
    static void fr(Reg32 value) {
		ASMV("add $2,%0,$0" : : "r"(value));
    }

    static Reg16 pdp() { return 0; }
    static void pdp(Reg16 pdp) {}

    static Log_Addr ip() {
		Log_Addr value, tmp;
	
		ASMV("	.set noreorder			\n"
			"	add  %1, $31, $0		\n"
			"	jal  __eip__			\n"
			"	nop				\n"
			"__eip__:	addi  %0, $31,  8	\n"
			"	add  $31,  %1, $0		\n"
			".set reorder			\n"
			: "=r"(value), "=r"(tmp)
			);
	
		return value;
    }

    static Context * init_stack(
		Log_Addr stack, unsigned int size, void (* exit)(),
		int (* entry)())
	{
		Log_Addr sp = stack + size;
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
		sp -= sizeof(Context);
		unsigned int * sta = (unsigned int *) sp;
		for(int i=0;i<(sizeof(Context)/sizeof(int));i++) sta[i]=0;
		return  new (sp) Context(entry);
    }

    template<typename T1>
    static Context * init_stack(
		Log_Addr stack, unsigned int size, void (* exit)(),
		int (* entry)(T1 a1), T1 a1) {
		Log_Addr sp = stack + size;
		sp -= sizeof(T1); *static_cast<T1 *>(sp) = a1;
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
		sp -= sizeof(Context);
		return new (sp) Context(entry);
    }

    template<typename T1, typename T2>
    static Context * init_stack(
		Log_Addr stack, unsigned int size, void (* exit)(),
		int (* entry)(T1 a1, T2 a2), T1 a1, T2 a2) {
		Log_Addr sp = stack + size;
		sp -= sizeof(T2); *static_cast<T2 *>(sp) = a2;
		sp -= sizeof(T1); *static_cast<T1 *>(sp) = a1;
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
		sp -= sizeof(Context);
		return new (sp) Context(entry);
    }

    template<typename T1, typename T2, typename T3>
    static Context * init_stack(
		Log_Addr stack, unsigned int size, void (* exit)(),
		int (* entry)(T1 a1, T2 a2, T3 a3), T1 a1, T2 a2, T3 a3) {
		Log_Addr sp = stack + size;
		sp -= sizeof(T3); *static_cast<T3 *>(sp) = a3;
		sp -= sizeof(T2); *static_cast<T2 *>(sp) = a2;
		sp -= sizeof(T1); *static_cast<T1 *>(sp) = a1;
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(exit);
		sp -= sizeof(int); *static_cast<int *>(sp) = Log_Addr(entry);
		sp -= sizeof(Context);
		return new (sp) Context(entry);
    }
    
    static void thread_wrapper() {
	// Get a pointer to the virtual stack created by init_stack!
        int * sp = reinterpret_cast<int *>(CPU::sp() + 72);
        int (*entry)() = reinterpret_cast<int (*)()>(*sp);
        sp += sizeof(int);
        void (*exit)() = reinterpret_cast<void (*)()>(*sp);
        entry();
        exit();
    }

    template<typename T1>
    static void thread_wrapper(T1 a1) {
	// Get a pointer to the virtual stack created by init_stack!
        int * sp = (CPU::sp() + 72);
        int (*entry)() = reinterpret_cast<int (*)()>(*sp);
        sp += sizeof(int);
        void (*exit)() = reinterpret_cast<void (*)()>(*sp);
        sp += sizeof(T1);
        T1 p1 = *static_cast<T1 *>(sp);
        entry(p1);
        exit();
    }

    template<typename T1, typename T2>
    static void thread_wrapper(T1 a1, T2 a2) {
	// Get a pointer to the virtual stack created by init_stack!
        int * sp = (CPU::sp() + 72);
        int (*entry)() = reinterpret_cast<int (*)()>(*sp);
        sp += sizeof(int);
        void (*exit)() = reinterpret_cast<void (*)()>(*sp);
        sp += sizeof(T1);
        T1 p1 = *static_cast<T1 *>(sp);
        sp += sizeof(T2);
        T2 p2 = *static_cast<T2 *>(sp);
        entry(p1, p2);
        exit();
    }

    template<typename T1, typename T2, typename T3>
    static void thread_wrapper(T1 a1, T2 a2, T3 a3) {
	// Get a pointer to the virtual stack created by init_stack!
        int * sp = (CPU::sp() + 72);
        int (*entry)() = reinterpret_cast<int (*)()>(*sp);
        sp += sizeof(int);
        void (*exit)() = reinterpret_cast<void (*)()>(*sp);
        sp += sizeof(T1);
        T1 p1 = *static_cast<T1 *>(sp);
        sp += sizeof(T2);
        T2 p2 = *static_cast<T2 *>(sp);
        sp += sizeof(T3);
        T2 p3 = *static_cast<T3 *>(sp);
        entry(p1, p2, p3);
        exit();
    }

    static int init(System_Info *si) { return 0; }
	static void show_stuff(char * bi);
	
	static void printchar(char ch)
	{
		while((*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS) & IRQ_UART_WRITE_AVAILABLE) == 0);
	
		*reinterpret_cast<volatile unsigned int *>(UART_WRITE) = ch;
	}

	static void printhex2(int i)
	{
		printchar("0123456789abcdef"[(i >> 4) & 15]);
		printchar("0123456789abcdef"[i & 15]);
	}

	static void printhex4(int i)
	{
		printhex2(i >> 8);
		printhex2(i & 255);
	}

	static void printhex8(int i)
	{
		printhex4(i >> 16);
		printhex4(i & 0x0000FFFF);
	}
};

__END_SYS

#endif
