package josek;
import java.io.*;

public class CodeHook_osc_schedule_posttaskhook extends CodeHook_Standard {
	public void fireImmediately() {
		if (conf.hooks.postTask) writeLine("\t\tif (ot != TID_IDLE_TASK) PostTaskHook();");
	}
}
