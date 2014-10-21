package robertino;

import robertino.mdsa.*;
import keso.core.*;

import keso.driver.avr.atmega8535.*;


public final class Main extends Task {

	private static final short OBSTACLE_TRIGGER_LEVEL = 80;

	private static final short ZERO_SPEED = (short) 0;
	private static final short FORWARD_DRIVE_SPEED = (short) 300;
	private static final short REVERSE_DRIVE_SPEED = (short) -300;
	private static final short FORWARD_TURN_SPEED = (short) 200;
	private static final short REVERSE_TURN_SPEED = (short) -200;

	private static final byte TURN_LEFT = (byte) 0;
	private static final byte TURN_RIGHT = (byte) 1;

	public void launch() {

		byte turn = TURN_LEFT;
		byte stuckCounter = 0;

        
        for (;;) {

			for (short i = 0; i < 30000; ++i) {
			}

			if (!isObstacleInFront()) {

				turn = (byte) ((turn + 1) % 10); 

				if (turn >= 5) {
					driveForwardLeft();
				} else {
					driveForwardRight();
				}
			} else {
				if (isObstacleAtRightSide()) {
					do {
						turnCCW();
					} while (isObstacleInFront() || isObstacleAtRightSide());
				} else {
					do {
						turnCW();
					} while (isObstacleInFront() || isObstacleAtLeftSide());
				}
			}

			if (isStuck()) {

				if (stuckCounter > 10) {
					driveBackward();

					for (short i = 0; i < 30000; ++i) {
						for (byte j = 0; j < 10; ++j) {
						}
					}

					turnCCW();

					for (short i = 0; i < 30000; ++i) {
						for (byte j = 0; j < 10; ++j) {
						}
					}

					stuckCounter = 0;

				} else {
					++stuckCounter;
				}
			} else {
				stuckCounter = 0;
			}

		}

	}

	private boolean isObstacleInFront() {
		if (((MDSAService) PortalService.lookup("drive2")).readADValue(MDSA.AD_CHANNEL_DISTANCE_SENSOR_LEFT) > OBSTACLE_TRIGGER_LEVEL) {
			return true;
		}
		if (((MDSAService) PortalService.lookup("drive0")).readADValue(MDSA.AD_CHANNEL_DISTANCE_SENSOR_RIGHT) > OBSTACLE_TRIGGER_LEVEL) {
			return true;
		}

		return false;
	}

	private boolean isObstacleAtRightSide() {
		return ((MDSAService) PortalService.lookup("drive2")).readADValue(MDSA.AD_CHANNEL_DISTANCE_SENSOR_RIGHT) > OBSTACLE_TRIGGER_LEVEL;
	}

	private boolean isObstacleAtLeftSide() {
		return ((MDSAService) PortalService.lookup("drive0")).readADValue(MDSA.AD_CHANNEL_DISTANCE_SENSOR_LEFT) > OBSTACLE_TRIGGER_LEVEL;
	}

	private boolean isStuck() {
		return ((MDSAService) PortalService.lookup("drive2")).isMotorStuck() ||
			((MDSAService) PortalService.lookup("drive0")).isMotorStuck() ||
			((MDSAService) PortalService.lookup("drive1")).isMotorStuck();
	}



	private void turnCW() {
		((MDSAService) PortalService.lookup("drive2")).setMotorSpeed(REVERSE_TURN_SPEED);
		((MDSAService) PortalService.lookup("drive0")).setMotorSpeed(REVERSE_TURN_SPEED);
		((MDSAService) PortalService.lookup("drive1")).setMotorSpeed(REVERSE_TURN_SPEED);
	}


	private void turnCCW() {
		((MDSAService) PortalService.lookup("drive2")).setMotorSpeed(FORWARD_TURN_SPEED);
		((MDSAService) PortalService.lookup("drive0")).setMotorSpeed(FORWARD_TURN_SPEED);
		((MDSAService) PortalService.lookup("drive1")).setMotorSpeed(FORWARD_TURN_SPEED);
	}

	private void driveForwardLeft() {
		((MDSAService) PortalService.lookup("drive2")).setMotorSpeed((short) (FORWARD_DRIVE_SPEED + 100));
		((MDSAService) PortalService.lookup("drive0")).setMotorSpeed((short) (REVERSE_DRIVE_SPEED + 100));
		((MDSAService) PortalService.lookup("drive1")).setMotorSpeed((short) (ZERO_SPEED + 100));
	}

	private void driveForwardRight() {
		((MDSAService) PortalService.lookup("drive2")).setMotorSpeed((short) (FORWARD_DRIVE_SPEED - 100));
		((MDSAService) PortalService.lookup("drive0")).setMotorSpeed((short) (REVERSE_DRIVE_SPEED - 100));
		((MDSAService) PortalService.lookup("drive1")).setMotorSpeed((short) (ZERO_SPEED - 100));
	}


	private void driveForward() {
		((MDSAService) PortalService.lookup("drive2")).setMotorSpeed(FORWARD_DRIVE_SPEED);
		((MDSAService) PortalService.lookup("drive0")).setMotorSpeed(REVERSE_DRIVE_SPEED);
		((MDSAService) PortalService.lookup("drive1")).setMotorSpeed(ZERO_SPEED);
	}

	private void driveBackward() {
		((MDSAService) PortalService.lookup("drive2")).setMotorSpeed(REVERSE_DRIVE_SPEED);
		((MDSAService) PortalService.lookup("drive0")).setMotorSpeed(FORWARD_DRIVE_SPEED);
		((MDSAService) PortalService.lookup("drive1")).setMotorSpeed(ZERO_SPEED);
	}
}
