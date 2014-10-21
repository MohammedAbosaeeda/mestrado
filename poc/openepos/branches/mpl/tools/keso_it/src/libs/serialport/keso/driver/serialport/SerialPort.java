package keso.driver.serialport;

import keso.core.*;

public interface SerialPort {    
    
    public static final byte TRANSFERMODE_SYNC = (byte) 10; 
    public static final byte TRANSFERMODE_ASYNC = (byte) 11;

    
    public static final byte PARITY_NONE = (byte) 20;
    public static final byte PARITY_EVEN = (byte) 21;
    public static final byte PARITY_ODD = (byte) 22;
    public static final byte PARITY_MARK = (byte) 23;
    public static final byte PARITY_SPACE = (byte) 24;
    
    
    public static final byte STOPBITS_ONE = (byte) 1;
    public static final byte STOPBITS_ONE_POINT_FIVE = (byte) 30;
    public static final byte STOPBITS_TWO = (byte) 2;

    
    public static final byte DATABITS_5 = (byte) 5;
    public static final byte DATABITS_6 = (byte) 6;
    public static final byte DATABITS_7 = (byte) 7;
    public static final byte DATABITS_8 = (byte) 8;
    public static final byte DATABITS_9 = (byte) 9;
    
    
    public static final byte SYNC_CLOCK_MODE_RISING_EDGE = (byte) 40;
    public static final byte SYNC_CLOCK_MODE_FALLING_EDGE = (byte) 41;
    
    
    public void setBaudrate(int baudRate);
    
    
    public void setTransferMode(byte mode);
    
    
    public void setParityMode(byte mode);
    
    
    public void setStopBits(byte stopBits);
    
    
    public void setFrameSize(byte size);
    
    
    public void setSynchronousClockMode(byte mode);

        
    /**
     * Value indicating that the next frame was not correctly received i.e. the
     * stop bit was zero. The error is cleared by reading the next frame with
     * read().
     *
     * @return true if the next frame has an error, else false
     *
     */
    public boolean hasFrameError();
    
    
    /**
     * Value indicating that the next frame was not correctly received i.e. the
     * parity was wrong. The error is cleared by reading the next frame with
     * read().
     *
     * @return true if the next frame has an error, else false
     *
     */
    public boolean hasParityError();
    
    
    /**
     * Value indicating that an data over run ocurred. This happens when there
     * were more frames received, than could be processed The error is cleared
     * by reading the next frame with read().
     *
     * @return true if the next frame has an error, else false
     *
     */
    public boolean hasDataOverRun();

    public void enablePacketMode();
    
    public Memory allocTransmitBuffer(short size, int timeout);
    public Memory allocReceiveBuffer(short size, int timeout);

    public void write(byte data);
    public void writePacket(Memory data, short length);
       
    public byte read();
    public void readPacket(Memory data, short length, boolean needStart);
}
