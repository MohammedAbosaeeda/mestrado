#ifndef __pc_traits_h
#define __pc_traits_h

__BEGIN_SYS

class PC_Common;
template <> struct Traits<PC_Common>: public Traits<void>
{
//     static const bool debugged = true;
};

template <> struct Traits<PC>: public Traits<PC_Common>
{
    static const unsigned int MAX_CPUS = 8;

    // Boot Image
    static const unsigned int BOOT_LENGTH_MIN   = 128;
    static const unsigned int BOOT_LENGTH_MAX   = 512;
    static const unsigned int BOOT_IMAGE_ADDR   = 0x00008000;

    // Physical Memory
    static const unsigned int MEM_BASE  = 0x00000000;
    static const unsigned int MEM_TOP   = 0x20000000; // 512 MB
 
    // Logical Memory Map
    static const unsigned int BOOT      = 0x00007c00;
    static const unsigned int SETUP     = 0x00100000; // 1 MB
    static const unsigned int INIT      = 0x00200000; // 2 MB
  
    static const unsigned int APP_LOW   = 0x00000000;
    static const unsigned int APP_CODE  = 0x00000000;
    static const unsigned int APP_DATA  = 0x00400000; // 4 MB
    static const unsigned int APP_HIGH  = 0x04000000; // 64 MB

    static const unsigned int PHY_MEM   = 0x80000000; // 2 GB
    static const unsigned int IO_BASE   = 0xf0000000; // 4 GB - 256 MB
    static const unsigned int IO_TOP    = 0xff400000; // 4 GB - 12 MB
    
    static const unsigned int SYS       = IO_TOP; // 4 GB - 12 MB
    static const unsigned int SYS_CODE  = 0xff700000;
    static const unsigned int SYS_DATA  = 0xff740000;
    
    static const unsigned int IDLE_STACK_SIZE = 128 * 1024;
    //static const unsigned int APPLICATION_STACK_SIZE = 1024 * 128; //1024 * 1024 * 15;
    static const unsigned int APPLICATION_STACK_SIZE = 1024 * 128;
    //static const unsigned int APPLICATION_HEAP_SIZE = 32 * 1024 * 1024; 
    static const unsigned int APPLICATION_HEAP_SIZE = 19 * 1024 * 1024;
    //static const unsigned int APPLICATION_HEAP_SIZE = 32 * 1024 * 1024;
    
    static const unsigned int SYSTEM_STACK_SIZE = 4096;
    //static const unsigned int SYSTEM_HEAP_SIZE = 32 * 1024 * 1024;//128 * APPLICATION_STACK_SIZE;
    static const unsigned int SYSTEM_HEAP_SIZE = 12 * 1024 * 1024;//128 * APPLICATION_STACK_SIZE;
    //static const unsigned int SYSTEM_HEAP_SIZE = 32 * 1024 * 1024;

    static const unsigned int PRIORITY_HEAP_BASE_ADDR = 0x00400000 + SYSTEM_HEAP_SIZE + 1048576; //TODO: This addrees is wrong --- APP_DATA + SYSTEM_HEAP_SIZE + 1MB
    static const unsigned int PRIORITY_HEAP_SIZE = 1024 * 1024; //1MB
    static const unsigned int PRIORITY_HEAP_TOP_ADDR = PRIORITY_HEAP_BASE_ADDR + PRIORITY_HEAP_SIZE - 1;
};

template <> struct Traits<PC_PCI>: public Traits<PC_Common>
{
    static const bool enabled = false;
    static const int MAX_BUS = 0;
    static const int MAX_DEV_FN = 0xff;
};

template <> struct Traits<PC_IC>: public Traits<PC_Common>
{
};

template <> struct Traits<PC_Timer>: public Traits<PC_Common>
{
    // Meaningful values for the PC's timer frequency range from 100 to
    // 10000 Hz. The choice must respect the scheduler time-slice, i. e.,
    // it must be higher than the scheduler invocation frequency.
    static const int FREQUENCY = 1000; // Hz
};

template <> struct Traits<PC_RTC>: public Traits<PC_Common>
{
    static const unsigned int EPOCH_DAY = 1;
    static const unsigned int EPOCH_MONTH = 1;
    static const unsigned int EPOCH_YEAR = 1970;
    static const unsigned int EPOCH_DAYS = 719499;
};

template <> struct Traits<PC_EEPROM>: public Traits<PC_Common>
{
};

template <> struct Traits<PC_UART>: public Traits<PC_Common>
{
    static const unsigned int CLOCK = 1843200; // 1.8432 MHz
    static const unsigned int COM1 = 0x3f8; // to 0x3ff, IRQ4
    static const unsigned int COM2 = 0x2f8; // to 0x2ff, IRQ3
    static const unsigned int COM3 = 0x3e8; // to 0x3ef, no IRQ
    static const unsigned int COM4 = 0x2e8; // to 0x2ef, no IRQ
};

template <> struct Traits<PC_Display>: public Traits<PC_Common>
{
    static const int COLUMNS = 80;
    static const int LINES = 25;
    static const int TAB_SIZE = 8;
    static const unsigned int FRAME_BUFFER_ADDRESS = 0xb8000;
};

template <> struct Traits<PC_Ethernet>: public Traits<PC_Common>
{
    static const bool enabled = false;
    typedef LIST<PCNet32> NICS;
};

template <> struct Traits<PCNet32>: public Traits<PC_Ethernet>
{
    static const unsigned int UNITS = NICS::Count<PCNet32>::Result;
    static const unsigned int SEND_BUFFERS = 8; // per unit
    static const unsigned int RECEIVE_BUFFERS = 8; // per unit
};

template <> struct Traits<E100>: public Traits<PC_Ethernet>
{
    static const unsigned int UNITS = NICS::Count<E100>::Result;
    static const unsigned int SEND_BUFFERS = 8; // per unit
    static const unsigned int RECEIVE_BUFFERS = 8; // per unit
};

template <> struct Traits<C905>: public Traits<PC_Ethernet>
{
    static const unsigned int UNITS = NICS::Count<C905>::Result;
    static const unsigned int SEND_BUFFERS = 8; // per unit
    static const unsigned int RECEIVE_BUFFERS = 8; // per unit
};

__END_SYS

#endif