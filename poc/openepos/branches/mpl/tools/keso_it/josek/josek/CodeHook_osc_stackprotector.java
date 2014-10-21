package josek;
import java.util.Random;

public class CodeHook_osc_stackprotector extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;
		if (cmdLine.isDefined("stackprotector_simple")) {
			Random r = new Random();
			writeLine("void rand_stackprotector() {");
			writeLine("	srand(" + r.nextInt() + ");");
			writeLine("}");
			writeLine("");
			writeLine("void init_stackprotector() {");
			writeLine("	int i;");
			writeLine("	rand_stackprotector();");
			writeLine("	for (i = 0; i < 4096; i++) StackProtector0[i] = (rand() & 0xff);");
			for (int i = 1; i <= conf.getTaskCount(); i++) {
				s = new StringBuffer("	for (i = 0; i < 4096; i++) StackProtector");
				s.append(i);
				s.append("[i] = (rand() & 0xff);");
				writeLine(new String(s));
			}
			writeLine("}");
			writeLine("");
			writeLine("void check_stackprotector() {");
			writeLine("	int i;");
			writeLine("	rand_stackprotector();");
			for (int i = 0; i <= conf.getTaskCount(); i++) {
				s = new StringBuffer("	for (i = 0; i < 4096; i++) if (StackProtector");
				s.append(i);
				s.append("[i] != (rand() & 0xff)) {");
				writeLine(new String(s));
				
				s = new StringBuffer("		fprintf(stderr, \"Error: stack protector ");
				s.append(i);
				s.append(" error at position 0x%x (%d): found 0x%x (%d)\\n\", i, i, StackProtector");
				s.append(i);
				s.append("[i], StackProtector");
				s.append(i);
				s.append("[i]);");
				writeLine(new String(s));
				
				writeLine("		abort();");
				writeLine("	}");
			}
			writeLine("}");
			writeLine("");
		} else if (cmdLine.isDefined("stackprotector")) {
			writeLine("void init_stackprotector() {");
			for (int i = 0; i <= conf.getTaskCount(); i++) {
				s = new StringBuffer("	if (mprotect(StackProtector");
				s.append(i);
				s.append(", PAGESIZE, PROT_NONE)) perror(\"StackProtector");
				s.append(i);
				s.append(" is unable to mprotect\");");
				writeLine(new String(s));
			}
			writeLine("}");
			writeLine("");
	
		}
	}
}
