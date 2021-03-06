// EPOS-- PC_Display (CGA/6845) Declarations
//

#ifndef __pc_display_h
#define __pc_display_h

#include <display.h>

__BEGIN_SYS

class PC_Display: public Display_Common
{
private:
    typedef Traits<PC_Display> _Traits;
//    static const Type_Id TYPE = Type<PC_Display>::TYPE;

    // 6845 Registers
    enum {
	ADDR_REG  = 0x03d4,
	DATA_REG  = 0x03d5,
	CTRL_REG  = 0x03d8,
	COLOR_REG = 0x03d9
    };

    // 6845 Internal Addresses
    enum {
	ADDR_CUR_START	= 0x0a,	// cursor mask
	ADDR_CUR_END	= 0x0b,
	ADDR_PAGE_HI	= 0x0c,	// current frame buffer page
	ADDR_PAGE_LO	= 0x0d,
	ADDR_CUR_POS_HI	= 0x0e,	// current curor position
	ADDR_CUR_POS_LO	= 0x0f
    };

public:
    PC_Display(unsigned short * fb = reinterpret_cast<unsigned short *>(
		   _Traits::FRAME_BUFFER_ADDRESS))
	: _frame_buffer(fb) {}
    ~PC_Display() {}

    void clear() { 
	for(unsigned int i = 0; i < lines() * columns(); i++)
	    _frame_buffer[i] = 0x720;
	position(0);
    }
    void putc(char c){
	unsigned int pos = position();

	switch(c) {
	case '\n':
	    pos = (pos + columns()) / columns() * columns();
	    break;
	case '\t':
	    pos = (pos + tab_size()) / tab_size() * tab_size();
	    break;
	default:
	    _frame_buffer[pos++] = 0x0700 | c;
	}
	if(pos >= lines() * columns()) {
	    scroll();
	    pos-= columns();
	}
	position(pos);
    }
    void puts(const char * s) {
	while(*s != '\0')
	    putc(*s++);
    }

    unsigned int lines() { return _Traits::LINES; }
    unsigned int columns() { return _Traits::COLUMNS; }

    void position(int * line, int * column) {
	unsigned int pos = position();
	*column = pos % columns();
	*line = pos / columns();
    }
    void position(int line, int column) {
	if(line > static_cast<int>(lines()))
	    line = lines();
	if(column > static_cast<int>(columns()))
	    column = columns();
	if((line < 0) || (column < 0)) {
	    int old_line, old_column;
	    position(&old_line, &old_column);
	    if(column < 0)
		column = old_column;
	    if(line < 0)
		line = old_line;
	}
	position(line * columns() + column);
    }

    static int init(System_Info *si);

private:
    unsigned int tab_size() { return _Traits::TAB_SIZE; }

    volatile int position() {
	IA32 cpu;
	cpu.out8(ADDR_REG, ADDR_CUR_POS_LO);
	int pos = cpu.in8(DATA_REG);
	cpu.out8(ADDR_REG, ADDR_CUR_POS_HI);
	pos |= cpu.in8(DATA_REG) << 8;
	return pos;
    }
    void position(int pos) {
	IA32 cpu;
	cpu.out8(ADDR_REG, ADDR_CUR_POS_LO);
	cpu.out8(DATA_REG, pos & 0xff);
	cpu.out8(ADDR_REG, ADDR_CUR_POS_HI);
	cpu.out8(DATA_REG, pos >> 8);
    }

    void scroll() {
	for(unsigned int i = 0; i < (lines() - 1) * columns(); i++)
	    _frame_buffer[i] = _frame_buffer[i + columns()];
	for(unsigned int i = (lines() - 1) * columns(); 
	    i < lines() * columns(); i++)
	    _frame_buffer[i] = 0x0720;
    }

private:
    unsigned short * _frame_buffer;
};

__END_SYS

#endif
