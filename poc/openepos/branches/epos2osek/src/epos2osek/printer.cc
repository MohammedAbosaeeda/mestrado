#include <utility/ostream.h>
#include <epos2osek/printer.hh>

__USING_SYS

void Printer::print(char * message) {
    OStream cout;
    cout << message;
}
