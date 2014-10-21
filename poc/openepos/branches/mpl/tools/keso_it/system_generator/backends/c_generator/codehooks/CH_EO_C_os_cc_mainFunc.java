package josek;
import java.io.*;

public class CH_EO_C_os_cc_mainFunc extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;

		// interior of main function. E.g.:
		//     StartOS(OSDEFAULTAPPMODE);
		//     Thread * m = Thread::self();
		//     db<Thread>(WRN) << "Thread main: " << m << ")\n";
		//     db<Thread>(WRN) << "Thread job1: " << &task_job1 << ")\n";
		//     db<Thread>(WRN) << "Thread job2: " << &task_job2 << ")\n";
		//     task_job1.activate(true);
		//     while(true);

		writeLine("\tOSEK_Task::startOS(OSDEFAULTAPPMODE);");
		if(cmdLine.isDefined("debug")) {
			writeLine("\tThread * m = Thread::self();");
			writeLine("\tdb<Thread>(WRN) << \"Thread main: \" << m << \")\\\n\";");
			for(int i=0; i<conf.getTaskCount(); i++) {
				String taskName = conf.getTask(i).getName();
				writeLine("\tdb<Thread>(WRN) << \"" + taskName +": \" << &task_" + taskName + " << \")\\\n\";");
			}
		}

		// Starting autostart alarms. E.g.:
		// Handler_Function handler1(&alarm_myAlarm_cyclicHandling);
		// new Alarm(10, &handler1, 1);
		for(int i=0; i<conf.getAlarmCount(); i++) {
			HighlevelConfiguration_Alarm alm = conf.getAlarm(i);
			if(alm.getAutostart()) {
				int alarmTime = alm.getAlarmTime();
				int cycleTime = alm.getCycleTime();
				String funcName = "";
				AlarmEvent almEv = alm.getEvent();
				if(almEv instanceof AlarmEvent_Callback) {
					AlarmEvent_Callback ev = (AlarmEvent_Callback) almEv;
					if(cycleTime == 0) funcName="&alarmHandlerFunc_" + ev.getFuncName();
					else funcName="&alarm_" + alm.getName() + "_cyclicHandling";
				} else {
					if(cycleTime == 0) funcName="&alarmHandlerFunc_" + alm.getName();
					else funcName="&alarm_" + alm.getName() + "_cyclicHandling";
				}
				int timeInNanoSeconds = 1;
				String baseCounterName = alm.getBaseCounterName();
				for(int k=0; k<conf.getCounterCount(); k++) {
					HighlevelConfiguration_Counter counter = conf.getCounter(k);
					if(counter.getName().equals(baseCounterName)) 
						timeInNanoSeconds = counter.getTimeInNanoSeconds();
				}
				alarmTime *= timeInNanoSeconds / 1000;
				writeLine("\tHandler_Function handler" + i + "(" + funcName + ");");
				writeLine("\tOSEK_Task::alarms[" + i + "] = new Alarm(" + alarmTime + ", &handler" + i + ", 1);");
			}
		}

		for(int i=0; i<conf.getTaskCount(); i++) {
			HighlevelConfiguration_Task task = conf.getTask(i);
			if(task.getAutoactivation()) {
				String taskName = task.getName();
				writeLine("\ttask_" + taskName + ".activate(false);");
			}
		}
		writeLine("\tOSEK_Task::osek_reschedule(true);");
		writeLine("\twhile(true);");
	}
}
