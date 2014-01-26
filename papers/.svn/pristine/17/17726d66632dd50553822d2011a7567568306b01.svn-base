void MAC::power(int mode) {
  switch(mode) {
  // ...
  case STANDBY:
    this->flush_buffers();
    timer->power(STANDBY);
    this->disable();
    radio->power(STANDBY);
    break;
  // ...
  }
}

void Timer::power(int mode) {
  switch(mode) {
  // ...
  case STANDBY:
    this->disable();
    break;
  // ...
  }
}

void Radio::power(int mode) {
  switch(mode) {
  // ...
  case STANDBY:
    this->disable();
    break;
  // ...
  }
}