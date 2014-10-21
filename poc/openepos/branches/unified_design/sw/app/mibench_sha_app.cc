/* NIST Secure Hash Algorithm */

#define MIBENCH_INPUT_SMALL

#include "mibench_sha/include/sha_data.h"
#include "mibench_sha/include/sha.h"

#include <machine.h>
#include <timer.h>
volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(System::Traits<System::EPOSSOC_Timer>::BASE_ADDRESS);

int main()
{
    System::OStream cout;
    cout << "\nRunning mibench-sha\n";
    System::Timer::stop();//stop system timer to avoid scheduler jitter
    unsigned int time = *TIMER_REG;
    //*((volatile unsigned int*)0xFFFFFFF8) = 0; //for virtual platform only

    mibench::SHA_INFO sha_info;

    mibench::sha_stream(&sha_info, mibench::mibench_file_data, mibench::MIBENCH_FILE_DATA_SIZE);
	mibench::sha_print(&sha_info);

	time = *TIMER_REG-time;

	cout << "\nFinished in " << time;
	//*((volatile unsigned int*)0xFFFFFFFC) = 0; //for virtual platform only

    return 0;
}
