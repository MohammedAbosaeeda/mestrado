int main() {
  while(1) {
    modem_send(dest, sensor_read());
    alarm(120000000, NO_BLOCK);

    modem_standby();

    sensor_standby();

    process_standby();	


  }
}
