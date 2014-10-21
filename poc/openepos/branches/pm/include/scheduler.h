// EPOS-- Scheduler Abstraction Declarations

#ifndef __scheduler_h
#define __scheduler_h

#include <utility/queue.h>
#include <rtc.h>
#include <tsc.h>
#include <alarm.h>
#include <scheduler.h>  // mudar para criterion.h
#include <battery_lifetime.h>

__BEGIN_SYS

// All scheduling criteria, or disciplins, must define operator int() with 
// semantics of returning the desired order of a given object in the 
// scheduling list
namespace Scheduling_Criteria
{
    enum { INFINITE = -1 };

    // Priority (static and dynamic)
    class Priority
    {
    public:
	enum {
	    MAIN   = 0,
	    HIGH   = 1,
	    NORMAL =  (unsigned(1) << (sizeof(int) * 8 - 1)) -3,
	    LOW    =  (unsigned(1) << (sizeof(int) * 8 - 1)) -2,
	    IDLE   = (unsigned(1) << (sizeof(int) * 8 - 1)) -1
	};

    public:
	Priority(int p = NORMAL): _priority(p) {
	db<Priority>(TRC) << "Priority::Priority(" << p << ")\n";
	}

	operator const volatile int() const volatile { return _priority; }

    public:
	void priority(int p) {_priority = p;}

    protected:
	volatile int _priority;
    };

    class PriorityLong
    {
    public:
	enum {
	    MAIN   = 0,
	    HIGH   = 1,
	    NORMAL =  (unsigned(1) << (sizeof(long) * 8 )) - 3, 
	    LOW    =  (unsigned(1) << (sizeof(long) * 8 )) - 2,
	    IDLE   = (unsigned(1) << (sizeof(long) * 8 )) - 1
	};

    public:
	PriorityLong(unsigned long p = NORMAL): _priority(p) {

	
	db<Priority>(TRC) << "PriorityLong::Priority(" << p << ")\n";
	}

	operator const volatile unsigned long() const volatile { return _priority; }

    public:
	void priority(unsigned long p) {_priority = p;}

        int times() { return 0; }
        RTC::Microsecond phase() { return 0; }
        RTC::Microsecond period() { return 0; }

    protected:
	volatile unsigned long _priority;
    };



    // Round-Robin
    class Round_Robin: public Priority
    {
    public:
	enum {
	    MAIN   = 0,
	    NORMAL = 1,
	    IDLE   = (unsigned(1) << (sizeof(int) * 8 - 1)) -1
	};

    public:
	Round_Robin(int p = NORMAL): Priority(p) {}
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

    public:
	FCFS(int p = NORMAL): Priority((p == IDLE) ? IDLE : TSC::time_stamp()) {}
    };


    // Super class for real-time
    class RealTime: public PriorityLong
    {
    public:
	RealTime(unsigned long p): PriorityLong(p), _relDeadline(INFINITE) {} // aperiodic non real-time
	RealTime(unsigned long p, const RTC::Microsecond & deadline): PriorityLong(p), _relDeadline(deadline) {/*db<RealTime>(TRC) << "RealTime::RealTime(" << p << "," << deadline << ")\n";*/} // Aperiodic real-time

    public:
	bool operator <=(const RealTime & other) const {return (unsigned long)*this < (unsigned long)other;}

    public:
       RTC::Microsecond deadline() {return _relDeadline;}
    private:
       RTC::Microsecond _relDeadline;
    };

    // Super class for periodic thread
    class Periodic: public RealTime
    {
    public:
	Periodic(unsigned long p): RealTime(p), _period(INFINITE), _times(0) {} // Aperiodic non real-time
	Periodic(unsigned long p, const RTC::Microsecond & deadline): RealTime(p,deadline), _period(INFINITE), _times(0) {} // Aperiodic real-time
	Periodic(unsigned long p, const RTC::Microsecond & deadline, const RTC::Microsecond & period, const int times): RealTime(p, deadline), _period(period), _times(times) {/*	db<Periodic>(TRC) << "Periodic::Periodic(" << p << "," << deadline << "," << period << "," << times << ")\n";*/} //periodic real-time
    public:
       RTC::Microsecond period() {return _period;}
       int times() {return _times;}
       void times(int t) {_times = t;}
    private:
       RTC::Microsecond _period;
       int _times;
    };


    // Rate Monotonic
    class RM: public Periodic
    {
    public:
	enum {
	    MAIN      = 0,
	    PERIODIC  = 1,
	    APERIODIC = (unsigned(1) << (sizeof(int) * 8 - 1)) -2,
	    NORMAL    = APERIODIC,
	    IDLE      = (unsigned(1) << (sizeof(int) * 8 - 1)) -1
	};

