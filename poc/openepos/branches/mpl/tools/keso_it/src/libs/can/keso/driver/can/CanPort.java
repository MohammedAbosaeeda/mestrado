package keso.driver.can;

import keso.core.*;

/**
 *
 * @author Ralf Ellner
 */
public interface CanPort {
    
    public final static byte READ_SINGLE    = (byte) 0x01; 
    public final static byte READ_MULTI     = (byte) 0x02;
    public final static byte WRITE          = (byte) 0x03;
    public final static byte MODE_MASK      = (byte) 0x03;

    public final static byte MAX_MESSAGE_LENGTH = (byte) 8;
   
    public final static byte STANDARD_ID_BITS = (byte) 12;
    
    /**
     * Get a message object for sending or receiving.
     * If an error occurs null is returned.
     */
    public CanMessageObject openMessageObject(byte mode, boolean useExtendedID);

    public void setBaudrate(int value);

}
