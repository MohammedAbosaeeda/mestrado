#ifndef __panda_traits_h
#define __panda_traits_h

__BEGIN_SYS

template <> struct Traits<PandaBoard>: public Traits<void>
{
	static const unsigned int MAX_CPUS = 2;
	static const unsigned int CLOCK = 667*1000*1000;//need to check
	static const unsigned int APPLICATION_STACK_SIZE = 1024*4096;
	static const unsigned int APPLICATION_HEAP_SIZE  = 64*1024*1024;
	static const unsigned int SYSTEM_HEAP_SIZE = APPLICATION_HEAP_SIZE / 2;

	static const unsigned int PRIORITY_HEAP_BASE_ADDR = 0;
	static const unsigned int PRIORITY_HEAP_SIZE = 4;
	static const unsigned int PRIORITY_HEAP_TOP_ADDR = PRIORITY_HEAP_BASE_ADDR + PRIORITY_HEAP_SIZE - 1;
};

template <> struct Traits<Zynq>: public Traits<void>
{
    static const bool enabled   = true;
};
template <> struct Traits<PandaBoard_IC>: public Traits<void>
{
    static const bool enabled = false;
};

template <> struct Traits<PandaBoard_Timer>: public Traits<void>
{
    static const bool enabled = false;
};

template <> struct Traits<PandaBoard_NIC>: public Traits<void>
{
    typedef LIST<PandaBoard_NIC> NICS;
};

__END_SYS

#endif

