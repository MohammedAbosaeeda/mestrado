/*
 * scheduler_test.h
 *
 *  Created on: Jun 7, 2011
 *      Author: tiago
 */

#ifndef SCHEDULER_TEST_H_
#define SCHEDULER_TEST_H_

template<unsigned int C_MAX_THREADS, unsigned int C_DWIDTH>
class Scheduler_Test {

private:
	Testbench<C_MAX_THREADS, C_DWIDTH> *tb;

public:
	Scheduler_Test(Testbench<C_MAX_THREADS, C_DWIDTH> *_tb) {
		tb = _tb;
	}

	enum {
		CMD_CREATE = 0x01000000,
		CMD_DESTROY = 0x02000000,
		CMD_INSERT = 0x03000000,
		CMD_REMOVE = 0x04000000,
		CMD_REMOVE_HEAD = 0x05000000,
		CMD_UPDATE_RUNNING = 0x06000000,
		CMD_SET_QUANTUM = 0x07000000,
		CMD_ENABLE = 0x08000000,
		CMD_DISABLE = 0x09000000,
		CMD_INT_ACK = 0x0A000000,
		CMD_GETID = 0x0B000000,
		CMD_CHOSEN = 0x0C000000,
		CMD_SIZE = 0x0D000000,
		CMD_RSTICKS = 0x0E000000,
		CMD_INVALIDATE_RUNNING = 0x0F000000
	};

	//Status
	enum {
		STAT_RESCHEDULE = 0x00200000,
		STAT_ENABLE = 0x00100000,
		STAT_DONE = 0x00080000,
		STAT_FULL = 0x00040000,
		STAT_EMPTY = 0x00020000,
		STAT_ERROR = 0x00010000
	};

	int execute_cmd(int command, int prio, unsigned int parameter) {
		std::cout << "Sending param " << (void*)parameter << std::endl;
		tb->write_param(parameter);

		std::cout << "Sending cmd " << (void*)command << " and prio " << (void*)prio << std::endl;
		tb->write_cmd(command | (0x0000FFFF & prio));

		std::cout << "Reading Status" << std::endl;
		unsigned int status = tb->read_status();
		while (!((status & STAT_DONE) || (status & STAT_ERROR)))
			status = tb->read_status();

		print_status(status);

		int return_v = 0;
		if (status & STAT_ERROR)
			return_v = -1;
		else {
			std::cout << "Reading return val" << std::endl;
			return_v = tb->read_return();
		}

		std::cout << "CMD returned: " << (void*) return_v << std::endl;

		return return_v;
	}

	int enable() {
		std::cout << "Send enable" << std::endl;
		int result = execute_cmd(CMD_ENABLE, 0, 0);
		if (result == -1)
			std::cout << "ERROR\n" << std::endl;
		else
			std::cout << "OK\n" << std::endl;
		return result;
	}

	int disable() {
		std::cout << "Send disable" << std::endl;
		int result = execute_cmd(CMD_DISABLE, 0, 0);
		if (result == -1)
			std::cout << "ERROR\n" << std::endl;
		else
			std::cout << "OK\n" << std::endl;
		return result;
	}

	int set_quantum(unsigned int quantum_ticks) {
		std::cout << "Set up quantum" << std::endl;
		int result = execute_cmd(CMD_SET_QUANTUM, 0, quantum_ticks);
		if (result == -1)
			std::cout << "ERROR\n" << std::endl;
		else
			std::cout << "OK\n" << std::endl;
		return result;
	}

	/*
	int set_tid_bitmap(unsigned int val) {
		std::cout << "Set tid bitmap (returns previous val)" << std::endl;
		int result = execute_cmd(CMD_SET_TID_BITMAP, 0, val);
		if (result == -1)
			std::cout << "ERROR\n" << std::endl;
		else
			std::cout << "OK\n" << std::endl;
		return result;
	}
	*/

	int chosen() {
		std::cout << "Send chosen" << std::endl;
		int obj = execute_cmd(CMD_CHOSEN, 0, 0);
		if (obj == -1)
			std::cout << "ERROR\n" << std::endl;
		else
			std::cout << "OK\n" << std::endl;
		return obj;
	}

	void insert(int obj, int priority) {
		std::cout << "Scheduler insert" << std::endl;
		std::cout << "Send create" << std::endl;
		int tid = execute_cmd(CMD_CREATE, priority, obj);
		if (tid == -1)
			std::cout << "ERROR" << std::endl;
		else
			std::cout << "OK" << std::endl;

		if (tid > 0) {
			if (!chosen()) {
				std::cout << "Send update_running" << std::endl;
				tid = execute_cmd(CMD_UPDATE_RUNNING, priority, tid);
				if (tid == -1)
					std::cout << "ERROR" << std::endl;
				else
					std::cout << "OK" << std::endl;
			} else {
				std::cout << "Send insert" << std::endl;
				execute_cmd(CMD_INSERT, priority, tid);
				if (tid == -1)
					std::cout << "ERROR" << std::endl;
				else
					std::cout << "OK" << std::endl;
			}
		}
		//tmp = schtsc.time_stamp() - tmp;
		//kout << tmp << "\n";
		std::cout << "Scheduler insert finished\n" << std::endl;
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
};

#endif /* SCHEDULER_TEST_H_ */
