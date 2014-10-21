// EPOS Add Abstraction Declarations

#ifndef __component_unified_h
#define __component_unified_h

#include "traits.h"

//IF hardware, include HW, else, include SW
#ifdef HIGH_LEVEL_SYNTHESIS
#include "../../hw/components/src/system/types_hw.h"
#else
#include "../../sw/include/system/types_sw.h"
#endif

namespace Unified {

class Component {

public:
    typedef System::Channel_t Channel_t;

protected:
    Component(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid) {}

};

};

#endif
