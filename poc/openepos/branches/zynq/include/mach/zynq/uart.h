#ifndef __panda_uart_h__
#define __panda_uart_h__

#include <uart.h>
//#define SIM_MODE
__BEGIN_SYS
class Zynq_UART : public UART_Common
{
	//Reference: Zynq-7000 All Programmable SoC, Technical Reference Manual.
	//ug585-Zynq-7000-TRM.pdf
	typedef unsigned long Reg32;
	volatile Reg32* _base;
    enum {
        UART0_BASE = 0xE0000000,
        UART1_BASE = 0xE0001000
    };
	static const unsigned int default_uart = 0;
	public:
    Zynq_UART(unsigned int baud, unsigned int data_bits, unsigned int parity,
    unsigned int stop_bits, unsigned int unit = 0){
	}


	Zynq_UART(unsigned int base = default_uart)
	{
		if(base == 0)
			_base = (volatile Reg32*)UART0_BASE;
		else if(base == 1)
			_base = (volatile Reg32*)UART1_BASE;

		if(default_uart == 1)
			ps7_pripherals_init_data();
		else
			init();
	}

	private:
	//Don't use this function!
	void mask_write(Reg32 a, Reg32 b, Reg32 c)
	{
		Reg32* p = (Reg32*)a;
		*p = ((*p)&(~b)) | c;
	}
	void ps7_pripherals_init_data()
	{
		//unlock SLCR
		mask_write(0XF8000008,0x0000FFFF,0x0000DF0D);
		set_pll_clock(26);
		/* Bit 8:
		Termination is used during read transactions and
		may be disabled (automatically by hardware)
		when there are no reads taking place through the
		DDR Interface. Disabling termination reduces
		power consumption.
		0: termination always enabled
		1: use 'dynamic_dci_ts' to disable termination
		when not in use
		NOTE: This bit must be 0 during DRAM
		init/training. It may be set to 1 after init/training
		completes.

		Bit7:
		Use ibuf_disable_into control ibuf
		0: ibuf is enabled
		1: use ibuf_disable_in_to control enable
		NOTE: This must be 0 during DRAM
		init/training and can only be set to 1 after
		init/training completes.
		*/
		//mask_write(0XF8000B48,0x00000180,0x00000180);
		//mask_write(0XF8000B4C,0x00000180,0x00000180);
		//mask_write(0XF8000B50,0x00000180,0x00000180);
		//mask_write(0XF8000B54,0x00000180,0x00000180);
		//lock SLCR
		mask_write(0XF8000004,0x0000FFFF,0x0000767B);
		//Divide baud rate by 6+1
		mask_write(0XE0001034,0x000000FF,0x00000006);
		//set CD = 62
		mask_write(0XE0001018,0x0000FFFF,0x0000003E);
		//enable and reset receive/transmit data paths
		mask_write(0XE0001000,0x000001FF,0x00000017);
		//set 1N8
		mask_write(0XE0001004,0x00000FFF,0x00000020);

		mask_write(0XE000D000,0x00080000,0x00080000);
		mask_write(0XF8007000,0x20000000,0x00000000);

		mask_write(0xE0001024,0x20,0x20);
		//these settings configure a 115200 baud rate
		//assuming uart_ref_clk = 50mhz
	}

