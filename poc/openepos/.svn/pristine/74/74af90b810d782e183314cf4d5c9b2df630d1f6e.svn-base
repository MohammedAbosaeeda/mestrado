// EPOS-- Scheduler Abstraction Declarations

#ifndef __scheduler_h
#define __scheduler_h

#include "queue.h"
#include <iostream>

#include "../bus_model.h"

// All scheduling criteria, or disciplins, must define operator int() with 
// semantics of returning the desired order of a given object in the 
// scheduling list
namespace Scheduling_Criteria
{
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
	Priority(int p = NORMAL): _priority(p) {}

	operator const volatile int() const volatile { return _priority; }

    protected:
	volatile int _priority;
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
};

class Thread {
public:
	typedef Scheduling_Criteria::Priority Criterion;

	Criterion _critetion;
	int _id;

	Thread() :_critetion(0), _id(0){}

	Criterion criterion(){return _critetion;}
	void criterion(Criterion _crit){_critetion = _crit;}

	int id(){return _id;}
	void id(int _i){_id = _i;}

};

template<unsigned int C_MAX_THREADS, unsigned int C_DWIDTH>
class EPOS_Scheduler
{
protected:
	typedef Thread T;
    typedef typename Thread::Criterion Rank_Type;
    typedef Scheduling_Queue<Thread, Rank_Type, false> Queue;

    //Commands
    enum {
      CMD_CREATE         = 0x01000000,
      CMD_DESTROY        = 0x02000000,
      CMD_INSERT         = 0x03000000,
      CMD_REMOVE         = 0x04000000,
      CMD_REMOVE_HEAD    = 0x05000000,
      CMD_UPDATE_RUNNING = 0x06000000,
      CMD_SET_QUANTUM    = 0x07000000,
      CMD_ENABLE         = 0x08000000,
      CMD_DISABLE        = 0x09000000,
      CMD_INT_ACK        = 0x0A000000,
      CMD_GETID          = 0x0B000000,
      CMD_CHOSEN         = 0x0C000000,
      CMD_SIZE		 	 = 0x0D000000,
      CMD_RSTICKS	 	 = 0x0E000000
    };

    //Status
    enum {
      STAT_RESCHEDULE	 = 0x00200000,
      STAT_ENABLE 	 	 = 0x00100000,
      STAT_DONE          = 0x00080000,
      STAT_FULL          = 0x00040000,
      STAT_EMPTY         = 0x00020000,
      STAT_ERROR         = 0x00010000,
    };

    static const int STAT_ERROR_RETURN  = 0xCAFECAF1;

public:
    typedef T Object_Type;
    typedef typename Queue::Element Element;

public:
    EPOS_Scheduler(Bus_Model<C_MAX_THREADS, C_DWIDTH> *model) :tb(model) {}

    unsigned int schedulables() {
    	return execute_cmd(CMD_SIZE, 0, 0);
    }

    Thread* chosen() {
    	//std::cout << "EPOS_Scheduler::chosen()\n";
    	T * obj = (T*)execute_cmd(CMD_CHOSEN, 0, 0);
    	//std::cout << "EPOS_Scheduler::chosen() = " << (void*)obj << "\n";
    	return const_cast<T * volatile>(obj);
    }

    void insert(T * obj) {
    	//std::cout << "EPOS_Scheduler::insert("<< (void*)obj << ")\n";
    	int priority = obj->criterion();
    	int tid = execute_cmd(CMD_CREATE, priority, (size_t)obj);
    	if (tid != STAT_ERROR_RETURN) {
    		if(!chosen()) {
    			execute_cmd(CMD_UPDATE_RUNNING, priority, tid);
    		} else {
    			execute_cmd(CMD_INSERT, priority, tid);
    		}
    	}
    }

    T * remove(T * obj) {
    	//std::cout << "EPOS_Scheduler::remove("<< (void*)obj << ")\n";
		int tid = execute_cmd(CMD_GETID, 0, (size_t)obj);
		if(tid == STAT_ERROR_RETURN) {
			obj = 0;
		} else {
			//return 0; //Thread not found!

			T * running = chosen();
	        execute_cmd(CMD_DESTROY, 0, tid); //Destroy thread

	        if(obj == running){
		   		//REMOVE HEAD AND UPDATE RUNNING
		   		running = (T*) execute_cmd(CMD_REMOVE_HEAD, 0, 0);
		   		tid = execute_cmd(CMD_GETID, 0, (size_t)running);
		   		execute_cmd(CMD_UPDATE_RUNNING, 0, tid);
			}
	    }
		//std::cout << "EPOS_Scheduler::remove(...) = "<< (void*)obj << "\n";
		return obj;
    }

