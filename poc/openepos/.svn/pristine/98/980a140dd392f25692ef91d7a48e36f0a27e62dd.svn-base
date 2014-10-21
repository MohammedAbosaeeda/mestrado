#include <alarm_osek.h>

__BEGIN_SYS

void AlarmOSEK::osek_getTicks(TickRefType tick) {
    *tick = _requests.head()->rank();
}

void AlarmOSEK::osek_cancel() {
	_requests.remove(this);
} 


__END_SYS
