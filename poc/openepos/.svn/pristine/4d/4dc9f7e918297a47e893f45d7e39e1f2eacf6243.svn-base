// EPOS Mult Abstraction Test Program

#include <components/add.h>
#include <components/mult.h>
#include <utility/ostream.h>

__USING_SYS

void complex_test(){
    Implementation::Channel_t dummy;
    unsigned char iid[2] = {0,0};
    Implementation::Mult unified(dummy,dummy,iid);

    Mult rtl(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    OStream cout;

    cout << "\nMult Complex Test\n\n";

    for (int i = 0; i < 8; ++i) {
        for (int j = 0; j < 8; ++j) {
            cout << "unified.mult(" << i << ", " << j << ") = ";
            unsigned int data_uni = unified.mult(i,j); cout << data_uni << "\n";
            cout << "rtl.mult(" << i << ", " << j << ") = ";
            unsigned int data_rtl = rtl.mult(i,j); cout << data_rtl << "\n";

            if(data_rtl != data_uni){
                cout << "ERROR: data_rtl != data_uni\n";
                goto ERROR;
            }

            cout << "unified.mult_square(" << i << ", " << j << ") = ";
            data_uni = unified.mult_square(i,j); cout << data_uni << "\n";
            cout << "rtl.mult_square(" << i << ", " << j << ") = ";
            data_rtl = rtl.mult_square(i,j); cout << data_rtl << "\n";

            if(data_rtl != data_uni){
                cout << "ERROR: data_rtl != data_uni\n";
                goto ERROR;
            }
        }
    }

    ERROR:
    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;
}

void simple_test(){

    Mult mult(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    OStream cout;

    cout << "\nMult Complex Test\n\n";

    unsigned int result = 0;

    cout << "Calling mult.mult(" << 0 << ", " << 0 << ")\n";
    result = mult.mult(0,0); cout << "Result = " << result << "\n";

    cout << "Calling mult.mult(" << 0 << ", " << 10 << ")\n";
    result = mult.mult(0,10); cout << "Result = " << result << "\n";

    cout << "Calling mult.mult(" << 10 << ", " << 0 << ")\n";
    result = mult.mult(10,0); cout << "Result = " << result << "\n";

    cout << "Calling mult.mult(" << 1 << ", " << 10 << ")\n";
    result = mult.mult(1,10); cout << "Result = " << result << "\n";

    cout << "Calling mult.mult(" << 10 << ", " << 1 << ")\n";
    result = mult.mult(10,1); cout << "Result = " << result << "\n";

    cout << "Calling mult.mult(" << 10 << ", " << 5 << ")\n";
    result = mult.mult(10,5); cout << "Result = " << result << "\n";

    cout << "Calling mult.mult(" << 5 << ", " << 10 << ")\n";
    result = mult.mult(5,10); cout << "Result = " << result << "\n";

    cout << "Calling mult.mult(" << 10 << ", " << 10 << ")\n";
    result = mult.mult(10,10); cout << "Result = " << result << "\n";

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;
}

int main()
{

    simple_test();


    return 0;
}
