package keso.driver.can;

import keso.core.*;


/**
 *
 * This class is the abstraction of a buffer for a CAN packet.
 * NOTE: An instance may be used for either sending or receiving. Using 
 * it for both will result in an unpredictable behavior!
 *
 */
public interface CanMessageObject {

    
    public Memory allocTransmitBuffer(short size, int timeout);
    
    
    public Memory allocReceiveBuffer(short size, int timeout);
    

    public void installEventHandler(CanEventHandler handler);
    
    /**
     * Set the CAN ID of the MOB.
     */
    public void setIdentifier(int id);

    /**
     * Set the receive mask. A zero bit in the mask indicates a don't
     * care bit in the CAN ID.
     * @require MOB must be opened with READ_MULTI.
     */
    public void setReceiveMask(int mask);
    
    /**
     * Returns the CAN ID of the packet currently located in the MOB buffer.
     * @require !releaseReceiveBuffer().
     */
    public int getPacketIdentifier();
   
    /**
     * Read the next byte from the MOB buffer. The caller has to check before reading if
     * the buffer does contain one more byte.
     */ 
    public byte read();
    
    /**
     * Read the content from the MOB buffer into memory. The remaining
     * bytes in the MOB buffer are read (up to maxLength bytes).
     * @param buffer The buffer to write to.
     * @param offset Offset in the buffer.
     * @param maxLength Maximum number of bytes to write into buffer.
     * @return Number of bytes written to buffer.
     */
    public byte readPacket(Memory buffer, short offset, byte maxLength);

    /**
     * Mark the MOB receive buffer as writable.
     */
    public void releaseReceiveBuffer();
    
    /**
     * Write one byte to the MOB buffer. The caller has to ensure that there is enough space
     * left in the buffer.
     */
    public void write(byte data);
    
    /**
     * Write the content of a buffer to the MOB buffer.
     * @param buffer The buffer to read from.
     * @param offset Offset in the buffer.
     * @param maxLength Maximum number of bytes to read from the buffer.
     * @return Number of bytes read from the buffer.
     */
    public byte writePacket(Memory buffer, short offset, byte maxLength);    
    
    /**
     * Write the content of the MOB buffer to the CAN bus.
     */
    public void flush();

}


