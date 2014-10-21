package josek;
import java.io.*;

public class CodeHook_osc_settimer extends CodeHook_Standard {
	public void fireImmediately() {
		int nanoSeconds = conf.getTimerInterval();
		int seconds = nanoSeconds / 1000000000;
		int uSeconds = (nanoSeconds % 1000000000) / 1000;
	
		if (conf.getCounterCount() == 0) return;
		
		if ((seconds == 0) && (uSeconds < 10000)) {
			System.err.println("[josek] WARNING: Timer resolution critically low (" + uSeconds + " us)");
		}
		
		writeLine("void settimer() {");
		writeLine("	struct itimerval tim = {");
		writeLine("		.it_value =		{	.tv_sec = " + seconds + ",");
		writeLine("							.tv_usec = " + uSeconds);
		writeLine("						},");
		writeLine("		.it_interval =	{	.tv_sec = 0,");
		writeLine("							.tv_usec = 0");
		writeLine("						}");
		writeLine("	};");
		writeLine("	if (setitimer(ITIMER_REAL, &tim, NULL)) perror(\"setitimer\");");
		writeLine("}");
		
		writeLine("");
	}
}