    public:
	RM(int p): Periodic(p) {} // Aperiodic non real-time
	RM(const RTC::Microsecond & period, const int times): Periodic(period,period,period,times) {} //periodic real-time
    private:
    };

    // Earliest Deadline First
    class EDF: public Periodic
    {
    public:
	enum {
	    MAIN      = 0,
	    PERIODIC  = 1,
	    APERIODIC = (unsigned(1) << (sizeof(long) * 8 )) - 2,
	    NORMAL    = APERIODIC,
	    IDLE   = (unsigned(1) << (sizeof(long) * 8 )) - 1
	};

    public:
	EDF(unsigned long p): Periodic(p),_activations(0) {/*kout << "EDF Contrutor\n";*/} // Aperiodic non real-time

//grw	EDF(const EDF &tmp): Periodic(tmp){ kout << "EDF Construtor de copia\n";}


        //EDF(const RTC::Microsecond & deadline):Periodic(deadline, deadline, INFINITE,0), _phase(0), _activations(0) { db<EDF>(TRC) << "EDF::EDF(" <<  deadline << ")\n"; } // aperiodic real-time
        
	EDF(const RTC::Microsecond & deadline, const RTC::Microsecond & phase):Periodic(Alarm::ticks(phase+deadline), deadline, INFINITE,0), _phase(phase), _activations(0) { db<EDF>(TRC) << "EDF::EDF(" <<  deadline << ")\n"; } // aperiodic real-time

	EDF(const RTC::Microsecond & deadline, const RTC::Microsecond & period, const int times, const RTC::Microsecond & phase):Periodic(Alarm::ticks(phase+deadline), deadline, period,times), _phase(phase), _activations(0) {
		db<EDF>(TRC) << "EDF::EDF(tnow="<< Alarm::time(Alarm::elapsed()) << " deadline=" << deadline << " period=" << period << " times=" << times << " phase=" << phase <<  " absolutdeadline=" << phase+deadline << ")\n";
		} // periodic real-time
   
    public:
	//Sobrecarga de operador Adicao 
	const EDF operator+(const EDF & crit) const{ 
		EDF tmp(*this);
		tmp.priority((unsigned long)*this + (unsigned long)crit);
		return tmp;
	}

	//Sobrecarga de operador Subtracao
	const EDF operator-(const EDF & crit) const{ 
		EDF tmp(*this);
		tmp.priority((unsigned long)*this - (unsigned long)crit);
		return tmp;
	}

	void operator-=(const EDF & crit) { 
		if(!(unsigned long)*this){ 
			return;
		}
		if((unsigned long)*this <= (unsigned long)crit){
			this->priority(0);
		} else {
			this->priority((unsigned long)*this - (unsigned long)crit);
		}
	}

	void operator+=(const EDF & crit) { 
		this->priority((unsigned long)*this + (unsigned long)crit);
	}

    public:
        RTC::Microsecond phase() {return _phase;}
        unsigned int activations() {return _activations;}
        void activations(unsigned int activations) {_activations = activations;}
    private:
	 RTC::Microsecond _phase;
         unsigned int _activations;
    };

/*
	// Imprecise Computation
    class Imprecise: public EDF
    {
    public:
	enum {
	    MAIN      = 0,
	    PERIODIC  = 1,
	    ENERGY = (unsigned(1) << (sizeof(long) * 8 )) - 3,
	    APERIODIC = (unsigned(1) << (sizeof(long) * 8 )) - 2,
	    NORMAL    = APERIODIC,
	    IDLE      = (unsigned(1) << (sizeof(long) * 8 )) - 1
	};

    public:
	Imprecise(unsigned long p): EDF(p) {} // Aperiodic non real-time
	Imprecise(const RTC::Microsecond & deadline, const RTC::Microsecond & phase): EDF(deadline, phase) { db<Imprecise>(TRC) << "Imprecise::Imprecise(" <<  deadline << ")\n"; } // aperiodic real-time
        
	Imprecise(const RTC::Microsecond & deadline, const RTC::Microsecond & period, const int times, const RTC::Microsecond & phase): EDF(deadline,period,times,phase) {
		db<Imprecise>(WRN) << "Imprecise::Imprecise(tnow="<< Alarm::time(Alarm::elapsed()) << " deadline=" << deadline << " period=" << period << " times=" << times << " phase=" << phase <<  " absolutdeadline=" << phase+deadline << ")\n";
		} // periodic real-time
    };

*/
};



namespace Scheduling_Scheduler {

// Objects subject to scheduling by Scheduler must declare a type "Criterion"
// that will be used as the scheduling criterion (viz, through operators <, >,
// and ==) and must also define a method "link" to access the list element
// pointing to the object.
template <typename T>
class Scheduler {
protected:
    typedef typename T::Criterion Rank_Type;

