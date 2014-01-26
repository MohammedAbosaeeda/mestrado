#include <utility/ostream.h>
#include <thread.h>
#include <alarm.h>
__USING_SYS
const int iterations = 100;
int func_a(void);
Thread * a;
OStream cout;

int main() {
    cout << "Thread test\n";
    a = new Thread(&func_a);
    int status_a = a->join();
    cout << "Thread test done\n";
    delete a;
    return 0;
}

int func_a(void) {
    for(int i = iterations; i > 0; i--) {
        for(int i = 0; i < 79; i++)
            cout << "a";
            cout << "\n";
            Alarm::delay(500000);
    }
    return 'A';
}