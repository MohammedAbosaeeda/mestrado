package kesocopter.dev.engine;

import keso.driver.tricore.tc1796b.GPIO;

public class Engine {
	/**
	 * Thrustlevel of the engine, in steps of 5%.
	 */
	private byte thrustlevel;
	private byte ioport, iopin;
	private int iter;

	public Engine(byte port, byte pin) {
		thrustlevel=20; // start disabled
		iter=0;
		ioport = port;
		iopin = pin;
		GPIO.config(port,pin,GPIO.GPIO_OUTPUT);
	}

	public void setThrustLevel(byte percent) {
		thrustlevel = (byte) (20 +  (percent / 5));
	}

	/**
	 * Must be called every 50us to generate the correct output
	 * signal.
	 */
	public void iterate() {
		iter++;
		if(iter == 1)
			GPIO.write(ioport,iopin,true);
		else if(iter == thrustlevel)
		 	GPIO.write(ioport,iopin,false);	
		else if(iter == 439) // interval complete
			iter = 0;
	}
}

