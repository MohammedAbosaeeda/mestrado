// EPOS-- SoftMIPS Machine Mediator Implementation

#include <mach/softmips/machine.h>
#include <thread.h>
#include <cpu.h>

__BEGIN_SYS

// Class attributes
SoftMIPS::int_handler * SoftMIPS::_int_vector[INT_VECTOR_SIZE];

// Class methods
void SoftMIPS::panic()
{
    CPU::int_disable(); 
    Display display;
    //display.position(24, 73);
    display.puts("PANIC!");
    CPU::halt();
}

//     template <Handler h> static void handler_wrapper()
// 	{
// //		bool was = CPU::int_disable();
// //		if (was) CPU::int_enable();

// 		ASM
// 		(
// 			"addi	$29, $29, -4	\n"
// 			"sw		$31, 0($29)		\n"
// 			"jal	%0				\n"
// 			"lw		$31, 0($29)		\n"
// 			"addi	$29, $29, 4		\n"
// 			: : "r"(h)
// 		);
//     }

static void context_save();
static void context_load();

void SoftMIPS::int_wrap(unsigned int dummy)
{
	//int_disable via mtc0
	ASMV(	".set noreorder		\n"
			"mtc0  $0, $12	\n"
			//"nop			\n"
			".set reorder		\n"
		);
		
	context_save();
	static unsigned char cont = 0;
	static unsigned char cont2 = 0;
	int * context = (int *)(Thread::running_context());
	int * sp =  (int *) CPU::sp();
/*	while(((*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS)) & IRQ_UART_WRITE_AVAILABLE) == 0);
	*reinterpret_cast<volatile unsigned int*>(0x20000000) = '@';*/
	*reinterpret_cast<volatile unsigned int*>(DISPLAY_REG2) = cont++;
	//CPU::printhex8(*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS));
	//*reinterpret_cast<volatile unsigned int*>(IRQ_MASK) = CPU::IRQ_MASK_DISABLED;
	
	//CPU::regs<CPU::IRQ_MASK>(CPU::IRQ_MASK_DISABLED);
	//IC::enable();
	//*reinterpret_cast<volatile unsigned int*>(0x20000000) = '\n';
	//*reinterpret_cast<volatile unsigned int*>(0x20000000) = '\r';

	//CPU::printhex8(0xff);
	//CPU::printchar('A');
	//CPU::printchar('\n');
	/*CPU::printhex8((int)context);
	CPU::printchar('\n'); CPU::printchar('\r');
	CPU::printhex8((int)sp);
	CPU::printchar('\n'); CPU::printchar('\r');*/
	for(unsigned int i=0;i < sizeof(CPU::Context)/sizeof(int);i++) context[i]=sp[i];
	//memcpy(context, sp, sizeof(CPU::Context));
	register int ptr = (int) &int_dispatch;
	ASMV(	".set noreorder		\n"
			"	and	$26, %0, %0\n"
			//"	lui	$26, %hi(%0)\n"
			//"	ori	$26, $26, %lo(%0)	\n"
			"	jalr	$26	\n"
			"	nop			\n"
			".set reorder		\n"
			: "+r"(ptr)
		);
	/*while((*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS) & IRQ_UART_WRITE_AVAILABLE) == 0);
	*reinterpret_cast<volatile unsigned int*>(0x20000000) = 'B';
	*reinterpret_cast<volatile unsigned int*>(0x20000000) = '\n';
	*reinterpret_cast<volatile unsigned int*>(0x20000000) = '\r';*/

	//imprimir no seco...
	//*reinterpret_cast<volatile unsigned int*>(0x20000000) = 'A';
	//*reinterpret_cast<volatile unsigned int*>(0x20000000) = '\n';
/*while(((*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS)) & IRQ_UART_WRITE_AVAILABLE) == 0);
	 *reinterpret_cast<volatile unsigned int*>(0x20000000) = '#';*/
	*reinterpret_cast<volatile unsigned int*>(DISPLAY_REG1) = cont2++;
	//*reinterpret_cast<volatile unsigned int*>(IRQ_MASK) = CPU::IRQ_MASK_DEFAULT;
	//context_load will reenable interrupts via mtc0.
	context_load();
	//Thread * run = Thread::running();
	//run->_context->load(&run->_context);
	//cont->load(cont);

}

