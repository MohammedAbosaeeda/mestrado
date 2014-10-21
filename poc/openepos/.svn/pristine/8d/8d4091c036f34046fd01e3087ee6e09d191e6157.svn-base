package josek;
import java.io.*;

public class CodeHook_osc_ioisr extends CodeHook_Standard {
	public void fireImmediately() {
		if (conf.getIOISRCount() == 0) return;
		
		SSingleton.clear();
		SSingleton.add("static unsigned char IO_ISR_Processed;");
		writeSingStr();
		
		SSingleton.clear();
		SSingleton.add("static struct pollfd IO_ISR_Table[");
		SSingleton.add(Integer.toString(conf.getIOISRCount()));
		SSingleton.add("];");
		writeSingStr();
	}
}
