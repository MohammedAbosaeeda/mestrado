package josek;
import java.io.*;

public class CodeHook_osc_counters extends CodeHook_Standard {
	public void fireImmediately() {
		if (conf.getCounterCount() == 0) return;
		
		SSingleton.clear();
		SSingleton.add("static CounterType CntrCurrentValue[] = { ");
		for (int i = 0; i < conf.getCounterCount(); i++) {
			SSingleton.add("0");
			if (i != conf.getCounterCount() - 1) SSingleton.add(", ");
		}
		SSingleton.add(" };");
		writeSingStr();

		SSingleton.clear();
		SSingleton.add("static CounterTickType CntrTickValue[] = { ");
		for (int i = 0; i < conf.getCounterCount(); i++) {
			SSingleton.add("0");
			if (i != conf.getCounterCount() - 1) SSingleton.add(", ");
		}
		SSingleton.add(" };");
		writeSingStr();
		
		if (conf.getAlarmCount() > 0) {
			SSingleton.clear();
			SSingleton.add("static AlarmTickType AlarmTicks[] = { ");
			for (int i = 0; i < conf.getAlarmCount(); i++) {
				SSingleton.add(conf.getAlarm(i).getAlarmTime());
				if (i != conf.getAlarmCount() - 1) SSingleton.add(", ");
			}
			SSingleton.add(" };");
			writeSingStr();
			
			SSingleton.clear();
			SSingleton.add("static AlarmTickType AlarmCycle[] = { ");
			for (int i = 0; i < conf.getAlarmCount(); i++) {
				SSingleton.add(conf.getAlarm(i).getCycleTime());
				if (i != conf.getAlarmCount() - 1) SSingleton.add(", ");
			}
			SSingleton.add(" };");
			writeSingStr();
		}
		
		writeLine("");
	}
}