	public:
	//Compute and return the current baud rate
	static unsigned int baud_rate()
	{
		unsigned int uart_ref_clk, clk, clk_div, cd, bdiv;
		clk = (in32(SLCR::UART_CLK_CTRL) >> 4 ) & 0x3;
		clk_div = (in32(SLCR::UART_CLK_CTRL) >> 8 ) & 0x3f;

		switch(clk)
		{
			case 0: //IO PLL
				uart_ref_clk = 0xf8000108;
				break;
			case 2: //ARM PLL
				uart_ref_clk = 0xf8000100;
				kout << "(using ARM PLL)";
				break;
			case 3://DDR PLL
				uart_ref_clk = 0xf8000104;
				kout << "(using DDR PLL)";
				break;
			default:
				kout << "Error defining UART's source clock\n";
				return 0;
		}
		//PS_CLK is 33.33MHZ, and is multiplied by the PLL
		uart_ref_clk = ((in32(uart_ref_clk) >> 12) & 0x7f) * 33000000; //866MHZ by default
		//kout << "uart_ref_clk: " << uart_ref_clk << endl;
		cd = in32(BAUD_RATE_GEN_REG0 | (default_uart ==0?UART0_BASE:UART1_BASE)) & 0xffff;
		//kout << "CD: " << CD << endl;
		bdiv = in32(BAUD_RATE_DIVIDER_REG0 | (default_uart==0?UART0_BASE:UART1_BASE)) & 0xff;
		//kout << "BDIV: " << bdiv << endl;

		
		clk = uart_ref_clk/clk_div; //should be 50MHZ (866/17)
		//kout << "clk: " << clk << endl;

		return (clk/(cd*(bdiv+1)));
	}
	private:
    //registers offset
    enum {
        CONTROL_REG0            = 0x00,
        MODE_REG0               = 0x04,
        INTRPT_EN_REG0          = 0x08,
        INTRPT_DIS_REG0         = 0x0C,
        INTRPT_MASK_REG0        = 0x10,
        CHNL_INT_STS_REG0       = 0x14,
        BAUD_RATE_GEN_REG0      = 0x18,
		RCVR_TIMEOUT_REG0		= 0x1C,
        RCVR_FIFO_TRIGGER_LEVEL0= 0x20,
        MODEM_CTRL_REG0         = 0x24,
        MODEM_STS_REG0          = 0x28,
        CHANNEL_STS_REG0        = 0x2C,
        TX_RX_FIFO0             = 0x30,
        BAUD_RATE_DIVIDER_REG0  = 0x34,
        FLOW_DELAY_REG0         = 0x38,
        TX_FIFO_TRIGGER_LEVEL0  = 0x44,
	};
	// System Level Control Registers (slcr)
	class SLCR{
		public:
		enum{
			UART_RST_CTRL	= 0xF8000228,
			UART_CLK_CTRL	= 0xF8000154,
			SLCR_UNLOCK		= 0xF8000008,
			MIO_PIN_46		= 0xF80007B8,
			MIO_PIN_47		= 0xF80007BC,
			MIO_PIN_48		= 0xF80007C0,
			MIO_PIN_49		= 0xF80007C4,
		};
		//UART_RST_CTRL bits
		enum{
			UART1_REF_RST		= 1<<3,
			UART0_REF_RST		= 1<<2,
			UART1_CPU1X_RST		= 1<<1,
			UART0_CPU1X_RST		= 1,
		};
		//UART_CLK_CTRL bits
		enum{
			DIVISOR			= 0x1f00,
			SRCSEL			= 0x30,
			CLKACT1			= 1<<1,
			CLKACT0			= 1,
			SRCSEL_SHIFT	= 4,
			DIVISOR_SHIFT	= 8,
		};
		//SLCR_UNLOCK bits
		enum{
			UNLOCK_KEY		= 0xffff,
		};
	};
    //Control_reg0 bits
    enum{
        STOPBRK     = 1<<8,
        STARTBRK    = 1<<7,
        RSTTO       = 1<<6,
        TXDIS       = 1<<5,
        TXEN        = 1<<4,
        RXDIS       = 1<<3,
        RXEN        = 1<<2,
        TXRES       = 1<<1,
        RXRES       = 1,
    };

