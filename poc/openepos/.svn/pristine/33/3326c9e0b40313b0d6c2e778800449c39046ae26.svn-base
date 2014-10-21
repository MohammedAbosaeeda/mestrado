// EPOS Scheduler Abstraction Declarations

#ifndef __scheduler_h
#define __scheduler_h

#include <tsc.h>
#include <rtc.h>
#include <machine.h>

#include <utility/maybe.h>
using Unified::Maybe;
#include <utility/list.h>
using Unified::Scheduling_List;
using Unified::List_Elements::Doubly_Linked_Scheduling_Value;
#include <utility/meta.h>
using Unified::IF;

#include <aspects/dynamic_alloc.h>
#include <aspects/static_alloc.h>
using Unified::Static_Allocation;

#include "../../components/include/sched.h"

__BEGIN_SYS

// SW-only scheduling criteria
namespace Scheduling_Criteria
{
    class Priority : public Unified::Scheduling_Criteria::Priority {
    public:
        Priority(int p = NORMAL): Unified::Scheduling_Criteria::Priority(p) {}
    };
    class Round_Robin : public Unified::Scheduling_Criteria::Round_Robin {
    public:
        Round_Robin(int p = NORMAL): Unified::Scheduling_Criteria::Round_Robin(p) {}
    };

    // First-Come, First-Served (FIFO)
    class FCFS: public Priority
    {
    public:
	enum {
	    MAIN   = 0,
	    NORMAL = 1,
	    IDLE   = (unsigned(1) << (sizeof(int) * 8 - 1)) -1
	};

	static const bool timed = false;
	static const bool preemptive = false;
	static const bool energy_aware = false;

    public:
	FCFS(int p = NORMAL)
	    : Priority((p == IDLE) ? IDLE : TSC::time_stamp()) {}
    };

    // Rate Monotonic
    class RM: public Priority
    {
    public:
	enum {
	    MAIN      = 0,
	    PERIODIC  = 1,
	    APERIODIC = (unsigned(1) << (sizeof(int) * 8 - 1)) -2,
	    NORMAL    = APERIODIC,
	    IDLE      = (unsigned(1) << (sizeof(int) * 8 - 1)) -1
	};

	static const bool timed = false;
	static const bool preemptive = true;
	static const bool energy_aware = false;

    public:
	RM(int p): Priority(p), _deadline(0) {} // Aperiodic
	RM(const RTC::Microsecond & d): Priority(PERIODIC), _deadline(d) {}

    private:
	RTC::Microsecond _deadline;
    };

    // Energy-aware Rate Monotonic
    class EARM: public RM
    {
    public:
	enum {
	    MAIN	= 0,
	    PERIODIC	= 1,
	    APERIODIC	= (unsigned(1) << (sizeof(int) * 8 - 2)) - 1,
	    IDLE	= (unsigned(1) << (sizeof(int) * 8 - 2)),
	    BEST_EFFORT	= (unsigned(1) << (sizeof(int) * 8 - 2)) + 1,
	    NORMAL	= APERIODIC
	};

	static const bool timed = true;
	static const bool preemptive = true;
	static const bool energy_aware = true;

    public:
	EARM(int p): RM(p) {}
	EARM(const RTC::Microsecond & d): RM(d) {}
    };

    // Earliest Deadline First
    class EDF: public Priority
    {
    public:
	enum {
	    MAIN      = 0,
	    PERIODIC  = 1,
	    APERIODIC = (unsigned(1) << (sizeof(int) * 8 - 1)) -2,
	    NORMAL    = APERIODIC,
	    IDLE      = (unsigned(1) << (sizeof(int) * 8 - 1)) -1
	};

	static const bool timed = false;
	static const bool preemptive = true;
	static const bool energy_aware = false;

    public:
	EDF(int p): Priority(p), _deadline(0) {} // Aperiodic
	EDF(const RTC::Microsecond & d): Priority(d >> 8), _deadline(d) {}

    private:
	RTC::Microsecond _deadline;
    };

    // CPU Affinity
    class CPU_Affinity: public Priority
    {
    public:
	static const unsigned int QUEUES = Traits<Machine>::MAX_CPUS;
  
    public:
	CPU_Affinity(int p = NORMAL): Priority(p),
	     _affinity(((p == IDLE) || (p == MAIN)) ? Machine::cpu_id()
		       : ++_next_cpu %= Machine::n_cpus()) {}

	CPU_Affinity(int p, int a): Priority(p), _affinity(a) {}

