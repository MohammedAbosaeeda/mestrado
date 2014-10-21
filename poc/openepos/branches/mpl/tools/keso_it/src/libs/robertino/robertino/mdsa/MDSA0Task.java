package robertino.mdsa;

import keso.core.*;

public final class MDSA0Task extends Task {

    public void launch() {
        PortalService.handlePackets("drive0", "robertino_can");
    }
    
}
