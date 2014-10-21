#ifndef __mc13224v_traits_h
#define __mc13224v_traits_h

// Mediators - Machine - MC13224V

__BEGIN_SYS

class MC13224V_Common;

template <> struct Traits<MC13224V_Common>: public Traits<void>
{
};

template <> struct Traits<MC13224V>: public Traits<MC13224V_Common>
{
	static const unsigned int MAX_CPUS = 1;
	static const unsigned int CLOCK = 24000000;
	static const unsigned int APPLICATION_STACK_SIZE = 1048;
	static const unsigned int APPLICATION_HEAP_SIZE = 3072;
	static const unsigned int SYSTEM_HEAP_SIZE = 2*APPLICATION_HEAP_SIZE;
	static const bool SMP = false;
};

template <> struct Traits<MC13224V_Timer_0>: public Traits<MC13224V_Common>
{
	static const int FREQUENCY = 20;
};

template <> struct Traits<MC13224V_Timer_1>: public Traits<MC13224V_Common>
{
	static const int FREQUENCY = 10;//24000000/128.0;;
};

template <> struct Traits<MC13224V_Timer_2>: public Traits<MC13224V_Common>
{
	static const int FREQUENCY = 10;//24000000/128.0;
};

template <> struct Traits<MC13224V_Timer_3>: public Traits<MC13224V_Common>
{
	static const int FREQUENCY = 10;//24000000/128.0;
};

template <> struct Traits<MC13224V_RTC>: public Traits<MC13224V_Common>
{
	static const unsigned int EPOCH_DAYS = 1;
};

template <> struct Traits<MC13224V_Display>: public Traits<MC13224V_Common>
{
	static const bool on_serial = true;
};

template <> struct Traits<MC13224V_NIC>: public Traits<void>
{
    static const bool enabled = false;

    typedef LIST<CMAC> NICS;

    static const unsigned int RADIO_UNITS = NICS::Count<Radio>::Result;
};

template <> struct Traits<CMAC>: public Traits<void>
{
    static const bool debugged      = false;
    static const bool SM_STEP_DEBUG = false;

    static const unsigned int  FREQUENCY       = 0;
    static const unsigned int  POWER           = 5;
    static const bool          TIME_TRIGGERED  = false;
    static const bool          COORDINATOR     = false;
    static const unsigned int  SLEEPING_PERIOD = 1000; //ms
    static const unsigned long TIMEOUT         = 1000; //ms
    static const unsigned int  ADDRESS         = 0x0001;
    static const unsigned int  MTU             = 118; 

    typedef CMAC_States::Generic_Sync		Sync_State;
    typedef CMAC_States::IEEE802154_Pack	Pack_State;
    typedef CMAC_States::Contention_Empty	Contention_State;
    typedef CMAC_States::Generic_Tx		Tx_State;
    typedef CMAC_States::Ack_Rx_Empty	Ack_Rx_State;
    typedef CMAC_States::Generic_Lpl		Lpl_State;
    typedef CMAC_States::Generic_Rx		Rx_State;
    typedef CMAC_States::IEEE802154_Unpack	Unpack_State;
    typedef CMAC_States::Ack_Tx_Empty	Ack_Tx_State;
};

__END_SYS

#endif

