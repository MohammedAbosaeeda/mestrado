#ifndef __armv7_traits_h
#define __armv7_traits_h

// Mediators - Architecture - ARMV7
__BEGIN_SYS

template <> struct Traits<ARMV7>: public Traits<void>
{
    enum {LITTLE, BIG};
    static const unsigned int ENDIANESS         = LITTLE;
    static const unsigned int WORD_SIZE         = 32;
    static const unsigned int CLOCK             = 667000000;
    static const bool unaligned_memory_access   = false;
};

template <> struct Traits<ARMV7_TSC>: public Traits<void>
{
	static const bool enabled = false;
};

template <> struct Traits<ARMV7_MMU>: public Traits<void>
{
	static const bool enabled = true;
	static const unsigned int MMU_TABLE_ADDR = 0x104000;
};

__END_SYS

#endif
