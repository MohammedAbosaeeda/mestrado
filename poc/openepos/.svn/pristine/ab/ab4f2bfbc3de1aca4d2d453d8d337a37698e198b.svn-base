package test;

public class PrinterThread extends Thread {
	
	private int iterations;
	private char message;

	public PrinterThread(char msg, int iter) {
		super();
		this.iterations = iter;
		this.message = msg;
	}
	
	public void run() {
		for(int i = iterations; i > 0; i--) {
			for(int j = 0; j < 79; j++) {
				UART.put(message);
			}
			
			UART.put('\n');
			
			Thread.sleep(500);			
		}
	}
	
}
