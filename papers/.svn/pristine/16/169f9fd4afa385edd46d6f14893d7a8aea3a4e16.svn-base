// ...
  // When initializing system
  event void Boot.booted() {
    // ...
    // Put radio in listening mode
    call RadioControl.start();
    // ...
  }

  // When data is received
  event message_t* Receive.receive(
                     message_t* bufPtr,
                     void* payload, uint8_t len
                   ) {
    if (len != sizeof(radio_sense_msg_t))
      return bufPtr;
    else {
      radio_sense_msg_t* rsm = 
               (radio_sense_msg_t*) payload;
      if(rsm->data == EMERGENCY) {
        // Turn radio off
        // Someone has to turn it on again later
        RadioControl.stop();
        emergency_semaphore++;
      }

      return bufPtr;
    }
  }
//...
