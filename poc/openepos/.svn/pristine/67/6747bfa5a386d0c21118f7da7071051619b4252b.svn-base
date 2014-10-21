StatusType GetAlarmBase(enum ALARMS aid, AlarmBaseType *info) {
	return E_OK;
}

StatusType GetAlarm(enum ALARMS aid, CounterTickType *tick) {
	return E_OK;
}

StatusType SetRelAlarm(enum ALARMS aid, CounterTickType increment, CounterTickType cycle) {
	
	return E_OK;
}

StatusType SetAbsAlarm(enum ALARMS aid, CounterTickType start, CounterTickType cycle) {
	if (AlarmTicks[aid] != 0) return E_OS_STATE;							/* Alarm already in use */
	if (aid > sizeof(enum ALARMS)) return E_OS_ID;							/* EO: Alarm ID invalid */
	/* TODO: Not CntrMaxValue(aid), but CntrMaxValue(BasedCounter(aid))! */
#if 0
	if ((start < 0) || (start > CntrMaxValue(aid))) return E_OS_VALUE;		/* EO */
	if ((cycle != 0) 
		&& ((cycle < MinCycle(aid)) || (cycle > MaxAllowedValue(aid)))) return E_OS_VALUE;	/* EO */
#endif

	return E_OK;
}

StatusType CancelAlarm(enum ALARMS aid) {
	if (AlarmTicks[aid] == 0) return E_OS_NOFUNC;	/* Alarm already stopped */
	if (aid > sizeof(enum ALARMS)) return E_OS_ID;	/* EO: Alarm ID invalid */
	return E_OK;
}

