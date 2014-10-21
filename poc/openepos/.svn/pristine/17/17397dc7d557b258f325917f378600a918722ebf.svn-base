extern void switchtask(void **old, void **new);
extern void dispatch(void **new);
extern void schedule(unsigned char);

void Schedule();
StatusType ActivateTask(TaskType id);
unsigned char __TerminateTask();
unsigned char __ChainTask(TaskType tid);
unsigned char TerminateTask();
unsigned char ChainTask(TaskType tid);
void EnableAllInterrupts();
void DisableAllInterrupts();
void ResumeAllInterrupts();
void SuspendAllInterrupst();
void ResumeOSInterrupts();
void SuspendOSInterrupts();
unsigned char GetResource(ResourceType res);
unsigned char ReleaseResource(ResourceType res);
void ShutdownOS(StatusType status) __attribute__ ((noreturn));
StatusType SetEvent(TaskType id, EventMaskType mask);
StatusType ClearEvent(EventMaskType mask);
StatusType GetEvent(TaskType id, EventMaskType *mask);
StatusType WaitEvent(EventMaskType mask);
StatusType SetupIOISR(int fd, int isrlvl, unsigned char target);

