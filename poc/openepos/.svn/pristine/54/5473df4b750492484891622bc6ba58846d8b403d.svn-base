// EPOS Configuration

#ifndef __traits_h
#define __traits_h

#include "system/types.h"
#include "../../../components/include/traits.h"

namespace System {

template <class Imp>
struct Traits : public Unified::Traits<Imp> {};


// Utilities
//...


// System parts
//...


// Common Mediators 
//...


// Services
//...


// Abstractions
template <> struct Traits<Thread>: public Traits<void>
{
    typedef Unified::Scheduling_Criteria::Priority Criterion;
    static const bool smp = false;
};

template <> struct Traits<Unified::Sched<Thread, Traits<Thread>::Criterion> > :  public Unified::Traits<Unified::Sched<void,void> >
{
};


}

#endif
