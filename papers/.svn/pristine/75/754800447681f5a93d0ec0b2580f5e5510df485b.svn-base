    void Thread::reschedule(bool preempt)
    {
        ...
        Thread * prev = running();
        Thread * next = _scheduler.choose();

        unsigned long long n_l1_data_snooped = _perf.l1_data_cache_snooped();

        if(n_l1_data_snooped > 1000) {
            // move a thread to another core
            ...
        }

        dispatch(prev, next);
        ...
     }