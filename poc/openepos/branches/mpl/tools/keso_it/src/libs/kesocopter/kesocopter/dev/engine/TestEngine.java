package kesocopter.dev.engine;

import keso.core.Task;
import keso.core.TaskService;
import test.DebugOut;

public final class TestEngine extends Task {
	private int cnt=0;
	Engine e1;

	static {
		keso.driver.tricore.tc1796b.TricoreSTM.setRMC(1);
		keso.driver.tricore.tc1796b.TricorePWR.setCPUFrequency();
	};

	public TestEngine() {
		int cpufreq, sysfreq;
		e1 = new Engine((byte)0, (byte) 5);
		cpufreq = keso.driver.tricore.tc1796b.TricorePWR.getCPUFrequency();
		sysfreq = keso.driver.tricore.tc1796b.TricorePWR.getSystemFrequency();
		DebugOut.println("CPU @ " + cpufreq/1000000 + " MHz");
		DebugOut.println("System @ " + sysfreq/1000000 + " MHz");
	}

	private int tlevel=100;
	private boolean tup=false;

	public void launch() {
		if(cnt++ == 100000) {
			cnt =0;
			
			if(tup) {
				tlevel += 5;
				if(tlevel==100)
					tup = false;
			} else {
				tlevel -= 5;
				if(tlevel==0)
					tup = true;
			}
			DebugOut.println("Setting Thrustlevel to " + tlevel + "%");
			e1.setThrustLevel((byte)tlevel);
		}
		e1.iterate();
	}
}
