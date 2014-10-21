package keso.core.io;

import keso.core.*;


public interface PacketHandler {
    
    /**
     *
     * This method must be implemented by a custom packet handler. 
     * @param source: The packet stream that caused the event.
     */
    public void packetReceived(PacketStream source);

}

