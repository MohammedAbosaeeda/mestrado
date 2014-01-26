int main() {
  while(1) {
    modem_send(dest, sensor_read());
    alarm(120000000, NO_BLOCK);
    system_standby();	
  }
}
