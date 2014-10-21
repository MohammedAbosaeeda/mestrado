// EPOS-- SoftMIPS RTC Mediator Declarations

#ifndef __softmips_rtc_h
#define __softmips_rtc_h

#include <rtc.h>

__BEGIN_SYS

class SoftMIPS_RTC: public RTC_Common
{
private:
    static const unsigned int EPOCH_DAYS = Traits<SoftMIPS_RTC>::EPOCH_DAYS;

public:
    SoftMIPS_RTC() {}

    static Date date();
    static void date(const Date & d);

    static Second seconds_since_epoch() { 
	return date().to_offset(EPOCH_DAYS); 
    }

    static int init(System_Info * si) { return 0; }
};

__END_SYS

#endif
