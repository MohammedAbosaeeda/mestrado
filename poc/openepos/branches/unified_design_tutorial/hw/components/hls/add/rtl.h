/*
 * rtl.h
 *
 *  Created on: Jan 26, 2012
 *      Author: tiago
 */

#ifndef ADD_RTL_H_
#define ADD_RTL_H_

#include <systemc.h>
#include "../../../mach/common/rtsnoc_router/noc_to_catapult.h"

#include "rtl/cycle.cxx"

DECLARE_NODE(add_node, ADD_NS);

DECLARE_NODE_FAST(add_node);


#endif /* RTL_H_ */
