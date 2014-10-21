package robertino.mdsa;

import keso.core.*;

public final class MDSA1Task extends Task {

    public void launch() {
        PortalService.handlePackets("drive1", "robertino_can");
    }
    
}
