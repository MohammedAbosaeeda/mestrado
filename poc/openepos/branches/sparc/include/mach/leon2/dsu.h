// EPOS-- LEON2 Debug Support Unit Mediator Implementation

#ifndef __leon2_dsu_h
#define __leon2_dsu_h

__BEGIN_SYS

class LEON2_DSU
{
private:
    enum { DSUCTRL = 0x90000000 };

    enum { 
	   DEBUGON  = 0x000075f8, 
	   DEBUGOFF = 0x00006080
	 };

public:
    LEON2_DSU() {}

    static void enter_debug_mode() {
	*(reinterpret_cast<volatile int *>(DSUCTRL)) |= DEBUGON;	
	ASMV ("nop; nop; nop; nop; nop; nop;");
    }

    static void leave_debug_mode() {
        *(reinterpret_cast<volatile int *>(DSUCTRL)) &= DEBUGOFF;
        ASMV ("nop; nop; nop; nop; nop; nop;");
    }

    static int init(System_Info *si) { return 0; }
};

__END_SYS

#endif
