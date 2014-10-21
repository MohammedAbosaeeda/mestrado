package lukas.device;

public final class Coil {
	private static byte activeCoil;

	public static void init() {
		for(int i=5; i<=12; i++)
			GPIO.config((byte) 0, (byte) i, GPIO.GPIO_OUTPUT);

		allOff();
	}

	public static void on(byte coilno) {
		if(chkCoilNo(coilno)==false) return;

		if(activeCoil != -1)
			off(activeCoil);

		GPIO.write((byte)0, (byte)(coilno+(byte)5), true);
		activeCoil=coilno;
	}

	public static void off(byte coilno) {
		if(chkCoilNo(coilno)==false) return;

		GPIO.write((byte)0, (byte)(coilno+(byte)5), false);
		if(activeCoil==coilno)
			activeCoil=(byte)-1;
	}

	public static void allOff() {
		for(int i = 5; i <= 12; i++)
			GPIO.write((byte)0, (byte)i, false);
		activeCoil=(byte)-1;
	}

	public static boolean status(byte coilno) {
		if(chkCoilNo(coilno)==false) return false;
		return GPIO.read((byte)0, (byte)(coilno+(byte)5));
	}

	public static byte current() {
		return activeCoil;
	}

	private static boolean chkCoilNo(byte coilno) {
		if ((coilno < (byte)0) || (coilno > (byte)7))
			return false;
		return true;
	}
}
