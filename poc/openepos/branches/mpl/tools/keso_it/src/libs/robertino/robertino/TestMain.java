package robertino;

import robertino.mdsa.*;
import keso.core.*;

import keso.driver.avr.atmega8535.*;

public final class TestMain extends Task {

	public void launch() {

		MotorDriver.setDesiredSpeed((short)200);
		for (short i = 0; i < 30000; ++i) {
			for (short l=0;l<10;l++) {}
	       	}
		MotorDriver.setDesiredSpeed((short)0);

		for (;;) {

			((MDSAService) PortalService.lookup("drive0")).setMotorSpeed((short)200);
			for (short i = 0; i < 30000; ++i) { }

			((MDSAService) PortalService.lookup("drive0")).setMotorSpeed((short)0);
			for (short i = 0; i < 10000; ++i) { }

			((MDSAService) PortalService.lookup("drive0")).setMotorSpeed((short)-200);
			for (short i = 0; i < 30000; ++i) { }

			((MDSAService) PortalService.lookup("drive0")).setMotorSpeed((short)0);
			for (short i = 0; i < 10000; ++i) { }
		}

	}
}
