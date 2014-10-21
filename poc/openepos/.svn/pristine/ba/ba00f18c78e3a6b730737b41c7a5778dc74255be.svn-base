package josek;
import java.io.*;

public class CH_EO_C_os_cc_alarmsData extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;

		// Array with all alarms . E.g.:
		// Alarm* OSEK_Task::alarms[4] = {};
		int alarmCount = conf.getAlarmCount();
		writeLine("AlarmOSEK* OSEK_Task::alarms[" + alarmCount + "] = {};");

		// Array for saving the cycle time of non autostart alarms . E.g.:
		// Alarm* OSEK_Task::alarmsCycle[4] = {};					
		writeLine("TickType OSEK_Task::alarmCycle[" + alarmCount + "] = {};");

		// Alarm handler functions. E.g.:
		// extern void alarmHandlerFunc_myAlarm(void);
		for(int i=0; i<conf.getAlarmCount(); i++) { 
			HighlevelConfiguration_Alarm alm = conf.getAlarm(i);
			AlarmEvent almEv = alm.getEvent();
			if(almEv instanceof AlarmEvent_Callback) {
				AlarmEvent_Callback cb = (AlarmEvent_Callback) almEv;
				writeLine("extern \"C\" void alarmHandlerFunc_" + cb.getFuncName() + "(void);");
			} else writeLine("void alarmHandlerFunc_" + alm.getName() + "();");
		}
		
		// Alarm handler functions cyclic alarms. E.g.:
		// void alarm_myAlarm_cyclicHanding();
		for(int i=0; i<conf.getAlarmCount(); i++) { 
			HighlevelConfiguration_Alarm alm = conf.getAlarm(i);
			if(alm.getCycleTime() > 0)
				writeLine("void alarm_" + alm.getName() + "_cyclicHandling();");
		}
	}
}
