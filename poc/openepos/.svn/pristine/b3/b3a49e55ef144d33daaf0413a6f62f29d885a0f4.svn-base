package josek;
import java.io.*;

public class CodeHook_osc_main_preschedule extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;
		if (cmdLine.isDefined("timer")) writeLine("	settimer();");
		if (conf.hooks.startup) writeLine("	StartupHook();");
		
		for (int i = 0; i < conf.getTaskCount(); i++) {
			if (conf.getTask(i).getAutoactivation()) {
				s = new StringBuffer("	activatetask(");
				s.append(conf.getTask(i).getName());
				s.append(");");
				writeLine(new String(s));
			}
		}
	}

}
