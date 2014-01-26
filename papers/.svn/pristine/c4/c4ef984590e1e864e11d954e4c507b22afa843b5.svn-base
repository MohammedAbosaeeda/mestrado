#include <chronometer.h>
__USING_SYS;
int main() {
    OStream cout;
    cout << "Chronometer test\n";
    Chronometer c;
    c.start();
    do_something();
    c.stop();
    cout << "do_something() has taken " << c.read() << "ms" << endl;
    return 0;
}