package lukas;

import keso.core.*;
import lukas.device.*;

public final class Main extends Task {

	public static void callback(boolean risingEdge, byte sensorno) {
		Task fsmTask = TaskService.getTaskByName("FSMTask");
		TaskService.activate(fsmTask);
	}

	public static void errorCallback(boolean risingEdge, byte sensorno) {
		PhotoSensor.enableRisingEdge();
		PhotoSensor.enableFallingEdge();

		Coil.allOff();
		if(!risingEdge) {
			Coil.on(sensorno);
			int time;
			Stopwatch.start((byte)0);
			do {
				time = Stopwatch.stop((byte)0);
			} while(time < 25000);

			Coil.off(sensorno);
		}
	}

	static void StartupHook() {
		Coil.init();
		PhotoSensor.init();
		keso.driver.tricore.tc1796b.TricorePWR.setCPUFrequency();
		keso.driver.tricore.tc1796b.TricoreSTM.setRMC(1);
		Stopwatch.init(1000000);

		lukas.generator.Sequence.start();
	}

	public void launch() {
		int timeToNextStep;

		do {
			timeToNextStep = lukas.fsm.FSM.step();
		} while(timeToNextStep == 0);

		if (timeToNextStep > 0) {
			Alarm sa= AlarmService.getAlarmByName("SysAlarm");
			AlarmService.setRelAlarm(sa, timeToNextStep, 0);
		}

		TaskService.terminate();
	}
}