    //MODE_REG0 bits
    enum{
        CHMODE      = (1<<9)|(1<<8),
        NBSTOP      = (1<<7)|(1<<6),
        PAR         = (1<<5)|(1<<4)|(1<<3),
        CHRL        = (1<<2)|(1<<1),
        CLKS        = 1,
    };
    //Intrpt_en_reg0, Intrpt_dis_reg0, 
    //Intrpt_mask_reg0 and Chnl_int_sts_reg0 bits
    enum{
        TOVR        = 1<<12,
        TNFUL       = 1<<11,
        TTRIG       = 1<<10,
        DMSI        = 1<<9,
        TIMEOUT     = 1<<8,
        PARE        = 1<<7,
        FRAME       = 1<<6,
        ROVR        = 1<<5,
        TFUL        = 1<<4,
        TEMPTY      = 1<<3,
        RFUL        = 1<<2,
        REMPTY      = 1<<1,
        RTRIG       = 1,
    };
    //Baud_rate_gen_reg0 bits
    enum{
        CD          = 0xffff,
    };
    //Rcvr_timeout_reg0 bits
    enum{
        RTO         = 0xff,
    };
    //Rcvr_FIFO_trigger_level0 bits
    enum{
        RCVR_RTRIG  = 0x3f,
    };
    //Modem_ctrl_reg0 bits
    enum{
        FCM         = 1<<5,
        RTS         = 1<<1,
        DTR         = 1,
    };
    //Modem_sts_reg0 bis
    enum{
        FCMS        = 1<<8,
        DCD         = 1<<7,
		RI			= 1<<6,
		DSR			= 1<<5,
		CTS			= 1<<4,
		DDCD		= 1<<3,
		TERI		= 1<<2,
		DDSR		= 1<<1,
        //DTR        = 1, defined above
    };
    //Channel_sts_reg0 bis
	class STS{ //just a namespace due to name conflicts.
		public:
		enum{
			TNFUL       = 1<<14,
			TTRIG       = 1<<13,
			FDELT		= 1<<12,
			TACTIVE		= 1<<11,
			RACTIVE		= 1<<10,
			TFUL		= 1<<4,
			TEMPTY		= 1<<3,
			RFUL		= 1<<2,
			REMPTY		= 1<<1,
			RTRIG       = 1,
		};
	};
	//TX_RX_FIFO0 bits
	enum{
		FIFO		= 0xff,
	};
	//Baud_rate_divider_reg0 bits
	enum{
		BDIV		= 0xff,
	};
	//Flow_delay_reg0 bits
	enum{
		FDEL		= 0x3f,
	};
	//Tx_FIFO_trigger_level0 bits
	enum{
		TTRIG_TxFIFO= 0x3f,
	};

private:


	//Auxiliary functions.
	static void out32(const Reg32 reg, const Reg32 value){
		(*(volatile Reg32 *)reg) = value;
	}

	/*Writes to the specified register (using _base), matching the 
	 * mask to write the value to the corresponding area.
	 * It uses a read-modify strategy (non-destructive).*/
	inline void write_bit(Reg32 reg, Reg32 mask, Reg32 value){
		int n=0;
		//Matching mask with the value.
		while((mask & (1<<n)) == 0)
			++n;
		Reg32* p = (Reg32*)(_base + reg/sizeof(Reg32));
		*p = ((*p)&(~mask)) | (mask & (value << n));
	}
	
	inline Reg32 get_value(Reg32 reg){
		return *((Reg32*)reg);
	}
	static inline Reg32 in32(Reg32 reg){
		return (*((Reg32*)reg));
	}
	enum{
		IO_PLL_CTRL=0xf8000108,
		IO_PLL_CFG=0xf8000118,
		PLL_STATUS= 0xf800010c,

		FDIV_SHIFT = 12,
		PLL_BYPASS_FORCE = 1<<4,
		PLL_RESET = 1,

		IO_PLL_LOCK = 1<<2,
		
		LOCK_CNT_SHIFT = 12,//10 bits
		PLL_CP_SHIFT = 8,//4 bits
		PLL_RES_SHIFT=4,//4 bits
	};
    void set_pll_clock(unsigned int fdiv)
	{
		out32(IO_PLL_CTRL, (in32(IO_PLL_CTRL)&(0x7f<<FDIV_SHIFT)) |
				(fdiv<<FDIV_SHIFT));
		out32(IO_PLL_CFG, (2<<PLL_CP_SHIFT) | (12<<PLL_RES_SHIFT) |
				(375<<LOCK_CNT_SHIFT));
		out32(IO_PLL_CTRL, in32(IO_PLL_CTRL) | PLL_BYPASS_FORCE);
		
		out32(IO_PLL_CTRL, in32(IO_PLL_CTRL) | PLL_RESET);
		out32(IO_PLL_CTRL, in32(IO_PLL_CTRL) & 0xfffffffe);

		while((in32(PLL_STATUS) & IO_PLL_LOCK) == 0)
			;
		out32(IO_PLL_CTRL, in32(IO_PLL_CTRL) & 0xffffffef);
	}
	
