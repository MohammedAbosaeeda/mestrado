class AVR8_GPIO_Port: 
        protected GPIO_Port_Common {
public:
 enum {
   PORTA = 0x39,
   PORTB = 0x36,
   PORTC = 0x33,
   PORTD = 0x30
 }; // ...
 void operator=(unsigned char value) {
   _ddr = (unsigned char)0xff;
   _port = value;
 }
 operator unsigned char() {
   _ddr = (unsigned char)0x00;
   return _pin;
 } // ...
private:
 IO_Register<unsigned char> _pin;
 IO_Register<unsigned char> _ddr;
 IO_Register<unsigned char> _port;
};


