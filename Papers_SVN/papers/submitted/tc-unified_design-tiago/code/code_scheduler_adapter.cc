template <typename T> class Scenario_Adapter<Scheduler<T> > :
  private
    IF<Traits<Scheduler<T> >::hardware,
       HW_Scenario<Scheduler<T> >,
       SW_Scenario<Scheduler<T> > >::Result,
  private
    Scheduler<T> 
{
...
  Link insert(T *obj, Scheduler<T>::Criterion rank) {
    Link link = Scenario::allocate(obj, rank);
    Scheduler<T>::insert(Scenario::get(link));
    return link;
  }

  T* remove(Link link) {
    T* obj = Scheduler<T>::remove(Scenario::get(link));   
    Scenario::free(link);
    return obj;
  }
...
};