static void context_save()
{
	ASM(
		".set noreorder			\n"
		".set noat	\n"
		"	add		$27, $29, $0	#backup sp\n"
		"	lui		$29, 0x100a	#camanga braba\n"
		//"	ori		$29, $29	#camanga braba\n"
		"	addi	$29, $29, -132  #adjust sp	\n"
		//"	sw		$31, 0($29)			\n"
		"	sw		$1,  4($29)			\n"
		"	sw		$2,  8($29)			\n"
		"	sw		$3,  12($29)		\n"
		"	sw		$4,  16($29)		\n"
		"	sw		$5,  20($29)		\n"
		"	sw		$6,  24($29)		\n"
		"	sw		$7,  28($29)		\n"
		"	sw		$8,  32($29)		\n"
		"	sw		$9,  36($29)		\n"
		"	sw		$10, 40($29)		\n"
		"	sw		$11, 44($29)		\n"
		"	sw		$12, 48($29)		\n"
		"	sw		$13, 52($29)		\n"
		"	sw		$14, 56($29)		\n"
		"	sw		$15, 60($29)		\n"
		"	sw		$16, 64($29)		\n"
		"	sw		$17, 68($29)		\n"
		"	sw		$18, 72($29)		\n"
		"	sw		$19, 76($29)		\n"
		"	sw		$20, 80($29)		\n"
		"	sw		$21, 84($29)		\n"
		"	sw		$22, 88($29)		\n"
		"	sw		$23, 92($29)		\n"
		"	sw		$24, 96($29)		\n"
		"	sw		$25, 100($29)		\n"
			//$26 and $27 not in context
		"	sw		$28, 104($29)		\n"
		"	sw		$27, 108($29)		\n" //27 contains backup $29
		"	sw		$30, 112($29)		\n"
		"	sw		$31, 116($29)		\n"
		//"	mfc0	$27, $12			\n"
		//"	nop							\n"
		//"	sw		$27, 120($26)		\n"
		"	mfc0  $26, $14        #C0_EPC=14 (Exception PC)	\n"
		"	addi  $26, $26, -4    #Backup one opcode	\n"
		"	sw    $26, 128($29)    #pc	\n"
		"	sw		$26, 0($29)			\n" //pc(or ip) is where it stopped.
		"	mfhi  $27	\n"
		"	sw    $27, 120($29)    #hi	\n"
		"	mflo  $27	\n"
		"	sw    $27, 124($29)    #lo	\n"
		".set reorder\n"
		".set at\n"
	   );
}

void context_load()
{
	ASM(
		".set noreorder			\n"
		".set noat	\n"
		"	lw		$31, 0($29)			\n"
		"	lw		$1,  4($29)			\n"
		"	lw		$2,  8($29)			\n"
		"	lw		$3,  12($29)		\n"
		"	lw		$4,  16($29)		\n"
		"	lw		$5,  20($29)		\n"
		"	lw		$6,  24($29)		\n"
		"	lw		$7,  28($29)		\n"
		"	lw		$8,  32($29)		\n"
		"	lw		$9,  36($29)		\n"
		"	lw		$10, 40($29)		\n"
		"	lw		$11, 44($29)		\n"
		"	lw		$12, 48($29)		\n"
		"	lw		$13, 52($29)		\n"
		"	lw		$14, 56($29)		\n"
		"	lw		$15, 60($29)		\n"
		"	lw		$16, 64($29)		\n"
		"	lw		$17, 68($29)		\n"
		"	lw		$18, 72($29)		\n"
		"	lw		$19, 76($29)		\n"
		"	lw		$20, 80($29)		\n"
		"	lw		$21, 84($29)		\n"
		"	lw		$22, 88($29)		\n"
		"	lw		$23, 92($29)		\n"
		"	lw		$24, 96($29)		\n"
		"	lw		$25, 100($29)		\n"
		"	lw		$28, 104($29)		\n"
		//"	lw		$29, 108($29)		\n"
		"	lw		$30, 112($29)		\n"
		"	lw		$31, 116($29)		\n"
		//"	mfc0	$27, $12			\n"
		//"	nop							\n"
		//"	sw		$27, 120($26)		\n"
		"	lw    $27, 120($29)    #hi	\n"
		"	mthi  $27	\n"
		"	lw    $27, 124($29)    #lo	\n"
		"	mtlo  $27	\n"	
		"	lw    $26, 0($29)    #pc	\n"
		//"	addi  $29, $29, 132   #adjust sp	\n"
		"	lw		$29, 108($29)		\n"
		"	ori   $27, $0, 0x1    #re-enable interrupts	\n"
		"	jr    $26	\n"
		"	mtc0  $27, $12        #STATUS=1; enable interrupts	\n"
		".set reorder\n"
		".set at\n"
	);

}

void SoftMIPS::int_dispatch(unsigned int dummy)
{
    // Default interrupt multiplexer: highest priority
    // interrupts are the lower ones. Can be replaced,
    // if desired, using exc_handler().
	
	//0x20000020 = CPU::IRQ_STATUS -- por algum motivo da pau.
	//unsigned int mask = CPU::regs<0x20000020>() & (CPU::IRQ_MASK_DEFAULT);
	unsigned int ip   = IC::pending();
	unsigned int nr = 0, fl = 1;
	
	//unsigned int temp = CPU::get_bk_mask();
	//db<SoftMIPS>(WRN) << "SoftMIPS::int_dispatch(mask=" << temp << ")!\n";
    while (nr <= LAST_HARD_INT) {
		if(ip & fl) {
			//db<SoftMIPS>(WRN) << "SoftMIPS::int_dispatch(int=" << nr << ")!\n";
			//db<SoftMIPS>(WRN) << "__I__\n";
			
			//CPU::regs<CPU::IRQ_MASK>(mask & (~(fl)));
			//CPU::unset_mask(fl);
			_int_vector[nr](nr);
			//CPU::set_mask(fl);
		}
	
		fl <<= 1;
		++ nr;
    }
	//CPU::set_bk_mask(temp);
	//kout << "#### End interrupt handler\n";
}

