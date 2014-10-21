#ifndef syscall_response_h
#define syscall_response_h

#include <syscall_types.h>
#include <system/config.h>

__BEGIN_SYS

typedef struct Syscall_Response_st {
    Object_Id id;
    int error_code;
} Syscall_Response;

__END_SYS

#endif
