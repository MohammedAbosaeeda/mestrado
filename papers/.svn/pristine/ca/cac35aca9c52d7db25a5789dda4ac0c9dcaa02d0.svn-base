int main() {
  while(1) {
    modem_send(dest, sensor_read());
    alarm(120000000, NO_BLOCK);
    tcpip_stack_standby();
    modem_standby();
    uart_standby();
    sensor_standby();
    adc_standby();
    process_standby();
    scheduler_standby();
    cpu_standby();	
  }
}
