int print_leds(void) {
    while(1) {
	unsigned char leds = 0;
	for(unsigned char i = 0; i < NUM_LEDS; i++) {
            // leds |= ... ;
	}
	CPU::out8(Machine::IO::PORTA, ~leds);
    }
}
void change_led_intensity(void){
    for(unsigned char i = 0; i < NUM_LEDS; i++) {
	// led_intensity[i] == ... ;
    }
}
int main() {
    Handler_Function handler(&change_led_intensity);
    Alarm alarm_a(20000, &handler, Alarm::INFINITE);
    Thread * a = new Thread(&print_leds);
    int status_a = a->join();
}
