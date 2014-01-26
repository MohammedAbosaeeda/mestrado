package test;

import keso.core.Task;

public class UART_SendSingleCharTest extends Task {
    public void launch() {
        char message = 'H';
        
        while (true) {
            UART.put(message);
        }

    }
    
}
