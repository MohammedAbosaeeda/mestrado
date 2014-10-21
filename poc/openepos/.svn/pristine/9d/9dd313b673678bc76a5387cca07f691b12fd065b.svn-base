package josek;
import java.io.*;

public class CodeHook_osc_tasks extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;
		if (cmdLine.isDefined("stackprotector")) {
			writeLine("static unsigned char StackProtector0[PAGESIZE] __attribute__ ((aligned (PAGESIZE)));");
		}

		for (int i = 0; i < conf.getTaskCount(); i++) {	
			s = new StringBuffer("static unsigned char STACK_");
			s.append(conf.getTask(i).getName());
			s.append("[STACKSIZE_TASK_");
			s.append(conf.getTask(i).getName());
			s.append("];");
			writeLine(new String(s));
		
			if (cmdLine.isDefined("stackprotector")) {
				s = new StringBuffer("static unsigned char StackProtector");
				s.append(i + 1);
				s.append("[PAGESIZE];");
				writeLine(new String(s));
			}
		}		
		writeLine("");

		s = new StringBuffer("struct taskdesc_s taskdesc[");
		s.append((conf.getTaskCount() + 1));
		s.append("] = {");
		writeLine(new String(s));
		
		for (int i = 0; i < conf.getTaskCount(); i++) {
			s = new StringBuffer("	{ ");
			s.append("STACK_");
			s.append(conf.getTask(i).getName());
			s.append(",	STACK_");
			s.append(conf.getTask(i).getName());
			s.append(" + STACKSIZE_TASK_");
			s.append(conf.getTask(i).getName());
			s.append(" - CTXSIZE,	JOSEK_TASK_");
			s.append(conf.getTask(i).getName());
			s.append(",	");
			if (conf.getTask(i).getAutoactivation()) s.append("READY,		");
				else s.append("SUSPENDED,	");
			s.append(conf.getTaskPriority(i));		/* Default priority */
			s.append(", 0, 0, 0 },");
			writeLine(new String(s));
		}
		writeLine("	{ NULL,			NULL,										NULL,				READY,		0, 0, 0, 0 }");
		writeLine("};");

		s = new StringBuffer("static int Stacksizes[] = { ");
		for (int i = 0; i < conf.getTaskCount(); i++) {
			s.append("STACKSIZE_TASK_");
			s.append(conf.getTask(i).getName());
			if (i != conf.getTaskCount() - 1) s.append(", ");
		}
		s.append(" };");
		writeLine(new String(s));

		writeLine("");
	}
}
