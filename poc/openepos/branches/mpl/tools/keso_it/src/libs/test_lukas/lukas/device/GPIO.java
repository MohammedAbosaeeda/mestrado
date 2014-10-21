package lukas.device;

import keso.core.*;
public final class GPIO {
	public static final byte GPIO_INPUT=0;
	public static final byte GPIO_OUTPUT=1;

	private static final int GPIO0_BASE = 0xf0000c00;
	private static final int N_GPIO = 11;

	private static final int GPIO_PORTSIZE=256;
	private static final int OFS_GPIO_OUT=0;
	private static final int OFS_GPIO_OMR=1*4;
	private static final int OFS_GPIO_IOCR=4*4;
	private static final int OFS_GPIO_IN=9*4;
	private static final int OFS_GPIO_PDR=16*4;
	private static final int OFS_GPIO_ESR=20*4;

	private static final Memory gpio=
		MemoryService.allocStaticDeviceMemory(GPIO0_BASE,N_GPIO*GPIO_PORTSIZE);

	/**
	 * configures a specific pin on a specific port as
	 * input or output
	 */
	public static final void config(byte port, byte pin, byte iotype) {
		if(checkPinPort(port, pin) == false) return;

		// Should work for little endian
		int offset = port*GPIO_PORTSIZE + OFS_GPIO_IOCR + pin;

		switch(iotype) {
			case GPIO_INPUT:
				gpio.set8(offset, (byte) 0);
				break;

			case GPIO_OUTPUT:
				gpio.set8(offset, (byte) 0x80);
		}
	}

	/**
	 * writes a specific high or low level to an output port
	 */
	public static final void write(byte port, byte pin, boolean value) {
		if(checkPinPort(port, pin) == false) return;

		int offset = port*GPIO_PORTSIZE + OFS_GPIO_OUT;

		if(value)
			gpio.or32(offset, 1<<pin);
		else gpio.and32(offset, ~(1<<pin));
	}

	/**
	 * reads a specific high or low level from an input port
	 */
	public static final boolean read(byte port, byte pin) {
		if(checkPinPort(port, pin) == false) return false;

		int offset = port*GPIO_PORTSIZE + OFS_GPIO_IN;
		int gpioin = gpio.get32(offset);
		return ( (gpioin & (1<<pin)) != 0 );
	}

	private static final boolean checkPinPort(byte port, byte pin) {
		if( (port<(byte)0) || (port>(byte)10)) return false;
		if( (pin<(byte)0)  || (pin>(byte)15)) return false;
		return true;
	}
}
