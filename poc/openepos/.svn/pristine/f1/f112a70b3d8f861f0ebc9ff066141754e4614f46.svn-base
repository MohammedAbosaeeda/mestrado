// EPOS PC RTC Mediator Implementation

#include <rtc.h>

__BEGIN_SYS


MC13224V_RTC::Date MC13224V_RTC::date()
{
    unsigned int tmp = _s2;
    Date date(_Y2, _M2, _D2,
	      _h2, _m2, tmp);

    if(tmp != _s2)
	date = Date(_Y2, _M2, _D2,
		    _h2, _m2, _s2);

    date.adjust_year(1900);
    if(date.year() < EPOCH_YEAR)
	date.adjust_year(100);

    return date;
}

void MC13224V_RTC::date(const Date & d)
{
	_Y2 = d.year();
	_M2= d.month();
	_D2 = d.day();
	_h2 = d.hour();
	_m2 = d.minute();
	_s2 = d.second();

}

__END_SYS
