// EPOS-- SoftMIPS Display Mediator Declarations

#ifndef __softmips_display_h
#define __softmips_display_h

#include <display.h>

__BEGIN_SYS

class SoftMIPS_Display: public Display_Common
{
private:
    static const unsigned int FB =
	Traits<SoftMIPS_Display>::FRAME_BUFFER_ADDRESS;
    static const int LINES = Traits<SoftMIPS_Display>::LINES;
    static const int COLUMNS = Traits<SoftMIPS_Display>::COLUMNS;
    static const int TAB_SIZE = Traits<SoftMIPS_Display>::TAB_SIZE;

public:
    // Frame Buffer
    typedef unsigned char Cell;
    typedef Cell * Frame_Buffer;

public:
    SoftMIPS_Display(Frame_Buffer fb = reinterpret_cast<Frame_Buffer>(FB))
	: _frame_buffer(fb) {}

    void putc(char c)
    {
		switch(c) {
		case '\n':
			_cursor = (_cursor + COLUMNS) / COLUMNS * COLUMNS;
			break;
		case '\t':
			_cursor = (_cursor + TAB_SIZE) / TAB_SIZE * TAB_SIZE;
			break;
		default:
			*_frame_buffer = c;
		}
		if(_cursor >= LINES * COLUMNS) {
			scroll();
			_cursor-= COLUMNS;
		}
    }

    void puts(const char * s) {
	while(*s != '\0')
	    putc(*s++);
    }

    void clear() { 
	for(unsigned int i = 0; i < LINES * COLUMNS; i++)
	    _frame_buffer[i] = ' ';
	_cursor = 0;
    }

    void position(int * line, int * column) {
	*column = _cursor % COLUMNS;
	*line = _cursor / COLUMNS;
    }

    void position(int line, int column) {
	if(line > LINES)
	    line = LINES;
	if(column > COLUMNS)
	    column = COLUMNS;
	if((line < 0) || (column < 0)) {
	    int old_line, old_column;
	    position(&old_line, &old_column);
	    if(column < 0)
		column = old_column;
	    if(line < 0)
		line = old_line;
	}
	_cursor = line * COLUMNS + column;
    }

    void geometry(int * lines, int * columns) {
	*lines = LINES;
	*columns = COLUMNS;
    }

    static int init(System_Info * si) { return 0; }

private:
    void scroll() {
	for(unsigned int i = 0; i < (LINES - 1) * COLUMNS; i++)
	    _frame_buffer[i] = _frame_buffer[i + COLUMNS];
	for(unsigned int i = (LINES - 1) * COLUMNS; i < LINES * COLUMNS; i++)
	    _frame_buffer[i] = ' ';
    }

private:
    unsigned int _cursor;
    Frame_Buffer _frame_buffer;
};

__END_SYS

#endif
