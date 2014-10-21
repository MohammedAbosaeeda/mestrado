#include <object_table.h>
#include <system.h>

__BEGIN_SYS

int Object_Table::count = 0;

Object_Table::Object_Table(){
    for(int i = 0; i < 32; i++) {
        _vec[i] = 0;
    }
}

Object_Table::~Object_Table() {

}

Object_Address Object_Table::get_object(Object_Id id) {
    return _vec[id];
}

Object_Address Object_Table::delete_object(Object_Id id) {
    Object_Address add = _vec[id];
    _vec[id] = 0;
    return add;
}

Object_Id Object_Table::insert_object(Object_Address obj) {
    if( count < 32 ) {
        Object_Id id = get_free_id();
        _vec[id] = obj;
        return id;
    } else {
        int i = 0;
        while(_vec[i] != 0 && i < 32)
            i++;
        if(i == 32)
            return -1;
        _vec[i] = obj;
        return i;
    }
}

Object_Id Object_Table::get_free_id() {
    return count++;
}
__END_SYS
