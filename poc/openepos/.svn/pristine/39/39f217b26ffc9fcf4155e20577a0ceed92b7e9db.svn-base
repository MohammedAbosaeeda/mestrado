package josek;
import java.io.*;

public class CodeHook_osc_main_signalhandler extends CodeHook_Standard {
	private void installHandler(String signalName, String signalHandler) {
		SSingleton.incIndent();
			SSingleton.clear();
			SSingleton.incIndent();
			SSingleton.add("{ /* Signal handler for signal ");
			SSingleton.add(signalName);
			SSingleton.add(" */");
			SSingleton.newLine();
				SSingleton.addNL("struct sigaction A;");
				SSingleton.addNL("A.sa_handler = " + signalHandler + ";");
				SSingleton.addNL("sigemptyset(&A.sa_mask);");
				SSingleton.addNL("A.sa_flags = SA_RESTART;");
				SSingleton.incIndent();
				SSingleton.addNL("if ((sigaction(" + signalName + ", &A, NULL) == -1)) {");
					SSingleton.addNL("perror(\"sigaction\");");
					SSingleton.decIndent();
					SSingleton.addNL("exit(1);");
				SSingleton.decIndent();
				SSingleton.addNL("}");
			SSingleton.decIndent();
			SSingleton.addNL("}");
		writeSingStr();
	}

	public void fireImmediately() {
		if (conf.getAlarmCount() > 0) installHandler("SIGALRM", "alarm_handler");
		if (conf.haveIOISRs()) {
			installHandler("SIGIO", "JOSEK_ISR_IO");
		
			SSingleton.incIndent();
			SSingleton.clear();
			SSingleton.incIndent();
			SSingleton.addNL("{");
			SSingleton.addNL("/* All IO ISR registered, start helper thread */");
			SSingleton.addNL("pthread_t tid;");
			SSingleton.decIndent();
			SSingleton.addNL("pthread_create(&tid, NULL, IO_ISR_Helper_Thread, NULL);");
			SSingleton.addNL("}");
			writeSingStr();
		}

		if (conf.getISRCount() > 0) {
			int i;
			for (i = 0; i < conf.getISRCount(); i++) {
				int sigNr = conf.getISR(i).getSignal();
				if (sigNr != 0) {
					installHandler(Integer.toString(sigNr), "JOSEK_ISR_" + conf.getISR(i).getName());
				}
			}
		}
	}
}
