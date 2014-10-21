// EPOS Configuration Engine

#ifndef __config_h
#define __config_h

//============================================================================
// DEFINITIONS
//============================================================================
#define __SYS_NS		System
#define __BEGIN_SYS		namespace __SYS_NS {
#define __END_SYS		}
#define __USING_SYS		using namespace __SYS_NS;
#define __SYS(X)		::__SYS_NS::X

#define ASM			__asm__
#define ASMV			__asm__ __volatile__

#define __HEADER_ARCH(X)	<arch/ARCH/X.h>
#define __HEADER_MACH(X)	<mach/MACH/X.h>

//============================================================================
// ARCHITECTURE AND MACHINE SELECTION
//============================================================================
#define ARCH mips32
#define __ARCH_TRAITS_H	 __HEADER_ARCH(traits)

#define MACH axi4lite
#define __MACH_TRAITS_H	 __HEADER_MACH(traits)

//============================================================================
// CONFIGURATION
//============================================================================
#include <system/types.h>
#include <utility/meta.h>
using Unified::CASE;
using Unified::EQUAL;
using Unified::IF;
using Unified::IF_INT;
using Unified::LIST;
using Unified::SWITCH;
using Unified::Nil_Case;
using Unified::DEFAULT;
using Unified::EQUAL;
#include __HEADER_MACH(config)
#include <traits.h>
#include <system/info.h>

//============================================================================
// THINGS EVERBODY NEEDS
//============================================================================
#include <utility/ostream.h>
#include <utility/debug.h>

#endif
