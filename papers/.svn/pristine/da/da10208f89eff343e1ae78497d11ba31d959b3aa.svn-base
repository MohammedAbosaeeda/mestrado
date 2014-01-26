//...
  //When period is reached
  event void MilliTimer.fired() {
    //Turn On Sensor
    call ReadControl.StdControl.start();
    //Start Sensor Read
    call Read.read();
  }

  //Senor Read Done
  event void Read.readDone(error_t result,
                           uint16_t data) {
    count++;
    if(count < 64) {
      temperature += data;
      call Read.read();
    }
    else {
      temperature = temperature / 64;
      count = 0;
    }
    //Turn Off Sensor
    ReadControl.StdControl.stop();
    //...
  }
//...