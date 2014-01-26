#include <utility/ostream.h>
#include <alarm.h>

__USING_SYS

int main() {
    OStream cout;

    cout << "This is a test!\n";

    while (1) {
        cout << 'U';
        Alarm::delay(100000);
    }
}
