// EPOS-- Power Management Utility Implementation

#include <system/config.h>
#include <utility/power_manager.h>
#include <tsc.h>

__BEGIN_SYS

namespace Power_Manager_Xunxo
{
	Power_Manager_Instances_List_Holder::ListP Power_Manager_Instances_List_Holder::instances;

	unsigned long long GetTimeStamp()
	{
		return TSC::time_stamp();
	}
};

__END_SYS
