    ...
    Semaphore s;
    ...
    int func0(void)
    {
	    #ifndef __SEQUENTIAL
	    Perf_Mon perf0;
	    perf0.l1_data_cache_snooped();
	    #endif
	    register unsigned int sum0;
	    ....
		
	    #ifndef __SEQUENTIAL
	    s.p();
	    cout << "\nL1 data cache snooped func0 = " << perf0.get_l1_data_cache_snooped() << "\n";
	    s.v();
	    #endif
    }
    ...