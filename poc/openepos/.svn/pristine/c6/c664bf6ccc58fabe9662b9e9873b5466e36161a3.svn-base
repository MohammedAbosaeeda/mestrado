package josek;
import java.io.*;

public class CodeHook_osh_symbols extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;
		
		if (conf.getTaskCount() > 0) {
			s = new StringBuffer("enum TASKS { ");
			for (int i = 0; i < conf.getTaskCount(); i++) {
				s.append(conf.getTask(i).getName());
				if (i != conf.getTaskCount() - 1) s.append(", ");
			}
			s.append(" };");
			writeLine(new String(s));
		}
		
		if (conf.getResourceCount() > 0) {
			s = new StringBuffer("enum RESOURCES { ");
			for (int i = 0; i < conf.getResourceCount(); i++) {
				s.append(conf.getResource(i).getName());
				if (i != conf.getResourceCount() - 1) s.append(", ");
			}
			s.append(" };");
			writeLine(new String(s));
		}
		
		if (conf.getCounterCount() > 0) {
			s = new StringBuffer("enum COUNTERS { ");
			for (int i = 0; i < conf.getCounterCount(); i++) {
				s.append(conf.getCounter(i).getName());
				if (i != conf.getCounterCount() - 1) s.append(", ");
			}
			s.append(" };");
			writeLine(new String(s));
		}
		
		if (conf.getAlarmCount() > 0) {
			SSingleton.clear();
			SSingleton.add("enum ALARMS { ");
			for (int i = 0; i < conf.getAlarmCount(); i++) {
				SSingleton.add(conf.getAlarm(i).getName());
				if (i != conf.getAlarmCount() - 1) SSingleton.add(", ");
			}
			SSingleton.add(" };");
			writeSingStr();
		}

		if (conf.getISRCount() > 0) {
			SSingleton.clear();
			SSingleton.add("enum ISRNAMES { ");
			for (int i = 0; i < conf.getISRCount(); i++) {
				SSingleton.add(conf.ISRLevels.getLevel(i));
				if (i != conf.getISRCount() - 1) SSingleton.add(", ");
			}
			SSingleton.add(" };");
			writeSingStr();

		}
		
		writeLine("");
		
		if (conf.getTaskCount() > 0) {
			for (int i = 0; i < conf.getTaskCount(); i++) {
				s = new StringBuffer("#define STACKSIZE_TASK_");
				s.append(conf.getTask(i).getName());
				s.append(" 65536");
				writeLine(new String(s));

				s = new StringBuffer("extern void JOSEK_TASK_");
				s.append(conf.getTask(i).getName());
				s.append("();");
				writeLine(new String(s));
			}
			writeLine("");
		}

		if (conf.getEventCount() > 0) {
			for (int i = 0; i < conf.getEventCount(); i++) {
				s = new StringBuffer("#define ");
				s.append(conf.getEvent(i).getName());
				s.append(" ");
				s.append(conf.getEvent(i).getEventMask());
				writeLine(new String(s));
			}
			writeLine("");
		}
		
		if (conf.getIOISRCount() > 0) {
			s = new StringBuffer("#define NUMBER_IO_ISR ");
			s.append(Integer.toString(conf.getIOISRCount()));
			writeLine(new String(s));
		}
		
		writeLine("#define INVALID_TASK " + conf.getTaskCount());
		writeLine("#define NUMTASKS " + conf.getTaskCount());
		writeLine("#define TID_IDLE_TASK " + conf.getTaskCount());
		writeLine("#define MAX_TASK_ACTIVE INVALID_TASK");
		writeLine("#define NUMBER_PRIORITIES " + conf.priorities.size());
		writeLine("#define NUMBER_COUNTERS " + conf.getCounterCount());
		
		if (cmdLine.isDefined("stackprotector")) {
			writeLine("#define PAGESIZE 4096");
		}
		
		writeLine("");
	}
}
