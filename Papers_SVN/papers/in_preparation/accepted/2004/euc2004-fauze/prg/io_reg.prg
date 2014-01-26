template<typename reg_type, 
                  IO_MODES mode = traits<Machine>::IO_MODE>
class IO_Register : public IO_Register<reg_type, mode> {};
//---------------------------------------
template<typename reg_type>
class IO_Register<reg_type, IO_PORT> { 
public:
    template<typename type> void operator=(type value) { 
	CPU::out(this, reinterpret_cast<reg_type>(value)); }  
    operator reg_type() { 
	return reinterpret_cast<reg_type>(CPU::in(this)); }  
private:
    reg_type data; // only to the proper size 
};
//---------------------------------------
template<typename reg_type>
class IO_Register<reg_type, MEMORY_MAPPED> {
public:
    template<typename type> void operator=(type value) { 
	data = reinterpret_cast<reg_type>(value); }
    operator reg_type() { return data; }  
private:
    reg_type data; 
};
