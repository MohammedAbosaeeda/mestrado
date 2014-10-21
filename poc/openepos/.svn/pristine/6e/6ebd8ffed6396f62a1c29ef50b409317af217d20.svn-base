package josek;
import java.io.*;

public class CH_EO_C_osek_os_h_enums extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;
		
		// Naming of the osek task. E.g.
		// enum TASKS { job1, job2 };
		if(conf.getTaskCount()>0) {
			writeLine("enum TASKS {");
			for(int i=0; i<conf.getTaskCount(); i++) {
				HighlevelConfiguration_Task task = conf.getTask(i);
				String taskName = task.getName();
				String separator;
				if(i < conf.getTaskCount() - 1) separator = ",";
				else separator = "";
				writeLine("\t" + taskName + separator);
			}
			writeLine("};");
		}

		// Definition of resources. E.g.:
		// enum RESOURCES { RES_SCHEDULER, res1, res2 };
		if(conf.getResourceCount()>0) {
			writeLine("enum RESOURCES {");
			for(int i=0; i<conf.getResourceCount(); i++) { 
				HighlevelConfiguration_Resource res = conf.getResource(i);
				String resName = res.getName();
				String separator;
				if(i < conf.getResourceCount() - 1) separator = ",";
				else separator = "";
				writeLine("\t" + resName + separator);
			}
			writeLine("};\n");
		}

		// Events. E.g.:
		// enum EVENTS { myEvent1 = 1, myEvent2 = 2 };
		if(conf.getEventCount()>0) {
			writeLine("enum EVENTS {");
			for(int i=0; i<conf.getEventCount(); i++) {
				HighlevelConfiguration_Event event = conf.getEvent(i);
				String eventName = event.getName();
				String separator;
				if(i < conf.getEventCount() - 1) separator = ",";
				else separator = "";
				writeLine("\t" + eventName + " = " + event.getEventMask() + separator);
			}
			writeLine("};\n");
		}

		// Alarm names. E.g.:
		// enum ALARMS { myAlarm1, myAlarm2 };
		if(conf.getAlarmCount()>0) {
			writeLine("enum ALARMS {");
			for(int i=0; i<conf.getAlarmCount(); i++) {
				HighlevelConfiguration_Alarm alarm = conf.getAlarm(i);
				String alarmName = alarm.getName();
				String separator;
				if(i < conf.getAlarmCount() - 1) separator = ",";
				else separator = "";
				writeLine("\t" + alarmName + separator);
			}
			writeLine("};\n");
		}
	}
}
