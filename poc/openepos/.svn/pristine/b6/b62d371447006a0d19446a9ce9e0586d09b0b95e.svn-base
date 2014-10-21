package josek;
import java.io.*;

// HOOK zur Erzeugung von os.cc
public class CH_EO_C_os_cc extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;

		// includes
		// f.e.: #inlcude <osek_os.h>
		writeLine("#include <osek_os.h>");
		writeLine("#include <osek_task.h>");
		if(conf.getAlarmCount() > 0) {
			writeLine("#include <alarm.h>");
			writeLine("#include <clock.h>");
		}
		writeLine("");

		// Declaration and definition of task functions. E.g.:
		// extern int func_job1(void);
		// OSEK_Task task_job1 = OSEK_Task(func_job1, 0, 2, -5);
		// extern int func_job2(void);
		// OSEK_Task task_job2 = OSEK_Task(func_job2, 1, 2, -5);
		for(int i=0; i<conf.getTaskCount(); i++) {
			HighlevelConfiguration_Task task = conf.getTask(i);
			String taskName = task.getName();
			int taskPrio = task.getPriority();
			writeLine("extern int func_" + taskName + "(void);");
			s = new StringBuffer("OSEK_Task task_" + taskName);
			s.append(" = OSEK_Task(func_" + taskName);
			s.append(", " +  i + ", 2, " + taskPrio + ");");
			writeLine(new String(s));
		}
		writeLine("");

		// Definiton of the array of tasks. E.g.:
		// OSEK_Task* OSEK_Task::tasks[2] = {task_job1, task_job2};
		writeLine("OSEK_Task* OSEK_Task::tasks[" + conf.getTaskCount() + "] = {");
		for(int i=0; i<conf.getTaskCount(); i++) {
			HighlevelConfiguration_Task task = conf.getTask(i);
			String taskName = task.getName();
			String separator;
			if(i < conf.getTaskCount() - 1) separator = ",";
			else separator = "";
			writeLine("\ttask_" + taskName + separator);
		}
		writeLine("");
		
		// Naming of the osek task. E.g.
		// enum TaskType { job1, job2 }
		writeLine("enum TaskType {");
		for(int i=0; i<conf.getTaskCount(); i++) {
			HighlevelConfiguration_Task task = conf.getTask(i);
			String taskName = task.getName();
			String separator;
			if(i < conf.getTaskCount() - 1) separator = ",";
			else separator = "";
			writeLine("\t" + taskName + separator);
		}
		writeLine("}\n");

		// Alarm names. E.g.:
		// enum AlarmType { myAlarm1, myAlarm2 };
		writeLine("enum AlarmType {");
		for(int i=0; i<conf.getAlarmCount(); i++) {
			HighlevelConfiguration_Alarm alarm = conf.getAlarm(i);
			String alarmName = alarm.getName();
			String separator;
			if(i < conf.getAlarmCount() - 1) separator = ",";
			else separator = "";
			writeLine("\t" + alarmName + separator);
		}
		writeLine("}\n");


		// Alarm callback functions. E.g.:
		// extern void alarmCB_myAlarm(void);
		for(int i=0; i<conf.getAlarmCount(); i++) { 
			HighlevelConfiguration_Alarm alm = conf.getAlarm(i);
			AlarmEvent almEv = alm.getEvent();
			if(almEv instanceof AlarmEvent_Callback) {
				AlarmEvent_Callback cb = (AlarmEvent_Callback) almEv;
				writeLine("extern void alarmCB_" + cb.getFuncName() + "(void);");
			}
		}
		writeLine("");
		
		// Function StartupHook()
		if(conf.hooks.startup) writeLine("extern void StartupHook()");
		writeLine("");

		// Alarm handler functions zyclic alarms. E.g.:
		// void alarm_myAlarm_cyclicHanding();
		for(int i=0; i<conf.getAlarmCount(); i++) { 
			HighlevelConfiguration_Alarm alm = conf.getAlarm(i);
			if(alm.getCycleTime() > 0) 
				writeLine("void alarm_" + alm.getName() + "_cyclicHandling();");
		}
		writeLine("");


		// Definition of resources. E.g.:
		// enum ResourceType { RES_SCHEDULER, res1, res2 };
		writeLine("enum ResourceType {");
		for(int i=0; i<conf.getResourceCount(); i++) { 
			HighlevelConfiguration_Resource res = conf.getResource(i);
			String resName = res.getName();
			String separator;
			if(i < conf.getResourceCount() - 1) separator = ",";
			else separator = "";
			writeLine("\t" + resName + separator);
		}
		writeLine("}\n");

		// Events. E.g.:
		// enum EventType { dummy = 0, myEvent1 = 1, myEvent2 = 2 };
		writeLine("enum EventType {");
		for(int i=0; i<conf.getEventCount(); i++) {
			HighlevelConfiguration_Event event = conf.getEvent(i);
			String eventName = event.getName();
			String separator;
			if(i < conf.getEventCount() - 1) separator = ",";
			else separator = "";
			writeLine("\t" + eventName + separator);
		}
		writeLine("}\n");

		// Names of resources. E.g.:


		// main Function. E.g.:
		// int main() {
		//     StartOS(OSDEFAULTAPPMODE);
		//     Thread * m = Thread::self();
		//     db<Thread>(WRN) << "Thread main: " << m << ")\n";
		//     db<Thread>(WRN) << "Thread job1: " << &task_job1 << ")\n";
		//     db<Thread>(WRN) << "Thread job2: " << &task_job2 << ")\n";
		//     task_job1.activate(true);
		//     while(true);
		// }
		writeLine("int main() {");
		writeLine("\tStartOS(OSDEFAULTAPPMODE);");
		if(cmdLine.isDefined("debug")) {
			writeLine("\tThread * m = Thread::self();");
			writeLine("\tdb<Thread>(WRN) << \"Thread main: \" << m << \")\\\n\";");
			for(int i=0; i<conf.getEventCount(); i++) {
				String eventName = conf.getEvent(i).getName();
				writeLine("\tdb<Thread>(WRN) << \"" + eventName + ": \" << &task_job1 << \")\\\n\";");
			}
		}
		
		for(int i=0; i<conf.getEventCount(); i++) {
			String eventName = conf.getEvent(i).getName();
			writeLine("\ttask_" + eventName + ".activate(true);");
		}
		writeLine("\twhile(true);");
		writeLine("}");
	}
}
