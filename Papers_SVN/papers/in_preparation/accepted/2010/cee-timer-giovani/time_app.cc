static int iterations = 100;
static Alarm::Microsecond time = 100000;
int main() 
{
    OStream cout;
    Clock clock;
    Chronometer chron;
    // Read current system time
    cout << "Current Time: " << clock.now() << endl;
    // Create a handler function and associate it
    // to a periodic time event.
    Handler_Function handler(&func);
    Alarm alarm(time, &handler, iterations);
    // Start a chronometer and put this thread to sleep
    // Afterwards, stop and read the chronometer
    chron.start();
    Alarm::delay(time * (iterations + 1));
    chron.stop();
    cout << "Elapsed time: " << chron.read() << endl;
    return 0;
}