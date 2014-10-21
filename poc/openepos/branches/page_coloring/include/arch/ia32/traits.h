#ifndef __ia32_traits_h
#define __ia32_traits_h

__BEGIN_SYS

// IA32
template <> struct Traits<IA32>: public Traits<void>
{
};

template <> struct Traits<IA32_TSC>: public Traits<void>
{
};

template <> struct Traits<IA32_MMU>: public Traits<void>
{
    static const bool debugged = false;
    static const bool page_coloring = true;
    static const bool user_centric = true;
    static const unsigned int colors = 16;
    
    static const bool uncolored_system_heap = false;
};

template <> struct Traits<IA32_PMU>: public Traits<void>
{
  static const bool enabled = false;
};

__END_SYS

#endif
