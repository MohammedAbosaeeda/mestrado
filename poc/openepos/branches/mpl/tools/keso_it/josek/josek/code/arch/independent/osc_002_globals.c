unsigned char current_task = TID_IDLE_TASK;
static char current_priority = -1;
static MACHINEWORD taskprio_runnable[(NUMBER_PRIORITIES / 8 / sizeof(MACHINEWORD)) + 1];

//? assertions
static int reslocks = 0x00;

//?
static struct bbqueue queues[NUMBER_PRIORITIES];

static unsigned char initialization = 1;

//? signals
static sigset_t oldmask;
static unsigned char intlevel = 0;

//?
