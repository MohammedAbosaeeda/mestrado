int thermometer_sample() {
    // Open ADC power attribute on sysfs
  struct sysfs_attribute * adc_power
    = sysfs_open_attribute(
        "/sys/devices/i2c/AD8493/power/state"
      );

  int accumulator = 0;
  int value;

  // Switching to power state 0 (ON)
  sysfs_write_attribute(adc_power, "0", 1);
  for(int i = 0; i < 64; i++) {
      // Read ADC result
    ad8493_get(adc_device, &value);
    accumulator += value;
  }
  // Switching to power state 3 (OFF)
  sysfs_write_attribute(adc_power, "3", 2);

  // Convert reading into celcius degrees
  return raw_to_celcius(accumulator / 64);
}