    T * choose() {
    	//std::cout << "EPOS_Scheduler::choose()\n";

		//Insert Running
		T * obj = chosen();

		int obj_tid = execute_cmd(CMD_GETID, 0, (size_t)obj);
		execute_cmd(CMD_INSERT, obj->criterion(), obj_tid);
	
		//Get REMOVE HEAD
		obj = (T *)execute_cmd(CMD_REMOVE_HEAD, 0, 0);
		obj_tid = execute_cmd(CMD_GETID, 0, (size_t)obj);

		//Set HEAD Running
		execute_cmd(CMD_UPDATE_RUNNING, 0, obj_tid);

		//std::cout << "EPOS_Scheduler::choose() = " << (void*)obj << "\n";
		return obj;
    }

    T * choose_another() {
    	//std::cout << "EPOS_Scheduler::choose_another()\n";

		//Remove HEAD
		T * obj = (T *)execute_cmd(CMD_REMOVE_HEAD, 0, 0);

		//Insert Running
		T * running = chosen();
		int rtid = execute_cmd(CMD_GETID, 0, (size_t)running);
		execute_cmd(CMD_INSERT, running->criterion(), rtid);

		//Update Running
		execute_cmd(CMD_UPDATE_RUNNING, 0, execute_cmd(CMD_GETID, 0, (size_t)obj));

		//std::cout << "EPOS_Scheduler::choose_another() = " << (void*)obj << "\n";
		return obj;
    }

    T * choose(T * obj) {
    	//std::cout << "EPOS_Scheduler::choose("<< (void*)obj << ")\n";

		//PROCURA OBJETO
		int obj_id = execute_cmd(CMD_GETID, 0, (size_t)obj);
		if(obj_id == STAT_ERROR_RETURN) {
			obj = 0;
		} else {
			//REMOVE OBJ
			if(!execute_cmd(CMD_REMOVE, 0, obj_id)) {
				obj = 0; //obj nao esta na fila! 
			} else {
				T * running = chosen();
				int rtid = execute_cmd(CMD_GETID, 0, (size_t)running);
				execute_cmd(CMD_INSERT, running->criterion(), rtid);
				// Seta running
				execute_cmd(CMD_UPDATE_RUNNING, 0, obj_id);
			}
		}

		//std::cout << "EPOS_Scheduler::choose(...) = " << (void*)obj << "\n";
		return obj;
    }

    void reset_quantum(){
    	//std::cout << "EPOS_Scheduler::reset_quantum()\n";
    	execute_cmd(CMD_RSTICKS, 0, 0);
    }

    void init(){
    	//std::cout << "EPOS_Scheduler::init()\n";
    	execute_cmd(CMD_DISABLE, 0, 0);

    	//Set-up Quantum
    	unsigned int quantum_ticks = 1000;
    	execute_cmd(CMD_SET_QUANTUM, 0, quantum_ticks);

    	execute_cmd(CMD_ENABLE, 0, 0);
    }

private:
    int execute_cmd(int command, int prio, unsigned int parameter){
    	//std::cout << "Sending param " << (void*)parameter << std::endl;
    	tb->write_param(parameter);

    	//std::cout << "Sending cmd " << (void*)command << " and prio " << (void*)prio << std::endl;
    	tb->write_cmd(command | (0x0000FFFF & prio));

    	//std::cout << "Reading Status" << std::endl;
    	unsigned int status = tb->read_status();
    	while (!((status & STAT_DONE) || (status & STAT_ERROR)))
    		status = tb->read_status();

    	//print_status(status);

    	int return_v = 0;
    	if (status & STAT_ERROR){
    		std::cout << "-------\nexecute_cmd error" << std::endl;
    		print_status(status);
    		std::cout << "-------" << std::endl;
    		return_v = STAT_ERROR_RETURN;
    	}
    	else {
    		//std::cout << "Reading return val" << std::endl;
    		return_v = tb->read_return();
    	}

    	//std::cout << "CMD returned: " << (void*) return_v << std::endl;

    	return return_v;
    }

    void print_status(unsigned int status){
    	std::cout << "Status: ";
    	if(status & STAT_ERROR)
    		std::cout << "ERROR & ";
    	if(status & STAT_DONE)
    		std::cout << "DONE & ";
    	if(status & STAT_RESCHEDULE)
    		std::cout << "BLOCK & ";
    	if(status & STAT_ENABLE)
    		std::cout << "RESUME & ";
    	if(status & STAT_FULL)
    		std::cout << "FULL & ";
    	if(status & STAT_EMPTY)
    		std::cout << "EMPTY & ";
    	std::cout << std::endl;
    }

public:
	Bus_Model<C_MAX_THREADS, C_DWIDTH> *tb;

};


#endif
