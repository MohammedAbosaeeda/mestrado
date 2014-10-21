#ifndef __resource_table_unified_h
#define __resource_table_unified_h

#include "resource.h"
#include "../utility/meta.h"
#include "../traits.h"

namespace Unified {

enum{
    NOC_SW_NODE = Address::LOCAL_NN,
    NOC_FREE_NODE_0 = Address::LOCAL_EE,
    NOC_FREE_NODE_1 = Address::LOCAL_SS,
    NOC_FREE_NODE_2 = Address::LOCAL_WW,
    NOC_FREE_NODE_3 = Address::LOCAL_NE,
    NOC_FREE_NODE_4 = Address::LOCAL_SE,
    NOC_FREE_NODE_5 = Address::LOCAL_SW,
    NOC_FREE_NODE_6 = Address::LOCAL_NW,//Free
};


template<class T, int IID>
class Resource_Table;

template<class T>
class Resource_Table_Array;

template<> class Resource_Table<Add,0> {public: static const unsigned int X=0,Y=0;
    static const unsigned int HW_LOCAL= NOC_FREE_NODE_0;
    static const unsigned int LOCAL= IF_INT<Traits<Add>::hardware,HW_LOCAL,NOC_SW_NODE>::Result;
    static const unsigned char IID[Traits<Add>::n_ids];};

template<> class Resource_Table_Array<Add>{public:
    static const unsigned char IID[1][Traits<Add>::n_ids];
    static unsigned int id_count;};


template<> class Resource_Table<Mult,0> {public: static const unsigned int X=0,Y=0;
    static const unsigned int HW_LOCAL= NOC_FREE_NODE_2;
    static const unsigned int LOCAL= IF_INT<Traits<Mult>::hardware,HW_LOCAL,NOC_SW_NODE>::Result;
    static const unsigned char IID[Traits<Mult>::n_ids];};

template<> class Resource_Table_Array<Mult>{public:
    static const unsigned char IID[1][Traits<Mult>::n_ids];
    static unsigned int id_count;};



template<class T, int IID> struct Type2IDX;

template<> struct Type2IDX<Add,0>{ enum {IDX = 0}; };

template<> struct Type2IDX<Mult,0>{ enum {IDX = 1}; };

class PHY_Table{
public:

    static int type2IDX(int tid, int iid){
        switch (tid) {
        case ADD_ID:
            switch (iid) {
            case 0:
                return Type2IDX<Add,0>::IDX; break;
            default:
                break;
            }
        break;
        case MULT_ID:
            switch (iid) {
            case 0:
                return Type2IDX<Mult,0>::IDX; break;
            default:
                break;
            }
        break;
        default:
        break;
        }
        return -1;
    }

    static const unsigned int X[7],Y[7],LOCAL[7];
};

}

#endif /* __resource_h */