    static const bool smp = Traits<Thread>::smp;

    typedef Scheduling_Queue<T, Rank_Type, smp> Queue;

public:
    typedef T Object_Type;
    typedef typename Queue::Element Element;

public:
    Scheduler() {}

    unsigned int schedulables() { return _ready.size(); }

    T * volatile  chosen() { 
	return const_cast<T * volatile>(_ready.chosen()->object()); 
    }

    void insert(T * obj) {
	db<Scheduler>(TRC) << "Scheduler[chosen=" << chosen()
			   << "]::insert(" << obj << ")\n";
	_ready.insert(obj->link()); 
    }

    T * remove(T * obj) {
	db<Scheduler>(TRC) << "Scheduler[chosen=" << chosen()
			   << "]::remove(" << obj << ")\n";
	return _ready.remove(obj) ? obj : 0;
    }

    void suspend(T * obj) {
	db<Scheduler>(TRC) << "Scheduler[chosen=" << chosen()
			   << "]::suspend(" << obj << ")\n";
	_ready.remove(obj);
// 	_suspend.insert(obj->link());
    }

    void resume(T * obj) {
	db<Scheduler>(TRC) << "Scheduler[chosen=" << chosen() 
			   << "]::resume(" << obj << ")\n";
// 	_suspended.remove(obj->link());
	_ready.insert(obj->link());
    }

    T * choose() {
	db<Scheduler>(TRC) << "Scheduler[chosen=" << chosen()
			   << "]::choose() => ";
	T * obj = _ready.choose()->object();

	db<Scheduler>(TRC) << obj << "\n";
	return obj;
    }

    T * choose_another() {
	db<Scheduler>(TRC) << "Scheduler[chosen=" << chosen()
			   << "]::choose_another() => ";
	T * obj = _ready.choose_another()->object();
	db<Scheduler>(TRC) << obj << "\n";
	return obj;
    }

    T * choose(T * obj) {
	db<Scheduler>(TRC) << "Scheduler[chosen=" << chosen() 
			   << "]::choose(" << obj;
	if(!_ready.choose(obj))
	    obj = 0;
	db<Scheduler>(TRC) << ") => " << obj << "\n";
	return obj;
    }

protected:
    Scheduling_Queue<Object_Type, Rank_Type, smp> _ready;
//     Queue<Object_Type, smp, Element> _suspended;
};



template <typename T>
class Scheduler_EDF : public Scheduler<T> {
protected:

    typedef Scheduler<T> Base;

    typedef typename Base::Rank_Type Rank_Type;
    
    static const bool smp = Base::smp;

    typedef Scheduling_Queue<T, Rank_Type, smp, true> RelativeQueue;

public:
    typedef T Object_Type;

    typedef typename RelativeQueue::Element RelativeElement;

public:
    Scheduler_EDF() : _periodic(true) {}

    unsigned int only_edf_schedulables() { return _relativeReady.size(); }

    unsigned int schedulables() { return (only_edf_schedulables() + Base::schedulables()); }

    T * volatile  chosen() { 
	if(_periodic && only_edf_schedulables())
		return const_cast<T * volatile>(_relativeReady.chosen()->object()); 
	return Base::chosen();
    }

    void insert(T * obj) {
        if((unsigned long)(obj->link()->rank()) < Rank_Type::NORMAL){
		db<Scheduler_EDF>(TRC) << "Scheduler_EDF[chosen=" << chosen()
			   << "]::insert(" << obj << ")\n";
		Timer::Tick now = Alarm::elapsed();
		if(only_edf_schedulables()){ 
			T * head = const_cast<T * volatile>(_relativeReady.head()->object());
			head->link()->promote((unsigned long)(now - _timeElapsed));
		}
		_timeElapsed = now;
		_relativeReady.insert(obj->link()); 
	} else {
		Base::insert(obj);
	}
    }

    T * remove(T * obj) {
	if((unsigned long)(obj->link()->rank()) < Rank_Type::NORMAL){
		db<Scheduler_EDF>(TRC) << "Scheduler_EDF[chosen=" << chosen()
			   << "]::remove(" << obj << ")\n";
		return _relativeReady.remove(obj) ? obj : 0;
	} else {
		return Base::remove(obj);
	}
    }

    void suspend(T * obj) {
	if((unsigned long)(obj->link()->rank()) < Rank_Type::NORMAL){
		db<Scheduler_EDF>(TRC) << "Scheduler_EDF[chosen=" << chosen()
			   << "]::suspend(" << obj << ")\n";
		_relativeReady.remove(obj);
		Rank_Type crit(obj->link()->rank());
		crit.priority(Alarm::ticks(crit.deadline()));
		obj->link()->rank(crit);
	} else {
		Base::suspend(obj);
	}	
    }

