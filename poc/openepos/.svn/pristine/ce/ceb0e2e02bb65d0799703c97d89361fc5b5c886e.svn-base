#include <utility/ostream.h>
#include <display.h>
#include <utility/bheap.h>
#include <utility/list.h>
#include <scheduler.h>
#include <rtc.h>

__USING_SYS

OStream cout;
Display d;

#define N 10

typedef  Scheduling_Criteria::GEDF Crit;
typedef Scheduling_BHeap<int, Crit> Sched_Heap;
typedef Scheduling_List<int, Crit> Sched_List;

int main()
{
  d.clear();
  cout << "BHeap test\n";
 
  Sched_Heap bheap;
  int o[N];
  Sched_List l;
  Sched_Heap::Element * e[N];
  Sched_List::Element * se[N];
  
  for(int i = 0; i < N; i++) {
      o[i] = i;
      RTC::Microsecond m = i * 100;
      e[i] = new Sched_Heap::Element(&o[i], Crit(i, 1));
      se[i] = new Sched_List::Element(&o[i], Crit(i, 1));
      bheap.insert(e[i]);
      l.insert(se[i]);
      //cout << "Inserted elem = " << *e[i]->object() << " BHeap head() = " << *bheap.head()->object() 
      //<< " List head = " << *l.head()->object() << "\n";
      //cout << "The BHeap has now " << bheap.size() << " elements\n";
      //cout << "The list has now " << l.size() << " elements\n";
  }
  cout << "Heap head = " << bheap.head()->object() << "\n";
  cout << "List head = " << l.head()->object() << "\n";
  
  for(int i = 0; i < bheap.size(); i++)
    cout << *bheap.value(i)->object() << ",";
  cout << "\n";
  for(Sched_List::Iterator i = l.begin(); i != l.end(); i++) {
    cout << *i->object();
    if(Sched_List::Iterator(i->next()) != l.end())
        cout << ",";
    }
    cout << "\n";
  
  /*bheap.remove(bheap.head());
  l.remove(l.head());
  cout << "Heap head = " << *bheap.head()->object() << "\n";
  cout << "List head = " << *l.head()->object() << "\n";
  */
  
  //Scheduling_BHeap<int>::Element *elem = bheap.search(&o[3]);
  //cout << "Elem = " << *elem->object() << "\n";
  
  cout << "Scheduling the bheap => " << *bheap.choose()->object() << "\n";
  cout << "Scheduling the list => " << *l.choose()->object() << "\n";
  
  for(int i = 0; i < bheap.size(); i++)
    cout << *bheap.value(i)->object() << ",";
  cout << "\n";
  /*
  for(Scheduling_List<int>::Iterator i = l.begin(); i != l.end(); i++) {
    cout << *i->object();
    if(Scheduling_List<int>::Iterator(i->next()) != l.end())
        cout << ",";
    }
    cout << "\n";     */
    
  cout << "BHEAP Forcing scheduling of another element => " <<
    *bheap.choose_another()->object() << "\n";
  cout << "LIST Forcing scheduling of another element => " <<
    *l.choose_another()->object() << "\n";  
   
  cout << "BHeap Forcing scheduling of element whose value is " << o[N/2] << " => " 
     << *bheap.choose(e[N/2])->object() << "\n";    
  cout << "List Forcing scheduling of element whose value is " << o[N/2] << " => " 
     << *l.choose(se[N/2])->object() << "\n";  
  
  for(int i = 0; i < bheap.size(); i++)
    cout << *bheap.value(i)->object() << ",";
  cout << "\n";
  for(Sched_List::Iterator i = l.begin(); i != l.end(); i++) {
    cout << *i->object();
    if(Sched_List::Iterator(i->next()) != l.end())
        cout << ",";
    }
    cout << "\n";   
     
  cout << "Removing the bheaps's head => " << *bheap.remove(bheap.choose())->object()
     << "\n";    
  cout << "Removing the list's head => " << *l.remove(l.choose())->object()
     << "\n";   
 
  cout << "BHeap Removing the element whose value is " << o[N/4] << " => " 
     << *bheap.remove(e[N/4])->object() << "\n";   
  cout << "List Removing the element whose value is " << o[N/4] << " => " 
     << *l.remove(se[N/4])->object() << "\n";   
  for(int i = 0; i < bheap.size(); i++)
    cout << *bheap.value(i)->object() << ",";
  cout << "\n";
  for(Sched_List::Iterator i = l.begin(); i != l.end(); i++) {
    cout << *i->object();
    if(Sched_List::Iterator(i->next()) != l.end())
        cout << ",";
    }
    cout << "\n";
    
    while(bheap.size() > 0) {
    cout << *bheap.remove(bheap.choose())->object();
    if(bheap.size() > 0)
        cout << ", ";
    }
    cout << "\n";
    cout << "The BHeap has now " << bheap.size() << " elements\n";
    for(int i = 0; i < N; i++)
    delete e[i];
    
    while(l.size() > 0) {
    cout << *l.remove(l.choose())->object();
    if(l.size() > 0)
        cout << ", ";
    }
    cout << "\n";
    cout << "The list has now " << l.size() << " elements\n";
    for(int i = 0; i < N; i++)
    delete se[i];
  
  while(1);
  return 0;
}