	void init(){
		//Unlocking SLCR registers (p. 1500)
		out32(SLCR::SLCR_UNLOCK, 0xDF0D);
		set_pll_clock(26);//33MHZ*26

		//(Following instructions from page 554 of the manual.)
        //Reset Controller (using option 2, p. 558)
		out32(SLCR::UART_RST_CTRL, 0);
		if((Reg32)_base == UART0_BASE){
			//write(SLCR::UART_RST_CTRL, SLCR::UART0_REF_RST, 0);
			//write(SLCR::UART_RST_CTRL, SLCR::UART0_CPU1X_RST, 0);

			/*Configure I/O signal routing*/
			out32(SLCR::MIO_PIN_46, 0x12E1);
			out32(SLCR::MIO_PIN_47, 0x12E0);

        /*	Configure UART_Ref_Clk (assuming I/O PPL = 667mhz.
			Dividing it by 17 to get 50mhz), based on p. 628.*/
			out32(SLCR::UART_CLK_CTRL, SLCR::CLKACT0 | (17<<SLCR::DIVISOR_SHIFT));
		}
		else if((Reg32)_base == UART1_BASE){
			//write(SLCR::UART_RST_CTRL, SLCR::UART1_REF_RST, 0);
			//write(SLCR::UART_RST_CTRL, SLCR::UART1_CPU1X_RST, 0);

			/*Configure I/O signal routing*/
			out32(SLCR::MIO_PIN_49, 0x12E1);
			out32(SLCR::MIO_PIN_48, 0x12E0);

			out32(SLCR::UART_CLK_CTRL, SLCR::CLKACT1 | (17<<SLCR::DIVISOR_SHIFT));
		}

        //Configure controller functions
		write_bit(MODE_REG0, 0x3FF, 0x20);

		write_bit(CONTROL_REG0, RXEN, 0);
		write_bit(CONTROL_REG0, RXDIS, 1);
		write_bit(CONTROL_REG0, TXEN, 0);
		write_bit(CONTROL_REG0, TXDIS, 1);

		/* The input clock for UART is the UART_REF_CLK.
		 * The UART_REF_CLK is sourced from IO_PLL,
		 * which in turn mutiplies (by default) PS_CLK (33.33MHz) by 26.
		 * We can divide this 866MHz clock from the IO_PLL to 50MHz by
		 * writing 17 in the UART_CLK_CTRL, as we did above.
		 * Therefore, the input clock for UART (sel_clk) is of 50MHz.
		 * The baud rate is calculated as follows:
		 * baud_rate = sel_clk/(CD * (BDIV+1))
		 * Keeping BDIV (which is used for over sample) in its default value (15),
		 * we end up with a CD = 325 (if the target baud rate is 9600).
		 * */
		if(Traits<Zynq_UART>::BAUD_RATE == 9600)
		{
			write_bit(BAUD_RATE_GEN_REG0, CD, 325);
			write_bit(BAUD_RATE_DIVIDER_REG0, BDIV, 15); //default value
		}
		else if(Traits<Zynq_UART>::BAUD_RATE == 115200)
		{
			write_bit(BAUD_RATE_GEN_REG0, CD, 62);
			write_bit(BAUD_RATE_DIVIDER_REG0, BDIV, 6);
		}
		else //the general case
		{	//CD should not be bigger than 2^16.
			write_bit(BAUD_RATE_GEN_REG0,
					50000000/(16*Traits<Zynq_UART>::BAUD_RATE), 325);
			write_bit(BAUD_RATE_DIVIDER_REG0, BDIV, 15);
		}
		write_bit(CONTROL_REG0, TXRES, 1);
		write_bit(CONTROL_REG0, RXRES, 1);

		write_bit(CONTROL_REG0, RXEN, 1);
		write_bit(CONTROL_REG0, RXDIS, 0);
		write_bit(CONTROL_REG0, TXEN, 1);
		write_bit(CONTROL_REG0, TXDIS, 0);

		//Disabling FIFO interrupts (option b)
		write_bit(RCVR_FIFO_TRIGGER_LEVEL0, RCVR_RTRIG, 0);
		write_bit(RCVR_TIMEOUT_REG0, RTO, 0);

		out32(((unsigned int)_base) | CONTROL_REG0, 0x117);


		//Configure Interrupts
		write_bit(RCVR_FIFO_TRIGGER_LEVEL0, RCVR_RTRIG, 0);

		write_bit(INTRPT_DIS_REG0, RTRIG, 1);
		write_bit(INTRPT_EN_REG0, RTRIG, 0);
		if((*(Reg32*)INTRPT_MASK_REG0) != 0) //for debug
			; //Problem
		
        //Configure modem controls (optional) TODO
    }
	public:
    //Manage Transmit and receive data
	void put(unsigned char ch){
		//while((get_value(CHANNEL_STS_REG0) & STS::TEMPTY) != 1);
		while((get_value(CHANNEL_STS_REG0) & STS::TFUL) != 0);
		write_bit(TX_RX_FIFO0, FIFO, (Reg32)ch);
	}
	unsigned char get(){
		while((get_value(CHANNEL_STS_REG0) & STS::RTRIG) != 1);
		return (*(unsigned char*)(TX_RX_FIFO0)) & FIFO;
	}



};




