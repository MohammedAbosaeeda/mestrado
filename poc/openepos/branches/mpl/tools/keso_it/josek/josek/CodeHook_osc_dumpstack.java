package josek;

public class CodeHook_osc_dumpstack extends CodeHook_Standard {
	public void fireImmediately() {
		System.err.println("WARNING: Code disabled (TODO) - asm undeclared??");
		/*
		writeLine("void currentstack() {");
		writeLine("	void *sp;");
		writeLine("	asm(\"movl %%esp, %0\\n\" : \"=r\"(sp));");
		writeLine("	sp += 4;");
		writeLine("	fprintf(stderr, \"[DEBUG] Stackpointer: %p\\n\", sp);");
		
		StringBuffer s;
		for (int i = 0; i < conf.getTaskCount(); i++) {
			s = new StringBuffer("	if ((sp >= (void*)STACK_");
			s.append(conf.getTask(i).getName());
			s.append(") && (sp < (void*)(STACK_");
			s.append(conf.getTask(i).getName());
			s.append(" + STACKSIZE_TASK_");
			s.append(conf.getTask(i).getName());
			s.append("))) fprintf(stderr, \"In Task von ");
			s.append(conf.getTask(i).getName());
			s.append(" (%d Bytes)\\n\", sp - (void*)STACK_");
			s.append(conf.getTask(i).getName());
			s.append(");");
			writeLine(new String(s));
		}
		writeLine("}");
		writeLine("");
		*/
	}
}
