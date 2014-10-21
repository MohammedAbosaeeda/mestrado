package josek;
import java.io.*;

public class CH_EO_C_os_cc_tasks extends CodeHook_Standard {
	public void fireImmediately() {
		StringBuffer s;

		// Declaration and definition of task functions. E.g.:
		// extern int func_job1(void);
		// OSEK_Task task_job1 = OSEK_Task(func_job1, 0, 2, -5);
		// extern int func_job2(void);
		// OSEK_Task task_job2 = OSEK_Task(func_job2, 1, 2, -5);
		for(int i=0; i<conf.getTaskCount(); i++) {
			HighlevelConfiguration_Task task = conf.getTask(i);
			String taskName = task.getName();
			int taskPrio = conf.getTaskPriority(i);
			writeLine("extern \"C\" int func_" + taskName + "(void);");
			s = new StringBuffer("OSEK_Task task_" + taskName);
			s.append(" = OSEK_Task(func_" + taskName);
            // s.append(", " +  i + ", 2, -" + taskPrio + ");"); // original
            // s.append(", " +  i + ", Thread::SUSPENDED, " + "Thread::NORMAL" + ");"); // by mkl
            s.append(", " +  i + ", Thread::SUSPENDED, " + "Scheduling_Criteria::OSEK_Priority::HIGH" + ");"); // by mkl
			writeLine(new String(s));
		}
		writeLine("");

		// Definiton of the array of tasks. E.g.:
		// OSEK_Task* OSEK_Task::tasks[2] = {task_job1, task_job2};
		writeLine("OSEK_Task* OSEK_Task::tasks[" + conf.getTaskCount() + "] = {");
		for(int i=0; i<conf.getTaskCount(); i++) {
			HighlevelConfiguration_Task task = conf.getTask(i);
			String taskName = task.getName();
			String separator;
			if(i < conf.getTaskCount() - 1) separator = ",";
			else separator = "";
			writeLine("\t&task_" + taskName + separator);
		}
		writeLine("};\n");
	}
}
