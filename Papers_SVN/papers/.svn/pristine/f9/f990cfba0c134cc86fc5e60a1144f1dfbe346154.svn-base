public class SensorApp extends Thread {
  // ...    
  public void run() {
    NIC nic = new NIC();
    TemperatureSensor ts = new TemperatureSensor();
    // ...
    while (true) {
      msg[1] = c++;
      msg[2] = ts.sample();
      while (int r = nic.send(NIC.BROADCAST, msg) 
             != NIC.SENT_OK) 
      { System.out.println("failed " + r); }

      // Send OK
      Alarm.delay(1000000);
    }
  }
}
