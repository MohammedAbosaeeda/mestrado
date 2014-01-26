Thread *a,*b,*c,*d,*e,*f;

int func_a() {
    while(1) {
	//suspends itself
	a->suspend();
    }
}
int func_b() {
    while(1) {
	b->suspend();
    }
}
....
int main() {
    //creates all Threads and their Handlers
    a = new Thread(&func_a);
    Handler_Thread handler_a(a);
    b = new Thread(&func_b);
    Handler_Thread handler_b(b);
    ....
    //creates all Alarms
    Alarm alarm_a(Period_A, &handler_a, Alarm::INFINITE);
    Alarm alarm_b(Period_B, &handler_b, Alarm::INFINITE);
    ....
    int status_a = a->join();
    int status_b = b->join();
    ....
}