package josek;
import java.io.*;

public class CH_EO_C_os_cc_resources extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;
	
		// Array for definition of ceiling priorities and
		// for deposition of task priorities. E.g.:
		// PriorityType OSEK_Task::resourcePriority[][2] = {
    	//     -999, 0,
    	//     -12, 0,
    	//     -12, 0
		// };
		writeLine("PriorityType OSEK_Task::resourcePriority[][2] = {");
		for(int i=0; i<conf.getResourceCount(); i++) { 
			HighlevelConfiguration_Resource res = conf.getResource(i);
			String separator;
			if(i < conf.getResourceCount() - 1) separator = ",";
			else separator = "";
			
			writeLine("\t-" + res.getCeilingPriority() + ", 0" + separator);
		}
		writeLine("};\n");
	}
}
