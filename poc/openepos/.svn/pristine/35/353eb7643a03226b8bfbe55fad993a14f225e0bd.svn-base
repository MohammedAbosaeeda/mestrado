package josek;
import java.io.*;

public class CodeHook_osc_timerhandler extends CodeHook_Standard {
	public void fireImmediately() {
		if (conf.getAlarmCount() == 0) return;
		writeLine("	if (signal == SIGALRM) {");
		writeLine("		unsigned char i;");
		writeLine("		settimer();");
		writeLine("		for (i = 0; i < NUMBER_COUNTERS; i++) {");
		writeLine("			CntrTickValue[i]++;");
		writeLine("			if (CntrTickValue[i] == CntrTickMaxValue(i)) {");
		writeLine("				CntrTickValue[i] = 0;");
		writeLine("				CntrCurrentValue[i]++;");
		writeLine("				if (CntrCurrentValue[i] == CntrMaxValue(i)) {");
		writeLine("					CntrCurrentValue[i] = 0;");
		writeLine("				}");
		if (conf.getAlarmCount() > 0) {
			writeLine("				switch (i) {");

			SSingleton.setIndent(5);

			for (int i = 0; i < conf.getCounterCount(); i++) {
				/* Are there alarms based on counter "i"? */
				if (conf.getCounter(i).getAlarmCount() > 0) {
					SSingleton.clear();
					SSingleton.add("case ");
					SSingleton.add(i);
					SSingleton.add(": {");
					writeSingStr();
				
					SSingleton.incIndent();
			
					/* Generate code for all alarms based on counter "i" */
					for (int j = 0; j < conf.getCounter(i).getAlarmCount(); j++) {
						SSingleton.clear();
						SSingleton.add("if ((AlarmTicks[");
						SSingleton.add(conf.getCounter(i).getAlarm(j).getName());
						SSingleton.add("] != 0) && ((--AlarmTicks[");
						SSingleton.add(conf.getCounter(i).getAlarm(j).getName());
						SSingleton.add("]) == 0)) {");
						writeSingStr();
						SSingleton.incIndent();
						
						conf.getCounter(i).getAlarm(j).getEvent().generateCode();
						writeSingStr();

						SSingleton.clear();
						SSingleton.add("AlarmTicks[");
						SSingleton.add(conf.getCounter(i).getAlarm(j).getName());
						SSingleton.add("] = AlarmCycle[");
						SSingleton.add(conf.getCounter(i).getAlarm(j).getName());
						SSingleton.add("];");
						writeSingStr();
						
						SSingleton.decIndent();
						SSingleton.clear();
						SSingleton.add("}");
						writeSingStr();
						
						if (j < conf.getCounter(i).getAlarmCount() - 1) writeLine("");
					}
					
					SSingleton.clear();
					SSingleton.add("break;");
					writeSingStr();
					SSingleton.decIndent();
					
					SSingleton.clear();
					SSingleton.add("}");
					writeSingStr();
				}
			}
			SSingleton.setIndent(0);
			writeLine("				}");
		}

		writeLine("			}");
		writeLine("		}");
		
		writeLine("	}");
		writeLine("");
	}
}