/*******************************************************/





class TL16C750_UART : public UART_Common 
{
public:
    enum {
        UART1_BASE = 0x4806A000,
        UART2_BASE = 0x4806C000,
        UART3_BASE = 0x48020000,
        UART4_BASE = 0x4806E000,
    };
    
    //registers offset
    enum {
        THR     = 0x00,
        DLL     = 0x00,
        DLH     = 0x04,
        IER     = 0x04,
        FCR     = 0x08,
        LCR     = 0x0C,
        LSR     = 0x14,
        MDR1    = 0x20,
        SYNC    = 0x54,
        SYSS    = 0x58,        
    };
    
    //IER bits
    enum {
        RHR_IT          = 1,
        THR_IT          = (1 << 1),
        LINE_STS_IT     = (1 << 2),
        MODEM_STS_IT    = (1 << 3),
        SLEEP_MODE      = (1 << 4),
        XOFF_IT         = (1 << 5),
        RTS_IT          = (1 << 6),
        CTS_IT          = (1 << 7),
        IER_ENABLE_ALL  = 0xFF,
        IER_DISABLE_ALL = 0x00,
    };
    
    //FCR bits
    enum {
        FIFO_EN         = 1,
        RX_FIFO_CLEAR   = (1 << 1),
        TX_FIFO_CLEAR   = (1 << 2),
        DMA_MODE        = (1 << 3),
        //TX_FIFO_TRIG
        //RX_FIFO_TRIG 
    };
    
    //LCR bits
    enum {
        LENGTH_5     = 0x00,
        LENGTH_6     = 0x01,
        LENGTH_7     = 0x02,
        LENGTH_8     = 0x03,
        NB_STOP      = 1 << 2,
        PARITY_EN    = 1 << 3,
        PARITY_TYPE1 = 1 << 4,
        PARITY_TYPE2 = 1 << 5,
        BREAK_EN     = 1 << 6,
        DIV_EN       = 1 << 7,
        CONFIG_A     = 0x80,
        CONFIG_B     = 0xBF,
    };
    