/*void SoftMIPS::exc_dispatch()
{
    int exc = MASK_SHIFT(CPU::flags<CPU::CP0_CAUSE>(),
			 CPU::EXC_MASK, CPU::EXC_SHIFT);

    // Jump to the handler, based on ExcCode field value.
    // The default interrupt handler (below) is set on
    // initialization (testmips_init.cc).

    db<SoftMIPS>(WRN) << "SoftMIPS::exc_dispatch(exc=" << exc << ")!\n";
    _int_vector[exc + LAST_HARD_INT](exc);
}*/

void SoftMIPS::int_not(unsigned int i)
{
    db<SoftMIPS>(WRN) << "\nInt " << i << " occurred, but no handler installed\n";

    panic();
}

/*void main_exception_handler()
{
	ASM
	(
		".set noat				\n"
		".set noreorder			\n"
		"addi	$29, $29, -120	\n"
		"sw		$31, 116($29)	\n"
		"sw		$30, 112($29)	\n"
		"sw		$28, 108($29)	\n"
		"sw		$27, 104($29)	\n"
		"sw		$26, 100($29)	\n"
		"sw		$25,  96($29)	\n"
		"sw		$24,  92($29)	\n"
		"sw		$23,  88($29)	\n"
		"sw		$22,  84($29)	\n"
		"sw		$21,  80($29)	\n"
		"sw		$20,  76($29)	\n"
		"sw		$19,  72($29)	\n"
		"sw		$18,  68($29)	\n"
		"sw		$17,  64($29)	\n"
		"sw		$16,  60($29)	\n"
		"sw		$15,  56($29)	\n"
		"sw		$14,  52($29)	\n"
		"sw		$13,  48($29)	\n"
		"sw		$12,  44($29)	\n"
		"sw		$11,  40($29)	\n"
		"sw		$10,  36($29)	\n"
		"sw		$9,   32($29)	\n"
		"sw		$8,   28($29)	\n"
		"sw		$7,   24($29)	\n"
		"sw		$6,   20($29)	\n"
		"sw		$5,   16($29)	\n"
		"sw		$4,   12($29)	\n"
		"sw		$3,    8($29)	\n"
		"sw		$2,    4($29)	\n"
		"sw		$1,    0($29)	\n"
		"mfc0	$16, $%0		\n"
		: : "i"(CPU::CP0_EPC)
	);

// 	ASM
// 	(
// 		"jal	%0				\n"
// 		"nop					\n"
// 		: : "r"(_exc_handler), "i"(CPU::CP0_EPC)
// 	);

	ASM
	(
		"mtc0	$16, $%0		\n"
		"lw		$31, 116($29)	\n"
		"lw		$30, 112($29)	\n"
		"lw		$28, 108($29)	\n"
		"lw		$27, 104($29)	\n"
		"lw		$26, 100($29)	\n"
		"lw		$25,  96($29)	\n"
		"lw		$24,  92($29)	\n"
		"lw		$23,  88($29)	\n"
		"lw		$22,  84($29)	\n"
		"lw		$21,  80($29)	\n"
		"lw		$20,  76($29)	\n"
		"lw		$19,  72($29)	\n"
		"lw		$18,  68($29)	\n"
		"lw		$17,  64($29)	\n"
		"lw		$16,  60($29)	\n"
		"lw		$15,  56($29)	\n"
		"lw		$14,  52($29)	\n"
		"lw		$13,  48($29)	\n"
		"lw		$12,  44($29)	\n"
		"lw		$11,  40($29)	\n"
		"lw		$10,  36($29)	\n"
		"lw		$9,   32($29)	\n"
		"lw		$8,   28($29)	\n"
		"lw		$7,   24($29)	\n"
		"lw		$6,   20($29)	\n"
		"lw		$5,   16($29)	\n"
		"lw		$4,   12($29)	\n"
		"lw		$3,    8($29)	\n"
		"lw		$2,    4($29)	\n"
		"lw		$1,    0($29)	\n"
		"addi	$29, $29, 120	\n"
		"eret					\n"

		// Ugly padding			
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
		"nop					\n"
//		".set at				\n"
//		".set reorder			\n"
		: : "i"(CPU::CP0_EPC)
	);
}*/

__END_SYS
