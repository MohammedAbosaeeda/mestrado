/* Based on OSEK OS 2.2.3 Specification */
#ifndef EPOS2OSEK_DEFS_H
#define EPOS2OSEK_DEFS_H

/* 13.1 Common data types */
typedef 
enum StatusTypeEnum {
    E_OK = 0,
    E_OS_ACCESS = 1,
    E_OS_CALLEVEL = 2,
    E_OS_ID = 3,
    E_OS_LIMIT = 4,
    E_OS_NOFUNC = 5,
    E_OS_RESOURCE = 6,
    E_OS_STATE = 7,
    E_OS_VALUE = 8
} StatusType;
/* --- */

/* 13.2.1 Data Types */
typedef int TaskType;
/* --- */

/* 13.2.4 Constants */
typedef 
enum TaskStateTypeEnum {
    OSEK_RUNNING,
    OSEK_WAITING,
    OSEK_READY,
    OSEK_SUSPENDED,
    OSEK_INVALID_TASK    
} TaskStateType;

/* 13.7.3 Constants */
typedef
enum ApplicationModesEnum {
    OSDEFAULTAPPMODE
} ApplicationModes;
/* --- */

/* specific of this implementation */
typedef 
enum SomeTaskPrioritiesEnum {
    OSEK_LOWEST = 0,
    OSEK_IDLE = 1,
    OSEK_NORMAL = 2,
    OSEK_HIGH = 3
} SomeTaskPriorities;
/* --- */

#endif
