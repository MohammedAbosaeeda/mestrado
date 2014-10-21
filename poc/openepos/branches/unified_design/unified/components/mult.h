// EPOS Mult Abstraction Declarations

#ifndef __mult_unified_h
#define __mult_unified_h

#include "component.h"

#include "add.h"

namespace Implementation {

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

PROXY_BEGIN(Mult)
    unsigned int mult(unsigned int a, unsigned int b){
        return Base::call_r<Mult::OP_MULT,unsigned int>(a,b);
    }
    unsigned int mult_square(unsigned int a, unsigned int b){
        return Base::call_r<Mult::OP_MULT_SQUARE,unsigned int>(a,b);
    }
PROXY_END


AGENT_BEGIN(Mult)
    D_CALL_R_2(mult, OP_MULT, unsigned int, unsigned int, unsigned int)
    D_CALL_R_2(mult_square, OP_MULT_SQUARE, unsigned int, unsigned int, unsigned int)
AGENT_END

};

DECLARE_COMPONENT(Mult);


#endif /* MULT_H_ */
