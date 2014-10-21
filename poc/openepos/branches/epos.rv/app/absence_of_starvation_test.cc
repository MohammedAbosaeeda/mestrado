/*
 * absence_of_starvation_test.cc
 *
 *  Created on: 2013-05-28
 *      Author: mateus
 */

/*
 * Using Traits<Absence_Of_Starvation_Traits>::STARVATION_DEADLINE
 *
 * (i) = NUMBER_OF_SCHEDULABLES * (Traits<Imp::Thread>::QUANTUM + CONTEXT_SWITCHING_TIME);
 * Usually, no starvation happen.
 *
 * (ii) = Traits<Imp::Thread>::QUANTUM;
 * Some starvations are expected (e.g. 4 starvations /100 threads).
 *
 * (iii) = 0;
 * All threads are expected to starve.
 *
 */

#include <utility/ostream.h>
#include <thread.h>
#include <alarm.h>

__USING_SYS

const RTC::Microsecond ONE_SECOND = 1000 * 1000;

const unsigned int MAX_APP_THREADS = 100;

void do_something(const RTC::Microsecond t);
int foo();

OStream cout;

Thread* threads[MAX_APP_THREADS];

int main()
{
	unsigned int num_threads;

	for (num_threads = 0;
			(num_threads < MAX_APP_THREADS) /* &&
			(Absence_Of_Starvation<Verified_Thread, true>::number_of_starvations() == 0)*/;
			num_threads++
		)
	{
		cout << "Creating Thread number: " << num_threads << "\n";
		threads[num_threads] = new Thread(num_threads, foo);
		// do_something(ONE_SECOND);
	}

	for (unsigned int i = 0; i < num_threads; i++)
	{
		threads[i]->join();
	}

	cout << "Number of Application Threads: " << num_threads << "\n";
	cout << "Number of Starvations: " << Absence_Of_Starvation<Verified_Thread, true>::number_of_starvations() << "\n";

	for (unsigned int i = 0; i < num_threads; i++)
	{
		delete threads[i];
	}
}


int foo()
{
	do_something(ONE_SECOND * 3000);

	return 0;
}


void do_something(const RTC::Microsecond t)
{
	for (unsigned int i = 0; i < t; i++);
}
