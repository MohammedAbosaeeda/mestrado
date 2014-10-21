// EPOS ELF Utility Implementation

#include <utility/elf.h>
#include <utility/string.h>

__BEGIN_SYS

int ELF::load_segment(int i, Elf32_Addr addr) { return -1; }
// Compiler tries to compile this?
/*
int ELF::load_segment(int i, Elf32_Addr addr)
{
    // TODO: Fix return 0 bug (should return -1)
    if((i > segments()) || (segment_type(i) != PT_LOAD))
        return 0;
    
    char * src = (char *)(unsigned(this) + seg(i)->p_offset);
    char * dst = (char *)((addr) ? addr : segment_address(i));
    
    memcpy(dst, src, seg(i)->p_filesz);
    memset(dst + seg(i)->p_filesz, 0,
           seg(i)->p_memsz - seg(i)->p_filesz);
    
    return seg(i)->p_memsz;
}
*/

int ELF64::load_segment(int i, Elf64_Addr addr)
{
    // TODO: Fix return 0 bug (should return -1)
    if((i > segments()) || (segment_type(i) != PT_LOAD)) {
    	return 0;
    }
    
    char * src = (char *)(long(this) + seg(i)->p_offset);
    char * dst = (char *)((addr) ? addr : segment_address(i));
    
    memcpy(dst, src, seg(i)->p_filesz);
    memset(dst + seg(i)->p_filesz, 0,
       seg(i)->p_memsz - seg(i)->p_filesz);

    return seg(i)->p_memsz;
}


__END_SYS
