package test;

public final class ISRHandlers {
	public static int counter=0;

	public static void isr1Handler() {
		counter++;
	}
}
