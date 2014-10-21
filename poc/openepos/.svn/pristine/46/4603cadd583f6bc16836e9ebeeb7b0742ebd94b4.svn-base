// EPOS Unified Design HW Types Declarations

#ifndef __types_hw_h
#define __types_hw_h

/*
 * Catapult stuff
 */
#include <ac_int.h>
#include <ac_channel.h>

#include "../traits.h"
#include "ctti.h"

namespace System {

typedef ac_int<8,false> Msg_Header_Elem_t;
typedef struct{
    Msg_Header_Elem_t   msg_type;
    Msg_Header_Elem_t   instance_id;
    Msg_Header_Elem_t   type_id;
} Msg_Header_t;
enum{
    MSG_TYPE_CALL = 0,
    MSG_TYPE_RESP,
    MSG_TYPE_CALL_DATA,
    MSG_TYPE_RESP_DATA,
    MSG_TYPE_ERROR
};

typedef ac_int<32,false> Msg_Payload_t;

typedef struct {
    Msg_Payload_t   payload;
    Msg_Header_t    header;
} Msg_t;

//ATTENTION: the msg's members order above will yield the following pkt in the channel:
//  56         . . .            32 31    0
//  h_type_id h_inst_id h_msg_type payload

typedef ac_channel<Msg_t> Channel_t;


/*
 * HW stubs and types
 */

enum{
	IMP_DISPATCHER, //top level=true and hw = true
	IMP_DUMMY, //top_level=true and hw=false
	IMP_STUB, //top_level=false and (hw=false or (hw=true and global=true))
	IMP_UNIFIED, //top_level=false and (hw=true and global=false)
	IMP_ERROR,
};
template<bool top_level, bool hardware, bool global>
struct IMP_TYPE {
	enum {
		Result = top_level ? (hardware ? IMP_DISPATCHER : IMP_DUMMY)
							: ((!hardware || (hardware && global)) ? IMP_STUB
																   : (hardware && !global) ? IMP_UNIFIED : IMP_ERROR)
	};
};

#define SMASH3(x,y,z) x##y##z
#define QMAKESTR(x) #x
#define MAKESTR(x) QMAKESTR(x)
//#define C_INCLUDE(name) MAKESTR(SMASH3(../../hw/components/src/, name, .h))
#define C_INCLUDE(name) MAKESTR(../../hw/components/src/name.h)



};

#endif /* TYPES_H_ */
