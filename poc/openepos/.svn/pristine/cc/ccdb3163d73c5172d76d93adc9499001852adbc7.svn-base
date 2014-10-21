#ifndef __cpu_traits_h
#define __cpu_traits_h

#include <system/config.h>

__BEGIN_SYS

// IA32
template <> struct Traits<CPU>: public Traits<void>
{
    enum {LITTLE, BIG};
    static const unsigned int ENDIANESS         = LITTLE;
    static const unsigned int WORD_SIZE         = 64;
    static const unsigned int CLOCK             = 2000000000;
    static const bool unaligned_memory_access   = true;
};

template <> struct Traits<CPU_TSC>: public Traits<void>
{
};

template <> struct Traits<CPU_MMU>: public Traits<void>
{
};

template <> struct Traits<CPU_PMU>: public Traits<void>
{
  static const bool enabled = false;
};

__END_SYS

#endif
