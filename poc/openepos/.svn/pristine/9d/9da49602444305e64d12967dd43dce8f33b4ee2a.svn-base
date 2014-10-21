
#ifndef __resource_table_iids_h
#define __resource_table_iids_h

namespace Unified {

const unsigned char Resource_Table<Add,0>::IID[Traits<Add>::n_ids] = {0};
unsigned int Resource_Table_Array<Add>::id_count = 0;
const unsigned char Resource_Table_Array<Add>::IID[1][Traits<Add>::n_ids] = { {Resource_Table<Add,0>::IID[0]} };

const unsigned char Resource_Table<Mult,0>::IID[Traits<Mult>::n_ids] = {0,Resource_Table<Add,0>::IID[0]};
unsigned int Resource_Table_Array<Mult>::id_count = 0;
const unsigned char Resource_Table_Array<Mult>::IID[1][Traits<Mult>::n_ids] = { {Resource_Table<Mult,0>::IID[0],Resource_Table<Mult,0>::IID[1]}};


const unsigned int PHY_Table::X[7] = { Resource_Table<Add,0>::X,
                                       Resource_Table<Mult,0>::X,
                                       0,
                                       0,
                                       0,
                                       0,
                                       0};
const unsigned int PHY_Table::Y[7] = { Resource_Table<Add,0>::Y,
                                       Resource_Table<Mult,0>::Y,
                                       0,
                                       0,
                                       0,
                                       0,
                                       0};
const unsigned int PHY_Table::LOCAL[7] = { Resource_Table<Add,0>::LOCAL,
                                              Resource_Table<Mult,0>::LOCAL,
                                              0,
                                              0,
                                              0,
                                              0,
                                              0};

}

#endif
