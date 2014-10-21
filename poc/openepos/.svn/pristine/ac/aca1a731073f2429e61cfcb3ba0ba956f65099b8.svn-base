// EPOS Add Abstraction Test Program

#include <components/add.h>
#include <utility/ostream.h>

__USING_SYS

void simple_test(){
    OStream cout;

    Add add(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    cout << "\nADD Simple Test\n\n";

    cout << "Calling add.add(" << 0 << ", " << 0 << ")\n";
    unsigned int result = add.add(0,0); cout << "Result = " << result << "\n";

    cout << "Calling add.add(" << 40 << ", " << 0 << ")\n";
    result = add.add(40,0); cout << "Result = " << result << "\n";

    cout << "Calling add.add(" << 0 << ", " << 40 << ")\n";
    result = add.add(0,40); cout << "Result = " << result << "\n";

    cout << "Calling add.add(" << 1 << ", " << 39 << ")\n";
    result = add.add(1,39); cout << "Result = " << result << "\n";

    cout << "Calling add.add(" << 39 << ", " << 1 << ")\n";
    result = add.add(39,1); cout << "Result = " << result << "\n";

    cout << "Calling add.add(" << 20 << ", " << 20 << ")\n";
    result = add.add(20,20); cout << "Result = " << result << "\n";

    cout << "Calling add.add(" << 35 << ", " << 5 << ")\n";
    result = add.add(35,5); cout << "Result = " << result << "\n";

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;
}

void complex_test(){
    Implementation::Channel_t dummy;
    unsigned char iid[1] = {0};
    Implementation::Add unified(dummy,dummy,iid);

    Add rtl(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    OStream cout;

    cout << "\nADD Complex Test\n\n";

    for (int i = 0; i < 8; ++i) {
        for (int j = 0; j < 8; ++j) {
            cout << "unified.add(" << i << ", " << j << ") = ";
            unsigned int data_uni = unified.add(i,j); cout << data_uni << "\n";
            cout << "rtl.add(" << i << ", " << j << ") = ";
            unsigned int data_rtl = rtl.add(i,j); cout << data_rtl << "\n";

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

int main()
{

    simple_test();

    return 0;
}
