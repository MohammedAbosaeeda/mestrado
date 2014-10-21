#include <syscall_types.h>
#include <system/config.h>
#include <utility/vector.h>

#ifndef _object_table_h
#define _object_table_h

__BEGIN_SYS

class Object_Table {
    public:

    Object_Table();
    ~Object_Table();

	Object_Address get_object(Object_Id id);
	Object_Address delete_object(Object_Id id);
	Object_Id insert_object(Object_Address obj);
    static Object_Id get_free_id();

    private:
        static int count;
        Object_Address _vec[32];
};


__END_SYS
#endif
