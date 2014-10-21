#ifndef __osek_defs_h
#define __osek_defs_h


#define E_OK            0
#define E_OS_ACCESS     1
#define E_OS_CALLLEVEL  2
#define E_OS_ID         3
#define E_OS_LIMIT      4
#define E_OS_NUFUNC     5
#define E_OS_RESOURCE   6
#define E_OS_STATE      7
#define E_OS_VALUE      8
#define E_OS_NOFUNC     9

typedef unsigned char         StatusType;
typedef int                   TaskType;
typedef TaskType*             TaskRefType;
typedef unsigned char         TaskStateType;
enum {
	OSEK_RUNNING,
	OSEK_READY,
	OSEK_SUSPENDED,
	OSEK_WAITING,
	OSEK_INVALID_TASK
};

typedef TaskStateType*        TaskStateRefType;
typedef unsigned char         ResourceType;
typedef unsigned int          EventMaskType;
typedef EventMaskType*        EventMaskRefType;
typedef unsigned char         AlarmType;
typedef unsigned int          TickType;
typedef TickType*             TickRefType;
struct AlarmBaseType {
    TickType maxallowedvalue;
    TickType ticksperbase;
    TickType mincycle;
};
typedef struct AlarmBaseType*        AlarmBaseRefType;
typedef unsigned char         AppModeType;
typedef unsigned char         OSServiceIdType;

typedef int                   PriorityType;


typedef OSServiceIdType OSServiceIds;
enum {
    OSServiceId_ActivateTask,
    OSServiceId_TerminateTask,
    OSServiceId_ChainTask,
    OSServiceId_Schedule,
    OSServiceId_GetTaskID,
    OSServiceId_GetTaskState,
    OSServiceId_EnableAllInterrupts,
    OSServiceId_DisableAllInterrupts,
    OSServiceId_ResumeAllInterrupts,
    OSServiceId_SuspendAllInterrupts,
    OSServiceId_ResumeOSInterrupts,
    OSServiceId_SuspendOSInterrupts,
    OSServiceId_GetResource,
    OSServiceId_ReleaseResource,
    OSServiceId_SetEvent,
    OSServiceId_ClearEvent,
    OSServiceId_GetEvent,
    OSServiceId_WaitEvent,
    OSServiceId_GetAlarmBase,
    OSServiceId_GetAlarm,
    OSServiceId_SetRelAlarm,
    OSServiceId_SetAbsAlarm,
    OSServiceId_CancelAlarm,
    OSServiceId_GetActiveApplicationMode
};

typedef AppModeType AppModes;
enum {
    OSDEFAULTAPPMODE
};


#endif
