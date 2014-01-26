package test;
import keso.core.Task;
import epos.mediador.UART;

public class UART_Test extends Task {
    public void launch() {
        UART serial = new UART(19200, 8, 0, 1, 0);
        while (true) {
            serial.put('M');
        }
    }  
}
