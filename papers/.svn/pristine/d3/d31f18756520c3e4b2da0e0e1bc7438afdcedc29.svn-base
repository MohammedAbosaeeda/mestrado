	...
	int func0(void)
	{
		#ifndef __SEQUENTIAL
		Perf_Mon perf0;
		perf0.cpu_clk_unhalted_bus();
		#endif
		register unsigned int sum0;
		....
		
		#ifndef __SEQUENTIAL
		s.p();
		cout << "\nCPU unhalted bus func0 = " << perf0.get_cpu_clk_unhalted_bus() << "\n";
		s.v();
		#endif
	}
	...