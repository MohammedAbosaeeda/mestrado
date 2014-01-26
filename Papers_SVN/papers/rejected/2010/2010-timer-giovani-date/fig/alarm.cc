static Chronometer chron;
static Microsecond time_stamps[11];
volatile static int n = 0;
void handler(void) {
    time_stamps[n++] = chron.read();
}
int main() {
    // Register the events
    Handler_Function handler(&handler);
    Alarm alarm(PERIOD, &handler, 11);
    // Wait for all handlers to finish
    while(n<11) {  }
    // Print intervals
    for(unsigned int i = 0; i < 10; i++)
	cout << time_stamps[i+1] - time_stamps[i] << endl;
}

