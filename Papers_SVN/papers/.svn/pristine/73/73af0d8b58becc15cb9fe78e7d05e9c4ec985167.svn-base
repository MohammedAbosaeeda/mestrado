package test;
import keso.core.Task;

public class UART_Test extends Task {
  public void launch() {
    UART serial;
    serial = new UART(19200, 8, 0, 1, 0);
    for(int i = 0; i < 10000; i++) {
      serial.put('M');
    }
  }  
}

