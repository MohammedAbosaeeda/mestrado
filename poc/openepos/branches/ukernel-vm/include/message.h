
#ifndef syscall_message_h
#define syscall_message_h

#include <syscall_types.h>


struct Syscall_Message_st {
    Method method_id;
	Object_Id object;
    void** params;
    int nparams;
};

typedef struct Syscall_Message_st Syscall_Message;


#endif
