#ifndef __os_h
#define __os_h

#include <osek_defs.h>
#include "app_dep_osek_defs.h"

#define TASK(name)                int func_##name(void)
#define ALARMCALLBACK(name)       void alarmHandlerFunc_##name(void)
#define ISR(name)                 void int_handler_##name(unsigned int interrupt)

#define ActivateTask              extC_activate
#define ChainTask                 extC_chain
#define TerminateTask             extC_terminate
#define Schedule                  extC_schedule
#define GetTaskID                 extC_getTaskID
#define GetTaskState              extC_getTaskState
#define EnableAllInterrupts       extC_enableAllInterrupts
#define DisableAllInterrupts      extC_disableAllInterrupts
#define ResumeAllInterrupts       extC_resumeAllInterrupts
#define SuspendAllInterrupts      extC_suspendAllInterrupts
#define ResumeOSInterrupts        extC_resumeOSInterrupts
#define SuspendOSInterrupts       extC_suspendOSInterrupts
#define GetResource               extC_getResource
#define ReleaseResource           extC_releaseResource
#define SetEvent                  extC_setEvent
#define GetEvent                  extC_getEven
#define ClearEvent                extC_clearEvent
#define WaitEvent                 extC_waitEvent
#define GetAlarmBase              extC_getAlarmBase
#define GetAlarm                  extC_getAlarm
#define SetRelAlarm               extC_setRelAlarm
#define SetAbsAlarm               extC_setAbsAlarm
#define CancelAlarm               extC_cancelAlarm
#define GetActiveApplicationMode  extC_getActiveApplicationMode
#define StartOS                   extC_startOS
#define ShutdownOS                extC_shutdownOS

#define RUNNING       OSEK_RUNNING
#define READY         OSEK_READY
#define SUSPENDED     OSEK_SUSPENDED
#define WAITING       OSEK_WAITING
#define INVALID_TASK  OSEK_INVALID_TASK

extern  StatusType extC_activate(TaskType task);
extern  StatusType extC_activate(TaskType task);
extern  StatusType extC_chain(TaskType task);
extern  StatusType extC_terminate();
extern  StatusType extC_schedule();
extern  StatusType extC_getTaskID(TaskRefType taskRef);
extern  StatusType extC_getTaskState(TaskType taskID, TaskStateRefType state);
extern  void extC_enableAllInterrupts();
extern  void extC_disableAllInterrupts();
extern  void extC_resumeAllInterrupts();
extern  void extC_suspendAllInterrupts();
extern  void extC_resumeOSInterrupts();
extern  void extC_suspendOSInterrupts();
extern  StatusType extC_getResource(ResourceType resource);
extern  StatusType extC_releaseResource(ResourceType resource);
extern  StatusType extC_setEvent(TaskType TaskId, EventMaskType mask);
extern  StatusType extC_getEvent(TaskType TaskId, EventMaskRefType maskRef);
extern  StatusType extC_clearEvent(EventMaskType mask);
extern  StatusType extC_waitEvent(EventMaskType mask);
extern  StatusType extC_getAlarmBase(AlarmType alarmId, AlarmBaseRefType info);
extern  StatusType extC_getAlarm(AlarmType alarmId, TickRefType tick);
extern  StatusType extC_setRelAlarm(AlarmType alarmId, TickType increment, TickType cycle);
extern  StatusType extC_setAbsAlarm(AlarmType alarmId, TickType start, TickType cycle);
extern  StatusType extC_cancelAlarm(AlarmType alarmId);
extern  void extC_startOS(AppModeType mode);
extern  void extC_shutdownOS(StatusType error);
extern  void extC_setAppMode(AppModeType mode);

extern int write(int fd, const char *buf, int count);

#endif

