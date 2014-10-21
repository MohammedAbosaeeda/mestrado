/*
 * priority_linked_list.h
 *
 *  Created on: Feb 9, 2011
 *      Author: tiago
 */

#ifndef PRIORITY_LINKED_LIST_H_
#define PRIORITY_LINKED_LIST_H_

#include <systemc.h>

template<unsigned int IDX_SIZE = 4, unsigned int PRIO_SIZE = 3, unsigned int OBJ_SIZE = 32>
SC_MODULE(Priority_Linked_List) {

	sc_in<bool> clk_in;
	sc_in<bool> rst_in;

	sc_out<sc_uint<IDX_SIZE+1> > head_out;
	sc_out<sc_uint<PRIO_SIZE> > priority_out;

	sc_out<bool> list_empty_out;
	sc_out<bool> list_full_out;
	sc_out<bool> alloc_empty_out;
	sc_out<bool> alloc_full_out;
	sc_out<sc_uint<IDX_SIZE+1> > count_out;

	sc_in<sc_uint<IDX_SIZE> > idx_in;
	sc_out<sc_uint<IDX_SIZE+1> > idx_out;
	sc_in<sc_uint<OBJ_SIZE> > object_in;
	sc_out<sc_uint<OBJ_SIZE> > object_out;
	sc_out<bool> found_out;
	sc_in<sc_uint<PRIO_SIZE> > priority_in;
	sc_out<bool> ready_out;
	sc_in<bool> request_in;
	sc_in<sc_uint<3> > operation_in;

	sc_in<bool> sim_debug_in;

	static const unsigned int OP_ALLOCATE = 0x0;
	static const unsigned int OP_DEALLOCATE = 0x1;
	static const unsigned int OP_INSERT = 0x2;
	static const unsigned int OP_REMOVE = 0x3;
	static const unsigned int OP_REMOVE_HEAD = 0x4;
	static const unsigned int OP_GET_IDX = 0x5;
	static const unsigned int OP_SEARCH_IDX = 0x6;
	static const unsigned int OP_GET_OBJECT = 0x7;
	//static const unsigned int OP_DEBUG = 0x7;

	SC_CTOR(Priority_Linked_List){
		SC_CTHREAD(main_process, clk_in.pos());
		reset_signal_is(rst_in, true);

		//SC_METHOD(count_process);//TODO commented for synthesis with agility (uncomment for a working model)
		//sensitive << count;//TODO commented for synthesis with agility (uncomment for a working model)

		//SC_METHOD(ready_process);//TODO commented for synthesis with agility (uncomment for a working model)
		//sensitive << idle << request_in;//TODO commented for synthesis with agility (uncomment for a working model)

		//SC_METHOD(bitmap_full_empty_process);
		//sensitive << clk_in.pos();

		//SC_METHOD(set_prio_out);
		//sensitive << head_out << list_empty_out;

		//SC_METHOD(dump_data);
		//sensitive << clk_in.pos();
	}

	static const unsigned int LIST_SIZE = 1 << IDX_SIZE;

	bool alloc_bitmap[LIST_SIZE+1];
	bool in_fifo_bitmap[LIST_SIZE];
	sc_uint<OBJ_SIZE> objects[LIST_SIZE+1];

	sc_uint<PRIO_SIZE> priority[LIST_SIZE+1];
	sc_uint<IDX_SIZE+1> next_table[LIST_SIZE+1];
	sc_uint<IDX_SIZE+1> prev_table[LIST_SIZE+1];

	sc_signal<sc_uint<IDX_SIZE+1> > search_current;
	sc_signal<sc_uint<IDX_SIZE+1> > search_insert;
	sc_signal<sc_uint<IDX_SIZE+1> > count;

	sc_signal<bool> idle;


	static const unsigned int NULL_VAL = LIST_SIZE;

	static bool null(sc_uint<IDX_SIZE+1> val){
		sc_uint<IDX_SIZE+1> comp(NULL_VAL);
		return val == comp;
	}

	static sc_uint<IDX_SIZE+1> null(){
		sc_uint<IDX_SIZE+1> comp(NULL_VAL);
		return comp;
	}

	void ready_process(){
		ready_out = idle.read() && !request_in.read();
	}

	void count_process(){
		count_out = count.read();
		list_empty_out = count.read() == 0;
		list_full_out = count.read()[IDX_SIZE] == 1;
	}

	void update_bitmap_full_empty(){
		bool and_reduce = true;
		bool or_reduce = false;
		for (unsigned int i = 0; i < LIST_SIZE; ++i) {
			and_reduce = and_reduce && alloc_bitmap[i];
			or_reduce = or_reduce || alloc_bitmap[i];
		}
		alloc_full_out = and_reduce;
		alloc_empty_out = !or_reduce;
	}

	/*
	void set_prio_out(){
		if(!list_empty_out.read())
			priority_out = priority[head_out.read()];
		else
			priority_out = 0;
	}
	*/

	void main_process(){
		main_process_reset();
		wait();
		while(true){
			main_process_behavior();
			wait();
		}
	}

	void main_process_reset(){
		//TODO commented for synthesis with agility (uncomment for a working model)
		/*head_out = null();
		priority_out = 0;
		for (unsigned int i = 0; i < LIST_SIZE; ++i) {
			alloc_bitmap[i] = false;
			objects[i] = 0;
			priority[i] = 0;
			next_table[i] = null();
			prev_table[i] = null();
			in_fifo_bitmap[i] = false;
		}
		alloc_bitmap[LIST_SIZE] = true;
		objects[LIST_SIZE] = 0;
		priority[LIST_SIZE] = 0;
		next_table[LIST_SIZE] = null();
		prev_table[LIST_SIZE] = null();

		search_current = null();
		search_insert = null();
		count = 0;
		idle = false;
		found_out = false;*/
	}

	void main_process_behavior(){
		if(request_in.read()){
			idle = false;
			switch (operation_in.read()) {
				case OP_ALLOCATE:
					allocate();
					break;
				case OP_DEALLOCATE:
					deallocate();
					break;
				case OP_INSERT:
					insert();
					count = count.read() + 1;
					break;
				case OP_REMOVE:
					remove();
					count = count.read() - 1;
					break;
				case OP_REMOVE_HEAD:
					remove_head();
					count = count.read() - 1;
					break;
				case OP_GET_IDX:
					get_idx();
					break;
				case OP_SEARCH_IDX:
					search_idx();
					break;
				case OP_GET_OBJECT:
					get_object();
					break;
				default:
					break;
			}
		}
		update_bitmap_full_empty();
		idle = true;
	}

	void allocate(){
		sc_uint<OBJ_SIZE> obj_aux = object_in.read();
		sc_uint<PRIO_SIZE> prio_aux = priority_in.read();

		unsigned int free = NULL_VAL;
		for (unsigned int i = 0; i < LIST_SIZE; ++i) {
			if (!alloc_bitmap[i])
				free = i;
		}

		wait();

		objects[free] = obj_aux;
		priority[free] = prio_aux;
		idx_out = free;
		alloc_bitmap[free] = true;
	}

	void deallocate(){
		alloc_bitmap[idx_in.read()] = false;
	}

	void get_idx(){
		sc_uint<OBJ_SIZE> aux = object_in.read();
		idx_out = null();
		found_out = false;
		wait();
		for (unsigned int i = 0; i < LIST_SIZE; ++i) {
			if((aux == objects[i]) && alloc_bitmap[i]){
				idx_out = i;
				found_out = true;
				break;
			}
			wait();
		}
	}

	void search_idx(){
		if(alloc_bitmap[idx_in.read()] && in_fifo_bitmap[idx_in.read()]){
			found_out = true;
			object_out = objects[idx_in.read()];
		}
		else{
			found_out = false;
		}
		wait();
	}

	void get_object(){
		if(alloc_bitmap[idx_in.read()]){
			found_out = true;
			object_out = objects[idx_in.read()];
		}
		else{
			found_out = false;
		}
		wait();
	}

	void insert(){
		priority[idx_in.read()] = priority_in.read();
		if(list_empty_out.read()){
			head_out = idx_in.read();
			priority_out = priority_in.read();
			next_table[idx_in.read()] = null();
			prev_table[idx_in.read()] = null();
			in_fifo_bitmap[idx_in.read()] = true;
		}
		else{
			//TODO otimizar os aux e a comparação
			search_current = head_out.read();
			search_insert = idx_in.read();
			bool not_found = false;
			wait();
			while(priority[search_insert.read()] >= priority[search_current.read()]){
				not_found = null(next_table[search_current.read()]);
				if(not_found) break;
				search_current = next_table[search_current.read()];
				wait();
			}

			//search_current should have a priority > then search_insert
			in_fifo_bitmap[search_insert.read()] = true;
			if(not_found){
				//insere depois
				sc_uint<IDX_SIZE+1> aux = next_table[search_current.read()];
				next_table[search_current.read()] = search_insert.read();
				next_table[search_insert.read()] = aux;
				prev_table[search_insert.read()] = search_current.read();
				//if(!null(aux))
				prev_table[aux] = search_insert.read();
			}
			else{
				//insere antes
				if(search_current.read() == head_out.read()){
					prev_table[search_insert.read()] = null();
					next_table[search_insert.read()] = search_current.read();
					prev_table[search_current.read()] = search_insert.read();
					head_out = search_insert.read();
					priority_out = priority[search_insert.read()];
				}
				else{
					sc_uint<IDX_SIZE+1> aux = prev_table[search_current.read()];
					next_table[aux] = search_insert.read();
					prev_table[search_insert.read()] = aux;
					next_table[search_insert.read()] = search_current.read();
					prev_table[search_current.read()] = search_insert.read();
				}
			}
		}
	}

	void remove(){
		priority[idx_in.read()] = 0;

		if(idx_in.read() == head_out.read()){
			remove_head();
		}
		else{
			next_table[prev_table[idx_in.read()]] = next_table[idx_in.read()];
			prev_table[next_table[idx_in.read()]] = prev_table[idx_in.read()];
			in_fifo_bitmap[idx_in.read()] = false;
		}

	}

	void remove_head(){
		priority[head_out.read()] = 0;

		head_out = next_table[head_out.read()];
		priority_out = priority[next_table[head_out.read()]];

		next_table[head_out.read()] = null();
		prev_table[head_out.read()] = null();

		prev_table[next_table[head_out.read()]] = null();

		in_fifo_bitmap[head_out.read()] = false;

	}

	/*
	void dump_data(){

		if(rst_in.read() || !sim_debug_in.read()) return;

		std::cout << "head_out: " << head_out.read() << "\n";

		std::cout << "priority = \t[";
		for (unsigned int i = 0; i < LIST_SIZE; ++i)
			std::cout << priority[i].read() << ", ";
		std::cout << "]\n";

		std::cout << "next_table = \t[";
		for (unsigned int i = 0; i < LIST_SIZE; ++i)
			std::cout << next_table[i].read() << ", ";
		std::cout << "]\n";

		std::cout << "prev_table = \t[";
		for (unsigned int i = 0; i < LIST_SIZE; ++i)
			std::cout << prev_table[i].read() << ", ";
		std::cout << "]\n";

		std::cout << "search_current: " << search_current.read() << "\n";
		std::cout << "search_insert: " << search_insert.read() << "\n";
		std::cout << "count: " << count.read() << "\n";

		std::cout << "list: ";

		sc_uint<IDX_SIZE+1> current = head_out.read();
		while(!null(current)){
			std::cout << "(" << current << ", " << priority[current].read() << ") -> ";
			current = next_table[current].read();
		}
		std::cout << std::endl;

	}

	void dump_data_internal(){
		std::cout << "---------------------\n";
		dump_data();
		std::cout << "---------------------" << std::endl;
	}
	*/

};


#endif /* PRIORITY_LINKED_LIST_H_ */
