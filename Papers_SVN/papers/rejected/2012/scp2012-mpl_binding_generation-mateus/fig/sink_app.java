public class SinkApp extends Thread {
  // ...    
  public void run() {
    NIC nic = new NIC();
    // ...
    while (true) {
      while(!(nic.receive(msg)) > 0)) {
        System.out.println("failed\n");
      }
      // Shows temperature
    }
  }
}
