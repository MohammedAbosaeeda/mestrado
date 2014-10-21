package josek;
import java.io.*;

public class CodeHook_osc_schedule_pretaskhook extends CodeHook_Standard {
	public void fireImmediately() {
		if (conf.hooks.preTask) writeLine("\t\tif (new_task != TID_IDLE_TASK) PreTaskHook();");
	}
}
