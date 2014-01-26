template <> struct Traits<Scheduler<Thread> > {
  typedef Scheduling_Criteria::Priority Criterion;
};
