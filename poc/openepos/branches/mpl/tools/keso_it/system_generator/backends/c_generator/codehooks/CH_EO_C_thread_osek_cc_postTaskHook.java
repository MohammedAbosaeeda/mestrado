package josek;
import java.io.*;

public class CH_EO_C_thread_osek_cc_postTaskHook extends CodeHook_Standard {
	public void fireImmediately() {
		if(conf.hooks.postTask) writeLine("PostTaskHook();");
	}
}