package josek;
import java.io.*;

public class CH_EO_C_os_cc_includes extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;

		// includes
		// f.e.: #inlcude <osek_os.h>
		// writeLine("#include <osek_os.h>"); // by mkl
		writeLine("#include <osek_task.h>");
        writeLine("#include <thread.h>"); // by mkl
		if(conf.getAlarmCount() > 0) {
			writeLine("#include <alarm.h>");
			writeLine("#include <clock.h>");
		}
	}
}
