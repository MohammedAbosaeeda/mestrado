for (int i = 0; i < 64; i++) {
  // Read from device
  dev_read (DEV_MICA2_TEMP, &data, 1);
  accumulator += data;
}
// MantisOS device driver turns sensor
// ON and OFF for every reading
