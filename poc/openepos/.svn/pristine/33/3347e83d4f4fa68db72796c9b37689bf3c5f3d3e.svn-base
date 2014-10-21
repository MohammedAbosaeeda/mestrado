// Unified components configuration

#ifndef __traits_unified_h
#define __traits_unified_h

#include "system/types.h"

namespace Unified {

template <class Imp>
struct Traits
{
    static const bool enabled = true;
    static const bool debugged = false;
    static const bool power_management = false;
    static const bool singleton = false;
};


template <> struct Traits<Add>: public Traits<void>
{
    static const bool hardware = false;

    static const unsigned int n_ids = 1;
};

template <> struct Traits<Mult>: public Traits<void>
{
    static const bool hardware = false;

    static const unsigned int n_ids = Traits<Add>::n_ids + 1;
};

template <> struct Traits<Sched<void,void> >: public Traits<void>
{
    static const bool hardware = false;

    static const unsigned int n_ids = 1;

    static const unsigned int queue_size = 8;//Static-alloc-only
};

}

#endif
