/* Based on OSEK OS 2.2.3 Specification */
#ifndef EPOS2OSEK_H
#define EPOS2OSEK_H

#define TASK(name)                int func_##name(void)

#include <epos2osek_defs.h>

void print(char * message);

void StartOS(ApplicationModes appMode);

StatusType TerminateTask();

/* -> ActivateTask
 * Description: The task <TaskID> is transferred from the suspended state into
 * the ready state. The operating system ensures that the task code is being
 * executed from the first statement. */
StatusType ActivateTask(TaskType taskID);

/* -> ChainTask
 * Description: This service causes the termination of the calling task. After
 * termination of the calling task a succeeding task <TaskID> is activated.
 * Using this service, it ensures that the succeeding task starts to run at
 * the earliest after the calling task has been terminated. */
StatusType ChainTask(TaskType taskID);

#endif
