/*
 * time_master.h
 *
 *  Created on: Dec 3, 2011
 *      Author: mateus
 */

#ifndef __time_master_h
#define __time_master_h

namespace System {

class TimeMaster
{

public:

	static void wait()
	{
		for(int i = 0; i < 0xffff; i++);
	}


	static void wait_more(int m)
	{
		for(int i = 0; i < m; i++) {
			wait();
		}
	}

};


}


#endif
