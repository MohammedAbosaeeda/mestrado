
#include "ptp.h"
#include <rtc.h>
#include <uart.h>
#include <utility/ostream.h>
#include <thread.h>
#include <alarm.h>

__USING_SYS;

OStream cout;
NIC * _nic;
PTP * _ptp;
UART uart(9600, 8 , 0 , 1);
unsigned long _timestamp;

void initProtocolParameters()
{
	// Iniciando e Configurando o PTP Clock
	_ptp->_ptp_parameters._original_clock_stratum = PTP::PTP_DataSet::STRATUM_SLAVE;
	_ptp->_ptp_parameters._clock_stratum = PTP::PTP_DataSet::STRATUM_SLAVE;
	_ptp->_ptp_parameters._state = PTP::INITIALIZING;
//	_ptp.ptp_id = 2;
}


int main(int argc, char **argv)
{

	_nic = new NIC();
	_ptp = new PTP();
	initProtocolParameters();
	_ptp->setNIC(_nic);
	_ptp->execute();

	while(true){
		Alarm::delay(2000000);
		kout << "Microseconds " << ARM7_TSC::getMicroseconds() << "\n";
		kout << "TimeStamp " << ARM7_TSC::getTimeStamp() + (ARM7_TSC::getMicroseconds()/1000000) << "\n";
	}
	return 1;
}
