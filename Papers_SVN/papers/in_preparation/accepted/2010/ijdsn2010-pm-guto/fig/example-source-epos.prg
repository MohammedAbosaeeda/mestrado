Thermometer thermometer;
Actuator actuator;
Semaphore emergency(0);
Communicator link(CONTROL_CENTER);

int main() {
  new Thread(&recovery, HIGH_PRIO, NO_DVS);
  new Thread(&trigger, NORMAL_PRIO);
  new Periodic_Thread(&monitor, LOW_PRIO, 1000000);
  return 0;
}
void monitor() {
  int count = 0;
  while(true) {
    int temperature = thermometer.get();
    thermometer.power(OFF);
    if(temperature > THRESHOLD)
      emergency.v();
    if(!(++count % 10)) {
      link.write(temperature);
      link.power(OFF);
    }
    wait_for_next_cycle;
  }
}
void trigger() {
  while(true) {
    link.power(LISTEN_ONLY);
    if(link.read() == EMERGENCY) // blocks calling thread
      emergency.v();
  }
}
void recovery() {
  while(true) {
    emergency.p();
    int temperature = thermometer.get();
    thermometer.power(OFF);
    while(temperature > THRESHOLD) {
      actuator.shoot();
      link.write(temperature);
      link.power(OFF);
      delay(STABILIZATION_TIME);
      temperature = thermometer.get();
      thermometer.power(OFF);
    }
  }
}
