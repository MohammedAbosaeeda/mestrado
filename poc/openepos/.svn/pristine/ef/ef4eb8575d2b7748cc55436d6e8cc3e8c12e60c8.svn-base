package josek;
import java.io.*;

public class CH_EO_C_osek_os_h_hookPrototypes extends CodeHook_Standard {
	public void fireImmediately() {
		if(conf.hooks.startup) writeLine("extern \"C\" void StartupHook();");
		if(conf.hooks.shutdown) writeLine("extern \"C\" void ShutdownHook();");
		if(conf.hooks.preTask) writeLine("extern \"C\" void PreTaskHook();");
		if(conf.hooks.postTask) writeLine("extern \"C\" void PostTaskHook();");
	}
}
