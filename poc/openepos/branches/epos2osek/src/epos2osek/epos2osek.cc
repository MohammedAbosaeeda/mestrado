#include <system/config.h>
#include <epos2osek/printer.hh>
#include <epos2osek.h>
#include <epos2osek/basic_task2thread.hh>

__USING_SYS

void print(char * message) {
    Printer::print(message);
}

void StartOS(ApplicationModes appMode) {
    BasicTask2Thread::startOS(appMode);
}

StatusType TerminateTask() {
    return BasicTask2Thread::terminate();
}

StatusType ActivateTask(TaskType taskID) {
    return BasicTask2Thread::activate(taskID);
}

StatusType ChainTask(TaskType taskID) {
    return BasicTask2Thread::chain(taskID);
}