    //LSR bits
    enum {
        RX_FIFO_E   = 1,
        RX_OE       = (1 << 1),
        RX_PE       = (1 << 2),
        RX_FE       = (1 << 3),
        RX_BI       = (1 << 4),
        TX_FIFO_E   = (1 << 5),
        TX_SR_E     = (1 << 6),
        RX_FIFO_STS = (1 << 7),
    };    
    
    //MDR1 bits
    enum {
        MDR1_SIR_MODE   = 0x01,
        MDR1_16_MODE    = 0x02,
        MDR1_13_MODE    = 0x03,
        MDR1_MIR_MODE   = 0x04,
        MDR1_FIR_MODE   = 0x05,
        MDR1_CIR_MODE   = 0x06,
        MDR1_DISABLE    = 0x07,
        MDR1_IR_SLEEP   = (1 << 3),
        MDR1_SET_TXIR   = (1 << 4),
        MDR1_SCT        = (1 << 5),
        MDR1_SIP_MODE   = (1 << 6),
        MDR1_FRAME_MODE = (1 << 7),
    };
    
    //SYNC bits
    enum {
        AUTOIDLE        = 1,
        SOFTRESET       = (1 << 1),
        ENAWAKEUP       = (1 << 2),
        NOIDLE          = (0x01 << 3),
        SMARTIDLE       = (0x02 << 3),
        SMARTIDLEWK     = (0x03 << 3),
    };
    
    //SYSS bits
    enum {
        RESETDONE = 1,
    };
    
    //default clock and baud rate
    enum {
        CLOCK = 48 * 1000 * 1000,
        BAUD_RATE = 115200,
    };
    
protected:
    const static unsigned char TX_BUFFER_FULL  = (1 << 5);
    const static unsigned char RX_BUFFER_EMPTY = (1 << 4);

public:
    TL16C750_UART(unsigned long base) {
        _base = (unsigned long*)base;
        _flag = (unsigned long*)(base + LSR);
        
        //reset and init UART
        //reset();
        //init(CLOCK, BAUD_RATE);
    }

    void put(unsigned char c) {
		/*
        while (!(*_flag & TX_FIFO_E)) ;
        *(_base + THR) = c;
		*/
    }

    char get() {
        while (!(*_flag & RX_FIFO_E)) ;
        //*_flag |= RX_FIFO_E;
        return (char)(*_base & 0xFF);
    }
    
    void init(unsigned int clock, unsigned int baud_rate) {
        // disable UART
        disable();
        
        //enable access to uart_fcr uart_ier 
        *(_base + LCR) = 0x00;
        
        //configure FIFOs
        *(_base + FCR) = FIFO_EN; 
        
        //disable interrupts and sleep mode
        *(_base + IER) = IER_DISABLE_ALL;
        
        //enable access to DLH e DLL
        *(_base + LCR) = CONFIG_B;
        
        //load the new divisor UART_16X mode
        unsigned int divisor = (unsigned int) clock / (baud_rate << 4);
        *(_base + DLL) = divisor;
        *(_base + DLH) = (divisor >> 8);
        
        //configure protocol formatting
        //char lenght = 8, stop bits = 1, no parity, no break control bit, normal operating condition
        *(_base + LCR) = (LENGTH_8);
        
        //switch to UART mode
        enable();
    }
    
private:
    void disable() {
        *(_base + MDR1) = MDR1_DISABLE;
    }
    
    void enable() {
        *(_base + MDR1) = MDR1_16_MODE;
    }
    
    void reset() {
        *(_base + SYNC) |= SOFTRESET;
        while(!(*(_base + SYSS) & RESETDONE)) ; 
    }

protected:
    volatile unsigned long * _base;
    volatile unsigned long * _flag;
};

class PandaBoard_UART : public TL16C750_UART {
public:
    PandaBoard_UART(unsigned int unit=0) :
        TL16C750_UART(UART3_BASE) {}

    // Dummy contructor with configuration
    PandaBoard_UART(unsigned int baud, unsigned int data_bits, unsigned int parity,
    unsigned int stop_bits, unsigned int unit = 0) : TL16C750_UART(UART3_BASE) {}
};
__END_SYS

#endif
