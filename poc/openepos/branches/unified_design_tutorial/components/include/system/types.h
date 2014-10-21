// EPOS Internal Unified Components Type Management System

#ifndef __unified_types_h
#define __unified_types_h

namespace Unified {

// Dummy class for incomplete stuff
template<int>
class Dummy;

class Add;
class Mult;

template <class,class> class Sched;
namespace Scheduling_Criteria
{
    class Priority;
    class Round_Robin;
};

}

#include "types_id.h"

#endif
