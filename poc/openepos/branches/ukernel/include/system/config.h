// EPOS Configuration Engine

#ifndef __config_h
#define __config_h

//============================================================================
// DEFINITIONS
//============================================================================

namespace EPOS {
    namespace S {
        namespace C {}
    }
    namespace U {}
}

#define __BEGIN_SYS             namespace EPOS { namespace S {
#define __END_SYS               }}
#define _SYS                    ::EPOS::S

#define __BEGIN_CORE            namespace EPOS { namespace S {
#define __END_CORE              }}
#define _CORE                   ::EPOS::S

#define __BEGIN_API             namespace EPOS {
#define __END_API               }
#define _API                    ::EPOS

#define ASM                     __asm__
#define ASMV                    __asm__ __volatile__

#define __HEADER_ARCH(X)	    <arch/ARCH/X.h>
#define __HEADER_MACH(X)	    <mach/MACH/X.h>
#define __HEADER_APPLICATION_T(X)   <../app/X##_traits.h>
#define __HEADER_APPLICATION(X)     __HEADER_APPLICATION_T(X)

//============================================================================
// ARCHITECTURE, MACHINE, AND APPLICATION SELECTION
// This section is generated automatically from makedefs
//============================================================================
#define ARCH xxx
#define __ARCH_TRAITS_H	        __HEADER_ARCH(traits)

#define MACH xxx
#define __MACH_TRAITS_H	        __HEADER_MACH(traits)

#define APPLICATION xxx
#define __APPLICATION_TRAITS_H  __HEADER_APPLICATION(APPLICATION)

//============================================================================
// CONFIGURATION
//============================================================================
#include <system/types.h>
#include <system/meta.h>
#include __APPLICATION_TRAITS_H
#include <system/info.h>

//============================================================================
// THINGS EVERBODY NEEDS
//============================================================================
#include <utility/ostream.h>
#include <utility/debug.h>

#endif
