/* TinyOS Sensing Application */
configuration SenseToUART {}
implementation {
  components Main, SenseToInt, IntToUART, TimerC, DemoSensorC as Sensor;
  Main.StdControl -> SenseToInt;
  Main.StdControl -> IntToUART;
  SenseToInt.Timer -> TimerC.Timer[unique("Timer")];
  SenseToInt.TimerControl -> TimerC;
  SenseToInt.ADC -> Sensor;
  SenseToInt.ADCControl -> Sensor;
  SenseToInt.IntOutput -> IntToUART;
}
