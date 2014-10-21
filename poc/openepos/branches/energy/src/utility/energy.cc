// OpenEPOS Energy Management Utility Implementation

#include <utility/energy.h>
#include <system/kmalloc.h>

__BEGIN_SYS

void * wrap_kcalloc(unsigned int n, unsigned int bytes) {
    return kcalloc(n, bytes);
}

__END_SYS