	const volatile int & queue() const volatile { return _affinity; }

	static int current() { return Machine::cpu_id(); }

    private:
	volatile int _affinity;

	static int _next_cpu;
    };
};

// Scheduling_Queue
template <typename T, typename Criterion, unsigned int Q>
class Scheduling_Queue
{
private:
    typedef Doubly_Linked_Scheduling_Value<T, Criterion> Element_Type;
    typedef Scheduling_List<T, Criterion, Element_Type> Queue;

public:
    typedef typename Queue::Element Element;

public:
    Scheduling_Queue() {}

    unsigned int size() { return _ready[Criterion::current()].size(); }

    Maybe<Element*> volatile chosen() {
        return _ready[Criterion::current()].chosen();
    }

    void insert(Element * e) {
        _ready[e->rank().queue()].insert(e);
    }

    Maybe<Element*> remove(Element * e) {
        // removing object instead of element forces a search and renders
        // removing inexistent objects harmless
        return _ready[e->rank().queue()].remove(e->object());
    }

    Maybe<Element*> choose() {
        return _ready[Criterion::current()].choose();
    }

    Maybe<Element*> choose_another() {
        return _ready[Criterion::current()].choose_another();
    }

    Maybe<Element*> choose(Element * e) {
        return _ready[e->rank().queue()].choose(e->object());
    }

private:
    Queue _ready[Q];
};

// Specialization for single-queue
template <typename T, typename Criterion>
class Scheduling_Queue<T, Criterion, 1>: public Scheduling_List<T, Criterion, Doubly_Linked_Scheduling_Value<T, Criterion> >
{
private:
    typedef Scheduling_List<T, Criterion, Doubly_Linked_Scheduling_Value<T, Criterion> > Base;

public:
    typedef typename Base::Element Element;

public:
    Maybe<Element*> remove(Element * e) {
        if (e) {
            Element* tmp = Base::remove(e);
            return Maybe<Element*>(tmp, tmp != 0);
        }
        else {
            return Maybe<Element*>();
        }
    }

    Maybe<Element*> choose() {
        return Base::choose();
    }

    Maybe<Element*> choose(Element * e) {
        return Base::choose(e->object());
    }
};


// Scheduler
// Objects subject to scheduling by Scheduler must declare a type "Criterion"
// that will be used as the scheduling queue sorting criterion (viz, through
// operators <, >, and ==) and must also define a method "link" to export the
// list element pointing to the object being handled.
//
// The second parameter is the allocator. The allocator is used to allocate the
// links used in the Scheduling_Queue.
template <typename T, typename Criterion = typename Traits<T>::Criterion >
class Scheduler: public Scheduling_Queue<T, Criterion, Criterion::QUEUES>
{
private:
    typedef Scheduling_Queue<T, Criterion, Criterion::QUEUES> Base;

public:
    typedef typename Base::Element Element;

    //typedef Static_Allocation<Element, unsigned int, 8> Allocator;
    typedef Dynamic_Allocation<Element, true> Allocator;
    typedef typename Allocator::Idx_Type Link;

public:
    Scheduler() :Base(), _allocator() {}

    void update_rank(Link link, int rank) {
        _allocator.get(link)->rank(rank);
    }

    int get_rank(Link link) {
        return _allocator.get(link)->rank();
    }

    unsigned int schedulables() { return Base::size(); }

    T chosen() {
        return Base::chosen().get()->object();
    }

    Link insert(T obj, Criterion rank) {
        Link link;
        _allocator.allocate(link, obj, rank);
        Base::insert(_allocator.get(link));
        return link;
    }

    T remove(Link link) {
        Maybe<Element*> elem = Base::remove(_allocator.get(link));
        T tmp = elem.exists() ? elem.get(_allocator.null())->object() : (T)0;
        _allocator.free(link);
        return tmp;
    }

    void suspend(Link link) {
        Base::remove(_allocator.get(link));
    }

    void resume(Link link) {
        Base::insert(_allocator.get(link));
    }

    T choose() {
        T obj = Base::choose().get()->object();
        return obj;
    }

    T choose_another() {
        T obj = Base::choose_another().get()->object();
        return obj;
    }

    T choose(Link link) {
        Maybe<Element*> elem = Base::choose(_allocator.get(link));
        return elem.exists() ? elem.get(_allocator.null())->object() : (T)0;
    }

private:
    Allocator _allocator;

};

__END_SYS

#endif
