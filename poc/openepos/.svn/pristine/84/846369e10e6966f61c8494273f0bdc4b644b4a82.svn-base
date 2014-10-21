// EPOS-- LEON2 RTC Mediator Declarations

#ifndef __leon2_rtc_h
#define __leon2_rtc_h

#include <rtc.h>

__BEGIN_SYS

class LEON2_RTC: public RTC_Common
{
private:
    static const unsigned int EPOCH_DAYS = Traits<LEON2_RTC>::EPOCH_DAYS;

public:
    LEON2_RTC() {}

    static Date date();
    static void date(const Date & d);

    static Second seconds_since_epoch() { 
	return date().to_offset(EPOCH_DAYS); 
    }

    static int init(System_Info * si) { return 0; }
};

__END_SYS

#endif
