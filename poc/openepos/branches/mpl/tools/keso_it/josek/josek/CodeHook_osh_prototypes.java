package josek;
import java.io.*;

public class CodeHook_osh_prototypes extends CodeHook_Standard {
	public void fireImmediately() {
		if (conf.hooks.startup) writeLine("void StartupHook();");
		if (conf.hooks.preTask) writeLine("void PreTaskHook();");
		if (conf.hooks.postTask) writeLine("void PostTaskHook();");
		if (conf.hooks.error) writeLine("void ErrorHook();");
		if (conf.hooks.shutdown) writeLine("void ShutdownHook();");
		if (conf.hooks.preISR) writeLine("void PreISRHook();");
		if (conf.hooks.postISR) writeLine("void PostISRHook();");

		for (int i = 0; i < conf.getAlarmCount(); i++) {
			if (conf.getAlarm(i).getEvent() instanceof AlarmEvent_Callback) {
				SSingleton.clear();
				SSingleton.add("void JOSEK_CALLBACK_");
				SSingleton.add(((AlarmEvent_Callback)(conf.getAlarm(i).getEvent())).getFuncName());
				SSingleton.add("();");
				writeSingStr();
			}
		}

		if (conf.haveIOISRs()) writeLine("void JOSEK_ISR_IO(int __signalname);");
		for (int i = 0; i < conf.getISRCount(); i++) {
			SSingleton.clear();
			SSingleton.add("void JOSEK_ISR_");
			SSingleton.add((conf.getISR(i).getName()));
			SSingleton.add("(int __signalname);");
			writeSingStr();
		}
	}
}
