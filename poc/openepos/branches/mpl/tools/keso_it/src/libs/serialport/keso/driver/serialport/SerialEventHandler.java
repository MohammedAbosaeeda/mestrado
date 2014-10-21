package keso.driver.serialport;

import keso.core.io.*;

public interface SerialEventHandler {
    
    /**
     *
     * This method is called when at least one byte was received
     *
     */
    public void handleSerialEvent();

}
