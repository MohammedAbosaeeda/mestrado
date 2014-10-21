package josek;
import java.io.*;

public class CH_EO_C_osek_os_h_taskDecs extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;

 		for(int i=0; i<conf.getTaskCount(); i++) {
 			HighlevelConfiguration_Task task = conf.getTask(i);
 			String taskName = task.getName();
 			writeLine("extern __SYS(OSEK_Task) task_" + taskName + ";");
 		}
	}
}
