// EPOS Mult Abstraction Declarations

#ifndef __mult_unified_h
#define __mult_unified_h

#include "component.h"
#include C_INCLUDE(add)

namespace Unified {

class Mult : public Component{

public:
	enum {
		OP_MULT = 0xF0,
		OP_MULT_SQUARE = 0xF1
	};

private:
	System::Add add;


public:
	Mult(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<Mult>::n_ids])
	:Component(rx_ch, tx_ch, iid[0]),
	 add(rx_ch, tx_ch, &iid[1]){}


public:
	unsigned int mult(unsigned int a, unsigned int b){
		unsigned int acc = 0;
		MULT: for (unsigned int i = 0; i < a; ++i) {
			acc = add.add(acc, b);
		}
		return acc;
	}

	unsigned int mult_square(unsigned int a, unsigned int b){
		unsigned int acc = mult(a,b);
		unsigned int square = mult(acc,acc);

		return square;
	}

};

}


#endif /* MULT_H_ */
