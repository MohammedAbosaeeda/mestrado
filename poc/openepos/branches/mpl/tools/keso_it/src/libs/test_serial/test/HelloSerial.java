package test;

import keso.driver.serialport.SerialPort;
import keso.driver.tricore.tc1796b.*;
import keso.core.Task;
import keso.core.TaskService;
//import keso.core.Events;
//import keso.core.EventService;

public class HelloSerial extends Task {

	public void launch() {
		SerialPort serialPort = new ASC0Polling();
		//SerialPort serialPort = new ASC0InterruptDriven();

		int sysFreq = TricorePWR.getSystemFrequency();
		int cpuFreq = TricorePWR.getCPUFrequency();

		serialPort.println();
		serialPort.println("Hello world! This is your serial port speaking.");
		serialPort.println();

		serialPort.print("System frequency: ");
		serialPort.print(sysFreq);
		serialPort.println(" Hz");
		serialPort.print("CPU frequency: ");
		serialPort.print(cpuFreq);
		serialPort.println(" Hz");
		
		serialPort.println();
		serialPort.print("A negative number: ");
		serialPort.print(-42);
		serialPort.print(" and a zero: ");
		serialPort.println(0);
		
		serialPort.println();
		serialPort.println("Press some keys for great fun ('q' quits).");

		char in = ' ';
		int keys = 0;
		while (in != 'q' && in != 'Q') {
			in = serialPort.getc();
			serialPort.print('[');
			serialPort.print(in);
			serialPort.print(']');
			keys++;
		}
		serialPort.println();
		serialPort.print("You pressed ");
		serialPort.print(keys);
		serialPort.println(" keys. Amazing!");
		serialPort.println();
		serialPort.println("Goodbye.");

		TaskService.terminate();
	}

	protected void finalize() {
	}
}
