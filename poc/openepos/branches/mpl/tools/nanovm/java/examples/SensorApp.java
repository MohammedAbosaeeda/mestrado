/* Author: Mateus Krepsky Ludwich. */

import nanovm.epos.mediator.NIC;
import nanovm.epos.mediator.TemperatureSensor;
import nanovm.epos.abstraction.PeriodicThread;


class SensorNode extends PeriodicThread {
    private char msg;
    private NIC nic;
    private TemperatureSensor temp;
    private char c;

    public SensorNode(int period) {
        super(period);
        nic = new NIC();
        temp = new TemperatureSensor();
        c = 0;
    }
    
    public void start() {
        super.start();
    }

    public void run() {
        char id = 1;
        // System.out.println("Sensor id = " + id);

        msg = temp.sample();

        int r;
        if ((r = nic.send(NIC.BROADCAST, msg)) != NIC.SENT_OK) {
            // System.out.println("failed " + r);
        }

        // System.out.println("tx done");
    }

}



public class SensorApp {
    
    
    public static void main(String[] args) {
        int period = 1000000; // 1s
        SensorNode sensor = new SensorNode(period);
        sensor.start();
    }
}


