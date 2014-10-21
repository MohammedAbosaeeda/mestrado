package lukas.fsm;

import keso.core.TaskService;
import lukas.control.Command;
import lukas.generator.Sequence;
import lukas.device.*;
import lukas.common.Timing;

public class FSM {
	private static final int FSM_FETCH        =0;
	private static final int FSM_UP           =1;
	private static final int FSM_DOWN         =2;
	private static final int FSM_HOLD         =3;
	private static final int FSM_RELEASE      =4;
	private static final int FSM_END          =5;
	
	private static final int FSM_UP_START     =0;
	private static final int FSM_UP_1         =1;
	private static final int FSM_UP_2         =2;
	private static final int FSM_UP_END       =3;
	
	private static final int FSM_DOWN_START   =0;
	private static final int FSM_DOWN_1       =1;
	private static final int FSM_DOWN_2       =2;
	private static final int FSM_DOWN_END     =3;
	
	private static final int FSM_HOLD_START   =0;
	private static final int FSM_HOLD_END     =1;
	
	private static final int FSM_RELEASE_START=0;
	private static final int FSM_RELEASE_END  =1;

	private static int state         = FSM_FETCH;
	private static int state_up      = FSM_UP_START;
	private static int state_down    = FSM_DOWN_START;
	private static int state_hold    = FSM_HOLD_START;
	private static int state_release = FSM_RELEASE_START;

	private static byte currentCoil = 0;
	private static int coilsToGo = 0;
	private static int timeToWait = 0;

	public static int fetch() {
		int timeToNextStep = -1;
		short cmdtype = Sequence.getCommand();
		short cmdn = Sequence.getCommand();

		switch (cmdtype) {
			case Command.MOVE_END:
				Coil.allOff();
				PhotoSensor.errorOnBelow((byte) 8);
				break;

			case Command.MOVE_UP:
				state = FSM_UP;
				state_up = FSM_UP_START;
				coilsToGo = cmdn;
				timeToNextStep = 0;
				break;

			case Command.MOVE_DOWN:
				state = FSM_DOWN;
				state_down = FSM_DOWN_START;
				coilsToGo = cmdn;
				timeToNextStep = 0;
				break;

			case Command.MOVE_HOLD:
				state = FSM_HOLD;
				state_hold = FSM_HOLD_START;
				timeToWait = cmdn;
				timeToNextStep = 0;
				break;

			case Command.MOVE_RELEASE:
				state = FSM_RELEASE;
				state_release = FSM_RELEASE_START;
				timeToWait = cmdn;
				timeToNextStep = 0;
		}
		return timeToNextStep;
	}

	public static int up() {
		int timeToNextStep = -1;
		switch(state_up) {
			case FSM_UP_START:
				PhotoSensor.maskAllButOne(currentCoil);
				PhotoSensor.disableBothEdges();
				PhotoSensor.enableRisingEdge();
				PhotoSensor.errorOnBelow(currentCoil);
				Coil.on(currentCoil);
				state_up = FSM_UP_1;
				break;

			case FSM_UP_1:
				PhotoSensor.maskAllButOne((byte)(currentCoil+(byte)1));
				Coil.on((byte)(currentCoil+(byte)1));
				PhotoSensor.errorOnBelow(currentCoil);
				state_up = FSM_UP_2;
				break;

			case FSM_UP_2:
				currentCoil++;
				coilsToGo--;
				PhotoSensor.errorOnBelow(currentCoil);
				if (coilsToGo == 0) {
					state_up = FSM_UP_END;
					timeToNextStep = 0;
					PhotoSensor.maskAll();
				} else {
					PhotoSensor.maskAllButOne((byte)(currentCoil+(byte)1));
					PhotoSensor.errorOnBelow(currentCoil);
					Coil.on((byte)(currentCoil+(byte)1));
				}
			}
		return timeToNextStep;
	}

	public static int down() {
		int timeToNextStep = -1;

		switch(state_down) {
			case FSM_DOWN_START:
				PhotoSensor.maskAllButOne((byte)(currentCoil-(byte)1));
				PhotoSensor.disableBothEdges();
				PhotoSensor.enableFallingEdge();
				PhotoSensor.errorOnBelow((byte)(currentCoil-(byte)1));
				Coil.off(currentCoil);
				state_down = FSM_DOWN_1;
				break;

			case FSM_DOWN_1:
				currentCoil--;
				coilsToGo--;
				PhotoSensor.maskAll();
				PhotoSensor.disableBothEdges();
				PhotoSensor.errorOnBelow(currentCoil);

				if(coilsToGo == 0) {
					state_down = FSM_DOWN_END;
					timeToNextStep = 0;
				} else {
					state_down = FSM_DOWN_2;
					timeToNextStep = Timing.DELAY_TO_BRAKE;
				}
				break;

			case FSM_DOWN_2:
				Coil.on(currentCoil);
				PhotoSensor.errorOnBelow((byte)(currentCoil-(byte)1));
				state_down = FSM_DOWN_START;
				timeToNextStep = Timing.BRAKE_DURATION;
		}
		return timeToNextStep;
	}

	public static int hold() {
		int timeToNextStep = -1;

		switch(state_hold) {
			case FSM_HOLD_START:
				PhotoSensor.maskAll();
				PhotoSensor.disableBothEdges();
				PhotoSensor.errorOnBelow(currentCoil);
				Coil.on(currentCoil);
				timeToNextStep = timeToWait;
				state_hold = FSM_HOLD_END;
		}
		return timeToNextStep;
	}

	public static int release() {
		int timeToNextStep = -1;

		switch(state_release) {
			case FSM_RELEASE_START:
				PhotoSensor.maskAll();
				PhotoSensor.disableBothEdges();
				PhotoSensor.errorOnBelow(currentCoil);
				Coil.allOff();
				timeToNextStep = timeToWait;
				state_release = FSM_RELEASE_END;
		}
		return timeToNextStep;
	}

	public static int step() {
		int timeToNextStep = -1;

		switch(state) {
			case FSM_FETCH:
				timeToNextStep = fetch();
				break;

			case FSM_UP:
				if (state_up == FSM_UP_END) {
					state = FSM_FETCH;
					timeToNextStep = 0;
				} else {
					timeToNextStep = up();
				}
				break;

			case FSM_DOWN:
				if (state_down == FSM_DOWN_END) {
					state = FSM_FETCH;
					timeToNextStep = 0;
				} else {
					timeToNextStep = down();
				}
				break;

			case FSM_HOLD:
				if (state_hold == FSM_HOLD_END) {
					state = FSM_FETCH;
					timeToNextStep = 0;
				} else {
					timeToNextStep = hold();
				}
				break;

			case FSM_RELEASE:
				if (state_release == FSM_RELEASE_END) {
					state = FSM_FETCH;
					timeToNextStep = 0;
				} else {
					timeToNextStep = release();
				}
		}
		return timeToNextStep;
	}
}
