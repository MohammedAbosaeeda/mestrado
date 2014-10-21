//EPOS Unified components Compile-time Type Information

#ifndef __unified_ctti_h
#define __unified_ctti_h

#include "types.h"

namespace Unified {

// Type -> Id
template<typename T>struct Type2Id  { enum { ID = UNKNOWN_TYPE_ID }; };

template<> struct Type2Id<Add>  { enum { ID = ADD_ID }; };
template<> struct Type2Id<Mult>  { enum { ID = MULT_ID }; };

// Id -> Type
template<Type_Id id> struct Id2Type { typedef Dummy<0> TYPE; };

template<> struct Id2Type<ADD_ID>   { typedef Add TYPE; };
template<> struct Id2Type<MULT_ID>   { typedef Mult TYPE; };

}

#endif /* CTTI_H_ */