    void resume(T * obj) {
	if((unsigned long)(obj->link()->rank()) < Rank_Type::NORMAL){
		db<Scheduler_EDF>(TRC) << "Scheduler_EDF[chosen=" << chosen() 
			   << "]::resume(" << obj << ")\n";
		Timer::Tick now = Alarm::elapsed();
		if(only_edf_schedulables()){ 
			T * head = const_cast<T * volatile>(_relativeReady.head()->object());
			head->link()->promote((unsigned long)(now - _timeElapsed));
		}
		_timeElapsed = now;
		_relativeReady.insert(obj->link());
	} else {
		Base::resume(obj);
	}
    }

    T * choose() {
	if(only_edf_schedulables()){
		db<Scheduler_EDF>(TRC) << "Scheduler_EDF[chosen=" << chosen()
			   << "]::choose() => ";
		T * obj = _relativeReady.choose()->object();

		db<Scheduler_EDF>(TRC) << obj << "\n";
		_periodic = true;
		return obj;
	} else {
		_periodic = false;
		return Base::choose();
	}
    }

    T * choose_another() {
	if((!_periodic && only_edf_schedulables()) || (_periodic && only_edf_schedulables() > 1)){
		db<Scheduler_EDF>(TRC) << "Scheduler_EDF[chosen=" << chosen()
			   << "]::choose_another() => ";
		T * obj;
		if(_periodic){
			obj = _relativeReady.choose_another()->object();
		} else {
			_periodic = true;
			obj = _relativeReady.choose()->object();	
		}		
		db<Scheduler_EDF>(TRC) << obj << "\n";
		return obj;
	} else { 	
		if(_periodic){
			_periodic = false;
			return Base::choose();
		}	
		return Base::choose_another();
	}
    }

    T * choose(T * obj) {
	if((unsigned long)(obj->link()->rank()) < Rank_Type::NORMAL){
		db<Scheduler_EDF>(TRC) << "Scheduler_EDF[chosen=" << chosen() 
			   << "]::choose(" << obj;
		if(!_relativeReady.choose(obj))
		    obj = 0;
		db<Scheduler_EDF>(TRC) << ") => " << obj << "\n";
		//TODO: Necessidade de verificar se a thread foi selecionada
		_periodic = true;
		return obj;
	} else {
		_periodic = false;
		return Base::choose();
	}
    }

protected:
    Timer::Tick _timeElapsed;
    bool _periodic;
    RelativeQueue _relativeReady;
};


template <typename T>
class Scheduler_Imprecise: public Scheduler_EDF<T> 
{
protected:

    typedef Scheduler_EDF<T> Base;

    typedef typename Base::Rank_Type Rank_Type;

public:
	Scheduler_Imprecise(): _lifetime(Traits<Battery_Lifetime>::lifetime), _energy(false) {
		db<Scheduler_Imprecise>(TRC) << "Scheduler_Imprecise::Scheduler_Imprecise()\n"; }

public:
	T * has_energy(T * obj)
	{
		_energy = false;
		if(!_lifetime.has_energy() && (unsigned long)(obj->link()->rank()) >= Rank_Type::NORMAL){
           		_energy = true;
           		db<Scheduler_Imprecise>(TRC) << "Scheduler !has_energy => ";
           		return _energy_t;
		}
		return obj;
	}

	T * volatile chosen()
	{
		if(_energy)
			return _energy_t;
		return Base::chosen();
	}

	
	void insert(T * obj)
	{
		db<Scheduler_Imprecise>(TRC) << "Scheduler_Imprecise[chosen=" << 
			chosen() << "]::insert(" << obj << ")\n";
		if((unsigned long)(obj->link()->rank()) == Rank_Type::IDLE){
			_energy_t = obj;
			_lifetime.init();
		}
		Base::insert(obj);
	}

	T * choose()
	{
		db<Scheduler_Imprecise>(TRC) << "Scheduler_Imprecise[chosen=" << 
			chosen() << "]::choose() => ";
		T * obj = has_energy(Base::choose());
		db<Scheduler_Imprecise>(TRC) << obj << "\n";

		return obj;
	}

	T * choose_another()
	{
		db<Scheduler_Imprecise>(TRC) << "Scheduler_Imprecise[chosen=" << 
			chosen() << "]::choose_another() => ";
		T * obj = has_energy(Base::choose_another());
		db<Scheduler_Imprecise>(TRC) << obj << "\n";
		return obj;
	}

    	T * choose(T * obj) 
	{
		return Base::choose(obj);
	}
	
	private:
		Battery_Lifetime _lifetime;
		bool _energy;
		T * _energy_t;
};

};

__END_SYS

#endif
