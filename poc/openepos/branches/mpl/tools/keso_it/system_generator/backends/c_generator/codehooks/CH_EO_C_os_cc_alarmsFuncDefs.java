package josek;
import java.io.*;

public class CH_EO_C_os_cc_alarmsFuncDefs extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;

		// definition of alarm handler functions for initialization
		// of cyclic alarms. E.g.:
		// "void alarm_myAlarm_cyclicHandling() {
		//     Handler_Function* h = new Handler_Function(&alarmHandlerFunc_myAlarm);
		//     alarmHandlerFunc_myAlarm();
		//     new Alarm(OSEK::alarmCycle[myAlarm], h, -1);
		// }
		for(int i=0; i<conf.getAlarmCount(); i++) {
			HighlevelConfiguration_Alarm alarm = conf.getAlarm(i);
			if(alarm.getCycleTime() == 0) continue;
			String alarmName = alarm.getName();
			AlarmEvent alEvent = alarm.getEvent();
			
			// determine name of handler function
			String funcName;
			if(alEvent instanceof AlarmEvent_Callback) 
				funcName = "alarmHandlerFunc_" + ((AlarmEvent_Callback) alEvent).getFuncName();
			else funcName = "alarmHandlerFunc_" + alarmName;

			// determine string for cycle time
			String cycleTimeString;
			if(alarm.getAutostart()) {
				int timeInNanoSeconds = 1;
				String baseCounterName = alarm.getBaseCounterName();
				for(int k=0; k<conf.getCounterCount(); k++) {
					HighlevelConfiguration_Counter counter = conf.getCounter(k);
					if(counter.getName().equals(baseCounterName)) 
						timeInNanoSeconds = counter.getTimeInNanoSeconds();
				}
				cycleTimeString = (new Long(alarm.getCycleTime() * timeInNanoSeconds / 1000)).toString();
			} else cycleTimeString = "OSEK_Task::alarmCycle[" + alarmName + "]";

			writeLine("void alarm_" + alarmName + "_cyclicHandling() {");
			writeLine("\tFunction_Handler* h = new Function_Handler(&" + funcName + ");");
			writeLine("\t" + funcName + "();");
			writeLine("\tnew Alarm(" + cycleTimeString + ", h, -1);");
			writeLine("}");
		}
		writeLine("\n");


		// definition of alarm handler functions for setting Events and
		// activating Tasks. E.g.:
		// void alarmHandlerFunc_myAlarm1() {
		//     task_job2.activate(true);
		// }
		//
		// or
		//
		// void alarmHandlerFunc_myAlarm2() {
		//     OSEK_Task::setEvent(job3, event1);
		// }
		for(int i=0; i<conf.getAlarmCount(); i++) {
			HighlevelConfiguration_Alarm alarm = conf.getAlarm(i);
			String alarmName = alarm.getName();
			AlarmEvent alEvent = alarm.getEvent();
			if(alEvent instanceof AlarmEvent_Callback) continue;
			writeLine("void alarmHandlerFunc_" + alarmName + "() {");
			if(alEvent instanceof AlarmEvent_ActivateTask)
				writeLine("\ttask_" + ((AlarmEvent_ActivateTask) alEvent).getTaskName() + 
				          ".activate(true);");
			else if(alEvent instanceof AlarmEvent_SetEvent)
				writeLine("\tOSEK_Task::setEvent(" + 
				          ((AlarmEvent_SetEvent) alEvent).getTaskName() + ", " + 
				          ((AlarmEvent_SetEvent) alEvent).getEventName() + ");");
			writeLine("}\n");
		}


		// The function setRelAlarm. E.g.:
		//
		// StatusType OSEK_Task::setRelAlarm(AlarmType alarmId,
        //                                   TickType increment,
        //                                   TickType cycle) {

    	//    alarmCycle[alarmId] = cycle * 1000;
    	//    switch(alarmId) {
        //        case myAlarm5:
        //            Handler_Function* h = 
		//                new Handler_Function(&cyclic_alarm_init_myAlarm);
		//            new Alarm((increment-cycle) * 1000, h, 1);
		//            break;
    	//    }
		// }
		writeLine("StatusType OSEK_Task::setRelAlarm(AlarmType alarmId,");
		writeLine("                                  TickType increment,");
		writeLine("                                  TickType cycle) {");
		writeLine("");
		writeLine("\tFunction_Handler* h;");
		writeLine("\tswitch(alarmId) {");
		for(int i=0; i<conf.getAlarmCount(); i++) {
			HighlevelConfiguration_Alarm alarm = conf.getAlarm(i);
			if(!alarm.getAutostart()) {
				int timeInNanoSeconds = 1;
				String baseCounterName = alarm.getBaseCounterName();
				for(int k=0; k<conf.getCounterCount(); k++) {
					HighlevelConfiguration_Counter counter = conf.getCounter(k);
					if(counter.getName().equals(baseCounterName)) 
						timeInNanoSeconds = counter.getTimeInNanoSeconds();
				}
				String alarmName = alarm.getName();
				AlarmEvent alEvent = alarm.getEvent();
				String funcSuffix;
				if(alEvent instanceof AlarmEvent_Callback) {
					AlarmEvent_Callback cbEvent = (AlarmEvent_Callback) alEvent;
					funcSuffix = cbEvent.getFuncName();
				} else funcSuffix = alarm.getName();
				writeLine("\t  case " + alarmName + ":");
				writeLine("\t\talarmCycle[alarmId] = cycle * " + timeInNanoSeconds / 1000 + ";");
				writeLine("\t\tif(cycle==0) h = new Function_Handler(&alarmHandlerFunc_" + funcSuffix + ");");
				writeLine("\t\telse h = new Function_Handler(&alarm_" + alarmName + "_cyclicHandling);");
				writeLine("\t\tnew Alarm(increment * " + timeInNanoSeconds / 1000 + ", h, 1);");
				writeLine("\t\tbreak;");
			}
		}
		writeLine("\t}");

		writeLine("}");
		
	}
}
